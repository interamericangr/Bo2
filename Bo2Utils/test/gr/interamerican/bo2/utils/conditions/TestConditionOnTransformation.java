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
package gr.interamerican.bo2.utils.conditions;

import gr.interamerican.bo2.utils.adapters.AnyOperation;
import gr.interamerican.bo2.utils.adapters.GetTheSame;
import gr.interamerican.bo2.utils.comparators.SpecificNumberComparator;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestConditionOnTransformation {
	
	/**
	 * Tests check()
	 */
	@Test
	public void testCheck() {
		AnyOperation<Integer, Integer> transform = new GetTheSame<Integer>();
		GreaterThan<Integer> isPositive = new GreaterThan<Integer>(new SpecificNumberComparator<Integer>(), 0);
		ConditionOnTransformation<Integer, Integer> conditionOnTransformation = 
			new ConditionOnTransformation<Integer, Integer>(transform, isPositive);
		Assert.assertEquals(transform, conditionOnTransformation.transformation);
		Assert.assertEquals(isPositive, conditionOnTransformation.condition);
		Assert.assertTrue(conditionOnTransformation.check(5));
		Assert.assertFalse(conditionOnTransformation.check(-5));
	}

}
