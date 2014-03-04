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

import gr.interamerican.bo2.utils.meta.descriptors.LongBoPropertyDescriptor;
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
public class TestSelfDrawnLongTextField extends WicketTest {
	
	/**
	 * Test creation.
	 */
	@Test
	public void testConstructor_noModel_noDefault() {
		LongBoPropertyDescriptor descriptor = new LongBoPropertyDescriptor();
		SelfDrawnLongTextField field = 
			new SelfDrawnLongTextField(TestPage.TEST_ID, descriptor);
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
		LongBoPropertyDescriptor descriptor = new LongBoPropertyDescriptor();
		SelfDrawnLongTextField field = 
			new SelfDrawnLongTextField(TestPage.TEST_ID, new Model<Long>(), descriptor);
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
		LongBoPropertyDescriptor descriptor = new LongBoPropertyDescriptor();
		descriptor.setHasDefault(true);
		Long defaultValue = 18l;
		descriptor.setDefaultValue(defaultValue);
		SelfDrawnLongTextField field = 
			new SelfDrawnLongTextField(TestPage.TEST_ID, descriptor);
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
		LongBoPropertyDescriptor descriptor = new LongBoPropertyDescriptor();
		descriptor.setHasDefault(true);
		Long defaultValue = 5l;
		descriptor.setDefaultValue(defaultValue);
		SelfDrawnLongTextField field = 
			new SelfDrawnLongTextField(TestPage.TEST_ID, new Model<Long>(), descriptor);
		tester.startPage(getTestPage(field));
		Assert.assertSame(field,tester.getComponentFromLastRenderedPage(subjectPath()));
		Assert.assertEquals(defaultValue, field.getDefaultModelObject());
		
		testFormSubmission(field);
	}
	
	/**
	 * @param textField
	 */
	private void testFormSubmission(TextField<Long> textField) {
		FormTester formTester = tester.newFormTester(formPath());
		formTester.setValue(TestPage.TEST_ID, "10"); //$NON-NLS-1$
		formTester.submit();
		Assert.assertEquals(new Long(10), textField.getModelObject());
	}

}
