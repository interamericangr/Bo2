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

import gr.interamerican.bo2.utils.beans.Range;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestIsWithinRange {
	
	/**
	 * Tests the constructor that takes the values as a collection.
	 */
	@Test
	public void testCheck() {
		Range<Double> range = new Range<Double>(2.0, 4.0);
		IsWithinRange<Double> condition = new IsWithinRange<Double>(range);
		Assert.assertFalse(condition.check(1.9));
		Assert.assertTrue(condition.check(2.0));
		Assert.assertTrue(condition.check(3.0));
		Assert.assertTrue(condition.check(4.0));
		Assert.assertFalse(condition.check(5.6));
	}

}
