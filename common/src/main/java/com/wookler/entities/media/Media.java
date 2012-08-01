/**
 * 
 */
package com.wookler.entities.media;

import java.util.Date;

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
@Entity(recordset = "MEDIA")
public abstract class Media extends AbstractEntity {
	@Attribute(name = "TYPE")
	@XmlElement(name = "type")
	protected EnumMediaType type;

	@Attribute(name = "ID", keyattribute = true, size = 256, autoincr = true)
	@XmlElement(name = "id")
	private String id;

	@Attribute(name = "REFID", size = 256)
	@XmlElement(name = "id")
	private String refid;

	@Attribute(name = "TAGS", handler = "com.wookler.core.persistence.handlers.StringArrayConvertor", size = 2048)
	@XmlElement(name = "tags")
	private String[] tags;

	@Attribute(name = "PUBLISHED")
	@XmlElement(name = "published")
	private Date published;

	@Attribute(name = "VIEWS")
	@XmlElement(name = "views")
	private long viewcount;

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
	 * @param type
	 */
	public void setType(EnumMediaType type) {
		this.type = type;
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

	/**
	 * @return the published
	 */
	public Date getPublished() {
		return published;
	}

	/**
	 * @param published
	 *            the published to set
	 */
	public void setPublished(Date published) {
		this.published = published;
	}

	/**
	 * @return the viewcount
	 */
	public long getViewcount() {
		return viewcount;
	}

	/**
	 * @param viewcount
	 *            the viewcount to set
	 */
	public void setViewcount(long viewcount) {
		this.viewcount = viewcount;
	}

	/**
	 * @return the refid
	 */
	public String getRefid() {
		return refid;
	}

	/**
	 * @param refid
	 *            the refid to set
	 */
	public void setRefid(String refid) {
		this.refid = refid;
	}
}
