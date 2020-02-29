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
package gr.interamerican.bo2.utils.conditions.functional;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.utils.conditions.ExistsIn;

/**
 * Unit test for {@link PropertyExistsIn}.
 */
public class TestPropertyExistsIn {

	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_withArray() {
		@SuppressWarnings("nls")
		PropertyExistsIn<BeanWith2Fields, String> condition = new PropertyExistsIn<>(BeanWith2Fields::getField1, "that",
				"them");
		Assert.assertNotNull(condition.getCondition());
		Assert.assertTrue(condition.getCondition() instanceof ExistsIn);
	}

	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_withCollection() {
		@SuppressWarnings("nls")
		String[] strings = { "that", "them" };
		PropertyExistsIn<BeanWith2Fields, String> condition = new PropertyExistsIn<>(BeanWith2Fields::getField1,
				Arrays.asList(strings));
		Assert.assertNotNull(condition.getCondition());
		Assert.assertTrue(condition.getCondition() instanceof ExistsIn);
	}

}
