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
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.utils.beans.TranslatorImpl;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.descriptors.IntegerBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for TranslatableBoPropertyDescriptorWrapper
 */
public class TestTranslatableBoPropertyDescriptorWrapper extends Bo2WicketTest{

	
	/**
	 * BoPropertyDescriptor
	 */
	private IntegerBoPropertyDescriptor descriptor = new IntegerBoPropertyDescriptor();
	
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
	 * INDEX
	 */
	private static final int INDEX = 1;
	
	/**
	 * NAME
	 */
	private static final String NAME = "descriptorName"; //$NON-NLS-1$
	
	/**
	 * PACKAGE_NAME
	 */
	private static final String PACKAGE_NAME = "descPackageName"; //$NON-NLS-1$
	
	
	/**
	 * CLASS_NAME
	 */
	private static final String CLASS_NAME = "descClassName"; //$NON-NLS-1$
	
	/**
	 * TRANSLATION
	 */
	private static final String TRANSLATION = "translatedValue"; //$NON-NLS-1$
	
	/**
	 * DEFAULT_VALUE
	 */
	private static final int DEFAULT_VALUE = 10;
	
	/**
	 * TranslatableBoPropertyDescriptorWrapper to test
	 */
	private TranslatableBoPropertyDescriptorWrapper<Integer, String, ?> wrapper; 
	
	
	/**
	 * Init
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Before
	public void Init(){
		descriptor.setIndex(INDEX);
		descriptor.setName(NAME);
		descriptor.setPackageName(PACKAGE_NAME);
		descriptor.setClassName(CLASS_NAME);
		descriptor.setReadOnly(true);
		descriptor.setNullAllowed(true);
		descriptor.setHasDefault(true);
		descriptor.setDefaultValue(10);
		
		session.setLanguageId(1L);
		translator.learn(resourceId, 1L, TRANSLATION);
		
		wrapper = new TranslatableBoPropertyDescriptorWrapper(descriptor,resourceId,translator);
	}
	
	
	/**
	 * Test getLabel
	 */
	@Test
	public void testGetLabel(){
		assertEquals(TRANSLATION,wrapper.getLabel());

	}
	
	/**
	 * Test SetLabel
	 */
	@Test(expected = RuntimeException.class)
	public void testSetLabel(){
		wrapper.setLabel("label"); //$NON-NLS-1$
	}
	
	
	/**
	 * Test GetIndex
	 */
	@Test
	public void testGetIndex(){
		assertEquals((Integer)INDEX,wrapper.getIndex());
	}

	
	/**
	 * Test GetIndex
	 */
	@Test(expected = RuntimeException.class)
	public void testSetIndex(){
		wrapper.setIndex(INDEX);
	}
	
	
	/**
	 * Test getName
	 */
	@Test
	public void testGetName(){
		assertEquals(NAME,wrapper.getName());
	}
	
	
	/**
	 * Test setName
	 */
	@Test(expected = RuntimeException.class)
	public void testSetName(){
		wrapper.setName(NAME);
	}
	
	/**
	 * Test getPackageName
	 */
	@Test
	public void testGetPackageName(){
		assertEquals(PACKAGE_NAME,wrapper.getPackageName());
	}
	
	/**
	 * Test setPackageName
	 */
	@Test(expected = RuntimeException.class)
	public void testSetPackageName(){
		wrapper.setPackageName(PACKAGE_NAME);
	}
	
	
	/**
	 * Test getClassName
	 */
	@Test
	public void testGetClassName(){
		assertEquals(CLASS_NAME,wrapper.getClassName());
	}
	
	/**
	 * Test getFullyQualifiedName
	 */
	@Test
	public void testGetFullyQualifiedName(){
		assertEquals(StringUtils.concat(PACKAGE_NAME,StringConstants.DOT,CLASS_NAME,StringConstants.DOT,NAME),wrapper.getFullyQualifiedName());
	}
	
	/**
	 * Test getFullyQualifiedClassName
	 */
	@Test
	public void testGetFullyQualifiedClassName(){
		assertEquals(StringUtils.concat(PACKAGE_NAME,StringConstants.DOT,CLASS_NAME),wrapper.getFullyQualifiedClassName());
	}
	
	
	/**
	 * Test setClassName
	 */
	@Test(expected = RuntimeException.class)
	public void testSetClassName(){
		wrapper.setClassName(CLASS_NAME);
	}
	
	/**
	 * Test isReadOnly
	 */
	@Test
	public void testIsReadOnly(){
		assertTrue(wrapper.isReadOnly());
	}
	
	
	/**
	 * Test setReadOnly
	 */	
	@Test(expected = RuntimeException.class)
	public void testSetReadOnly(){
		wrapper.setReadOnly(true);
	}
	
	/**
	 * Test isNullAllowed
	 */
	@Test
	public void testIsNullAllowed(){
		assertTrue(wrapper.isNullAllowed());
	}
	
	/**
	 * Test setReadOnly
	 */
	@Test(expected = RuntimeException.class)
	public void testSetNullAllowed(){
		wrapper.setNullAllowed(true);
	}
	
	/**
	 * Test isHasDefault
	 */
	@Test
	public void testIsHasDefault(){
		assertTrue(wrapper.isHasDefault());
	}
	
	/**
	 * Test setHasDefault
	 */
	@Test(expected = RuntimeException.class)
	public void testSetHasDefault(){
		wrapper.setHasDefault(true);
	}
	
	
	/**
	 * Test Parse
	 * @throws ParseException
	 */
	@Test
	public void testParse() throws ParseException{
		assertEquals((Integer)1,wrapper.parse("1")); //$NON-NLS-1$
	}
	
	/**
	 * Test format
	 */
	@Test
	public void testFormat(){
		assertEquals("1",wrapper.format(1)); //$NON-NLS-1$
	}
	
	/**
	 * Test getDefaultValue
	 */
	@Test
	public void testGetDefaultValue(){
		assertEquals((Integer)DEFAULT_VALUE,wrapper.getDefaultValue());
	}
	
	/**
	 * Test setDefaultValue
	 */
	@Test(expected = RuntimeException.class)
	public void testSetDefaultValue(){
		wrapper.setDefaultValue(DEFAULT_VALUE);
	}
	
	
	/**
	 * Test parseAndValidate
	 * @throws ParseException
	 * @throws ValidationException 
	 */
	@Test
	public void testParseAndValidate() throws ParseException, ValidationException{
		assertEquals((Integer)1,wrapper.parseAndValidate("1")); //$NON-NLS-1$
	}
	
	
	/**
	 * Test parseAndValidate
	 * @throws ValidationException 
	 */
	@Test(expected = ValidationException.class)
	public void testValidate_Exception() throws ValidationException{
		descriptor.setNullAllowed(false);
		wrapper.validate(null);
	}
	
	/**
	 * Test parseAndValidate
	 * @throws ValidationException 
	 */
	@Test
	public void testValidate() throws ValidationException{
		descriptor.setNullAllowed(true);
		wrapper.validate(null);
	}
	
	/**
	 * Test getDescriptor
	 */
	@Test
	public void testGetDescriptor(){
		assertEquals(IntegerBoPropertyDescriptor.class,wrapper.getDescriptor().getClass());
	}
	
	
}
