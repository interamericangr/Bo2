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
package gr.interamerican.bo2.impl.open.po;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * unit tests for {@link AbstractKey}.
 */
public class TestAbstractKey {
	
	
	/**
	 * Tests that the getFieldsCopy() method returns
	 * a copy of the getFields() but not the same array.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetFieldsCopy() {
		Object[] objects = {1, 2, "this", "that"};
		
		Impl o1 = new Impl();
		o1.setFields(objects);
		Object[] copy = o1.getElementsCopy();
		
		assertArrayEquals(objects, copy);
		assertSame(o1.getElements(), objects);
		assertNotSame(o1.getElements(), copy);
		
	}
	
	/**
	 * Tests that the getFieldsCopy() method does not return
	 * null, even if the fields array is null.
	 */
	@Test	
	public void testGetFieldsCopyNeverReturnsNull() {
		Impl o1 = new Impl();
		o1.setFields(null);
		assertNotNull(o1.getElementsCopy());
	}
	
	
	/**
	 * Tests that equals works for the same object.
	 */
	@Test
	public void testCompareSameObject() {
		Impl o1 = new Impl();
		assertTrue(o1.compareTo(o1)==0);
		assertTrue(o1.equals(o1));
	}
	
	
	/**
	 * Tests that equals works for the same array.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCompareSameArray() {
		Object[] objects = {1, 2, "this", "that"};
		
		Impl o1 = new Impl();
		o1.setFields(objects);
		Impl o2 = new Impl();
		o2.setFields(objects);
		
		assertTrue(o1.equals(o2));
		assertTrue(o1.compareTo(o2)==0);		
		assertTrue(o2.equals(o1));
		assertTrue(o2.compareTo(o1)==0);		
		assertEquals(o1.hashCode(), o2.hashCode());
	}
	
	/**
	 * Tests that equals works for the equal arrays.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCompareEqualArrays() {
		Object[] objects1 = {1, 2, "this", "that"};
		Object[] objects2 = {1, 2, "this", "that"};
		
		Impl o1 = new Impl();
		o1.setFields(objects1);
		Impl o2 = new Impl();
		o2.setFields(objects2);
		
		assertTrue(o1.equals(o2));
		assertTrue(o1.compareTo(o2)==0);		
		assertTrue(o2.equals(o1));
		assertTrue(o2.compareTo(o1)==0);		
		assertEquals(o1.hashCode(), o2.hashCode());	}
	
	/**
	 * Tests that arrays with more objects are greater than 
	 * arrays with less when the rest elements are the same.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCompareSameElementsDifferentSize() {
		Object[] objects1 = {1, 2, "this", null};
		Object[] objects2 = {1, 2, "this"};
		
		Impl o1 = new Impl();
		o1.setFields(objects1);
		Impl o2 = new Impl();
		o2.setFields(objects2);
		
		assertTrue(o1.compareTo(o2)>0);
		assertTrue(o2.compareTo(o1)<0);
	}
	

	/**
	 * Tests the comparison for arrays with the same size.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCompareSameSize() {
		Object[] objects1 = {1, 3, "this"};
		Object[] objects2 = {1, 2, "this"};
		
		Impl o1 = new Impl();
		o1.setFields(objects1);
		Impl o2 = new Impl();
		o2.setFields(objects2);
		
		assertTrue(o1.compareTo(o2)>0);
		assertTrue(o2.compareTo(o1)<0);
	}
	
	/**
	 * Tests the comparison for arrays with the same size.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCompareDifferentSize() {
		Object[] objects1 = {1, 3, "this"};
		Object[] objects2 = {1, 2, "this", "none"};
		
		Impl o1 = new Impl();
		o1.setFields(objects1);
		Impl o2 = new Impl();
		o2.setFields(objects2);
		
		assertTrue(o1.compareTo(o2)>0);
		assertTrue(o2.compareTo(o1)<0);
	}
	
	
	
	
	
	/**
	 * Implementation of AbstractKeyImpl for the tests.
	 */
	private static class Impl extends AbstractKey {		
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * elements.
		 */
		private Object[] elements;
		
		/**
		 * Sets the elements.
		 * @param elements
		 */
		public void setFields(Object[] elements) {
			this.elements = elements;
		}
		
		@Override
		public Object[] getElements() {
			return elements;
		}
		
	}

}
