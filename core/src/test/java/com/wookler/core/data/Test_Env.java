/**
 * 
 */
package com.wookler.core.data;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wookler.core.Env;
import com.wookler.utils.LogUtils;

/**
 * @author subhagho
 * 
 */
public class Test_Env {
	private static final Logger log = LoggerFactory.getLogger(Test_Env.class);

	private static final String configfile = "/Work/projects/wookler-server/core/src/main/java/com/wookler/core/config/wookler-server.xml";

	/**
	 * Test method for {@link com.wookler.core.Env#get()}.
	 */
	@Test
	public void testGet() {
		try {
			Env.create(configfile);
			log.info("Environment initialzied...");
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			fail(e.getLocalizedMessage());
		}
	}

}
