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
package gr.interamerican.bo2.impl.open.po;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * unit tests for {@link ValuesBasedKey}.
 */
public class TestValuesBasedKey {
	
	/**
	 * Tests setField1
	 */
	@Test
	public void testSetField1() {
		ValuesBasedKey o = new ValuesBasedKey();
		o.setElement(1, 1);
		Object[] expecteds = {1};
		assertArrayEquals(expecteds, o.getElements());
	}

	/**
	 * Tests setField2
	 */
	@Test
	public void testSetField2() {
		ValuesBasedKey o = new ValuesBasedKey();
		o.setElement(2, 1);
		Object[] expecteds = {null,1};
		assertArrayEquals(expecteds, o.getElements());
	}
	
	/**
	 * Tests setField2
	 */
	@Test
	public void testSetField1and2() {
		ValuesBasedKey o = new ValuesBasedKey();
		o.setElement(1, 1);
		o.setElement(2, 2);
		Object[] expecteds = {1,2};
		assertArrayEquals(expecteds, o.getElements());
	}
	
	/**
	 * Tests getField
	 */
	@Test
	public void testGetField() {
		ValuesBasedKey o = new ValuesBasedKey();
		int position = 4;
		Object expected1 = 1;
		o.setElement(position, expected1);		
		assertEquals(expected1, o.getElement(position));		
	}
	
	/**
	 * Tests getField
	 */
	@Test
	public void testSetFieldDoesNotMessWithOtherFields() {
		ValuesBasedKey o = new ValuesBasedKey();
		int position = 1;
		Object expected1 = 1;
		o.setElement(position, expected1);		
		assertEquals(expected1, o.getElement(position));
		
		position = 2;
		Object expected2 = 2;
		o.setElement(position, expected2);		
		assertEquals(expected2, o.getElement(position));
		assertEquals(expected1, o.getElement(1));
	}
	
	/**
	 * Tests getField
	 */
	@Test
	public void testGetFieldDoesNotThrowNullPointer() {
		ValuesBasedKey o = new ValuesBasedKey();
		assertNull(o.getElement(3));		
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_withArrayArg() {
		Object[] array = {5, 5L};
		ValuesBasedKey key = new ValuesBasedKey(array);
		assertEquals(5, key.getElement(1));
		assertEquals(5L, key.getElement(2));
	}
	
	
	


}
