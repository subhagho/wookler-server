/**
 * 
 */
package com.wookler.core.utils;

import java.util.ArrayList;
import java.util.List;

import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.EnumEntityState;
import com.wookler.entities.Creative;

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
		List<String> strings = new ArrayList<String>();
		List<Creative> creatives = new ArrayList<Creative>();
		
		try {
			boolean retval = containsObjectArray(array, new Integer(3));
			System.out.println("Found : " + retval);
			retval = compare(EnumEntityState.New, "newx");
			System.out.println("Enum Compare : " + retval);
			Object src = strings;
			if (src instanceof Iterable) {
				if (isEntityType(src))
					System.out.println("Invalid...");
			}
			src = creatives;
			if (src instanceof Iterable) {
				if (isEntityType(src))
					System.out.println("Invalid...");
			}
			Creative cr = new Creative();
			cr.setHtml("<html></html>");
			cr.setId(99999);
			creatives.add(cr);
			if (src instanceof Iterable) {
				if (isEntityType(src))
					System.out.println("Valid...");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> boolean isEntityType(Object src) {
		Iterable<T> entities = (Iterable<T>) src;
		if (entities.iterator().hasNext()) {
			T value = entities.iterator().next();
			if (value instanceof AbstractEntity) {
				return true;
			}
		}
		return false;
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
