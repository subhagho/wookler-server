/**
 * 
 */
package com.wookler.core.persistence;

/**
 * Annotation marks fields to be persisted for an Entity. Fields should be
 * exposed via getters/setters (method name format getXxxxx()/setXxxxx().
 * 
 * @author subhagho
 * 
 */
public @interface Attribute {
	/**
	 * Column name this entity attribute maps to.
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * Is this attribute a key/key part.
	 * 
	 * @return
	 */
	boolean keyattribute() default false;

}
