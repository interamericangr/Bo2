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
import org.apache.wicket.model.Model;
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
	 * Model.
	 */
	private Model<String> model = new Model<String>();
	
	/**
	 * Initializes the descriptor.
	 */
	@Before
	@SuppressWarnings("nls")
	public void before() {
		descriptor.setMaxLength(20);
		descriptor.setMinLength(0);
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
		SelfDrawnStringTextField tf = (SelfDrawnStringTextField) getTestSubject();
		
		assertions(null);
		Assert.assertTrue(tf.getConvertEmptyInputStringToNull());
	}
	
	/**
	 * Test creation with default value.
	 */
	@Test
	public void testCreation_withDefault() {
		descriptor.setHasDefault(true);
		descriptor.setDefaultValue(StringConstants.EMPTY);
		tester.startPage(getTestPage());		
		assertions(StringConstants.EMPTY);
	}
	
	/**
	 * testEmptyInputSubmitsNull.
	 */
	@Test
	public void testEmptyInputSubmitsNull() {
		tester.startPage(getTestPage());
		SelfDrawnStringTextField tf = (SelfDrawnStringTextField) getTestSubject();
		
		assertions(null);
		Assert.assertTrue(tf.getConvertEmptyInputStringToNull());
		
		FormTester formTester = tester.newFormTester(formPath());
		String expected = "hello123456"; //$NON-NLS-1$
		formTester.setValue(TestPage.TEST_ID, expected);
		formTester.submit();
		assertions(expected);
		
		formTester = tester.newFormTester(formPath());
		formTester.setValue(TestPage.TEST_ID, StringConstants.SPACE);
		formTester.submit();
		assertions(null);
	}
	
	/**
	 * testEmptyInputSubmitsNull.
	 */
	@Test
	public void testEmptyInputSubmitsEmpty() {
		tester.startPage(getTestPage());
		SelfDrawnStringTextField tf = (SelfDrawnStringTextField) getTestSubject();
		
		assertions(null);
		
		tf.setConvertEmptyInputStringToNull(false);
		Assert.assertFalse(tf.getConvertEmptyInputStringToNull());
		
		FormTester formTester = tester.newFormTester(formPath());
		formTester = tester.newFormTester(formPath());
		formTester.setValue(TestPage.TEST_ID, StringConstants.SPACE);
		formTester.submit();
		assertions(StringConstants.EMPTY);
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
	
	/**
	 * Common assertions.
	 *
	 * @param modelObject the model object
	 */
	void assertions(String modelObject) {
		tester.assertComponent(subjectPath(), SelfDrawnStringTextField.class);
		SelfDrawnStringTextField subject = (SelfDrawnStringTextField) tester.getComponentFromLastRenderedPage(subjectPath());
		Assert.assertFalse(subject.isRequired());
		Assert.assertTrue(model == subject.getModel());
		Assert.assertTrue(model == subject.getDefaultModel());
		Assert.assertEquals(modelObject, subject.getModelObject());
	}
	
	@Override
	protected Component initializeComponent(String wicketId) {
		return new SelfDrawnStringTextField(wicketId, model, descriptor);
	}

}
