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
package gr.interamerican.wicket.bo2.markup.html.panel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.wicket.callback.CallbackAction;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link CallbackActionPanel}.
 */
public class TestCallbackActionPanel {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		String id = "id"; //$NON-NLS-1$
		List<CallbackAction> actions = new ArrayList<CallbackAction>();		
		CallbackActionPanel panel = new CallbackActionPanel(id, actions);
		Assert.assertEquals(id, panel.getId());
		Assert.assertEquals(actions, panel.actions);
	}
	
	/**
	 * Tests callback(target).
	 */
	@Test
	public void testCallback() {
		String id = "id"; //$NON-NLS-1$
		List<CallbackAction> actions = new ArrayList<CallbackAction>();		
		CallbackActionPanel panel = new CallbackActionPanel(id, actions);
		CallbackAction action = mock(CallbackAction.class);
		AjaxRequestTarget target = mock(AjaxRequestTarget.class);
		
		panel.callback(action, target);
		verify(action,times(1)).callBack(target);
		
	}

}
