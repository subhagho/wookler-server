package com.wookler.core.persistence.query;

import static org.junit.Assert.*;

import org.junit.Test;

public class Test_FilterConditionParser {

	@Test
	public void testParse() {
		try {
			String[] conditions = { "VAR = 'VALUESTRING'", "VAR != 99999",
					"VAR <= 8347398", "VAR >= -110002", "VAR < 0.887282",
					"VAR > 098888", "VAR LIke '\\[(.*),(.*)\\]'",
					"VAR between ['1STR', '8STR']", "VAR CONTAINS 'STRINGKEY'" };
			FilterConditionParser parser = new FilterConditionParser();
			for (String cond : conditions) {
				FilterCondition fcond = parser.parse(cond);
				System.out.println("FILTER : " + fcond.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

}
