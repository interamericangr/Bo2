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

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

/**
 * tests the GenericNumberComparator class
 */
public class TestGenericNumberComparator {

	/**
	 * Every possible pair of the 8 Number sub-types is tested
	 */
	@Test
	public void testGenericNumberComparator() {

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

		// these will be converted to double by the GenericNumberComparator
		String number1 = "1"; //$NON-NLS-1$
		String number2 = "2"; //$NON-NLS-1$
		String number3 = "1"; //$NON-NLS-1$

		GenericNumberComparator gnc = new GenericNumberComparator();

		// simple tests
		assertTrue(gnc.compare(byte1, byte3) == 0);
		assertTrue(gnc.compare(byte1, byte2) < 0);
		assertTrue(gnc.compare(byte2, byte1) > 0);
		assertTrue(gnc.compare(short1, short3) == 0);
		assertTrue(gnc.compare(short1, short2) < 0);
		assertTrue(gnc.compare(short2, short1) > 0);
		assertTrue(gnc.compare(integer1, integer3) == 0);
		assertTrue(gnc.compare(integer1, integer2) < 0);
		assertTrue(gnc.compare(integer2, integer1) > 0);
		assertTrue(gnc.compare(long1, long3) == 0);
		assertTrue(gnc.compare(long1, long2) < 0);
		assertTrue(gnc.compare(long2, long1) > 0);
		assertTrue(gnc.compare(bigInt1, bigInt3) == 0);
		assertTrue(gnc.compare(bigInt1, bigInt2) < 0);
		assertTrue(gnc.compare(bigInt2, bigInt1) > 0);
		assertTrue(gnc.compare(float1, float3) == 0);
		assertTrue(gnc.compare(float1, float2) < 0);
		assertTrue(gnc.compare(float2, float1) > 0);
		assertTrue(gnc.compare(double1, double3) == 0);
		assertTrue(gnc.compare(double1, double2) < 0);
		assertTrue(gnc.compare(double2, double1) > 0);
		assertTrue(gnc.compare(bigDecimal1, bigDecimal3) == 0);
		assertTrue(gnc.compare(bigDecimal1, bigDecimal2) < 0);
		assertTrue(gnc.compare(bigDecimal2, bigDecimal1) > 0);
		assertTrue(gnc.compare(number1, number3) == 0);
		assertTrue(gnc.compare(number1, number2) < 0);
		assertTrue(gnc.compare(number2, number1) > 0);

		// cross-tests
		// bytes
		assertTrue(gnc.compare(byte1, short1) == 0);
		assertTrue(gnc.compare(byte1, short2) < 0);
		assertTrue(gnc.compare(byte2, short1) > 0);

		assertTrue(gnc.compare(byte1, integer1) == 0);
		assertTrue(gnc.compare(byte1, integer2) < 0);
		assertTrue(gnc.compare(byte2, integer1) > 0);

		assertTrue(gnc.compare(byte1, long1) == 0);
		assertTrue(gnc.compare(byte1, long2) < 0);
		assertTrue(gnc.compare(byte2, long1) > 0);

		assertTrue(gnc.compare(byte1, bigInt1) == 0);
		assertTrue(gnc.compare(byte1, bigInt2) < 0);
		assertTrue(gnc.compare(byte2, bigInt1) > 0);

		assertTrue(gnc.compare(byte1, float1) == 0);
		assertTrue(gnc.compare(byte1, float2) < 0);
		assertTrue(gnc.compare(byte2, float1) > 0);

		assertTrue(gnc.compare(byte1, double1) == 0);
		assertTrue(gnc.compare(byte1, double2) < 0);
		assertTrue(gnc.compare(byte2, double1) > 0);

		assertTrue(gnc.compare(byte1, bigDecimal1) == 0);
		assertTrue(gnc.compare(byte1, bigDecimal2) < 0);
		assertTrue(gnc.compare(byte2, bigDecimal1) > 0);

		assertTrue(gnc.compare(byte1, number1) == 0);
		assertTrue(gnc.compare(byte1, number2) < 0);
		assertTrue(gnc.compare(byte2, number1) > 0);

		// short
		assertTrue(gnc.compare(short1, integer1) == 0);
		assertTrue(gnc.compare(short1, integer2) < 0);
		assertTrue(gnc.compare(short2, integer1) > 0);

		assertTrue(gnc.compare(short1, long1) == 0);
		assertTrue(gnc.compare(short1, long2) < 0);
		assertTrue(gnc.compare(short2, long1) > 0);

		assertTrue(gnc.compare(short1, bigInt1) == 0);
		assertTrue(gnc.compare(short1, bigInt2) < 0);
		assertTrue(gnc.compare(short2, bigInt1) > 0);

		assertTrue(gnc.compare(short1, float1) == 0);
		assertTrue(gnc.compare(short1, float2) < 0);
		assertTrue(gnc.compare(short2, float1) > 0);

		assertTrue(gnc.compare(short1, double1) == 0);
		assertTrue(gnc.compare(short1, double2) < 0);
		assertTrue(gnc.compare(short2, double1) > 0);

		assertTrue(gnc.compare(short1, bigDecimal1) == 0);
		assertTrue(gnc.compare(short1, bigDecimal2) < 0);
		assertTrue(gnc.compare(short2, bigDecimal1) > 0);

		assertTrue(gnc.compare(short1, number1) == 0);
		assertTrue(gnc.compare(short1, number2) < 0);
		assertTrue(gnc.compare(short2, number1) > 0);

		// integer
		assertTrue(gnc.compare(integer1, long1) == 0);
		assertTrue(gnc.compare(integer1, long2) < 0);
		assertTrue(gnc.compare(integer2, long1) > 0);

		assertTrue(gnc.compare(integer1, bigInt1) == 0);
		assertTrue(gnc.compare(integer1, bigInt2) < 0);
		assertTrue(gnc.compare(integer2, bigInt1) > 0);

		assertTrue(gnc.compare(integer1, float1) == 0);
		assertTrue(gnc.compare(integer1, float2) < 0);
		assertTrue(gnc.compare(integer2, float1) > 0);

		assertTrue(gnc.compare(integer1, double1) == 0);
		assertTrue(gnc.compare(integer1, double2) < 0);
		assertTrue(gnc.compare(integer2, double1) > 0);

		assertTrue(gnc.compare(integer1, bigDecimal1) == 0);
		assertTrue(gnc.compare(integer1, bigDecimal2) < 0);
		assertTrue(gnc.compare(integer2, bigDecimal1) > 0);

		assertTrue(gnc.compare(integer1, number1) == 0);
		assertTrue(gnc.compare(integer1, number2) < 0);
		assertTrue(gnc.compare(integer2, number1) > 0);

		// long
		assertTrue(gnc.compare(long1, bigInt1) == 0);
		assertTrue(gnc.compare(long1, bigInt2) < 0);
		assertTrue(gnc.compare(long2, bigInt1) > 0);

		assertTrue(gnc.compare(long1, float1) == 0);
		assertTrue(gnc.compare(long1, float2) < 0);
		assertTrue(gnc.compare(long2, float1) > 0);

		assertTrue(gnc.compare(long1, double1) == 0);
		assertTrue(gnc.compare(long1, double2) < 0);
		assertTrue(gnc.compare(long2, double1) > 0);

		assertTrue(gnc.compare(long1, bigDecimal1) == 0);
		assertTrue(gnc.compare(long1, bigDecimal2) < 0);
		assertTrue(gnc.compare(long2, bigDecimal1) > 0);

		assertTrue(gnc.compare(long1, number1) == 0);
		assertTrue(gnc.compare(long1, number2) < 0);
		assertTrue(gnc.compare(long2, number1) > 0);

		// bigInt
		assertTrue(gnc.compare(bigInt1, float1) == 0);
		assertTrue(gnc.compare(bigInt1, float2) < 0);
		assertTrue(gnc.compare(bigInt2, float1) > 0);

		assertTrue(gnc.compare(bigInt1, double1) == 0);
		assertTrue(gnc.compare(bigInt1, double2) < 0);
		assertTrue(gnc.compare(bigInt2, double1) > 0);

		assertTrue(gnc.compare(bigInt1, bigDecimal1) == 0);
		assertTrue(gnc.compare(bigInt1, bigDecimal2) < 0);
		assertTrue(gnc.compare(bigInt2, bigDecimal1) > 0);

		assertTrue(gnc.compare(bigInt1, number1) == 0);
		assertTrue(gnc.compare(bigInt1, number2) < 0);
		assertTrue(gnc.compare(bigInt2, number1) > 0);

		// float
		assertTrue(gnc.compare(float1, double1) == 0);
		assertTrue(gnc.compare(float1, double2) < 0);
		assertTrue(gnc.compare(float2, double1) > 0);

		assertTrue(gnc.compare(float1, bigDecimal1) == 0);
		assertTrue(gnc.compare(float1, bigDecimal2) < 0);
		assertTrue(gnc.compare(float2, bigDecimal1) > 0);

		assertTrue(gnc.compare(float1, number1) == 0);
		assertTrue(gnc.compare(float1, number2) < 0);
		assertTrue(gnc.compare(float2, number1) > 0);

		// double
		assertTrue(gnc.compare(double1, bigDecimal1) == 0);
		assertTrue(gnc.compare(double1, bigDecimal2) < 0);
		assertTrue(gnc.compare(double2, bigDecimal1) > 0);

		assertTrue(gnc.compare(double1, number1) == 0);
		assertTrue(gnc.compare(double1, number2) < 0);
		assertTrue(gnc.compare(double2, number1) > 0);

		// bigDecimal
		assertTrue(gnc.compare(bigDecimal1, number1) == 0);
		assertTrue(gnc.compare(bigDecimal1, number2) < 0);
		assertTrue(gnc.compare(bigDecimal2, number1) > 0);

	}

}
