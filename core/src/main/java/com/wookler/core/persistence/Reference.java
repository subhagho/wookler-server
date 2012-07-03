/**
 * 
 */
package com.wookler.core.persistence;

/**
 * @author subhagho
 * 
 */
public @interface Reference {
	/**
	 * Target entity the attribute points to.
	 * 
	 * @return
	 */
	Class<AbstractEntity> target();

	/**
	 * Target entity attribute this field refers to.
	 * 
	 * @return
	 */
	String attribute();
}
