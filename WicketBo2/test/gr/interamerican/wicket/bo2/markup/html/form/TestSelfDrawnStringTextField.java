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
package gr.interamerican.wicket.bo2.markup.html.form;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link SelfDrawnStringTextField}.
 */
public class TestSelfDrawnStringTextField extends WicketTest {
	
	/**
	 * Descriptor.
	 */
	private StringBoPropertyDescriptor descriptor = new StringBoPropertyDescriptor();
	
	/**
	 * Initializes the descriptor.
	 */
	@Before
	@SuppressWarnings("nls")
	public void before() {
		descriptor.setMaxLength(20);
		descriptor.setMinLength(10);
		descriptor.setIndex(0);
		descriptor.setPackageName("gr.interamerican");
		descriptor.setName("foo");
		descriptor.setClassName("Foo");
	}

	/**
	 * Test creation with no default.
	 */
	@Test
	public void testCreation_nodefault() {
		tester.startPage(getTestPage());		
		@SuppressWarnings("unchecked")
		TextField<String> tf = (TextField<String>) getTestSubject();
		Assert.assertTrue(tf.getConvertEmptyInputStringToNull());
		Assert.assertNull(tf.getModelObject());
		Assert.assertFalse(getFeedbackPanel().anyErrorMessage());
	}
	
	/**
	 * Test creation with default value.
	 */
	@Test
	public void testCreation_withDefault() {
		descriptor.setHasDefault(true);
		descriptor.setDefaultValue(StringConstants.EMPTY);
		tester.startPage(getTestPage());		
		@SuppressWarnings("unchecked")
		TextField<String> tf = (TextField<String>) getTestSubject();		
		Assert.assertNotNull(tf.getModelObject());
		Assert.assertEquals(StringConstants.EMPTY, tf.getModelObject());
		Assert.assertFalse(getFeedbackPanel().anyErrorMessage());
	}
	
	/**
	 * Test creation with regular expression.
	 */
	@Test
	public void testCreation_withExpression() {
		String expression = "[^b]at"; //$NON-NLS-1$
		descriptor.setExpression(expression);
		tester.startPage(getTestPage());
		
		@SuppressWarnings("unchecked")
		TextField<String> tf = (TextField<String>) getTestSubject();
		
		tf.setDefaultModelObject("bat"); //$NON-NLS-1$
		
		FormTester ft = tester.newFormTester(TestPage.FORM_ID);
		ft.submit();
		
		Assert.assertTrue(getFeedbackPanel().anyErrorMessage());
		List<FeedbackMessage> messages = getFeedbackPanel().getFeedbackMessagesModel().getObject();
		Assert.assertEquals(1, messages.size());
		String msg = messages.get(0).toString();
		Assert.assertTrue(msg.contains(expression));
	}
	
	@Override
	protected Component initializeComponent() {
		return new SelfDrawnStringTextField(TestPage.TEST_ID, descriptor);
	}

}
