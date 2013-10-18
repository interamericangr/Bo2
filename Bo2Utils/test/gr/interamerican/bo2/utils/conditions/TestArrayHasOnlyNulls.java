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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 */
public class TestArrayHasOnlyNulls {

	
	/**
	 * ArrayHasOnlyNulls
	 */
	ArrayHasOnlyNulls<Integer> condition = new ArrayHasOnlyNulls<Integer> ();
	
	
	/**
	 * test Check
	 */
	@Test
	public void testCheck_nullValues(){
		Integer [] array = {null,null};
		assertTrue(condition.check(array));
	}
	
	/**
	 * test Check
	 */
	@Test
	public void testCheck_noNullValues(){
		Integer [] array = {1,2};
		assertFalse(condition.check(array));
	}
	
}
