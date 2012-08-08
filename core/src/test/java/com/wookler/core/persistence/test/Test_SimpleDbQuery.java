/**
 * 
 */
package com.wookler.core.persistence.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sqewd.open.dal.api.utils.KeyValuePair;
import com.sqewd.open.dal.api.utils.LogUtils;
import com.sqewd.open.dal.core.persistence.query.SimpleDbQuery;
import com.wookler.entities.media.Creative;
import com.wookler.entities.media.Sequence;
import com.wookler.entities.media.VideoMedia;

/**
 * @author subhagho
 * 
 */
public class Test_SimpleDbQuery {
	private static final Logger log = LoggerFactory
			.getLogger(Test_SimpleDbQuery.class);

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.wookler.core.persistence.query.SimpleDbQuery#getSelectQuery(java.lang.Class)}
	 * .
	 */
	@Test
	public void testQuery() {
		try {
			String query = "STR=3;REF.STR='xxx';REF.REF.DT=12900123020";
			SimpleDbQuery dbq = new SimpleDbQuery();
			
			List<String> ddls = dbq.getCreateTableDDL(VideoMedia.class);
			for (String ddl : ddls) {
				log.info("SQL[" + ddl + "]");
			}

			ddls = dbq.getCreateTableDDL(Sequence.class);
			for (String ddl : ddls) {
				log.info("SQL[" + ddl + "]");
			}

			List<KeyValuePair<String>> indx = new ArrayList<KeyValuePair<String>>();
			indx.add(new KeyValuePair<String>("TESTINDX", "MEDIAID,STARTTIME"));
			ddls = dbq.getCreateIndexDDL(Sequence.class, indx);
			for (String ddl : ddls) {
				log.info("SQL[" + ddl + "]");
			}

			ddls = dbq.getCreateTableDDL(Creative.class);
			for (String ddl : ddls) {
				log.info("SQL[" + ddl + "]");
			}

			String update = dbq.getUpdateQuery(Sequence.class);
			log.info("[UPDATE][" + update + "]");

			String insert = dbq.getInsertQuery(Sequence.class);
			log.info("[INSERT][" + insert + "]");

		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.wookler.core.persistence.query.SimpleDbQuery#getSelectQuery(java.lang.Class)}
	 * .
	 */
	@Test
	public void testPerfQuery() {
		try {
			while (true) {
				long stime = new Date().getTime();
				int count = 10;

				for (int ii = 0; ii < count; ii++) {
					String query = "STR=3;REF.STR='xxx';REF.REF.DT=12900123020";
					SimpleDbQuery dbq = new SimpleDbQuery();
					
					/*
					 * List<String> ddls =
					 * dbq.getCreateTableDDL(VideoMedia.class);
					 * 
					 * ddls = dbq.getCreateTableDDL(Sequence.class);
					 * 
					 * List<KeyValuePair<String>> indx = new
					 * ArrayList<KeyValuePair<String>>(); indx.add(new
					 * KeyValuePair<String>("TESTINDX", "MEDIAID,STARTTIME"));
					 * ddls = dbq.getCreateIndexDDL(Sequence.class, indx);
					 * 
					 * ddls = dbq.getCreateTableDDL(Creative.class);
					 */
					String update = dbq.getUpdateQuery(Sequence.class);

					String insert = dbq.getInsertQuery(Sequence.class);

				}

				log.info("Time to process [" + count + "] loops : "
						+ (new Date().getTime() - stime));
				break;
				// Thread.sleep(500);
			}
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			fail(e.getLocalizedMessage());
		}
	}
}
