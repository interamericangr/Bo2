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
import gr.interamerican.bo2.utils.meta.validators.StringMaxLengthValidator;
import gr.interamerican.bo2.utils.meta.validators.StringMinLengthValidator;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link StringBoPropertyDescriptor}.
 */
public class TestStringBoPropertyDescriptor {
	
	/**
	 * Sample StringBoPropertyDescriptor.
	 */
	private StringBoPropertyDescriptor descriptor;
	
	/**
	 * Setup the sample.
	 */
	@Before
	public void setup() {
		descriptor = new StringBoPropertyDescriptor();
	}
	
	/**
	 * Test creation
	 */
	@Test
	public void testConstruct() {
		assertNotNull(descriptor.parser);
		assertNotNull(descriptor.getFormatter());
		assertEquals(2, descriptor.validators.size());
		assertEquals(descriptor.minLength, descriptor.getMinLength());
		assertEquals(descriptor.maxLength, descriptor.getMaxLength());
		
		assertEquals(descriptor.minLength,
			descriptor.getRegisteredValidatorWithType(StringMinLengthValidator.class).getMinLength());
		assertEquals(descriptor.maxLength,
			descriptor.getRegisteredValidatorWithType(StringMaxLengthValidator.class).getMaxLength());
	}

	/**
	 * Test setMinLength, setMaxLength, validate
	 * @throws ValidationException
	 */
	@Test 	
	public void testSetZeroAllowedNegativeAllowedAndValidate() throws ValidationException{
		descriptor.setMinLength(5);
		descriptor.validate("value"); //$NON-NLS-1$
		
		boolean caught = false;
		try {
			descriptor.validate("a"); //$NON-NLS-1$
		} catch (ValidationException ve) {
			caught = true;
		}
		assertTrue(caught);
		
		descriptor.setMaxLength(5);
		caught = false;
		try {
			descriptor.validate("longString"); //$NON-NLS-1$
		} catch (ValidationException ve) {
			caught = true;
		}
		assertTrue(caught);
	}
	
	/**
	 * Test setExpression
	 */
	@Test
	public void testSetExpression() {
		assertEquals(2, descriptor.validators.size());
		descriptor.setExpression("lala"); //$NON-NLS-1$
		assertEquals(3, descriptor.validators.size());
		descriptor.setExpression(""); //$NON-NLS-1$
		assertEquals(2, descriptor.validators.size());
		descriptor.setExpression("lala"); //$NON-NLS-1$
		assertEquals(3, descriptor.validators.size());
		descriptor.setExpression(null);
		assertEquals(2, descriptor.validators.size());
	}
}
