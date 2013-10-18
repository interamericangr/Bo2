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
package gr.interamerican.bo2.samples.implopen.po;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.archutil.po.UserKey;

import org.junit.Test;

/**
 * 
 */
public class TestUserKey {
	
	/**
	 * sample key
	 */	
	UserKey sample = new UserKey(5);	
	/**
	 * little key.
	 */
	UserKey little = new UserKey(3);
	/**
	 * greater key.
	 */
	UserKey big = new UserKey(9);
	/**
	 * 
	 */
	UserKey equal = new UserKey(5);
	/**
	 * 
	 */
	UserKey nu = new UserKey(null);
	
	/**
	 * Test compare to.
	 */
	@Test
	public void testCompareTo() {
		assertTrue(sample.compareTo(little)>0);
		assertTrue(sample.compareTo(big)<0);
		assertTrue(sample.compareTo(equal)==0);
		assertTrue(sample.compareTo(sample)==0);
		assertTrue(sample.compareTo(nu)>0);
		assertTrue(nu.compareTo(sample)<0);
	}
	
	/**
	 * Test compare to.
	 */
	@Test
	public void testEquals() {
		assertFalse(sample.equals(little));
		assertFalse(sample.equals(big));
		assertTrue(sample.equals(equal));
		assertTrue(sample.equals(sample));
		assertFalse(sample.equals(nu));
		assertFalse(nu.equals(sample));
	}
	
	/**
	 * Test compare to.
	 */
	@Test
	public void testHashCode() {
		assertEquals(sample.hashCode(),sample.getId().intValue());
		assertEquals(nu.hashCode(),0);
	}
	
}
