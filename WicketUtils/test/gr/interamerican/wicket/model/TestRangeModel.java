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
package gr.interamerican.wicket.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.utils.beans.Range;

/**
 * Tests {@link RangeModel}.
 */
public class TestRangeModel {

	/**
	 * Test method for {@link RangeModel#detach()}.
	 */
	@Test
	public void testDetach() {
		@SuppressWarnings("unchecked")
		Model<Range<Double>> mocked = mock(Model.class);
		RangeModel<Double> tested = new RangeModel<>(mocked, true);
		tested.detach();
		verify(mocked).detach();
	}

	/**
	 * Test method for {@link RangeModel#getObject()}.
	 */
	@Test
	public void testGetObject_null() {
		RangeModel<Double> tested = new RangeModel<>(Model.of(), false);
		assertNull(tested.getObject());
	}

	/**
	 * Test method for {@link RangeModel#getObject()}.
	 */
	@Test
	public void testGetObject() {
		Model<Range<Long>> model = Model.of(new Range<>(14L, 20L));
		assertEquals((Long) 14L, new RangeModel<>(model, true).getObject());
		assertEquals((Long) 20L, new RangeModel<>(model, false).getObject());
	}

	/**
	 * Test method for {@link RangeModel#setObject(Comparable)}.
	 */
	@Test
	public void testSetObject() {
		Model<Range<Long>> model = Model.of();
		assertNull(model.getObject());
		new RangeModel<>(model, true).setObject(5L);
		assertNotNull(model.getObject());
		assertEquals((Long) 5L, model.getObject().getLeft());
		new RangeModel<>(model, false).setObject(20L);
		assertEquals((Long) 5L, model.getObject().getLeft());
		assertEquals((Long) 20L, model.getObject().getRight());
	}
}