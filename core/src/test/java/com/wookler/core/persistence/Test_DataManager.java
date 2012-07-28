/**
 * 
 */
package com.wookler.core.persistence;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wookler.core.Env;
import com.wookler.core.data.Test_Env;
import com.wookler.entities.EnumVideoSource;
import com.wookler.entities.Sequence;
import com.wookler.entities.Tag;
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
			int count = 100;
			long stime = new Date().getTime();
			for (int ii = 0; ii < count; ii++) {
				int id = ii % 6;

				List<AbstractEntity> entities = manager.read(
						"TYPE = Video; ID = " + id
								+ "; SORT LENGTH ASC; LIMIT 2",
						VideoMedia.class);
				if (entities != null)
					log.debug("[ID=" + id + "] size = " + entities.size());

				// assertEquals(true, (entities != null && entities.size() >
				// 0));
				if (entities.size() > 0) {
					for (AbstractEntity entity : entities) {
						log.info(entity.toString());
						List<AbstractEntity> sequences = manager
								.read("MEDIAID="
										+ ((VideoMedia) entity).getId()
										+ "; CREATIVE.ID = 1; SORT STARTTIME ASC",
										Sequence.class);
						for (AbstractEntity seq : sequences) {
							log.debug("\tSEQUENCE : " + seq.toString());
						}
						List<AbstractEntity> tags = manager.read("MEDIAID="
								+ ((VideoMedia) entity).getId(), Tag.class);
						for (AbstractEntity tag : tags) {
							log.debug("\tTAG : " + tag.toString());
						}
					}
				}
			}
			long etime = new Date().getTime();
			log.info("Time to execute [" + count + "] iterations = "
					+ (etime - stime) / 1000 + " secs");

			AbstractEntity ae = DataManager.newInstance(VideoMedia.class);
			VideoMedia vm = (VideoMedia) ae;

			UUID uuid = UUID.randomUUID();

			vm.setId(uuid.toString());
			vm.setLength(new Date().getTime());
			vm.setName("TEST-MEDIA-VIDEO");
			vm.setPublished(new Date());
			vm.setRefid("TEST-MEDIA-VIDEO-REF");
			vm.setSource(EnumVideoSource.YouTube);
			vm.setTags(new String[] { "Video", "TV", "Episode 3" });
			vm.setDescription("This is a test video media, for delete");

			manager.save(vm);

			List<AbstractEntity> vmf = manager.read("ID=" + uuid.toString(),
					VideoMedia.class);
			log.info("Found Video [" + vmf.get(0).toString() + "]");

			vm.setState(EnumEntityState.Deleted);

			manager.save(vm);

			vmf = manager.read("ID=" + uuid.toString(), VideoMedia.class);

			assertEquals(0, vmf.size());
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			fail(e.getLocalizedMessage());
		}
	}

}
