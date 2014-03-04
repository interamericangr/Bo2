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
package gr.interamerican.wicket.bo2.utils;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.bo2.creators.SelfDrawnPanelCreator;
import gr.interamerican.wicket.bo2.factories.ServicePanelDefinitionFactory;
import gr.interamerican.wicket.bo2.factories.meta.BaseClassForTestingComponentFactory;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnDropDownChoiceForEntry;
import gr.interamerican.wicket.markup.html.TestPage;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link SelfDrawnUtils}.
 */
public class TestSelfDrawnUtils extends BaseClassForTestingComponentFactory{
	
	/**
	 * Test getComponentFromSelfDrawnPanel
	 */
	@Test
	public void testGetComponentFromSelfDrawnPanel(){
		TestBeanDescriptor beanDescriptor = new TestBeanDescriptor();
		SelfDrawnPanelCreator<TestBean> creator = new SelfDrawnPanelCreator<TestBean>(beanDescriptor);	
		TestBean bean = new TestBean();
		CompoundPropertyModel<TestBean> model = new CompoundPropertyModel<TestBean>(bean);
		
		ModeAwareBeanPanelDef<TestBean> definition = ServicePanelDefinitionFactory.createBeanPanelDef(TestPage.TEST_ID, model);
		Panel selfDrawnPanel = creator.createPanel(definition);
		tester.startPage(getTestPage(selfDrawnPanel));
		tester.assertComponent(path(StringConstants.EMPTY), Panel.class); 
		tester.assertRenderedPage(TestPage.class);

		Component secondComp = SelfDrawnUtils.getComponentFromSelfDrawnPanel(selfDrawnPanel, "secondAttribute"); //$NON-NLS-1$
		Assert.assertEquals(SelfDrawnDropDownChoiceForEntry.class, secondComp.getClass());
	}
		
}
