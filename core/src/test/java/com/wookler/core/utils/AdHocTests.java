/**
 * 
 */
package com.wookler.core.utils;

import java.lang.reflect.Field;

/**
 * @author subhagho
 * 
 */
public class AdHocTests {
	static enum TestEnum {
		One, Two, Three
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestEnum tenum = TestEnum.Two;
		String value = "Three";

		try {
			Object obj = tenum;
			if (obj.getClass().isEnum()) {
				Class<?> type = obj.getClass();
				tenum = (TestEnum) getEnum(type, value);
				System.out.println("Enum value : " + tenum.name());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Object getEnum(Class type, String value) throws Exception {
		return Enum.valueOf(type, value);
	}
}
