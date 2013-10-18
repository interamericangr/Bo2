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
 * Unit test for Column
 */
public class TestColumn {

	/**
	 * column to test
	 */
	Column column = new Column();
	
	/**
	 * ALIAS
	 */
	private static final String ALIAS = "alias"; //$NON-NLS-1$
	
	/**
	 * NAME
	 */
	private static final String NAME = "name"; //$NON-NLS-1$
	
	/**
	 * TBNAME
	 */
	private static final String TBNAME = "tbName"; //$NON-NLS-1$
	
	/**
	 * TBCREATOR
	 */
	private static final String TBCREATOR = "tbCreator"; //$NON-NLS-1$
	
	/**
	 * COLUMN_NUMBER
	 */
	private static final int COLUMN_NUMBER = 1;
	
	/**
	 * COLUMN_TYPE
	 */
	private static final IntegerType COLUMN_TYPE = IntegerType.INSTANCE;
	
	/**
	 * LENGTH
	 */
	private static final int LENGTH = 10;
	
	/**
	 * SCALE
	 */
	private static final int SCALE = 10;
	
	/**
	 * LABEL
	 */
	private static final String LABEL = "label"; //$NON-NLS-1$
	
	/**
	 * REAMARKS
	 */
	private static final String REAMARKS = "remarks"; //$NON-NLS-1$
	
	
	/**
	 * Test setAlias
	 */
	@Test
	public void testSetAlias(){
		column.setAlias(ALIAS); 
		assertEquals(ALIAS,column.alias); 
	}
	
	
	/**
	 * Test getAlias
	 */
	@Test
	public void testGetAlias(){
		column.alias = ALIAS; 
		assertEquals(ALIAS,column.getAlias()); 
	}
	
	
	/**
	 * Test setName
	 */
	@Test
	public void testSetName(){
		column.setName(NAME); 
		assertEquals(NAME,column.name); 
	}
	
	/**
	 * Test getName
	 */
	@Test
	public void testGetName(){
		column.name = NAME; 
		assertEquals(NAME,column.getName()); 
	}
	
	/**
	 * Test setTbName
	 */
	@Test
	public void testSetTbName(){
		column.setTbName(TBNAME); 
		assertEquals(TBNAME,column.tbName); 
	}
	
	/**
	 * Test getTbName
	 */
	@Test
	public void testGetTbName(){
		column.tbName = TBNAME; 
		assertEquals(TBNAME,column.getTbName()); 
	}
	
	/**
	 * Test getTbCreator
	 */
	@Test
	public void testSetTbCreator(){
		column.setTbCreator(TBCREATOR); 
		assertEquals(TBCREATOR,column.tbCreator); 
	}
	
	/**
	 * Test getTbCreator
	 */
	@Test
	public void testGetTbCreator(){
		column.setTbCreator(TBCREATOR); 
		assertEquals(TBCREATOR,column.getTbCreator()); 
	}
	
	/**
	 * Test setColumnNo
	 */
	@Test
	public void testSetColumnNo(){
		column.setColumnNo(COLUMN_NUMBER); 
		assertEquals((Integer)COLUMN_NUMBER,column.columnNo); 
	}
	
	/**
	 * Test getColumnNo
	 */
	@Test
	public void testGetColumnNo(){
		column.columnNo = COLUMN_NUMBER; 
		assertEquals((Integer)COLUMN_NUMBER,column.getColumnNo()); 
	}
	
	
	/**
	 * Test setColumnType
	 */
	@Test
	public void testSetColumnType(){
		column.setColumnType(COLUMN_TYPE); 
		assertEquals(COLUMN_TYPE,column.columnType); 
	}
	
	/**
	 * Test getColumnType
	 */
	@Test
	public void testGetColumnType(){
		column.columnType = COLUMN_TYPE; 
		assertEquals(COLUMN_TYPE,column.getColumnType()); 
	}
	
	/**
	 * Test setLength
	 */
	@Test
	public void testSetLength(){
		column.setLength(LENGTH); 
		assertEquals((Integer)LENGTH,column.length);
	}
	
	/**
	 * Test getLength
	 */
	@Test
	public void testGetLength(){
		column.length = LENGTH; 
		assertEquals((Integer)LENGTH,column.getLength());
	}
	
	/**
	 * Test getScale
	 */
	@Test
	public void testSetScale(){
		column.setScale(SCALE); 
		assertEquals((Integer)SCALE,column.scale);
	}
	
	/**
	 * Test getScale
	 */
	@Test
	public void testGetScale(){
		column.scale = SCALE; 
		assertEquals((Integer)SCALE,column.getScale());
	}
	
	/**
	 * Test setLabel
	 */
	@Test
	public void testSetLabel(){
		column.setLabel(LABEL);  
		assertEquals(LABEL,column.label); 
	}
	
	/**
	 * Test getLabel
	 */
	@Test
	public void testGetLabel(){
		column.label = LABEL;  
		assertEquals(LABEL,column.getLabel()); 
	}
	
	
	/**
	 * Test setRemarks
	 */
	@Test
	public void testSetRemarks(){
		column.setRemarks(REAMARKS);  
		assertEquals(REAMARKS,column.remarks); 
	} 
	
	/**
	 * Test getRemarks
	 */
	@Test
	public void testGetRemarks(){
		column.remarks = REAMARKS;  
		assertEquals(REAMARKS,column.getRemarks()); 
	}
}



