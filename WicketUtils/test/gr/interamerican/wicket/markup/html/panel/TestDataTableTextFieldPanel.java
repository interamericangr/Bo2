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
package gr.interamerican.wicket.markup.html.panel;

import static org.junit.Assert.assertTrue;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests the {@link DataTableTextFieldPanel}.
 */
public class TestDataTableTextFieldPanel extends WicketTest {

	@Override
	protected Component initializeComponent(String wicketId) {
		return new DataTableTextFieldPanel<BeanWith1Field>(wicketId, new Model<BeanWith1Field>(new BeanWith1Field(15L)),
				"field2"); //$NON-NLS-1$
	}

	/**
	 * Test check box panel.
	 */
	@Test
	public void testDataTableTextFieldPanel() {
		@SuppressWarnings("unchecked")
		DataTableTextFieldPanel<BeanWith1Field> tested = startAndTestComponent(DataTableTextFieldPanel.class);
		assertTrue(tested.get("textField") instanceof TextField); //$NON-NLS-1$
	}
}