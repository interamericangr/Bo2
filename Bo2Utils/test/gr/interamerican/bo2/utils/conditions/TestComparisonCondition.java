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

import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.comparators.ComparablesComparator;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link EqualTo}.
 */
public class TestComparisonCondition {
	
	/**
	 * Tests constructor
	 */
	@Test
	public void testConstructor_1() {		
		Comparator<Object> comparator = Utils.cast(new ComparablesComparator());
		Object o = new Object();
		CC cc = new CC(comparator, o);
		Assert.assertEquals(comparator, cc.comparator);
		Assert.assertEquals(o, cc.comparedValue);
	}
	
	/**
	 * Tests constructor
	 */
	@Test
	public void testConstructor_2() {
		Object o = 5;
		CC cc = new CC(o);		
		Assert.assertEquals(o, cc.comparedValue);
	}
	
	
	/**
	 * Tests constructor
	 */
	@Test(expected=RuntimeException.class)
	public void testConstructor_fail() {
		Object o = new Object();
		CC cc = new CC(o);		
	}
	
	class CC extends ComparisonCondition<Object> {
		/**
		 * Creates a new CC object. 
		 *
		 * @param comparator
		 * @param comparedValue
		 */
		CC(Comparator<Object> comparator, Object comparedValue) {
			super(comparator, comparedValue);
		}

		/**
		 * Creates a new CC object. 
		 *
		 * @param comparedValue
		 */
		CC(Object comparedValue) {
			super(comparedValue);
		}

		public boolean check(Object t) {			
			return false;
		}		
	}

}
