/**
 * 
 */
package com.wookler.core.persistence;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.wookler.core.InitializedHandle;
import com.wookler.utils.KeyValuePair;
import com.wookler.utils.ListParam;

/**
 * Abstract persistence handler. All handlers to persist entities should inherit
 * from this class.
 * 
 * @author subhagho
 * 
 * @param <T>
 *            - Entity Type(s).
 */
public abstract class AbstractPersister implements InitializedHandle {
	private HashMap<String, HashMap<String, AttributeReflection>> metacache = new HashMap<String, HashMap<String, AttributeReflection>>();

	protected EnumPersisterState state = EnumPersisterState.Unknown;

	protected String classtype;

	/**
	 * Get the key to be used to lookup this Persister in the cache. Usually the
	 * key should be the absolute classname of the type T.
	 * 
	 * @return
	 */
	public String key() {
		return classtype;
	}

	/**
	 * Get/Load the type metadata.
	 * 
	 * @param type
	 *            - Class type
	 * @return
	 * @throws Exception
	 */
	protected HashMap<String, AttributeReflection> getEntityMetadata(
			Class<AbstractEntity> type) throws Exception {
		if (!metacache.containsKey(type.getCanonicalName())) {
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
					ar.Getter = type.getMethod(mname, (Class<?>) null);
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
		return metacache.get(type.getCanonicalName());
	}

	/**
	 * Set the Field value to the passed object. The base method only supports
	 * primitive types.
	 * 
	 * @param entity
	 *            - Target Object
	 * @param fd
	 *            - Field to set
	 * @param value
	 *            - Value Object
	 * @throws Exception
	 */
	protected void setFieldValue(AbstractEntity entity, Field fd, Object value)
			throws Exception {
		if (fd.getType().isPrimitive()) {
			PropertyUtils.setProperty(entity, fd.getName(), value);
		} else
			throw new Exception(
					"Non-primitive type support not implemented in the base method.");
	}

	/**
	 * Get the Getter/Setter method name for the field.
	 * 
	 * @param prefix
	 *            - "get"/"set"
	 * @param field
	 *            - Field name
	 * @return
	 */
	public static String getMethodName(String prefix, String field) {
		return prefix + field.toUpperCase().charAt(0) + field.substring(1);
	}

	/**
	 * Initialize the persistence handler.
	 * 
	 * @param params
	 *            - Initialization parameters.
	 * @throws Exception
	 */
	public abstract void init(ListParam params) throws Exception;

	/**
	 * Load a list of entity records based on the column keys specified.
	 * 
	 * @note Search keys are ANDED, no grouping operations are supported.
	 * @param columnkeys
	 *            - List of Column->Value to be used for searching.
	 * @param type
	 *            - Class type of the entity to search.
	 * @return
	 * @throws Exception
	 */
	public abstract List<AbstractEntity> read(
			List<KeyValuePair<String>> columnkeys, Class<AbstractEntity> type)
			throws Exception;

	/**
	 * Persist the specified entity record.
	 * 
	 * @param record
	 *            - Entity record instance.
	 * @throws Exception
	 */
	public abstract void save(AbstractEntity record) throws Exception;

	/**
	 * Bulk save a list of entity records.
	 * 
	 * @param records
	 *            - List of entity records.
	 * @throws Exception
	 */
	public abstract void save(List<AbstractEntity> records) throws Exception;

	/**
	 * Delete the specified entity record.
	 * 
	 * @param record
	 *            - Entity record.
	 * @throws Exception
	 */
	public abstract void delete(AbstractEntity record) throws Exception;
}
