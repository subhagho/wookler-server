/**
 * 
 */
package com.wookler.core.persistence.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sqewd.open.dal.api.utils.ListParam;
import com.sqewd.open.dal.api.utils.LogUtils;
import com.sqewd.open.dal.api.utils.ValueParam;
import com.sqewd.open.dal.core.Env;
import com.sqewd.open.dal.core.persistence.DataImport;
import com.sqewd.open.dal.core.persistence.DataManager;
import com.sqewd.open.dal.core.persistence.csv.CSVPersister;
import com.wookler.core.test.Config;
import com.wookler.entities.users.Profile;

/**
 * @author subhagho
 * 
 */
public class Test_DataImport {
	private static final Logger log = LoggerFactory
			.getLogger(Test_DataImport.class);

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Env.create(Config._CONFIG_FILENAME_);
		DataManager.create(Env.get().getConfig());
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
	 * {@link com.wookler.core.persistence.DataImport#load(java.lang.String[])}.
	 */
	@Test
	public void testLoad() {
		try {
			CSVPersister source = new CSVPersister();

			// Setup CSV Persister
			ListParam params = new ListParam();

			ValueParam vp = new ValueParam();
			vp.setKey(CSVPersister._PARAM_KEY_);
			vp.setValue("CSVIMPORTSRC");
			params.add(vp);

			vp = new ValueParam();
			vp.setKey(CSVPersister._PARAM_DATADIR_);
			vp.setValue("/var/wookler/data/");
			params.add(vp);

			source.init(params);

			DataImport importer = new DataImport(source);
			/*
			 * importer.load(new String[] { VideoMedia.class.getCanonicalName(),
			 * Creative.class.getCanonicalName(),
			 * Sequence.class.getCanonicalName(), Tag.class.getCanonicalName(),
			 * ProductHistory.class.getCanonicalName() });
			 */
			importer.load(new String[] { "com.wookler.entities.users.Profile" });
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			fail(e.getLocalizedMessage());
		}
	}

}
/*
 * <persister name="CSVPERSISTER"
 * class="com.wookler.core.persistence.csv.CSVPersister"> <params> <param
 * type="Value" name="key" value="CSVPERSISTER" /> <param type="Value"
 * name="datadir" value="/var/wookler/data/" /> </params> </persister>
 */