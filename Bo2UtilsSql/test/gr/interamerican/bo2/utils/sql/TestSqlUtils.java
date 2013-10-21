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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.sql.elements.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
public class TestSqlUtils {

	
	/**
	 * Tests toString when value is null
	 */
	@Test
	public void testToString_nullValue(){
		String expected = "null"; //$NON-NLS-1$
		String actual = SqlUtils.toString(null);
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests toString 
	 */
	@Test
	public void testToString(){
		Integer value = 10;
		String expected = value.toString();
		String actual = SqlUtils.toString(value);
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests toQuotedString when value is null
	 */
	@Test
	public void testToQuotedString_nullValue(){
		String expected = "null"; //$NON-NLS-1$
		String actual = SqlUtils.toQuotedString(null);
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests toQuotedString 
	 */
	@Test
	public void testToQuotedString(){
		Integer value = 10;
		String expected = StringUtils.quotes(value.toString());
		String actual = SqlUtils.toQuotedString(value);
		assertEquals(expected,actual);
	}
	
	
	/**
	 * Tests sqlString an integer value
	 */
	@Test
	public void testSqlString_Integer(){
		Integer value = 10;
		String expected = "10"; //$NON-NLS-1$
		String actual = SqlUtils.sqlString(value);
		assertEquals(expected,actual);
	}
	
	
	/**
	 * Tests sqlString a date value
	 */
	@Test
	public void testSqlString_Date(){
		Date dt = DateUtils.getDate(2011, Calendar.APRIL, 6);
		String expected = "'2011-04-06'"; //$NON-NLS-1$
		String actual = SqlUtils.sqlString(dt);
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests statementParameter a date value
	 */
	@Test
	public void testStatementParameter_integerValue(){
		Integer value = 10;
		Integer expected = value;
		Object actual = SqlUtils.statementParameter(value);
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests statementParameter a date value
	 */
	@Test
	public void testStatementParameter_dateValue(){
		Date dt = new Date();
		Date expected = new java.sql.Date(dt.getTime());
		Object actual = SqlUtils.statementParameter(dt);
		assertEquals(expected,actual);	
	}
	
	/**
	 * Tests statementParameter a null value
	 */
	@Test
	public void testStatementParameter_nullValue(){	
		assertNull(SqlUtils.statementParameter(null));	
	}
	
	/**
	 * Unit test for getParameterNames(sql)
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetParameterNames() {				
		String sql = "select a,b,c from DD join FF on DD.a=FF.a where DD.a = :parm1 or FF.c=:parm2";
		List<String> params = SqlUtils.getParameterNames(sql);
		assertEquals(2, params.size());		
		assertEquals("parm1", params.get(0));
		assertEquals("parm2", params.get(1));
	}
	
	/**
	 * Unit test for getParameterNames(sql)
	 */
	@SuppressWarnings("nls")
	@Test
	public void testNamesToParametersList() {	
		String[] strings = {"name", "minAge", "maxAge"};
		List<String> paramNames = Arrays.asList(strings);	
		List<Parameter> paramsList = SqlUtils.namesToParametersList(paramNames);
		assertEquals(3, paramsList.size());
		
		Parameter[] params = paramsList.toArray(new Parameter[0]);
		for (int i = 0; i < params.length; i++) {
			assertNotNull(params[i]);
			assertEquals(params[i].getName(), strings[i]);
		}
	}
	
	/**
	 * Unit test for getParameterNames(sql)
	 */
	@SuppressWarnings("nls")
	@Test
	public void testReplaceParametersWithMarkers() {				
		String sql = "select a,b,c from DD join FF on DD.a=FF.a where DD.a = :parm1 or FF.c = :parm2";
		String actual = SqlUtils.replaceParametersWithMarkers(sql);
		String expected = "select a,b,c from DD join FF on DD.a=FF.a where DD.a = ? or FF.c = ?";
		assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for {@link SqlUtils#createInFromCollection(java.util.Collection, String, boolean, boolean)} 
	 */
	@Test
	public void testCreateInFromCollection(){
		List<Object> l=null;
		Assert.assertEquals(StringConstants.EMPTY, SqlUtils.createInFromCollection(l, StringConstants.EMPTY, false, false));
		l=new ArrayList<Object>();
		l.add(StringConstants.ZERO);
		Assert.assertEquals(StringConstants.EMPTY, SqlUtils.createInFromCollection(l, null, false, false));
		Assert.assertEquals(StringConstants.EMPTY, SqlUtils.createInFromCollection(l, StringConstants.EMPTY, false, false));
		Assert.assertEquals(" col in (0)", SqlUtils.createInFromCollection(l, "col", false, false));		 //$NON-NLS-1$ //$NON-NLS-2$
		l.add(StringConstants.ZERO);
		Assert.assertEquals(" col in (0,0)", SqlUtils.createInFromCollection(l, "col", false, false));		 //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals(" col not in (0,0)", SqlUtils.createInFromCollection(l, "col", true, false));		 //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals(" and col not in (0,0)", SqlUtils.createInFromCollection(l, "col", true, true));		 //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	
}
