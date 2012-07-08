/**
 * 
 */
package com.wookler.core.persistence.query;

import java.util.Date;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;

import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.AttributeReflection;
import com.wookler.core.persistence.EnumPrimitives;
import com.wookler.core.persistence.ReflectionUtils;

/**
 * @author subhagho
 * 
 */
public class ConditionMatcher {
	public boolean match(AbstractEntity entity, String column,
			EnumOperator operator, Object value) throws Exception {
		if (column.indexOf('.') < 0) {
			AttributeReflection attr = ReflectionUtils.get().getAttribute(
					entity.getClass(), column);
			Object evalue = PropertyUtils.getProperty(entity,
					attr.Field.getName());
			if (evalue != null) {
				return compare(value, evalue, operator, attr.Field.getType());
			}
		} else {

		}
		return false;
	}

	private boolean compare(Object src, Object tgt, EnumOperator operator,
			Class<?> type) throws Exception {
		if (EnumPrimitives.isPrimitiveType(type)) {
			EnumPrimitives etype = EnumPrimitives.type(type);
			switch (etype) {
			case EShort:
				return compareShort(src, tgt, operator);
			case EInteger:
				return compareInt(src, tgt, operator);
			case ELong:
				return compareLong(src, tgt, operator);
			case EFloat:
				return compareFloat(src, tgt, operator);
			case EDouble:
				return compareDouble(src, tgt, operator);
			case ECharacter:
				return compareChar(src, tgt, operator);
			}
			return src.equals(tgt);
		} else if (type == String.class) {
			return compareString(src, tgt, operator);
		} else if (type == Date.class) {
			Date ds = (Date) src;
			Date dt = (Date) tgt;
			return compareLong(ds.getTime(), dt.getTime(), operator);
		} else if (type.isArray()) {
			if (operator != EnumOperator.Contains)
				return false;
			Class<?> atype = type.getComponentType();
			if (EnumPrimitives.isPrimitiveType(atype)) {
				EnumPrimitives etype = EnumPrimitives.type(atype);
			} else if (atype.equals(String.class)) {

			}
		} else {
			throw new Exception("Unsupported value comparison. [CLASS:"
					+ type.getCanonicalName() + "]");
		}
		return false;
	}

	private boolean containsShortArray(Object src, Object tgt) throws Exception {
		return false;
	}

	private boolean compareString(Object src, Object tgt, EnumOperator oper) {
		switch (oper) {
		case Equal:
			return (((String) src).compareTo((String) tgt) == 0);
		case GreaterThan:
			return (((String) src).compareTo((String) tgt) > 0);
		case GreaterThanEqual:
			return (((String) src).compareTo((String) tgt) >= 0);
		case LessThan:
			return (((String) src).compareTo((String) tgt) < 0);
		case LessThanEqual:
			return (((String) src).compareTo((String) tgt) <= 0);
		case NotEqual:
			return (((String) src).compareTo((String) tgt) != 0);
		default:
			return false;
		}
	}

	private boolean compareShort(Object src, Object tgt, EnumOperator oper) {
		switch (oper) {
		case Equal:
			return ((Short) src == Short.parseShort((String) tgt));
		case GreaterThan:
			return ((Short) src > Short.parseShort((String) tgt));
		case GreaterThanEqual:
			return ((Short) src >= Short.parseShort((String) tgt));
		case LessThan:
			return ((Short) src < Short.parseShort((String) tgt));
		case LessThanEqual:
			return ((Short) src <= Short.parseShort((String) tgt));
		case NotEqual:
			return ((Short) src != Short.parseShort((String) tgt));
		case Between:
			String[] values = (String[]) tgt;
			short minv = Short.parseShort(values[0]);
			short maxv = Short.parseShort(values[1]);
			return ((Short) src >= minv && (Short) src <= maxv);
		default:
			return false;
		}
	}

	private boolean compareInt(Object src, Object tgt, EnumOperator oper) {
		switch (oper) {
		case Equal:
			return ((Integer) src == Integer.parseInt((String) tgt));
		case GreaterThan:
			return ((Integer) src > Integer.parseInt((String) tgt));
		case GreaterThanEqual:
			return ((Integer) src >= Integer.parseInt((String) tgt));
		case LessThan:
			return ((Integer) src < Integer.parseInt((String) tgt));
		case LessThanEqual:
			return ((Integer) src <= Integer.parseInt((String) tgt));
		case NotEqual:
			return ((Integer) src != Integer.parseInt((String) tgt));
		case Between:
			String[] values = (String[]) tgt;
			int minv = Integer.parseInt(values[0]);
			int maxv = Integer.parseInt(values[1]);
			return ((Integer) src >= minv && (Integer) src <= maxv);
		case Contains:
			int itgt = Integer.parseInt((String) tgt);
			if (src.getClass().isArray()) {

			} else if (src instanceof Iterable<?>) {
				Iterable<?> iobj = (Iterable<?>) src;
				Iterator<?> iter = iobj.iterator();
				while (iter.hasNext()) {
					int val = (Integer) iter.next();
					if (val == itgt)
						return true;
				}
			}
		default:
			return false;
		}
	}

	private boolean compareLong(Object src, Object tgt, EnumOperator oper) {
		switch (oper) {
		case Equal:
			return ((Long) src == Long.parseLong((String) tgt));
		case GreaterThan:
			return ((Long) src > Long.parseLong((String) tgt));
		case GreaterThanEqual:
			return ((Long) src >= Long.parseLong((String) tgt));
		case LessThan:
			return ((Long) src < Long.parseLong((String) tgt));
		case LessThanEqual:
			return ((Long) src <= Long.parseLong((String) tgt));
		case NotEqual:
			return ((Long) src != Long.parseLong((String) tgt));
		case Between:
			String[] values = (String[]) tgt;
			long minv = Long.parseLong(values[0]);
			long maxv = Long.parseLong(values[1]);
			return ((Long) src >= minv && (Long) src <= maxv);
		default:
			return false;
		}
	}

	private boolean compareFloat(Object src, Object tgt, EnumOperator oper) {
		switch (oper) {
		case Equal:
			return ((Float) src == Float.parseFloat((String) tgt));
		case GreaterThan:
			return ((Float) src > Float.parseFloat((String) tgt));
		case GreaterThanEqual:
			return ((Float) src >= Float.parseFloat((String) tgt));
		case LessThan:
			return ((Float) src < Float.parseFloat((String) tgt));
		case LessThanEqual:
			return ((Float) src <= Float.parseFloat((String) tgt));
		case NotEqual:
			return ((Float) src != Float.parseFloat((String) tgt));
		case Between:
			String[] values = (String[]) tgt;
			float minv = Float.parseFloat(values[0]);
			float maxv = Float.parseFloat(values[1]);
			return ((Float) src >= minv && (Float) src <= maxv);
		default:
			return false;
		}
	}

	private boolean compareDouble(Object src, Object tgt, EnumOperator oper) {
		switch (oper) {
		case Equal:
			return ((Double) src == Double.parseDouble((String) tgt));
		case GreaterThan:
			return ((Double) src > Double.parseDouble((String) tgt));
		case GreaterThanEqual:
			return ((Double) src >= Double.parseDouble((String) tgt));
		case LessThan:
			return ((Double) src < Double.parseDouble((String) tgt));
		case LessThanEqual:
			return ((Double) src <= Double.parseDouble((String) tgt));
		case NotEqual:
			return ((Double) src != Double.parseDouble((String) tgt));
		case Between:
			String[] values = (String[]) tgt;
			double minv = Double.parseDouble(values[0]);
			double maxv = Double.parseDouble(values[1]);
			return ((Double) src >= minv && (Double) src <= maxv);
		default:
			return false;
		}
	}

	private boolean compareChar(Object src, Object tgt, EnumOperator oper) {
		switch (oper) {
		case Equal:
			return ((Character) src == ((String) tgt).charAt(0));
		case GreaterThan:
			return ((Character) src > ((String) tgt).charAt(0));
		case GreaterThanEqual:
			return ((Character) src >= ((String) tgt).charAt(0));
		case LessThan:
			return ((Character) src < ((String) tgt).charAt(0));
		case LessThanEqual:
			return ((Character) src <= ((String) tgt).charAt(0));
		case NotEqual:
			return ((Character) src != ((String) tgt).charAt(0));
		case Between:
			String[] values = (String[]) tgt;
			char minv = values[0].charAt(0);
			char maxv = values[1].charAt(0);
			return ((Character) src >= minv && (Character) src <= maxv);
		default:
			return false;
		}
	}
}
