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
package gr.interamerican.bo2.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.EqualsIgnoreCaseCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;



/**
 * Unit tests for {@link ConditionUtils}.
 */
@SuppressWarnings("nls")
public class TestConditionUtils {
	/**
	 * strings for the test.
	 */
	String[] strings = {"alpha", "beta", "gamma", "Alpha"};
	
	/**
	 * Tests fillSubset()
	 */	
	@Test
	public void testFillSubset() {		
		List<String> list = Arrays.asList(strings);
		ArrayList<String> subset = new ArrayList<String>();
		Condition<String> cond = new EqualsIgnoreCaseCondition("alpha");
		ConditionUtils.fillSubset(list, subset, cond);
		assertEquals(2, subset.size());
		for (String string : subset) {
			assertTrue(cond.check(string));
		}
	}
	
	/**
	 * Tests fillSubset()
	 */	
	@Test
	public void testGetSubset() {		
		List<String> list = Arrays.asList(strings);		
		Condition<String> cond = new EqualsIgnoreCaseCondition("alpha");
		List<String> subset = ConditionUtils.getSubset(list, cond);
		assertEquals(2, subset.size());
		for (String string : subset) {
			assertTrue(cond.check(string));
		}
	}
	

}
