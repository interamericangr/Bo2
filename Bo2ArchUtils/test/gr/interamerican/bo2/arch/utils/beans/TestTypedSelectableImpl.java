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
package gr.interamerican.bo2.arch.utils.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.arch.ext.Codified;

import org.junit.Test;

/**
 * TestTypedSelectableImpl
 */
public class TestTypedSelectableImpl {
	
	
	/**
	 * CODE
	 */
	private static final Integer CODE = 1;
	
	/**
	 * TYPE_ID
	 */
	private static final Long TYPE_ID = 1L;
	
	/**
	 * SUB_TYPE_ID
	 */
	private static final Long SUB_TYPE_ID = 1L;
	
	/**
	 * NAME
	 */
	private static final String NAME = "name"; //$NON-NLS-1$
	
	
	/**
	 * selectable
	 */
	TypedSelectableImpl<Integer> selectable = new TypedSelectableImpl<Integer>(TYPE_ID,SUB_TYPE_ID,CODE,NAME);
	
	/**
	 * emptySelectable
	 */
	TypedSelectableImpl<Integer> emptySelectable = new TypedSelectableImpl<Integer>();
	
	
	/**
	 * Test getCode
	 */
	@Test
	public void testGetCode(){
		assertEquals(CODE,selectable.getCode());
	}
	
	/**
	 * Test setCode
	 */
	@Test
	public void testSetCode(){
		selectable.setCode(CODE);
		assertEquals(CODE,selectable.getCode());
	}
	
	/**
	 * Test getTypeId
	 */
	@Test
	public void testGetTypeId(){
		assertEquals(TYPE_ID,selectable.getTypeId());
	}
	
	/**
	 * Test setTypeId
	 */
	@Test
	public void testSetTypeId(){
		selectable.setTypeId(TYPE_ID);
		assertEquals(TYPE_ID,selectable.getTypeId());
	}
	
	/**
	 * Test getSubTypeId
	 */
	@Test
	public void testGetSubTypeId(){
		assertEquals(SUB_TYPE_ID,selectable.getSubTypeId());
	}
	
	/**
	 * Test setSubTypeId
	 */
	@Test
	public void testSetSubTypeId(){
		selectable.setSubTypeId(SUB_TYPE_ID);
		assertEquals(SUB_TYPE_ID,selectable.getSubTypeId());
	}
	
	/**
	 * Test getName
	 */
	@Test
	public void testGetName(){
		assertEquals(NAME,selectable.getName());
	}
	
	/**
	 * Test setName
	 */
	@Test
	public void testSetName(){
		selectable.setName(NAME);
		assertEquals(NAME,selectable.getName());
	}

	/**
	 * Test Equals
	 */
	@Test
	public void testEquals(){
		TypedSelectableImpl<Integer> selectable2 = new TypedSelectableImpl<Integer>(TYPE_ID,SUB_TYPE_ID,CODE,NAME);
		assertTrue(selectable.equals(selectable2));
	}
	
	/**
	 * Test Equals
	 */
	@Test
	public void testEqualsWithFalseValue(){
		assertFalse(selectable.equals(new String()));
	}
	
	
	/**
	 * Test hashCode
	 */
	@Test
	public void testHashCode(){
		assertNotNull(selectable.hashCode());
	}
	
	/**
	 * Test CompareTo
	 */
	@Test
	public void testCompareTo(){
		
		assertEquals(1,selectable.compareTo(null));
		
		Codified <Integer> typed = new TypedSelectableImpl<Integer>();
		typed.setCode(1);
		selectable.compareTo(typed);
	}
	
}
