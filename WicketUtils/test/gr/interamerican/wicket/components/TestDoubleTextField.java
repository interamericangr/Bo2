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
package gr.interamerican.wicket.components;

import static org.junit.Assert.*;

import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.wicket.behavior.ValidationStyleBehavior;
import gr.interamerican.wicket.markup.html.Markup;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link DoubleTextField}.
 */
public class TestDoubleTextField extends WicketTest {

	@Override
	protected Component initializeComponent(String wicketId) {
		return new DoubleTextField(wicketId, new Model<>(0.0), 3);
	}

	/**
	 * Test method for {@link DoubleTextField#DoubleTextField(String, IModel, Integer)}.
	 */
	@Test
	public void testDoubleTextFieldStringIModelOfDoubleInteger() {
		DoubleTextField field = startAndTestComponent(DoubleTextField.class, Markup.input);
		assertTrue(field.getConverter(Double.class) instanceof FixedDigitsDoubleConverter);
		assertTrue(field.getBehaviors().contains(ValidationStyleBehavior.INSTANCE));
		assertFalse(field.getBehaviors(NumberFormatBehaviour.class).isEmpty());
	}

	/**
	 * Test method for {@link DoubleTextField#getConverter(Class)}.
	 */
	@Test
	public void testGetConverterClassOfC() {
		DoubleTextField tested = startAndTestComponent(DoubleTextField.class, Markup.input);
		assertTrue(tested.getConverter(Double.class)
				.convertToString(842.51, Locale.getDefault()).endsWith("510")); //$NON-NLS-1$
	}
}