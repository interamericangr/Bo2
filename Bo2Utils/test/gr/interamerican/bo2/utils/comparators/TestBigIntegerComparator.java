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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * 
 */
public class TestBigIntegerComparator {

	
	/**
	 * BigIntegerComparator
	 */
	BigIntegerComparator bc =  new BigIntegerComparator();
	
	/**
	 * test compare when numbers are BigDecimal
	 */
	@Test
	public void testCompare(){
		BigDecimal bd1 = new BigDecimal(10);
		BigDecimal bd2 = new BigDecimal(10);
		assertEquals(0,bc.compare(bd1, bd2));
	}
	
}
