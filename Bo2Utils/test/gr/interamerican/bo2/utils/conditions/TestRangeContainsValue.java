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
 * Unit test for {@link RangeContainsValue}.
 */
public class TestRangeContainsValue {
	
	/**
	 * Unit test for check.
	 */
	@Test
	public void testCheck() {
		RangeContainsValue<Integer> condition = new RangeContainsValue<Integer>(5);
		Range<Integer> range = new Range<Integer>(2,7);
		Assert.assertTrue(condition.check(range));
	}

}
