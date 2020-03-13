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
/*
 * Created on 15 Íïå 2005
 *
 */
package gr.interamerican.bo2.impl.open.records;


import gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * CsvRecord is a record of a comma separated values (CSV) file. <br>
 * 
 * The default separator is semicolon, but it is possible to change
 * the separator to any character. <br>
 */
public class StringIndexedCsvRecord extends AbstractBaseRecord
implements ModifiableIndexedFieldsRecord<String>  {
	
	/**
	 * Name of column.
	 */
	private static final String COL = "Column"; //$NON-NLS-1$
	
	/**
	 * Internal Csv record.
	 */
	private CsvRecord rec;
	

	
	/**
	 * field indexes.
	 */
	private List<String> fieldIndexes;
	
	/**
	 * Gets the name of a column.
	 * @param index Column index.
	 * @return Returns the string that is used as a String index
	 *         for this column.
	 */
	public static String columnName(Integer index) {
		return COL+index;
	}
	
	
	/**
	 * Creates a new StringIndexedCsvRecord.
	 * 
	 * @param rec {@link CsvRecord} of this object.
	 */
	public StringIndexedCsvRecord(CsvRecord rec) {
		this.rec = rec;
		List<String> fieldnames = new ArrayList<String>();
		for (Integer col : rec.getFields()) {
			fieldnames.add(COL+col);
		}
		fieldIndexes = Collections.unmodifiableList(fieldnames);
	}	

	
	/**
	 * Gets the index of the column.
	 *
	 * @param field the field
	 * @return Returns a column name.
	 */
	private Integer fieldIndex(String field) {
		String s = field.replace(COL, "").trim(); //$NON-NLS-1$
		try {
			return Integer.parseInt(s);
			
		} catch (NumberFormatException e) {
			throw new FieldNotFoundException(field);
		}
	}

	@Override
	public List<String> getFields() {
		return fieldIndexes;
	}

	@Override
	public String getString(String field) {
		return rec.getString(fieldIndex(field));
	}

	@Override
	public void setString(String field, String value) {
		rec.setString(fieldIndex(field), value);
	}

	@Override
	public byte[] getBytes(String field) {
		return getString(field).getBytes(charset());
	}

	@Override
	public void setBytes(String field, byte[] value) {
		setString(field, new String(value, charset()));
	}

	@Override
	public boolean getBoolean(String field) {
		String val = getString(field);
		return StringUtils.string2Bool(val);
	}

	@Override
	public void setBoolean(String field, boolean value) {
		String val = StringUtils.bool2String(value);
		setString(field, val);
	}

	@Override
	public int getInt(String field) {
		String val = getString(field);
		return NumberUtils.string2Int(val);
	}

	@Override
	public void setInt(String field, int value) {
		String val = Integer.toString(value);
		setString(field, val);
	}

	@Override
	public long getLong(String field) {
		String val = getString(field);
		return NumberUtils.string2Long(val);
	}

	@Override
	public void setLong(String field, long value) {
		String val = Long.toString(value);
		setString(field, val);
	}

	@Override
	public double getDouble(String field) {
		String val = getString(field);
		return NumberUtils.string2Double(val);
	}

	@Override
	public void setDouble(String field, double value) {
		String val = NumberUtils.format(value);
		setString(field, val);
	}

	@Override
	public Date getDate(String field) {
		String val = getString(field);
		try {
			return DateUtils.getDate(val);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setDate(String field, Date value) {
		String val = DateUtils.formatDate(value);
		setString(field, val);
	}

	@Override
	public Calendar getCalendar(String field) {
		Date dt = getDate(field);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal;
	}

	@Override
	public void setCalendar(String field, Calendar value) {
		String val = DateUtils.formatCalendar(value);
		setString(field, val);
	}

	@Override
	public BigDecimal getBigDecimal(String field) {
		String val = getString(field);
		return NumberUtils.string2BigDecimal(val);
	}

	@Override
	public void setBigDecimal(String field, BigDecimal value) {
		String val = NumberUtils.format(value);
		setString(field, val);
	}

	@Override
	public short getShort(String field) {
		return new Integer(getInt(field)).shortValue();
	}

	@Override
	public void setShort(String field, short value) {
		setInt(field, value);
	}

	@Override
	public float getFloat(String field) {
		return new Double(getDouble(field)).floatValue();
	}

	@Override
	public void setFloat(String field, float value) {
		setDouble(field, value);
	}

	@Override
	public byte getByte(String field) {
		byte[] bytes = getBytes(field);
		if (bytes == null || bytes.length == 0) {
			return 0;
		}
		return bytes[0];
	}

	@Override
	public void setByte(String field, byte value) {
		byte[] bytes = { value };
		setBytes(field, bytes);
	}

	@Override
	public Object getObject(String field) {
		return getString(field);
	}

	@Override
	public void setObject(String field, Object value) {
		String v = value == null ? "" : value.toString(); //$NON-NLS-1$
		setString(field, v);
	}

	@Override
	public byte[] getBytes() {
		return rec.getBytes();
	}

	@Override
	public String getBuffer() {
		return rec.getBuffer();
	}

	@Override
	public void setBytes(byte[] arg) {
		rec.setBytes(arg);
	}

	@Override
	public void setBuffer(String arg) {
		rec.setBuffer(arg);
	}

	/**
	 * Gets the underlying {@link CsvRecord}.
	 * 
	 * @return Returns the underlying {@link CsvRecord}.
	 */
	public CsvRecord getRec() {
		return rec;
	}
}