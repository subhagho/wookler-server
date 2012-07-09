/**
 * 
 */
package com.wookler.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author subhagho
 * 
 */
public class AdHocTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] array = { 1, 2, 3, 4 };
		List<Integer> list = new ArrayList<Integer>();
		try {
			boolean retval = containsObjectArray(array, new Integer(3));
			System.out.println("Found : " + retval);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> boolean containsObjectArray(Object src, Object tgt)
			throws Exception {
		T[] array = (T[]) src;
		for (T val : array) {
			if (val.equals((T) tgt))
				return true;
		}
		return false;
	}
}
