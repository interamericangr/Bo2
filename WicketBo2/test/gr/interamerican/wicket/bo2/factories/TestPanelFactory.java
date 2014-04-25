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
package gr.interamerican.wicket.bo2.factories;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBack;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDef;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDefImpl;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;
import gr.interamerican.wicket.samples.factories.ServicePanelIdProvider;
import gr.interamerican.wicket.test.WicketTest;

import org.apache.wicket.markup.html.panel.Panel;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link PanelFactory}.
 */
public class TestPanelFactory extends WicketTest {
	
	/**
	 * Unit test for create()
	 */
	@Test
	public void testCreate() {
		ServicePanelWithBackDef def = new ServicePanelWithBackDefImpl();
		def.setWicketId("xx"); //$NON-NLS-1$
		def.setPanelId(ServicePanelIdProvider.TEST_PANEL_ID);
		Panel panel = PanelFactory.create(def);
		Assert.assertTrue(panel instanceof ServicePanelWithBack);
	}
	
	/**
	 * test registerFixture
	 */
	@Test
	public void testRegisterFixture() {
		ServicePanel fixture = Mockito.mock(ServicePanel.class);
		PanelFactory.registerFixture(StringConstants.COLON, fixture);
		ServicePanelDef def = Factory.create(ServicePanelDef.class);
		def.setPanelId(StringConstants.COLON);
		Assert.assertTrue(PanelFactory.create(def) == fixture);
	}
	
}
