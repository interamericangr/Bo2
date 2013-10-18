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
import gr.interamerican.bo2.utils.sql.types.IntegerType;

import org.junit.Test;

/**
 * Unit test for Parameter
 */
public class TestParameter {

	/**
	 * parameter to test
	 */
	Parameter parameter = new Parameter();
	
	/**
	 * NAME
	 */
	private static final String NAME = "name"; //$NON-NLS-1$
		
	/**
	 * TYPE
	 */
	private static final IntegerType TYPE = IntegerType.INSTANCE;
	
	
	/**
	 * Test setName
	 */
	@Test
	public void testSetName(){
		parameter.setName(NAME); 
		assertEquals(NAME,parameter.name); 
	}
	
	/**
	 * Test getName
	 */
	@Test
	public void testGetName(){
		parameter.name = NAME; 
		assertEquals(NAME,parameter.getName()); 
	}
	
	
	/**
	 * Test setType
	 */
	@Test
	public void testSetType(){
		parameter.setType(TYPE); 
		assertEquals(TYPE,parameter.type); 
	}
	
	/**
	 * Test getType
	 */
	@Test
	public void testGetType(){
		parameter.type = TYPE; 
		assertEquals(TYPE,parameter.getType()); 
	}
	
}
