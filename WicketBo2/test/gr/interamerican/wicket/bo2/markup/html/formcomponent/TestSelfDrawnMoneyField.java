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
package gr.interamerican.wicket.bo2.markup.html.formcomponent;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;
import gr.interamerican.bo2.utils.meta.ext.descriptors.MoneyBoPropertyDescriptor;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

import java.math.BigDecimal;

import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestSelfDrawnMoneyField extends WicketTest {
	
	/** 
	 * Tests the components
	 */
	@Test
	public void testSelfDrawnMoneyField(){
		SelfDrawnMoneyField field = new SelfDrawnMoneyField(TestPage.TEST_ID, new Model<Money>(), getBoPropertyDecriptor(false));
		tester.startPage(getTestPage(field));
		Assert.assertSame(field,tester.getComponentFromLastRenderedPage(subjectPath()));
		Assert.assertNotNull(field.getDefaultModelObject());
		Assert.assertEquals(field.getModel().getObject().getAmount(), BigDecimal.ZERO);
		
		testFormSubmission(field);
	}
	
	/** 
	 * Tests the component when disabled.
	 */
	@Test
	public void testSelfDrawnMoneyField_disabled(){
		Money money = new MoneyImpl(BigDecimal.TEN);
		SelfDrawnMoneyField field = new SelfDrawnMoneyField(TestPage.TEST_ID, new Model<Money>(money), getBoPropertyDecriptor(true));
		tester.startPage(getTestPage(field));
		Assert.assertSame(field,tester.getComponentFromLastRenderedPage(subjectPath()));
		Assert.assertNotNull(field.getDefaultModelObject());
		Assert.assertEquals(0, BigDecimal.TEN.compareTo(field.getModelObject().getAmount()));
		
		FormTester formTester = tester.newFormTester(formPath());
		formTester.submit();
		
		tester.assertNoErrorMessage();
		
		Assert.assertEquals(0, BigDecimal.TEN.compareTo(field.getModelObject().getAmount()));
	}
	
	
	/**
	 * @param readOnly 
	 * @return Returns the MoneyBoPropertyDescriptor
	 */
	private MoneyBoPropertyDescriptor getBoPropertyDecriptor(boolean readOnly){
		MoneyBoPropertyDescriptor lbpd = new MoneyBoPropertyDescriptor();
		lbpd.setHasDefault(false);
		lbpd.setNullAllowed(false);
		lbpd.setNegativeAllowed(false);
		lbpd.setReadOnly(readOnly);
        return lbpd;
    }
	
	/**
	 * @param textField
	 */
	@SuppressWarnings("nls")
	private void testFormSubmission(SelfDrawnMoneyField textField) {
		FormTester formTester = tester.newFormTester(formPath());
		formTester.setValue(TestPage.TEST_ID + ":component", "10");
		formTester.submit();
		Assert.assertEquals(BigDecimal.TEN.compareTo(textField.getModelObject().getAmount()), 0);
	}
	
}
