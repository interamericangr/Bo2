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
package gr.interamerican.wicket.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.CallbackAction;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for ButtonFactory.
 */
public class TestButtonFactory {
	
	/**
	 * Unit test for create.
	 */
	@Test
	public void testCreate_withAction() {
		String id = "buttonId"; //$NON-NLS-1$
		CallbackAction action = Mockito.mock(CallbackAction.class);
		AjaxButton button = ButtonFactory.createButton(id, action);
		assertNotNull(button);		
		assertEquals(id,button.getId());
		assertTrue(button instanceof CallbackAjaxButton);		
		CallbackAjaxButton cab = (CallbackAjaxButton) button;
		assertEquals(action,cab.getAction());
	}
	
	/**
	 * Unit test for create.
	 */
	@Test
	public void testCreate_withActionAndPanel() {
		String id = "buttonId"; //$NON-NLS-1$
		CallbackAction action = Mockito.mock(CallbackAction.class);
		FeedbackPanel panel = Mockito.mock(FeedbackPanel.class);		
		AjaxButton button = ButtonFactory.createButton(id, action, panel);
		assertNotNull(button);		
		assertEquals(id,button.getId());
		assertTrue(button instanceof CallbackAjaxButton);		
		CallbackAjaxButton cab = (CallbackAjaxButton) button;
		assertEquals(action,cab.getAction());		
	}
	
	/**
	 * Unit test for create.
	 */
	@Test
	public void testCreate_withModelActionAndPanel() {
		String id = "buttonId"; //$NON-NLS-1$
		CallbackAction action = Mockito.mock(CallbackAction.class);
		FeedbackPanel panel = Mockito.mock(FeedbackPanel.class);
		Model<String> model = new Model<String>(id);
		AjaxButton button = ButtonFactory.createButton(id, model, action, panel);
		assertNotNull(button);		
		assertEquals(id,button.getId());
		assertEquals(model,button.getModel());
		assertTrue(button instanceof CallbackAjaxButton);		
		CallbackAjaxButton cab = (CallbackAjaxButton) button;
		assertEquals(action,cab.getAction());		
	}

}
