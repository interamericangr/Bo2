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

import static org.junit.Assert.assertEquals;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBack;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDefImpl;
import gr.interamerican.wicket.test.WicketTest;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for ServicePanelDefImpl
 */
public class TestServicePanelDefImpl extends WicketTest{

	
	/**
	 * panel to test
	 */
	ServicePanelDefImpl panel = new ServicePanelDefImpl();
	
	/**
	 * WICKET_ID
	 */
	private static final String WICKET_ID = "wicketId"; //$NON-NLS-1$
	
	/**
	 * ServicePanelWithBackDefImpl
	 */
	static ServicePanelWithBackDefImpl impl = new ServicePanelWithBackDefImpl();
	
	/**
	 * SERVICE_PANEL
	 */
	private static ServicePanelWithBack SERVICE_PANEL; 

	
	/**
	 * Initialize
	 */
	@Before
	public void init(){
		impl.setWicketId("wicketId"); //$NON-NLS-1$
		SERVICE_PANEL = new ServicePanelWithBack(impl);
		
	}
	
	/**
	 * Test setWicketId
	 */
	@Test
	public void testSetWicketId(){
		panel.setWicketId(WICKET_ID);
		assertEquals(WICKET_ID,panel.wicketId);
	}
	
	/**
	 * Test getWicketId
	 */
	@Test
	public void testGetWicketId(){
		panel.wicketId = WICKET_ID;
		assertEquals(WICKET_ID,panel.getWicketId());
	}
	
	/**
	 * Test setServiceId
	 */
	@Test
	public void testSetServicePanel(){
		panel.setServicePanel(SERVICE_PANEL);
		assertEquals(SERVICE_PANEL,panel.servicePanel);
	}
	
	/**
	 * Test getServiceId
	 */
	@Test
	public void testGetServicePanel(){
		panel.servicePanel = SERVICE_PANEL;
		assertEquals(SERVICE_PANEL,panel.getServicePanel());
	}
	

	
}
