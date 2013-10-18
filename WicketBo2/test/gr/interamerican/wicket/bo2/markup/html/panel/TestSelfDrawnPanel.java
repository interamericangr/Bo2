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

import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.LongBoPropertyDescriptor;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link SelfDrawnPanel}.
 */
public class TestSelfDrawnPanel extends WicketTest {
	
	/**
	 * Model of SelfDrawnPanel.
	 */
	private CompoundPropertyModel<BeanWith1Field> model;
	
	/**
	 * Test creation and correct model propagation.
	 */
	@Test
	public void testSelfDrawnPanel() {
		tester.startPage(testPageSource());
		@SuppressWarnings("unchecked")
		TextField<Long> tf =  (TextField<Long>) tester.getComponentFromLastRenderedPage(path("repeater:field2:component")); //$NON-NLS-1$
		
		Long actual = tf.getModel().getObject();
		Long expected = model.getObject().getField2();
		Assert.assertEquals(expected, actual);
		
		/*
		 * Change the model object and assert correct propagation to components.
		 */
		model.setObject(new BeanWith1Field(1L));
		
		actual = tf.getModel().getObject();
		expected = model.getObject().getField2();
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for submitting the form of a se
	 */
	@Test
	public void testSelfDrawnPanel_componentValidators() {
		tester.startPage(testPageSource());
		Assert.assertFalse(getFeedbackPanel().anyErrorMessage());
		FormTester formTester = tester.newFormTester(formPath());
		formTester.setValue(TestPage.TEST_ID + ":repeater:field2:component", "-10"); //$NON-NLS-1$ //$NON-NLS-2$
		formTester.submit(TestPage.SUBMIT_BUTTON_ID);
		Assert.assertTrue(getFeedbackPanel().anyErrorMessage());
		/*
		 * This is added to refresh the markup with the feedback panel message.
		 * It is not necessary for the test.
		 */
		tester.executeAjaxEvent(getAjaxButton(), "onclick"); //$NON-NLS-1$
	}
	
	@Override
	protected Component initializeComponent() {
		model = new CompoundPropertyModel<BeanWith1Field>(new BeanWith1Field(0L));
		return new SelfDrawnPanel<BeanWith1Field>(TestPage.TEST_ID, model, createBod());
	}
	
	/**
	 * @return Returns the BusinessObjectDescriptor for BeanWith1Field.
	 */
	private BusinessObjectDescriptor<BeanWith1Field> createBod() {
		BusinessObjectDescriptor<BeanWith1Field> bod = new BasicBusinessObjectDescriptor<BeanWith1Field>();
		List<BoPropertyDescriptor<?>> list = new ArrayList<BoPropertyDescriptor<?>>();
		list.add(getLongDecriptor());
		bod.setPropertyDescriptors(list);
		return bod;
	}
	
	/**
	 * @return Returns the LongBoPropertyDescriptor for field2 of BeanWith1Field.
	 */
	private LongBoPropertyDescriptor getLongDecriptor(){
		LongBoPropertyDescriptor lbpd = new LongBoPropertyDescriptor();
		lbpd.setClassName(BeanWith1Field.class.getName());
		lbpd.setHasDefault(false);
		lbpd.setName("field2"); //$NON-NLS-1$
		lbpd.setPackageName(BeanWith1Field.class.getPackage().getName());
		lbpd.setNullAllowed(false);
		lbpd.setNegativeAllowed(false);
        return lbpd;
    }
	
}
