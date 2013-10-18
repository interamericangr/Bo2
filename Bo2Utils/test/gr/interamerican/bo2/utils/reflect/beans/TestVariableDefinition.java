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
package gr.interamerican.bo2.utils.reflect.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for {@link VariableDefinition}.
 */
public class TestVariableDefinition {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_withTwoArgs() {
		String name = "name"; //$NON-NLS-1$
		VariableDefinition<String> def = new VariableDefinition<String>(name, String.class);
		assertEquals(name, def.name);
		assertEquals(String.class, def.type);
	}
	
	
	/**
	 * Tests getName().
	 */
	@Test
	public void testGetName() {
		String name = "name"; //$NON-NLS-1$
		VariableDefinition<String> def = new VariableDefinition<String>(name, String.class);
		assertEquals(name, def.getName());
	}
	
	/**
	 * Tests getType().
	 */
	@Test
	public void testGetType() {		
		VariableDefinition<String> def = new VariableDefinition<String>(null, String.class);
		assertEquals(String.class, def.getType());
	}
	
	
	
	/**
	 * Tests getType().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetValue() {		
		VariableDefinition<String> def = new VariableDefinition<String>("var", String.class);
		String value = "value";
		def.value = value;
		assertEquals(value, def.getValue());
	}
	
	/**
	 * Tests getType().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSetValue() {		
		VariableDefinition<String> def = new VariableDefinition<String>("var", String.class);
		String value = "value";
		def.setValue(value);
		assertEquals(value, def.value);
	}

}
