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

import jxl.Cell;
import jxl.DateCell;


/**
 * Row of an Excel file.
 */
public class XlsRow 
implements OrderedFieldsContainer {
	
	/**
	 * Xls file row.
	 */
	private Cell[] row;

	/**
	 * Creates a new XlsRow object. 
	 *
	 * @param row
	 *        Cells of the row.
	 */
	public XlsRow(Cell[] row) {
		super();
		this.row = row;
	}
	
	/**
	 * Gets the null safe numeric contents of a cell.
	 * 
	 * @param columnIndex 
	 *        Cell index.
	 *        
	 * @return Returns the contents of the cell.
	 */
	private String nullSafeNumericContent(int columnIndex) {
		if (columnIndex>=row.length) {
			return StringConstants.ZERO;
		}
		return Utils.notNull(row[columnIndex].getContents(), StringConstants.ZERO);
	}
	
	/**
	 * Gets the null safe numeric contents of a cell.
	 * 
	 * @param columnIndex 
	 *        Cell index.
	 *        
	 * @return Returns the contents of the cell.
	 */
	private String nullSafeContent(int columnIndex) {
		if (columnIndex>=row.length) {
			return StringConstants.EMPTY;
		}
		return Utils.notNull(row[columnIndex].getContents(), StringConstants.EMPTY);
	}

	
	public String getString(int columnIndex) {		
		return nullSafeContent(columnIndex);
	}

	
	public BigDecimal getBigDecimal(int columnIndex) {		
		String contents = nullSafeNumericContent(columnIndex);
		return NumberUtils.string2BigDecimal(contents);
	}
	
	public double getDouble(int columnIndex) {	
		String contents = nullSafeNumericContent(columnIndex);
		return NumberUtils.string2Double(contents);
	}
	
	public float getFloat(int columnIndex) {
		String contents = nullSafeNumericContent(columnIndex);
		Double d = NumberUtils.string2Double(contents); 
		return d.floatValue();
	}
		
	public int getInt(int columnIndex) {
		String contents = nullSafeNumericContent(columnIndex);
		return NumberUtils.string2Int(contents);
	}
		
	public long getLong(int columnIndex) {
		String contents = nullSafeNumericContent(columnIndex);
		return NumberUtils.string2Long(contents);
	}
	
	public short getShort(int columnIndex) {
		
		String contents = nullSafeNumericContent(columnIndex);
		Integer i = NumberUtils.string2Int(contents);
		return i.shortValue();
	}

	public boolean getBoolean(int columnIndex) {
		String contents = nullSafeContent(columnIndex);		
		return StringUtils.string2Bool(contents);
	}

	public Date getDate(int columnIndex)  {
		if (columnIndex>=row.length) {
			return null;
		}
		Cell cell = row[columnIndex];
		if (cell instanceof DateCell) {
			DateCell dr = (DateCell) cell;
			Date dt = dr.getDate();
			DateUtils.removeTime(dt); 
			return dt;
		}		
		String contents = cell.getContents();		
		try {
			return DateUtils.getDate(contents);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public byte getByte(int columnIndex) throws DataAccessException {
		return (byte) getShort(columnIndex);
	}

	
	public byte[] getBytes(int columnIndex) throws DataAccessException {
		String contents = nullSafeContent(columnIndex);		
		return contents.getBytes();
	}

	public Calendar getCalendar(int columnIndex) throws DataAccessException {
		Date dt = getDate(columnIndex);
		if (dt==null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal;
	}
	
	public Object getObject(int columnIndex) throws DataAccessException {		
		return getString(columnIndex);
	}
	

}
