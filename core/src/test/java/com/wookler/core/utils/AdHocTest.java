/**
 * 
 */
package com.wookler.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.wookler.core.persistence.AbstractPersister;

/**
 * @author subhagho
 * 
 */
public class AdHocTest {
	private static class Natives {
		private String string;
		private short varshort;
		private int varint;
		private long varlong;
		private float varfloat;
		private double vardouble;
		private boolean varbool;
		private byte varbyte;
		private char varchar;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuffer buff = new StringBuffer();
			buff.append("[STRING : ").append(string).append("]\n");
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

		/**
		 * @return the string
		 */
		public String getString() {
			return string;
		}

		/**
		 * @param string
		 *            the string to set
		 */
		public void setString(String string) {
			this.string = string;
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

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Natives nt = new Natives();

			Field[] fields = nt.getClass().getDeclaredFields();
			for (Field fd : fields) {
				Method mt = nt.getClass().getMethod(
						AbstractPersister.getMethodName("set", fd.getName()),
						new Class[] { fd.getType() });
				Class<?> type = fd.getType();
				if (type.equals(String.class)) {
					mt.invoke(nt, "This is a string...");
				} else if (type.equals(Short.class) || type.equals(short.class)) {
					mt.invoke(nt, Short.MAX_VALUE);
				} else if (type.equals(Integer.class) || type.equals(int.class)) {
					mt.invoke(nt, Integer.MAX_VALUE);
				} else if (type.equals(Long.class) || type.equals(long.class)) {
					mt.invoke(nt, Long.MAX_VALUE);
				} else if (type.equals(Float.class) || type.equals(float.class)) {
					mt.invoke(nt, Float.MAX_VALUE);
				} else if (type.equals(Double.class) || type.equals(double.class)) {
					mt.invoke(nt, Double.MAX_VALUE);
				}
			}

			System.out.println("VALUE:\n" + nt.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
