/**
 * 
 */
package com.wookler.core.persistence.query;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.wookler.core.persistence.query.DummyEntitiesData.EntityMatchRoot;

/**
 * @author subhagho
 * 
 */
public class Test_SimpleFilterQuery {

	@Test
	public void testDoSelect() {
		try {
			String qstring = "FORReference.FORReference.FORint=99999;FORDate > "
					+ new Date(0).getTime();
			EntityMatchRoot entity = new EntityMatchRoot();
			SimpleFilterQuery query = new SimpleFilterQuery();
			query.parse(qstring);
			boolean retval = query.doSelect(entity);
			assertEquals(true, retval);

			qstring = "FORReference.FORReference.FORint=987666;FORDate > "
					+ new Date(0).getTime();
			query.parse(qstring);
			retval = query.doSelect(entity);
			assertEquals(false, retval);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
