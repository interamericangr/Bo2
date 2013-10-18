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
package gr.interamerican.bo2.utils.comparators;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * tests {@link SpecificNumberComparator}
 */
public class TestSpecificNumberComparator {

	/**
	 * Tests the class.
	 */
	@Test
	public void testSpecificNumberComparator() {
		SpecificNumberComparator<Integer> c = new SpecificNumberComparator<Integer>();
		assertTrue(c.compare(3, 3)==0);
		assertTrue(c.compare(3, 5)<0);
		assertTrue(c.compare(7, 5)>0);
		
		SpecificNumberComparator<Double> d = new SpecificNumberComparator<Double>();
		assertTrue(d.compare(3.0, 3.0)==0);
		assertTrue(d.compare(3.0, 5.2)<0);
		assertTrue(d.compare(7.0, 5.6)>0);
	}

	
}
