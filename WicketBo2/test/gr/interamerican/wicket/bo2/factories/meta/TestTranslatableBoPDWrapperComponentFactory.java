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

import gr.interamerican.bo2.arch.ext.Translator;
import gr.interamerican.bo2.arch.utils.TranslatorRegistry;
import gr.interamerican.bo2.arch.utils.beans.TranslatorImpl;
import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.descriptors.TranslatableBoPropertyDescriptorWrapper;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnStringTextField;
import gr.interamerican.wicket.markup.html.TestPage;

import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 */
public class TestTranslatableBoPDWrapperComponentFactory extends BaseClassForTestingComponentFactory {	

	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private Translator translator=  new TranslatorImpl();
	/**
	 * 
	 */
	private TranslatableBoPDWrapperComponentFactory<StringBoPropertyDescriptor> translatableBoPDComponentFactory =
		new TranslatableBoPDWrapperComponentFactory<StringBoPropertyDescriptor>();
	
	/**
	 * 
	 */
	private StringBoPropertyDescriptor stringBoPropertyDescriptor = bean1descriptor.descriptionDescriptor();
	
	/**
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TranslatableBoPropertyDescriptorWrapper tbpD = 
		new TranslatableBoPropertyDescriptorWrapper(stringBoPropertyDescriptor, null, this.getClass().getName());

	/**
	 * setup
	 */
	@Before
	@SuppressWarnings("unchecked")
	@Override
	public void before() {
		super.before();
		if(TranslatorRegistry.getRegisteredTranslator(this.getClass().getName())==null) {
			TranslatorRegistry.registerTranslator(this.getClass().getName(), translator);
		}
	}
	
		
	/**
	 * 
	 */
	@Test
	public void testDrawMain_First(){
		Component component = translatableBoPDComponentFactory.drawMain(tbpD, TestPage.TEST_ID);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnStringTextField.class); 
	}
	
	/**
	 * 
	 */
	@Test
	public void testDrawMain_Second(){
		PropertyModel<Bean1> model = new PropertyModel<Bean1>(bean1,stringBoPropertyDescriptor.getName());
		Component component = translatableBoPDComponentFactory.drawMain(TestPage.TEST_ID, model, tbpD);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnStringTextField.class); 
	}
}
