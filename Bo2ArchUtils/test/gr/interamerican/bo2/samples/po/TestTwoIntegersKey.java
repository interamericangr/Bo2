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
package gr.interamerican.bo2.samples.po;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.archutil.po.TwoIntegersKey;

import org.junit.Test;

/**
 * Unit test for {@link TwoIntegersKey}.
 */
public class TestTwoIntegersKey {
	
	/**
	 * sample key
	 */	
	TwoIntegersKey sample = new TwoIntegersKey(5,9);	
	/**
	 * little key.
	 */
	TwoIntegersKey little1 = new TwoIntegersKey(3,10);
	/**
	 * little key.
	 */
	TwoIntegersKey little2 = new TwoIntegersKey(5,8);
	/**
	 * little key.
	 */
	TwoIntegersKey big1 = new TwoIntegersKey(6,0);
	/**
	 * little key.
	 */
	TwoIntegersKey big2 = new TwoIntegersKey(5,11);
	/**
	 * little key.
	 */
	TwoIntegersKey eq = new TwoIntegersKey(5,9);	
	
	/**
	 * Test compare to.
	 */
	@Test
	public void testCompareTo() {
		assertTrue(sample.compareTo(little1)>0);
		assertTrue(sample.compareTo(little2)>0);
		assertTrue(sample.compareTo(big1)<0);
		assertTrue(sample.compareTo(big2)<0);
		assertTrue(sample.compareTo(eq)==0);
		assertTrue(sample.compareTo(sample)==0);
	}
	
	/**
	 * Test compare to.
	 * 
	 * Also implicitly tests no arg constructor and getters and setters.
	 */
	@Test
	public void testEquals() {
		assertFalse(sample.equals(little1));
		assertFalse(sample.equals(little2));
		assertFalse(sample.equals(big1));
		assertFalse(sample.equals(big2));
		assertTrue(sample.equals(eq));
		assertTrue(sample.equals(sample));
		
		TwoIntegersKey k1 = new TwoIntegersKey();
		k1.setFirst(sample.getFirst());
		k1.setSecond(sample.getSecond());
		assertTrue(sample.equals(k1));
	}
	

	
	
}
