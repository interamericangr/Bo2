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
package gr.interamerican.wicket.validation;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.junit.Test;

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link NotNullValidator}.
 */
public class TestNotNullValidator extends WicketTest {

	/**
	 * Test method for {@link NotNullValidator#getDependentFormComponents()}.
	 */
	@Test
	public void testGetDependentFormComponents() {
		DateTextField tf = new DateTextField("one", new StyleDateConverter(true)); //$NON-NLS-1$
		TextField<BigDecimal> tf2 = new TextField<>("two"); //$NON-NLS-1$
		NotNullValidator tested = new NotNullValidator(tf, tf2);
		FormComponent<?>[] components = tested.getDependentFormComponents();
		assertEquals(2, components.length);
		assertSame(tf, components[0]);
		assertSame(tf2, components[1]);
	}

	/**
	 * Test method for {@link NotNullValidator#validate(Form)}.
	 */
	@Test
	public void testValidate() {
		DateTextField tf = new DateTextField("one", new StyleDateConverter(true)); //$NON-NLS-1$
		TextField<BigDecimal> tf2 = new TextField<>("two"); //$NON-NLS-1$
		// null both
		tf.setConvertedInput(null);
		tf2.setConvertedInput(null);
		NotNullValidator tested = new NotNullValidator(tf, tf2);
		tested.validate(null);
		assertFalse(tf.hasErrorMessage());
		assertFalse(tf2.hasErrorMessage());
		// both filled
		tf.setConvertedInput(DateUtils.today());
		tf2.setConvertedInput(BigDecimal.ONE);
		tested.validate(null);
		assertFalse(tf.hasErrorMessage());
		assertFalse(tf2.hasErrorMessage());
		// one filled only
		tf2.setConvertedInput(null);
		tested.validate(null);
		assertFalse(tf.hasErrorMessage());
		assertTrue(tf2.hasErrorMessage());
	}
}