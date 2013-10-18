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

import gr.interamerican.bo2.utils.conditions.IsNull;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link Filter}.
 */
public class TestConditionAdapter {
	
	/**
	 * Unit test for the adapter.
	 */
	@Test
	public void testExecute() {
		IsNull<Integer> condition = new IsNull<Integer>();		
		ConditionAdapter<Integer> adapter = new ConditionAdapter<Integer>(condition);
		
		Assert.assertSame(condition, adapter.condition);
		
		Assert.assertEquals(condition.check(5), adapter.execute(5));
		Assert.assertEquals(condition.check(null), adapter.execute(null));
	}

}
