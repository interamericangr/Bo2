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
package gr.interamerican.wicket.ajax.markup.html.form;

import static org.junit.Assert.assertEquals;
import gr.interamerican.wicket.samples.actions.DummyCallback;
import gr.interamerican.wicket.test.WicketTest;

import org.apache.wicket.model.Model;
import org.junit.Test;

/**
 * Unit test for {@link CallbackAjaxLink}.
 */
public class TestCallbackAjaxLink extends WicketTest{

	
	/**
	 * CallbackAjaxButton to test
	 */
	private CallbackAjaxLink<String> link;
	
	/**
	 * id
	 */
	private String id = "id"; //$NON-NLS-1$
	
	/**
	 * model
	 */
	private Model<String> model = new Model<String>("link label"); //$NON-NLS-1$
	
	/**
	 * action
	 */
	private DummyCallback action = new DummyCallback();
	
	/**
	 * Test Constructor
	 */
	@Test
	public void testConstructor(){
		link = new CallbackAjaxLink<String>(id,action);
		link.setModel(model);
		assertEquals(link.action,action);
	}
	
}
