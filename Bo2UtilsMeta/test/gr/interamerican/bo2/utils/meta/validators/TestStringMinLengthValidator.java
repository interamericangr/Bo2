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
package gr.interamerican.bo2.utils.meta.validators;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;

import org.junit.Test;
/**
 * StringMaxLengthValidatorTest
 */
public class TestStringMinLengthValidator {

	
	/**
	 * StringMinLengthValidator me length iso me 2
	 */
	StringMinLengthValidator stringMinLenghtVal = new StringMinLengthValidator(2);
	/**
	 * StringMinLengthValidator me length iso me 0
	 */
	StringMinLengthValidator zeroLengthVal = new StringMinLengthValidator();
	
	/**
	 * Test getMaxLength
	 */
	@Test
	public void testGetMinLength(){
 
		assertEquals(2,stringMinLenghtVal.getMinLength());
	}
	
	/**
	 * Test setMaxLength
	 */
	@Test
	public void testSetMinLength(){
 
		stringMinLenghtVal.setMinLength(4);
	}
	
	
	/**
	 * Test validate
	 * @throws ValidationException 
	 */
	@Test
	public void testValidateHigherLength() throws ValidationException{
 
		stringMinLenghtVal.validate("1234"); //$NON-NLS-1$
	}
	
	/**
	 * Test validate
	 * @throws ValidationException 
	 */
	@Test(expected=ValidationException.class)
	public void testValidateLowerLength() throws ValidationException{
 
		stringMinLenghtVal.validate("1"); //$NON-NLS-1$
	}
	
	
}
