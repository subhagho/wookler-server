/**
 * 
 */
package com.wookler.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sqewd.open.dal.core.persistence.AttributeReflection;
import com.sqewd.open.dal.core.persistence.ReflectionUtils;


/**
 * @author subhagho
 * 
 */
@XmlRootElement(name = "Property")
@XmlAccessorType(XmlAccessType.NONE)
public class PropertyDef {
	@XmlElement(name = "Name")
	private String name;
	@XmlElement(name = "JavaType")
	private String type;
	@XmlElement(name = "JsonName")
	private String jsonname;
	@XmlElement(name = "DbColumn")
	private String dbcolumn;
	@XmlElement(name = "Size")
	private String dbsize;
	@XmlElement(name = "Enumeration")
	private String[] enumeration;

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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the jsonname
	 */
	public String getJsonname() {
		return jsonname;
	}

	/**
	 * @param jsonname
	 *            the jsonname to set
	 */
	public void setJsonname(String jsonname) {
		this.jsonname = jsonname;
	}

	/**
	 * @return the dbcolumn
	 */
	public String getDbcolumn() {
		return dbcolumn;
	}

	/**
	 * @param dbcolumn
	 *            the dbcolumn to set
	 */
	public void setDbcolumn(String dbcolumn) {
		this.dbcolumn = dbcolumn;
	}

	/**
	 * @return the dbsize
	 */
	public String getDbsize() {
		return dbsize;
	}

	/**
	 * @param dbsize
	 *            the dbsize to set
	 */
	public void setDbsize(String dbsize) {
		this.dbsize = dbsize;
	}

	public static PropertyDef load(Class<?> type, String field)
			throws Exception {
		AttributeReflection attr = ReflectionUtils.get().getAttribute(type,
				field);
		if (attr == null)
			return null;
		PropertyDef def = new PropertyDef();
		def.dbcolumn = attr.Column;
		def.name = attr.Column;
		def.type = attr.Field.getType().getCanonicalName();
		if (attr.Field.isAnnotationPresent(XmlElement.class)) {
			XmlElement xattr = attr.Field.getAnnotation(XmlElement.class);
			def.jsonname = xattr.name();
		}
		if (attr.Size > 0) {
			def.dbsize = "" + attr.Size;
		} else {
			def.dbsize = "-";
		}
		if (attr.Field.getType().isEnum()) {
			def.enumeration = getEnumValues(attr.Field.getType());
		}
		return def;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T extends Enum> String[] getEnumValues(Class<?> type)
			throws Exception {
		T[] enums = (T[]) type.getEnumConstants();
		if (enums != null && enums.length > 0) {
			String[] values = new String[enums.length];
			for (int ii = 0; ii < enums.length; ii++) {
				values[ii] = enums[ii].name();
			}
			return values;
		}
		return null;
	}

	/**
	 * @return the enumeration
	 */
	public String[] getEnumeration() {
		return enumeration;
	}

	/**
	 * @param enumeration
	 *            the enumeration to set
	 */
	public void setEnumeration(String[] enumeration) {
		this.enumeration = enumeration;
	}
}
