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

import static org.junit.Assert.assertEquals;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * 
 *
 */
public class TestWicketUtils {	
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

	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testWicketUtils(){	
		FeedbackPanel fdp = (FeedbackPanel)wicketTester.
		getComponentFromLastRenderedPage("feedbackPanel");		 //$NON-NLS-1$
		Assert.assertFalse(fdp.anyMessage());
				
		TextField<String> textField=(TextField<String>)wicketTester.
		getComponentFromLastRenderedPage("form2:field");		 //$NON-NLS-1$
		Assert.assertTrue(textField.isVisible());		
		Assert.assertTrue(textField.isRenderAllowed());

	}
	
	
	/**
	 * Test ValidateNotNull
	 */
	@Test
	public void testValidateNotNull(){
		Component component = new EmptyPanel("panelid");  //$NON-NLS-1$
		IModel<Void> model = null; 
		DropDownChoice<String> formComponent = new DropDownChoice("id",model); //$NON-NLS-1$
		WicketUtils.validateNotNull(component, formComponent);
		assertEquals(1,component.getSession().getFeedbackMessages().size());
	}
	
	
	/**
	 * Test ValidateNotNull
	 */
	@Test
	public void testValidateNotNull_withTreeArgs(){
		Component component = new EmptyPanel("panelid");  //$NON-NLS-1$
		IModel<Void> model = null; 
		DropDownChoice <String> formComponent = new DropDownChoice("id",model); //$NON-NLS-1$
		String resourceKey = "1"; //$NON-NLS-1$
		WicketUtils.validateNotNull(component,resourceKey,formComponent);
		assertEquals(1,component.getSession().getFeedbackMessages().size());
	}


	/**
	 * Test setEnabled
	 * 
	 */
	@Test
	public void testSetEnabled() {
		wicketTester.startPage(WicketPage.class);
		wicketTester.assertRenderedPage(WicketPage.class);		
		Form form = (Form)wicketTester.getComponentFromLastRenderedPage("form2");
		TextField<Double> doubleTextFieldWithoutBinding = (TextField<Double>)form.get("field");
		FormTester formTester = wicketTester.newFormTester("form2");
		WicketUtils.setEnabled(true, doubleTextFieldWithoutBinding);
		wicketTester.assertEnabled("form2:field");
		
		WicketUtils.setEnabled(false, doubleTextFieldWithoutBinding);
		wicketTester.assertDisabled("form2:field");
		
	}

	/**
	 *Test setVisible
	 * 
	 */
	@Test
	public void testSetVisible() {
		wicketTester.startPage(WicketPage.class);
		wicketTester.assertRenderedPage(WicketPage.class);		
		Form form = (Form)wicketTester.getComponentFromLastRenderedPage("form2");
		TextField<Double> doubleTextFieldWithoutBinding = (TextField<Double>)form.get("field");
		FormTester formTester = wicketTester.newFormTester("form2");
		WicketUtils.setVisible(true, doubleTextFieldWithoutBinding);
		wicketTester.assertVisible("form2:field");
		
		WicketUtils.setVisible(false, doubleTextFieldWithoutBinding);
		wicketTester.assertInvisible("form2:field");
	}
}
