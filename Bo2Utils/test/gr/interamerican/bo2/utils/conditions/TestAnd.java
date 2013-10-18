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

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link EqualTo}.
 */
public class TestAnd {
	
	/**
	 * Tests check()
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCheck() {
		And<Object> trueAnd = new And<Object>(new True<Object>(), new True<Object>());
		Assert.assertTrue(trueAnd.check(null));
		And<Object> falseAnd = new And<Object>(new True<Object>(), new False<Object>());
		Assert.assertFalse(falseAnd.check(null));
		And<Object> trueAnd2 = new And<Object>(new ArrayList<Condition<Object>>());
		Assert.assertTrue(trueAnd2.check(null));
	}

}
