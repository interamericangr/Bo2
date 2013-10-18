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
package gr.interamerican.bo2.creation.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 
 */
public class TestJavabeanDefinition {

	
	/**
	 * list with definitions
	 */
	List<VariableDefinition<?>> fields = new ArrayList<VariableDefinition<?>>();
		
	/**
	 * JavabeanDefinition
	 */
	JavabeanDefinition definition;
	
	/**
	 * Tests getType().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetType() {		
		
		VariableDefinition<String> stringDef = new VariableDefinition<String>("field1", String.class);
		VariableDefinition<Integer> intDef = new VariableDefinition<Integer>("field2", Integer.class);
		fields.add(stringDef);
		fields.add(intDef);
		
		definition = new JavabeanDefinition("typeName", fields);
		assertEquals(String.class,definition.getFieldType("field1"));
		assertEquals(Integer.class,definition.getFieldType("field2"));
		assertNull(definition.getFieldType("field3"));
	}
}
