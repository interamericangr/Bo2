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
 * Unit test for {@link LessThanOrEqual}.
 */
public class TestLessThanOrEqual {
	
	/**
	 * Tests check()
	 */
	@Test
	public void testCheck() {
		Comparator<Number> comp = new NumberComparator();
		LessThanOrEqual<Number> lt = new LessThanOrEqual<Number>(comp, 5);
		Assert.assertSame(comp, lt.comparator);
		Assert.assertTrue(lt.check(-12));
		Assert.assertTrue(lt.check(0));
		Assert.assertTrue(lt.check(4));
		Assert.assertTrue(lt.check(5));
		Assert.assertFalse(lt.check(8));
	}
	
	/**
	 * Tests check()
	 */
	@Test
	public void testCheck_2() {		
		LessThanOrEqual<Integer> lt = new LessThanOrEqual<Integer>(5);		
		Assert.assertTrue(lt.check(-12));
		Assert.assertTrue(lt.check(0));
		Assert.assertTrue(lt.check(4));
		Assert.assertTrue(lt.check(5));
		Assert.assertFalse(lt.check(8));
	}

}
