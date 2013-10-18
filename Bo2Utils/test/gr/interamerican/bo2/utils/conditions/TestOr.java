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
public class TestOr {
	
	/**
	 * Tests check()
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCheck() {
		Or<Object> trueOr = new Or<Object>(new False<Object>(), new True<Object>());
		Assert.assertTrue(trueOr.check(null));
		Or<Object> falseOr = new Or<Object>(new False<Object>(), new False<Object>());
		Assert.assertFalse(falseOr.check(null));
		Or<Object> falseOr2 = new Or<Object>(new ArrayList<Condition<Object>>());		
		Assert.assertFalse(falseOr2.check(null));
	}

}
