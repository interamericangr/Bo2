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
 * CsvRecord is a record of a comma separated values (CSV) file. <br/>
 * 
 * The default separator is semicolon, but it is possible to change
 * the separator to any character. <br/>
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
	 * @param field
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
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getFields()
	 */	
	public List<String> getFields() {		
		return fieldIndexes;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getString(java.lang.Object)
	 */
	public String getString(String field) {
		return rec.getString(fieldIndex(field));
	}

	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setString(java.lang.Object, java.lang.String)
	 */
	public void setString(String field, String value) {
		rec.setString(fieldIndex(field), value);			
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getBytes(java.lang.Object)
	 */
	public byte[] getBytes(String field) {		
		return getString(field).getBytes(charset());		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setBytes(java.lang.Object, byte[])
	 */
	public void setBytes(String field, byte[] value) {
		setString(field, new String(value, charset()));
	}

	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getBoolean(java.lang.Object)
	 */
	public boolean getBoolean(String field) {
		String val = getString(field);
		return StringUtils.string2Bool(val);
	}

	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setBoolean(java.lang.Object, boolean)
	 */
	public void setBoolean(String field, boolean value) {
		String val = StringUtils.bool2String(value);
		setString(field, val);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getInt(java.lang.Object)
	 */
	public int getInt(String field) {		
		String val = getString(field);		
		return NumberUtils.string2Int(val);
	}

	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setInt(java.lang.Object, int)
	 */
	public void setInt(String field, int value) {
		String val = Integer.toString(value);
		setString(field, val);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getLong(java.lang.Object)
	 */
	public long getLong(String field) {
		String val = getString(field);
		return NumberUtils.string2Long(val);
	}

	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setLong(java.lang.Object, long)
	 */
	public void setLong(String field, long value) {
		String val = Long.toString(value);
		setString(field, val);		
	}

	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getDouble(java.lang.Object)
	 */
	public double getDouble(String field) {
		String val = getString(field);
		return NumberUtils.string2Double(val);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setDouble(java.lang.Object, double)
	 */
	public void setDouble(String field, double value) {		
		String val = NumberUtils.format(value);
		setString(field, val);			
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getDate(java.lang.Object)
	 */
	public Date getDate(String field) {
		String val = getString(field);
		try {
			return DateUtils.getDate(val);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setDate(java.lang.Object, java.util.Date)
	 */
	public void setDate(String field, Date value) {
		String val = DateUtils.formatDate(value);
		setString(field, val);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getCalendar(java.lang.Object)
	 */
	public Calendar getCalendar(String field) {
		Date dt = getDate(field);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal;
	}

	/*
	 * 	(non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setCalendar(java.lang.Object, java.util.Calendar)
	 */
	public void setCalendar(String field, Calendar value) {
		String val = DateUtils.formatCalendar(value);
		setString(field, val);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getBigDecimal(java.lang.Object)
	 */
	public BigDecimal getBigDecimal(String field) {
		String val = getString(field);
		return NumberUtils.string2BigDecimal(val);
	}	

	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setBigDecimal(java.lang.Object, java.math.BigDecimal)
	 */
	public void setBigDecimal(String field, BigDecimal value) {
		String val = NumberUtils.format(value);
		setString(field, val);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getShort(java.lang.Object)
	 */	
	public short getShort(String field) {		
		return new Integer(getInt(field)).shortValue();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setShort(java.lang.Object, short)
	 */		
	public void setShort(String field, short value) {
		setInt(field, value);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getFloat(java.lang.Object)
	 */
	public float getFloat(String field) {		
		return new Double(getDouble(field)).floatValue();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setFloat(java.lang.Object, float)
	 */
	public void setFloat(String field, float value) {		
		setDouble(field, value);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getByte(java.lang.Object)
	 */
	public byte getByte(String field) {		
		byte[] bytes =  getBytes(field);
		if (bytes==null || bytes.length==0) {
			return 0;
		}
		return bytes[0];
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setByte(java.lang.Object, byte)
	 */
	public void setByte(String field, byte value) {
		byte[] bytes = {value};
		setBytes(field, bytes);
	}
	
	/*
	 * 	(non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.IndexedFieldsRecord#getObject(java.lang.Object)
	 */
	public Object getObject(String field) {		
		return getString(field);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord#setObject(java.lang.Object, java.lang.Object)
	 */
	public void setObject(String field, Object value) {
		String v = value == null ? "" : value.toString(); //$NON-NLS-1$
		setString(field, v);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.Record#getBytes()
	 */
	public byte[] getBytes() {	
		return rec.getBytes();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.Record#getBuffer()
	 */
	public String getBuffer() {		
		return rec.getBuffer();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.Record#setBytes(byte[])
	 */
	public void setBytes(byte[] arg) {
		rec.setBytes(arg);	
	}	
	
	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.records.Record#setBuffer(java.lang.String)
	 */
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
