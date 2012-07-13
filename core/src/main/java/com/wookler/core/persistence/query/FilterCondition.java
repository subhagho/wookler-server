/**
 * 
 */
package com.wookler.core.persistence.query;

/**
 * @author subhagho
 * 
 */
public class FilterCondition {
	public static final String _STRING_DEFAULT_ = "DEFAULT";
	public static final String _STRING_NULL_ = "NULL";

	private String column;
	private EnumOperator comparator;
	private Object value;

	public FilterCondition(String column, EnumOperator comparator, Object value) {
		this.column = column;
		this.comparator = comparator;
		this.value = value;
	}

	/**
	 * @return the column
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * @param column
	 *            the column to set
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * @return the comparator
	 */
	public EnumOperator getComparator() {
		return comparator;
	}

	/**
	 * @param comparator
	 *            the comparator to set
	 */
	public void setComparator(EnumOperator comparator) {
		this.comparator = comparator;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(column).append(" ").append(comparator.name()).append(" ");
		if (value != null) {
			if (!value.getClass().isArray()) {
				buff.append(value.toString());
			} else {
				String[] data = (String[]) value;
				buff.append("[");
				boolean first = true;
				for (String dd : data) {
					if (first)
						first = false;
					else
						buff.append(", ");
					buff.append(dd);
				}

				buff.append("]");
			}
		} else {
			buff.append("NULL");
		}
		return buff.toString();
	}
}
