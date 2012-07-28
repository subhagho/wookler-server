/**
 * 
 */
package com.wookler.core.persistence;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wookler.core.Env;
import com.wookler.core.data.Test_Env;
import com.wookler.core.persistence.csv.CSVPersister;
import com.wookler.entities.Creative;
import com.wookler.entities.ProductHistory;
import com.wookler.entities.Sequence;
import com.wookler.entities.Tag;
import com.wookler.entities.VideoMedia;
import com.wookler.utils.ListParam;
import com.wookler.utils.LogUtils;
import com.wookler.utils.ValueParam;

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
			importer.load(new String[] { VideoMedia.class.getCanonicalName(),
					Creative.class.getCanonicalName(),
					Sequence.class.getCanonicalName(),
					Tag.class.getCanonicalName(),
					ProductHistory.class.getCanonicalName() });
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