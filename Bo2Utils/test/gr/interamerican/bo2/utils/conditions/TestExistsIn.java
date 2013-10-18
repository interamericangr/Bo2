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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link ExistsIn}.
 */
@SuppressWarnings("nls")
public class TestExistsIn {
	
	/**
	 * Tests the constructor that takes the values as a collection.
	 */
	@Test
	public void testConstructor_withCollection() {
		List<String> list = new ArrayList<String>();
		list.add("this");
		list.add("that");
		ExistsIn<String> cond = new ExistsIn<String>(list);
		Assert.assertNotNull(cond.set);
		Assert.assertTrue(cond.set.containsAll(list));
	}
	
	/**
	 * Tests the constructor that takes the values as a collection.
	 */	
	@Test
	public void testConstructor_withVarargs() {
		String[] array = {"this", "that"};
		ExistsIn<String> cond = new ExistsIn<String>(array);
		Assert.assertNotNull(cond.set);
		Assert.assertTrue(cond.set.containsAll(Arrays.asList(array)));		
	}
	
	/**
	 * Tests the constructor that takes the values as a collection.
	 */	
	@Test
	public void testCheck() {		
		ExistsIn<String> cond = new ExistsIn<String>("this", "that");		
		Assert.assertTrue(cond.check("this"));
		Assert.assertTrue(cond.check("that"));
		Assert.assertFalse(cond.check("then"));
	}
	
	

}
