/**
 * 
 */
package com.wookler.core.persistence;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/**
 * Abstract class, to be inherited by all entities that are persisted.
 * 
 * @author subhagho
 * 
 */
public abstract class AbstractEntity {
	protected EnumEntityState state = EnumEntityState.Unknown;

	@Attribute(name = "TIMESTAMP", handler = "com.wookler.core.persistence.handlers.DateHandler")
	@XmlElement(name = "timestamp")
	protected Date timestamp;

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

	/**
	 * Indicates that the value of an attribute of this entity has been updated.
	 * Method sets the state of the entity.
	 * 
	 * Should be invoked by all setters.
	 */
	protected void updated() {
		if (state == EnumEntityState.New)
			return;
		if (state == EnumEntityState.Loaded)
			state = EnumEntityState.Updated;
		if (state == EnumEntityState.Deleted)
			return;
	}

	/**
	 * Persist this instance of the Entity. Persistence handlers should be
	 * implemented by the inherited classes.
	 * 
	 * @throws Exception
	 */
	public void save() throws Exception {
		(DataManager.get().getPersister(getClass())).save(this);
	}

	/**
	 * Delete this instance of the Entity. This will delete the entity from the
	 * persistence store.
	 * 
	 * @throws Exception
	 */
	public void delete() throws Exception {
		(DataManager.get().getPersister(getClass())).save(this);
	}
}
