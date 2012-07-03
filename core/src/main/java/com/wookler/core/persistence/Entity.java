/**
 * 
 */
package com.wookler.core.persistence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation marking a POJO as a persisted entity.
 * 
 * @author subhagho
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
	/**
	 * Table/ColumnGroup/RecordSet name this entity is mapped to.
	 * 
	 * @return
	 */
	String recordset();

	/**
	 * Custom data persister to use for this entity. If no explicit persister is
	 * specified the DataManager will search for the persister.
	 * 
	 * @return
	 */
	Class<AbstractPersister> persister();
}
