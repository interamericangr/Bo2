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
package gr.interamerican.bo2.utils.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for {@link Null}.
 */
public class TestNull {
	
	/**
	 * Unit test for equals.
	 */
	@Test
	public void testEquals() {
		assertTrue(Null.NULL.equals(null));
		assertTrue(Null.NULL.equals(Null.NULL));
		Object obj = new Object();
		assertFalse(Null.NULL.equals(obj));
	}
	
	/**
	 * Unit test for hashcode.
	 */
	@Test
	public void testHashcode() {
		assertEquals(0, Null.NULL.hashCode());		
	}
	
	/**
	 * Unit test for hashcode.
	 */
	@Test
	public void testToString() {
		assertEquals("null", Null.NULL.toString());		 //$NON-NLS-1$
	}
	
	/**
	 * Unit test for compareTo.
	 */
	@Test
	public void testCompareTo() {
		assertTrue(Null.NULL.compareTo(null)==0);
		assertTrue(Null.NULL.compareTo(Null.NULL)==0);
		assertTrue(Null.NULL.compareTo(new Object())<0);
	}

}
