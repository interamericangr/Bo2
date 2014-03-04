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

import gr.interamerican.bo2.utils.meta.descriptors.IntegerBoPropertyDescriptor;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link SelfDrawnBigDecimalTextField}.
 */
public class TestSelfDrawnIntegerTextField extends WicketTest {
	
	/**
	 * Test creation.
	 */
	@Test
	public void testConstructor_noModel_noDefault() {
		IntegerBoPropertyDescriptor descriptor = new IntegerBoPropertyDescriptor();
		SelfDrawnIntegerTextField field = 
			new SelfDrawnIntegerTextField(TestPage.TEST_ID, descriptor);
		tester.startPage(getTestPage(field));
		Assert.assertSame(field,tester.getComponentFromLastRenderedPage(subjectPath()));
		Assert.assertNull(field.getDefaultModelObject());
		
		testFormSubmission(field);
	}
	
	/**
	 * Test creation.
	 */
	@Test
	public void testConstructor_withModel_noDefault() {
		IntegerBoPropertyDescriptor descriptor = new IntegerBoPropertyDescriptor();
		SelfDrawnIntegerTextField field = 
			new SelfDrawnIntegerTextField(TestPage.TEST_ID, new Model<Integer>(), descriptor);
		tester.startPage(getTestPage(field));
		Assert.assertSame(field,tester.getComponentFromLastRenderedPage(subjectPath()));
		Assert.assertNull(field.getDefaultModelObject());
		
		testFormSubmission(field);
	}
	
	
	
	
	/**
	 * Test creation with default value.
	 */
	@Test
	public void testConstructor_noModel_defaultValue() {
		IntegerBoPropertyDescriptor descriptor = new IntegerBoPropertyDescriptor();
		descriptor.setHasDefault(true);
		Integer defaultValue = 18;
		descriptor.setDefaultValue(defaultValue);
		SelfDrawnIntegerTextField field = 
			new SelfDrawnIntegerTextField(TestPage.TEST_ID, descriptor);
		tester.startPage(getTestPage(field));
		Assert.assertSame(field,tester.getComponentFromLastRenderedPage(subjectPath()));
		
		//field = new SelfDrawnBigDecimalTextField(TestPage.TEST_ID, new Model<BigDecimal>(), descriptor);
		
		Assert.assertEquals(defaultValue, field.getDefaultModelObject());
		
		testFormSubmission(field);
	}
	
	/**
	 * Test creation with default value.
	 */
	@Test
	public void testConstructor_withModel_defaultValue() {
		IntegerBoPropertyDescriptor descriptor = new IntegerBoPropertyDescriptor();
		descriptor.setHasDefault(true);
		Integer defaultValue = 5;
		descriptor.setDefaultValue(defaultValue);
		SelfDrawnIntegerTextField field = 
			new SelfDrawnIntegerTextField(TestPage.TEST_ID, new Model<Integer>(), descriptor);
		tester.startPage(getTestPage(field));
		Assert.assertSame(field,tester.getComponentFromLastRenderedPage(subjectPath()));
		Assert.assertEquals(defaultValue, field.getDefaultModelObject());
		
		testFormSubmission(field);
	}
	
	/**
	 * @param textField
	 */
	private void testFormSubmission(TextField<Integer> textField) {
		FormTester formTester = tester.newFormTester(formPath());
		formTester.setValue(TestPage.TEST_ID, "10"); //$NON-NLS-1$
		formTester.submit();
		Assert.assertEquals(new Integer(10), textField.getModelObject());
	}

}
