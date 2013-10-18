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
package gr.interamerican.wicket.callback;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gr.interamerican.wicket.samples.actions.DummyCallback;
import gr.interamerican.wicket.test.WicketTest;
import gr.interamerican.wicket.utils.WicketPage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link CallbackWrapper}.
 */
public class TestCallbackWrapper extends WicketTest{
	
	/**
	 * action to test
	 */
	DummyCallback action = new DummyCallback();
	
	/**
	 * Test setup.
	 */
	@Before
	public void setup() {
		action.setExecuted(false);
	}
	
	/**
	 * Unit test for the constructor.
	 */
	@Test
	public void testConstructor_withArg() {
		CallbackWrapper wrapper = new CallbackWrapper(action);
		assertSame(action, wrapper.action);
	}

	/**
	 * Unit test for the no arg constructor.
	 */
	@Test
	public void testConstructor_withoutArg() {
		CallbackWrapper wrapper = new CallbackWrapper();
		assertNull(wrapper.action);
	}
		
	/**
	 * Test CallBack with action..
	 */
	@Test
	public void testCallBack_withAction(){
		WicketPage page = new WicketPage();
		AjaxRequestTarget target = new AjaxRequestTarget(page);
		CallbackWrapper wrapper = new CallbackWrapper(action);
		wrapper.callBack(target);
		assertTrue(action.isExecuted());
	}
	
	/**
	 * Test CallBack with action.
	 * 
	 * No assert. just make sure there is no Exception here.
	 */
	@Test
	public void testCallBack_withoutAction(){
		WicketPage page = new WicketPage();
		AjaxRequestTarget target = new AjaxRequestTarget(page);
		CallbackWrapper wrapper = new CallbackWrapper();
		wrapper.callBack(target);
	}
	
	
	/**
	 * Test CallBack with form.
	 * 
	 * No assert. just make sure there is no Exception here.
	 */
	@Test
	public void testCallBack_withForm(){
		WicketPage page = new WicketPage();
		Form<Void> form = new Form<Void>("form"); //$NON-NLS-1$
		AjaxRequestTarget target = new AjaxRequestTarget(page);
		CallbackWrapper wrapper = new CallbackWrapper();
		wrapper.callBack(target,form);
	}
	
	
	/**
	 * Test CallBack with form and action
	 */
	@Test
	public void testCallBack_withFormAndAction(){
		WicketPage page = new WicketPage();
		Form<Void> form = new Form<Void>("form"); //$NON-NLS-1$
		AjaxRequestTarget target = new AjaxRequestTarget(page);
		CallbackWrapper wrapper = new CallbackWrapper(action);
		wrapper.callBack(target,form);
	}
	
	/**
	 * Test getAction
	 */
	@Test
	public void testSetAction(){
		CallbackWrapper wrapper = new CallbackWrapper();
		wrapper.setAction(action);
		assertEquals(action,wrapper.action);
	}
	
	/**
	 * Test getAction
	 */
	@Test
	public void testGetAction(){
		CallbackWrapper wrapper = new CallbackWrapper();
		wrapper.action = action;
		assertEquals(action,wrapper.getAction());
	}
	
	
	
	
	
}
