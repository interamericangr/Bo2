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

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import gr.interamerican.wicket.utils.WicketPage;

/**
 * The Class TestDataTableLinkPanel.
 */
@Deprecated
public class TestDataTableLinkPanel {

	/** the WicketTester. */
	public WicketTester wicketTester = null;


	/**
	 * Sets the up.
	 */
	@Before
	public void setUp(){	
		wicketTester = new WicketTester();	
	}

	
	/**
	 * Tests the Page.
	 */
	@Test
	public void testPage(){		
		wicketTester.startPage(WicketPage.class);
		wicketTester.assertRenderedPage(WicketPage.class);		
	}
	
	/**
	 * Test data table link panel.
	 */
	@Test
	public void testDataTableLinkPanel(){
		wicketTester.startPage(WicketPage.class);
		wicketTester.assertRenderedPage(WicketPage.class);	
		wicketTester.clickLink("dateTableLinkPanel:editItemLink",true); //$NON-NLS-1$
		wicketTester.clickLink("dateTableLinkPanel1:editItemLink",true); //$NON-NLS-1$
	}
}