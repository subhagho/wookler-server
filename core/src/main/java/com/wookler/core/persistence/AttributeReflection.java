/**
 * 
 */
package com.wookler.core.persistence;

import java.lang.reflect.Method;

/**
 * Structure encapsulates the Entity Attribute parameters extracted from the
 * annotation using reflection.
 * 
 * @author subhagho
 * 
 */
public class AttributeReflection {
	public java.lang.reflect.Field Field;
	public String Column;
	public Method Getter;
	public Method Setter;
	public boolean IsKeyColumn = false;
	public boolean AutoIncrement = false;
	public int Size;
	public CustomFieldDataHandler Convertor = null;
	public ReferenceReflection Reference = null;
}
