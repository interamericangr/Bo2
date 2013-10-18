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
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.sql.MockResultSet;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

import org.junit.Test;

/**
 * 
 */
public class TestTimeType {

	
	/**
	 * TimeType
	 */
	TimeType timeType = TimeType.INSTANCE;
	
	
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
		String actual = timeType.sqlString(null);
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests SqlString 
	 */
	@Test
	public void testSqlString(){
		Time t = new Time((2*60*60+2*60+2)*1000);
		String actual = timeType.sqlString(t);
		assertTrue(actual.startsWith("'")); //$NON-NLS-1$
		assertTrue(actual.endsWith(":02:02'")); //$NON-NLS-1$
	}
	
	/**
	 * Tests statementParameter when value is null
	 */
	@Test
	public void testStatementParameter_nullValue(){
		assertNull(timeType.statementParameter(null));
	}
	
	/**
	 * Tests statementParameter a date value
	 */
	@Test
	public void testStatementParameter(){
		Time t = new Time(0);
		Time expected = new Time(t.getTime());
		Object actual = timeType.statementParameter(t);
		assertEquals(expected,actual);	
	}
	
	/**
	 * Tests get when column index is string
	 * @throws SQLException
	 */
	@Test
	public void testGet_string() throws SQLException{
		Date expected = rs.getTime("columnIndex"); //$NON-NLS-1$
		Date actual = timeType.get(rs,"columnIndex"); //$NON-NLS-1$
		assertEquals(expected,actual);
		
	}
	
	/**
	 * Tests get when column index is integer
	 * @throws SQLException
	 */
	@Test
	public void testGet_integer() throws SQLException{
		Date expected = rs.getTime(0); 
		Date actual = timeType.get(rs,0); 
		assertEquals(expected,actual);
		
	}
	
}
