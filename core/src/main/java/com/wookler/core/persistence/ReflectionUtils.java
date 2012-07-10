/**
 * 
 */
package com.wookler.core.persistence;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author subhagho
 * 
 */
public class ReflectionUtils {
	private HashMap<String, HashMap<String, AttributeReflection>> metacache = new HashMap<String, HashMap<String, AttributeReflection>>();

	/**
	 * Get the Getter/Setter method name for the field.
	 * 
	 * @param prefix
	 *            - "get"/"set"
	 * @param field
	 *            - Field name
	 * @return
	 */
	public String getMethodName(String prefix, String field) {
		return prefix + field.toUpperCase().charAt(0) + field.substring(1);
	}

	/**
	 * Get the reflection definition of the class column.
	 * 
	 * @param type
	 *            - Class type
	 * @param column
	 *            - Column name : Column name refers to the annotated name and
	 *            not the field name.
	 * @return
	 * @throws Exception
	 */
	public AttributeReflection getAttribute(Class<?> type, String column)
			throws Exception {
		HashMap<String, AttributeReflection> map = getEntityMetadata(type);
		if (map.containsKey(column)) {
			return map.get(column);
		}
		throw new Exception("No attributes found for entity ["
				+ type.getCanonicalName() + "] named [" + column + "]");
	}

	/**
	 * Get/Load the type metadata.
	 * 
	 * @param type
	 *            - Class type
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, AttributeReflection> getEntityMetadata(Class<?> type)
			throws Exception {
		if (!metacache.containsKey(type.getCanonicalName())) {
			synchronized (metacache) {

				HashMap<String, AttributeReflection> map = new HashMap<String, AttributeReflection>();

				Field[] fields = type.getDeclaredFields();
				if (fields != null && fields.length > 0) {
					for (Field fd : fields) {
						if (!fd.isAnnotationPresent(Attribute.class))
							continue;
						Attribute attr = (Attribute) fd
								.getAnnotation(Attribute.class);
						AttributeReflection ar = new AttributeReflection();
						ar.Field = fd;
						ar.Column = attr.name();

						String mname = getMethodName("get", fd.getName());
						ar.Getter = type.getMethod(mname);
						mname = getMethodName("set", fd.getName());
						ar.Setter = type.getMethod(mname, fd.getType());

						if (fd.isAnnotationPresent(Reference.class)) {
							Reference ref = (Reference) fd
									.getAnnotation(Reference.class);
							ar.Reference = new ReferenceReflection();
							ar.Reference.Class = ref.target();
							ar.Reference.Field = ref.attribute();
							ar.Reference.Type = ref.association();
						}
						map.put(ar.Column, ar);
					}
				}
				metacache.put(type.getCanonicalName(), map);
			}
		}
		return metacache.get(type.getCanonicalName());
	}

	
	private static ReflectionUtils _instance = new ReflectionUtils();

	/**
	 * Get a handle to the Reflections Utility class.
	 * 
	 * @return
	 */
	public static ReflectionUtils get() {
		return _instance;
	}
}
