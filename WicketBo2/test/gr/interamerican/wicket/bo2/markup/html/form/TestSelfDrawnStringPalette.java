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
package gr.interamerican.wicket.bo2.markup.html.form;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.junit.Test;

import gr.interamerican.bo2.utils.meta.descriptors.StringSelectionsBoPropertyDescriptor;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link SelfDrawnTextArea}.
 */
@SuppressWarnings("nls")
public class TestSelfDrawnStringPalette extends WicketTest {

	/**
	 * Test creation with default value.
	 */
	@Test
	public void testCreation() {
		SelfDrawnStringPallete tested = startAndTestComponent(SelfDrawnStringPallete.class);
		assertNotNull(tested.getChoices());
		assertTrue(tested.getModelCollection().isEmpty());
	}

	@Override
	protected Component initializeComponent(String id) {
		StringSelectionsBoPropertyDescriptor descriptor = new StringSelectionsBoPropertyDescriptor(
				Arrays.asList("a", "b"));
		descriptor.setName("choices");
		descriptor.setLabel("lala lala");
		descriptor.setHasDefault(false);
		return new SelfDrawnStringPallete(id, descriptor);
	}
}