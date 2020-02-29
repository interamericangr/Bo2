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
package gr.interamerican.wicket.markup.html.panel.service;

import org.apache.wicket.Component;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;

/**
 * Test {@link EmptyServicePanel}.
 */
public class TestEmptyServicePanel extends WicketTest {

	/**
	 * Test creation.
	 */
	@Test
	public void testCreation() {
		tester.startPage(getTestPage());
		commonAssertions_noError();
	}

	@Override
	protected Component initializeComponent(String wicketId) {
		ServicePanelDef def = new ServicePanelDefImpl();
		def.setWicketId(wicketId);
		return new EmptyServicePanel(def);
	}
}