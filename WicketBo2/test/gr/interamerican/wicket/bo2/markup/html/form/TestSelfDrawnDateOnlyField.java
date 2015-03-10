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

import gr.interamerican.bo2.utils.meta.descriptors.DateBoPropertyDescriptor;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link SelfDrawnBigDecimalTextField}.
 */
public class TestSelfDrawnDateOnlyField extends WicketTest {

	/**
	 * BAD date.
	 */
	static final String badDate = "01/04/1980"; //$NON-NLS-1$

	/**
	 * Test creation.
	 */
	@Test
	public void testConstructor_noModel_noDefault() {
		DateBoPropertyDescriptor descriptor = new DateBoPropertyDescriptor();
		SelfDrawnDateOnlyField field = new SelfDrawnDateOnlyField(TestPage.TEST_ID,
				new Model<Date>(), descriptor);
		tester.startPage(getTestPage(field));
		Assert.assertSame(field, tester.getComponentFromLastRenderedPage(subjectPath()));
		Assert.assertNull(field.getDefaultModelObject());
		tester.debugComponentTrees();
		FormTester formTester = tester.newFormTester(formPath());
		String datePathId = TestPage.TEST_ID + ":date"; //$NON-NLS-1$
		formTester.setValue(datePathId, "01/04/1981"); //$NON-NLS-1$
		formTester.submit();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MONTH, Calendar.APRIL);
		c.set(Calendar.YEAR, 1981);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Assert.assertEquals(c.getTime(), field.getDefaultModelObject());
		commonAssertions_noError();
	}

	/**
	 * Test creation.
	 */
	@Test
	public void testConstructor_noModel_noDefault_baddate() {
		DateBoPropertyDescriptor descriptor = new DateBoPropertyDescriptor();
		SelfDrawnDateOnlyField field = new SelfDrawnDateOnlyField(TestPage.TEST_ID,
				new Model<Date>(), descriptor);
		tester.startPage(getTestPage(field));
		Assert.assertSame(field, tester.getComponentFromLastRenderedPage(subjectPath()));
		Assert.assertNull(field.getDefaultModelObject());
		tester.debugComponentTrees();
		FormTester formTester = tester.newFormTester(formPath());
		String datePathId = TestPage.TEST_ID + ":date"; //$NON-NLS-1$

		formTester.setValue(datePathId, badDate);
		formTester.submit();

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MONTH, Calendar.APRIL);
		c.set(Calendar.YEAR, 1980);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		// Assert.assertEquals(c.getTime(), field.getDefaultModelObject());
		commonAssertions_noError();
		// System.out.println(tester.getLastResponse().getDocument());
	}
}
