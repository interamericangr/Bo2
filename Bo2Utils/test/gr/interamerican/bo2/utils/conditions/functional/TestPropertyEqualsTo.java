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

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWith2Fields;
import gr.interamerican.bo2.utils.conditions.EqualityCondition;

/**
 * Unit test for {@link PropertyEqualsTo}.
 */
public class TestPropertyEqualsTo {

	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		PropertyEqualsTo<BeanWith2Fields,?> condition = new PropertyEqualsTo<>(
				BeanWith2Fields::getField1, "5"); //$NON-NLS-1$
		Assert.assertNotNull(condition.getCondition());
		Assert.assertTrue(condition.getCondition() instanceof EqualityCondition);
	}
}