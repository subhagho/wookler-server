/**
 * 
 */
package com.wookler.core.persistence;

/**
 * @author subhagho
 * 
 */
public class AttributeNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2363615347671567073L;

	/**
	 * 
	 */
	public AttributeNotFoundException() {
		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public AttributeNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public AttributeNotFoundException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public AttributeNotFoundException(Throwable arg0) {
		super(arg0);
	}

}
