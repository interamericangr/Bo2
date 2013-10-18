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
package gr.interamerican.bo2.impl.open.records;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link SimpleRecord}.
 */
@SuppressWarnings("nls")
public class TestSimpleRecord {
	/**
	 * Record for the tests.
	 */
	SimpleRecord rec = new SimpleRecord();
	
	/**
	 * Tests setBuffer.
	 */	
	@Test
	public void testSetBuffer() {
		String s = "setBuffer";
		rec.setBuffer(s);
		Assert.assertEquals(s, rec.buffer);		
	}
	

	/**
	 * Tests setBytes.
	 */	
	@Test
	public void testSetBytes() {
		String s = "setBytes";
		byte[] bytes = s.getBytes();
		rec.setBytes(bytes);
		Assert.assertEquals(s, rec.buffer);		
	}	
	
	/**
	 * Tests getBuffer.
	 */
	@Test
	public void testGetBuffer() {
		String s = "getBuffer";
		rec.buffer = s;
		Assert.assertEquals(rec.getBuffer(), rec.buffer);		
	}
	
	/**
	 * Tests getBuffer.
	 */
	@Test
	public void testGetBytes() {
		String s = "getBytes";
		rec.buffer = s;
		Assert.assertArrayEquals(rec.getBytes(), rec.buffer.getBytes());		
	}

}
