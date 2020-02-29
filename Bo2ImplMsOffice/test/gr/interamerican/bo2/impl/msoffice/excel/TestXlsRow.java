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
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedInputStream;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for {@link XlsQuery}.
 */
public class TestXlsRow {
	
	/**
	 * Setup tests. <br>
	 * 
	 * Open the workbook and initialize the sheet.
	 *
	 * @throws UnexpectedException the unexpected exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@BeforeClass
	public static void setup() throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {
			@SuppressWarnings("nls")
			@Override
			public void work() throws LogicException, DataException, InitializationException, UnexpectedException {
				String manager = "LOCALFS";
				String streamName = "Sample.xls";
				NamedStreamsProvider nsp = getProvider().getResource(manager, NamedStreamsProvider.class);
				NamedInputStream nis = (NamedInputStream) nsp.getStream(streamName);
				InputStream input = nis.getStream();
				Workbook workbook;
				try {
					workbook = WorkbookFactory.create(input);
				} catch (IOException e) {
					throw new DataException(e);
				} catch (EncryptedDocumentException e) {
					throw new DataException(e);
				} catch (InvalidFormatException e) {
					throw new DataException(e);
				}
				sheet = workbook.getSheetAt(0);
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
		Row row0 = sheet.getRow(0);
		Assert.assertNotNull(row0);
		Assert.assertEquals("Column1", row0.getCell(0).getStringCellValue());
		Assert.assertEquals("Column2", row0.getCell(1).getStringCellValue());
		Assert.assertEquals("Column3", row0.getCell(2).getStringCellValue());
		Assert.assertEquals("Column4", row0.getCell(3).getStringCellValue());
	}

	/**
	 * Test for getString().
	 */
	@Test
	public void testGetString_doesNotReturnNullOnEmptyCell() {
		XlsRow row1 = returnXlsRow(sheet.getRow(1));
		Assert.assertNotNull(row1.getString(2));
	}

	/**
	 * Return xls row.
	 *
	 * @param row the row
	 * @return {@link XlsRow}.
	 */
	private XlsRow returnXlsRow(Row row) {
		List<Cell> cells = new ArrayList<Cell>();
		for (int colNum = row.getFirstCellNum(); colNum < row.getLastCellNum(); colNum++) {
			Cell cell = row.getCell(colNum);
			cells.add(cell);
		}
		return new XlsRow(CollectionUtils.toArray(cells, new Cell[cells.size()]));
	}

	/**
	 * Test for getString().
	 */
	@Test
	public void testGetString_withEmptyCell() {
		XlsRow row2 = returnXlsRow(sheet.getRow(1));
		Assert.assertEquals(StringConstants.EMPTY, row2.getString(2));
	}

	/**
	 * Test for getString().
	 */
	@Test
	public void testGetString_doesNotReturnNullOnEmptyCellAfterTheLastColumn() {
		XlsRow row2 = returnXlsRow(sheet.getRow(2));
		Assert.assertNotNull(row2.getString(5));
		Assert.assertNotNull(row2.getString(10));
	}

	/**
	 * Test for getDouble().
	 */
	@Test
	public void testGetDouble() {
		XlsRow row4 = returnXlsRow(sheet.getRow(4));
		Assert.assertEquals(Double.valueOf(15.0), new Double(row4.getDouble(1)));
		Assert.assertEquals(Double.valueOf(15.241), Double.valueOf(row4.getDouble(2)));
	}

	/**
	 * Test for getFloat().
	 */
	@Test
	public void testGetFloat() {
		XlsRow row4 = returnXlsRow(sheet.getRow(4));
		Assert.assertEquals(Float.valueOf(15.0f), Float.valueOf(row4.getFloat(1)));
		Assert.assertEquals(Float.valueOf(15.241f), Float.valueOf(row4.getFloat(2)));
	}

	/**
	 * Test for getBigDecimal().
	 */
	@Test
	public void testGetBigDecimal() {
		XlsRow row4 = returnXlsRow(sheet.getRow(4));
		Assert.assertEquals(new BigDecimal(15.241).setScale(3, RoundingMode.HALF_EVEN), row4.getBigDecimal(2));
	}

	/**
	 * Test for getLong().
	 */
	@Test
	public void testGetLong() {
		XlsRow row4 = returnXlsRow(sheet.getRow(4));
		Assert.assertEquals(15L, row4.getLong(1));
	}

	/**
	 * Test for getInt().
	 */
	@Test
	public void testGetInt() {
		XlsRow row4 = returnXlsRow(sheet.getRow(4));
		Assert.assertEquals(15, row4.getInt(1));
	}

	/**
	 * Test for getShort().
	 */
	@Test
	public void testGetShort() {
		XlsRow row4 = returnXlsRow(sheet.getRow(4));
		Assert.assertEquals(15, row4.getShort(1));
	}

	/**
	 * Test for getShort().
	 */
	@Test
	public void testGetBoolean() {
		XlsRow row3 = returnXlsRow(sheet.getRow(3));
		Assert.assertEquals(false, row3.getBoolean(4));
	}

	/**
	 * Test for getDate().
	 */
	@Test
	public void testGetDate() {
		XlsRow row4 = returnXlsRow(sheet.getRow(4));
		Date dt = row4.getDate(3);
		Assert.assertEquals(MAY13, dt);
	}

	/**
	 * Test for getCalendar().
	 *
	 * @throws DataAccessException the data access exception
	 */
	@Test
	public void testGetCalendar() throws DataAccessException {
		XlsRow row4 = returnXlsRow(sheet.getRow(4));
		Calendar cal = row4.getCalendar(3);
		Assert.assertEquals(MAY13, cal.getTime());
	}

	/**
	 * Test for getObject().
	 *
	 * @throws DataAccessException the data access exception
	 */
	@Test
	public void testGetObject() throws DataAccessException {
		XlsRow row4 = returnXlsRow(sheet.getRow(4));
		Assert.assertEquals(row4.getString(0), row4.getObject(0));
	}

	/**
	 * Test for getBytes().
	 *
	 * @throws DataAccessException the data access exception
	 */
	@Test
	public void testGetBytes() throws DataAccessException {
		XlsRow row4 = returnXlsRow(sheet.getRow(4));
		String string = row4.getString(0);
		byte[] expected = string.getBytes();
		byte[] actual = row4.getBytes(0);
		Assert.assertArrayEquals(expected, actual);
	}
}
