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
		check(array);
		check(list);
	}

	public static void check(Object obj) {
		if (obj.getClass().isArray()) {
			int[] array = (int[]) obj;
			for (Object aobj : array) {
				int value = (Integer) aobj;
				System.out.println("Value : " + value);
			}
			System.out.println("Is an array.");
		} else if (obj instanceof Iterable<?>) {
			System.out.println("Is an Iterable.");
		}
	}
}
