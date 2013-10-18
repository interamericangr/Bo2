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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.formatters.DecimalFormatter;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.parsers.DoubleParser;
import gr.interamerican.bo2.utils.meta.validators.NumberDecimalLengthValidator;
import gr.interamerican.bo2.utils.meta.validators.NumberIntegerLengthValidator;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test of {@link AbstractNumberBoPropertyDescriptor}.
 */
public class TestAbstractNumberBoPropertyDescriptor {
	
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
	@SuppressWarnings("unchecked")
	public void testConstructor(){
		assertNotNull(descriptor.parser);
		assertNotNull(descriptor.getFormatter());
		assertEquals(2, descriptor.validators.size());
		assertEquals(descriptor.lengthOfDecimalPart, descriptor.getLengthOfDecimalPart());
		assertEquals(descriptor.lengthOfIntegerPart, descriptor.getLengthOfIntegerPart());
		
		assertEquals(descriptor.lengthOfIntegerPart,
			descriptor.getRegisteredValidatorWithType(NumberIntegerLengthValidator.class).getMaxIntegerLength());
		assertEquals(descriptor.lengthOfDecimalPart,
			descriptor.getRegisteredValidatorWithType(NumberDecimalLengthValidator.class).getMaxDecimalLength());
	}	
		
	/**
	 * Test setZeroAllowed, setNegativeAllowed, validate
	 * @throws ValidationException
	 */
	@Test 	
	public void testSetZeroAllowedNegativeAllowedAndValidate() throws ValidationException{
		descriptor.setZeroAllowed(false);
		assertEquals(3, descriptor.validators.size());
		descriptor.setNegativeAllowed(false);
		assertEquals(4, descriptor.validators.size());
		descriptor.setZeroAllowed(true);
		assertEquals(3, descriptor.validators.size());
		descriptor.validate(0.0d);
		descriptor.setNegativeAllowed(true);
		assertEquals(2, descriptor.validators.size());
		descriptor.validate(-1.0d);
		assertEquals(2, descriptor.validators.size());
		
		descriptor.setZeroAllowed(false);
		boolean caught = false;
		try {
			descriptor.validate(0.0d);
		} catch (ValidationException ve) {
			caught = true;
		}
		assertTrue(caught);
		
		descriptor.setNegativeAllowed(false);
		caught = false;
		try {
			descriptor.validate(-1.0d);
		} catch (ValidationException ve) {
			caught = true;
		}
		assertTrue(caught);
	}
	
	/**
	 * Test setLengthOfIntegerPart, setLengthOfDecimalPart, validate
	 * @throws ValidationException
	 */
	@Test 	
	@SuppressWarnings("unchecked")
	public void testSetLengthOfIntegerPartLengthOfDecimalPartAndValidate() throws ValidationException{
		descriptor.validate(1000.101d);

		descriptor.setLengthOfIntegerPart(1);
		assertEquals(descriptor.getLengthOfIntegerPart(), descriptor.lengthOfIntegerPart);
		assertEquals(descriptor.getLengthOfIntegerPart(),
				descriptor.getRegisteredValidatorWithType(NumberIntegerLengthValidator.class).getMaxIntegerLength());

		descriptor.setLengthOfDecimalPart(1);
		assertEquals(descriptor.getLengthOfDecimalPart(), descriptor.lengthOfDecimalPart);
		assertEquals(descriptor.getLengthOfDecimalPart(),
				descriptor.getRegisteredValidatorWithType(NumberDecimalLengthValidator.class).getMaxDecimalLength());
		
		boolean caught = false;
		try {
			descriptor.validate(10.0d);
		} catch (ValidationException ve) {
			caught = true;
		}
		assertTrue(caught);
		
		caught = false;
		try {
			descriptor.validate(1.101d);
		} catch (ValidationException ve) {
			caught = true;
		}
		assertTrue(caught);
	}
	
	/**
	 * Implementation of descriptor for the tests.
	 */
	private class Descriptor extends AbstractNumberBoPropertyDescriptor<Double> {
		/**
		 * Creates a new Descriptor object. 
		 */
		public Descriptor() {
			super(new DoubleParser(1));			
		}
		
		@Override
		protected Formatter<Double> getFormatter() {
			return new DecimalFormatter<Double>(1);
		}
	}
	
}
