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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import gr.interamerican.bo2.samples.SampleBean2;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.functional.Match;
import gr.interamerican.bo2.utils.conditions.functional.PropertiesEqual;
import gr.interamerican.bo2.utils.conditions.functional.PropertyIsNull;

/**
 * Unit tests for {@link SelectionUtils}.
 */
@SuppressWarnings("deprecation")
public class TestSelectionUtils {

	/**
	 * Unit test for max.
	 */
	@Test
	public void testMax_onEmptySet() {
		Set<BeanWith2Fields> set = new HashSet<BeanWith2Fields>();
		assertNull(SelectionUtils.max(set, "field2")); //$NON-NLS-1$
		assertNull(SelectionUtils.max(set, BeanWith2Fields::getField2));
	}

	/**
	 * Unit test for max.
	 */
	@Test
	public void testMax_onFullSet() {
		Set<BeanWith2Fields> set = new HashSet<BeanWith2Fields>();
		BeanWith2Fields b1 = new BeanWith2Fields();
		b1.setField2(12);
		set.add(b1);
		BeanWith2Fields b2 = new BeanWith2Fields();
		b2.setField2(null);
		set.add(b2);
		BeanWith2Fields b3 = new BeanWith2Fields();
		b3.setField2(20);
		set.add(b3);
		assertSame(b3, SelectionUtils.max(set, "field2")); //$NON-NLS-1$
		assertSame(b3, SelectionUtils.max(set, BeanWith2Fields::getField2));
	}

	/**
	 * Unit test for max.
	 */
	@Test
	public void testMin_onEmptySet() {
		Set<BeanWith2Fields> set = new HashSet<BeanWith2Fields>();
		assertNull(SelectionUtils.min(set, "field2")); //$NON-NLS-1$
		assertNull(SelectionUtils.min(set, BeanWith2Fields::getField2));
	}

	/**
	 * Unit test for max.
	 */
	@Test
	public void testMin_onFullSet() {
		Set<BeanWith2Fields> set = new HashSet<BeanWith2Fields>();
		BeanWith2Fields b1 = new BeanWith2Fields();
		b1.setField2(12);
		set.add(b1);
		BeanWith2Fields b2 = new BeanWith2Fields();
		b2.setField2(18);
		set.add(b2);
		BeanWith2Fields b3 = new BeanWith2Fields();
		set.add(b3);
		assertSame(SelectionUtils.min(set, "field2"), b3); //$NON-NLS-1$
		assertSame(SelectionUtils.min(set, BeanWith2Fields::getField2), b3);
	}

	/**
	 * Unit test for max.
	 */
	@Test
	public void testSelection() {
		assertTrue(SelectionUtils.selection(true, true, false));
		assertFalse(SelectionUtils.selection(false, true, false));
	}

	/**
	 * Unit test for max.
	 */
	@SuppressWarnings("nls")
	@Test(expected = RuntimeException.class)
	public void testSelectFirstByProperty_fail() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		BeanWith2Fields bean2 = new BeanWith2Fields();
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1, bean2);
		SelectionUtils.selectFirstByProperty("field3", "hello", collection, BeanWith2Fields.class);
		// cannot re-produce with functional - won't even compile
	}

	/**
	 * Unit test for max.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSelectFirstByProperty_succeed() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		bean1.setField1("hello");
		BeanWith2Fields bean2 = new BeanWith2Fields();
		bean2.setField1("hello");
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1, bean2);
		assertEquals(bean1, SelectionUtils.selectFirstByProperty("field1", "hello", collection, BeanWith2Fields.class));
		assertEquals(bean1, SelectionUtils.selectFirstByProperty(BeanWith2Fields::getField1, "hello", collection));

		Collection<BeanWith2Fields> emptyCollection = Arrays.asList();
		assertNull(SelectionUtils.selectFirstByProperty("field1", "hello", emptyCollection, BeanWith2Fields.class));
		assertNull(SelectionUtils.selectFirstByProperty(BeanWith2Fields::getField1, "hello", emptyCollection));
	}

	/**
	 * Unit test for selectFirstWithNotNullProperty.
	 */
	@SuppressWarnings("nls")
	@Test(expected = RuntimeException.class)
	public void testSelectFirstWithNotNullByProperty_fail() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		BeanWith2Fields bean2 = new BeanWith2Fields();
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1, bean2);
		SelectionUtils.selectFirstWithNotNullProperty("field3", collection, BeanWith2Fields.class);
		// cannot re-produce with functional - won't even compile
	}

	/**
	 * Unit test for selectFirstWithNotNullProperty.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSelectFirstWithNotNullByProperty_succeed() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		bean1.setField1(null);
		BeanWith2Fields bean2 = new BeanWith2Fields();
		bean2.setField1("hello");
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1, bean2);
		assertEquals(bean2, SelectionUtils.selectFirstWithNotNullProperty("field1", collection, BeanWith2Fields.class));
		assertEquals(bean2, SelectionUtils.selectFirstWithNotNullProperty(BeanWith2Fields::getField1, collection));
	}

	/**
	 * Unit test for selectFirstWithNullProperty.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSelectFirstWithNullByProperty_succeed() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		bean1.setField1(null);
		BeanWith2Fields bean2 = new BeanWith2Fields();
		bean2.setField1("hello");
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1, bean2);
		assertEquals(bean1, SelectionUtils.selectFirstWithNullProperty("field1", collection, BeanWith2Fields.class));
		assertEquals(bean1, SelectionUtils.selectFirstWithNullProperty(BeanWith2Fields::getField1, collection));
	}

	/**
	 * Unit test for findIfItExistsByProperty.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testExistsByProperty_succeed() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		bean1.setField1("hello");
		BeanWith2Fields bean2 = new BeanWith2Fields();
		bean2.setField1("hello");
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1, bean2);
		assertTrue(SelectionUtils.existsByProperty("field1", "hello", collection, BeanWith2Fields.class));
		assertTrue(SelectionUtils.existsByProperty(BeanWith2Fields::getField1, "hello", collection));
		assertFalse(SelectionUtils.existsByProperty("field1", "petros", collection, BeanWith2Fields.class));
		assertFalse(SelectionUtils.existsByProperty(BeanWith2Fields::getField1, "petros", collection));
	}

	/**
	 * Unit test for findIfItExistsByProperty.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testExistsByCondition_succeed() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		bean1.setField1("hello");
		bean1.setField2(4);

		BeanWith2Fields bean2 = new BeanWith2Fields();
		bean2.setField1("hello");
		bean2.setField2(4);

		Collection<BeanWith2Fields> collection = Arrays.asList(bean1, bean2);

		Condition<BeanWith2Fields> exists = new PropertiesEqual<>(new Match<>(BeanWith2Fields::getField1, "hello"),
				new Match<>(BeanWith2Fields::getField2, 4));
		boolean actual = SelectionUtils.existsByCondition(exists, collection);
		assertTrue(actual);

		Condition<BeanWith2Fields> notExists = new PropertyIsNull<BeanWith2Fields>(BeanWith2Fields::getField1);
		actual = SelectionUtils.existsByCondition(notExists, collection);
		assertFalse(actual);

	}

	/**
	 * tests getMatchingElements.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetMatchingElements() {
		BeanWithOrderedFields bwof1 = new BeanWithOrderedFields("1", "a", 1, 1L, 1D);
		BeanWithOrderedFields bwof2 = new BeanWithOrderedFields("2", "b", 2, 2L, 2D);
		BeanWithOrderedFields bwof3 = new BeanWithOrderedFields("2", "c", 3, 3L, 3D);
		List<BeanWithOrderedFields> collection = Arrays.asList(new BeanWithOrderedFields[] { bwof1, bwof2, bwof3 });

		List<BeanWithOrderedFields> results = SelectionUtils.getMatchingElements(collection, "first", "2");
		assertEquals(results.size(), 2);
		assertEquals(results.get(0), bwof2);
		assertEquals(results.get(1), bwof3);

		results = SelectionUtils.getMatchingElements(collection, "third", 3);
		assertEquals(results.size(), 1);
		assertEquals(results.get(0), bwof3);

		results = SelectionUtils.getMatchingElements(collection, "third", 78);
		assertEquals(results.size(), 0);
	}
	
	/**
	 * Test for {@link SelectionUtils#selectFirstByProperties(String[], Object[], Collection, Class)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSelectFirstByProperties() {
		List<SampleBean2> list = new ArrayList<SampleBean2>();
		SampleBean2 sampleBean = new SampleBean2();
		sampleBean.setField1("a");
		sampleBean.setField2("c");

		SampleBean2 sampleBean2 = new SampleBean2();
		sampleBean2.setField1("b");
		sampleBean2.setField2("d");

		list.add(sampleBean);
		list.add(sampleBean2);
				
		SampleBean2 actual = SelectionUtils.selectFirstByProperties(new String[] {"field1","field2"}, new String[] {"a","c"}, list, SampleBean2.class);
		assertNotNull(actual);
		assertEquals(sampleBean, actual);
	}
	
	/**
	 * Test for {@link SelectionUtils#selectFirstByProperties(Collection, Match...)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSelectFirstByProperties_Matchers() {
		List<SampleBean2> list = new ArrayList<SampleBean2>();
		SampleBean2 sampleBean = new SampleBean2();
		sampleBean.setField1("a");
		sampleBean.setField2("c");

		SampleBean2 sampleBean2 = new SampleBean2();
		sampleBean2.setField1("b");
		sampleBean2.setField2("d");

		list.add(sampleBean);
		list.add(sampleBean2);
		
		SampleBean2 actual = SelectionUtils.selectFirstByProperties(list, new Match<SampleBean2,String>(SampleBean2::getField1,"a"), new Match<SampleBean2,String>(SampleBean2::getField2,"c"));
		assertNotNull(actual);
		assertEquals(sampleBean, actual);
	}
}