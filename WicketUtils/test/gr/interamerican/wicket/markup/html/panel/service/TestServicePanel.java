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

import static org.junit.Assert.assertNotNull;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDefImpl;
import gr.interamerican.wicket.test.WicketTest;

import org.junit.Before;
import org.junit.Test;

/**
 * The Class {@link ServicePanel}
 */
public class TestServicePanel extends WicketTest {

	/** ServicePanelWithBackDefImpl. */
	private ServicePanelWithBackDefImpl impl = new ServicePanelWithBackDefImpl();

	/** panel. */
	private ServicePanel panel;

	/**
	 * Init.
	 */
	@Before
	public void Init() {
		impl.setWicketId("wicketId"); //$NON-NLS-1$
		panel = new EmptyServicePanel(impl);
	}

	/**
	 * Test GetDefinition.
	 */
	@Test
	public void testGetDefinition() {
		assertNotNull(panel.getDefinition());
	}
}