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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.bean.BeanWith3Fields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Utils}.
 * 
 */
public class TestUtils {

	/**
	 * Tests notNull(v,dv)
	 */
	@Test
	public void testNotNull() {
		Long val = 2L;
		Long defVal = 4L;

		assertEquals(val, Utils.notNull(val, defVal));
		assertEquals(defVal, Utils.notNull(null, defVal));
	}
	
	/**
	 * Tests notNull(v,dv)
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testIsNullOrEmpty() {
		Object object = null;
		assertTrue(Utils.isNullOrEmpty(object));
		
		object = new Object();
		assertFalse(Utils.isNullOrEmpty(object));
		
		object = StringConstants.EMPTY;
		assertTrue(Utils.isNullOrEmpty(object));
		
		object = StringConstants.COLON;
		assertFalse(Utils.isNullOrEmpty(object));
		
		object = new ArrayList<Object>();
		assertTrue(Utils.isNullOrEmpty(object));
		
		((ArrayList<Object>) object).add(new Object());
		assertFalse(Utils.isNullOrEmpty(object));
		
		object = new Object[0];
		assertTrue(Utils.isNullOrEmpty(object));
		
		object = new Object[1];
		assertFalse(Utils.isNullOrEmpty(object));
	}

	/**
	 * Utils.equals()
	 */
	@Test
	public void testEquals() {		
		Long one = new Long(1);
		Long two = new Long(2);
		Object three = new Object();
		
		assertTrue(Utils.equals(one, 1L));
		assertTrue(Utils.equals(null, null));
		assertFalse(Utils.equals(one, two));
		assertFalse(Utils.equals(one, null));
		assertFalse(Utils.equals(null, one));
		assertFalse(Utils.equals(one, three));
		assertTrue(Utils.equals(1L, 1L));
		
	}
	
	/**
	 * Tests Utils.same()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSame() {
		BeanWith3Fields left = new BeanWith3Fields();
		BeanWith3Fields right = new BeanWith3Fields();
		assertTrue(Utils.same(left, right));
		left.setField1("this");
		assertTrue(Utils.same(left, right, "field1"));
		assertFalse(Utils.same(left, right));
		right.setField1("this");
		assertTrue(Utils.same(left, right));
		left.setField2(4);
		left.setField3(6.0);
		right.setField2(4);
		right.setField3(6.0);
		assertTrue(Utils.same(left, right));
		assertTrue(Utils.same(left, right, "field1"));
	}
	
	/**
	 * Tests Utils.same()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSame_nullLeft() {
		BeanWith3Fields left = null;
		BeanWith3Fields right = new BeanWith3Fields();
		assertFalse(Utils.same(left, right,"field1"));
	}
	
	
	
	/**
	 * Utils.equals()
	 */
	@Test
	@SuppressWarnings("nls")
	public void testEquals_ForIterables() {		
		Object[] array1 = {1L, 2, 3.0, "four", 'c'};
		Object[] array2 = {1L, 2, 3.0, "four", 'c'};		
		assertTrue(Utils.equals(array1, array2));
	}
	
	/**
	 * 
	 */
	@Test
	public void testNullSafeCompare() {
		assertTrue(Utils.nullSafeCompare(null, null)==0);
		assertTrue(Utils.nullSafeCompare(null, 0)<0);
		assertTrue(Utils.nullSafeCompare(1, 0)>0);
		assertTrue(Utils.nullSafeCompare(1, null)>0);
		assertTrue(Utils.nullSafeCompare(1, 1)==0);		
	}

	
	/**
	 * tests generateHashCode
	 */
	@Test
	public void testGenerateHashCode(){
		Integer [] arr = {1,2,3};
		Integer actual = Utils.generateHashCode(arr);
		assertTrue(actual>0);
	}
	
	/**
	 * Tests alike
	 */
	@Test
	@SuppressWarnings("nls")
	public void testAlike_sets_true() {
		String[] fields = {"field1", "field2", "field3"};
		BeanWith3Fields a1 = new BeanWith3Fields("one", 1, 1.0);
		BeanWith3Fields a2 = new BeanWith3Fields("two", 2, 2.0);
		BeanWith3Fields a3 = new BeanWith3Fields("three", 3, 3.0);
		Set<BeanWith3Fields> left = new HashSet<BeanWith3Fields>();
		left.add(a1);
		left.add(a2);
		left.add(a3);
		
		BeanWith3Fields b1 = new BeanWith3Fields("one", 1, 1.0);
		BeanWith3Fields b2 = new BeanWith3Fields("two", 2, 2.0);
		BeanWith3Fields b3 = new BeanWith3Fields("three", 3, 3.0);
		Set<BeanWith3Fields> right = new HashSet<BeanWith3Fields>();
		right.add(b1);
		right.add(b2);
		right.add(b3);
		
		
		boolean res = Utils.alike(left, right, BeanWith3Fields.class, "field1", fields);
		Assert.assertTrue(res);		
	}
	
	/**
	 * Tests alike
	 */
	@Test
	@SuppressWarnings("nls")
	public void testAlike_sets_differentCount() {
		String[] fields = {"field1", "field2", "field3"};
		BeanWith3Fields a1 = new BeanWith3Fields("one", 1, 1.0);
		BeanWith3Fields a2 = new BeanWith3Fields("two", 2, 2.0);		
		Set<BeanWith3Fields> left = new HashSet<BeanWith3Fields>();
		left.add(a1);
		left.add(a2);
		
		BeanWith3Fields b1 = new BeanWith3Fields("one", 1, 1.0);
		BeanWith3Fields b2 = new BeanWith3Fields("two", 2, 2.0);
		BeanWith3Fields b3 = new BeanWith3Fields("three", 3, 3.0);
		Set<BeanWith3Fields> right = new HashSet<BeanWith3Fields>();
		right.add(b1);
		right.add(b2);
		right.add(b3);
		
		boolean res = Utils.alike(left, right, BeanWith3Fields.class, "field1", fields);
		Assert.assertFalse(res);		
	}
	
	/**
	 * Tests alike
	 */
	@Test
	@SuppressWarnings("nls")
	public void testAlike_sets_notAllMatching() {
		String[] fields = {"field1", "field2", "field3"};
		BeanWith3Fields a1 = new BeanWith3Fields("one", 1, 1.0);
		BeanWith3Fields a2 = new BeanWith3Fields("two", 2, 2.0);
		BeanWith3Fields a3 = new BeanWith3Fields("four", 4, 4.0);
		Set<BeanWith3Fields> left = new HashSet<BeanWith3Fields>();
		left.add(a1);
		left.add(a2);
		left.add(a3);
		
		BeanWith3Fields b1 = new BeanWith3Fields("one", 1, 1.0);
		BeanWith3Fields b2 = new BeanWith3Fields("two", 2, 2.0);
		BeanWith3Fields b3 = new BeanWith3Fields("three", 3, 3.0);
		Set<BeanWith3Fields> right = new HashSet<BeanWith3Fields>();
		right.add(b1);
		right.add(b2);
		right.add(b3);
		
		boolean res = Utils.alike(left, right, BeanWith3Fields.class, "field1", fields);
		Assert.assertFalse(res);		
	}
	
	/**
	 * Tests alike
	 */		
	@Test
	@SuppressWarnings("nls")
	public void testAlike_sets_notAllAlike() {
		String[] fields = {"field1", "field2", "field3"};
		BeanWith3Fields a1 = new BeanWith3Fields("one", 1, 1.0);
		BeanWith3Fields a2 = new BeanWith3Fields("two", 2, 2.0);
		BeanWith3Fields a3 = new BeanWith3Fields("four", 4, 4.0);
		Set<BeanWith3Fields> left = new HashSet<BeanWith3Fields>();
		left.add(a1);
		left.add(a2);
		left.add(a3);
		
		BeanWith3Fields b1 = new BeanWith3Fields("one", 1, 1.0);
		BeanWith3Fields b2 = new BeanWith3Fields("two", 2, 2.0);
		BeanWith3Fields b3 = new BeanWith3Fields("four", 3, 3.0);
		Set<BeanWith3Fields> right = new HashSet<BeanWith3Fields>();
		right.add(b1);
		right.add(b2);
		right.add(b3);
		
		boolean res = Utils.alike(left, right, BeanWith3Fields.class, "field1", fields);
		Assert.assertFalse(res);		
	}
	
	
	/**
	 * Tests alike
	 */		
	@SuppressWarnings("nls")
	@Test
	public void testAlike_nullLeft() {
		BeanWith3Fields left = null;
		BeanWith3Fields right = new BeanWith3Fields();
		assertFalse(Utils.alike(left, right,"field1"));
	}
	
	/**
	 * Tests alike
	 */		
	@SuppressWarnings("nls")
	@Test
	public void testAlike_sets_nullLeft() {
		String[] fields = {"field1", "field2", "field3"};
		Set<BeanWith3Fields> left = null;
		Set<BeanWith3Fields> right = new HashSet<BeanWith3Fields>();
		assertFalse(Utils.alike(left, right, BeanWith3Fields.class, "field1", fields));
	}
	
	/**
	 * Tests equals
	 */		
	@Test
	public void testIterable() {
		Iterable<Integer> iterOne = Arrays.asList(1,2);
		Iterable<Integer> iterTwo = Arrays.asList(1,2);
		assertTrue(Utils.equals(iterOne, iterTwo));
	}

}

	


