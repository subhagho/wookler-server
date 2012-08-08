/**
 * 
 */
package com.wookler.core.persistence.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sqewd.open.dal.api.persistence.AbstractEntity;
import com.sqewd.open.dal.api.persistence.EnumEntityState;
import com.sqewd.open.dal.api.utils.LogUtils;
import com.sqewd.open.dal.core.Env;
import com.sqewd.open.dal.core.persistence.DataManager;
import com.wookler.core.test.Config;
import com.wookler.entities.media.EnumVideoSource;
import com.wookler.entities.media.VideoMedia;

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
		Env.create(Config._CONFIG_FILENAME_);
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

			AbstractEntity ae = DataManager.newInstance(VideoMedia.class);
			VideoMedia vm = (VideoMedia) ae;

			// vm.setId(uuid.toString());
			vm.setLength(new Date().getTime());
			vm.setName("TEST-MEDIA-VIDEO");
			vm.setPublished(new Date());
			vm.setRefid("TEST-MEDIA-VIDEO-REF");
			vm.setSource(EnumVideoSource.YouTube);
			vm.setTags(new String[] { "Video", "TV", "Episode 3" });
			vm.setDescription("This is a test video media, for delete");

			manager.save(vm);

			List<AbstractEntity> vmf = manager.read("NAME=TEST-MEDIA-VIDEO",
					VideoMedia.class);
			vm = (VideoMedia) vmf.get(0);
			String id = vm.getId();

			log.info("Found Video [" + vmf.get(0).toString() + "]");

			vm.setState(EnumEntityState.Deleted);

			manager.save(vm);

			vmf = manager.read("ID=" + id, VideoMedia.class);

			assertEquals(0, vmf.size());
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			fail(e.getLocalizedMessage());
		}
	}

}
