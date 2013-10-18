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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.matching.MatchingRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Unit test for {@link CollectionUtils}.
 */
public class TestMatchingUtils {	
	
	/**
	 * Unit test for keyMatches.
	 */	
	@Test
	public void testKeyMatches() {
		Set<Long> keys = new HashSet<Long>();
		keys.add(1L);
		keys.add(2L);
		
		Map<Long, Integer> left = new HashMap<Long, Integer>();
		left.put(1L, 1);
		left.put(2L, 2);
		Map<Long, String> right = new HashMap<Long, String>();
		right.put(1L, "one"); //$NON-NLS-1$
		right.put(2L, "two"); //$NON-NLS-1$
		Map<Long, Pair<Integer, String>> map = MatchingUtils.keyMatches(keys, left, right);
		assertEquals(2, map.size());
		assertEquals(new Integer(1), map.get(1L).getLeft());
		assertEquals(new Integer(2), map.get(2L).getLeft());
		assertEquals("one", map.get(1L).getRight()); //$NON-NLS-1$
		assertEquals("two", map.get(2L).getRight()); //$NON-NLS-1$
		
		keys.clear();
		keys.add(3L);
		map = MatchingUtils.keyMatches(keys, left, right);
		assertEquals(1, map.size());
		assertNotNull(map.get(3L));
		assertNull(map.get(3L).getLeft());
		assertNull(map.get(3L).getRight());
	}
	
	/**
	 * Unit test for intersection (with Map arguments).
	 */
	@Test
	public void testIntersectionWithMaps() {
		
		Map<Long, Integer> left = new HashMap<Long, Integer>();
		left.put(1L, 1);
		left.put(2L, 2);
		Map<Long, String> right = new HashMap<Long, String>();
		right.put(2L, "two"); //$NON-NLS-1$
		right.put(3L, "three"); //$NON-NLS-1$
		
		Map<Long, Pair<Integer, String>> map = MatchingUtils.intersection(left, right);
		assertEquals(1, map.size());
		assertEquals(new Integer(2), map.get(2L).getLeft());
		assertEquals("two", map.get(2L).getRight()); //$NON-NLS-1$
	}
	
	/**
	 * Unit test for intersection (with Map arguments).
	 */
	@Test
	public void testUnionWithMaps() {
		
		Map<Long, Integer> left = new HashMap<Long, Integer>();
		left.put(1L, 1);
		left.put(2L, 2);
		Map<Long, String> right = new HashMap<Long, String>();
		right.put(2L, "two"); //$NON-NLS-1$
		right.put(3L, "three"); //$NON-NLS-1$
		
		Map<Long, Pair<Integer, String>> map = MatchingUtils.union(left, right);
		assertEquals(3, map.size());
		assertEquals(new Integer(1), map.get(1L).getLeft());
		assertEquals(null, map.get(1L).getRight());
		assertEquals(new Integer(2), map.get(2L).getLeft());
		assertEquals("two", map.get(2L).getRight()); //$NON-NLS-1$
		assertEquals(null, map.get(3L).getLeft());
		assertEquals("three", map.get(3L).getRight()); //$NON-NLS-1$
		
		//test that Pairs created from intersection of the two maps have both elements not null
		Map<Long, Pair<Integer, String>> intersectionMap = MatchingUtils.intersection(left, right);
		for (Long l : intersectionMap.keySet()) {
			assertNotNull(map.get(l).getRight());
			assertNotNull(map.get(l).getLeft());
		}
	}
	
	/**
	 * Unit test for intersection (with set arguments).
	 */
	@SuppressWarnings("nls")
	@Test
	public void testUnionWithSets() {
		
		Set<Bean1> leftSet = new HashSet<Bean1>();
		leftSet.add(new Bean1("one", "1"));
		leftSet.add(new Bean1("two", "2"));
		
		Set<Bean2> rightSet = new HashSet<Bean2>();
		rightSet.add(new Bean2("two", new Integer(2)));
		rightSet.add(new Bean2("three", new Integer(3)));
		
		Map<String, Pair<Bean1, Bean2>> map = 
			MatchingUtils.union(leftSet, Bean1.class, "field1", rightSet, Bean2.class, "field1");
		
		assertEquals(3, map.size());
		
		assertEquals(map.get("one").getLeft().getField1(), "one");
		assertEquals(map.get("one").getLeft().getField2(), "1");
		assertNull(map.get("one").getRight());
		
		assertEquals(map.get("two").getLeft().getField1(), "two");
		assertEquals(map.get("two").getLeft().getField2(), "2");
		assertEquals(map.get("two").getRight().getField1(), "two");
		assertEquals(map.get("two").getRight().getField2(), new Integer(2));
		
		assertNull(map.get("three").getLeft());
		assertEquals(map.get("three").getRight().getField1(), "three");
		assertEquals(map.get("three").getRight().getField2(), new Integer(3));
	}
	
	/**
	 * Unit test for intersection (with set arguments).
	 */
	@SuppressWarnings({ "nls", "unused" })
	@Test (expected=RuntimeException.class)
	public void testUnionWithSetsException() {
		
		Set<Bean1> leftSet = new HashSet<Bean1>();
		leftSet.add(new Bean1("one", "1"));
		
		Set<Bean2> rightSet = new HashSet<Bean2>();
		rightSet.add(new Bean2("two", new Integer(2)));
		
		Map<String, Pair<Bean1, Bean2>> map = 
			MatchingUtils.union(leftSet, Bean1.class, "field2", rightSet, Bean2.class, "field2");
	}
	
	/**
	 * Unit test for intersection (with set arguments).
	 */
	@SuppressWarnings("nls")
	@Test
	public void testIntersectionWithSets() {
		
		Set<Bean1> leftSet = new HashSet<Bean1>();
		leftSet.add(new Bean1("one", "1"));
		leftSet.add(new Bean1("two", "2"));
		
		Set<Bean2> rightSet = new HashSet<Bean2>();
		rightSet.add(new Bean2("two", new Integer(2)));
		rightSet.add(new Bean2("three", new Integer(3)));
		
		Map<String, Pair<Bean1, Bean2>> map = 
			MatchingUtils.intersection(leftSet, Bean1.class, "field1", rightSet, Bean2.class, "field1");
		
		assertEquals(1, map.size());
		
		assertEquals(map.get("two").getLeft().getField1(), "two");
		assertEquals(map.get("two").getLeft().getField2(), "2");
		assertEquals(map.get("two").getRight().getField1(), "two");
		assertEquals(map.get("two").getRight().getField2(), new Integer(2));
	}
	
	/**
	 * Unit test for intersection (with set arguments).
	 */
	@SuppressWarnings("nls")
	@Test (expected=RuntimeException.class)
	public void testIntersectionWithSetsException() {
		
		Set<Bean1> leftSet = new HashSet<Bean1>();
		leftSet.add(new Bean1("one", "1"));
		
		Set<Bean2> rightSet = new HashSet<Bean2>();
		rightSet.add(new Bean2("two", new Integer(2)));
		
		@SuppressWarnings("unused")
		Map<String, Pair<Bean1, Bean2>> map = 
			MatchingUtils.intersection(leftSet, Bean1.class, "field2", rightSet, Bean2.class, "field2");
	}
	

	
	
	/**
	 * Unit test for match
	 */	
	@SuppressWarnings("nls")
	@Test
	public void testMatch_Set_Pair() {
		Set<Integer> ints = new HashSet<Integer>();
		ints.add(1);
		ints.add(2);
		ints.add(5);
		ints.add(6);
		
		List<Pair<Integer, String>> pairs = new ArrayList<Pair<Integer,String>>();
		pairs.add(new Pair<Integer, String>(1,"x"));
		pairs.add(new Pair<Integer, String>(1,"x"));
		pairs.add(new Pair<Integer, String>(1,"z"));
		pairs.add(new Pair<Integer, String>(2,"z"));
		pairs.add(new Pair<Integer, String>(4,"f"));
		pairs.add(new Pair<Integer, String>(7,"k"));
		
		List<Pair<Integer, String>> actuals = MatchingUtils.match(ints, pairs);
		
		List<Pair<Integer, String>> expecteds = new ArrayList<Pair<Integer,String>>();
		expecteds.add(new Pair<Integer, String>(1,"x"));
		expecteds.add(new Pair<Integer, String>(1,"x"));
		expecteds.add(new Pair<Integer, String>(1,"z"));
		expecteds.add(new Pair<Integer, String>(2,"z"));
		expecteds.add(new Pair<Integer, String>(null,"f"));
		expecteds.add(new Pair<Integer, String>(null,"k"));
		expecteds.add(new Pair<Integer, String>(5,null));
		expecteds.add(new Pair<Integer, String>(6,null));
		
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));
	}
	
	/**
	 * tests CollectionUtils.match()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testMatch_SetSet() {
		Set<String> left = new HashSet<String>();
		left.add("foo");
		left.add("bar");
		
		Set<String> right = new HashSet<String>();
		right.add("testFoo");
		right.add("testFoo_1");
		right.add("testFooBar");
		
		int leftSize = left.size();
		int rightSize = right.size();
		
		MatchingRule<String, String> rule =  new TestMethodMatchingRule();
		List<Pair<String, String>> actuals = MatchingUtils.match(left, right, rule);
		
		List<Pair<String, String>> expecteds = new ArrayList<Pair<String,String>>();
		expecteds.add(new Pair<String, String>("foo", "testFoo"));
		expecteds.add(new Pair<String, String>("foo", "testFoo_1"));
		expecteds.add(new Pair<String, String>("bar", null));
		expecteds.add(new Pair<String, String>(null, "testFooBar"));
		
		assertEquals(leftSize, left.size());
		assertEquals(rightSize, right.size());
		
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));
	}
	
	/**
	 * Unit test for full join.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFullJoin() {
		String[] leftsArray = {"a", "b", "c", null};
		List<String> lefts = Arrays.asList(leftsArray);
		Integer[] rightsArray = {1, 2};
		List<Integer> rights = Arrays.asList(rightsArray);
		@SuppressWarnings("rawtypes")
		Pair[] expectedsArray = {
			new Pair<String, Integer>("a", 1),
			new Pair<String, Integer>("a", 2),
			new Pair<String, Integer>("b", 1),
			new Pair<String, Integer>("b", 2),
			new Pair<String, Integer>("c", 1),
			new Pair<String, Integer>("c", 2),
			new Pair<String, Integer>(null, 1),
			new Pair<String, Integer>(null, 2),
		};
		@SuppressWarnings("unchecked")
		List<Pair<String, Integer>> expecteds = 
			Arrays.asList((Pair<String, Integer>[])expectedsArray);
		List<Pair<String, Integer>> actuals =
			MatchingUtils.fullJoin(lefts, rights);
		assertEquals(expecteds, actuals);
	}
	
	
/////////////////////////////////////////////////////////////SAMPLES/////////////////////////////////////////////////////////////
	
	/**
	 * Matches a method to a set of test methods with the assumption that
	 * method foo is tested by testFoo and testFoo_*
	 * 
	 */
	private class TestMethodMatchingRule implements MatchingRule<String, String> {
		
		public boolean isMatch(String sample, String element) {
			String prefix1 = "test" + StringUtils.firstCapital(sample);  //$NON-NLS-1$
			String prefix2 = prefix1+StringConstants.UNDERSCORE;
			return element.equals(prefix1) || element.startsWith(prefix2);
		}
		
	}
	
	
	
	/**
	 * test sample
	 */
	private class Bean1 {
		
		/**
		 * field1
		 */
		String field1;
		
		/**
		 * field2
		 */
		String field2;
		
		/**
		 * Creates a new CollectionUtilsTest.Bean1 object. 
		 * @param f1 
		 * @param f2 
		 *
		 */
		public Bean1(String f1, String f2) {
			this.field1 = f1;
			this.field2 = f2;
		}
		
		/**
		 * Gets the field1.
		 *
		 * @return Returns the field1
		 */
		public String getField1() {
			return field1;
		}

		/**
		 * Assigns a new value to the field1.
		 *
		 * @param field1 the field1 to set
		 */
		@SuppressWarnings("unused")
		public void setField1(String field1) {
			this.field1 = field1;
		}

		/**
		 * Gets the field2.
		 *
		 * @return Returns the field2
		 */
		public String getField2() {
			return field2;
		}

		/**
		 * Assigns a new value to the field2.
		 *
		 * @param field2 the field2 to set
		 */
		@SuppressWarnings("unused")
		public void setField2(String field2) {
			this.field2 = field2;
		}


	}
	/**
	 * test sample
	 */
	private class Bean2 {

		/**
		 * field1
		 */
		String field1;
		
		/**
		 * field2
		 */
		Integer field2;
		
		/**
		 * Creates a new CollectionUtilsTest.Bean1 object. 
		 * @param f1 
		 * @param f2 
		 *
		 */
		public Bean2(String f1, Integer f2) {
			this.field1 = f1;
			this.field2 = f2;
		}
		
		/**
		 * Gets the field1.
		 *
		 * @return Returns the field1
		 */
		public String getField1() {
			return field1;
		}

		/**
		 * Assigns a new value to the field1.
		 *
		 * @param field1 the field1 to set
		 */
		@SuppressWarnings("unused")
		public void setField1(String field1) {
			this.field1 = field1;
		}

		/**
		 * Gets the field2.
		 *
		 * @return Returns the field2
		 */
		public Integer getField2() {
			return field2;
		}

		/**
		 * Assigns a new value to the field2.
		 *
		 * @param field2 the field2 to set
		 */
		@SuppressWarnings("unused")
		public void setField2(Integer field2) {
			this.field2 = field2;
		}

	}

}
