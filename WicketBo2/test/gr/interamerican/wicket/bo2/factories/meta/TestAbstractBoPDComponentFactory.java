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
package gr.interamerican.wicket.bo2.factories.meta;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import org.apache.wicket.Component;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.impl.open.creation.test.Bo2BaseTest;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.meta.descriptors.BooleanBoPropertyDescriptor;
import gr.interamerican.wicket.samples.factories.SampleBoPDComponentFactory;

/**
 * Unit tests for {@link AbstractBoPDComponentFactory}.
 */
public class TestAbstractBoPDComponentFactory extends Bo2BaseTest{
	
	/**
	 * Unit test for {@link AbstractBoPDComponentFactory#draw(gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor, String, String)} 
	 */
	@SuppressWarnings({ "nls" })
	@Test
	public void testDraw() {
		BooleanBoPropertyDescriptor descriptor = new BooleanBoPropertyDescriptor();
		Component expectedMock = mock(Component.class);

		SampleBoPDComponentFactory tested = spy(new SampleBoPDComponentFactory());
		doReturn(expectedMock).when(tested).drawLabel(any(BooleanBoPropertyDescriptor.class), any(String.class));
		Pair<Component, Component> result = tested.draw(descriptor , "testId", "label");
		
		assertNotNull(result.getLeft());
		assertEquals(expectedMock, result.getLeft());
		assertNull(result.getRight());
	}
	
	/**
	 * Unit test for {@link AbstractBoPDComponentFactory#draw(org.apache.wicket.model.IModel, gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor, String, String)}
	 */
	@SuppressWarnings("nls")
	@Test
	public void testDraw_ModelProvided() {
		BooleanBoPropertyDescriptor descriptor = new BooleanBoPropertyDescriptor();
		Component expectedMock = mock(Component.class);

		SampleBoPDComponentFactory tested = spy(new SampleBoPDComponentFactory());
		doReturn(expectedMock).when(tested).drawLabel(any(BooleanBoPropertyDescriptor.class), any(String.class));
		Pair<Component, Component> result = tested.draw(Model.of(StringConstants.NINE),descriptor , "testId", "label");
		
		assertNotNull(result.getLeft());
		assertEquals(expectedMock, result.getLeft());
		assertNull(result.getRight());
	}
}
