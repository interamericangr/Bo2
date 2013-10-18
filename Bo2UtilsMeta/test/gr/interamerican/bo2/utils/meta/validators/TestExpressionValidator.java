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

import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;

import org.junit.Test;

/**
 * Unit tests for {@link ExpressionValidator}.
 */
public class TestExpressionValidator {
	
	/**
	 * Sample regex.
	 */
	private String regex = "[^b]at"; //?at but not bat //$NON-NLS-1$
	
	/**
	 * Test subject.
	 */
	private ExpressionValidator validator = new ExpressionValidator(regex);

	/**
	 * Test method for validate(java.lang.String).
	 * @throws ValidationException 
	 */
	@Test
	public void testValidate() throws ValidationException {
		validator.validate("hat"); //$NON-NLS-1$
		
		boolean caught = false;
		try {
			validator.validate("bat"); //$NON-NLS-1$
		}catch(ValidationException ve) {
			caught = true;
		}
		assertTrue(caught);
	}

}
