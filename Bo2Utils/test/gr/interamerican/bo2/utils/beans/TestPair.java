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
package gr.interamerican.bo2.utils.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.utils.StringUtils;

import org.junit.Test;

/**
 * Unit test for {@link Pair}.
 */
public class TestPair {
	
	/**
	 * Tests the two arguments constructor.
	 */
	@Test
	public void testConstructor_WithTwoArguments() {
		Pair<Integer, String> pair = new Pair<Integer, String>(2,"2"); //$NON-NLS-1$
		assertEquals(new Integer(2), pair.getLeft());
		assertEquals("2", pair.getRight()); //$NON-NLS-1$
	}
	
	/**
	 * test equals()
	 */
	@Test
	public void testEquals() {
		Integer l = 2;
		Integer r = 3;
		Pair<Integer, Integer> p1 = new Pair<Integer, Integer>(l,r);
		Pair<Integer, Integer> p2 = new Pair<Integer, Integer>(l,r);
		assertTrue(p1.equals(p2));
		assertTrue(p1.hashCode()==p2.hashCode());
		
		Pair<Integer, Integer> p3 = new Pair<Integer, Integer>(8,2);
		assertFalse(p1.equals(p3));
		
		assertFalse(p1.equals(null));
		assertFalse(p1.equals(new Object()));
	}
	
	/**
	 * test equals()
	 */
	@Test
	public void testEquals_WithNulls() {
		Pair<Integer, Integer> p1 = new Pair<Integer, Integer>();
		Pair<Integer, Integer> p2 = new Pair<Integer, Integer>();
		assertTrue(p1.equals(p2));
		assertTrue(p1.hashCode()==p2.hashCode());
	}
	
	/**
	 * test equals()
	 */
	@Test
	public void testHashcode() {
		Integer l = 2;
		Integer r = 3;
		Pair<Integer, Integer> p1 = new Pair<Integer, Integer>(l,r);
		Pair<Integer, Integer> p2 = new Pair<Integer, Integer>(l,r);
		assertEquals(p1.hashCode(), p2.hashCode());	
		
		Pair<Integer, Integer> p3 = new Pair<Integer, Integer>(8,2);
		assertTrue(p1.hashCode()!=p3.hashCode());
	}

	/**
	 * test equals()
	 */
	@Test
	public void testHashcode_WithNulls() {
		Integer l = 2;
		Pair<Integer, Integer> p1 = new Pair<Integer, Integer>();
		Pair<Integer, Integer> p2 = new Pair<Integer, Integer>();
		p1.setLeft(l);
		p2.setLeft(l);
		assertEquals(p1.hashCode(), p2.hashCode());		
	}
	
	/**
	 * Unit test for toString()
	 */
	@Test
	public void testToString() {
		testToString(new Pair<Object, Object>());
		testToString(new Pair<Object, Object>(new Object(), "ss")); //$NON-NLS-1$
	}
	
	/**
	 * Unit tests for toString().
	 * @param pair
	 */
	@SuppressWarnings("nls")
	private void testToString(Pair<?,?> pair) {
		String string = pair.toString();
		assertTrue(string.startsWith("["));
		assertTrue(string.endsWith("]"));
		assertTrue(string.indexOf(',')!=0);
		String left = StringUtils.toString(pair.getLeft());
		assertTrue(string.indexOf(left)==1);
		String right = StringUtils.toString(pair.getRight());
		assertTrue(string.indexOf(right)!=0);
	}
	
	/**
	 * Unit test for toString()
	 */
	@Test
	public void testPair() {
		Object[] array = {new Object(), new Object()};
		Pair<Object, Object> actual = Pair.pair(array);
		assertNotNull(actual);
		Pair<Object, Object> expected = new Pair<Object, Object>(array[0], array[1]);
		assertEquals(expected, actual);
	}
	
	
	
	

	
}
