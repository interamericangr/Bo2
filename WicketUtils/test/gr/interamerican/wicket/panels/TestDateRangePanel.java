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
package gr.interamerican.wicket.panels;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.validation.NotNullValidator;

/**
 * Tests {@link DateRangePanel}.
 */
public class TestDateRangePanel extends WicketTest {

	@Override
	protected Component initializeComponent(String wicketId) {
		return new DateRangePanel(wicketId, Model.of());
	}

	/**
	 * Test method for {@link DateRangePanel#onBeforeRender()}.
	 */
	@Test
	public void testOnBeforeRender() {
		DateRangePanel panel = startAndTestComponent(DateRangePanel.class);
		Collection<IFormValidator> validators = getForm().getFormValidators();
		List<IFormValidator> result = validators.stream().filter(t -> t instanceof NotNullValidator)
				.collect(Collectors.toList());
		assertEquals(1, result.size());
		NotNullValidator validator = (NotNullValidator) result.get(0);
		assertEquals(2, validator.getDependentFormComponents().length);
		assertTrue(panel.contains(validator.getDependentFormComponents()[0], false));
		assertTrue(panel.contains(validator.getDependentFormComponents()[1], false));
	}

	/**
	 * Test method for {@link DateRangePanel#DateRangePanel(String, IModel)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testDateRangePanel() {
		DateRangePanel panel = startAndTestComponent(DateRangePanel.class);
		Component from = panel.get("from");
		assertTrue(from instanceof DateField);
		assertEquals("From TestId", ((DateField) from).getLabel().getObject());

		Component to = panel.get("to");
		assertTrue(to instanceof DateField);
		assertEquals("To TestId", ((DateField) to).getLabel().getObject());
	}
}