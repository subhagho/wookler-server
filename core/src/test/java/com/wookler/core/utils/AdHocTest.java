/**
 * 
 */
package com.wookler.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;

import com.wookler.core.persistence.AbstractPersister;

/**
 * @author subhagho
 * 
 */
public class AdHocTest {
	public static class Natives {
		private String varstring;
		private short varshort;
		private int varint;
		private long varlong;
		private float varfloat;
		private double vardouble;
		private boolean varbool;
		private byte varbyte;
		private char varchar;

		/**
		 * @return the varstring
		 */
		public String getVarstring() {
			return varstring;
		}

		/**
		 * @param varstring
		 *            the varstring to set
		 */
		public void setVarstring(String varstring) {
			this.varstring = varstring;
		}

		/**
		 * @return the varshort
		 */
		public short getVarshort() {
			return varshort;
		}

		/**
		 * @param varshort
		 *            the varshort to set
		 */
		public void setVarshort(short varshort) {
			this.varshort = varshort;
		}

		/**
		 * @return the varint
		 */
		public int getVarint() {
			return varint;
		}

		/**
		 * @param varint
		 *            the varint to set
		 */
		public void setVarint(int varint) {
			this.varint = varint;
		}

		/**
		 * @return the varlong
		 */
		public long getVarlong() {
			return varlong;
		}

		/**
		 * @param varlong
		 *            the varlong to set
		 */
		public void setVarlong(long varlong) {
			this.varlong = varlong;
		}

		/**
		 * @return the varfloat
		 */
		public float getVarfloat() {
			return varfloat;
		}

		/**
		 * @param varfloat
		 *            the varfloat to set
		 */
		public void setVarfloat(float varfloat) {
			this.varfloat = varfloat;
		}

		/**
		 * @return the vardouble
		 */
		public double getVardouble() {
			return vardouble;
		}

		/**
		 * @param vardouble
		 *            the vardouble to set
		 */
		public void setVardouble(double vardouble) {
			this.vardouble = vardouble;
		}

		/**
		 * @return the varbool
		 */
		public boolean isVarbool() {
			return varbool;
		}

		/**
		 * @param varbool
		 *            the varbool to set
		 */
		public void setVarbool(boolean varbool) {
			this.varbool = varbool;
		}

		/**
		 * @return the varbyte
		 */
		public byte getVarbyte() {
			return varbyte;
		}

		/**
		 * @param varbyte
		 *            the varbyte to set
		 */
		public void setVarbyte(byte varbyte) {
			this.varbyte = varbyte;
		}

		/**
		 * @return the varchar
		 */
		public char getVarchar() {
			return varchar;
		}

		/**
		 * @param varchar
		 *            the varchar to set
		 */
		public void setVarchar(char varchar) {
			this.varchar = varchar;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuffer buff = new StringBuffer();
			buff.append("[STRING : ").append(varstring).append("]\n");
			buff.append("[SHORT : ").append(varshort).append("]\n");
			buff.append("[INT : ").append(varint).append("]\n");
			buff.append("[LONG : ").append(varlong).append("]\n");
			buff.append("[FLOAT : ").append(varfloat).append("]\n");
			buff.append("[DOUBLE : ").append(vardouble).append("]\n");
			buff.append("[BOOL : ").append(varbool).append("]\n");
			buff.append("[BYTE : ").append(varbyte).append("]\n");
			buff.append("[CHAR : ").append(varchar).append("]\n");
			return buff.toString();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Natives nt = new Natives();

			Field[] fields = nt.getClass().getDeclaredFields();
			for (Field fd : fields) {
				Class<?> type = fd.getType();
				if (type.equals(String.class)) {
					PropertyUtils.setProperty(nt, fd.getName(),
							"This is a new String...");
				} else if (type.equals(Short.class) || type.equals(short.class)) {
					PropertyUtils
							.setProperty(nt, fd.getName(), Short.MAX_VALUE);
				} else if (type.equals(Integer.class) || type.equals(int.class)) {
					PropertyUtils.setProperty(nt, fd.getName(),
							Integer.MAX_VALUE);
				} else if (type.equals(Long.class) || type.equals(long.class)) {
					PropertyUtils.setProperty(nt, fd.getName(), Long.MAX_VALUE);
				} else if (type.equals(Float.class) || type.equals(float.class)) {
					PropertyUtils
							.setProperty(nt, fd.getName(), Float.MAX_VALUE);
				} else if (type.equals(Double.class)
						|| type.equals(double.class)) {
					PropertyUtils.setProperty(nt, fd.getName(),
							Double.MAX_VALUE);
				}
			}

			System.out.println("VALUE:\n" + nt.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
