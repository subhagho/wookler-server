/**
 * 
 */
package com.wookler.test;

import com.sqewd.open.dal.core.persistence.query.SQLQuery;
import com.wookler.entities.media.ProductView;

/**
 * @author subhagho
 * 
 */
public class AdHocTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SQLQuery sq = new SQLQuery(ProductView.class);
			String sql = sq.parse("", -1);
			System.out.println("[" + sql + "]");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
