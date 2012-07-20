/**
 * 
 */
package com.wookler.core.persistence.db;

import java.lang.reflect.Field;
import java.util.Date;

import com.wookler.core.persistence.AttributeReflection;
import com.wookler.core.persistence.Entity;
import com.wookler.core.persistence.EnumPrimitives;
import com.wookler.core.persistence.ReflectionUtils;
import com.wookler.core.persistence.query.AbstractCondition;
import com.wookler.core.persistence.query.ConditionTransformer;
import com.wookler.core.persistence.query.EnumOperator;
import com.wookler.core.persistence.query.FilterCondition;
import com.wookler.utils.DateUtils;
import com.wookler.utils.KeyValuePair;

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
			String cleft = null;
			if (type != null) {
				if (fc.getColumn().indexOf('.') > 0) {
					String[] refpath = fc.getColumn().split("\\.");
					KeyValuePair<Class<?>> kvp = getJoinCondition(refpath, 0,
							type);
					cleft = kvp.getKey();
					type = kvp.getValue();
				} else {
					String table = null;

					// Get table name
					Entity eann = (Entity) type.getAnnotation(Entity.class);
					table = eann.recordset();

					cleft = table + "." + fc.getColumn();
				}
			} else {
				cleft = fc.getColumn();
			}

			StringBuffer buff = new StringBuffer(cleft);
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
			case Contains:
				buff.append(" like ");
				fc.setValue("%" + (String) fc.getValue() + "%");
				break;
			default:
				throw new Exception("Operator [" + fc.getComparator().name()
						+ "] not supported");
			}
			if (type != null) {
				String value = getValue(fc, type);
				buff.append(value);
			} else {
				buff.append(fc.getValue());
			}

			return buff.toString();
		}
		return null;
	}

	private KeyValuePair<Class<?>> getJoinCondition(String[] reference,
			int offset, Class<?> type) throws Exception {
		String column = reference[offset];
		AttributeReflection attr = ReflectionUtils.get().getAttribute(type,
				column);
		if (offset == reference.length - 1) {
			Entity eann = (Entity) type.getAnnotation(Entity.class);
			String table = eann.recordset();
			String cleft = table.concat(".").concat(attr.Column);; 
			return new KeyValuePair<Class<?>>(cleft, type);
		} else {
			if (attr.Reference == null)
				throw new Exception(
						"Invalid Condition : Cannot resolve column [" + column
								+ "] for type [" + type.getCanonicalName()
								+ "]");
			Class<?> rtype = Class.forName(attr.Reference.Class);
			return getJoinCondition(reference, offset + 1, rtype);
		}
	}

	private String getValue(FilterCondition fc, Class<?> type) throws Exception {
		String column = fc.getColumn();
		if (column.indexOf('.') > 0) {
			String[] parts = column.split("\\.");
			column = parts[parts.length - 1];
		}
		AttributeReflection attr = ReflectionUtils.get().getAttribute(type,
				column);
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
