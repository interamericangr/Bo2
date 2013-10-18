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

import gr.interamerican.wicket.test.WicketTest;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link AbstractCallbackAction}.
 */
public class TestSimpleCallbackAction 
extends WicketTest {
	
	/**
	 * flag.
	 */
	boolean flag = false;
	
	/**
	 * Action to test.
	 */
	SimpleCallbackAction action = new SimpleCallbackAction() {		
		@Override 
		protected void work() {
			flag = true;
		}
	};
	
	/**
	 * Unit test for setCaller.
	 */
	@Test
	public void testCallback_withTwo() {
		flag = false;
		Page page = homePage();
		AjaxRequestTarget target = new AjaxRequestTarget(page); 
		Form<Object> form = new Form<Object>("formId"); //$NON-NLS-1$
		action.callBack(target, form);
		Assert.assertTrue(flag);
	}
	
	/**
	 * Unit test for setCaller.
	 */
	@Test
	public void testCallback_withOne() {
		flag = false;
		Page page = homePage();
		AjaxRequestTarget target = new AjaxRequestTarget(page);
		action.callBack(target);
		Assert.assertTrue(flag);
	}
	

}
