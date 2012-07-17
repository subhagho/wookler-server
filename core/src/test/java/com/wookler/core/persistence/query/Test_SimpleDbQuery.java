/**
 * 
 */
package com.wookler.core.persistence.query;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wookler.entities.Sequence;
import com.wookler.entities.VideoMedia;
import com.wookler.utils.LogUtils;

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
	 * {@link com.wookler.core.persistence.query.SimpleDbQuery#parseSelectQuery(java.lang.Class)}
	 * .
	 */
	@Test
	public void testParseSelectQuery() {
		try {
			String query = "ID=3;TYPE='Video';TIMESTAMP BETWEEN ['1/1/2001;MM/dd/yyyy', '1/1/2010;MM/dd/yyyy']";
			SimpleDbQuery dbq = new SimpleDbQuery();
			dbq.parse(query);
			String sql = dbq.parseSelectQuery(VideoMedia.class);
			log.info("SQL[" + sql + "]");

			List<String> ddls = dbq.getCreateTableDDL(Sequence.class);
			for (String ddl : ddls) {
				log.info("SQL[" + ddl + "]");
			}
		} catch (Exception e) {
			LogUtils.stacktrace(log, e);
			fail(e.getLocalizedMessage());
		}
	}

}
