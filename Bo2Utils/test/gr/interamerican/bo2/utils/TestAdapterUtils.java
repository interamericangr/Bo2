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

import gr.interamerican.bo2.utils.adapters.Filter;
import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.beans.Tree;
import gr.interamerican.bo2.utils.comparators.SpecificNumberComparator;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.GreaterThan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link AdapterUtils}.
 * 
 * This test uses as sample adapter a Filter that filters
 * values less than or equal to zero. 
 */
public class TestAdapterUtils {
	
	/**
	 * Input args array.
	 */
	Integer[] argsArray = {-4, -8, 0, 12, null, 21, null};
	
	/**
	 * Expected results array.
	 */
	Integer[] expectedArray = {null, null, null, 12, null, 21, null};
	
	/**
	 * Comparator for the condition.
	 */
	Comparator<Integer> comparator = new SpecificNumberComparator<Integer>();
	/**
	 * Condition for the filter.
	 */
	Condition<Integer> condition = new GreaterThan<Integer>(comparator, 0);
	/**
	 * Filter used as sample adapter.
	 */
	Filter<Integer> filter = new Filter<Integer>(condition);
	
	/**
	 * Void Operation.
	 */
	VoidOperation<Object> print = new VoidOperation<Object>() {
		public void execute(Object a) {
			System.out.println(a);
		}
	};
	
	/**
	 * Test apply on a List.
	 */
	@Test
	public void apply_onList() {
		List<Integer> args = Arrays.asList(argsArray);
		List<Integer> expected = Arrays.asList(expectedArray);		
		List<Integer> actual = AdapterUtils.apply(args, filter);		
		Assert.assertEquals(expected, actual);		
	}
	
	/**
	 * Test apply on an array.
	 */
	@Test
	public void apply_onArray() {
		Integer[] actuals = AdapterUtils.apply(argsArray, new Integer[0], filter);		
		Assert.assertArrayEquals(expectedArray, actuals);		
	}
	
	/**
	 * Test apply on a Set.
	 */
	@Test
	public void apply_onSet() {
		Set<Integer> set = new HashSet<Integer>();
		set.add(-8);
		set.add(0);
		set.add(5);
		set.add(3);
		Set<Integer> result = AdapterUtils.apply(set, filter);		
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.contains(3));
		Assert.assertTrue(result.contains(5));
	}
	
	/**
	 * Test apply on a Set.
	 */
	@Test
	public void apply_onMap() {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int[] numbers = {-8, -12, 0, 5, 8};
		for (int i = 0; i < numbers.length; i++) {
			map.put(numbers[i], numbers[i]);
		}
		Map<Integer, Integer> result = AdapterUtils.apply(map, filter);
		Assert.assertEquals(numbers.length, result.size());
		Assert.assertEquals(null, result.get(-8));
		Assert.assertEquals(null, result.get(-12));
		Assert.assertEquals(null, result.get(0));
		Assert.assertEquals(new Integer(5), result.get(5));
		Assert.assertEquals(new Integer(8), result.get(8));
		
	}
	
	
	/**
	 * Test apply on a tree.
	 */
	@Test
	public void apply_onTree() {
		Tree<Integer> tree = new Tree<Integer>(10);
		tree.setName("tree"); //$NON-NLS-1$
		tree.add(1);
		tree.add(-2);
		Tree<Integer> result = AdapterUtils.apply(tree, filter);	
		Assert.assertNotNull(result.getAnyNodeOf((1)));
		Assert.assertNull(result.getAnyNodeOf(-2));	
	}
	
	
	/**
	 * Test apply on a tree.
	 */
	@Test
	public void apply_voidOnArray() {
		Object[] objects = {new Object(), new Object()};
		AdapterUtils.apply(objects, print);
	}
	
	/**
	 * Test apply on a tree.
	 */
	@Test
	public void apply_voidOnCollection() {
		Object[] objects = {new Object(), new Object()};
		List<Object> list = Arrays.asList(objects);
		AdapterUtils.apply(list, print);
	}
	
	/**
	 * Test concat.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConcat() {
		@SuppressWarnings("rawtypes")
		List<Class> list = new ArrayList<Class>();
		list.add(Integer.class);
		list.add(String.class);
		list.add(Class.class);
		String actual = AdapterUtils.concat(list, "name", Class.class, "-");
		String expected = "java.lang.Integer-java.lang.String-java.lang.Class";
		Assert.assertEquals(expected,actual);		
	}
	
	/**
	 * Test getProperty.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetProperty() {
		@SuppressWarnings("rawtypes")
		List<Class> list = new ArrayList<Class>();
		list.add(Integer.class);
		list.add(String.class);
		list.add(Class.class);
		List<String> names = AdapterUtils.getProperty(list, "name", Class.class);		
		Assert.assertEquals(3,names.size());
		for (int i = 0; i < list.size(); i++) {
			Assert.assertEquals(list.get(i).getName(), names.get(i));
		}
	}
	
	/**
	 * Test getName.
	 */
	@Test
	public void testGetName() {
		@SuppressWarnings("rawtypes")
		List<Class> list = new ArrayList<Class>();
		list.add(Integer.class);
		list.add(String.class);
		list.add(Class.class);
		List<String> names = AdapterUtils.getName(list, Class.class);		
		Assert.assertEquals(3,names.size());
		for (int i = 0; i < list.size(); i++) {
			Assert.assertEquals(list.get(i).getName(), names.get(i));
		}
	}
		
	

}
