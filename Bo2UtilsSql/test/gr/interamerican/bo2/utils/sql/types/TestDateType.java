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
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.samples.sql.MockResultSet;
import gr.interamerican.bo2.utils.DateUtils;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * 
 */
public class TestDateType {

	
	/**
	 * DateType
	 */
	DateType dateType = DateType.INSTANCE;
	
	
	/**
	 * MockResultSet
	 */
	MockResultSet rs = new MockResultSet();
	
	/**
	 * Tests SqlString when value is null
	 */
	@Test
	public void testSqlString_nullValue(){
		String expected = "null"; //$NON-NLS-1$
		String actual = dateType.sqlString(null);
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests SqlString 
	 */
	@Test
	public void testSqlString(){
		Date dt = DateUtils.getDate(2011, Calendar.APRIL, 6);
		String expected = "'2011-04-06'"; //$NON-NLS-1$
		String actual = dateType.sqlString(dt);
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests statementParameter when value is null
	 */
	@Test
	public void testStatementParameter_nullValue(){
		assertNull(dateType.statementParameter(null));
	}
	
	/**
	 * Tests statementParameter a date value
	 */
	@Test
	public void testStatementParameter(){
		Date dt = new Date();
		Date expected = new java.sql.Date(dt.getTime());
		Object actual = dateType.statementParameter(dt);
		assertEquals(expected,actual);	
	}
	
	/**
	 * Tests get when column index is string
	 * @throws SQLException
	 */
	@Test
	public void testGet_string() throws SQLException{
		Date expected = rs.getDate("columnIndex"); //$NON-NLS-1$
		Date actual = dateType.get(rs,"columnIndex"); //$NON-NLS-1$
		assertEquals(expected,actual);
		
	}
	
	/**
	 * Tests get when column index is integer
	 * @throws SQLException
	 */
	@Test
	public void testGet_integer() throws SQLException{
		Date expected = rs.getDate(0); 
		Date actual = dateType.get(rs,0); 
		assertEquals(expected,actual);
		
	}
	
}
