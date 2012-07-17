/**
 * 
 */
package com.wookler.core.persistence;

import java.lang.reflect.Method;

/**
 * @author subhagho
 * 
 */
public class AttributeReflection {
	public java.lang.reflect.Field Field;
	public String Column;
	public Method Getter;
	public Method Setter;
	public boolean IsKeyColumn = false;
	public CustomFieldDataHandler Convertor = null;
	public ReferenceReflection Reference = null;
}
