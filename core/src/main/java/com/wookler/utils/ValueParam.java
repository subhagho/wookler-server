/**
 * 
 */
package com.wookler.utils;

import org.w3c.dom.Element;

/**
 * @author subhagho
 * 
 */
public class ValueParam extends AbstractParam {
	private String value;

	public ValueParam(String key, String value) {
		type = EnumParamType.Value;
		this.key = key;
		this.value = value;
	}

	public ValueParam() {
		type = EnumParamType.Value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wookler.utils.AbstractParam#parse(org.w3c.dom.Element)
	 */
	@Override
	public void parse(Element node) throws Exception {
		key = node.getAttribute(XMLUtils._PARAM_ATTR_NAME_);
		value = node.getAttribute(XMLUtils._PARAM_ATTR_VALUE_);
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Get the value parsed as an Integer.
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getIntValue() throws Exception {
		return Integer.parseInt(value);
	}

	/**
	 * Get the value parsed as an Integer. If null/empty value or parse failed,
	 * return default value passed.
	 * 
	 * @param defval
	 *            - default value.
	 * @return
	 */
	public int getIntValue(int defval) {
		if (value != null && !value.isEmpty()) {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				// Do nothing...
			}
		}
		return defval;
	}

	/**
	 * Get the value parsed as an Long.
	 * 
	 * @return
	 * @throws Exception
	 */
	public long getLongValue() throws Exception {
		return Long.parseLong(value);
	}

	/**
	 * Get the value parsed as an Long. If null/empty value or parse failed,
	 * return default value passed.
	 * 
	 * @param defval
	 *            - default value.
	 * @return
	 */
	public long getLongValue(long defval) {
		if (value != null && !value.isEmpty()) {
			try {
				return Long.parseLong(value);
			} catch (Exception e) {
				// Do nothing...
			}
		}
		return defval;
	}

	/**
	 * Get the value parsed as an Double.
	 * 
	 * @return
	 * @throws Exception
	 */
	public double getDoubleValue() throws Exception {
		return Double.parseDouble(value);
	}

	/**
	 * Get the value parsed as an Double. If null/empty value or parse failed,
	 * return default value passed.
	 * 
	 * @param defval
	 *            - default value.
	 * @return
	 */
	public double getDoubleValue(double defval) {
		if (value != null && !value.isEmpty()) {
			try {
				return Double.parseDouble(value);
			} catch (Exception e) {
				// Do nothing...
			}
		}
		return defval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{ " + key + " -> " + value + "}\n";
	}

}
