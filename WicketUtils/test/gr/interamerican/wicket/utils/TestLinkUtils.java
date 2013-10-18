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
package gr.interamerican.wicket.utils;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * 
 *
 */
public class TestLinkUtils {
	
	
	
	/**
	 * the WicketTester
	 */
	public WicketTester wicketTester = null;


	/**
	 * 
	 */
	@Before
	public void setUp(){	
		wicketTester = new WicketTester();	
	}

	
	/**
	 * Tests the Page
	 */
	@Test
	public void testPage(){		
		wicketTester.startPage(WicketPage.class);
		wicketTester.assertRenderedPage(WicketPage.class);		
	}
	
	@Test
	public void testCreateLink(){
		wicketTester.startPage(WicketPage.class);	
		wicketTester.assertComponent("link", Link.class);
        wicketTester.clickLink("link");		     
	}
	
	@Test
	public void testCreateLink1(){
		wicketTester.startPage(WicketPage.class);	
		wicketTester.assertComponent("gr.interamerican.wicket.utils.WicketPage", Link.class);
        wicketTester.clickLink("gr.interamerican.wicket.utils.WicketPage");		     
	}
	
	@Test
	public void testCreateLink2(){
		wicketTester.startPage(WicketPage.class);	
		wicketTester.assertComponent("link2", Link.class);
        wicketTester.clickLink("link2");		     
	}
	
	
	
	
	
	

}
