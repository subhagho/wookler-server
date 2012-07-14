/**
 * 
 */
package com.wookler.core;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.configuration.XMLConfiguration;
import org.w3c.dom.Document;

import com.wookler.core.persistence.DataManager;

/**
 * Initializes the System Environment. Shared handles should be registered with
 * the Env instance.
 * 
 * @author subhagho
 * 
 */
public class Env {
	public static final String _CONFIG_XPATH_ROOT_ = "/wookler";

	public static final String _CONFIG_DIR_WORK_ = "env.work[@directory]";
	public static final String _CONFIG_DIR_TEMP_ = "env.temp[@directory]";

	private XMLConfiguration config = null;
	private String configf = null;
	private String workdir = null;
	private String tempdir = null;
	private HashMap<String, Object> shared = new HashMap<String, Object>();

	private Env(String filename) throws Exception {
		configf = filename;
		config = new XMLConfiguration(configf);

		workdir = config.getString(_CONFIG_DIR_WORK_);
		if (workdir == null || workdir.isEmpty())
			throw new Exception("Invalid Configuration : Missing parameter ["
					+ _CONFIG_DIR_WORK_ + "]");
		tempdir = config.getString(_CONFIG_DIR_TEMP_);
		if (tempdir == null || tempdir.isEmpty())
			tempdir = System.getProperty("java.io.tmpdir");

		// Initialized the Data<anager
		DataManager.create(config);
	}

	/**
	 * @return the config
	 */
	public XMLConfiguration getConfig() {
		return config;
	}

	/**
	 * Get the configuration handle as a XML document.
	 * 
	 * @return
	 */
	public Document getConfigDom() {
		return config.getDocument();
	}

	/**
	 * @return the configf
	 */
	public String getConfigf() {
		return configf;
	}

	/**
	 * @return the workdir
	 */
	public String getWorkdir() {
		return workdir;
	}

	/**
	 * @return the tempdir
	 */
	public String getTempdir() {
		return tempdir;
	}

	/**
	 * Get/Create a directory specified by the sub-path under the temp
	 * directory.
	 * 
	 * @param folder
	 *            - Sub-Path
	 * @return
	 * @throws Exception
	 */
	public String getTempPath(String folder) throws Exception {
		String path = tempdir + "/" + folder;
		File di = new File(path);
		if (!di.exists()) {
			di.mkdirs();
		}
		return di.getAbsolutePath();
	}

	/**
	 * Get/Create a directory specified by the sub-path under the work
	 * directory.
	 * 
	 * @param folder
	 *            - Sub-Path
	 * @return
	 * @throws Exception
	 */
	public String getWorkPath(String folder) throws Exception {
		String path = workdir + "/" + folder;
		File di = new File(path);
		if (!di.exists()) {
			di.mkdirs();
		}
		return di.getAbsolutePath();
	}

	/**
	 * Register an Object instance to the shared cache.
	 * 
	 * @param key
	 *            - Search Key
	 * @param obj
	 *            - Object handle
	 * @param overwrite
	 *            - Overwrite if exists?
	 * @throws Exception
	 */
	public void register(String key, Object obj, boolean overwrite)
			throws Exception {
		if (shared.containsKey(key)) {
			if (overwrite) {
				shared.remove(key);
			} else {
				throw new Exception("Shared Cache : Key [" + key
						+ "] already exists, and overwrite set to false.");
			}
		}
		shared.put(key, obj);
	}

	/**
	 * Get an object handle from the shared cache.
	 * 
	 * @param key
	 *            - Search Key
	 * @return
	 */
	public Object lookup(String key) {
		if (shared.containsKey(key))
			return shared.get(key);
		return null;
	}

	// Instance
	private static Env _instance = null;
	private static Object _lock = new Object();

	/**
	 * Initialize the environment handle. Should only be called once, at the
	 * start of the application.
	 * 
	 * @param filename
	 *            - Configuration filename.
	 */
	public void create(String filename) {
		synchronized (_lock) {
			try {
				_instance = new Env(filename);
			} catch (Exception e) {
				e.printStackTrace();
				_instance = null;
			}
		}
	}

	/**
	 * Get a handle to the environment.
	 * 
	 * @return
	 * @throws Exception
	 */
	public Env get() throws Exception {
		synchronized (_lock) {
			if (_instance == null)
				throw new Exception(
						"Environment hasn't been initialized or initialization failed.");
			return _instance;
		}
	}

	/**
	 * Dispose the operating environment.
	 */
	public void dispose() {
		DataManager.dispose();
		if (_instance.shared != null) {
			_instance.shared.clear();
		}
		_instance.config = null;
	}
}
