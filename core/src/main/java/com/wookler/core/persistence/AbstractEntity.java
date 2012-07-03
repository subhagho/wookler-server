/**
 * 
 */
package com.wookler.core.persistence;

/**
 * Abstract class, to be inherited by all entities that are persisted.
 * 
 * @author subhagho
 * 
 */
public abstract class AbstractEntity {
	protected EnumEntityState state = EnumEntityState.Unknown;

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
	public abstract void save() throws Exception;

	/**
	 * Delete this instance of the Entity. This will delete the entity from the
	 * persistence store.
	 * 
	 * @throws Exception
	 */
	public abstract void delete() throws Exception;
}
