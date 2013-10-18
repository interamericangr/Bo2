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
package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.comparators.SpecificNumberComparator;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.GreaterThan;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link Filter}.
 */
public class TestFilter {
	
	/**
	 * Unit test for the adapter.
	 */
	@Test
	public void testExecute() {
		Comparator<Integer> comparator = new SpecificNumberComparator<Integer>();
		Condition<Integer> condition = new GreaterThan<Integer>(comparator, 0);
		Filter<Integer> filter = new Filter<Integer>(condition);
		
		Assert.assertSame(condition, filter.condition);
		
		Assert.assertEquals(Integer.valueOf(5), filter.execute(5));
		Assert.assertNull(filter.execute(0));
		Assert.assertNull(filter.execute(-2));
	}

}
