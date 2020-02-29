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

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class TestRange.
 */
public class TestRange {
	/**
	 * Instance being tested.
	 */
	private Range<Integer> r10to20 = new Range<Integer>(10,20);
	
	/**
	 * test for contains().
	 */
	@Test
	public void testContains() {
		assertFalse(r10to20.contains(-15));
		assertTrue(r10to20.contains(10));
		assertTrue(r10to20.contains(15));
		assertTrue(r10to20.contains(20));
		assertFalse(r10to20.contains(45));
	}
	
	/**
	 * test for overlasWith(range).
	 */
	@Test
	public void testOverlapsWith_single() {
		assertFalse(r10to20.overlapsWith(new Range<Integer>(0,5)));
		assertTrue(r10to20.overlapsWith(new Range<Integer>(0,10)));
		assertTrue(r10to20.overlapsWith(new Range<Integer>(0,15)));
		assertTrue(r10to20.overlapsWith(new Range<Integer>(10,20)));
		assertTrue(r10to20.overlapsWith(new Range<Integer>(12,15)));
		assertTrue(r10to20.overlapsWith(new Range<Integer>(12,25)));
		assertTrue(r10to20.overlapsWith(new Range<Integer>(20,30)));
		assertFalse(r10to20.overlapsWith(new Range<Integer>(30,40)));
	}
	
	/**
	 * test for overlapsWith(collection).
	 */
	@Test
	public void testOverlapsWith_OverlappingArray() {
		List<Range<Integer>> list = new ArrayList<Range<Integer>>();
		list.add(new Range<Integer>(0,5));
		list.add(new Range<Integer>(18,25));
		assertTrue(r10to20.overlapsWith(list));
	}
	
	/**
	 * test for overlapsWith(collection).
	 */
	@Test
	public void testOverlapsWith_NonOverlappingArray() {
		List<Range<Integer>> list = new ArrayList<Range<Integer>>();
		list.add(new Range<Integer>(0,5));
		list.add(new Range<Integer>(58,65));
		assertFalse(r10to20.overlapsWith(list));
	}

	
	/**
	 * test for static Range.contains().
	 */
	@Test
	public void testStaticContains() {
		assertTrue(Range.contains(r10to20, 15));
	}
	
	/**
	 * test for static Range.contains().
	 */
	@Test(expected=ClassCastException.class)
	public void testStaticContains_WithClassCastExceptionForLong() {
		Range.contains(r10to20, 1L);
	}
	
	/**
	 * test for static Range.contains().
	 */
	@Test(expected=ClassCastException.class)
	public void testStaticContains_WithClassCastExceptionForString() {
		Range.contains(r10to20, "this"); //$NON-NLS-1$
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
	 * tests the two arguments constructor.
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
	 * Unit test for compareTo.
	 */
	@Test
	public void testCompareTo() {		
		assertTrue(r10to20.compareTo(new Range<Integer>(0,5))>0);
		assertTrue(r10to20.compareTo(new Range<Integer>(0,10))>0);
		assertTrue(r10to20.compareTo(new Range<Integer>(0,15))>0);
		assertTrue(r10to20.compareTo(new Range<Integer>(0,30))>0);
		assertTrue(r10to20.compareTo(new Range<Integer>(10,20))==0);
		assertTrue(r10to20.compareTo(new Range<Integer>(12,15))<0);
		assertTrue(r10to20.compareTo(new Range<Integer>(12,25))<0);
		assertTrue(r10to20.compareTo(new Range<Integer>(20,30))<0);
		assertTrue(r10to20.compareTo(new Range<Integer>(30,40))<0);
		assertTrue(r10to20.compareTo(null)==1);
	}
	
	/**
	 * tests the two arguments constructor.
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
	
	/**
	 * test for static Range.contains().
	 */	
	@SuppressWarnings("rawtypes")
	@Test
	public void testRange() {
		Integer left = 3;
		Integer right = 4;
		Range r = Range.range(left, right);
		Assert.assertNotNull(r);
		Assert.assertEquals(left, r.getLeft());
		Assert.assertEquals(right, r.getRight());
	}
	
	/**
	 * test for intersection.
	 */
	@Test
	public void testIntersection_left() {
		Range<Integer> r1020 = new Range<Integer>(10,20);		
		Range<Integer> r0515 = new Range<Integer>(5,15);
		Range<Integer> expected = new Range<Integer>(10,15);
		Range<Integer> actual = r1020.intersection(r0515);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * test for intersection.
	 */
	@Test
	public void testIntersection_right() {
		Range<Integer> r1020 = new Range<Integer>(10,20);		
		Range<Integer> r1525 = new Range<Integer>(15,25);
		Range<Integer> expected = new Range<Integer>(15,20);
		Range<Integer> actual = r1020.intersection(r1525);
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * test for intersection.
	 */
	@Test
	public void testIntersection_inside() {
		Range<Integer> r1020 = new Range<Integer>(10,20);		
		Range<Integer> r1518 = new Range<Integer>(15,18);		
		Range<Integer> actual = r1020.intersection(r1518);
		Assert.assertEquals(r1518, actual);
	}
	
	/**
	 * test for intersection.
	 */
	@Test
	public void testIntersection_null() {
		Range<Integer> r1020 = new Range<Integer>(10,20);		
		Range<Integer> r2130 = new Range<Integer>(21,30);		
		Range<Integer> actual = r1020.intersection(r2130);
		Assert.assertNull(actual);
	}
	
	/**
	 * test for intersection.
	 */
	@Test
	public void testIntersection_rightlimit() {
		Range<Integer> r1020 = new Range<Integer>(10,20);		
		Range<Integer> r2030 = new Range<Integer>(20,30);	
		Range<Integer> r20 = new Range<Integer>(20,20);	
		Range<Integer> actual = r1020.intersection(r2030);
		Assert.assertEquals(r20, actual);
	}
	
	/**
	 * test for intersection.
	 */
	@Test
	public void testIntersection_leftlimit() {
		Range<Integer> r1020 = new Range<Integer>(10,20);		
		Range<Integer> r0510 = new Range<Integer>(5,10);	
		Range<Integer> r10 = new Range<Integer>(10,10);	
		Range<Integer> actual = r1020.intersection(r0510);
		Assert.assertEquals(r10, actual);
	}
	
	/**
	 * test for intersection.
	 */
	@Test
	public void testIntersection_equal() {
		Range<Integer> r1020 = new Range<Integer>(10,20);
		Range<Integer> actual = r1020.intersection(r1020);
		Assert.assertEquals(r1020, actual);
	}
	
	/**
	 * test for remainder.
	 */
	@Test
	public void testRemainder_noIntersection() {
		Range<Integer> r1020 = new Range<Integer>(10,20);
		Range<Integer> r3040  = new Range<Integer>(30,40);
		List<Range<Integer>> remainder = r1020.remainder(r3040);
		Assert.assertEquals(1, remainder.size());
		Assert.assertEquals(r1020, remainder.get(0));
	}
	
	/**
	 * test for remainder.
	 */
	@Test
	public void testRemainder_intersectOnLimit() {
		Range<Integer> r1020 = new Range<Integer>(10,20);
		Range<Integer> r2040  = new Range<Integer>(20,40);
		List<Range<Integer>> remainder = r1020.remainder(r2040);
		Assert.assertEquals(1, remainder.size());
		Assert.assertEquals(r1020, remainder.get(0));
	}
	
	/**
	 * test for remainder.
	 */
	@Test
	public void testRemainder_point() {
		Range<Integer> r1020 = new Range<Integer>(10,20);
		Range<Integer> r15  = new Range<Integer>(15,15);
		List<Range<Integer>> remainder = r1020.remainder(r15);
		Assert.assertEquals(r1020, remainder.get(0));
	}

	
	/**
	 * test for remainder.
	 */
	@Test
	public void testRemainder_equal() {
		Range<Integer> r1020 = new Range<Integer>(10,20);		
		List<Range<Integer>> remainder = r1020.remainder(r1020);
		Assert.assertEquals(0, remainder.size());		
	}
	
	/**
	 * test for remainder.
	 */
	@Test
	public void testRemainder_contained() {
		Range<Integer> r1020 = new Range<Integer>(10,20);
		Range<Integer> r0540  = new Range<Integer>(5,40);
		List<Range<Integer>> remainder = r1020.remainder(r0540);
		Assert.assertEquals(0, remainder.size());		
	}
	
	/**
	 * test for remainder.
	 */
	@Test
	public void testRemainder_leftIntersection() {
		Range<Integer> r1020 = new Range<Integer>(10,20);
		Range<Integer> r0515  = new Range<Integer>(05,15);
		List<Range<Integer>> remainder = r1020.remainder(r0515);
		Assert.assertEquals(1, remainder.size());
		Assert.assertEquals(new Range<Integer>(15,20), remainder.get(0));
	}
	
	/**
	 * test for remainder.
	 */
	@Test
	public void testRemainder_rightIntersection() {
		Range<Integer> r1020 = new Range<Integer>(10,20);
		Range<Integer> r1525  = new Range<Integer>(15,25);
		List<Range<Integer>> remainder = r1020.remainder(r1525);
		Assert.assertEquals(1, remainder.size());
		Assert.assertEquals(new Range<Integer>(10,15), remainder.get(0));
	}
	
	/**
	 * test for remainder.
	 */
	@Test
	public void testRemainder_middleIntersection() {
		Range<Integer> r1020 = new Range<Integer>(10,20);
		Range<Integer> r1416  = new Range<Integer>(14,16);
		List<Range<Integer>> remainder = r1020.remainder(r1416);
		Assert.assertEquals(2, remainder.size());
		Assert.assertEquals(new Range<Integer>(10,14), remainder.get(0));
		Assert.assertEquals(new Range<Integer>(16,20), remainder.get(1));
	}
	
	
	
	
}
