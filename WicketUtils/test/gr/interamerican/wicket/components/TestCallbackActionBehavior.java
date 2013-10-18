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
package gr.interamerican.wicket.components;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.wicket.callback.CallbackAction;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Test;

/**
 * Tests for {@link CallbackActionBehavior}.
 */
public class TestCallbackActionBehavior {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		CallbackAction action = mock(CallbackAction.class);
		String event = "onUpdate"; //$NON-NLS-1$
		CallbackActionBehavior beh = new CallbackActionBehavior(event, action);
		assertEquals(event, beh.getEvent());
		assertEquals(action, beh.callbackAction);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testOnUpdate() {
		CallbackAction action = mock(CallbackAction.class);
		String event = "onUpdate"; //$NON-NLS-1$
		CallbackActionBehavior beh = new CallbackActionBehavior(event, action);
		AjaxRequestTarget target = mock(AjaxRequestTarget.class);		
		beh.onUpdate(target);
		verify(action, times(1)).callBack(target);
	}
 
}
