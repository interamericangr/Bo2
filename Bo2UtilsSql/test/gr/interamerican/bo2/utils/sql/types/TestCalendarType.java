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

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * 
 */
public class TestCalendarType {

	
	/**
	 * CalendarType
	 */
	CalendarType calendarType = CalendarType.INSTANCE;
	
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
		Timestamp timestamp = rs.getTimestamp("columnIndex"); //$NON-NLS-1$
		Calendar expected = DateUtils.getCalendar(timestamp);
		Calendar actual = calendarType.get(rs,"columnIndex"); //$NON-NLS-1$
		assertEquals(expected,actual);	
	}
	
	/**
	 * Tests get when column index is string
	 * @throws SQLException
	 */
	@Test
	public void testGet_integer() throws SQLException{
		Timestamp timestamp = rs.getTimestamp(0); 
		Calendar expected = DateUtils.getCalendar(timestamp);
		Calendar actual = calendarType.get(rs,0); 
		assertEquals(expected,actual);	
	}
	
	/**
	 * Tests sqlString when value is null
	 */
	@Test
	public void testSqlString_nullValue(){
		String expected = "null"; //$NON-NLS-1$
		String actual = calendarType.sqlString(null);
		assertEquals(expected,actual);	
	}
	
	/**
	 * Tests sqlString when value is null
	 */
	@Test
	public void testSqlString(){
		Calendar calendar = new GregorianCalendar();
		java.util.Date dt = DateUtils.getDate(2011,00,01);
		calendar.setTime(dt);
		String expected = "'2011-01-01-00:00:00.000'"; //$NON-NLS-1$
		String actual = calendarType.sqlString(calendar);
		assertEquals(expected,actual);	
	}
	
	/**
	 * Tests StatementParameter when value is null
	 */
	@Test
	public void testStatementParameter_nullValue(){
		assertNull(calendarType.statementParameter(null));
	}
	
	/**
	 * Tests StatementParameter
	 */
	@Test
	public void testStatementParameter(){
		Calendar c = new GregorianCalendar();
		Date expected = new java.sql.Date(c.getTimeInMillis());
		Object actual = calendarType.statementParameter(c);
		assertEquals(expected,actual);	
	}
	
}
