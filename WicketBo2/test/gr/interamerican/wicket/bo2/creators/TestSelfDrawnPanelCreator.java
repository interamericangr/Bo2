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
package gr.interamerican.wicket.bo2.creators;

import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.samples.utils.meta.ChildBean;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.factories.ServicePanelDefinitionFactory;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;

import java.util.Arrays;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for SelfDrawnPanelCreator
 */
public class TestSelfDrawnPanelCreator extends Bo2WicketTest{
	
	/**
	 * descriptor for test
	 */
	BasicBusinessObjectDescriptor<ChildBean> beanDescriptor;
	
	/**
	 * Init
	 */
	@Before
	public void init(){
		beanDescriptor = new BasicBusinessObjectDescriptor<ChildBean>();
		BoPropertyDescriptor<?>[] descriptors = {sampleDescriptor()};
		beanDescriptor.setPropertyDescriptors(Arrays.asList(descriptors));
	}

	/**
	 * Test constructor
	 */
	@Test
	public void testConstructor(){
		SelfDrawnPanelCreator<ChildBean> creator = new SelfDrawnPanelCreator<ChildBean>(beanDescriptor);
		assertSame(beanDescriptor, creator.beanDescriptor);
	}
	
	
	/**
	 * Test constructor
	 */
	@Test
	public void testCreatePanel(){
		SelfDrawnPanelCreator<ChildBean> creator = new SelfDrawnPanelCreator<ChildBean>(beanDescriptor);
		String id =TestPage.TEST_ID;
		ChildBean bean = new ChildBean();
		CompoundPropertyModel<ChildBean> model = new CompoundPropertyModel<ChildBean>(bean);		
		ModeAwareBeanPanelDef<ChildBean> definition = ServicePanelDefinitionFactory.createBeanPanelDef(id, model);
		Component component = creator.createPanel(definition);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), Panel.class); 
	}
	
	
	
	/**
	 * Creates a sample descriptor
	 * @return sampleDesc
	 */
	StringBoPropertyDescriptor sampleDescriptor() {	
		StringBoPropertyDescriptor sampleDesc = new StringBoPropertyDescriptor();
		sampleDesc.setName("name"); //$NON-NLS-1$
		sampleDesc.setDefaultValue(StringConstants.SPACE);
		sampleDesc.setHasDefault(false);		
		sampleDesc.setMinLength(3);
		sampleDesc.setMaxLength(5);
		sampleDesc.setNullAllowed(false);
		sampleDesc.setReadOnly(false);
		sampleDesc.setPackageName(ChildBean.class.getPackage().getName());
		sampleDesc.setClassName(ChildBean.class.getSimpleName());
		return sampleDesc;
	}
	
}



