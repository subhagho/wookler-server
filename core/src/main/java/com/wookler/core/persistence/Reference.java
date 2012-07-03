/**
 * 
 */
package com.wookler.core.persistence;

/**
 * Annotation marks referenced entities and association attribute.
 * 
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

	/**
	 * Specify the association with the target entity.
	 * 
	 * @return
	 */
	EnumRefereceType association() default EnumRefereceType.One2One;

	/**
	 * Lazy Load referenced entity.
	 * 
	 * @return
	 */
	boolean lazyload() default false;
}
