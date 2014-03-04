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

import gr.interamerican.bo2.samples.bean.BeanWith3Fields;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.DoubleBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.IntegerBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnStringTextField;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link SelfDrawnPanel}.
 */
@SuppressWarnings("nls")
public class TestSelfDrawnPanel_MultipleColumns extends WicketTest {
	
	/**
	 * Model of SelfDrawnPanel.
	 */
	private CompoundPropertyModel<BeanWith3Fields> model;
	
	
	/**
	 * Test creation and correct model propagation.
	 */
	@Test
	public void testSelfDrawnGridPanel() {
		tester.startPage(getTestPage());
		
		@SuppressWarnings("unchecked")
		TextField<Integer> tf =  (TextField<Integer>) tester.getComponentFromLastRenderedPage(path("field2"));
		
		Integer actual = tf.getModel().getObject();
		Integer expected = model.getObject().getField2();
		Assert.assertEquals(expected, actual);
		
		/*
		 * Test SelfDrawnUtils TODO: move this
		 */
		Assert.assertTrue(getTestSubject() instanceof SelfDrawnPanel);
		Component cmp = SelfDrawnUtils.getComponentFromSelfDrawnPanel((MarkupContainer) getTestSubject(), "field2");
		Assert.assertTrue(cmp==tf);
		
		/*
		 * Change the model object and assert correct propagation to components.
		 */
		model.setObject(new BeanWith3Fields("1", 2, 1d));
		
		actual = tf.getModel().getObject();
		expected = model.getObject().getField2();
		Assert.assertEquals(expected, actual);
		
		commonAssertions_noError();
	}
	
	/**
	 * Test creation and correct model propagation.
	 */
	@Test
	public void testSelfDrawnGridPanel_submit() {
		tester.startPage(getTestPage());
		
		tester.assertComponent(path("field1"), SelfDrawnStringTextField.class);
		SelfDrawnStringTextField tf =  (SelfDrawnStringTextField) tester.getComponentFromLastRenderedPage(path("field1"));
		Assert.assertTrue(tf.getConvertEmptyInputStringToNull());
		
		FormTester formTester = tester.newFormTester(formPath());
		formTester.setValue(TestPage.TEST_ID + ":field1", ""); //$NON-NLS-2$
		formTester.setValue(TestPage.TEST_ID + ":field2", "10"); //$NON-NLS-2$
		formTester.setValue(TestPage.TEST_ID + ":field3", "10,1"); //$NON-NLS-2$
		formTester.submit();
		
		Assert.assertFalse(getFeedbackPanel().anyErrorMessage());
		
		Assert.assertNull(model.getObject().getField1());
		Assert.assertEquals(new Integer(10) , model.getObject().getField2());
		Assert.assertEquals(new Double(10.1), model.getObject().getField3());
		
		commonAssertions_noError();
	}
	
	/**
	 * Unit test for submitting the form of a se
	 */
	@Test
	public void testSelfDrawnPanel_componentValidators() {
		tester.startPage(getTestPage());
		Assert.assertFalse(getFeedbackPanel().anyErrorMessage());
		FormTester formTester = tester.newFormTester(formPath());
		formTester.setValue(TestPage.TEST_ID + ":field2", "-10"); //$NON-NLS-2$
		formTester.submit(TestPage.SUBMIT_BUTTON_ID);
		Assert.assertTrue(getFeedbackPanel().anyErrorMessage());
		/*
		 * This is added to refresh the markup with the feedback panel message.
		 * It is not necessary for the test.
		 */
		tester.executeAjaxEvent(getAjaxButton(), "onclick");
		commonAssertions_error("field2");
	}
	
	@Override
	protected Component initializeComponent() {
		model = new CompoundPropertyModel<BeanWith3Fields>(new BeanWith3Fields("1", 1, 1d));
		return new SelfDrawnPanel<BeanWith3Fields>(TestPage.TEST_ID, model, createBod(), 2, null);
	}
	
	/**
	 * @return Returns the BusinessObjectDescriptor for BeanWith3Fields.
	 */
	private BusinessObjectDescriptor<BeanWith3Fields> createBod() {
		BusinessObjectDescriptor<BeanWith3Fields> bod = new BasicBusinessObjectDescriptor<BeanWith3Fields>();
		List<BoPropertyDescriptor<?>> list = new ArrayList<BoPropertyDescriptor<?>>();
		list.add(getStringDecriptor());
		list.add(getIntegerDecriptor());
		list.add(getDoubleDecriptor());
		bod.setPropertyDescriptors(list);
		return bod;
	}
	
	/**
	 * @return Returns the StringBoPropertyDescriptor for field1 of BeanWith3Fields.
	 */
	private StringBoPropertyDescriptor getStringDecriptor(){
		StringBoPropertyDescriptor sbpd = new StringBoPropertyDescriptor();
		sbpd.setClassName(BeanWith3Fields.class.getName());
		sbpd.setHasDefault(false);
		sbpd.setName("field1");
		sbpd.setPackageName(BeanWith3Fields.class.getPackage().getName());
		sbpd.setNullAllowed(true);
		sbpd.setIndex(1);
        return sbpd;
    }
	
	/**
	 * @return Returns the IntegerBoPropertyDescriptor for field2 of BeanWith3Fields.
	 */
	private IntegerBoPropertyDescriptor getIntegerDecriptor(){
		IntegerBoPropertyDescriptor ibpd = new IntegerBoPropertyDescriptor();
		ibpd.setClassName(BeanWith3Fields.class.getName());
		ibpd.setHasDefault(false);
		ibpd.setName("field2");
		ibpd.setPackageName(BeanWith3Fields.class.getPackage().getName());
		ibpd.setNullAllowed(false);
		ibpd.setNegativeAllowed(false);
		ibpd.setIndex(2);
        return ibpd;
    }
	
	/**
	 * @return Returns the DoubleBoPropertyDescriptor for field2 of BeanWith3Fields.
	 */
	private DoubleBoPropertyDescriptor getDoubleDecriptor(){
		DoubleBoPropertyDescriptor dbpd = new DoubleBoPropertyDescriptor();
		dbpd.setClassName(BeanWith3Fields.class.getName());
		dbpd.setHasDefault(false);
		dbpd.setName("field3");
		dbpd.setPackageName(BeanWith3Fields.class.getPackage().getName());
		dbpd.setNullAllowed(false);
		dbpd.setNegativeAllowed(false);
		dbpd.setIndex(3);
        return dbpd;
    }
	
}
