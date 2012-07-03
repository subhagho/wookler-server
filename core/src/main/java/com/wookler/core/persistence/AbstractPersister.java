/**
 * 
 */
package com.wookler.core.persistence;

import java.util.List;

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
public abstract class AbstractPersister implements
		InitializedHandle {
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
	 * @return
	 * @throws Exception
	 */
	public abstract List<AbstractEntity> read(List<KeyValuePair<String>> columnkeys)
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
