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

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link Array}.
 */
public class TestArray {
	
	/**
	 * Test
	 */
	@Test
	public void testConstructor() {
		@SuppressWarnings("nls")
		Object[] objects = {1, "this", null, 2L};
		Array array = new Array(objects);
		Assert.assertArrayEquals(objects, array.array);
	}
	
	/**
	 * Test
	 */
	@Test
	public void testHashcodeAndEquals() {
		Array a1 = new Array(1, 2L, null);
		Array a2 = new Array(1, 2L, null);
		Assert.assertEquals(a1.hashCode(), a2.hashCode());
		Assert.assertEquals(a1, a2);
	}

}
