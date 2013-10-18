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
import gr.interamerican.bo2.arch.ext.Codified;

import org.junit.Test;

/**
 * TestCodifiedNamedImpl
 */
public class TestCodifiedNamedImpl {
	
	
	
	/**
	 * CODE
	 */
	private static final Integer CODE=1;
	
	/**
	 * name
	 */
	private static final String NAME= "name"; //$NON-NLS-1$
	
	/**
	 * emptyCodifiedName
	 */
	CodifiedNamedImpl<Integer> emptyCodifiedName = new CodifiedNamedImpl<Integer>();
	
	/**
	 * codifiedName
	 */
	CodifiedNamedImpl<Integer> codifiedName = new CodifiedNamedImpl<Integer>(CODE,NAME);
	
   
	/**
	 * Test getCode
	 */
	@Test
	public void testGetCode(){
		assertEquals(CODE,codifiedName.getCode());
	}
	
	
	/**
	 * Test setCode
	 */
	@Test
	public void testSetCode(){
		codifiedName.setCode(CODE);
		assertEquals(CODE,codifiedName.getCode());
	}
	
	
	/**
	 * Test getName
	 */
	@Test
	public void testGetName(){
		assertEquals(NAME,codifiedName.getName());
	}
	
	/**
	 * Test setName
	 */
	@Test
	public void testSetName(){
		codifiedName.setName(NAME);
		assertEquals(NAME,codifiedName.getName());
	}
	
	/**
	 * Test CompareTo
	 */
	@Test
	public void testCompareTo(){
		
		assertEquals(1,codifiedName.compareTo(null));
		
		Codified <Integer> typed = new TypedSelectableImpl<Integer>();
		typed.setCode(1);
		codifiedName.compareTo(typed);
	}
	
}
