/**
 * 
 */
package com.wookler.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author subhagho
 * 
 */
public class AdHocTests {
	private static class ListClass {
		private List<String> strings = null;

		private ArrayList<Double> doubles = null;

		/**
		 * @return the strings
		 */
		public List<String> getStrings() {
			return strings;
		}

		/**
		 * @param strings
		 *            the strings to set
		 */
		public void setStrings(List<String> strings) {
			this.strings = strings;
		}

		/**
		 * @return the astrings
		 */
		public ArrayList<Double> getDoubles() {
			return doubles;
		}

		/**
		 * @param astrings
		 *            the astrings to set
		 */
		public void setDoubles(ArrayList<Double> astrings) {
			this.doubles = astrings;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuffer buff = new StringBuffer();
			if (strings != null && strings.size() > 0) {
				buff.append("[LIST {");
				for (String st : strings) {
					buff.append(" ").append(st);
				}
				buff.append("}]\n");
			}
			if (doubles != null && doubles.size() > 0) {
				buff.append("[ARRAYLIST {");
				for (double db : doubles) {
					buff.append(" ").append(db);
				}
				buff.append("}]\n");
			}
			return buff.toString();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			Object obj = new ListClass();
			Field lfd = obj.getClass().getDeclaredField("strings");
			Field afd = obj.getClass().getDeclaredField("doubles");

			Type aty = afd.getGenericType();
			System.out.println("Type : " + aty);
			if (aty instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) aty;
				System.out.println("raw type: " + pt.getRawType());
				System.out.println("owner type: " + pt.getOwnerType());
				System.out.println("actual type args:");
				for (Type t : pt.getActualTypeArguments()) {
					System.out.println("    " + t);
				}
			}
			
			Type lty = lfd.getGenericType();
			System.out.println("Type : " + lty);
			if (lty instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) lty;
				System.out.println("raw type: " + pt.getRawType());
				System.out.println("owner type: " + pt.getOwnerType());
				System.out.println("actual type args:");
				for (Type t : pt.getActualTypeArguments()) {
					System.out.println("    " + t);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
