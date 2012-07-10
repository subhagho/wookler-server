/**
 * 
 */
package com.wookler.core.persistence.query;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.wookler.core.persistence.AbstractEntity;
import com.wookler.core.persistence.Attribute;
import com.wookler.core.persistence.Entity;

/**
 * @author subhagho
 * 
 */
public class Test_ConditionMatcher {
	@Entity(recordset = "EMBED2")
	public static class EntityMatchEmbed2 extends AbstractEntity {
		@Attribute(name = "FORint")
		private int forint = -99999;

		/**
		 * @return the forint
		 */
		public int getForint() {
			return forint;
		}

		/**
		 * @param forint
		 *            the forint to set
		 */
		public void setForint(int forint) {
			this.forint = forint;
		}

	}

	@Entity(recordset = "EMBED")
	public static class EntityMatchEmbed extends AbstractEntity {
		@Attribute(name = "FORshort")
		private short forshort = Short.MIN_VALUE;
		@Attribute(name = "FORReference")
		private EntityMatchEmbed2 forReference = new EntityMatchEmbed2();

		/**
		 * @return the forshort
		 */
		public short getForshort() {
			return forshort;
		}

		/**
		 * @param forshort
		 *            the forshort to set
		 */
		public void setForshort(short forshort) {
			this.forshort = forshort;
		}

		/**
		 * @return the forReference
		 */
		public EntityMatchEmbed2 getForReference() {
			return forReference;
		}

		/**
		 * @param forReference
		 *            the forReference to set
		 */
		public void setForReference(EntityMatchEmbed2 forReference) {
			this.forReference = forReference;
		}

	}

	@Entity(recordset = "ROOT")
	public static class EntityMatchRoot extends AbstractEntity {
		@Attribute(name = "FORshort")
		private short forshort = -1;
		@Attribute(name = "FORShort")
		private Short forShort = 1;
		@Attribute(name = "FORint")
		private int forint = -99999;
		@Attribute(name = "FORInt")
		private Integer forInt = 99999;
		@Attribute(name = "FORlong")
		private long forlong = -99999999;
		@Attribute(name = "FORLong")
		private Long forLong = 99999999L;
		@Attribute(name = "FORfloat")
		private float forfloat = -99.99f;
		@Attribute(name = "FORFloat")
		private Float forFloat = 99.99f;
		@Attribute(name = "FORdouble")
		private double fordouble = -9999.99;
		@Attribute(name = "FORDouble")
		private Double forDouble = 9999.99;
		@Attribute(name = "FORchar")
		private char forchar = 'A';
		@Attribute(name = "FORChar")
		private Character forChar = 'B';
		@Attribute(name = "FORString")
		private String forString = "77380-10632";
		@Attribute(name = "FORStringContains")
		private List<String> forStringContains = new ArrayList<String>();
		@Attribute(name = "FORIntContains")
		private Integer[] forIntConaints = { 1, 2, 3, 4, 5, 6, 7 };
		@Attribute(name = "FORDoubleContains")
		private double[] forDoubleContains = { 99.09, 99.01, 99.02 };
		@Attribute(name = "FORReference")
		private EntityMatchEmbed forReference = new EntityMatchEmbed();

		public EntityMatchRoot() {
			forStringContains.add("STRING_0");
			forStringContains.add("STRING_1");
			forStringContains.add("STRING_2");
		}

		/**
		 * @return the forshort
		 */
		public short getForshort() {
			return forshort;
		}

		/**
		 * @param forshort
		 *            the forshort to set
		 */
		public void setForshort(short forshort) {
			this.forshort = forshort;
		}

		/**
		 * @return the forShort
		 */
		public Short getForShort() {
			return forShort;
		}

		/**
		 * @param forShort
		 *            the forShort to set
		 */
		public void setForShort(Short forShort) {
			this.forShort = forShort;
		}

		/**
		 * @return the forint
		 */
		public int getForint() {
			return forint;
		}

		/**
		 * @param forint
		 *            the forint to set
		 */
		public void setForint(int forint) {
			this.forint = forint;
		}

		/**
		 * @return the forInt
		 */
		public Integer getForInt() {
			return forInt;
		}

		/**
		 * @param forInt
		 *            the forInt to set
		 */
		public void setForInt(Integer forInt) {
			this.forInt = forInt;
		}

		/**
		 * @return the forlong
		 */
		public long getForlong() {
			return forlong;
		}

		/**
		 * @param forlong
		 *            the forlong to set
		 */
		public void setForlong(long forlong) {
			this.forlong = forlong;
		}

		/**
		 * @return the forLong
		 */
		public Long getForLong() {
			return forLong;
		}

		/**
		 * @param forLong
		 *            the forLong to set
		 */
		public void setForLong(Long forLong) {
			this.forLong = forLong;
		}

		/**
		 * @return the forfloat
		 */
		public float getForfloat() {
			return forfloat;
		}

		/**
		 * @param forfloat
		 *            the forfloat to set
		 */
		public void setForfloat(float forfloat) {
			this.forfloat = forfloat;
		}

		/**
		 * @return the forFloat
		 */
		public Float getForFloat() {
			return forFloat;
		}

		/**
		 * @param forFloat
		 *            the forFloat to set
		 */
		public void setForFloat(Float forFloat) {
			this.forFloat = forFloat;
		}

		/**
		 * @return the fordouble
		 */
		public double getFordouble() {
			return fordouble;
		}

		/**
		 * @param fordouble
		 *            the fordouble to set
		 */
		public void setFordouble(double fordouble) {
			this.fordouble = fordouble;
		}

		/**
		 * @return the forDouble
		 */
		public Double getForDouble() {
			return forDouble;
		}

		/**
		 * @param forDouble
		 *            the forDouble to set
		 */
		public void setForDouble(Double forDouble) {
			this.forDouble = forDouble;
		}

		/**
		 * @return the forchar
		 */
		public char getForchar() {
			return forchar;
		}

		/**
		 * @param forchar
		 *            the forchar to set
		 */
		public void setForchar(char forchar) {
			this.forchar = forchar;
		}

		/**
		 * @return the forChar
		 */
		public Character getForChar() {
			return forChar;
		}

		/**
		 * @param forChar
		 *            the forChar to set
		 */
		public void setForChar(Character forChar) {
			this.forChar = forChar;
		}

		/**
		 * @return the forString
		 */
		public String getForString() {
			return forString;
		}

		/**
		 * @param forString
		 *            the forString to set
		 */
		public void setForString(String forString) {
			this.forString = forString;
		}

		/**
		 * @return the forStringContains
		 */
		public List<String> getForStringContains() {
			return forStringContains;
		}

		/**
		 * @param forStringContains
		 *            the forStringContains to set
		 */
		public void setForStringContains(List<String> forStringContains) {
			this.forStringContains = forStringContains;
		}

		/**
		 * @return the forIntConaints
		 */
		public Integer[] getForIntConaints() {
			return forIntConaints;
		}

		/**
		 * @param forIntConaints
		 *            the forIntConaints to set
		 */
		public void setForIntConaints(Integer[] forIntConaints) {
			this.forIntConaints = forIntConaints;
		}

		/**
		 * @return the forDoubleContains
		 */
		public double[] getForDoubleContains() {
			return forDoubleContains;
		}

		/**
		 * @param forDoubleContains
		 *            the forDoubleContains to set
		 */
		public void setForDoubleContains(double[] forDoubleContains) {
			this.forDoubleContains = forDoubleContains;
		}

		/**
		 * @return the forReference
		 */
		public EntityMatchEmbed getForReference() {
			return forReference;
		}

		/**
		 * @param forReference
		 *            the forReference to set
		 */
		public void setForReference(EntityMatchEmbed forReference) {
			this.forReference = forReference;
		}

	}

	/**
	 * Test method for
	 * {@link com.wookler.core.persistence.query.ConditionMatcher#match(com.wookler.core.persistence.AbstractEntity, java.lang.String, com.wookler.core.persistence.query.EnumOperator, java.lang.Object)}
	 * .
	 */
	@Test
	public void testMatchShort() {
		try {
			EntityMatchRoot entity = new EntityMatchRoot();
			ConditionMatcher matcher = new ConditionMatcher();
			boolean retval = false;

			retval = matcher.match(entity, "FORshort", EnumOperator.Equal, ""
					+ entity.getForshort());
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORshort", EnumOperator.NotEqual,
					"" + 0);
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORshort",
					EnumOperator.GreaterThan, "" + (entity.getForshort() - 1));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORshort",
					EnumOperator.GreaterThanEqual, ""
							+ (entity.getForshort() - 2));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORshort", EnumOperator.LessThan,
					"" + (entity.getForshort() + 1));
			assertEquals(true, retval);
			retval = matcher
					.match(entity, "FORshort", EnumOperator.LessThanEqual, ""
							+ (entity.getForshort() + 2));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORshort", EnumOperator.Between,
					new String[] { "" + (entity.getForshort() - 1),
							"" + (entity.getForshort() + 1) });
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORshort", EnumOperator.Between,
					new String[] { "" + (entity.getForshort() + 1),
							"" + (entity.getForshort() + 2) });
			assertEquals(false, retval);

			retval = matcher.match(entity, "FORShort", EnumOperator.Equal, ""
					+ entity.getForShort());
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORShort", EnumOperator.NotEqual,
					"" + 0);
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORShort",
					EnumOperator.GreaterThan, "" + (entity.getForShort() - 1));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORShort",
					EnumOperator.GreaterThanEqual, ""
							+ (entity.getForShort() - 2));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORShort", EnumOperator.LessThan,
					"" + (entity.getForShort() + 1));
			assertEquals(true, retval);
			retval = matcher
					.match(entity, "FORShort", EnumOperator.LessThanEqual, ""
							+ (entity.getForShort() + 2));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORShort", EnumOperator.Between,
					new String[] { "" + (entity.getForShort() - 1),
							"" + (entity.getForShort() + 1) });
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORShort", EnumOperator.Between,
					new String[] { "" + (entity.getForShort() + 1),
							"" + (entity.getForShort() + 2) });
			assertEquals(false, retval);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.wookler.core.persistence.query.ConditionMatcher#match(com.wookler.core.persistence.AbstractEntity, java.lang.String, com.wookler.core.persistence.query.EnumOperator, java.lang.Object)}
	 * .
	 */
	@Test
	public void testMatchInt() {
		try {
			EntityMatchRoot entity = new EntityMatchRoot();
			ConditionMatcher matcher = new ConditionMatcher();
			boolean retval = false;

			retval = matcher.match(entity, "FORint", EnumOperator.Equal, ""
					+ entity.getForint());
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORint", EnumOperator.NotEqual,
					"" + 0);
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORint", EnumOperator.GreaterThan,
					"" + (entity.getForint() - 1));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORint",
					EnumOperator.GreaterThanEqual, ""
							+ (entity.getForint() - 2));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORint", EnumOperator.LessThan, ""
					+ (entity.getForint() + 1));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORint",
					EnumOperator.LessThanEqual, "" + (entity.getForint() + 2));
			assertEquals(true, retval);
			retval = matcher.match(
					entity,
					"FORint",
					EnumOperator.Between,
					new String[] { "" + (entity.getForint() - 1),
							"" + (entity.getForint() + 1) });
			assertEquals(true, retval);
			retval = matcher.match(
					entity,
					"FORint",
					EnumOperator.Between,
					new String[] { "" + (entity.getForint() + 1),
							"" + (entity.getForint() + 2) });
			assertEquals(false, retval);

			retval = matcher.match(entity, "FORInt", EnumOperator.Equal, ""
					+ entity.getForInt());
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORInt", EnumOperator.NotEqual,
					"" + 0);
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORInt", EnumOperator.GreaterThan,
					"" + (entity.getForInt() - 1));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORInt",
					EnumOperator.GreaterThanEqual, ""
							+ (entity.getForInt() - 2));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORInt", EnumOperator.LessThan, ""
					+ (entity.getForInt() + 1));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORInt",
					EnumOperator.LessThanEqual, "" + (entity.getForInt() + 2));
			assertEquals(true, retval);
			retval = matcher.match(
					entity,
					"FORInt",
					EnumOperator.Between,
					new String[] { "" + (entity.getForInt() - 1),
							"" + (entity.getForInt() + 1) });
			assertEquals(true, retval);
			retval = matcher.match(
					entity,
					"FORInt",
					EnumOperator.Between,
					new String[] { "" + (entity.getForInt() + 1),
							"" + (entity.getForInt() + 2) });
			assertEquals(false, retval);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.wookler.core.persistence.query.ConditionMatcher#match(com.wookler.core.persistence.AbstractEntity, java.lang.String, com.wookler.core.persistence.query.EnumOperator, java.lang.Object)}
	 * .
	 */
	@Test
	public void testMatchLong() {
		try {
			EntityMatchRoot entity = new EntityMatchRoot();
			ConditionMatcher matcher = new ConditionMatcher();
			boolean retval = false;

			retval = matcher.match(entity, "FORlong", EnumOperator.Equal, ""
					+ entity.getForlong());
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORlong", EnumOperator.NotEqual,
					"" + 0);
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORlong", EnumOperator.GreaterThan,
					"" + (entity.getForlong() - 1));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORlong",
					EnumOperator.GreaterThanEqual, ""
							+ (entity.getForlong() - 2));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORlong", EnumOperator.LessThan, ""
					+ (entity.getForlong() + 1));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORlong",
					EnumOperator.LessThanEqual, "" + (entity.getForlong() + 2));
			assertEquals(true, retval);
			retval = matcher.match(
					entity,
					"FORlong",
					EnumOperator.Between,
					new String[] { "" + (entity.getForlong() - 1),
							"" + (entity.getForlong() + 1) });
			assertEquals(true, retval);
			retval = matcher.match(
					entity,
					"FORlong",
					EnumOperator.Between,
					new String[] { "" + (entity.getForlong() + 1),
							"" + (entity.getForlong() + 2) });
			assertEquals(false, retval);

			retval = matcher.match(entity, "FORLong", EnumOperator.Equal, ""
					+ entity.getForLong());
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORLong", EnumOperator.NotEqual,
					"" + 0);
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORLong", EnumOperator.GreaterThan,
					"" + (entity.getForLong() - 1));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORLong",
					EnumOperator.GreaterThanEqual, ""
							+ (entity.getForLong() - 2));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORLong", EnumOperator.LessThan, ""
					+ (entity.getForLong() + 1));
			assertEquals(true, retval);
			retval = matcher.match(entity, "FORLong",
					EnumOperator.LessThanEqual, "" + (entity.getForLong() + 2));
			assertEquals(true, retval);
			retval = matcher.match(
					entity,
					"FORLong",
					EnumOperator.Between,
					new String[] { "" + (entity.getForLong() - 1),
							"" + (entity.getForLong() + 1) });
			assertEquals(true, retval);
			retval = matcher.match(
					entity,
					"FORLong",
					EnumOperator.Between,
					new String[] { "" + (entity.getForLong() + 1),
							"" + (entity.getForLong() + 2) });
			assertEquals(false, retval);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
	}
}
