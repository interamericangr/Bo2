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
package gr.interamerican.bo2.impl.open.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 */
public class TestConstantType {
	/**
	 * type.
	 */
	private static final Long TYPE = 1000L;
	
	/**
	 * type to test.
	 */
	private ConstantType type = new ConstantType(TYPE);
	
	/**
	 * test getTypeId().
	 */
	@Test
	public void testGetTypeId() {
		assertEquals(TYPE, type.getTypeId());		
	}
	
	/**
	 * test setTypeId() leaves typeId unmodified.
	 */
	@Test
	public void testSetTypeId() {
		type.setTypeId(0L);
		assertEquals(TYPE, type.getTypeId());		
	}
	
	


}
