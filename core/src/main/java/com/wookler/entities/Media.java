/**
 * 
 */
package com.wookler.entities;

import javax.xml.bind.annotation.XmlElement;

import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.Attribute;
import com.wookler.core.persistence.Entity;

/**
 * Base entity for a Media Object.
 * 
 * @author subhagho
 * 
 */
@Entity
public abstract class Media extends AbstractEntity {
	@Attribute(name = "TYPE")
	@XmlElement(name = "type")
	protected EnumMediaType type;

	@Attribute(name = "ID", keyattribute = true)
	@XmlElement(name = "id")
	private String id;

	@Attribute(name = "TAGS", handler = "com.wookler.core.persistence.handlers.StringArrayHandler")
	@XmlElement(name = "tags")
	private String[] tags;

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get the media type.
	 * 
	 * @return
	 */
	public EnumMediaType getType() {
		return type;
	}

	/**
	 * @return the tags
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}
}
