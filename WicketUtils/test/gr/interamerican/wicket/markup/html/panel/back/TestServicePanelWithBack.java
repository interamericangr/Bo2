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
package gr.interamerican.wicket.markup.html.panel.back;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.markup.html.BaseTestPage;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.samples.actions.DummyCallback;
import gr.interamerican.wicket.test.WicketTest;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.util.tester.ITestPageSource;
import org.junit.Test;

/**
 * Unit test for {@link ServicePanelWithBack}.
 */
@SuppressWarnings("nls")
public class TestServicePanelWithBack 
extends WicketTest {
	
	/**
	 * Creates a sample definition.
	 * 
	 * @return returns a definition.
	 */	
	ServicePanelWithBackDef createDef() {
		ServicePanelWithBackDef def = new ServicePanelWithBackDefImpl();
		def.setBackAction(null);		
		def.setWicketId(BaseTestPage.TEST_ID);
		return def;
	}
	
	
	/**
	 * Creates a page source
	 * 
	 * @param def
	 * @return Returns a page source.
	 */
	@SuppressWarnings("serial")
	ITestPageSource pageSource(final ServicePanelWithBackDef def) {		
		return new ITestPageSource() {
			public Page getTestPage() {
				ServicePanelWithBack panel = new ServicePanelWithBack(def);
				return new TestPage(panel);
			}
		};
		
	}
	
	
	/**
	 * Tests creation of {@link ServicePanelWithBack}.
	 */	
	@Test
	public void testCreation_withCallback() {	
		final CallbackAction action = new DummyCallback();
		final ServicePanelWithBackDef def = createDef();
		def.setBackAction(action);
		ITestPageSource source = pageSource(def);
		
		tester.startPage(source);
		tester.assertRenderedPage(TestPage.class);
		tester.assertComponent(path("backForm"),Form.class);
		
		tester.assertComponent(path("backForm:backButton"),CallbackAjaxButton.class);
		tester.assertVisible(path("backForm:backButton"));			
		CallbackAjaxButton button = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("backForm:backButton"));
		assertSame(action, button.getAction());
	}
	
	/**
	 * Tests creation of {@link ServicePanelWithBack}.
	 */	
	@Test
	public void testCreation_withoutCallback() {
		final ServicePanelWithBackDef def = createDef();			
		ITestPageSource source = pageSource(def);	
		tester.startPage(source);
		tester.assertRenderedPage(TestPage.class);
		tester.assertInvisible(path("backForm"));
		tester.assertInvisible(path("backForm:backButton"));
		
		//since this was not rendered it is null??
		CallbackAjaxButton button = (CallbackAjaxButton) 
			tester.getComponentFromLastRenderedPage(path("backForm:backButton"));
		assertNull(button);

	}
	
	
	/**
	 * Test ValidateDef
	 */
	@Test(expected = RuntimeException.class)
	public void testValidateDef(){
		ServicePanelWithBackDef def = new ServicePanelWithBackDefImpl();
		def.setWicketId("wicketId");
		ServicePanelWithBack panel = new ServicePanelWithBack(def);
		def.setWicketId(null);
		panel.validateDef();
	}
	
	

}
