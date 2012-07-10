/**
 * 
 */
package com.wookler.core.utils;


import com.wookler.core.persistence.EnumEntityState;

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
		try {
			boolean retval = containsObjectArray(array, new Integer(3));
			System.out.println("Found : " + retval);
			retval = compare(EnumEntityState.New, "newx");
			System.out.println("Enum Compare : " + retval);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static <T extends Enum<T>> boolean compare(Object src, String value)
			throws Exception {
		String name = ((T) src).name();
		if (name.compareToIgnoreCase(value) == 0)
			return true;
		return false;
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
