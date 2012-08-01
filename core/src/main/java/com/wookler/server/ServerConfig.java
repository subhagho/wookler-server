/**
 * 
 */
package com.wookler.server;

import org.apache.commons.configuration.XMLConfiguration;

import com.wookler.core.Env;

/**
 * @author subhagho
 * 
 */
public class ServerConfig {
	public static final String _EMPTY_PATH_ELEMENT_ = "-";

	public static final String _SERVER_CONFIG_KEY_ = "SERVER-CONFIG";

	public static final String _CONFIG_SERVER_WEBROOT_ = "server.web[@directory]";
	public static final String _CONFIG_SERVER_PORT_ = "server[@port]";
	public static final String _CONFIG_SERVER_NTHREADS_ = "server[@numthreads]";

	private int port = 8080;
	private String webRoot;
	private int numThreads = 10;

	/**
	 * Initialize the Server Configuration.
	 * 
	 * @param config
	 *            - XML Configuration.
	 * 
	 * @throws Exception
	 */
	public void init(XMLConfiguration config) throws Exception {
		String value = config.getString(_CONFIG_SERVER_PORT_);
		if (value != null && !value.isEmpty())
			port = Integer.parseInt(value);

		value = config.getString(_CONFIG_SERVER_NTHREADS_);
		if (value != null && !value.isEmpty())
			numThreads = Integer.parseInt(value);

		value = config.getString(_CONFIG_SERVER_WEBROOT_);
		if (value != null && !value.isEmpty())
			webRoot = value;
		else
			webRoot = Env.get().getWorkdir();
		Env.get().register(_SERVER_CONFIG_KEY_, this, true);
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the webRoot
	 */
	public String getWebRoot() {
		return webRoot;
	}

	/**
	 * @param webRoot
	 *            the webRoot to set
	 */
	public void setWebRoot(String webRoot) {
		this.webRoot = webRoot;
	}

	/**
	 * @return the numThreads
	 */
	public int getNumThreads() {
		return numThreads;
	}

	/**
	 * @param numThreads
	 *            the numThreads to set
	 */
	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

}
