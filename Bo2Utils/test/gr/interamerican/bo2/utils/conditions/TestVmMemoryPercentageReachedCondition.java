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

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests of {@link VmMemoryPercentageReachedCondition}
 */
public class TestVmMemoryPercentageReachedCondition {
	
	/**
	 * subject
	 */
	VmMemoryPercentageReachedCondition subject = new VmMemoryPercentageReachedCondition();
	
	/**
	 * Test check.
	 */
	@Test
	public void testCheck() {
		Assert.assertFalse(subject.check(100L));
		Assert.assertFalse(subject.check(99L));
		Assert.assertFalse(subject.check(98L));
		Assert.assertNotNull(subject.check(50L));
		Assert.assertTrue(subject.check(2L));
		Assert.assertTrue(subject.check(1L));
		Assert.assertTrue(subject.check(0L));
	}
	
	/**
	 * Test check.
	 */
	@Test(expected=RuntimeException.class)
	public void testCheck_rtex() {
		subject.check(101L);
	}

}
