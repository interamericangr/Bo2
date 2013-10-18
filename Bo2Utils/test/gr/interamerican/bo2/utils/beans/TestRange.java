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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 */
public class TestRange {
	/**
	 * Instance being tested.
	 */
	private Range<Integer> range = new Range<Integer>(10,20);
	
	/**
	 * test for contains().
	 */
	@Test
	public void testContains() {
		assertFalse(range.contains(-15));
		assertTrue(range.contains(10));
		assertTrue(range.contains(15));
		assertTrue(range.contains(20));
		assertFalse(range.contains(45));
	}
	
	/**
	 * test for overlasWith().
	 */
	@Test
	public void testOverlapsWith() {
		assertFalse(range.overlapsWith(new Range<Integer>(0,5)));
		assertTrue(range.overlapsWith(new Range<Integer>(0,10)));
		assertTrue(range.overlapsWith(new Range<Integer>(0,15)));
		assertTrue(range.overlapsWith(new Range<Integer>(10,20)));
		assertTrue(range.overlapsWith(new Range<Integer>(12,15)));
		assertTrue(range.overlapsWith(new Range<Integer>(12,25)));
		assertTrue(range.overlapsWith(new Range<Integer>(20,30)));
		assertFalse(range.overlapsWith(new Range<Integer>(30,40)));
	}
	
	/**
	 * test for static Range.contains().
	 */
	@Test
	public void testStaticContains() {
		assertTrue(Range.contains(range, 15));
	}
	
	/**
	 * test for static Range.contains().
	 */
	@Test(expected=ClassCastException.class)
	public void testStaticContains_WithClassCastExceptionForLong() {
		Range.contains(range, 1L);
	}
	
	/**
	 * test for static Range.contains().
	 */
	@Test(expected=ClassCastException.class)
	public void testStaticContains_WithClassCastExceptionForString() {
		Range.contains(range, "this"); //$NON-NLS-1$
	}
	
	
	/**
	 * test for static Range.contains().
	 */	
	@Test
	public void testSetRight() {
		Range<Integer> r = new Range<Integer>();
		Integer left = 1;
		Integer right = 2;		
		r.setRight(right);
		assertNull(r.getLeft());
		assertEquals(right, r.getRight());		
		r.setLeft(left);
		assertEquals(right, r.getRight());
		assertEquals(left, r.getLeft());
	}
	
	/**
	 * test for static Range.contains().
	 */	
	@Test
	public void testSetLeft() {
		Range<Integer> r = new Range<Integer>();
		Integer left = 1;
		Integer right = 2;		
		r.setLeft(left);
		assertNotNull(r.getLeft());
		assertEquals(left, r.getRight());
		r.setRight(right);
		assertEquals(right, r.getRight());
		
	}
	
	/**
	 * tests the two arguments constructor
	 */
	@Test
	public void testConstructor() {
		Integer left = 1;
		Integer right = 2;
		Range<Integer> r = new Range<Integer>(left, right);		
		assertEquals(left, r.getLeft());
		assertEquals(right, r.getRight());
		r = new Range<Integer>(right, left);
		assertEquals(left, r.getLeft());
		assertEquals(right, r.getRight());
	}
	
	/**
	 * Unit test for compareTo
	 */
	@Test
	public void testCompareTo() {		
		assertTrue(range.compareTo(new Range<Integer>(0,5))>0);
		assertTrue(range.compareTo(new Range<Integer>(0,10))>0);
		assertTrue(range.compareTo(new Range<Integer>(0,15))>0);
		assertTrue(range.compareTo(new Range<Integer>(0,30))>0);
		assertTrue(range.compareTo(new Range<Integer>(10,20))==0);
		assertTrue(range.compareTo(new Range<Integer>(12,15))<0);
		assertTrue(range.compareTo(new Range<Integer>(12,25))<0);
		assertTrue(range.compareTo(new Range<Integer>(20,30))<0);
		assertTrue(range.compareTo(new Range<Integer>(30,40))<0);
		assertTrue(range.compareTo(null)==1);
	}
	
	/**
	 * tests the two arguments constructor
	 */
	@Test
	public void testConstructor_NullValue() {
		Integer left = null;
		Integer right = 2;
		Range<Integer> r = new Range<Integer>(left, right);		
		assertEquals(left, r.getLeft());
		assertEquals(right, r.getRight());
		
		r = new Range<Integer>(right, left);
		assertEquals(left, r.getLeft());
		assertEquals(right, r.getRight());
	}
	 
	/**
	 * test for static Range.contains().
	 */	
	@Test
	public void testSetRight_NullRightValue() {
		Integer left = 3;
		Integer right = 4;
		Range<Integer> r = new Range<Integer>(left, right);	
		r.setRight(null);
	}
	
}
