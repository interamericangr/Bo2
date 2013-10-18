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
package gr.interamerican.bo2.impl.open.records;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gr.interamerican.bo2.samples.implopen.po.Address;
import gr.interamerican.bo2.utils.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
/**
 * Unit tests for {@link CsvRecord}.
 */

public class TestCsvRecord {
	
	/**
	 * Count of columns of typical CsvRecord used in tests.
	 */
	private static final int COLUMN_COUNT=5;
	/**
	 * 1st field
	 */
	private static final int F1 = 0;
	/**
	 * 2nd field
	 */
	private static final int F2 = 1;
	/**
	 * 3nd field
	 */
	private static final int F3 = 2;
	/**
	 * 4th field
	 */
	private static final int F4 = 3;
	/**
	 * 5th field
	 */
	private static final int F5 = 4;
	
	
	/**
	 * tests successful creation
	 */	
	@Test
	public void testCreation() {
		String msg, expected, actual;
		
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		actual = sample.getBuffer();
		expected = ";;;;";   //$NON-NLS-1$
		msg = "new record";		 //$NON-NLS-1$
		assertEquals(msg, expected, actual);
	}
	
	/**
	 * tests setString
	 */
	@Test
	public void testSetString() {
		String msg, expected, actual;
		
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setString(F1, "aaaa");		 //$NON-NLS-1$
		actual = sample.getBuffer();
		expected = "aaaa;;;;";  //$NON-NLS-1$
		msg = "set F1 aaaa";		 //$NON-NLS-1$
		assertEquals(msg, expected, actual);
		
		sample.setString(F2, "bbbbbb");		 //$NON-NLS-1$
		actual = sample.getBuffer();
		expected = "aaaa;bbbbbb;;;";  //$NON-NLS-1$
		msg = "set F2 bbbbbb";		 //$NON-NLS-1$
		assertEquals(msg, expected, actual);
		
		sample.setString(F3, "ccccc");		 //$NON-NLS-1$
		actual = sample.getBuffer();
		expected = "aaaa;bbbbbb;ccccc;;";   //$NON-NLS-1$
		msg = "set F3 ccccc";		 //$NON-NLS-1$
		assertEquals(msg, expected, actual);
		
		sample.setString(F4, "ddddd");		 //$NON-NLS-1$
		actual = sample.getBuffer();
		expected = "aaaa;bbbbbb;ccccc;ddddd;";  //$NON-NLS-1$
		msg = "set F4 ddddd";		 //$NON-NLS-1$
		assertEquals(msg, expected, actual);
		
		sample.setString(F5, "rrrrrr");		 //$NON-NLS-1$
		actual = sample.getBuffer();
		expected = "aaaa;bbbbbb;ccccc;ddddd;rrrrrr";   //$NON-NLS-1$
		msg = "set F5 rrrrrr"; //$NON-NLS-1$
		assertEquals(msg, expected, actual);
		
	}
	
	/**
	 * tests setField methods
	 */
	@Test
	public void testSetFieldMethods() {
		String msg, expected, actual;
		
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		
		try {
			sample.setInt(F1, 12);		
			actual = sample.getBuffer();
			expected = "12;;;;";  //$NON-NLS-1$
			msg = "set int";		 //$NON-NLS-1$
			assertEquals(msg, expected, actual);
			
			sample.setDouble(F2, 123.5);		
			actual = sample.getBuffer();
			expected = "12;123,5;;;"; //$NON-NLS-1$
			msg = "set double"; //$NON-NLS-1$
			assertEquals(msg, expected, actual);
			
			Date dt = DateUtils.getDate("11/12/2010"); //$NON-NLS-1$
			sample.setDate(F3, dt);		
			actual = sample.getBuffer();
			expected = "12;123,5;11/12/2010;;"; //$NON-NLS-1$
			msg = "set date"; //$NON-NLS-1$
			assertEquals(msg, expected, actual);
			
			Calendar cal = new GregorianCalendar();
			cal.setTime(dt);
			sample.setCalendar(F4, cal);
			actual = sample.getBuffer();
			expected = "12;123,5;11/12/2010;2010-12-11-00:00:00.000;";  //$NON-NLS-1$
			
		} catch (ParseException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * tests FieldNotFount on setter
	 */
	@Test(expected=FieldNotFoundException.class)	
	public void testSetterFieldNotFound() {		
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setString(COLUMN_COUNT+1, "value"); //$NON-NLS-1$
	}
	
	/**
	 * tests FieldNotFount on getter
	 */
	@Test(expected=FieldNotFoundException.class)	
	public void testGetterFieldNotFound() {
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.getString(COLUMN_COUNT+1);
	}
	
	/**
	 * tests getString
	 */
	@Test
	public void testGetString() {
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setBuffer("word;;;;");				 //$NON-NLS-1$
		String actual = sample.getString(F1);
		String expected = "word"; //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getInt
	 */
	@Test
	public void testGetInt() {
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setBuffer("154;;;;");				 //$NON-NLS-1$
		Integer actual = sample.getInt(F1);
		Integer expected = 154;
		assertEquals(expected, actual);
		
		expected = 1587;
		sample.setInt(F3, expected);
		actual = sample.getInt(F3);
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getDouble
	 */
	@Test
	public void testGetDouble() {
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setBuffer("33;123,5;;;"); //$NON-NLS-1$
		
		Double actual = sample.getDouble(F1);
		Double expected = 33.0;
		assertEquals(expected, actual, 0.0);
		
		actual = sample.getDouble(F2);
		expected = 123.5;
		assertEquals(expected, actual, 0.0);
		
		expected = 587.457;
		sample.setDouble(F3, expected);
		actual = sample.getDouble(F3);
		assertEquals(expected, actual, 0.0);
	}
	
	/**
	 * tests getDate
	 */
	@Test
	public void testGetDate() {
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setBuffer("11/12/2010;;;;"); //$NON-NLS-1$
				
		try {
			Date actual = sample.getDate(F1);
			Date expected = DateUtils.getDate("11/12/2010"); //$NON-NLS-1$
			assertEquals(expected, actual);
			
			expected = DateUtils.getDate("03/05/2010"); //$NON-NLS-1$
			sample.setDate(F3, expected);
			actual = sample.getDate(F3);
		} catch (ParseException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * tests getCalendar
	 */
	@Test
	public void testGetCalendar() {
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setBuffer("2010-12-11-00:00:00.000;;;;"); //$NON-NLS-1$

		Calendar actual = sample.getCalendar(F1);
		Calendar expected = new GregorianCalendar(2010, Calendar.DECEMBER, 11);  
		assertEquals(expected, actual);
		
		expected = new GregorianCalendar(2010, Calendar.FEBRUARY, 15);
		sample.setCalendar(F3, expected);
		actual = sample.getCalendar(F3);
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getLong
	 */
	@Test
	public void testGetLong() {
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setBuffer("150;;;"); //$NON-NLS-1$
		
		Long actual = sample.getLong(0); 
		Long expected = 150L;
		assertEquals(expected, actual);
		
		expected = 200L;
		sample.setLong(1, expected); 
		actual = sample.getLong(1); 
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getFloat
	 */
	@Test
	public void testGetFloat() {
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setBuffer("150,5;;;"); //$NON-NLS-1$
		
		Float actual = sample.getFloat(0); 
		Float expected = (float) 150.5;
		assertEquals(expected, actual);
		
		expected = (float) 200.5;
		sample.setFloat(1, expected); 
		actual = sample.getFloat(1); 
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getShort
	 */
	@Test
	public void testGetShort() {
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setBuffer("3;;;"); //$NON-NLS-1$
		
		Short actual = sample.getShort(0); 
		Short expected = 3;
		assertEquals(expected, actual);
		
		expected = 5;
		sample.setShort(1, expected); 
		actual = sample.getShort(1); 
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getBigDecimal
	 */
	@Test
	public void testGetBigDecimal() {
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setBuffer("10;;;"); //$NON-NLS-1$
		
		BigDecimal actual = sample.getBigDecimal(0); 
		BigDecimal expected = new BigDecimal(10);
		assertEquals(expected, actual);
		
		expected = new BigDecimal(15);
		sample.setBigDecimal(1, expected); 
		actual = sample.getBigDecimal(1); 
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getBoolean
	 */
	@Test
	public void testGetBoolean() {
	    CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setBuffer("true;;;"); //$NON-NLS-1$
		
		Boolean actual = sample.getBoolean(0); 
		Boolean expected = true;
		assertEquals(expected, actual);
		
		expected = false;
		sample.setBoolean(1, expected); 
		actual = sample.getBoolean(1); 
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getObject
	 */
	@Test
	public void testGetObject() {
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		Address expected = new Address();
		sample.setObject(0, expected); 
		String actual = sample.getObject(0).toString(); 
		assertEquals(expected.toString(), actual);
	}
	
	
	/**
	 * tests getByte
	 */
	@Test
	public void testGetByte() {	
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		byte expected = 13;
		sample.setByte(0, expected); 
		byte actual = sample.getByte(0); 
		assertEquals(expected, actual);
	}	
	
	/**
	 * tests getBytes
	 */
	@Test
	public void testGetBytes() {	
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		byte[] expected = new byte[10];
		sample.setBytes(0, expected); 
		byte [] actual = sample.getBytes(0); 
		assertEquals(expected.length, actual.length);
	}
	
	/**
	 * tests toString
	 */
	@Test
	public void testToString() {	
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);		
		assertEquals(sample.getBuffer(), sample.toString());
	}
	
	/**
	 * test constructor with array.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testCreation_withArray() {
		String[] records = new String[]{"a", null, "c"};
		CsvRecord sample = new CsvRecord(records);
		
		assertEquals(sample.getString(0), records[0]);
		assertEquals(sample.getString(1), records[1]);
		assertEquals(sample.getString(2), records[2]);
		
		assertEquals(sample.getBuffer(), "a;null;c");
	}
	
}
