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
package gr.interamerican.bo2.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ArrayUtils}.
 */
public class TestArrayUtils {
	
	/**
	 * Tests ensureCapacity.
	 */
	@Test
	public void testEnsureCapacity() {
		
		Object[] o1 = {};
		Object[] o2 = ArrayUtils.ensureCapacity(o1, 10);
		assertEquals(o2.length, 10);
		
		Object[] o3 = {2, 3, 4};
		Object[] o4 = ArrayUtils.ensureCapacity(o3, 10);
		assertEquals(o4.length, 10);
		
		Object[] o5 = ArrayUtils.ensureCapacity(o3, 2);
		assertTrue(o3==o5);
	}
	
	
	
	/**
	 * Tests ensureCapacity.
	 */
	@Test
	public void testEnforceCapacity() {
		Object[] o1 = {};
		Object[] o2 = ArrayUtils.enforceCapacity(o1, 10);
		assertEquals(o2.length, 10);
		
		Object[] o3 = {1, 2, 3};
		
		Object[] o4 = ArrayUtils.enforceCapacity(o3, 10);
		Object[] o4expected = {1, 2, 3, null, null, null, null, null, null, null};
		assertArrayEquals(o4expected, o4);
		
		Object[] o5 = ArrayUtils.enforceCapacity(o3, 3);
		assertSame(o3,o5);

		Object[] o6 = ArrayUtils.enforceCapacity(o3, 2);
		Object[] o6expected = {1, 2};
		assertArrayEquals(o6expected, o6);
	}	
	
	/**
	 * Tests contains.
	 */
	@Test
	public void testContains() {
		Object[] array = {null, 0, "string", null, null}; //$NON-NLS-1$
		assertTrue(ArrayUtils.contains(array, 0));
		assertTrue(ArrayUtils.contains(array, "string")); //$NON-NLS-1$
		assertFalse(ArrayUtils.contains(array, null));
	}
	
	/**
	 * Tests containsNotNull.
	 */
	@Test
	public void testContainsNotNull() {
		Object[] array = {0, "string", null, null}; //$NON-NLS-1$
		assertTrue(ArrayUtils.containsNotNull(array, 0));
		assertTrue(ArrayUtils.containsNotNull(array, 1));
		assertFalse(ArrayUtils.containsNotNull(array, 2));
		assertFalse(ArrayUtils.containsNotNull(array, 3));
		assertFalse(ArrayUtils.containsNotNull(array, 5));
	}	
	
	
	/**
	 * Tests containsNotNull.
	 */
	@Test
	public void testContainsNull() {
		Object[] array1 = {0, "string", null, null}; //$NON-NLS-1$
		Object[] array2 = {0, "string"}; //$NON-NLS-1$
		assertTrue(ArrayUtils.containsNull(array1));
		assertFalse(ArrayUtils.containsNull(array2));
		
	}

	/**
	 * Tests getFirstNull.
	 */
	@Test
	public void testGetFirstNull() {
		Object[] array = {0, "string", null, null}; //$NON-NLS-1$
		Integer idx1 = ArrayUtils.getFirstNull(array);
		assertEquals(new Integer(2), idx1);
	}
	
	/**
	 * Tests getFirstNull.
	 */
	@Test
	public void testGetFirstNull_ReturningNull() {
		Object[] array = {0, "string", 2}; //$NON-NLS-1$
		Integer idx1 = ArrayUtils.getFirstNull(array);
		assertNull(idx1);
	}
	
	/**
	 * Tests getFirstNull.
	 */
	@Test
	public void testArrayAsListOfArrays() {
		Object[] in = {1,2,3};
		Object[][] expecteds = {
				{1},{2},{3}
		};
		List<Object[]> list = ArrayUtils.arrayAsListOfArrays(in);
		Object[][] actuals = list.toArray(new Object[0][]);
		assertArrayEquals(expecteds, actuals);
	}
	
	
	/**
	 * Tests removeNulls.
	 */
	@Test
	public void testRemoveNulls() {
		Object[] in = {1,null,3, StringConstants.SPACE};
		Object[] expecteds = {1,3,StringConstants.SPACE};
		Object[] actuals = ArrayUtils.removeNulls(in);
		assertArrayEquals(expecteds, actuals);
	}
	
	
	/**
	 * Tests copyOf.
	 */
	@Test
	public void testCopyOf() {
		byte[] buffer = new byte[100];
		Integer newLength = 50;
		byte[] newBuffer=ArrayUtils.copyOf(buffer, newLength);
		Integer expected = 50;
		Integer actual = newBuffer.length;
		assertEquals(expected,actual);
		for (int i = 0; i < newBuffer.length; i++) {
			assertEquals(buffer[i], newBuffer[i]);
		}
	}
	
	/**
	 * test safeGet().
	 */
	@Test
	@SuppressWarnings("nls")
	public void testSafeGet(){
		String[] array = null;
		assertNull(ArrayUtils.safeGet(array, 1));
		array = new String[]{"a", "b"};
		assertNull(ArrayUtils.safeGet(array, -1));
		assertNull(ArrayUtils.safeGet(array, 2));
		assertEquals(ArrayUtils.safeGet(array, 0), "a");
		assertEquals(ArrayUtils.safeGet(array, 1), "b");
	}
	
	/**
	 * test equals().
	 */
	@Test
	@SuppressWarnings("nls")
	public void testEquals(){
		Integer[] two = {1,2};
		Integer[] four1 = {1,2,3,4};
		Integer[] four2 = {1,2,3,4};
		Integer[] four3 = {5,6,7,8};
		Integer[] empty = {};
		
		
		Object[][] setups = {
			{null, null, "A=null, B=null", true},
			{null, two, "A=null, B!=null", false},
			{empty,null,"A=empty, B==null", false},
			{two, four1, "different sizes", false},
			{two, two, "same table", true},
			{four1, four3, "same size different elements", false},
			{four1, four2, "same size same elements", true}
		};
		
		for (int i = 0; i < setups.length; i++) {
			Object[] setup = setups[i];
			Integer[] array1 = (Integer[]) setup[0];
			Integer[] array2 = (Integer[]) setup[1];
			String message = (String) setup[2];
			boolean expected = (Boolean) setup[3];			
			boolean actual=ArrayUtils.equals(array1, array2);
			Assert.assertEquals(message, expected, actual);
		}
	}
		

}
