package com.wookler.core.persistence.query;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class Test_FilterConditionParser {

	@Test
	public void testParse() {
		try {
			String[] conditions = { "VAR = 'VALUESTRING'", "VAR != 99999",
					"VAR <= 8347398", "VAR >= -110002", "VAR < 0.887282",
					"VAR > 098888", "VAR LIke '\\[(.*),(.*)\\]'",
					"VAR between ['1STR', '8STR'];VAR > '098888;0000'",
					"VAR CONTAINS 'STRINGKEY'" };
			FilterConditionParser parser = new FilterConditionParser();
			for (String cond : conditions) {
				List<FilterCondition> conds = parser.parse(cond);
				if (conds.size() > 0) {
					System.out.println("---------" + cond + "---------");
					for (FilterCondition fcond : conds)
						System.out.println("\tFILTER : " + fcond.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
