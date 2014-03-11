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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link AbstractCallbackAction}.
 */
public class TestAbstractCallbackAction {
	/**
	 * Action to test.
	 */
	AbstractCallbackAction action = new AbstractCallbackAction() {		
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;
		
		public void callBack(AjaxRequestTarget target, Form<?> form) {
			/* empty */
		}		
		public void callBack(AjaxRequestTarget target) {
			/* empty */
		}
	};
	
	/**
	 * Unit test for setCaller.
	 */
	@Test
	public void testSetCaller() {
		Form<Object> form = new Form<Object>("formId"); //$NON-NLS-1$		
		action.setCaller(form);
		Assert.assertEquals(form, action.caller);
	}
	
	/**
	 * Unit test for setCaller.
	 */
	@Test
	public void testGetCaller() {
		Form<Object> form = new Form<Object>("formId"); //$NON-NLS-1$
		action.caller = form;
		Assert.assertEquals(form, action.getCaller());
	}

}
