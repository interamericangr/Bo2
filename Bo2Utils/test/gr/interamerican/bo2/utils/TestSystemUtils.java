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

import static gr.interamerican.bo2.utils.SystemUtils.freeMemory;
import static gr.interamerican.bo2.utils.SystemUtils.maxMemory;
import static gr.interamerican.bo2.utils.SystemUtils.totalMemory;
import static gr.interamerican.bo2.utils.SystemUtils.usedMemory;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for SystemUtils.
 */
public class TestSystemUtils {
	
	/**
	 * Test.
	 */
	@Test
	public void testUsedMemory() {
		Assert.assertTrue(usedMemory()>0);
	}
	
	
	/**
	 * Test.
	 */
	@Test
	public void testMaxMemory() {
		Assert.assertTrue(maxMemory()>0);
	}
	
	/**
	 * Test.
	 */
	@Test
	public void testFreeMemory() {
		Assert.assertTrue(freeMemory()>0);
	}	
	
	/**
	 * Test.
	 */
	@Test
	public void testTotalMemory() {
		Assert.assertTrue(totalMemory()>0);
	}

}
