/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.utils.comparators;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * tests the GenericComparator.compare() First tests the simple cases (o1==o2 ||
 * o1.equals(o2) || o1=null || o2=null) Then tests that the rest cases are
 * delegated to the correct comparator implementation (StringComparator,
 * GenericNumberComparator, GenericDateComparator)
 */

public class TestGenericComparator {
	
	/**
	 * tests equality between two null items
	 */
	public void testNullEqualsNull() {
		String s1 = null;
		String s2 = null;
		GenericComparatorImpl gci = new GenericComparatorImpl();
		assertEquals(gci.compare(s1, s2), 0);
		Integer i1 = null;
		assertEquals(gci.compare(s1, i1), 0);
	}

	/**
	 * tests the behavior of
	 * gr.interamerican.bo2.utils.comparators.GenericComparator with Strings
	 */
	@Test
	public void testStringsGenericComparator() {

		String stringO1 = "stringO1"; //$NON-NLS-1$
		String stringO2 = "stringO2"; //$NON-NLS-1$
		String stringO3 = "stringO1"; //$NON-NLS-1$
		String stringO4 = stringO1;
		String stringClassO1 = new String("stringO1"); //$NON-NLS-1$
		String stringClassO2 = new String("stringO2"); //$NON-NLS-1$
		String stringClassO3 = new String("stringO1"); //$NON-NLS-1$
		String stringClassO4 = stringClassO1;

		GenericComparatorImpl gci = new GenericComparatorImpl();

		// test strings
		assertEquals(gci.compare(stringO1, stringO3), 0);
		assertEquals(gci.compare(stringO1, stringO4), 0);
		assertEquals(gci.compare(stringO1, null), 1);
		assertEquals(gci.compare(null, stringO2), -1);
		assertEquals(gci.compare(stringO1, stringO2), 30);

		// test Strings
		assertEquals(gci.compare(stringClassO1, stringClassO3), 0);
		assertEquals(gci.compare(stringClassO1, stringClassO4), 0);
		assertEquals(gci.compare(stringClassO1, null), 1);
		assertEquals(gci.compare(null, stringClassO2), -1);
		assertEquals(gci.compare(stringClassO1, stringClassO2), 30);

		// cross-test strings, Strings
		assertEquals(gci.compare(stringO1, stringClassO1), 0);
		assertEquals(gci.compare(stringO1, stringClassO2), 30);
		assertEquals(gci.compare(stringO1, stringClassO3), 0);
		
	}

	
	/**
	 * test compare ���� ��� �� ��� ����� ����� null
	 */
	@Test
	public void testCompare_withNullValues(){
		GenericComparator gc = new GenericComparator();
		assertEquals(gc.compare(null,null), 0);
	}
	
	/**
	 * test findComparator
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFindComparator(){
		GenericComparator gc = new GenericComparator();
		Integer actual = gc.compare("string1", "string2");
		Integer i = new Integer(-1);
		assertEquals(i,actual);
	}
	
	/**
	 * tests the behavior of
	 * gr.interamerican.bo2.utils.comparators.GenericComparator with Numbers
	 */
	@Test
	public void testNumbersGenericComparator() {

		int integerO1 = 1;
		int integerO2 = 2;
		int integerO3 = 1;
		int integerO4 = integerO1;
		Integer integerClassO1 = new Integer(1);
		Integer integerClassO2 = new Integer(2);
		Integer integerClassO3 = new Integer(1);
		Integer integerClassO4 = integerClassO1;
		long longO1 = 1;
		long longO2 = 2;
		long longO3 = 1;
		long longO4 = longO1;
		Long longClassO1 = new Long(1);
		Long longClassO2 = new Long(2);
		Long longClassO3 = new Long(1);
		Long longClassO4 = longClassO1;

		GenericComparatorImpl gci = new GenericComparatorImpl();

		// test ints
		assertEquals(gci.compare(integerO1, integerO3), 0);
		assertEquals(gci.compare(integerO1, integerO4), 0);
		assertEquals(gci.compare(integerO1, integerO2), 10);

		// test Integers
		assertEquals(gci.compare(integerClassO1, integerClassO3), 0);
		assertEquals(gci.compare(integerClassO1, integerClassO4), 0);
		assertEquals(gci.compare(integerClassO1, integerClassO2), 10);

		// test longs
		assertEquals(gci.compare(longO1, longO3), 0);
		assertEquals(gci.compare(longO1, longO4), 0);
		assertEquals(gci.compare(longO1, longO2), 10);

		// test Longs
		assertEquals(gci.compare(longClassO1, longClassO3), 0);
		assertEquals(gci.compare(longClassO1, longClassO4), 0);
		assertEquals(gci.compare(longClassO1, longClassO2), 10);

		// cross-tests
		assertEquals(gci.compare(integerO1, integerClassO1), 0);
		assertEquals(gci.compare(integerO1, integerClassO2), 10);
		assertEquals(gci.compare(integerO1, integerClassO3), 0);
		assertEquals(gci.compare(longO1, longClassO1), 0);
		assertEquals(gci.compare(longO1, longClassO2), 10);
		assertEquals(gci.compare(longO1, longClassO3), 0);

		// long-int, Long-int, long-Integer, Long-Integer sent to
		// GenericNumberComparator
		assertEquals(gci.compare(integerO1, longO1), 10);
		assertEquals(gci.compare(integerO1, longClassO1), 10);
		assertEquals(gci.compare(longO1, integerClassO1), 10);
		assertEquals(gci.compare(longClassO1, integerClassO1), 10);
	}

	/**
	 * tests the behavior of
	 * gr.interamerican.bo2.utils.comparators.GenericComparator with Dates
	 */
	@Test
	public void testDatesGenericComparator() {

		Long millis = System.currentTimeMillis();

		Date dateO1 = new Date(millis);
		Date dateO2 = new Date(millis + 1000 * 3600 * 25); // 25h later
		Date dateO3 = new Date(millis);
		Date dateO4 = dateO1;
		Calendar calendarO1 = new GregorianCalendar();
		calendarO1.setTime(dateO1);
		Calendar calendarO2 = new GregorianCalendar();
		calendarO2.setTime(dateO2);
		Calendar calendarO3 = new GregorianCalendar();
		calendarO3.setTime(dateO3);
		Calendar calendarO4 = calendarO1;

		GenericComparatorImpl gci = new GenericComparatorImpl();

		// test dates
		assertEquals(gci.compare(dateO1, dateO3), 0);
		assertEquals(gci.compare(dateO1, dateO4), 0);
		assertEquals(gci.compare(dateO1, dateO2), 20);

		// test calendars
		assertEquals(gci.compare(calendarO1, calendarO3), 0);
		assertEquals(gci.compare(calendarO1, calendarO4), 0);
		assertEquals(gci.compare(calendarO1, calendarO2), 20);

		// cross-tests sent to GenericDateComparator
		assertEquals(gci.compare(dateO1, calendarO1), 20);
	}

	/**
	 * tests the behavior of
	 * gr.interamerican.bo2.utils.comparators.GenericComparator with any other
	 * Object
	 */
	@Test
	public void testOtherTypesGenericComparator() {

		OtherType otherTypeO1 = new OtherType("one", 1); //$NON-NLS-1$
		OtherType otherTypeO2 = new OtherType("two", 1); //$NON-NLS-1$
		OtherType otherTypeO3 = new OtherType("one", 1); //$NON-NLS-1$
		OtherType otherTypeO4 = otherTypeO1;

		GenericComparatorImpl gci = new GenericComparatorImpl();

		assertEquals(gci.compare(otherTypeO1, otherTypeO3), 30);
		assertEquals(gci.compare(otherTypeO1, otherTypeO4), 0);
		assertEquals(gci.compare(otherTypeO1, otherTypeO2), 30);

	}
	
	/**
	 * tests compare
	 */
	@Test
	public void testCompare_NullObjects(){
		Object nullObject1 = null;
		Integer integer2 = 2;
		GenericComparator impl = new GenericComparator();
		assertEquals(impl.compare(nullObject1, integer2), -1);
		assertEquals(impl.compare(integer2, nullObject1), 1);
	}
	
	

	/**
	 * Simple implementation of GenericComparator for this test
	 */
	private class GenericComparatorImpl extends GenericComparator {

		/**
		 * value that findComparator returns when it decides to compare numbers
		 */
		private int COMPARE_NUMBERS = 10;

		/**
		 * value that findComparator returns when it decides to compare dates
		 */
		private int COMPARE_DATES = 20;

		/**
		 * value that findComparator returns when it decides to compare strings
		 */
		private int COMPARE_STRINGS = 30;

		/**
		 * @param o1
		 *            object to compare
		 * @param o2
		 *            object to compare
		 * @return one value depending on what comparison it decides upon
		 */
		private int findComparator(Object o1, Object o2) {
			if (Number.class.isInstance(o1) && Number.class.isInstance(o2)) {
				return COMPARE_NUMBERS;
			}
			if ((Date.class.isInstance(o1) || Calendar.class.isInstance(o1))
					&& (Date.class.isInstance(o2) || Calendar.class
							.isInstance(o2))) {
				return COMPARE_DATES;
			}
			return COMPARE_STRINGS;
		}

		@Override
		public int compare(Object o1, Object o2) {
			if (o1 == o2) {
				return 0;
			}
			if (o1 == null) {
				return -1;
			}
			if (o2 == null) {
				return 1;
			}
			if (o1.equals(o2)) {
				return 0;
			}
			return findComparator(o1, o2);
		}

	}

	/**
	 * Arbitrary object used to test
	 * gr.interamerican.bo2.utils.comparators.GenericComparator behavior with
	 * arbitrary objects
	 */
	private class OtherType {
		/**
		 * the object has one String member
		 */
		String field1;
		/**
		 * and one int member
		 */
		int field2;

		/**
		 * Creates a new OtherType object. 
		 *
		 * @param f1 the String
		 * @param f2 the int
		 */
		public OtherType(String f1, int f2) {
			this.field1 = f1;
			this.field2 = f2;
		}

		@Override
		public String toString() {
			return field1 + new Integer(field2).toString();
		}
	}

}
