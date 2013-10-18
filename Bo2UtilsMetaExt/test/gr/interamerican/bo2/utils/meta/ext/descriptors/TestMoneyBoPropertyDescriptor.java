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
package gr.interamerican.bo2.utils.meta.ext.descriptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * 
 */
public class TestMoneyBoPropertyDescriptor {

	
	/**
	 * MoneyBoPropertyDescriptor
	 */
	MoneyBoPropertyDescriptor descriptor = new MoneyBoPropertyDescriptor();
	

	/**
	 * test setZeroAllowed
	 */
	@Test
	public void testSetZeroAllowed(){
		descriptor.setZeroAllowed(false);
		assertFalse(descriptor.zeroAllowed);
	}
	
	/**
	 * test test IsZeroAllowed
	 */
	@Test
	public void testIsZeroAllowed(){
		descriptor.setZeroAllowed(true);
		assertTrue(descriptor.isZeroAllowed());
	}
	
	
	/**
	 * test setNegativeAllowed
	 */
	@Test
	public void testSetNegativeAllowed(){
		descriptor.setNegativeAllowed(false);
		assertFalse(descriptor.negativeAllowed);
	}
	
	/**
	 * test test isNegativeAllowed
	 */
	@Test
	public void testIsNegativeAllowed(){
		descriptor.setZeroAllowed(true);
		assertTrue(descriptor.isNegativeAllowed());
	}
	
	
	/**
	 * test test setLengthOfIntegerPart
	 */
	@Test
	public void testSetLengthOfIntegerPart(){
		Integer length = 10;
		descriptor.setLengthOfIntegerPart(length);
		assertEquals(length,(Integer)descriptor.lengthOfIntegerPart);
	}
	
	/**
	 * test test getLengthOfIntegerPart
	 */
	@Test
	public void testGetLengthOfIntegerPart(){
		Integer length = 10;
		descriptor.setLengthOfIntegerPart(length);
		assertEquals(length,(Integer)descriptor.getLengthOfIntegerPart());
	}
	
	/**
	 * test test getLengthOfDecimalPart
	 */
	@Test
	public void testSetLengthOfDecimalPart(){
		Integer length = 10;
		descriptor.setLengthOfDecimalPart(length);
		assertEquals(length,(Integer)descriptor.lengthOfDecimalPart);
	}
	
	/**
	 * test test getLengthOfDecimalPart
	 */
	@Test
	public void testGetLengthOfDecimalPart(){
		Integer length = 10;
		descriptor.setLengthOfDecimalPart(length);
		assertEquals(length,(Integer)descriptor.getLengthOfDecimalPart());
	}
	
	
	
}
