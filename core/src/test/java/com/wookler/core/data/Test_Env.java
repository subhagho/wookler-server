/**
 * 
 */
package com.wookler.core.data;

import static org.junit.Assert.*;

import java.io.File;

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

	public static final String configfile = "/Work/projects/wookler-server/core/target/test-classes/wookler-server.xml";

	/**
	 * Test method for {@link com.wookler.core.Env#get()}.
	 */
	@Test
	public void testGet() {
		try {
			log.info("Current directory [" + new File(".").getAbsolutePath()
					+ "]");
			Env.create(configfile);
			log.info("Environment initialzied...");
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			fail(e.getLocalizedMessage());
		}
	}

}
