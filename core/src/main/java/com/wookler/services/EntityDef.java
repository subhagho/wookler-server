/**
 * 
 */
package com.wookler.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.sqewd.open.dal.api.persistence.AbstractPersister;
import com.sqewd.open.dal.api.persistence.Entity;
import com.sqewd.open.dal.api.persistence.ReflectionUtils;
import com.sqewd.open.dal.api.persistence.StructAttributeReflect;
import com.sqewd.open.dal.api.persistence.StructEntityReflect;
import com.sqewd.open.dal.core.persistence.DataManager;

/**
 * @author subhagho
 * 
 */
@XmlRootElement(name = "Entity")
@XmlAccessorType(XmlAccessType.NONE)
public class EntityDef {
	@XmlElement(name = "Name")
	private String name;
	@XmlElement(name = "Classname")
	private String classname;

	@XmlElement(name = "DataPersister")
	private String persister;

	@XmlElementWrapper(name = "Properties")
	private List<PropertyDef> properties;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the classname
	 */
	public String getClassname() {
		return classname;
	}

	/**
	 * @param classname
	 *            the classname to set
	 */
	public void setClassname(String classname) {
		this.classname = classname;
	}

	/**
	 * @return the properties
	 */
	public List<PropertyDef> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(List<PropertyDef> properties) {
		this.properties = properties;
	}

	/**
	 * @return the persister
	 */
	public String getPersister() {
		return persister;
	}

	/**
	 * @param persister
	 *            the persister to set
	 */
	public void setPersister(String persister) {
		this.persister = persister;
	}

	public static EntityDef load(Class<?> type) throws Exception {
		if (!type.isAnnotationPresent(Entity.class))
			throw new Exception("Class [" + type.getCanonicalName()
					+ "] has not been annotated as an Entity.");

		EntityDef entity = new EntityDef();
		StructEntityReflect enref = ReflectionUtils.get().getEntityMetadata(
				type);

		entity.name = enref.Entity;
		AbstractPersister pers = DataManager.get().getPersister(type);
		entity.persister = pers.getClass().getCanonicalName();
		entity.classname = type.getCanonicalName();

		entity.properties = new ArrayList<PropertyDef>();

		for (StructAttributeReflect attr : enref.Attributes) {
			PropertyDef pdef = PropertyDef.load(type, attr.Field.getName());
			if (pdef != null)
				entity.properties.add(pdef);
		}
		return entity;
	}

}
