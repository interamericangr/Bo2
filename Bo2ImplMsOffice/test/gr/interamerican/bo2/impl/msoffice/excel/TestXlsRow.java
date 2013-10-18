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
package gr.interamerican.bo2.impl.msoffice.excel;

import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedInputStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for {@link XlsQuery}.
 */
public class TestXlsRow {
	
	/**
	 * Setup tests. <br/>
	 * 
	 * Open the workbook and initialize the sheet.
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@BeforeClass
	public static void setup() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@SuppressWarnings("nls")
			@Override
			public void work() throws LogicException, DataException,
			InitializationException, UnexpectedException {
				String manager = "LOCALFS";
				String streamName = "Sample.xls";
				NamedStreamsProvider nsp = getProvider().getResource(manager, NamedStreamsProvider.class);
				NamedInputStream nis = (NamedInputStream) nsp.getStream(streamName);
				InputStream input = nis.getStream();
				
				Workbook workbook;
				try {
					workbook = Workbook.getWorkbook(input);
				} catch (BiffException e) {
					throw new DataException(e);
				} catch (IOException e) {
					throw new DataException(e);
				}
				sheet = workbook.getSheet(0);
			}
		}.execute();
	}
	
	/**
	 * Sheet for the tests.
	 */
	private static Sheet sheet;
	
	/**
	 * Date used in the tests.
	 */
	private static final Date MAY13 = DateUtils.getDate(2011, Calendar.MAY, 13);
	
	
	
	/**
	 * Test for getString().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetString() {
		Cell[] row0Cells = sheet.getRow(0);
		Assert.assertNotNull(row0Cells);
		XlsRow row0 = new XlsRow(row0Cells);
		Assert.assertEquals("Column1", row0.getString(0));
		Assert.assertEquals("Column2", row0.getString(1));
		Assert.assertEquals("Column3", row0.getString(2));
		Assert.assertEquals("Column4", row0.getString(3));
	}
	
	/**
	 * Test for getString().
	 */
	@Test
	public void testGetString_doesNotReturnNullOnEmptyCell() {		
		Cell[] row1Cells = sheet.getRow(1);
		XlsRow row1 = new XlsRow(row1Cells);
		Assert.assertNotNull(row1.getString(1));
	}
	
	/**
	 * Test for getString().
	 */
	@Test
	public void testGetString_withEmptyCell() {		
		Cell[] row1Cells = sheet.getRow(1);
		XlsRow row1 = new XlsRow(row1Cells);
		Assert.assertEquals(StringConstants.EMPTY, row1.getString(2));
	}
	
	/**
	 * Test for getString().
	 */
	@Test
	public void testGetString_doesNotReturnNullOnEmptyCellAfterTheLastColumn() {
		Cell[] row2Cells = sheet.getRow(2);
		XlsRow row2 = new XlsRow(row2Cells);
		Assert.assertNotNull(row2.getString(5));
		Assert.assertNotNull(row2.getString(10));		
	}
	
	
	
	
	/**
	 * Test for getDouble().
	 */
	@Test
	public void testGetDouble() {
		Cell[] row4Cells = sheet.getRow(4);
		XlsRow row4 = new XlsRow(row4Cells);
		Assert.assertEquals(Double.valueOf(0.0), Double.valueOf(row4.getDouble(0)));
		Assert.assertEquals(Double.valueOf(15.0), Double.valueOf(row4.getDouble(1)));
		Assert.assertEquals(Double.valueOf(15.241), Double.valueOf(row4.getDouble(2)));
	}
	
	/**
	 * Test for getFloat().
	 */
	@Test
	public void testGetFloat() {
		Cell[] row4Cells = sheet.getRow(4);
		XlsRow row4 = new XlsRow(row4Cells);
		Assert.assertEquals(Float.valueOf(0.0f), Float.valueOf(row4.getFloat(0)));
		Assert.assertEquals(Float.valueOf(15.0f), Float.valueOf(row4.getFloat(1)));
		Assert.assertEquals(Float.valueOf(15.241f), Float.valueOf(row4.getFloat(2)));
	}
	
	/**
	 * Test for getBigDecimal().
	 */
	@Test
	public void testGetBigDecimal() {
		Cell[] row4Cells = sheet.getRow(4);
		XlsRow row4 = new XlsRow(row4Cells);
		Assert.assertEquals(new BigDecimal(15.241).setScale(3,RoundingMode.HALF_EVEN), row4.getBigDecimal(2));
	}
	
	/**
	 * Test for getLong().
	 */
	@Test
	public void testGetLong() {
		Cell[] row4Cells = sheet.getRow(4);
		XlsRow row4 = new XlsRow(row4Cells);
		Assert.assertEquals(15L, row4.getLong(1));
	}
	
	/**
	 * Test for getInt().
	 */
	@Test
	public void testGetInt() {
		Cell[] row4Cells = sheet.getRow(4);
		XlsRow row4 = new XlsRow(row4Cells);
		Assert.assertEquals(15, row4.getInt(1));
	}
	
	/**
	 * Test for getShort().
	 */
	@Test
	public void testGetShort() {
		Cell[] row4Cells = sheet.getRow(4);
		XlsRow row4 = new XlsRow(row4Cells);
		Assert.assertEquals(15, row4.getShort(1));
	}
	
	/**
	 * Test for getDate(). 
	 */
	@Test
	public void testGetDate()  {
		Cell[] row4Cells = sheet.getRow(4);
		XlsRow row4 = new XlsRow(row4Cells);
		Date dt = row4.getDate(3);
		Assert.assertEquals(MAY13, dt);
	}
	
	/**
	 * Test for getCalendar().
	 * @throws DataAccessException 
	 */
	@Test
	public void testGetCalendar() throws DataAccessException {
		Cell[] row4Cells = sheet.getRow(4);
		XlsRow row4 = new XlsRow(row4Cells);
		Calendar cal = row4.getCalendar(3);		
		Assert.assertEquals(MAY13, cal.getTime()); 
	}
	
	/**
	 * Test for getObject().
	 * @throws DataAccessException 
	 */
	@Test
	public void testGetObject() throws DataAccessException {
		Cell[] row4Cells = sheet.getRow(4);
		XlsRow row4 = new XlsRow(row4Cells);
		Assert.assertEquals(row4.getString(0), row4.getObject(0));
	}
	
	/**
	 * Test for getByte().
	 * @throws DataAccessException 
	 */
	@Test
	public void testGetByte() throws DataAccessException {
		Cell[] row4Cells = sheet.getRow(4);
		XlsRow row4 = new XlsRow(row4Cells);
		Assert.assertEquals(0, row4.getByte(0));
	}
	
	/**
	 * Test for getBytes().
	 * @throws DataAccessException 
	 */
	@Test
	public void testGetBytes() throws DataAccessException {
		Cell[] row4Cells = sheet.getRow(4);
		XlsRow row4 = new XlsRow(row4Cells);
		String string = row4.getString(0);
		byte[] expected = string.getBytes();
		byte[] actual = row4.getBytes(0);
		Assert.assertArrayEquals(expected, actual);
	}

	


}
