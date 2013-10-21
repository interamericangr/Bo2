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
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MultipleValuesMap}.
 * 
 */
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
	

}
