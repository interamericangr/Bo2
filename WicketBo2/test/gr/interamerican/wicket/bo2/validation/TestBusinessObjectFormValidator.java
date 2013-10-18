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
package gr.interamerican.wicket.bo2.validation;

import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectValidationExpression;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.LongBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.samples.utils.meta.BusinessObjectValidationExpressionImpl;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnForm;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.markup.html.TestPanel;
import gr.interamerican.wicket.test.WicketTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link BusinessObjectFormValidator}.
 */
public class TestBusinessObjectFormValidator extends WicketTest {
	
	/**
	 * Self drawn form id in {@link TestPanel}.
	 */
	static final String SELF_DRAWN_FORM_ID = "sdf"; //$NON-NLS-1$
	
	/**
	 * Tests creation.
	 */
	@Test
	public void testSubmitSelfDrawnForm_validationFails() {
		tester.startPage(testPageSource());
		Assert.assertFalse(getFormTester().getForm().hasError());
		getFormTester().submit();
		Assert.assertTrue(getFormTester().getForm().hasError());
	}
	
	@Override
	@SuppressWarnings("nls")
	protected Component initializeComponent() {
		CompoundPropertyModel<BeanWith2Fields> model = 
			new CompoundPropertyModel<BeanWith2Fields>(new BeanWith2Fields("0", -1)); //$NON-NLS-1$
		Form<BeanWith2Fields> form = new SelfDrawnForm<BeanWith2Fields>("sdf", model, createBod());
		String markup = "<wicket:panel><form wicket:id=\"" 
				      + SELF_DRAWN_FORM_ID + "\"><div wicket:id=\"" 
			          + SelfDrawnForm.PANEL_WICKET_ID + "\"/></form></wicket:panel>";
		return new TestPanel(TestPage.TEST_ID, markup).add(form);
	}
	
	/**
	 * @return Returns the BusinessObjectDescriptor for BeanWith1Field.
	 */
	private BusinessObjectDescriptor<BeanWith2Fields> createBod() {
		BusinessObjectDescriptor<BeanWith2Fields> bod = new BasicBusinessObjectDescriptor<BeanWith2Fields>();
		List<BoPropertyDescriptor<?>> list = new ArrayList<BoPropertyDescriptor<?>>();
		list.add(getField1Decriptor());
		list.add(getField2Decriptor());
		bod.setPropertyDescriptors(list);
		bod.getExpressions().add(createExpression());
		return bod;
	}
	
	/**
	 * @return Returns a sample {@link BusinessObjectValidationExpression}
	 */
	@SuppressWarnings("nls")
	private BusinessObjectValidationExpression createExpression() {
		BusinessObjectValidationExpression expression = new BusinessObjectValidationExpressionImpl();
		expression.setExpression("field1.equals(field2.toString())");
		expression.setMessage("Expression evaluation failed for: " + expression.getExpression());
		return expression;
	}
	
	/**
	 * @return Returns the LongBoPropertyDescriptor for field2 of BeanWith1Field.
	 */
	private StringBoPropertyDescriptor getField1Decriptor(){
		StringBoPropertyDescriptor sbpd = new StringBoPropertyDescriptor();
		sbpd.setClassName(BeanWith1Field.class.getName());
		sbpd.setHasDefault(false);
		sbpd.setName("field1"); //$NON-NLS-1$
		sbpd.setPackageName(BeanWith1Field.class.getPackage().getName());
		return sbpd;
	}
	
	/**
	 * @return Returns the LongBoPropertyDescriptor for field2 of BeanWith1Field.
	 */
	private LongBoPropertyDescriptor getField2Decriptor(){
		LongBoPropertyDescriptor lbpd = new LongBoPropertyDescriptor();
		lbpd.setClassName(BeanWith1Field.class.getName());
		lbpd.setHasDefault(false);
		lbpd.setName("field2"); //$NON-NLS-1$
		lbpd.setPackageName(BeanWith1Field.class.getPackage().getName());
        return lbpd;
    }

}
