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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.PropertiesEqual;
import gr.interamerican.bo2.utils.conditions.PropertyIsNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link SelectionUtils}.
 */
public class TestSelectionUtils {

	/**
	 * Unit test for max
	 */
	@Test
	public void testMax_onEmptySet() {
		Set<BeanWith2Fields> set = new HashSet<BeanWith2Fields>();
		BeanWith2Fields maxOwner = SelectionUtils.max(set, "field2"); //$NON-NLS-1$
		assertNull(maxOwner);		
	}

	/**
	 * Unit test for max
	 */
	@Test
	public void testMax_onFullSet() {
		Set<BeanWith2Fields> set = new HashSet<BeanWith2Fields>();
		BeanWith2Fields b1 = new BeanWith2Fields();
		b1.setField2(12);
		set.add(b1);
		BeanWith2Fields b2 = new BeanWith2Fields();
		b2.setField2(18);
		set.add(b2);
		BeanWith2Fields b3 = new BeanWith2Fields();
		b3.setField2(20);
		set.add(b3);
		BeanWith2Fields maxOwner = SelectionUtils.max(set, "field2"); //$NON-NLS-1$
		assertSame(b3, maxOwner);		
	}

	/**
	 * Unit test for max
	 */
	@Test
	public void testMin_onEmptySet() {
		Set<BeanWith2Fields> set = new HashSet<BeanWith2Fields>();
		BeanWith2Fields minOwner = SelectionUtils.min(set, "field2"); //$NON-NLS-1$
		assertNull(minOwner);		
	}

	/**
	 * Unit test for max
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
		b2.setField2(2);
		set.add(b3);
		BeanWith2Fields minOwner = SelectionUtils.min(set, "field2"); //$NON-NLS-1$
		assertSame(minOwner, b3);		
	}
	
	/**
	 * Unit test for max
	 */
	@Test
	public void testSelection() {
		Assert.assertTrue (SelectionUtils.selection(true, true, false));
		Assert.assertFalse (SelectionUtils.selection(false, true, false));
	}
	
	/**
	 * Unit test for max
	 */
	@SuppressWarnings("nls")
	@Test(expected = RuntimeException.class)
	public void testSelectFirstByProperty_fail() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		BeanWith2Fields bean2 = new BeanWith2Fields();
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1,bean2);
		SelectionUtils.selectFirstByProperty("field3", "hello", collection, BeanWith2Fields.class);
	}
	
	/**
	 * Unit test for max
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSelectFirstByProperty_succeed() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		bean1.setField1("hello");
		BeanWith2Fields bean2 = new BeanWith2Fields();
		bean2.setField1("hello");
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1,bean2);
		BeanWith2Fields actual = SelectionUtils.selectFirstByProperty("field1", "hello", collection, BeanWith2Fields.class);
		Assert.assertEquals(bean1,actual);
		
		Collection<BeanWith2Fields> emptyCollection = Arrays.asList();
		Assert.assertNull(SelectionUtils.selectFirstByProperty("field1", "hello", emptyCollection, BeanWith2Fields.class));
	}
	
	/**
	 * Unit test for max
	 */
	@SuppressWarnings("nls")
	@Test(expected = RuntimeException.class)
	public void testSelectFirstWithNotNullByProperty_fail() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		BeanWith2Fields bean2 = new BeanWith2Fields();
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1,bean2);
		SelectionUtils.selectFirstWithNotNullProperty("field3", collection, BeanWith2Fields.class);
	}
	
	/**
	 * Unit test for max
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSelectFirstWithNotNullByProperty_succeed() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		bean1.setField1(null);
		BeanWith2Fields bean2 = new BeanWith2Fields();
		bean2.setField1("hello");
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1,bean2);
		BeanWith2Fields actual = SelectionUtils.selectFirstWithNotNullProperty("field1", collection, BeanWith2Fields.class);
		Assert.assertEquals(bean2,actual);
	}
	
	/**
	 * Unit test for max
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSelectFirstWithNullByProperty_succeed() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		bean1.setField1(null);
		BeanWith2Fields bean2 = new BeanWith2Fields();
		bean2.setField1("hello");
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1,bean2);
		BeanWith2Fields actual = SelectionUtils.selectFirstWithNullProperty("field1", collection, BeanWith2Fields.class);
		Assert.assertEquals(bean1,actual);
	}
	
	/**
	 * Unit test for findIfItExistsByProperty
	 */
	@Test
	@SuppressWarnings("nls")
	public void testExistsByProperty_succeed() {
		BeanWith2Fields bean1 = new BeanWith2Fields();
		bean1.setField1("hello");
		BeanWith2Fields bean2 = new BeanWith2Fields();
		bean2.setField1("hello");
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1,bean2);
		Boolean actual = SelectionUtils.existsByProperty("field1", "hello", collection, BeanWith2Fields.class);
		Assert.assertTrue(actual);
		actual = SelectionUtils.existsByProperty("field1", "petros", collection, BeanWith2Fields.class);
		Assert.assertFalse(actual);
	}
	
	/**
	 * Unit test for findIfItExistsByProperty
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
		
		Collection<BeanWith2Fields> collection = Arrays.asList(bean1,bean2);
		String[] properties = {"field1", "field2"};
		Object values[] = {"hello", 4};
		
		Condition<BeanWith2Fields> exists = 
			new PropertiesEqual<BeanWith2Fields>(properties, values, BeanWith2Fields.class);
		boolean actual = SelectionUtils.existsByCondition(exists, collection);
		Assert.assertTrue(actual);
		
		Condition<BeanWith2Fields> notExists = 
			new PropertyIsNull<BeanWith2Fields>("field1", BeanWith2Fields.class);
		actual = SelectionUtils.existsByCondition(notExists, collection);
		Assert.assertFalse(actual);
		
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
		List<BeanWithOrderedFields> collection = Arrays.asList(new BeanWithOrderedFields[]{bwof1, bwof2, bwof3});
		
		List<BeanWithOrderedFields> results = 
			SelectionUtils.getMatchingElements(collection, "first", "2");
		assertEquals(results.size(),2);
		assertEquals(results.get(0),bwof2);
		assertEquals(results.get(1),bwof3);
		
		results = SelectionUtils.getMatchingElements(collection, "third", 3);
		assertEquals(results.size(),1);
		assertEquals(results.get(0),bwof3);
		
		results = SelectionUtils.getMatchingElements(collection, "third", 78);
		assertEquals(results.size(),0);
	}

}
