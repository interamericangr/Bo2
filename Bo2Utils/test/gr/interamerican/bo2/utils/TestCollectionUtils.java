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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.bo2.samples.bean.BeanWithShort;
import gr.interamerican.bo2.samples.bean.NumberBean;
import gr.interamerican.bo2.utils.beans.AssociationTable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link CollectionUtils}.
 */
public class TestCollectionUtils {

	/**
	 * Tests putPropertiesToMap().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testPutPropertiesToMap() {
		Properties p1 = new Properties();
		p1.setProperty("a", "A");
		p1.setProperty("b", "B");

		Properties p2 = new Properties();
		p2.setProperty("c", "C");
		p2.setProperty("b", "B1");

		Map<String, String> map = new HashMap<String, String>();

		CollectionUtils.putPropertiesToMap(p1, map);
		String a = map.get("a");
		assertEquals("A", a);
		String b = map.get("b");
		assertEquals("B", b);

		CollectionUtils.putPropertiesToMap(p2, map);
		String aa = map.get("a");
		assertEquals("A", aa);
		String bb = map.get("b");
		assertEquals("B1", bb);
		String cc = map.get("c");
		assertEquals("C", cc);

	}

	/**
	 * Tests putPropertiesToMap().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testPutPropertiesToAssociationTable() {
		Properties p1 = new Properties();
		p1.setProperty("a", "A");
		p1.setProperty("b", "B");

		Properties p2 = new Properties();
		p2.setProperty("c", "C");
		p2.setProperty("b", "B1");

		AssociationTable<String, String> table = new AssociationTable<String, String>();

		CollectionUtils.putPropertiesToAssociationTable(p1, table);
		String a = table.getRight("a");
		assertEquals("A", a);
		String b = table.getLeft("B");
		assertEquals("b", b);

		CollectionUtils.putPropertiesToAssociationTable(p2, table);
		String aa = table.getRight("a");
		assertEquals("A", aa);
		String bb = table.getRight("b");
		assertEquals("B1", bb);
		String cc = table.getRight("c");
		assertEquals("C", cc);
	}

	/**
	 * Unit test for reArrange
	 */
	@Test
	public void testReArrange() {
		Object ob1 = new Object();
		Object ob2 = new Object();
		Set<Object> set = new HashSet<Object>();
		set.add(ob1);
		set.add(ob2);
		CollectionUtils.reArrange(set);
		assertTrue(set.contains(ob1));
		assertTrue(set.contains(ob2));
	}

	/**
	 * Unit test for containsNull
	 */
	@Test
	public void testContainsNull() {
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(new Object());
		assertFalse(CollectionUtils.containsNull(list));
		list.add(null);
		assertTrue(CollectionUtils.containsNull(list));

		Set<Object> set = new HashSet<Object>();
		set.add(new Object());
		assertFalse(CollectionUtils.containsNull(set));
		set.add(null);
		assertTrue(CollectionUtils.containsNull(set));
	}

	/**
	 * Unit test for modify
	 */
	@SuppressWarnings("nls")
	@Test
	public void testModify() {
		Map<String, String> map = new HashMap<String, String>();
		String key = "key";
		map.put(key, "old");
		String noKey = "nokey";
		String newValue = "the new value";
		CollectionUtils.modify(map, noKey, newValue);
		assertFalse(map.containsKey(noKey));

		CollectionUtils.modify(map, key, newValue);
		String actual = map.get(key);
		assertEquals(newValue, actual);
	}

	/**
	 * Unit test for addNextI
	 */
	@Test
	public void testAddNextI() {
		String property = "third"; //$NON-NLS-1$
		Set<BeanWithOrderedFields> set = new HashSet<BeanWithOrderedFields>();

		for (int i = 1; i < 5; i++) {
			BeanWithOrderedFields b = new BeanWithOrderedFields();
			b.setThird(null);
			CollectionUtils.addNextI(set, b, property);
			assertEquals(set.size(), i);
			assertTrue(set.contains(b));
			assertEquals(new Integer(i), b.getThird());
		}
		BeanWithOrderedFields b = new BeanWithOrderedFields();
		b.setThird(15);
		CollectionUtils.addNextI(set, b, property);
		assertEquals(set.size(), 5);
		assertTrue(set.contains(b));
		assertEquals(new Integer(15), b.getThird());
	}

	/**
	 * Unit test for addNextL
	 */
	@Test
	public void testAddNextL() {
		String property = "fourth"; //$NON-NLS-1$
		Set<BeanWithOrderedFields> set = new HashSet<BeanWithOrderedFields>();

		for (int i = 1; i < 5; i++) {
			BeanWithOrderedFields b = new BeanWithOrderedFields();
			b.setFourth(null);
			CollectionUtils.addNextL(set, b, property);
			assertEquals(set.size(), i);
			assertTrue(set.contains(b));
			assertEquals(new Long(i), b.getFourth());
		}
		BeanWithOrderedFields b = new BeanWithOrderedFields();
		b.setFourth(15L);
		CollectionUtils.addNextL(set, b, property);
		assertEquals(set.size(), 5);
		assertTrue(set.contains(b));
		assertEquals(new Long(15), b.getFourth());
	}

	/**
	 * Unit test for addNextS
	 */
	@Test
	public void testAddNextS() {
		String property = "first"; //$NON-NLS-1$
		Set<BeanWithShort> set = new HashSet<BeanWithShort>();

		for (int i = 1; i < 5; i++) {
			BeanWithShort b = new BeanWithShort();
			b.setFirst(null);
			CollectionUtils.addNextS(set, b, property);
			assertEquals(set.size(), i);
			assertTrue(set.contains(b));
			assertEquals(new Short((short) i), b.getFirst());
		}
		BeanWithShort b = new BeanWithShort();
		b.setFirst((short) 15);
		CollectionUtils.addNextS(set, b, property);
		assertEquals(set.size(), 5);
		assertTrue(set.contains(b));
		assertEquals(new Short((short) 15), b.getFirst());
	}
	
	/**
	 * Test ��� ��� addNextLs.
	 */
	@Test
	public void testAddNextLs() {
		String property = "fourth"; //$NON-NLS-1$
		List<BeanWithOrderedFields> elementsToAdd = new ArrayList<BeanWithOrderedFields>();
		Set<BeanWithOrderedFields> collection = new HashSet<BeanWithOrderedFields>();
		
		BeanWithOrderedFields bwof = new BeanWithOrderedFields();
		bwof.setFourth(1L);
		collection.add(bwof);
		
		for(int i=0; i<4; i++) {
			elementsToAdd.add(new BeanWithOrderedFields());
		}
		
		CollectionUtils.addNextLs(collection, elementsToAdd, property);
		assertEquals(5, collection.size());
		
		Set<Long> ids = new HashSet<Long>();
		ids.add(1L);
		ids.add(2L);
		ids.add(3L);
		ids.add(4L);
		ids.add(5L);
		for (BeanWithOrderedFields bean : collection) {
		      assertTrue(ids.contains(bean.getFourth()));
		      ids.remove(bean.getFourth());
		}
		assertEquals(0, ids.size());
	}

	/**
	 * Unit test for toMap.
	 */
	@Test
	public void testToMap() {
		String property = "third"; //$NON-NLS-1$

		BeanWithOrderedFields beans[] = new BeanWithOrderedFields[5];
		for (int i = 0; i < 5; i++) {
			beans[i] = new BeanWithOrderedFields();
			beans[i].setThird(Integer.valueOf(i));
		}
		Map<Object, BeanWithOrderedFields> map = CollectionUtils.toMap(Arrays.asList(beans), property,
				BeanWithOrderedFields.class);
		assertEquals(5, map.size());
		for (int i = 0; i < 5; i++) {
			BeanWithOrderedFields b = map.get(Integer.valueOf(i));
			assertSame(beans[i], b);
		}

	}

	/**
	 * Unit test for toMap.
	 */
	@Test(expected = RuntimeException.class)
	public void testToMap_NullValue() {
		String property = null;

		BeanWithOrderedFields beans[] = new BeanWithOrderedFields[5];
		for (int i = 0; i < 5; i++) {
			beans[i] = new BeanWithOrderedFields();
			beans[i].setThird(Integer.valueOf(i));
		}
		CollectionUtils.toMap(Arrays.asList(beans), property, BeanWithOrderedFields.class);

	}

	/**
	 * test getMandatoryProperty
	 */
	@Test
	public void getMandatoryProperty() {
		Properties p1 = new Properties();
		p1.setProperty("a", "A"); //$NON-NLS-1$ //$NON-NLS-2$
		p1.setProperty("b", "B  "); //$NON-NLS-1$ //$NON-NLS-2$

		String key = "b"; //$NON-NLS-1$
		String actual = CollectionUtils.getMandatoryProperty(p1, key);
		String expected = "B"; //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	
	/**
	 * test getMandatoryProperty
	 */
	@Test(expected = RuntimeException.class)
	public void getMandatoryProperty_fail() {
		Properties p1 = new Properties();
		p1.setProperty("a", "A"); //$NON-NLS-1$ //$NON-NLS-2$
		String key = "b"; //$NON-NLS-1$
		CollectionUtils.getMandatoryProperty(p1, key);

	}

	/**
	 * test getOptionalProperty
	 */
	@Test
	public void testGetOptionalProperty() {
		Properties p1 = new Properties();
		p1.setProperty("a", "A"); //$NON-NLS-1$ //$NON-NLS-2$
		p1.setProperty("b", "B  "); //$NON-NLS-1$ //$NON-NLS-2$

		String key = "b"; //$NON-NLS-1$
		String actual = CollectionUtils.getOptionalProperty(p1, key);
		String expected = "B"; //$NON-NLS-1$
		assertEquals(expected, actual);
	}

	
	/**
	 * test addAll
	 */
	@Test
	public void testAddAll() {
		Collection<Integer> collection = new ArrayList<Integer>();
		collection.add(1);
		Integer [] array = {4,5};
		Collection<Integer> result = CollectionUtils.addAll(collection, array);
		assertEquals(3,result.size());
	}

	
	/**
	 * test getOptionalProperty
	 */
	@Test
	public void testGetOptionalProperty_fail() {
		Properties p1 = new Properties();
		p1.setProperty("a", "A"); //$NON-NLS-1$ //$NON-NLS-2$

		String key = "b"; //$NON-NLS-1$
		String actual = CollectionUtils.getOptionalProperty(p1, key);
		String expected = StringConstants.EMPTY;
		assertEquals(expected, actual);
	}
	
	/**
	 * tests upCast
	 */
	@Test
	public void testUpCast() {
		Set<Bean2> s = new HashSet<Bean2>();
		Set<BeanWithOrderedFields> expected = new HashSet<BeanWithOrderedFields>();
		assertEquals(expected, CollectionUtils.upCast(s));

	}

	/**
	 * tests convert
	 */
	@Test
	public void testConvert() {
		List<BeanWithOrderedFields> l = new ArrayList<BeanWithOrderedFields>();
		List<Bean2> expected = new ArrayList<Bean2>();
		assertEquals(expected, CollectionUtils.convert(l));
	}

	/**
	 * tests sort()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSort() {
		BeanWithOrderedFields bwof1 = new BeanWithOrderedFields("1", "a", 1, 1L, 1D);
		BeanWithOrderedFields bwof2 = new BeanWithOrderedFields("2", "b", 2, 2L, 2D);
		BeanWithOrderedFields bwof3 = new BeanWithOrderedFields("3", "c", 3, 3L, 3D);
		BeanWithOrderedFields bwof4 = new BeanWithOrderedFields("4", "d", 4, 4L, 4D);
		BeanWithOrderedFields bwof5 = new BeanWithOrderedFields("5", "e", 5, 5L, 5D);
		
		BeanWithOrderedFields[] expecteds = {bwof1, bwof2, bwof3, bwof4, bwof5};
		
		Set<BeanWithOrderedFields> set = new HashSet<BeanWithOrderedFields>(Arrays.asList(expecteds));
		
		BeanWithOrderedFields[] actuals = CollectionUtils.sort(
				set, BeanWithOrderedFields.class, "first").toArray(new BeanWithOrderedFields[]{});
		assertArrayEquals(expecteds, actuals);
		
		actuals = CollectionUtils.sort(set, BeanWithOrderedFields.class, "second").toArray(new BeanWithOrderedFields[]{});
		assertArrayEquals(expecteds, actuals);
		
		actuals = CollectionUtils.sort(set, BeanWithOrderedFields.class, "third").toArray(new BeanWithOrderedFields[]{});
		assertArrayEquals(expecteds, actuals);
		
		actuals = CollectionUtils.sort(set, BeanWithOrderedFields.class, "fourth").toArray(new BeanWithOrderedFields[]{});
		assertArrayEquals(expecteds, actuals);
		
		actuals = CollectionUtils.sort(set, BeanWithOrderedFields.class, "fifth").toArray(new BeanWithOrderedFields[]{});
		assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * tests inverseSort()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testInverseSort() {
		BeanWithOrderedFields bwof1 = new BeanWithOrderedFields("1", "a", 1, 1L, 1D);
		BeanWithOrderedFields bwof2 = new BeanWithOrderedFields("2", "b", 2, 2L, 2D);
		BeanWithOrderedFields bwof3 = new BeanWithOrderedFields("3", "c", 3, 3L, 3D);
		BeanWithOrderedFields bwof4 = new BeanWithOrderedFields("4", "d", 4, 4L, 4D);
		BeanWithOrderedFields bwof5 = new BeanWithOrderedFields("5", "e", 5, 5L, 5D);
		
		BeanWithOrderedFields[] expecteds = {bwof5, bwof4, bwof3, bwof2, bwof1};
		
		Set<BeanWithOrderedFields> set = new HashSet<BeanWithOrderedFields>(Arrays.asList(expecteds));
		
		BeanWithOrderedFields[] actuals = CollectionUtils.inverseSort(
				set, BeanWithOrderedFields.class, "first").toArray(new BeanWithOrderedFields[]{});
		assertArrayEquals(expecteds, actuals);
		
		actuals = CollectionUtils.inverseSort(set, BeanWithOrderedFields.class, "second").toArray(new BeanWithOrderedFields[]{});
		assertArrayEquals(expecteds, actuals);
		
		actuals = CollectionUtils.inverseSort(set, BeanWithOrderedFields.class, "third").toArray(new BeanWithOrderedFields[]{});
		assertArrayEquals(expecteds, actuals);
		
		actuals = CollectionUtils.inverseSort(set, BeanWithOrderedFields.class, "fourth").toArray(new BeanWithOrderedFields[]{});
		assertArrayEquals(expecteds, actuals);
		
		actuals = CollectionUtils.inverseSort(set, BeanWithOrderedFields.class, "fifth").toArray(new BeanWithOrderedFields[]{});
		assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * tests sort()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSort_withTwoProperties() {
		BeanWithOrderedFields bwof1 = new BeanWithOrderedFields("1", "a", 1, 1L, 1D);
		BeanWithOrderedFields bwof2 = new BeanWithOrderedFields("2", "b", 2, 2L, 2D);
		BeanWithOrderedFields bwof3 = new BeanWithOrderedFields("1", "c", 3, 3L, 3D);
		BeanWithOrderedFields bwof4 = new BeanWithOrderedFields("2", "d", 4, 4L, 4D);
		BeanWithOrderedFields bwof5 = new BeanWithOrderedFields("1", "e", 5, 5L, 5D);
		BeanWithOrderedFields bwof6 = new BeanWithOrderedFields("2", "f", 5, 5L, 5D);
		
		BeanWithOrderedFields[] beans = {bwof1, bwof2, bwof3, bwof4, bwof5, bwof6};
		Set<BeanWithOrderedFields> set = new HashSet<BeanWithOrderedFields>(Arrays.asList(beans));
		
		BeanWithOrderedFields[] expecteds = {bwof1, bwof3, bwof5, bwof2, bwof4, bwof6};
		
		BeanWithOrderedFields[] actuals = CollectionUtils.sort(
				set, BeanWithOrderedFields.class, "first", "second").toArray(new BeanWithOrderedFields[]{});
		assertArrayEquals(expecteds, actuals);
	}
	
	/**
	 * Test readProperties() success.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testReadProperties_Succeed() {
		String path = "/gr/interamerican/bo2/utils/sample.properties";
		Properties p = CollectionUtils.readProperties(path);
		String a = p.getProperty("a");
		String b = p.getProperty("b");
		assertEquals("A", a);
		assertEquals("B#Comment", b);
	}
	
	/**
	 * Test readProperties() success.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testReadPropertiesFromXML() {
		String path = "/gr/interamerican/bo2/utils/sample.xml";
		Properties p = CollectionUtils.readPropertiesFromXML(path);
		String a = p.getProperty("a");
		String b = p.getProperty("b");
		assertEquals("A", a);
		assertEquals("B", b);
	}
	
	/**
	 * Test readProperties() failing.
	 */	
	@SuppressWarnings("nls")
	@Test(expected=RuntimeException.class)
	public void testReadProperties_Fail() {
		String path = "/com/foo/sample.properties";
		@SuppressWarnings("unused")
		Properties p = CollectionUtils.readProperties(path);
	}
	
	
	/**
	 * test IsNullOrEmpty
	 */
	@Test
	public void testIsNullOrEmpty() {
		
		assertTrue(CollectionUtils.isNullOrEmpty(null));
		
		Collection<?> c = Arrays.asList();
		assertTrue(CollectionUtils.isNullOrEmpty(c));
	}
	
	
	/**
	 * test IterableToSet
	 */
	@Test
	public void testIterableToSet() {
		Iterable<Integer> iter = Arrays.asList(1,2);
		Set<Integer> result = CollectionUtils.iterableToSet(iter);
		assertTrue(result.contains(1));
		assertTrue(result.contains(2));
	}
	
	/**
	 * Unit test for sum BD.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSumBD() {		
		NumberBean[] nb = {
				new NumberBean(),
				new NumberBean(),
				new NumberBean(),
				new NumberBean(),
		};
		nb[0].setBd1(new BigDecimal("1.23"));
		nb[1].setBd1(new BigDecimal("-1.030"));
		nb[3].setBd1(new BigDecimal("1.00005"));
		BigDecimal actual = CollectionUtils.
			sumBD(Arrays.asList(nb), NumberBean.class, "bd1");
		BigDecimal expected = new BigDecimal("1.20005");
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for sum BD.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSumD() {		
		BeanWith3Fields[] nb = {
				new BeanWith3Fields(null,1,1.1),
				new BeanWith3Fields(null,1,2.2),
				new BeanWith3Fields(null,1,3.3),
				new BeanWith3Fields(null,1,null),
		};		
		Double actual = CollectionUtils.
			sumD(Arrays.asList(nb), BeanWith3Fields.class, "field3");
		Double expected = 6.6;
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for sum BD.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSumI() {		
		BeanWith3Fields[] nb = {
				new BeanWith3Fields(null,1,1.1),
				new BeanWith3Fields(null,1,2.2),
				new BeanWith3Fields(null,1,3.3),
				new BeanWith3Fields(null,1,null),
		};		
		Integer actual = CollectionUtils.
			sumI(Arrays.asList(nb), BeanWith3Fields.class, "field2");
		Integer expected = 4;
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for toArray.
	 */
	@Test
	public void testToArray() {
		Set<Integer> set = new HashSet<Integer>();
		set.add(1); 
		set.add(4);
		set.add(5);
		Integer[] array = CollectionUtils.toArray(set, new Integer[0]);
		Assert.assertEquals(3, array.length);
		Assert.assertTrue(ArrayUtils.contains(array, 1));
		Assert.assertTrue(ArrayUtils.contains(array, 4));
		Assert.assertTrue(ArrayUtils.contains(array, 5));
	}
	
	/**
	 * Unit test for toArray.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSubMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "1");
		map.put(2, "2");
		map.put(3, "3");
		map.put(4, "4");
		Integer[] keysArr = {1, 4, 4, 5};
		Map<Integer, String> sub = CollectionUtils.subMap(map, Arrays.asList(keysArr));
		Assert.assertEquals(2, sub.size());
		Assert.assertTrue(sub.containsKey(1));
		Assert.assertTrue(sub.containsKey(4));
	}
	
	
	/**
	 * Unit test for toArray.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRemoveNulls() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "1");
		map.put(2, null);
		map.put(3, null);
		map.put(4, "4");
		CollectionUtils.removeNulls(map);
		Assert.assertEquals(2, map.size());
		Assert.assertEquals("1", map.get(1));
		Assert.assertEquals("4", map.get(4));
	}
	
	/**
	 * Unit test for partition.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testPartition() {
		List<String> list = new ArrayList<String>();
		list.add("a");
		List<List<String>> result = CollectionUtils.partition(list, 2);
		
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(1, result.get(0).size());
		Assert.assertEquals("a", result.get(0).get(0));
		
		list.add("b");
		list.add("c");
		list.add("d");
		result = CollectionUtils.partition(list, 2);
		
		Assert.assertEquals(2, result.size());
		Assert.assertEquals(2, result.get(0).size());
		Assert.assertEquals(2, result.get(1).size());
		Assert.assertEquals("a", result.get(0).get(0));
		Assert.assertEquals("b", result.get(0).get(1));
		Assert.assertEquals("c", result.get(1).get(0));
		Assert.assertEquals("d", result.get(1).get(1));
		
		list.add("e");
		result = CollectionUtils.partition(list, 2);
		
		Assert.assertEquals(3, result.size());
		Assert.assertEquals(2, result.get(0).size());
		Assert.assertEquals(2, result.get(1).size());
		Assert.assertEquals(1, result.get(2).size());
		Assert.assertEquals("a", result.get(0).get(0));
		Assert.assertEquals("b", result.get(0).get(1));
		Assert.assertEquals("c", result.get(1).get(0));
		Assert.assertEquals("d", result.get(1).get(1));
		Assert.assertEquals("e", result.get(2).get(0));
	}
	

	/**
	 * Javabean class for testing.
	 */
	@SuppressWarnings("serial")
	private static class Bean2 extends BeanWithOrderedFields { /* empty */ }

}
