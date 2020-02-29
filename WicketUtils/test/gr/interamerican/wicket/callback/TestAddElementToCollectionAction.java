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
package gr.interamerican.wicket.callback;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.bo2.utils.NumberCalculator;

/**
 * Unit Test of {@link AddElementToCollectionAction}.
 */
public class TestAddElementToCollectionAction {

	/**
	 * Test method for
	 * {@link AddElementToCollectionAction#process(java.io.Serializable)}.
	 */
	@Test
	public void testWork() {
		// setup
		Set<BeanWithOrderedFields> set = new HashSet<BeanWithOrderedFields>();
		String first = "firstField"; //$NON-NLS-1$
		Object copyFrom = new BeanWithOrderedFields(first, null, null, null, null);
		AddElementToCollectionAction<BeanWithOrderedFields, Integer> tested = new AddElementToCollectionAction<>(set,
				BeanWithOrderedFields::getThird, BeanWithOrderedFields::setThird, copyFrom, NumberCalculator.INTEGER);
		// empty set - first index value ( zero )
		assertTrue(set.contains(tested.process(new BeanWithOrderedFields())));
		assertEquals(1, set.size());
		assertEquals(1L, tested.set.iterator().next().getThird().longValue());
		assertEquals(first, tested.set.iterator().next().getFirst());
		// re-empty set - carry on with the next index value ( one ) even if
		// it's empty
		set.clear();
		assertTrue(set.contains(tested.process(new BeanWithOrderedFields())));
		assertEquals(1, set.size());
		assertEquals(2L, tested.set.iterator().next().getThird().longValue());
	}

	/**
	 * Test method for {@link AddElementToCollectionAction#getIndexFromSet()}.
	 */
	@Test
	public void testGetIndexFromSet() {
		Set<BeanWith1Field> set = new HashSet<BeanWith1Field>();
		BeanWith1Field bean = new BeanWith1Field(10L);
		set.add(bean);
		AddElementToCollectionAction<BeanWith1Field, Long> tested = new AddElementToCollectionAction<>(set,
				BeanWith1Field::getField2, BeanWith1Field::setField2, null, NumberCalculator.LONG);
		assertEquals(11L, tested.newIndex.longValue());
		bean.setField2(null);
		assertEquals(1L, tested.getIndexFromSet().longValue());
		set.clear();
		assertEquals(1L, tested.getIndexFromSet().longValue());
	}
}