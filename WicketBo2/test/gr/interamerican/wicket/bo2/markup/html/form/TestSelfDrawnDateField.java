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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.meta.descriptors.DateBoPropertyDescriptor;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link SelfDrawnDateField}.
 */
public class TestSelfDrawnDateField extends WicketTest {

	/**
	 * Test creation.
	 */
	@Test
	public void testConstructor_noModel_noDefault() {
		DateBoPropertyDescriptor descriptor = new DateBoPropertyDescriptor();
		SelfDrawnDateField field = new SelfDrawnDateField(TestPage.TEST_ID, new Model<Date>(), descriptor);
		tester.startPage(getTestPage(field));
		Assert.assertSame(field, tester.getComponentFromLastRenderedPage(subjectPath()));
		Assert.assertNull(field.getDefaultModelObject());
		testFormSubmission(field);

		testFormSubmission_error();
	}

	/**
	 * Test form submission.
	 *
	 * @param dateField
	 *            the date field
	 */
	private void testFormSubmission(DateField dateField) {
		FormTester formTester = tester.newFormTester(formPath());
		String datePathId = TestPage.TEST_ID + ":date"; //$NON-NLS-1$

		String date = DateFormat.getDateInstance(DateFormat.SHORT).format(DateUtils.getMonth1st(1981, Calendar.APRIL));
		formTester.setValue(datePathId, date);
		formTester.submit();

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MONTH, Calendar.APRIL);
		c.set(Calendar.YEAR, 1981);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Assert.assertEquals(c.getTime(), dateField.getDefaultModelObject());

		commonAssertions_noError();
	}

	/**
	 * Use 01/04/1980 to see the joda time date conversion issue Cannot parse
	 * "01/04/1980": Illegal instant due to time zone offset transition
	 * (Europe/Athens).
	 */
	private void testFormSubmission_error() {
		FormTester formTester = tester.newFormTester(formPath());
		String datePathId = TestPage.TEST_ID + ":date"; //$NON-NLS-1$
		String badDate = "01/04/1980"; //$NON-NLS-1$
		String label = "date"; //$NON-NLS-1$
		formTester.setValue(datePathId, badDate);
		formTester.submit();

		commonAssertions_error(label);
	}

	/**
	 * Test getInternalDateTextField.
	 */
	@SuppressWarnings("cast")
	@Test
	public void testGetInternalDateTextField() {
		DateBoPropertyDescriptor descriptor = new DateBoPropertyDescriptor();
		SelfDrawnDateField field = new SelfDrawnDateField(TestPage.TEST_ID, new Model<Date>(), descriptor);
		tester.startPage(getTestPage(field));
		Assert.assertTrue(field.getInternalDateTextField() instanceof DateTextField);
	}

}
