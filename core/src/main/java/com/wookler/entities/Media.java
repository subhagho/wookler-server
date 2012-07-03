/**
 * 
 */
package com.wookler.entities;

import java.util.Date;

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
	protected EnumMediaType type;

	@Attribute(name = "ID", keyattribute = true)
	private String id;

	@Attribute(name = "TIMESTAMP")
	private Date timestamp;

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
	 * @return
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
