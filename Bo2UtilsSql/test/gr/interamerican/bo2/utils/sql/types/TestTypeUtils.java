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
package gr.interamerican.bo2.utils.sql.types;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.samples.bean.Family;

import java.sql.Types;

import org.junit.Test;

/**
 * 
 */
public class TestTypeUtils {

	
	/**
	 * Tests getTypeForClass
	 */
	@Test
	public void testGetTypeForClass(){
		Type<?> expected = StringType.INSTANCE;
		Type<?> actual = TypeUtils.getTypeForClass(String.class);
		assertEquals(expected.getClass(),actual.getClass());
	}
	
	
	/**
	 * Tests getTypeForClass when value is null
	 */
	@Test
	public void testGetTypeForClass_nullValue(){
		Type<?> expected = ObjectType.INSTANCE;
		Type<?> actual = TypeUtils.getTypeForClass(Family.class);
		assertEquals(expected.getClass(),actual.getClass());
	}
	
	/**
	 * Tests getTypeForClass when value is null
	 */
	@Test
	public void testGetTypeOfSqlType(){
		Type<?> expected = IntegerType.INSTANCE;
		Type<?> actual = TypeUtils.getTypeOfSqlType(Types.INTEGER);
		assertEquals(expected,actual);
	}
	
}
