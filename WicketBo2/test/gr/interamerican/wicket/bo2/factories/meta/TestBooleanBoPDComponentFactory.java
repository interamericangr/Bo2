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
import gr.interamerican.bo2.utils.meta.descriptors.BooleanBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnCheckBox;
import gr.interamerican.wicket.markup.html.TestPage;

import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;
import org.junit.Test;

/**
 * 
 */
public class TestBooleanBoPDComponentFactory extends BaseClassForTestingComponentFactory {	
	
	/**
	 * 
	 */
	private BooleanBoPDComponentFactory booleanBoPDComponentFactory = 
		new BooleanBoPDComponentFactory();
	
	/**
	 * 
	 */
	private BooleanBoPropertyDescriptor booleanBoPropertyDescriptor = 
		bean1descriptor.booleanDescriptor();
	
	/**
	 * 
	 */
	@Test
	public void testDrawMain_First(){
		Component component = booleanBoPDComponentFactory.drawMain(booleanBoPropertyDescriptor, TestPage.TEST_ID);
		tester.startPage(testPageSource(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnCheckBox.class); 
	}
	/**
	 * 
	 */
	@Test
	public void testDrawMain_Second(){
		PropertyModel<Bean1> model = new PropertyModel<Bean1>(bean1,booleanBoPropertyDescriptor.getName());
		Component component = booleanBoPDComponentFactory.drawMain(TestPage.TEST_ID, model, booleanBoPropertyDescriptor);
		tester.startPage(testPageSource(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnCheckBox.class); 
	}

}
