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

import gr.interamerican.wicket.utils.WicketPage;

import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * 
 *
 */
public class TestDataTableRadioButtonPanel {


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
		wicketTester.startPage(WicketPage.class);
		wicketTester.assertRenderedPage(WicketPage.class);	
	}



	@SuppressWarnings("rawtypes")
	@Test
	public void testDataTableRadioButtonPanel(){

		Radio radioChoice =(Radio)wicketTester.
		getComponentFromLastRenderedPage("radioGroup:dataTableRadioButtonPanel:radioButton");

		//TODO: Find a way to assert the value of the radio
	}


}
