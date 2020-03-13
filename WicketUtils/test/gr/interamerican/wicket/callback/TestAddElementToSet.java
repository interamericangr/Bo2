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

/**
 * Unit Test of {@link AddElementToSet}.
 */
@Deprecated
public class TestAddElementToSet {

	/**
	 * Test method for {@link AddElementToSet#process(java.io.Serializable)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testWork() {
		// setup
		Set<BeanWithOrderedFields> set = new HashSet<BeanWithOrderedFields>();
		Object copyFrom = new BeanWithOrderedFields("firstField", null, null, null, null);
		AddElementToSet<BeanWithOrderedFields> tested = new AddElementToSet<>(set, "fourth",
				copyFrom);
		// empty set - first index value ( zero )
		assertTrue(set.contains(tested.process(new BeanWithOrderedFields())));
		assertEquals(1, set.size());
		assertEquals(1L, tested.set.iterator().next().getFourth().longValue());
		assertEquals("firstField", tested.set.iterator().next().getFirst());
		// re-empty set - carry on with the next index value ( one ) even if it's empty
		set.clear();
		assertTrue(set.contains(tested.process(new BeanWithOrderedFields())));
		assertEquals(1, set.size());
		assertEquals(2L, tested.set.iterator().next().getFourth().longValue());
	}

	/**
	 * Test method for {@link AddElementToSet#getIndexFromSet()}.
	 */
	@Test
	public void testGetIndexFromSet() {
		Set<BeanWith1Field> set = new HashSet<BeanWith1Field>();
		BeanWith1Field bean = new BeanWith1Field(10L);
		set.add(bean);
		AddElementToSet<BeanWith1Field> tested = new AddElementToSet<BeanWith1Field>(set, "field2", null); //$NON-NLS-1$
		assertEquals(11L, tested.newIndex.longValue());
		bean.setField2(null);
		assertEquals(1L, tested.getIndexFromSet().longValue());
		set.clear();
		assertEquals(1L, tested.getIndexFromSet().longValue());
	}
}