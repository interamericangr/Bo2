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
package gr.interamerican.wicket.bo2.factories.meta;

import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.descriptors.FloatBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnFloatTextField;
import gr.interamerican.wicket.markup.html.TestPage;

import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;
import org.junit.Test;

/**
 * 
 */
public class TestFloatBoPDComponentFactory extends BaseClassForTestingComponentFactory{
	
	/**
	 * 
	 */
	private FloatBoPDComponentFactory floatBoPDComponentFactory =
		new FloatBoPDComponentFactory();
	
	/**
	 * 
	 */
	private FloatBoPropertyDescriptor floatBoPropertyDescriptor = 
		bean1descriptor.floatDescriptor();

	/**
	 * 
	 */
	@Test
	public void testDrawMain_First(){
		Component component = floatBoPDComponentFactory.drawMain(floatBoPropertyDescriptor, TestPage.TEST_ID);
		tester.startPage(testPageSource(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnFloatTextField.class); 
	}
	
	/**
	 * 
	 */
	@Test
	public void testDrawMain_Second(){
		PropertyModel<Bean1> model = new PropertyModel<Bean1>(bean1,floatBoPropertyDescriptor.getName());
		Component component = floatBoPDComponentFactory.drawMain(TestPage.TEST_ID, model, floatBoPropertyDescriptor);
		tester.startPage(testPageSource(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnFloatTextField.class); 
	}

}
