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
import gr.interamerican.bo2.samples.sql.MockResultSet;
import gr.interamerican.bo2.utils.StringUtils;

import java.sql.SQLException;

import org.junit.Test;

/**
 * 
 */
public class TestBytesType {

	
	/**
	 * BytesType
	 */
	BytesType bytesType = BytesType.INSTANCE;
	
	/**
	 * MockResultSet
	 */
	MockResultSet rs = new MockResultSet();
	
	
	/**
	 * Tests get when column index is string
	 * @throws SQLException
	 */
	@Test
	public void testGet_string() throws SQLException{
		Integer expected = rs.getBytes("columnIndex").length; //$NON-NLS-1$
		Integer actual = bytesType.get(rs,"columnIndex").length; //$NON-NLS-1$
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests get when column index is integer
	 * @throws SQLException
	 */
	@Test
	public void testGet_integer() throws SQLException{
		Integer expected = rs.getBytes(0).length; 
		Integer actual = bytesType.get(rs,0).length; 
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests testSqlString when value is null
	 */
	@Test
	public void testSqlString_nullValue(){
		String expected = "null"; //$NON-NLS-1$
		String actual = bytesType.sqlString(null); 
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests testSqlString when value is null
	 */
	@Test
	public void testSqlString(){
		byte [] array = new byte[10];
		String expected = StringUtils.quotes(new String(array));
		String actual = bytesType.sqlString(array); 
		assertEquals(expected,actual);
	}
}
