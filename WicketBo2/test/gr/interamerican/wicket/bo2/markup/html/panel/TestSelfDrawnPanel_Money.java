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
package gr.interamerican.wicket.bo2.markup.html.panel;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;
import gr.interamerican.bo2.samples.archutil.beans.MoneyBean;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.MoneyBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.formcomponent.SelfDrawnMoneyField;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link SelfDrawnPanel}.
 */
public class TestSelfDrawnPanel_Money extends WicketTest {
	
	/**
	 * Model of SelfDrawnPanel.
	 */
	private CompoundPropertyModel<MoneyBean> model;
	
	/**
	 * Test creation and correct model propagation.
	 */
	@Test
	public void testSelfDrawnPanel() {
		tester.startPage(getTestPage());
		SelfDrawnMoneyField tf =  (SelfDrawnMoneyField) tester.getComponentFromLastRenderedPage(path("moneyAmount1")); //$NON-NLS-1$
		
		Money actual = tf.getModel().getObject();
		Money expected = model.getObject().getMoneyAmount1();
		Assert.assertEquals(expected, actual);
		
		/*
		 * Change the model object and assert correct propagation to components.
		 */
		model.setObject(new MoneyBean(BigDecimal.ZERO, BigDecimal.ZERO, new MoneyImpl(new BigDecimal("1.1")), null)); //$NON-NLS-1$
		
		actual = tf.getModel().getObject();
		expected = model.getObject().getMoneyAmount1();
		Assert.assertEquals(expected, actual);
		
		commonAssertions_noError();
	}
	
	/**
	 * Unit test for submitting the form of a selfdrawnpanel when
	 * the validation fails.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testSelfDrawnPanel_componentValidators() {
		tester.startPage(getTestPage());
		Assert.assertFalse(getFeedbackPanel().anyErrorMessage());
		FormTester formTester = tester.newFormTester(formPath());
		formTester.setValue(TestPage.TEST_ID + ":moneyAmount1:component", "-10");
		formTester.submit(TestPage.SUBMIT_BUTTON_ID);
		Assert.assertTrue(getFeedbackPanel().anyErrorMessage());
		/*
		 * This is added to refresh the markup with the feedback panel message.
		 */
		tester.executeAjaxEvent(getAjaxButton(), "onclick");
		commonAssertions_error("moneyAmount1");
	}
	
	/**
	 * Unit test for submitting the form of a selfdrawnpanel when
	 * the validation fails.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testSelfDrawnPanel_submit() {
		tester.startPage(getTestPage());
		Assert.assertFalse(getFeedbackPanel().anyErrorMessage());
		FormTester formTester = tester.newFormTester(formPath());
		formTester.setValue(TestPage.TEST_ID + ":moneyAmount1:component", "10");
		formTester.submit(TestPage.SUBMIT_BUTTON_ID);
		Assert.assertFalse(getFeedbackPanel().anyErrorMessage());
		
		MoneyImpl expected = new MoneyImpl(new BigDecimal(10));
		MoneyImpl actual = (MoneyImpl) model.getObject().getMoneyAmount1();
		System.out.println(actual);
		Assert.assertEquals(expected, actual);
		
		commonAssertions_noError();
	}
	
	@Override
	protected Component initializeComponent() {
		MoneyBean mb = new MoneyBean();
		mb.setMoneyAmount1(new MoneyImpl(new BigDecimal(1.1d)));
		model = new CompoundPropertyModel<MoneyBean>(mb);
		return new SelfDrawnPanel<MoneyBean>(TestPage.TEST_ID, model, createBod());
	}
	
	/**
	 * @return Returns the BusinessObjectDescriptor for MoneyBean.
	 */
	private BusinessObjectDescriptor<MoneyBean> createBod() {
		BusinessObjectDescriptor<MoneyBean> bod = new BasicBusinessObjectDescriptor<MoneyBean>();
		List<BoPropertyDescriptor<?>> list = new ArrayList<BoPropertyDescriptor<?>>();
		list.add(getAmount1Decriptor());
		bod.setPropertyDescriptors(list);
		return bod;
	}
	
	/**
	 * @return Returns the LongBoPropertyDescriptor for field2 of MoneyBean.
	 */
	private MoneyBoPropertyDescriptor getAmount1Decriptor(){
		MoneyBoPropertyDescriptor lbpd = new MoneyBoPropertyDescriptor();
		lbpd.setClassName(MoneyBean.class.getName());
		lbpd.setHasDefault(false);
		lbpd.setName("moneyAmount1"); //$NON-NLS-1$
		lbpd.setPackageName(MoneyBean.class.getPackage().getName());
		lbpd.setNullAllowed(false);
		lbpd.setNegativeAllowed(false);
        return lbpd;
    }
	
}
