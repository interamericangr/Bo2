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
package gr.interamerican.bo2.utils.meta.descriptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.FormatterResolver;
import gr.interamerican.bo2.utils.meta.parsers.ParserResolver;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link AbstractBoPropertyDescriptor}.
 */
public class TestAbstractBoPropertyDescriptor {
	
	/**
	 * Sample.
	 */
	Descriptor descriptor;
	
	/**
	 * Setup sample.
	 */
	@Before
	public void setup() {
		descriptor = new Descriptor();
	}
	
	/**
	 * Test constructor
	 */
	@Test
	public void testConstructor(){
		assertNotNull(descriptor.parser);
		assertNotNull(descriptor.getFormatter());
		assertEquals(0, descriptor.validators.size());
	}	
		
	/**
	 * Test setNullAllowed, validate
	 * @throws ValidationException
	 */
	@Test 	
	public void testSetNullAllowedAndValidate() throws ValidationException{
		descriptor.setNullAllowed(false);
		assertEquals(1, descriptor.validators.size());
		descriptor.setNullAllowed(true);
		assertEquals(0, descriptor.validators.size());
		descriptor.setNullAllowed(false);
		assertEquals(1, descriptor.validators.size());
		descriptor.validate("notNullValue"); //$NON-NLS-1$
		
		boolean caught = false;
		try {
			descriptor.validate(null);
		} catch (ValidationException ve) {
			caught = true;
		}
		assertTrue(caught);
	}
	
	/**
	 * TestGetFullyQualifiedName
	 */
	@Test
	public void testGetFullyQualifiedName() {
		descriptor.setPackageName("gr.iag"); //$NON-NLS-1$
		descriptor.setClassName("Doer"); //$NON-NLS-1$
		descriptor.setName("id"); //$NON-NLS-1$
		assertEquals("gr.iag.Doer.id", descriptor.getFullyQualifiedName()); //$NON-NLS-1$
	}
	
	/**
	 * TestGetFullyQualifiedClassName
	 */
	@Test 
	public void testGetFullyQualifiedClassName(){
		descriptor.setPackageName("gr.iag"); //$NON-NLS-1$
		descriptor.setClassName("Doer"); //$NON-NLS-1$
		assertEquals("gr.iag.Doer", descriptor.getFullyQualifiedClassName()); //$NON-NLS-1$
	}

	/**
	 * TestCheckDefault
	 */
	@Test
	public void testCheckDefault(){
		descriptor.setDefaultValue("default"); //$NON-NLS-1$
		descriptor.setNullAllowed(false);
		descriptor.setHasDefault(true);
		assertEquals("default", descriptor.checkDefault(null)); //$NON-NLS-1$
		descriptor.setNullAllowed(true);
		assertEquals("value", descriptor.checkDefault("value")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * Test parse
	 * @throws ParseException 
	 */
	@Test
	public void testParse() throws ParseException{
		descriptor.parse(StringConstants.EMPTY);
		descriptor.parse(null);
	}
	
	/**
	 * Test Parse and Validate
	 * @throws ParseException
	 * @throws ValidationException
	 */
	@Test
	public void testParseAndValidate() throws ParseException, ValidationException{
		descriptor.parseAndValidate(StringConstants.EMPTY);
		descriptor.parseAndValidate(null);
	}
	
	/**
	 * Test Equals
	 */
	@Test
	@SuppressWarnings("nls")
	public void testEquals (){
		assertFalse(descriptor.equals(null));
		assertFalse(descriptor.equals("value"));
		descriptor.setPackageName("x");
		descriptor.setClassName("y");
		descriptor.setName("z");
		
		Descriptor that = new Descriptor();
		that.setPackageName("x");
		that.setClassName("y");
		that.setName("");
		assertFalse(descriptor.equals(that));
		that.setName("z");
		assertTrue(descriptor.equals(that));	
	}
	
	/**
	 * Test HasCode
	 */
	@Test
	public void testHasCode(){
		descriptor.hashCode();
	}
	
	/**
	 * Test Format
	 */
	@Test
	public void testFormat(){
		descriptor.format(StringConstants.EMPTY);
		descriptor.format(null);
	}
	
	/**
	 * Test isReadOnly
	 */
	@Test
	public void testIsReadOnly(){
		descriptor.setReadOnly(false);
		assertFalse(descriptor.isReadOnly());
		descriptor.setReadOnly(true);
		assertTrue(descriptor.isReadOnly());
	}
	
	/**
	 * Test isReadOnly
	 */
	@Test
	public void testGetDefaultValue(){
		String value = "test"; //$NON-NLS-1$
		descriptor.setDefaultValue(value);
		assertEquals(value, descriptor.defaultValue);
		assertEquals(value, descriptor.getDefaultValue());
	}
	
	/**
	 * Test getIndex
	 */
	@Test
	public void  testGetIndex(){
		Integer index = 1;
		descriptor.setIndex(index);
		assertEquals(index, descriptor.index);
		assertEquals(index, descriptor.getIndex());
	}
	
	/**
	 * Test setLabel
	 */
	@Test
	@SuppressWarnings("nls")
	public void testSetLabel(){
		String label = "labelName";
		descriptor.setLabel(label);
		assertEquals(label, descriptor.label);
		assertEquals(label, descriptor.getLabel());
		descriptor.setLabel(null);
		descriptor.name="name";
		assertEquals(descriptor.getLabel(), descriptor.name);
	}
	
	/**
	 * Test getRegisteredValidatorWithType
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testGetRegisteredValidatorWithType() {
		assertNull(descriptor.getRegisteredValidatorWithType(Validator.class));
	}
	
	/**
	 * Implementation of descriptor for the tests.
	 */
	private class Descriptor extends AbstractBoPropertyDescriptor<String> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new Descriptor object. 
		 */
		public Descriptor() {
			super(ParserResolver.getParser(String.class));			
		}
		
		@Override
		protected Formatter<String> getFormatter() {
			return FormatterResolver.getFormatter(String.class);
		}
		
	}
	
}
