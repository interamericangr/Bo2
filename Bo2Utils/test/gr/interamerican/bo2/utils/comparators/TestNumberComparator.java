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
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

/**
 * tests gr.interamerican.bo2.utils.comparators.NumberComparator
 */
public class TestNumberComparator {

	/**
	 * Tests that the NumberComparator compares correctly all type combinations
	 * by converting both types to the most complex type Concrete Comparators
	 * (e.g. LongComparator) are tested implicitly here
	 */
	@Test
	public void testNumberComparator() {
		Byte byte1 = 1;
		Byte byte2 = 2;
		Byte byte3 = 1;
		Short short1 = 1;
		Short short2 = 2;
		Short short3 = 1;
		Integer integer1 = new Integer(1);
		Integer integer2 = new Integer(2);
		Integer integer3 = new Integer(1);
		Long long1 = new Long(1);
		Long long2 = new Long(2);
		Long long3 = new Long(1);
		BigInteger bigInt1 = BigInteger.ONE;
		BigInteger bigInt2 = BigInteger.TEN;
		BigInteger bigInt3 = BigInteger.ONE;
		Float float1 = 1.f;
		Float float2 = 2.f;
		Float float3 = 1.f;
		Double double1 = 1.d;
		Double double2 = 2.d;
		Double double3 = 1.d;
		BigDecimal bigDecimal1 = new BigDecimal(1);
		BigDecimal bigDecimal2 = new BigDecimal(2);
		BigDecimal bigDecimal3 = new BigDecimal(1);
		Integer integer4 = null;

		NumberComparatorImpl nci = new NumberComparatorImpl();

		// simple tests
		assertTrue(nci.compare(byte1, byte3) == 0);
		assertTrue(nci.compare(byte1, byte2) < 0);
		assertTrue(nci.compare(byte2, byte1) > 0);
		assertTrue(nci.compare(short1, short3) == 0);
		assertTrue(nci.compare(short1, short2) < 0);
		assertTrue(nci.compare(short2, short1) > 0);
		assertTrue(nci.compare(integer1, integer3) == 0);
		assertTrue(nci.compare(integer1, integer2) < 0);
		assertTrue(nci.compare(integer2, integer1) > 0);
		assertTrue(nci.compare(long1, long3) == 0);
		assertTrue(nci.compare(long1, long2) < 0);
		assertTrue(nci.compare(long2, long1) > 0);
		assertTrue(nci.compare(bigInt1, bigInt3) == 0);
		assertTrue(nci.compare(bigInt1, bigInt2) < 0);
		assertTrue(nci.compare(bigInt2, bigInt1) > 0);
		assertTrue(nci.compare(float1, float3) == 0);
		assertTrue(nci.compare(float1, float2) < 0);
		assertTrue(nci.compare(float2, float1) > 0);
		assertTrue(nci.compare(double1, double3) == 0);
		assertTrue(nci.compare(double1, double2) < 0);
		assertTrue(nci.compare(double2, double1) > 0);
		assertTrue(nci.compare(bigDecimal1, bigDecimal3) == 0);
		assertTrue(nci.compare(bigDecimal1, bigDecimal2) < 0);
		assertTrue(nci.compare(bigDecimal2, bigDecimal1) > 0);

		// cross-tests
		// bytes
		assertEquals(nci.typeOfComparison(byte1, byte3), 0);

		assertEquals(nci.typeOfComparison(byte1, short1), 1);
		assertTrue(nci.compare(byte1, short1) == 0);
		assertTrue(nci.compare(byte1, short2) < 0);
		assertTrue(nci.compare(byte2, short1) > 0);

		assertEquals(nci.typeOfComparison(byte1, integer1), 2);
		assertTrue(nci.compare(byte1, integer1) == 0);
		assertTrue(nci.compare(byte1, integer2) < 0);
		assertTrue(nci.compare(byte2, integer1) > 0);

		assertEquals(nci.typeOfComparison(byte1, long1), 3);
		assertTrue(nci.compare(byte1, long1) == 0);
		assertTrue(nci.compare(byte1, long2) < 0);
		assertTrue(nci.compare(byte2, long1) > 0);

		assertEquals(nci.typeOfComparison(byte1, bigInt1), 4);
		assertTrue(nci.compare(byte1, bigInt1) == 0);
		assertTrue(nci.compare(byte1, bigInt2) < 0);
		assertTrue(nci.compare(byte2, bigInt1) > 0);

		assertEquals(nci.typeOfComparison(byte1, float1), 5);
		assertTrue(nci.compare(byte1, float1) == 0);
		assertTrue(nci.compare(byte1, float2) < 0);
		assertTrue(nci.compare(byte2, float1) > 0);

		assertEquals(nci.typeOfComparison(byte1, double1), 6);
		assertTrue(nci.compare(byte1, double1) == 0);
		assertTrue(nci.compare(byte1, double2) < 0);
		assertTrue(nci.compare(byte2, double1) > 0);

		assertEquals(nci.typeOfComparison(byte1, bigDecimal1), 7);
		assertTrue(nci.compare(byte1, bigDecimal1) == 0);
		assertTrue(nci.compare(byte1, bigDecimal2) < 0);
		assertTrue(nci.compare(byte2, bigDecimal1) > 0);

		// short
		assertEquals(nci.typeOfComparison(short1, short3), 1);

		assertEquals(nci.typeOfComparison(short1, integer1), 2);
		assertTrue(nci.compare(short1, integer1) == 0);
		assertTrue(nci.compare(short1, integer2) < 0);
		assertTrue(nci.compare(short2, integer1) > 0);

		assertEquals(nci.typeOfComparison(short1, long1), 3);
		assertTrue(nci.compare(short1, long1) == 0);
		assertTrue(nci.compare(short1, long2) < 0);
		assertTrue(nci.compare(short2, long1) > 0);

		assertEquals(nci.typeOfComparison(short1, bigInt1), 4);
		assertTrue(nci.compare(short1, bigInt1) == 0);
		assertTrue(nci.compare(short1, bigInt2) < 0);
		assertTrue(nci.compare(short2, bigInt1) > 0);

		assertEquals(nci.typeOfComparison(short1, float1), 5);
		assertTrue(nci.compare(short1, float1) == 0);
		assertTrue(nci.compare(short1, float2) < 0);
		assertTrue(nci.compare(short2, float1) > 0);

		assertEquals(nci.typeOfComparison(short1, double1), 6);
		assertTrue(nci.compare(short1, double1) == 0);
		assertTrue(nci.compare(short1, double2) < 0);
		assertTrue(nci.compare(short2, double1) > 0);

		assertEquals(nci.typeOfComparison(short1, bigDecimal1), 7);
		assertTrue(nci.compare(short1, bigDecimal1) == 0);
		assertTrue(nci.compare(short1, bigDecimal2) < 0);
		assertTrue(nci.compare(short2, bigDecimal1) > 0);

		// integer
		assertEquals(nci.typeOfComparison(integer1, integer3), 2);

		assertEquals(nci.typeOfComparison(integer1, long1), 3);
		assertTrue(nci.compare(integer1, long1) == 0);
		assertTrue(nci.compare(integer1, long2) < 0);
		assertTrue(nci.compare(integer2, long1) > 0);

		assertEquals(nci.typeOfComparison(integer1, bigInt1), 4);
		assertTrue(nci.compare(integer1, bigInt1) == 0);
		assertTrue(nci.compare(integer1, bigInt2) < 0);
		assertTrue(nci.compare(integer2, bigInt1) > 0);

		assertEquals(nci.typeOfComparison(integer1, float1), 5);
		assertTrue(nci.compare(integer1, float1) == 0);
		assertTrue(nci.compare(integer1, float2) < 0);
		assertTrue(nci.compare(integer2, float1) > 0);

		assertEquals(nci.typeOfComparison(integer1, double1), 6);
		assertTrue(nci.compare(integer1, double1) == 0);
		assertTrue(nci.compare(integer1, double2) < 0);
		assertTrue(nci.compare(integer2, double1) > 0);

		assertEquals(nci.typeOfComparison(integer1, bigDecimal1), 7);
		assertTrue(nci.compare(integer1, bigDecimal1) == 0);
		assertTrue(nci.compare(integer1, bigDecimal2) < 0);
		assertTrue(nci.compare(integer2, bigDecimal1) > 0);

		// long
		assertEquals(nci.typeOfComparison(long1, long3), 3);

		assertEquals(nci.typeOfComparison(long1, bigInt1), 4);
		assertTrue(nci.compare(long1, bigInt1) == 0);
		assertTrue(nci.compare(long1, bigInt2) < 0);
		assertTrue(nci.compare(long2, bigInt1) > 0);

		assertEquals(nci.typeOfComparison(long1, float1), 5);
		assertTrue(nci.compare(long1, float1) == 0);
		assertTrue(nci.compare(long1, float2) < 0);
		assertTrue(nci.compare(long2, float1) > 0);

		assertEquals(nci.typeOfComparison(long1, double1), 6);
		assertTrue(nci.compare(long1, double1) == 0);
		assertTrue(nci.compare(long1, double2) < 0);
		assertTrue(nci.compare(long2, double1) > 0);

		assertEquals(nci.typeOfComparison(long1, bigDecimal1), 7);
		assertTrue(nci.compare(long1, bigDecimal1) == 0);
		assertTrue(nci.compare(long1, bigDecimal2) < 0);
		assertTrue(nci.compare(long2, bigDecimal1) > 0);

		// bigInt
		assertEquals(nci.typeOfComparison(bigInt1, bigInt3), 4);

		assertEquals(nci.typeOfComparison(bigInt1, float1), 5);
		assertTrue(nci.compare(bigInt1, float1) == 0);
		assertTrue(nci.compare(bigInt1, float2) < 0);
		assertTrue(nci.compare(bigInt2, float1) > 0);

		assertEquals(nci.typeOfComparison(bigInt1, double1), 6);
		assertTrue(nci.compare(bigInt1, double1) == 0);
		assertTrue(nci.compare(bigInt1, double2) < 0);
		assertTrue(nci.compare(bigInt2, double1) > 0);

		assertEquals(nci.typeOfComparison(bigInt1, bigDecimal1), 7);
		assertTrue(nci.compare(bigInt1, bigDecimal1) == 0);
		assertTrue(nci.compare(bigInt1, bigDecimal2) < 0);
		assertTrue(nci.compare(bigInt2, bigDecimal1) > 0);

		// float
		assertEquals(nci.typeOfComparison(float1, float3), 5);

		assertEquals(nci.typeOfComparison(float1, double1), 6);
		assertTrue(nci.compare(float1, double1) == 0);
		assertTrue(nci.compare(float1, double2) < 0);
		assertTrue(nci.compare(float2, double1) > 0);

		assertEquals(nci.typeOfComparison(float1, bigDecimal1), 7);
		assertTrue(nci.compare(float1, bigDecimal1) == 0);
		assertTrue(nci.compare(float1, bigDecimal2) < 0);
		assertTrue(nci.compare(float2, bigDecimal1) > 0);

		// double
		assertEquals(nci.typeOfComparison(double1, double3), 6);

		assertEquals(nci.typeOfComparison(double1, bigDecimal1), 7);
		assertTrue(nci.compare(double1, bigDecimal1) == 0);
		assertTrue(nci.compare(double1, bigDecimal2) < 0);
		assertTrue(nci.compare(double2, bigDecimal1) > 0);

		// bigDecimal
		assertEquals(nci.typeOfComparison(bigDecimal1, bigDecimal3), 7);
		
		//null
		assertEquals(nci.compare(integer4, integer1), -1);
		assertEquals(nci.compare(integer1, integer4), 1);

	}

	/**
	 * Local implementation of NumberComparator to extract private logic of
	 * NumberComparator for the tests
	 */
	private class NumberComparatorImpl extends NumberComparator {

		@Override
		int type(Number n) {
			return super.type(n);
		}

		/**
		 * utility method to check that accuracy is not lost from calculations
		 * of NumberComparator.compare(), i.e. that both Numbers are cast to the
		 * most complex type
		 * 
		 * @param n1
		 *            first number
		 * @param n2
		 *            second number
		 * @return requires highest complexity class position in array
		 *         NumberComparator.TYPES
		 */
		public int typeOfComparison(Number n1, Number n2) {
			return Math.max(type(n1), type(n2));
		}
	}

}
