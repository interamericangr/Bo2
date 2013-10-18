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
package gr.interamerican.wicket.bo2.markup.html.form;

import gr.interamerican.bo2.utils.DateUtils;

import java.util.Calendar;

import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;



/**
 * Unit and integration tests for self drawn components,
 * form and panel.
 */
@SuppressWarnings("nls")
public class TestSelfDrawnComponents {

	/**
	 * the WicketTester
	 */
	private static WicketTester wicketTester = null;

	/**
	 * 
	 */
	@BeforeClass
	public static void setUp(){	
		wicketTester = new WicketTester();	
		wicketTester.startPage(SelfDrawnTestPage.class);
		wicketTester.assertRenderedPage(SelfDrawnTestPage.class);	
	}



	
	
	
	
	
	

	/**
	 * 
	 */
	@Test
	public void testSelfDrawnLabel(){
		Label component =(Label)wicketTester.
		getComponentFromLastRenderedPage("selfDrawnLabel");
		Assert.assertEquals(component.getDefaultModelObject(),"This is a test"); 		
	}

	


	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test 
	public void testSelfDrawnTextArea(){
		TextArea<String> component =(TextArea<String>)wicketTester.
		getComponentFromLastRenderedPage("selfDrawnStringTextArea");
		Assert.assertEquals(component.getDefaultModelObject(),"This is a test"); 		
	}

	
	
	
	
	/**
	 * 
	 */
	@Test
	public void testSelfDrawnBooleanCheckBox(){
		CheckBox component =(CheckBox)wicketTester.
		getComponentFromLastRenderedPage("selfDrawnBooleanCheckBox");
		Assert.assertEquals(component.getDefaultModelObject(),new Boolean(true)); 		
	}

	/**
	 * 
	 */
	@Test
	public void testSelfDateField(){
		DateField component =(DateField)wicketTester.
		getComponentFromLastRenderedPage("selfDrawnDateField");
		Assert.assertEquals(component.getDefaultModelObject(),DateUtils.getDate(2011, Calendar.NOVEMBER, 25)); 		
	}

	

		
	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testSelfDropDownChoice(){
		DropDownChoice component =(DropDownChoice)wicketTester.
		getComponentFromLastRenderedPage("selfDrawnDrowDownChoice");
		Assert.assertEquals(component.getChoices().size(),1); 
		DropDownChoice component1 =(DropDownChoice)wicketTester.
		getComponentFromLastRenderedPage("selfDrawnDrowDownChoice1");
		Assert.assertEquals(component1.getChoices().size(),1); 				
	}


	
	
	
	

}
