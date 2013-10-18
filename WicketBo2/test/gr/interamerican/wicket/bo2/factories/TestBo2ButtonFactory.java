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
package gr.interamerican.wicket.bo2.factories;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.def.FeedbackOwner;
import gr.interamerican.wicket.markup.html.TestPage;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

/**
 * 
 */
public class TestBo2ButtonFactory extends Bo2WicketTest{


	/**
	 * Test createButton
	 */
	@Test
	public void testCreateButton(){
		String messageHandler = "feedbackOwnerMethod"; //$NON-NLS-1$
		IModel <String> model = new Model<String>();
		FeedbackOwner owner = new SampleFeedbackOwner();
		Component component = Bo2ButtonFactory.createButton(TestPage.TEST_ID,model, messageHandler, owner);
		tester.startPage(testPageSource(component));
		tester.assertComponent(path(StringConstants.EMPTY), AjaxButton.class); 
	}
	
	
	/**
	 * Test createButton with three arguments
	 */
	@Test
	public void testCreateButton_threeArgs(){
		String messageHandler = "feedbackOwnerMethod"; //$NON-NLS-1$
		FeedbackOwner owner = new SampleFeedbackOwner();
		Component component = Bo2ButtonFactory.createButton(TestPage.TEST_ID, messageHandler, owner);
		tester.startPage(testPageSource(component));
		tester.assertComponent(path(StringConstants.EMPTY), AjaxButton.class); 
	}
	
	
	
	/**
	 * SampleFeedbackOwner
	 */
	private class SampleFeedbackOwner implements FeedbackOwner{

		public FeedbackPanel getFeedBackPanel() {
			FeedbackPanel panel = new FeedbackPanel("testPanel"); //$NON-NLS-1$
			return panel;
		}
		
		/**
		 * Test method
		 */
		@SuppressWarnings("unused")
		private void feedbackOwnerMethod(){/*empty*/}
		
	}
}


