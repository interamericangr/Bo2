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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MultipleValuesMap}.
 * 
 */
@SuppressWarnings("nls")
public class TestMultipleValuesMap {
	
	/**
	 * Test put.
	 */
	@Test
	public void testPut() {
		MultipleValuesMap<String, Integer> map = new MultipleValuesMap<String, Integer>();
		map.put("s", 1);
		Assert.assertEquals(1, map.map.size());
		Set<Integer> s1 = map.map.get("s");
		Assert.assertEquals(1, s1.size());
		Assert.assertTrue(s1.contains(1));

		map.put("s", 2);
		Assert.assertEquals(1, map.map.size());
		Set<Integer> s2 = map.map.get("s");
		Assert.assertEquals(2, s2.size());
		Assert.assertTrue(s2.contains(1));
		Assert.assertTrue(s2.contains(2));
		
		map.put("t", 2);
		Assert.assertEquals(2, map.map.size());
		Set<Integer> t = map.map.get("t");
		Assert.assertEquals(2, s2.size());
		Assert.assertTrue(t.contains(2));		
	}
	
	/**
	 * Test get.
	 */
	@Test
	public void testGet() {
		MultipleValuesMap<String, Integer> map = new MultipleValuesMap<String, Integer>();
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		set.add(2);
		map.map.put("s", set);
		Set<Integer> s = map.get("s");
		Assert.assertTrue(s.contains(1));
		Assert.assertTrue(s.contains(2));
		
		Assert.assertNull(map.get("foo"));		
	}
	
	/**
	 * Test get.
	 */
	@Test
	public void testContainsKey() {
		MultipleValuesMap<String, Integer> map = new MultipleValuesMap<String, Integer>();
		String k = "k";
		Integer v = 1;
		Assert.assertFalse(map.containsKey(k));		
		map.put(k, v);
		Assert.assertTrue(map.containsKey(k));		
	}
	
	/**
	 * Test get.
	 */
	@Test
	public void testContainsValue() {
		MultipleValuesMap<String, Integer> map = new MultipleValuesMap<String, Integer>();
		String k = "k";
		Integer v = 1;				
		map.put(k, v);
		Assert.assertTrue(map.containsValue(k, 1));		
		Assert.assertFalse(map.containsValue(k, 2));
		Assert.assertFalse(map.containsValue("foo", 1));
	}
	
	/**
	 * Test get.
	 */
	@Test
	public void testKeySet() {
		MultipleValuesMap<String, Integer> map = new MultipleValuesMap<String, Integer>();
		map.put("s", 1);
		map.put("t", 2);
		map.put("r", 3);
		Set<String> keyset = map.keySet();
		Assert.assertEquals(map.map.keySet(), keyset);
	}
	
	/**
	 * Test size.
	 */
	@Test
	public void testSize() {
		MultipleValuesMap<String, Integer> map = new MultipleValuesMap<String, Integer>();
		Assert.assertEquals(0,map.size());		
		map.put("s", 1);
		map.put("t", 2);
		Assert.assertEquals(2,map.size());
	}
	
	
	/**
	 * Test size.
	 */
	@Test
	public void testSize_withKey() {
		MultipleValuesMap<String, Integer> map = new MultipleValuesMap<String, Integer>();
		String one = "one";
		String two = "two";
		Assert.assertEquals(0,map.size(one));		
		Assert.assertEquals(0,map.size(two));
		map.put(one, 1);
		map.put(one, 10);
		map.put(one, 100);		
		map.put(two, 1);
		Assert.assertEquals(3,map.size(one));
		Assert.assertEquals(1,map.size(two));
	}
	
	/**
	 * Test size.
	 */
	@Test
	public void testIterator() {
		MultipleValuesMap<String, Integer> map = new MultipleValuesMap<String, Integer>();
		Iterator<Pair<String, Set<Integer>>> iterator = map.iterator();
		Assert.assertNotNull(iterator);
		Assert.assertFalse(iterator.hasNext());		
	}
	
	
	/**
	 * Test size.
	 */
	@Test
	public void testRemove_value() {
		MultipleValuesMap<String, Integer> map = new MultipleValuesMap<String, Integer>();
		String one = "one";
		map.put(one, 1);
		map.put(one, 10);
		map.put(one, 100);		
		boolean b1 = map.remove(one, 10);		
		Assert.assertTrue(map.containsValue(one, 1));
		Assert.assertFalse(map.containsValue(one, 10));		
		Assert.assertTrue(b1);
		boolean b2 = map.remove(one, 5);
		Assert.assertFalse(b2);
		boolean b3 = map.remove("two", 1);
		Assert.assertFalse(b3);
	}
	
	/**
	 * Test size.
	 */
	@Test
	public void testRemove() {
		MultipleValuesMap<String, Integer> map = new MultipleValuesMap<String, Integer>();
		String one = "one";
		map.put(one, 1);
		map.put(one, 10);	
		boolean b1 = map.remove(one);
		Assert.assertFalse(map.containsKey(one));
		Assert.assertTrue(b1);
		
		boolean b2 = map.remove("two");
		Assert.assertFalse(b2);
	}

}
