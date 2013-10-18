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
package gr.interamerican.bo2.utils.sql;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;
import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.types.DateType;
import gr.interamerican.bo2.utils.sql.types.IntegerType;
import gr.interamerican.bo2.utils.sql.types.StringType;

import java.util.Date;

import org.junit.Test;

/**
 * Unit tests for {@link VariableDefinitionFactory}.
 */
@SuppressWarnings("nls")
public class TestVariableDefinitionFactory {
	
	/**
	 * Unit test for create with column.
	 */
	@Test
	public void testCreate_withColumn() {		
		Column col1 = new Column();
		col1.setAlias(null);
		col1.setName("col1"); //$NON-NLS-1$
		col1.setColumnType(IntegerType.INSTANCE);		
		VariableDefinition<?> vd1 = VariableDefinitionFactory.create(col1);
		assertEquals("col1", vd1.getName()); //$NON-NLS-1$
		assertEquals(Integer.class, vd1.getType());
	}
	
			
	/**
	 * Unit test for create with columns array.
	 */	
	@Test
	public void testCreate_withColumns() {		
		Column col1 = new Column();
		col1.setAlias(null);
		col1.setName("col1"); 
		col1.setColumnType(IntegerType.INSTANCE);
		Column col2 = new Column();
		col2.setAlias("second");
		col2.setName("col2"); 
		col2.setColumnType(StringType.INSTANCE);
		Column[] cols = {col1, col2};
		VariableDefinition<?>[] vds = VariableDefinitionFactory.create(cols);
		assertEquals(2, vds.length); 
		assertEquals("col1", vds[0].getName()); 
		assertEquals(Integer.class, vds[0].getType());
		assertEquals("second", vds[1].getName()); 
		assertEquals(String.class, vds[1].getType());
	}
	
	/**
	 * Unit test for create with parameter.
	 */
	@Test
	public void testCreate_withParameter() {		
		Parameter p1 = new Parameter();		
		p1.setName("parm1"); 
		p1.setType(IntegerType.INSTANCE);		
		VariableDefinition<?> vd1 = VariableDefinitionFactory.create(p1);
		assertEquals("parm1", vd1.getName()); 
		assertEquals(Integer.class, vd1.getType());
	}
	
	/**
	 * Unit test for create with paameters array.
	 */	
	@Test
	public void testCreate_withParameters() {		
		Parameter p1 = new Parameter();		
		p1.setName("parm1"); 
		p1.setType(IntegerType.INSTANCE);	
		Parameter p2 = new Parameter();		
		p2.setName("parm2"); 
		p2.setType(DateType.INSTANCE);	
		Parameter[] cols = {p1, p2};
		VariableDefinition<?>[] vds = VariableDefinitionFactory.create(cols);
		assertEquals(2, vds.length); 
		assertEquals("parm1", vds[0].getName()); 
		assertEquals(Integer.class, vds[0].getType());
		assertEquals("parm2", vds[1].getName()); 
		assertEquals(Date.class, vds[1].getType());
	}
		
	
	
	
	
	

}
