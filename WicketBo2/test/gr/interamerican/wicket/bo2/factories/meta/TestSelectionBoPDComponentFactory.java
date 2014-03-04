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
import gr.interamerican.bo2.utils.meta.descriptors.SelectionBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnSelectionDropDownChoice;
import gr.interamerican.wicket.markup.html.TestPage;

import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;
import org.junit.Test;

/**
 * 
 */
public class TestSelectionBoPDComponentFactory extends BaseClassForTestingComponentFactory{	
	/**
	 * 
	 */
	private SelectionBoPDComponentFactory  selectionBoPDComponentFactory = 
		new SelectionBoPDComponentFactory();
	
	/**
	 * 
	 */
	private SelectionBoPropertyDescriptor<Long> selectionBoPropertyDescriptor = bean1descriptor.selectionDescriptor();
		
	/**
	 * 
	 */
	@Test
	public void testDrawMain_First(){
		Component component = selectionBoPDComponentFactory.drawMain(selectionBoPropertyDescriptor, TestPage.TEST_ID);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnSelectionDropDownChoice.class); 
	}
	
	/**
	 * 
	 */
	@Test
	public void testDrawMain_Second(){
		PropertyModel<Bean1> model = new PropertyModel<Bean1>(bean1,selectionBoPropertyDescriptor.getName());
		Component component = selectionBoPDComponentFactory.drawMain(TestPage.TEST_ID,model,selectionBoPropertyDescriptor);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnSelectionDropDownChoice.class); 
	}
}
