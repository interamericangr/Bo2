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
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.meta.descriptors.BigDecimalBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnBigDecimalTextField;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnLabel;
import gr.interamerican.wicket.markup.html.TestPage;

import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;
import org.junit.Assert;
import org.junit.Test;


/**
 * 
 * 
 */
public class TestGenericBoPDComponentFactory extends BaseClassForTestingComponentFactory{
	
	/**
	 * 
	 */
	private GenericBoPDComponentFactory genericBoPDComponentFactory = 
		new GenericBoPDComponentFactory();
	
	/**
	 * 
	 */
	@Test
	public void testGenericBoPDComponentFactory() {	
		BigDecimalBoPropertyDescriptor descriptor = bean1descriptor.amountDescriptor();
		String wicketId = descriptor.getName();
		
		Pair<Component, Component> pair1 = genericBoPDComponentFactory.draw(descriptor, wicketId,TestPage.TEST_ID);
		
		PropertyModel<Bean1> model = new PropertyModel<Bean1>(bean1,wicketId);
		Pair<Component, Component> pair2 = genericBoPDComponentFactory.draw(model,descriptor,TestPage.TEST_ID,TestPage.TEST_ID);
		Component label1 = genericBoPDComponentFactory.drawLabel(descriptor, wicketId);
		Component label2 = genericBoPDComponentFactory.drawLabel(descriptor,TestPage.TEST_ID);
		Component cmp1 = genericBoPDComponentFactory.drawMain(descriptor, wicketId);
		Component cmp2 = genericBoPDComponentFactory.drawMain(TestPage.TEST_ID, model, descriptor);
		
		
		
		Assert.assertNotNull(pair1);
		Assert.assertNotNull(pair2);
		Assert.assertNotNull(label1);
		Assert.assertNotNull(label2);
		Assert.assertNotNull(cmp1);
		Assert.assertNotNull(cmp2);

		Assert.assertEquals(SelfDrawnLabel.class,label1.getClass());
		Assert.assertEquals(SelfDrawnLabel.class,label2.getClass());
		Assert.assertEquals(SelfDrawnBigDecimalTextField.class,cmp1.getClass());
		Assert.assertEquals(SelfDrawnBigDecimalTextField.class,cmp2.getClass());
	}
	
//	/**
//	 * 
//	 */
//	@Test
//	public void testDrawMain_First(){
//		Component component = doubleBoPDComponentFactory.drawMain(doubleBoPropertyDescriptor, TEST_WICKET_ID);
//		tester.startPage(getTestPage(component));
//		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnBigDecimalTextField.class); 
//	}
//	
//	/**
//	 * 
//	 */
//	@Test
//	public void testDrawMain_Second(){
//		Component component = doubleBoPDComponentFactory.drawMain(bean1, doubleBoPropertyDescriptor,TEST_WICKET_ID);
//		tester.startPage(getTestPage(component));
//		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnBigDecimalTextField.class); 
//	}
	
}
