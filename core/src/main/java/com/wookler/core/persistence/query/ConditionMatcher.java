/**
 * 
 */
package com.wookler.core.persistence.query;

import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;

import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.AttributeReflection;
import com.wookler.core.persistence.EnumPrimitives;
import com.wookler.core.persistence.ReflectionUtils;

/**
 * Utility Class to match Entity attributes against the filter condition.
 * 
 * @author subhagho
 * 
 */
public class ConditionMatcher {
	/**
	 * Match the filter value with the Entity attribute of the specified entity
	 * record.
	 * 
	 * @param entity
	 *            - Entity record to check.
	 * @param column
	 *            - Attribute Column name
	 * @param operator
	 *            - Condition Operator
	 * @param value
	 *            - Value to match.
	 * @return
	 * @throws Exception
	 */
	public boolean match(AbstractEntity entity, String column,
			EnumOperator operator, Object value) throws Exception {
		if (column.indexOf('.') < 0) {
			AttributeReflection attr = ReflectionUtils.get().getAttribute(
					entity.getClass(), column);
			Object src = PropertyUtils
					.getProperty(entity, attr.Field.getName());
			if (src != null) {
				return compare(src, value, operator, attr.Field.getType());
			}
		} else {
			String[] vars = column.split(".");
			Object src = null;
			Class<?> type = null;

			for (String var : vars) {
				if (src == null) {
					AttributeReflection attr = ReflectionUtils.get()
							.getAttribute(entity.getClass(), var);
					src = PropertyUtils.getProperty(entity,
							attr.Field.getName());
					type = attr.Field.getType();
				} else {
					AttributeReflection attr = ReflectionUtils.get()
							.getAttribute(src.getClass(), var);
					src = PropertyUtils.getProperty(entity,
							attr.Field.getName());
					type = attr.Field.getType();
				}
			}
			if (src != null) {
				return compare(src, value, operator, type);
			}
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
			if (atype.isPrimitive()) {
				EnumPrimitives etype = EnumPrimitives.type(atype);
				switch (etype) {
				case EShort:
					return containsShortArray(src, tgt);
				case EInteger:
					return containsIntArray(src, tgt);
				case ELong:
					return containsLongArray(src, tgt);
				case EFloat:
					return containsFloatArray(src, tgt);
				case EDouble:
					return containsDoubleArray(src, tgt);
				case ECharacter:
					return containsCharArray(src, tgt);
				}
			} else {
				containsObjectArray(src, tgt);
			}
		} else if (src instanceof Iterable<?>) {
			if (operator != EnumOperator.Contains)
				return false;
			containsObjectList(src, tgt);

		} else if (src instanceof Enum) {
			return compareEnum(src, tgt);
		} else {
			throw new Exception("Unsupported value comparison. [CLASS:"
					+ type.getCanonicalName() + "]");
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Enum<T>> boolean compareEnum(Object src,
			Object tgt) throws Exception {
		String name = ((T) src).name();
		if (name.compareToIgnoreCase((String) tgt) == 0)
			return true;
		return false;
	}

	private boolean containsObjectList(Object src, Object tgt) throws Exception {
		Iterable<?> iterable = (Iterable<?>) src;
		Iterator<?> iter = iterable.iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();
			Class<?> type = obj.getClass();
			boolean retval = false;
			if (EnumPrimitives.isPrimitiveType(type)) {
				EnumPrimitives etype = EnumPrimitives.type(type);
				switch (etype) {
				case EShort:
					retval = compareShort(src, tgt, EnumOperator.Equal);
				case EInteger:
					retval = compareInt(src, tgt, EnumOperator.Equal);
				case ELong:
					retval = compareLong(src, tgt, EnumOperator.Equal);
				case EFloat:
					retval = compareFloat(src, tgt, EnumOperator.Equal);
				case EDouble:
					retval = compareDouble(src, tgt, EnumOperator.Equal);
				case ECharacter:
					retval = compareChar(src, tgt, EnumOperator.Equal);
				}
				retval = src.equals(tgt);
			} else {
				retval = src.equals(tgt);
			}
			if (retval)
				return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private <T> boolean containsObjectArray(Object src, Object tgt)
			throws Exception {
		T[] array = (T[]) src;
		for (T val : array) {
			if (val.equals((T) tgt))
				return true;
		}
		return false;
	}

	private boolean containsShortArray(Object src, Object tgt) throws Exception {
		short[] array = (short[]) src;
		for (short val : array) {
			if (val == (Short) tgt)
				return true;
		}
		return false;
	}

	private boolean containsIntArray(Object src, Object tgt) throws Exception {
		int[] array = (int[]) src;
		for (int val : array) {
			if (val == (Short) tgt)
				return true;
		}
		return false;
	}

	private boolean containsLongArray(Object src, Object tgt) throws Exception {
		long[] array = (long[]) src;
		for (long val : array) {
			if (val == (Short) tgt)
				return true;
		}
		return false;
	}

	private boolean containsFloatArray(Object src, Object tgt) throws Exception {
		float[] array = (float[]) src;
		for (float val : array) {
			if (val == (Short) tgt)
				return true;
		}
		return false;
	}

	private boolean containsDoubleArray(Object src, Object tgt)
			throws Exception {
		double[] array = (double[]) src;
		for (double val : array) {
			if (val == (Short) tgt)
				return true;
		}
		return false;
	}

	private boolean containsCharArray(Object src, Object tgt) throws Exception {
		char[] array = (char[]) src;
		for (char val : array) {
			if (val == (Short) tgt)
				return true;
		}
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
		case In:
			String[] values = (String[]) tgt;
			for (String value : values) {
				if (value.compareTo((String) src) == 0)
					return true;
			}
			return false;
		case Like:
			if (Pattern.matches((String) tgt, (String) src))
				return true;
			return false;
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
		case In:
			String[] svalues = (String[]) tgt;
			for (String value : svalues) {
				short shval = Short.parseShort(value);
				if (shval == (Short) src)
					return true;
			}
			return false;
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
		case In:
			String[] svalues = (String[]) tgt;
			for (String value : svalues) {
				int shval = Integer.parseInt(value);
				if (shval == (Integer) src)
					return true;
			}
			return false;
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
		case In:
			String[] svalues = (String[]) tgt;
			for (String value : svalues) {
				long shval = Long.parseLong(value);
				if (shval == (Long) src)
					return true;
			}
			return false;
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
		case In:
			String[] svalues = (String[]) tgt;
			for (String value : svalues) {
				float shval = Float.parseFloat(value);
				if (shval == (Float) src)
					return true;
			}
			return false;
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
		case In:
			String[] svalues = (String[]) tgt;
			for (String value : svalues) {
				double shval = Double.parseDouble(value);
				if (shval == (Double) src)
					return true;
			}
			return false;
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
		case In:
			String[] svalues = (String[]) tgt;
			for (String value : svalues) {
				if (value.charAt(0) == (Character) src)
					return true;
			}
			return false;
		default:
			return false;
		}
	}
}
