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
package gr.interamerican.wicket.bo2.descriptors;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.utils.TranslatorRegistry;
import gr.interamerican.bo2.arch.utils.beans.TranslatorImpl;
import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.samples.utils.meta.Bean1descriptor;
import gr.interamerican.bo2.utils.meta.exceptions.MultipleValidationsException;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class TestTranslatableBusinessObjectDescriptorWrapper  extends Bo2WicketTest{
	
	/**
	 * Bean1descriptor
	 */
	private Bean1descriptor bean1Descriptor = new Bean1descriptor();
	
	/**
	 * translator
	 */
	private TranslatorImpl<String,Long> translator = new TranslatorImpl<String,Long>();
	
	/**
	 * resourceId
	 */
	private String resourceId = "resourceId"; //$NON-NLS-1$
	
	/**
	 * session
	 */
	@SuppressWarnings("unchecked")
	private Bo2WicketSession<?,Long> session = Bo2WicketSession.get();

	/**
	 * NAME
	 */
	private static final String NAME = "descriptorName"; //$NON-NLS-1$
	
	/**
	 * TRANSLATION
	 */
	private static final String TRANSLATION = "translatedValue"; //$NON-NLS-1$
	
	/**
	 * TranslatableBoPropertyDescriptorWrapper to test
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private TranslatableBusinessObjectDescriptorWrapper<Bean1, ?, ?> wrapper = 
		new TranslatableBusinessObjectDescriptorWrapper(bean1Descriptor,resourceId,this.getClass().getName()); 
	
	/**
	 * Init
	 */
	@Before
	public void Init(){		
		bean1Descriptor.setName(NAME);		
		session.setLanguageId(1L);
		translator.learn(resourceId, 1L, TRANSLATION);
		if(TranslatorRegistry.getRegisteredTranslator(this.getClass().getName()) == null) {
			TranslatorRegistry.registerTranslator(this.getClass().getName(), translator);
		}
	}
	
	/**
	 * Test getLabel
	 */
	@Test
	public void testGetLabel(){
		assertEquals(TRANSLATION,wrapper.getLabel());

	}
	
	/**
	 * Test getDescriptors
	 */
	@Test
	public void testGetDescriptors(){
		Assert.assertNotNull(wrapper.getPropertyDescriptors());
	}
	
	/**
	 * Test parseAndValidate
	 * @throws MultipleValidationsException 
	 */
	@Test (expected = MultipleValidationsException.class)
	public void testValidate() throws MultipleValidationsException{
		Bean1 bean1 = new Bean1();
		wrapper.validate(bean1);
	}
	/**
	 * Test getName
	 */
	@Test
	public void testGetName(){
		Assert.assertNotNull(wrapper.getName());
	}
	
	/**
	 * Test setLabel
	 */
	@Test(expected = RuntimeException.class)
	public void testSetLabel(){
		wrapper.setLabel("testLabel"); //$NON-NLS-1$
	}
	
	/**
	 * Test setPropertyDescriptors
	 */
	@Test(expected = RuntimeException.class)
	public void testSetPropertyDescriptors(){
		wrapper.setPropertyDescriptors(bean1Descriptor.getPropertyDescriptors()); 
	}
	
	/**
	 * Test setName
	 */
	@Test(expected = RuntimeException.class)
	public void testSetName(){
		wrapper.setName("testName"); //$NON-NLS-1$
	}
}
