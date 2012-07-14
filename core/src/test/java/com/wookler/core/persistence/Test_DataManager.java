/**
 * 
 */
package com.wookler.core.persistence;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wookler.core.Env;
import com.wookler.core.data.Test_Env;
import com.wookler.entities.VideoMedia;
import com.wookler.utils.LogUtils;

/**
 * @author subhagho
 * 
 */
public class Test_DataManager {
	private static final Logger log = LoggerFactory
			.getLogger(Test_DataManager.class);

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Env.create(Test_Env.configfile);
		log.info("Environment initialzied...");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Env.dispose();
	}

	/**
	 * Test method for
	 * {@link com.wookler.core.persistence.DataManager#read(java.lang.String, java.lang.Class)}
	 * .
	 */
	@Test
	public void testRead() {
		try {
			DataManager manager = DataManager.get();
			List<AbstractEntity> entities = manager.read("", VideoMedia.class);
			assertEquals(true, (entities != null && entities.size() > 0));
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			fail(e.getLocalizedMessage());
		}
	}

}
