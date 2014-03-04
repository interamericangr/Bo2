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

import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.LongBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.markup.html.TestPanel;
import gr.interamerican.wicket.test.WicketTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;
import org.junit.Test;

/**
 * Unit tests for {@link SelfDrawnForm}.
 */
public class TestSelfDrawnForm extends WicketTest {
	
	/**
	 * Self drawn form id in {@link TestPanel}.
	 */
	static final String SELF_DRAWN_FORM_ID = "sdf"; //$NON-NLS-1$
	
	/**
	 * Form to test.
	 */
	SelfDrawnForm<BeanWith2Fields> form;
	
	/**
	 * Tests creation.
	 */
	@Test
	public void testCreation() {
		tester.startPage(getTestPage());
		getFormTester().submit();
	}
	
	@Override
	@SuppressWarnings("nls")
	protected Component initializeComponent() {
		CompoundPropertyModel<BeanWith2Fields> model = 
			new CompoundPropertyModel<BeanWith2Fields>(new BeanWith2Fields("0", -1)); //$NON-NLS-1$
		form = new SelfDrawnForm<BeanWith2Fields>("sdf", model, createBod());
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
		return bod;
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
		lbpd.setNegativeAllowed(false);
        return lbpd;
    }

}
