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
import gr.interamerican.bo2.utils.meta.descriptors.IntegerBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnIntegerTextField;
import gr.interamerican.wicket.markup.html.TestPage;

import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;
import org.junit.Test;

/**
 * A factory for creating TestIntegerBoPDComponent objects.
 */
public class TestIntegerBoPDComponentFactory extends BaseClassForTestingComponentFactory{	
	
	/** The integer bo PD component factory. */
	private IntegerBoPDComponentFactory integerBoPDComponentFactory = 
		new IntegerBoPDComponentFactory();
	
	/** The integer bo property descriptor. */
	private IntegerBoPropertyDescriptor integerBoPropertyDescriptor = 
		bean1descriptor.integerDescriptor();
		
	/**
	 * Test draw main first.
	 */
	@Test
	public void testDrawMain_First(){
		Component component = integerBoPDComponentFactory.drawMain(integerBoPropertyDescriptor, TestPage.TEST_ID);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnIntegerTextField.class); 
	}
	
	/**
	 * Test draw main second.
	 */
	@Test
	public void testDrawMain_Second(){
		PropertyModel<Bean1> model = new PropertyModel<Bean1>(bean1,integerBoPropertyDescriptor.getName());
		Component component = integerBoPDComponentFactory.drawMain(TestPage.TEST_ID,model, integerBoPropertyDescriptor);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnIntegerTextField.class); 
	}
}
