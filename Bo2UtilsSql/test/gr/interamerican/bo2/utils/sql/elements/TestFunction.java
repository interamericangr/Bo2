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
package gr.interamerican.bo2.utils.sql.elements;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for Function
 */
public class TestFunction {

	
	/**
	 * NAME
	 */
	private static final String NAME = "functionName"; //$NON-NLS-1$
		
	/**
	 * TYPE
	 */
	private static final int ARGUMENTS = 10;
	
	
	/**
	 * parameter to test
	 */
	Function function = new Function(NAME,ARGUMENTS);
	
	
	/**
	 * Test setName
	 */
	@Test
	public void testSetName(){
		function.setName("otherName"); //$NON-NLS-1$
		assertEquals("otherName",function.name);  //$NON-NLS-1$
	}
	
	/**
	 * Test getName
	 */
	@Test
	public void testGetName(){
		assertEquals(NAME.toUpperCase(),function.getName()); 
	}
	
	/**
	 * Test setArguments
	 */
	@Test
	public void testSetArguments(){
		function.setArguments(20); 
		assertEquals(20,function.arguments);  
	}
	
	/**
	 * Test getArguments
	 */
	@Test
	public void testGetArguments(){
		assertEquals(ARGUMENTS,function.getArguments()); 
	}
	

	
}
