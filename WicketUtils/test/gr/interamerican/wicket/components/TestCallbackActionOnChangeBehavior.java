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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Test;

import gr.interamerican.wicket.callback.MockedCallback;

/**
 * Unit tests for {@link CallbackActionOnChangeBehavior}
 */
public class TestCallbackActionOnChangeBehavior {

	/**
	 * Test method for {@link gr.interamerican.wicket.components.CallbackActionOnChangeBehavior#CallbackActionOnChangeBehavior(gr.interamerican.wicket.callback.LegacyCallbackAction)}.
	 */
	@Test
	public void testCallbackActionOnChangeBehavior() {
		MockedCallback sampleCallback = new MockedCallback();
		CallbackActionOnChangeBehavior tested = new CallbackActionOnChangeBehavior(sampleCallback);
		
		assertNotNull(tested);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.components.CallbackActionOnChangeBehavior#onUpdate(org.apache.wicket.ajax.AjaxRequestTarget)}.
	 */
	@Test
	public void testOnUpdateAjaxRequestTarget() {
		MockedCallback sampleCallback = new MockedCallback();
		CallbackActionOnChangeBehavior tested = new CallbackActionOnChangeBehavior(sampleCallback);
		
		tested.onUpdate(mock(AjaxRequestTarget.class));
		assertTrue(sampleCallback.isExecuted());
	}

}
