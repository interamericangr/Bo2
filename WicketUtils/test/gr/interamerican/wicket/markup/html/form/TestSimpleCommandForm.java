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
package gr.interamerican.wicket.markup.html.form;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.utils.attributes.SimpleCommand;

import org.apache.wicket.model.IModel;
import org.junit.Test;

/**
 * Tests for {@link SimpleCommandForm}.
 */
public class TestSimpleCommandForm {
	
	/**
	 * Test for the constructor.
	 */
	@Test
	public void testConstructor_withoutModel() {
		SimpleCommand cmd = mock(SimpleCommand.class);
		String id = "ID"; //$NON-NLS-1$
		SimpleCommandForm<Void> form = new SimpleCommandForm<Void>(id,cmd);
		assertEquals(id, form.getId());
		assertEquals(cmd, form.action);
	}
	
	/**
	 * Test for the constructor.
	 */
	@Test
	public void testConstructor_withModel() {
		SimpleCommand cmd = mock(SimpleCommand.class);
		String id = "ID"; //$NON-NLS-1$
		@SuppressWarnings("unchecked") IModel<Object> model = mock(IModel.class);
		SimpleCommandForm<Object> form = new SimpleCommandForm<Object>(id, model,cmd);		
		assertEquals(id, form.getId());
		assertEquals(model, form.getModel());
		assertEquals(cmd, form.action);
	}
	
	/**
	 * Test for the constructor.
	 */
	@Test
	public void testOnSubmit() {
		SimpleCommand cmd = mock(SimpleCommand.class);
		String id = "ID"; //$NON-NLS-1$
		SimpleCommandForm<Object> form = new SimpleCommandForm<Object>(id, cmd);
		form.onSubmit();
		verify(cmd, times(1)).execute();
	}


}
