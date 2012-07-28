/**
 * 
 */
package com.wookler.core.persistence;

import java.util.List;

/**
 * Utility class to import data from one source to another.
 * 
 * @author subhagho
 * 
 */
public class DataImport {
	private AbstractPersister source;

	/**
	 * Create an instance of the DataImport class with the specified source.
	 * 
	 * @param source
	 *            - Data import source.
	 */
	public DataImport(AbstractPersister source) {
		this.source = source;
	}

	/**
	 * Import the data from the source to the service database.
	 * 
	 * @param entities
	 *            - Class name(s) of entities to load.
	 * @throws Exception
	 */
	public void load(String[] entities) throws Exception {
		for (String entity : entities) {
			Class<?> cls = Class.forName(entity);
			List<AbstractEntity> data = source.read("", cls);
			if (data != null && data.size() > 0) {
				AbstractPersister dest = DataManager.get().getPersister(cls);
				for (AbstractEntity en : data) {
					en.setState(EnumEntityState.Overwrite);
					dest.save(en);
				}
			}
		}
	}
}
