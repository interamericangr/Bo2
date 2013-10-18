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
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * Unit tests of class {@link Buffer}. 
 * 
 */
@SuppressWarnings("nls")
public class TestBuffer {
	
	/**
	 * name of field F1.
	 */
	private static final String F1 = "F1";
	/**
	 * name of field F2.
	 */
	private static final String F2 = "F2";
	/**
	 * name of field F3.
	 */
	private static final String F3 = "F3";
	/**
	 * name of field F4.
	 */
	private static final String F4 = "F4";
	/**
	 * name of field RF12.
	 */
	private static final String RF12 = "RF12";
	/**
	 * name of field RF2B.
	 */
	private static final String RF2B = "RF2B";
	
	/**
	 * tests successful creation
	 */
	@Test
	public void testCreation() {
		String msg, expected, actual;
		
		SampleBuffer sample = new SampleBuffer();
		actual = sample.getBuffer();
		expected = StringUtils.sameCharacterString(30, ' ');  
		msg = "new record";
		
		assertEquals(msg, expected, actual);
	}
	
	/**
	 * tests setString
	 */
	@Test
	public void testSetString() {
		String msg, expected, actual;
		
		SampleBuffer sample = new SampleBuffer();
		sample.setString(F1, "aaa");		
		actual = sample.getBuffer();
		expected = "aaa                           "; 
		msg = "set F1 aaa";		
		assertEquals(msg, expected, actual);
		
		sample.setString(F2, "bbbbbb");		
		actual = sample.getBuffer();
		expected = "aaa  bbbbb                    "; 
		msg = "set F2 bbbbbb";		
		assertEquals(msg, expected, actual);
		
		sample.setString(F3, "ccccc");		
		actual = sample.getBuffer();
		expected = "aaa  bbbbbccccc               "; 
		msg = "set F3 ccccc";		
		assertEquals(msg, expected, actual);
		
		sample.setString(F4, "ddddd");		
		actual = sample.getBuffer();
		expected = "aaa  bbbbbccccc     ddddd     "; 
		msg = "set F4 ddddd";		
		assertEquals(msg, expected, actual);
		
		sample.setString(RF12, "rrrrrr");		
		actual = sample.getBuffer();
		expected = "rrrrrr bbbccccc     ddddd     "; 
		msg = "set RF12 rrrrrr";
		assertEquals(msg, expected, actual);
		
		sample.setString(RF2B, "s");		
		actual = sample.getBuffer();
		expected = "rrrrrr s  ccccc     ddddd     "; 
		msg = "set RF2B s";		
		assertEquals(msg, expected, actual);
		
	}
	
	/**
	 * tests setField methods
	 */
	@Test
	public void testSetFieldMethods() {
		String msg, expected, actual;
		
		SampleBuffer sample = new SampleBuffer();
		
		try {
			sample.setInt(F1, 12);		
			actual = sample.getBuffer();
			expected = "   12                         "; 
			msg = "set int";		
			assertEquals(msg, expected, actual);
			
			sample.setDouble(F2, 123.5);		
			actual = sample.getBuffer();
			expected = "   12123,5                    "; 
			msg = "set double";
			assertEquals(msg, expected, actual);
			
			sample.setDouble(F2, 123.5);		
			actual = sample.getBuffer();
			expected = "   12123,5                    "; 
			msg = "set double";
			assertEquals(msg, expected, actual);
			
			Date dt = DateUtils.getDate("11/12/2010");
			sample.setDate(F3, dt);		
			actual = sample.getBuffer();
			expected = "   12123,511/12/2010          "; 
			msg = "set date";
			assertEquals(msg, expected, actual);
			
			Calendar cal = new GregorianCalendar();
			cal.setTime(dt);
			sample.setCalendar(F4, cal);
			actual = sample.getBuffer();
			expected = "   12123,511/12/20102010-12-11"; 
			
		} catch (ParseException e) {
			fail(e.toString());
		}
	}
	
	/**
	 * tests FieldNotFount on setter
	 */
	@Test(expected=FieldNotFoundException.class)	
	public void testSetterFieldNotFound() {
		SampleBuffer sample = new SampleBuffer();
		sample.setString("error", "value");
	}
	
	/**
	 * tests FieldNotFount on getter
	 */
	@Test(expected=FieldNotFoundException.class)	
	public void testGetterFieldNotFound() {
		SampleBuffer sample = new SampleBuffer();
		sample.getString("error");
	}
	
	/**
	 * tests getString
	 */
	@Test
	public void testGetString() {
		SampleBuffer sample = new SampleBuffer();
		sample.setBuffer("   12     11/12/20102010-12-11");
				
		String actual = sample.getString(F1);
		String expected = "   12";
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getInt
	 */
	@Test
	public void testGetInt() {
		SampleBuffer sample = new SampleBuffer();
		sample.setBuffer("   33123,5                    ");
				
		Integer actual = sample.getInt(F1);
		Integer expected = 33;
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
		SampleBuffer sample = new SampleBuffer();
		sample.setBuffer("   33123,5                    ");
				
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
		SampleBuffer sample = new SampleBuffer();
		sample.setBuffer("          11/12/20102010-12-11");
				
		try {
			Date actual = sample.getDate(F3);
			Date expected = DateUtils.getDate("11/12/2010");
			assertEquals(expected, actual);
			
			expected = DateUtils.getDate("03/05/2010");
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
		SampleBuffer sample = new SampleBuffer();
		sample.setBuffer("          11/12/20102010-12-11");
				

		Calendar actual = sample.getCalendar(F4);
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
		SampleBuffer sample = new SampleBuffer();
		sample.setBuffer("150"); //$NON-NLS-1$
		
		Long actual = sample.getLong(F1); //$NON-NLS-1$
		Long expected = 150L;
		assertEquals(expected, actual);
		
		expected = 200L;
		sample.setLong(F3, expected); //$NON-NLS-1$
		actual = sample.getLong(F3); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getFloat
	 */
	@Test
	public void testGetFloat() {
		SampleBuffer sample = new SampleBuffer();
		sample.setBuffer("150,5"); //$NON-NLS-1$
		
		Float actual = sample.getFloat(F1); //$NON-NLS-1$
		Float expected = (float) 150.5;
		assertEquals(expected, actual);
		
		expected = (float) 200.5;
		sample.setFloat(F2, expected); //$NON-NLS-1$
		actual = sample.getFloat(F2); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getFloat
	 */
	@Test
	public void testGetShort() {
		SampleBuffer sample = new SampleBuffer();
		sample.setBuffer("3"); //$NON-NLS-1$
		
		Short actual = sample.getShort(F1); //$NON-NLS-1$
		Short expected = 3;
		assertEquals(expected, actual);
		
		expected = 5;
		sample.setShort(F2, expected); //$NON-NLS-1$
		actual = sample.getShort(F2); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getBigDecimal
	 */
	@Test
	public void testGetBigDecimal() {
		SampleBuffer sample = new SampleBuffer();
		sample.setBuffer("10"); //$NON-NLS-1$
		
		BigDecimal actual = sample.getBigDecimal(F1); //$NON-NLS-1$
		BigDecimal expected = new BigDecimal(10);
		assertEquals(expected, actual);
		
		expected = new BigDecimal(15);
		sample.setBigDecimal(F2, expected); //$NON-NLS-1$
		actual = sample.getBigDecimal(F2); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getBoolean
	 */
	@Test
	public void testGetBoolean() {
		SampleBuffer sample = new SampleBuffer();
		sample.setBuffer("true"); //$NON-NLS-1$
		
		Boolean actual = sample.getBoolean(F1); //$NON-NLS-1$
		Boolean expected = true;
		assertEquals(expected, actual);
		
		expected = false;
		sample.setBoolean(F2, expected); //$NON-NLS-1$
		actual = sample.getBoolean(F2); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	
	/**
	 * tests getByte
	 */
	@Test
	public void testGetByte() {	
		SampleBuffer sample = new SampleBuffer();
		byte expected = 13;
		sample.setByte(F1, expected); //$NON-NLS-1$
		byte actual = sample.getByte(F1); //$NON-NLS-1$
		assertEquals(expected, actual);
	}
	
	/**
	 * tests getFields
	 */
	@Test
	public void testGetFields() {
		SampleBuffer sample = new SampleBuffer();
		Integer actual = sample.getFields().size();
		assertEquals((Integer)6,actual);
	}
	
	/**
	 * tests toString
	 */
	@Test
	public void testToString() {	
		SampleBuffer sample = new SampleBuffer();		
		assertEquals(sample.getBuffer(), sample.toString());
	}

	
	/**
	 * Sample implementation class of {@link Buffer} for use by the 
	 * unit tests.
	 */
	private static class SampleBuffer 
	extends Buffer {
		
		/*
		 * ----- ----- ---------- ----------
		 * F1    F2    F3         F4
		 * ------- --- 
		 * RF12    RF2B
		 *   
		 */
		
		/**
		 * fields
		 */
		private static String[] fields = {F1, F2, F3, F4, RF12, RF2B};
		
		/**
		 * lengths
		 */
		private static int[] lengths = {5, 5, 10, 10, 7, 3};
		
		/**
		 * positions
		 */
		private static int[] positions = {0, 5, 10, 20, 0, 7};
		
		/**
		 * Specification.
		 */
		private static final BufferSpec SPEC = new BufferSpec(fields, lengths, positions);
	
		/**
		 * Creates a new SampleBuffer object
		 */
		SampleBuffer() {
			super(SPEC);		
		}
	}	
	
}
