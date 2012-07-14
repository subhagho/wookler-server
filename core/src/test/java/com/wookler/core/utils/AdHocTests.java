/**
 * 
 */
package com.wookler.core.utils;

import java.util.ArrayList;
import java.util.Collection;
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
			if (Collection.class.isAssignableFrom(strings.getClass())) {
				System.out.println("Is collection...");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
