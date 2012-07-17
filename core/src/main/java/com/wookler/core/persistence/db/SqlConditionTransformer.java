/**
 * 
 */
package com.wookler.core.persistence.db;

import java.lang.reflect.Field;
import java.util.Date;

import com.wookler.core.persistence.AttributeReflection;
import com.wookler.core.persistence.EnumPrimitives;
import com.wookler.core.persistence.ReflectionUtils;
import com.wookler.core.persistence.query.AbstractCondition;
import com.wookler.core.persistence.query.ConditionTransformer;
import com.wookler.core.persistence.query.EnumOperator;
import com.wookler.core.persistence.query.FilterCondition;
import com.wookler.utils.DateUtils;

/**
 * @author subhagho
 * 
 */
public class SqlConditionTransformer implements ConditionTransformer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wookler.core.persistence.query.ConditionTransformer#transform(com
	 * .wookler.core.persistence.query.AbstractCondition)
	 */
	public String transform(AbstractCondition condition, Class<?> type)
			throws Exception {
		if (condition instanceof FilterCondition) {
			FilterCondition fc = (FilterCondition) condition;
			StringBuffer buff = new StringBuffer(fc.getColumn());
			switch (fc.getComparator()) {
			case Equal:
				buff.append(" = ");
				break;
			case NotEqual:
				buff.append(" <> ");
				break;
			case GreaterThan:
				buff.append(" > ");
				break;
			case GreaterThanEqual:
				buff.append(" >= ");
				break;
			case LessThan:
				buff.append(" < ");
				break;
			case LessThanEqual:
				buff.append(" <= ");
				break;
			case Between:
				buff.append(" between ");
				break;
			case In:
				buff.append(" in ");
				break;
			case Like:
				buff.append(" like ");
				break;
			default:
				throw new Exception("Operator [" + fc.getComparator().name()
						+ "] not supported");
			}
			String value = getValue(fc, type);
			buff.append(value);

			return buff.toString();
		}
		return null;
	}

	private String getValue(FilterCondition fc, Class<?> type) throws Exception {
		AttributeReflection attr = ReflectionUtils.get().getAttribute(type,
				fc.getColumn());
		if (fc.getValue() instanceof String) {
			String value = (String) fc.getValue();
			return quoteValue(value, attr.Field);
		} else if (fc.getValue().getClass().isArray()) {
			String[] values = (String[]) fc.getValue();
			for (int ii = 0; ii < values.length; ii++) {
				values[ii] = quoteValue(values[ii], attr.Field);
			}
			if (fc.getComparator() == EnumOperator.Between) {
				return values[0] + " and " + values[1];
			} else if (fc.getComparator() == EnumOperator.In) {
				StringBuffer buff = new StringBuffer();
				buff.append("(");
				boolean first = false;
				for (String value : values) {
					if (first)
						first = false;
					else
						buff.append(',');
					buff.append(value);
				}
				buff.append(")");
			}
		}
		return null;
	}

	private String quoteValue(String value, Field field) throws Exception {
		if (value.startsWith("'") || value.startsWith("\""))
			return value;

		if (EnumPrimitives.isPrimitiveType(field.getType())) {
			EnumPrimitives prim = EnumPrimitives.type(field.getType());
			if (prim == EnumPrimitives.ECharacter) {
				return "'" + value.charAt(0) + "'";
			} else {
				return value;
			}
		} else {
			if (field.getType().equals(String.class)) {
				return "'" + value + "'";
			} else if (field.getType().equals(Date.class)) {
				Date dt = DateUtils.fromString(value);
				return String.valueOf(dt.getTime());
			} else if (field.getType().isEnum()) {
				return "'" + value + "'";
			}
		}
		return value;
	}
}
