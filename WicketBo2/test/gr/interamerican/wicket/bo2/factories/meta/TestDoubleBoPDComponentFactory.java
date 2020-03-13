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
import gr.interamerican.bo2.utils.meta.descriptors.DoubleBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnDoubleTextField;
import gr.interamerican.wicket.markup.html.TestPage;

import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;
import org.junit.Test;

/**
 * A factory for creating TestDoubleBoPDComponent objects.
 */
public class TestDoubleBoPDComponentFactory extends BaseClassForTestingComponentFactory{

	/** The double bo PD component factory. */
	private DoubleBoPDComponentFactory doubleBoPDComponentFactory =  
		new DoubleBoPDComponentFactory();
	
	/** The double bo property descriptor. */
	private DoubleBoPropertyDescriptor doubleBoPropertyDescriptor = 
		bean1descriptor.percentageDescriptor();
	
	/**
	 * Test draw main first.
	 */
	@Test
	public void testDrawMain_First(){
		Component component = doubleBoPDComponentFactory.drawMain(doubleBoPropertyDescriptor, TestPage.TEST_ID);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnDoubleTextField.class); 
	}
	
	/**
	 * Test draw main second.
	 */
	@Test
	public void testDrawMain_Second(){
		PropertyModel<Bean1> model = new PropertyModel<Bean1>(bean1,doubleBoPropertyDescriptor.getName());
		Component component = doubleBoPDComponentFactory.drawMain(TestPage.TEST_ID, model, doubleBoPropertyDescriptor);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnDoubleTextField.class); 
	}

}
