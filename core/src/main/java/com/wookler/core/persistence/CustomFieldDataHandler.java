/**
 * 
 */
package com.wookler.core.persistence;

/**
 * Interface to be implemented for setting/getting custom data elements for an
 * Entity.
 * 
 * @author subhagho
 * 
 */
public interface CustomFieldDataHandler {
	/**
	 * Load a field value from the data record.
	 * 
	 * @param entity
	 *            - Entity Instance
	 * @param field
	 *            - Field to load
	 * @param record
	 *            - Data record
	 * @param column
	 *            - Field Offset Index
	 * @throws Exception
	 */
	public void load(AbstractEntity entity, String field, Object[] record,
			int column) throws Exception;

	/**
	 * Save a field value into the Data record.
	 * 
	 * @param entity
	 *            - Entity Instance
	 * @param field
	 *            - Field to Save
	 * @param record
	 *            - Data record
	 * @param column
	 *            - Field Offset Index
	 * @throws Exception
	 */
	public void save(AbstractEntity entity, String field, Object[] record,
			int column) throws Exception;

}
