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

import java.math.BigInteger;
import java.util.HashSet;

import org.apache.wicket.Component;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;

/**
 * Test for {@link TextFieldChoicesPanel}
 */
public class TestTextFieldChoicesPanel extends WicketTest {

	/**
	 * Test method for {@link TextFieldChoicesPanel}
	 */
	@Test
	public void testTextFieldChoicesPanel() {
		startAndTestComponent(TextFieldChoicesPanel.class);
	}

	@Override
	protected Component initializeComponent(String wicketId) {
		return new TextFieldChoicesPanel<BigInteger>(wicketId, Model.ofSet(new HashSet<>()), "testLabelValue", //$NON-NLS-1$
				BigInteger.class);
	}
}
