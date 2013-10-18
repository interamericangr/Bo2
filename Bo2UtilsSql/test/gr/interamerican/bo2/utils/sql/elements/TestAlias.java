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
 * Unit test for alias
 */
public class TestAlias {

	
	/**
	 * alias to test
	 */
	Alias alias = new Alias();
	
	/**
	 * ALIAS_NAME
	 */
	private static final String ALIAS_NAME = "aliasName"; //$NON-NLS-1$
		
	/**
	 * TBNAME
	 */
	private static final String TBNAME = "tbName"; //$NON-NLS-1$
	
	/**
	 * TBCREATOR
	 */
	private static final String TBCREATOR = "tbCreator"; //$NON-NLS-1$
	
	/**
	 * CREATOR
	 */
	private static final String CREATOR = "creator"; //$NON-NLS-1$
	
	
	/**
	 * Test setName
	 */
	@Test
	public void testSetName(){
		alias.setName(ALIAS_NAME);
		assertEquals(ALIAS_NAME,alias.name);
	}
	
	/**
	 * Test getName
	 */
	@Test
	public void testGetName(){
		alias.name = ALIAS_NAME;
		assertEquals(ALIAS_NAME,alias.getName());
	}
	
	
	/**
	 * Test setTbName
	 */
	@Test
	public void testSetTbName(){
		alias.setTbName(TBNAME);
		assertEquals(TBNAME,alias.tbName);
	}
	
	
	/**
	 * Test getTbName
	 */
	@Test
	public void testGetTbName(){
		alias.tbName = TBNAME;
		assertEquals(TBNAME,alias.getTbName());
	}
	
	/**
	 * Test setTbCreator
	 */
	@Test
	public void testSetTbCreator(){
		alias.setTbCreator(TBCREATOR);
		assertEquals(TBCREATOR,alias.tbCreator);
	}
	
	/**
	 * Test getTbCreator
	 */
	@Test
	public void testGetTbCreator(){
		alias.tbCreator = TBCREATOR;
		assertEquals(TBCREATOR,alias.getTbCreator()); 
	}
	
	/**
	 * Test setCreator
	 */
	@Test
	public void testSetCreator(){
		alias.setCreator(CREATOR); 
		assertEquals(CREATOR,alias.creator); 
	}
	
	/**
	 * Test getCreator
	 */
	@Test
	public void testGetCreator(){
		alias.creator =CREATOR; 
		assertEquals(CREATOR,alias.getCreator()); 
	}
	
}
