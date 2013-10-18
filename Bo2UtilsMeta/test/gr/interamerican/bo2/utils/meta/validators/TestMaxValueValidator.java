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
 * MaxValueValidatorTest
 */
public class TestMaxValueValidator {
	
	
	
	/**
	 * MaxValueValidator
	 */
	MaxValueValidator<Long> maxValid = new MaxValueValidator<Long>(1000L);
	
	/**
	 * Test getMaximum
	 */
	@Test
	public void testGetMaximum(){
		
	    assertEquals((Long)1000L,maxValid.getMaximum());
	}
	
	/**
	 * Test setMaxLength
	 */
	@Test
	public void testSetMaxLength(){
		
	    maxValid.setMaxLength(2000L);
	}

	
	/**
	 * Test validate
	 * @throws ValidationException 
	 */
	@Test
	public void testValidateLowerValue() throws ValidationException{
		
	    maxValid.validate(500L);
	}
	
	/**
	 * Test validate
	 * @throws ValidationException 
	 */
	@Test(expected=ValidationException.class)
	public void testValidateHigherValue() throws ValidationException{
		
	    maxValid.validate(1500L);
	}
	
	
}
