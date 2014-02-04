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
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.test.WicketTest;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;
import org.junit.Test;

/**
 * Unit tests for {@link SelfDrawnPanel}.
 */
public class TestSDPanel extends WicketTest {
	
	/**
	 * Model of SelfDrawnPanel.
	 */
	private CompoundPropertyModel<BeanWith3Fields> model;
	
	/**
	 * 
	 */
	@Test
	public void testSelfDrawnPanel2() {
		tester.startPage(testPageSource());
		System.out.println(tester.getServletResponse().getDocument());
	}
	
	/**
	 * Test creation and correct model propagation.
	 */
	//@Test
	public void testSelfDrawnPanel() {
		tester.startPage(testPageSource());
		
	}
	
	
	
	@Override
	protected Component initializeComponent() {
		model = new CompoundPropertyModel<BeanWith3Fields>(new BeanWith3Fields("test",5,2.3)); //$NON-NLS-1$
		return new SDPanel<BeanWith3Fields>(TestPage.TEST_ID, model, createBod(), 2);
	}
	
	/**
	 * @return Returns the BusinessObjectDescriptor for BeanWith1Field.
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
	 * @return d 
	 */
	private StringBoPropertyDescriptor getStringDecriptor(){
		StringBoPropertyDescriptor lbpd = new StringBoPropertyDescriptor();
		lbpd.setClassName(BeanWith3Fields.class.getName());
		lbpd.setHasDefault(false);
		lbpd.setName("field1"); //$NON-NLS-1$
		lbpd.setPackageName(BeanWith3Fields.class.getPackage().getName());
		lbpd.setNullAllowed(false);
        return lbpd;
    }
	
	/**
	 * @return d 
	 */
	private IntegerBoPropertyDescriptor getIntegerDecriptor(){
		IntegerBoPropertyDescriptor lbpd = new IntegerBoPropertyDescriptor();
		lbpd.setClassName(BeanWith3Fields.class.getName());
		lbpd.setHasDefault(false);
		lbpd.setName("field2"); //$NON-NLS-1$
		lbpd.setPackageName(BeanWith3Fields.class.getPackage().getName());
		lbpd.setNullAllowed(false);
        return lbpd;
    }
	
	/**
	 * @return d 
	 */
	private DoubleBoPropertyDescriptor getDoubleDecriptor(){
		DoubleBoPropertyDescriptor lbpd = new DoubleBoPropertyDescriptor();
		lbpd.setClassName(BeanWith3Fields.class.getName());
		lbpd.setHasDefault(false);
		lbpd.setName("field3"); //$NON-NLS-1$
		lbpd.setPackageName(BeanWith3Fields.class.getPackage().getName());
		lbpd.setNullAllowed(false);
        return lbpd;
    }
	
}
