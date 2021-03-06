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
 * Created on 15 ��� 2005
 *
 */
package gr.interamerican.bo2.impl.open.records;


import gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Buffer is a fixed length byte array with named fields of fixed length. <br>
 * 
 * The most frequent use of a buffer is to be printed as a record
 * in a fixed columns file. <br>
 * Buffer corresponds to data structures of other programming languages.
 * The c language equivalent is <code>struct {...}</code>.
 * The basic language equivalent is <code>type</code>. 
 * The cobol language equivalent is a record described by a copybook.
 */
public class Buffer extends AbstractBaseRecord
implements ModifiableIndexedFieldsRecord<String> {
	
	/**
	 * default byte for byte array initialization.
	 */
	private static final byte DEFAULT = (byte)' ';
		
	/**
	 * Record specification structure.
	 * 
	 * There is only on BufferSpec for each sub-type of Buffer. The
	 * specification of each sub-type is created by the constructor and gets
	 * stored in the <code>subtypeSpecifications</code>
	 * 
	 */
	private BufferSpec spec;

	/** buffer. */
	private char[] buffer;

	/**
	 * Positions to put a delimiter in order to transform the record to CSV.
	 */
	private Set<Integer> delimiterPositions;

	/**
	 * Creates a new Buffer object.
	 * 
	 * @param spec
	 *            Buffer specification.
	 */
	public Buffer(BufferSpec spec) {
		this.spec = spec;
		this.buffer = new char[spec.getBufferLength()];
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = DEFAULT;
		}
		delimiterPositions = spec.getFieldPositions();
		delimiterPositions.remove(0);
	}

	@Override
	public byte[] getBytes(String field) {
		char[] chars = getChars(field);
		return toByteArray(chars);
	}

	@Override
	public void setBytes(String field, byte[] value) {
		char[] chars = toCharArray(value);
		setChars(field, chars);
	}

	@Override
	public String getString(String field) {
		int pos = spec.getPosition(field);
		int len = spec.getLength(field);
		return new String(buffer, pos, len);
	}

	@Override
	public void setString(String field, String value) {
		setChars(field, value.toCharArray());
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
		int len = spec.getLength(field);
		String val = Integer.toString(value);
		val = StringUtils.fixedLengthPadRight(val, len);
		setString(field, val);
	}

	@Override
	public long getLong(String field) {
		String val = getString(field);
		return NumberUtils.string2Long(val);
	}

	@Override
	public void setLong(String field, long value) {
		int len = spec.getLength(field);
		String val = Long.toString(value);
		val = StringUtils.fixedLengthPadRight(val, len);
		setString(field, val);
	}

	@Override
	public double getDouble(String field) {
		String val = getString(field);
		return NumberUtils.string2Double(val);
	}

	@Override
	public void setDouble(String field, double value) {
		int len = spec.getLength(field);
		String val = NumberUtils.format(value);
		val = StringUtils.fixedLengthPadRight(val, len);
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
		int len = spec.getLength(field);
		String val = value.toString();
		val = StringUtils.fixedLengthPadRight(val, len);
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
		return toByteArray(buffer);
	}

	@Override
	public String getBuffer() {
		return new String(getBytes(), charset());
	}

	@Override
	public void setBytes(byte[] arg) {
		char[] chars = toCharArray(arg);
		setChars(chars);
	}

	@Override
	public void setBuffer(String arg) {
		setBytes(arg.getBytes(charset()));
	}

	@Override
	public List<String> getFields() {
		return spec.getFieldNames();
	}

	@Override
	public String toString() {
		return getBuffer();
	}

	/**
	 * Sets a String with right justification.
	 * 
	 * @param field
	 *        Field name.
	 * @param value
	 *        Value to set to the field.
	 */
	public void setStringRightJustified(String field, String value) {
		int len = spec.getLength(field);		
		String val = StringUtils.fixedLengthPadRight(value, len);		
		setString(field, val);			
	}
	
	/**
	 * Converts a byte array to char array.
	 *
	 * @param bytes the bytes
	 * @return Returns the char array.
	 */
	char[] toCharArray(byte[] bytes) {
		String s = new String(bytes, charset());
		return s.toCharArray();		
	}
	
	/**
	 * Converts a char array to byte array.
	 *
	 * @param chars the chars
	 * @return Returns the byte array.
	 */
	byte[] toByteArray(char[] chars) {
		String s = new String(chars);
		return s.getBytes(charset());		
	}
	
	/**
	 * Sets the value of the specified field.
	 * 
	 * @param field
	 *        Field name.
	 * @param chars
	 *        Characters to set.
	 */
	void setChars(String field, char[] chars) {		
		int pos = spec.getPosition(field);
		int len = spec.getLength(field);		
		int lengthToCopy = chars.length>len ? len : chars.length;
		System.arraycopy(chars, 0, buffer, pos, lengthToCopy);		
		if (lengthToCopy < len) {			
			for (int i = lengthToCopy; i < len; i++) {
				buffer[pos+i] = ' ';				
			}			
		}
	}
	
	/**
	 * Sets the contents of the buffer.
	 *
	 * @param arg the new chars
	 */
	void setChars(char[] arg) {
		int lengthToCopy = 
			arg.length>buffer.length ? buffer.length : arg.length;
		System.arraycopy(arg, 0, buffer, 0, lengthToCopy);		
		if (lengthToCopy<buffer.length) {			
			for (int i = lengthToCopy; i < buffer.length; i++) {
				buffer[i] = ' ';				
			}			
		}		
	}
	
	/**
	 * Gets the value of the specified field.
	 * 
	 * @param field
	 *        Field name.
	 * 
	 * @return Returns a character array with the value of the
	 *         specified field.
	 */
	char[] getChars(String field) {		
		int pos = spec.getPosition(field);
		int len = spec.getLength(field);
		char[] ret = new char[len];
		System.arraycopy(buffer, pos, ret, 0, len);
		return ret;
	}
	
	/**
	 * Creates a new CSV record with the results of this Buffer.
	 * 
	 * @param delimiter
	 *        Delimiter for the CSV record.
	 * 
	 * @return Returns the buffer converted to a CSV record.
	 */
	public String toCsv(char delimiter) {
		int capacity = buffer.length + delimiterPositions.size();		
		StringBuilder sb = new StringBuilder(capacity);
		sb.append(buffer);
		int i=0;
		for (Integer pos : delimiterPositions) {
			int p = pos + i;			
			sb.insert(p, delimiter);
			i++;
		}
		return sb.toString();
	}
}