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

import gr.interamerican.bo2.arch.def.OrderedFieldsContainer;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

/**
 * Row of an Excel file.
 * 
 * Legal field indices of this {@link OrderedFieldsContainer} have values 0 - {@link #size()}-1
 */
public class XlsRow implements OrderedFieldsContainer {

	/**
	 * Xls file row.
	 */
	private Cell[] row;

	/** The fmt. */
	private DataFormatter fmt = new DataFormatter();

	/**
	 * Creates a new XlsRow object.
	 * 
	 * @param row
	 *            Cells of the row.
	 */
	public XlsRow(Cell[] row) {
		super();
		this.row = row;
	}

	/**
	 * Gets the null safe numeric contents of a cell.
	 * 
	 * @param columnIndex
	 *            Cell index.
	 * 
	 * @return Returns the contents of the cell.
	 */
	private String nullSafeNumericContent(int columnIndex) {
		if (columnIndex >= row.length) {
			return StringConstants.ZERO;
		}
		return !StringUtils.isNullOrBlank(formatCell(columnIndex)) ? formatCell(columnIndex) : StringConstants.ZERO;
	}

	/**
	 * Format cell.
	 *
	 * @param columnIndex
	 *            the column index
	 * @return formatted cell.
	 */
	private String formatCell(int columnIndex) {
		Cell cell = row[columnIndex];
		return fmt.formatCellValue(cell);
	}

	/**
	 * Gets the null safe numeric contents of a cell.
	 * 
	 * @param columnIndex
	 *            Cell index.
	 * 
	 * @return Returns the contents of the cell.
	 */
	private String nullSafeContent(int columnIndex) {
		if (columnIndex >= row.length) {
			return StringConstants.EMPTY;
		}
		return Utils.notNull(formatCell(columnIndex), StringConstants.EMPTY);
	}

	@Override
	public String getString(int columnIndex) {
		return nullSafeContent(columnIndex);
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) {
		String contents = nullSafeNumericContent(columnIndex);
		return NumberUtils.string2BigDecimal(contents);
	}

	@Override
	public double getDouble(int columnIndex) {
		String contents = nullSafeNumericContent(columnIndex);
		return NumberUtils.string2Double(contents);
	}

	@Override
	public float getFloat(int columnIndex) {
		String contents = nullSafeNumericContent(columnIndex);
		Double d = NumberUtils.string2Double(contents);
		return d.floatValue();
	}

	@Override
	public int getInt(int columnIndex) {
		String contents = nullSafeNumericContent(columnIndex);
		return Integer.parseInt(contents);
	}

	@Override
	public long getLong(int columnIndex) {
		String contents = nullSafeNumericContent(columnIndex);
		return Long.parseLong(contents);
	}

	@Override
	public short getShort(int columnIndex) {
		String contents = nullSafeNumericContent(columnIndex);
		Integer i = NumberUtils.string2Int(contents);
		return i.shortValue();
	}

	@Override
	public boolean getBoolean(int columnIndex) {
		String contents = nullSafeBooleanContent(columnIndex);
		return StringUtils.string2Bool(contents);
	}

	/**
	 * Gets the null safe numeric contents of a cell.
	 * 
	 * @param columnIndex
	 *            Cell index.
	 * 
	 * @return Returns the contents of the cell.
	 */
	private String nullSafeBooleanContent(int columnIndex) {
		if (columnIndex >= row.length) {
			return StringConstants.EMPTY;
		}
		String value = formatCell(columnIndex);
		return !StringUtils.isNullOrBlank(value) ? value : StringConstants.EMPTY;
	}

	@Override
	public Date getDate(int columnIndex) {
		if (columnIndex >= row.length) {
			return null;
		}
		Date dt = null;
		Cell cell = row[columnIndex];
		try {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				dt = cell.getDateCellValue();
			} else {
				dt = DateUtils.getDate(cell.getStringCellValue());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (dt != null) {
			DateUtils.removeTime(dt);
		}
		return dt;
	}

	@Override
	public byte[] getBytes(int columnIndex) throws DataAccessException {
		String contents = nullSafeContent(columnIndex);
		return contents.getBytes();
	}

	@Override
	public Calendar getCalendar(int columnIndex) throws DataAccessException {
		Date dt = getDate(columnIndex);
		if (dt == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal;
	}

	@Override
	public Object getObject(int columnIndex) throws DataAccessException {
		return getString(columnIndex);
	}

	@Override
	public byte getByte(int field) throws DataAccessException {
		return 0;
	}

	/**
	 * Size.
	 *
	 * @return Returns the size of this {@link OrderedFieldsContainer}
	 */
	public int size() {
		return row.length;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size(); i++) {
			sb.append(getString(i));
			if (i != size() - 1) {
				sb.append(StringConstants.PIPE);
			}
		}
		return StringUtils.squareBrackets(sb.toString());
	}
}