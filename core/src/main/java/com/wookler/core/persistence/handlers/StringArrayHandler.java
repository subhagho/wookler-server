/**
 * 
 */
package com.wookler.core.persistence.handlers;

import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;

import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.AttributeReflection;
import com.wookler.core.persistence.CustomFieldDataHandler;
import com.wookler.core.persistence.ReflectionUtils;

/**
 * @author subhagho
 * 
 */
public class StringArrayHandler implements CustomFieldDataHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wookler.core.persistence.CustomFieldDataHandler#load(com.wookler.
	 * core.persistence.AbstractEntity, java.lang.String, java.lang.Object)
	 */
	public void load(AbstractEntity entity, String field, Object data)
			throws Exception {
		AttributeReflection attr = ReflectionUtils.get().getAttribute(
				entity.getClass(), field);
		if (attr.Field.getType().isArray()) {
			String sdata = (String) data;
			String[] spdata = sdata.split(";");
			PropertyUtils.setSimpleProperty(entity, attr.Field.getName(),
					spdata);
		} else if (Collection.class.isAssignableFrom(attr.Field.getType())) {
			Class<?> cls = attr.Field.getType();
			Object obj = cls.newInstance();
			Collection<?> coll = (Collection<?>)obj;
			String sdata = (String) data;
			String[] spdata = sdata.split(";");
			
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wookler.core.persistence.CustomFieldDataHandler#save(com.wookler.
	 * core.persistence.AbstractEntity, java.lang.String, java.lang.Object)
	 */
	public void save(AbstractEntity entity, String field, Object data)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
