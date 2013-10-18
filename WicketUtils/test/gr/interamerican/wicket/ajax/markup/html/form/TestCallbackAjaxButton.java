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
import gr.interamerican.wicket.utils.WicketPage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.junit.Test;

/**
 * 
 */
public class TestCallbackAjaxButton extends WicketTest{

	
	/**
	 * CallbackAjaxButton to test
	 */
	private CallbackAjaxButton button;
	
	/**
	 * id
	 */
	private String id = "id"; //$NON-NLS-1$
	
	/**
	 * model
	 */
	private Model<String> model = new Model<String>();
	
	/**
	 * action
	 */
	private DummyCallback action = new DummyCallback();
	
	/**
	 * Test Constructor
	 */
	@Test
	public void testConstructor(){
		button = new CallbackAjaxButton(id,model,action);
		assertEquals(button.action,action);
	}
	
	
	/**
	 * Test OnError
	 */
	@Test
	public void testOnError(){
		WicketPage page = new WicketPage();
		AjaxRequestTarget target = new AjaxRequestTarget(page);
		Form<Void> form = new Form <Void>("id"); //$NON-NLS-1$
		FeedbackPanel panel = new FeedbackPanel("panelId"); //$NON-NLS-1$
		button = new CallbackAjaxButton(id,model,action,panel);
		panel.setOutputMarkupId(true);
		button.onError(target, form);
		assertEquals(1,target.getComponents().size());
	}
}
