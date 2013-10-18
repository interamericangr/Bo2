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

import gr.interamerican.bo2.utils.comparators.NumberComparator;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link EqualTo}.
 */
public class TestEqualTo {
	
	/**
	 * Tests check()
	 */
	@Test
	public void testCheck() {
		Comparator<Number> comp = new NumberComparator();
		EqualTo<Number> et = new EqualTo<Number>(comp, 5);
		Assert.assertSame(comp, et.comparator);
		
		Assert.assertFalse(et.check(-12));
		Assert.assertFalse(et.check(4));
		Assert.assertTrue(et.check(5));
		Assert.assertFalse(et.check(8));
	}
	
	/**
	 * Tests check()
	 */
	@Test
	public void testCheck_2() {		
		EqualTo<Integer> et = new EqualTo<Integer>(5);		
		Assert.assertFalse(et.check(-12));
		Assert.assertFalse(et.check(4));
		Assert.assertTrue(et.check(5));
		Assert.assertFalse(et.check(8));
	}

}
