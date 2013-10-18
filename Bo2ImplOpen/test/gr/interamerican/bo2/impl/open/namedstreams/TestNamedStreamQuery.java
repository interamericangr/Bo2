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
package gr.interamerican.bo2.impl.open.namedstreams;


import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.records.IndexedFieldsRecord;
import gr.interamerican.bo2.impl.open.records.CsvRecord;
import gr.interamerican.bo2.impl.open.records.StringIndexedCsvRecord;
import gr.interamerican.bo2.samples.implopen.po.Address;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * 
 */
public class TestNamedStreamQuery {

	
	/**
	 * NamedStreamQuery to test
	 */
	StreamQuery query = new StreamQuery();

	/**
	 * Number of colums
	 */
	private static final int COLUMNS = 14;
	
	/**
	 * Field1
	 */
	private static final int FIELD1 = 1;
	
	/**
	 * Field2
	 */
	private static final int FIELD2 = 2;
	/**
	 * Field3
	 */
	private static final int FIELD3 = 3;
	
	/**
	 * Field4
	 */
	private static final int FIELD4 = 4;
	
	/**
	 * Field5
	 */
	private static final int FIELD5 = 5;
	/**
	 * Field6
	 */
	private static final int FIELD6 = 6;
	
	/**
	 * Field7
	 */
	private static final int FIELD7 = 7;
	/**
	 * Field8
	 */
	private static final int FIELD8 = 8;
	/**
	 * Field9
	 */
	private static final int FIELD9 = 9;
	/**
	 * Field10
	 */
	private static final int FIELD10 = 10;
	/**
	 * Field11
	 */
	private static final int FIELD11 = 11;
	/**
	 * Field12
	 */
	private static final int FIELD12 = 12;
	/**
	 * Field13
	 */
	private static final int FIELD13 = 13;
	/**
	 * Value for field1
	 */
	private static final String VALUE1 = "value1"; //$NON-NLS-1$
	
	/**
	 * Value for field2
	 */
	private static final Integer VALUE2 = 10; 
	/**
	 * Value for field3
	 */
	private static final Short VALUE3 = 1; 
	/**
	 * Value for field4
	 */
	private static final Double VALUE4 = 5.0;
	/**
	 * Value for field5
	 */
	private static final Long VALUE5 = 6000L;	
	
	/**
	 * Value for field6
	 */
	private static final Boolean VALUE6 = false;
	/**
	 * Value for field7
	 */
	private static final Float VALUE7 = (float)7.0;
	/**
	 * Value for field8
	 */
	private static final Byte VALUE8 = 13;
	/**
	 *  Value for field9
	 */
	private static final BigDecimal VALUE9 = new BigDecimal(13);
	/**
	 *  Value for field10
	 */
	private static final String VALUE10 = "2011-02-18"; //$NON-NLS-1$
	/**
	 *  Value for field11
	 */
	private static final Calendar VALUE11 = Calendar.getInstance();
	
	/**
	 *  Value for field12
	 */
	private static final byte[] VALUE12 = new byte[12]; 
	
	/**
	 * Value for field13
	 */
	private static final Address VALUE13 = new Address();
	
	

	/**
	 * Test getString
	 */
	@Test
	public void testGetString(){
		String actual = query.getString("1"); //$NON-NLS-1$
		assertEquals(VALUE1,actual); 
		}
	
	/**
	 * Test getInt
	 */
	@Test
	public void testGetInt(){
		Integer actual = query.getInt("2"); //$NON-NLS-1$
		assertEquals(VALUE2,actual); 
		}
	
	/**
	 * Test getShort
	 */
	@Test
	public void testGetShort(){
		Short actual = query.getShort("3");//$NON-NLS-1$
		assertEquals(VALUE3,actual); 
		}
	
	/**
	 * Test getDouble
	 */
	@Test
	public void testGetDouble(){
		Double actual = query.getDouble("4"); //$NON-NLS-1$
		assertEquals(VALUE4,actual); 
		}
	
	/**
	 * Test getLong
	 */
	@Test
	public void testGetLong(){
		Long actual = query.getLong("5"); //$NON-NLS-1$
		assertEquals(VALUE5,actual );
		}
	
	/**
	 * Test getLong
	 */
	@Test
	public void testGetBoolean(){
		Boolean actual = query.getBoolean("6"); //$NON-NLS-1$
		assertEquals(VALUE6,actual); 
		}
	
	/**
	 * Test getFloat
	 */
	@Test
	public void testGetFloat(){
		Float actual = query.getFloat("7"); //$NON-NLS-1$
		assertEquals(VALUE7, actual); 
		}
	
	
	/**
	 * Test getByte
	 */
	@Test
	public void testGetByte(){
		Byte actual = query.getByte("8"); //$NON-NLS-1$
		assertEquals(VALUE8,actual); 
		}
	
	/**
	 * Test getBigDecimal
	 */
	@Test
	public void testGetBigDecimal(){
		BigDecimal actual = query.getBigDecimal("9"); //$NON-NLS-1$
		assertEquals(VALUE9, actual);
		}
	
	/**
	 * Test getGetDate
	 */
	@Test
	public void testGetDate(){
		Date actual = query.getDate("10");//$NON-NLS-1$
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
		try {
			assertEquals(df.parse(VALUE10),actual); 
		} catch (ParseException e) {
          //empty
		} 
	}
	
	/**
	 * Test getBigDecimal
	 */
	@Test
	public void testGetCalendar(){
		Calendar actual = query.getCalendar("11"); //$NON-NLS-1$
		assertEquals(VALUE11,actual);
		}
	
	/**
	 * Test getGetBytes
	 */
	@Test
	public void testGetBytes(){
		byte [] actual = query.getBytes("12"); //$NON-NLS-1$
		assertEquals(VALUE12.length,actual.length); 
	}
	
	/**
	 * Test getGetObject
	 */
	@Test
	public void testGetObject(){
		assertEquals(VALUE13.toString(),query.getObject("13")); //$NON-NLS-1$
	}
	
	/**
	 * implementation to test
	 */
	private class StreamQuery extends NamedStreamQuery{

		/**
		 * Creates a new StreamQuery object. 
		 */
		public StreamQuery(){
			emptyRecord();
		}
		
		@Override
		protected IndexedFieldsRecord<String> emptyRecord() {
			CsvRecord record = new CsvRecord(COLUMNS);
			record.setString(FIELD1, VALUE1);
			record.setInt(FIELD2, VALUE2);
			record.setShort(FIELD3,VALUE3);
			record.setDouble(FIELD4,VALUE4);
			record.setLong(FIELD5,VALUE5);
			record.setBoolean(FIELD6,VALUE6);
			record.setFloat(FIELD7,VALUE7);
			record.setByte(FIELD8,VALUE8);
			record.setBigDecimal(FIELD9, VALUE9);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
			try{
			record.setDate(FIELD10, df.parse(VALUE10));
			}
			catch(ParseException e){
				//empty}	
			}
			record.setCalendar(FIELD11, VALUE11);
			record.setBytes(FIELD12, VALUE12);
			record.setObject(FIELD13, VALUE13);
			StringIndexedCsvRecord indexedRecord = new StringIndexedCsvRecord(record);
			return indexedRecord;
		}
		
	}
	
}
