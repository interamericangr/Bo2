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

import static org.junit.Assert.assertEquals;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.CheckBox;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests the {@link CheckBoxPanel}.
 */
public class TestCheckBoxPanel extends WicketTest {

	@Override
	protected Component initializeComponent(String wicketId) {
		return new CheckBoxPanel(wicketId, true);
	}

	/**
	 * Test check box panel.
	 */
	@Test
	public void testCheckBoxPanel(){
		CheckBoxPanel tested = startAndTestComponent(CheckBoxPanel.class);
		CheckBox checkBox = (CheckBox) tested.get("checkboxId"); //$NON-NLS-1$
		assertEquals(true , checkBox.getDefaultModelObject());		
	}
}