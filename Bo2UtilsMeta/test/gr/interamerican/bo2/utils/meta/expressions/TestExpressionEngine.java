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
package gr.interamerican.bo2.utils.meta.expressions;

import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Unit tests for {@link ExpressionEngine}.
 */
public class TestExpressionEngine {

	/**
	 * Test method for evaluate
	 * @throws ValidationException 
	 */
	@Test
	public void testEvaluate() throws ValidationException {
		String expression = "age>80 && amount<100"; //$NON-NLS-1$
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("age", 81); //$NON-NLS-1$
		context.put("amount", 99); //$NON-NLS-1$
		ExpressionEngine.INSTANCE.evaluate(expression, context, null);
	}
	
	/**
	 * Test method for evaluate
	 * @throws ValidationException 
	 */
	@Test(expected=ValidationException.class)
	public void testEvaluate_throwsVex() throws ValidationException {
		String expression = "age>80 && amount<100"; //$NON-NLS-1$
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("age", 79); //$NON-NLS-1$
		context.put("amount", 99); //$NON-NLS-1$
		ExpressionEngine.INSTANCE.evaluate(expression, context, null);
	}

}
