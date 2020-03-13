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

import static org.junit.Assert.*;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Test;

/**
 * Unit test of {@link MockedCallback}.
 */
public class TestMockedCallback {

	/**
	 * Test method for {@link MockedCallback#invoke(AjaxRequestTarget)}.
	 * @throws Exception 
	 */
	@Test
	public void testCallBackAjaxRequestTarget() throws Exception {
		MockedCallback tested = new MockedCallback();
		assertFalse(tested.isExecuted());
		tested.invoke(null);
		assertTrue(tested.isExecuted());
	}
}