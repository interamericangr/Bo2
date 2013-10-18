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
import static org.junit.Assert.assertNotNull;
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
 * Unit tests for {@link StringIndexedCsvRecord}.
 */
public class TestStringIndexedCsvRecord {
	
 
	/**
	 * number of Columns
	 */
	private static final int COLUMNS = 14;
	
	
	/**
	 * cvsRecord to test
	 */
	private CsvRecord rec = new CsvRecord(COLUMNS);
	
	/**
	 * StringIndexedCsvRecord to test
	 */
	private StringIndexedCsvRecord indexedRecord = new StringIndexedCsvRecord(rec);
	
	/**
	 * tests successful creation
	 */	
	@Test
	public void testCreation() {
		String expected, actual;
		indexedRecord.setBuffer("test;;;"); //$NON-NLS-1$
		actual = indexedRecord.getBuffer();
		expected = "test;;;;;;;;;;;;;";   //$NON-NLS-1$	
		assertEquals(expected, actual);
	}
	
	
	/**
	 * Test ColumnName
	 */
	@Test
	public void testColumnName(){
		assertEquals("Column1", StringIndexedCsvRecord.columnName(1)); //$NON-NLS-1$
	}
	/**
	 * Test ColumnName
	 */
	@Test
	public void testGetFields(){
		indexedRecord.getFields();
	}
	
	
	/**
	 * tests getString
	 */
	@Test
	public void testGetString() {
		indexedRecord.setBuffer("word"); //$NON-NLS-1$
		String actual = indexedRecord.getString("0"); //$NON-NLS-1$
		String expected = "word"; //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getInt
	 */
	@Test
	public void testGetInt() {
		indexedRecord.setBuffer("154;;;;"); //$NON-NLS-1$
		Integer actual = indexedRecord.getInt("0"); //$NON-NLS-1$
		Integer expected = 154;
		assertEquals(expected, actual);
		
		expected = 1587;
		indexedRecord.setInt("1", expected); //$NON-NLS-1$
		actual = indexedRecord.getInt("1"); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getDouble
	 */
	@Test
	public void testGetDouble() {
		indexedRecord.setBuffer("33;123,5;;;"); //$NON-NLS-1$
		
		Double actual = indexedRecord.getDouble("0"); //$NON-NLS-1$
		Double expected = 33.0;
		assertEquals(expected, actual, 0.0);
		
		actual = indexedRecord.getDouble("1"); //$NON-NLS-1$
		expected = 123.5;
		assertEquals(expected, actual, 0.0);
		
		expected = 587.457;
		indexedRecord.setDouble("2", expected); //$NON-NLS-1$
		actual = indexedRecord.getDouble("2"); //$NON-NLS-1$
		assertEquals(expected, actual, 0.0);
	}
	
	/**
	 * tests getDate
	 */
	@Test
	public void testGetDate() {
		indexedRecord.setBuffer("11/12/2010;;;;"); //$NON-NLS-1$
				
		try {
			Date actual = indexedRecord.getDate("0"); //$NON-NLS-1$
			Date expected = DateUtils.getDate("11/12/2010"); //$NON-NLS-1$
			assertEquals(expected, actual);
			
			expected = DateUtils.getDate("03/05/2010"); //$NON-NLS-1$
			indexedRecord.setDate("1", expected); //$NON-NLS-1$
			actual = indexedRecord.getDate("1"); //$NON-NLS-1$
		} catch (ParseException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * tests getCalendar
	 */
	@Test
	public void testGetCalendar() {
		indexedRecord.setBuffer("2010-12-11-00:00:00.000;;;;"); //$NON-NLS-1$

		Calendar actual = indexedRecord.getCalendar("0"); //$NON-NLS-1$
		Calendar expected = new GregorianCalendar(2010, Calendar.DECEMBER, 11);  
		assertEquals(expected, actual);
		
		expected = new GregorianCalendar(2010, Calendar.FEBRUARY, 15);
		indexedRecord.setCalendar("1", expected); //$NON-NLS-1$
		actual = indexedRecord.getCalendar("1"); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getLong
	 */
	@Test
	public void testGetLong() {
		indexedRecord.setBuffer("150;;;"); //$NON-NLS-1$
		
		Long actual = indexedRecord.getLong("0"); //$NON-NLS-1$
		Long expected = 150L;
		assertEquals(expected, actual);
		
		expected = 200L;
		indexedRecord.setLong("1", expected); //$NON-NLS-1$
		actual = indexedRecord.getLong("1"); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getFloat
	 */
	@Test
	public void testGetFloat() {
		indexedRecord.setBuffer("150,5;;;"); //$NON-NLS-1$
		
		Float actual = indexedRecord.getFloat("0"); //$NON-NLS-1$
		Float expected = (float) 150.5;
		assertEquals(expected, actual);
		
		expected = (float) 200.5;
		indexedRecord.setFloat("1", expected); //$NON-NLS-1$
		actual = indexedRecord.getFloat("1"); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getFloat
	 */
	@Test
	public void testGetShort() {
		indexedRecord.setBuffer("3;;;"); //$NON-NLS-1$
		
		Short actual = indexedRecord.getShort("0"); //$NON-NLS-1$
		Short expected = 3;
		assertEquals(expected, actual);
		
		expected = 5;
		indexedRecord.setShort("1", expected); //$NON-NLS-1$
		actual = indexedRecord.getShort("1"); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getBigDecimal
	 */
	@Test
	public void testGetBigDecimal() {
		indexedRecord.setBuffer("10;;;"); //$NON-NLS-1$
		
		BigDecimal actual = indexedRecord.getBigDecimal("0"); //$NON-NLS-1$
		BigDecimal expected = new BigDecimal(10);
		assertEquals(expected, actual);
		
		expected = new BigDecimal(15);
		indexedRecord.setBigDecimal("1", expected); //$NON-NLS-1$
		actual = indexedRecord.getBigDecimal("1"); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getBoolean
	 */
	@Test
	public void testGetBoolean() {
		indexedRecord.setBuffer("true;;;"); //$NON-NLS-1$
		
		Boolean actual = indexedRecord.getBoolean("0"); //$NON-NLS-1$
		Boolean expected = true;
		assertEquals(expected, actual);
		
		expected = false;
		indexedRecord.setBoolean("1", expected); //$NON-NLS-1$
		actual = indexedRecord.getBoolean("1"); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getObject
	 */
	@Test
	public void testGetObject() {		
		Address expected = new Address();
		indexedRecord.setObject("0", expected); //$NON-NLS-1$
		String actual = indexedRecord.getObject("0").toString(); //$NON-NLS-1$
		assertEquals(expected.toString(), actual);
	}
	
	/**
	 * tests getByte
	 */
	@Test
	public void testGetByte() {		
		byte expected = 13;
		indexedRecord.setByte("0", expected); //$NON-NLS-1$
		byte actual = indexedRecord.getByte("0"); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	
	/**
	 * tests getBytes
	 */
	@Test
	public void testGetBytes() {		
		byte[] expected = new byte[10];
		indexedRecord.setBytes("0", expected); //$NON-NLS-1$
		byte [] actual = indexedRecord.getBytes("0"); //$NON-NLS-1$
		assertEquals(expected.length, actual.length);
	}
	
	/**
	 * tests getRec
	 */
	@Test
	public void testGetRec() {		
		assertNotNull(indexedRecord.getRec());
	}
	
}
