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
import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * CsvRecord is a record of a comma separated values (CSV) file. <br/>
 * 
 * The default separator is semicolon, but it is possible to change
 * the separator to any character. <br/>
 * 
 * {@link CsvRecord}s are zero based.
 */
public class CsvRecord 
implements ModifiableIndexedFieldsRecord<Integer>  {
	
	/**
	 * default column value.
	 */
	private static final String DEFAULT = ""; //$NON-NLS-1$
	
	/**
	 * default separator.
	 */
	public static final char DEFAULT_SEPARATOR = ';';

	/**
	 * buffer
	 */
	private String[] fields;
	
	/**
	 * columns separator
	 */
	private char separator = DEFAULT_SEPARATOR;
	
	/**
	 * field indexes.
	 */
	private List<Integer> fieldIndexes;
	
	/**
	 * Creates a new CsvRecord object. The supplied array is defensively
	 * copied to an internal array.
	 *  
	 * @param records
	 *        Records. 
	 */
	public CsvRecord(String[] records) {
		this(records.length);
		for (int i = 0; i < records.length; i++) {
			setString(i, records[i]);
		}
	}
	
	/**
	 * Creates a new CsvRecord object. The supplied array is defensively
	 * copied to an internal array.
	 * 
	 * @param records 
	 *        Records.
	 * @param separator
	 *        Separator.
	 */
	public CsvRecord(String[] records, char separator) {
		this(records);
		this.separator = separator;
	} 
	
	/**
	 * Creates a new CsvRecord.
	 * 
	 * @param columnCount count of columns of the CSV record.
	 */
	public CsvRecord(int columnCount) {
		Integer[] indexes = new Integer[columnCount];		
		fields = new String[columnCount];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = DEFAULT;
			indexes[i] = i;
		}
		List<Integer> list = Arrays.asList(indexes);
		fieldIndexes = Collections.unmodifiableList(list);
	}
	
	/**
	 * Creates a new CsvRecord object. 
	 * 
	 * @param columnCount 
	 *        Count of columns.
	 * @param separator
	 *        Separator.
	 */
	public CsvRecord(int columnCount, char separator) {
		this(columnCount);
		this.separator = separator;
	} 

	/**
	 * Checks if this field exists.
	 * 
	 * @param field
	 */
	private void checkField(Integer field) {
		if (field>=fields.length) {
			throw new FieldNotFoundException(field.toString());
		}
	}		
	
	public List<Integer> getFields() {		
		return fieldIndexes;
	}

	public String getString(Integer field) {
		checkField(field);
		return fields[field];		
	}

	public void setString(Integer field, String value) {
		checkField(field);
		fields[field]=value;			
	}

	public byte[] getBytes(Integer field) {		
		return getString(field).getBytes();		
	}

	public void setBytes(Integer field, byte[] value) {
		setString(field, new String(value));
	}

	public boolean getBoolean(Integer field) {
		String val = getString(field);
		return StringUtils.string2Bool(val);
	}

	public void setBoolean(Integer field, boolean value) {
		String val = StringUtils.bool2String(value);
		setString(field, val);		
	}

	public int getInt(Integer field) {		
		String val = getString(field);		
		return NumberUtils.string2Int(val);
	}

	public void setInt(Integer field, int value) {
		String val = Integer.toString(value);
		setString(field, val);		
	}	
	
	public long getLong(Integer field) {
		String val = getString(field);
		return NumberUtils.string2Long(val);
	}

	public void setLong(Integer field, long value) {
		String val = Long.toString(value);
		setString(field, val);		
	}

	public double getDouble(Integer field) {
		String val = getString(field);
		return NumberUtils.string2Double(val);
	}

	public void setDouble(Integer field, double value) {		
		String val = NumberUtils.format(value);
		setString(field, val);			
	}

	public Date getDate(Integer field) {
		String val = getString(field);
		try {
			return DateUtils.getDate(val);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public void setDate(Integer field, Date value) {
		String val = DateUtils.formatDate(value);
		setString(field, val);
	}

	public Calendar getCalendar(Integer field) {
		Date dt = getDate(field);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal;
	}

	public void setCalendar(Integer field, Calendar value) {
		String val = DateUtils.formatCalendar(value);
		setString(field, val);		
	}

	public BigDecimal getBigDecimal(Integer field) {
		String val = getString(field);
		return NumberUtils.string2BigDecimal(val);
	}	

	public void setBigDecimal(Integer field, BigDecimal value) {
		String val = NumberUtils.format(value);
		setString(field, val);		
	}

	public short getShort(Integer field) {		
		return new Integer(getInt(field)).shortValue();
	}
	
	public void setShort(Integer field, short value) {
		setInt(field, value);
	}

	public float getFloat(Integer field) {		
		return new Double(getDouble(field)).floatValue();
	}

	public void setFloat(Integer field, float value) {		
		setDouble(field, value);
	}	

	public byte getByte(Integer field) {		
		byte[] bytes =  getBytes(field);
		if (bytes==null || bytes.length==0) {
			return 0;
		}
		return bytes[0];
	}

	public void setByte(Integer field, byte value) {
		byte[] bytes = {value};
		setBytes(field, bytes);
	}

	public Object getObject(Integer field) {		
		return getString(field);
	}

	public void setObject(Integer field, Object value) {
		String v = value == null ? "" : value.toString(); //$NON-NLS-1$
		setString(field, v);
	}

	public byte[] getBytes() {	
		return getBuffer().getBytes();
	}

	public String getBuffer() {		
		return StringUtils.array2String(fields, Character.toString(separator));
	}

	public void setBytes(byte[] arg) {
		setBuffer(new String(arg));	
	}	
	
	public void setBuffer(String arg) {
		String[] newFields = TokenUtils.splitTrim(arg, separator);
		setFields(newFields);
	}
	
	/**
	 * Sets the fields of this {@link CsvRecord}.
	 * 
	 * This method will never change this record's count of fields.
	 * If this record has more fields than the specified array, elements,
	 * then the remaining fields will be filled with blanks. If this
	 * record has less fields than the specified array's length, then
	 * the remaining elements of the array will be ignored.
	 * 
	 * @param newFields
	 *        Array with new fields.
	 */
	public void setFields(String[] newFields) {		
		for (int i = 0; i < fields.length; i++) {
			if (i<newFields.length) {
				fields[i]=newFields[i];
			} else {
				fields[i]=DEFAULT;
			}			
		}
	}
	
	/**
	 * Sets all fields of the specified array to be the fields of this 
	 * {@link CsvRecord}.
	 * 
	 * If the length of the specified array, exceeds the count of this 
	 * record's fields, then this record will increase its fields so that
	 * all elements of the specified array fit as fields of this record.
	 * 
	 * @param newFields
	 *        Array with new fields.
	 */
	public void setFieldsIgnoreNone(String[] newFields) {
		this.fields = ArrayUtils.ensureCapacity(this.fields, newFields.length);		
		for (int i = 0; i < fields.length; i++) {
			if (i<newFields.length) {
				fields[i]=newFields[i];
			} else {
				fields[i]=DEFAULT;
			}			
		}
	}
	
	@Override
	public String toString() {
		return getBuffer();
	}

}
