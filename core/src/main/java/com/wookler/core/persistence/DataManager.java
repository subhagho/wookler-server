/**
 * 
 */
package com.wookler.core.persistence;

import java.util.HashMap;

import org.apache.commons.configuration.XMLConfiguration;

import com.wookler.core.EnumInstanceState;

/**
 * @author subhagho
 * 
 */
public class DataManager {
	private EnumInstanceState state = EnumInstanceState.Unknown;

	private HashMap<String, AbstractPersister<?>> persisters = new HashMap<String, AbstractPersister<?>>();

	private void init(XMLConfiguration config) throws Exception {

	}

	/**
	 * Get the persistence handler defined for the specified entity type. If no
	 * persistence handler found for the current class, search thru the super
	 * classes to see if a handler is defined for any?
	 * 
	 * @param type
	 *            - Class of the Entity.
	 * @return
	 * @throws Exception
	 */
	public AbstractPersister<?> getPersister(Class<?> type) throws Exception {
		String key = type.getCanonicalName();
		if (persisters.containsKey(key)) {
			return persisters.get(key);
		}
		Class<?> ttype = type;
		while (true) {
			ttype = ttype.getSuperclass();
			if (ttype.getCanonicalName().compareTo(
					Object.class.getCanonicalName()) == 0)
				break;
			key = ttype.getCanonicalName();
			if (persisters.containsKey(key)) {
				return persisters.get(key);
			}
		}
		throw new Exception("No persistence handler found for class ["
				+ type.getCanonicalName() + "]");
	}

	// Instance
	private static DataManager _instance = new DataManager();

	public static void create(XMLConfiguration config) throws Exception {
		synchronized (_instance) {
			if (_instance.state == EnumInstanceState.Running)
				return;
			_instance.init(config);
		}
	}

	public static DataManager get() throws Exception {
		if (_instance.state != EnumInstanceState.Running) {
			throw new Exception(
					"Invalid Instance State : Instance not available [state="
							+ _instance.state.name() + "]");
		}
		return _instance;
	}
}
