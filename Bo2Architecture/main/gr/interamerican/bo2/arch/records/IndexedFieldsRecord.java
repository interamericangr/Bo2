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
package gr.interamerican.bo2.arch.records;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link Record} that is also a container of indexed fields.
 * 
 * @param <T> 
 *        Type of index.
 */
public interface IndexedFieldsRecord<T> 
extends Record {
	
	/**
	 * Names of the record's fields.
	 * 
	 * @return Return a list with the objects used to index the fields.
	 */
	public List<T> getFields();
	
	/**
	 * Gets a field's value as an Object Byte.
	 * 
	 * @param field the field index
	 * @return Returns the value of the field with the specified
	 *         name of this record as an Object.
	 */
	public Object getObject(T field);

	
	/**
	 * Gets a field's value as a String.
	 * 
	 * @param field the field index
	 * @return Returns the value of the field with the specified
	 *         name of this record as an array of bytes.
	 */
	public byte[] getBytes(T field);	

		
	/**
	 * Gets a field's value as a Double.
	 * 
	 * @param field the field index
	 * @return Returns the value of the field with the specified
	 *         name of this record as a Double.
	 */
	public double getDouble(T field);
	
	
	/**
	 * Gets a field's value as an int.
	 * 
	 * @param field the field index
	 * @return Returns the value of the field with the specified
	 *         name of this record as a int.
	 */
	public int getInt(T field);
	
	/**
	 * Gets a field's value as a long.
	 * 
	 * @param field the field index
	 * @return Returns the value of the field with the specified
	 *         name of this record as a long.
	 */
	public long getLong(T field);
	
	
	/**
	 * Gets a field's value as a boolean.
	 * 
	 * @param field the field index
	 * @return Returns the value of the field with the specified
	 *         name of this record as a boolean.
	 */
	public boolean getBoolean(T field);
	
	
	/**
	 * Gets a field's value as a date.
	 * 
	 * @param field the field index
	 * @return Returns the value of the field with the specified
	 *         name of this record as a date.
	 */
	public Date getDate(T field);
	
	/**
	 * Gets a field's value as a calendar.
	 * 
	 * @param field the field index
	 * @return Returns the value of the field with the specified
	 *         name of this record as a calendar.
	 */
	public Calendar getCalendar(T field);
	
	/**
	 * Gets a field's value as a BigDecimal.
	 * 
	 * @param field the field index
	 * @return Returns the value of the field with the specified
	 *         name of this record as a BigDecimal.
	 */
	public BigDecimal getBigDecimal(T field);
	
	
	/**
	 * Gets a field's value as a String
	 * @param field the field index
	 * @return the contents of the buffer part that is defined with the field index
	 */
	public String getString(T field);
	
	/**
	 * Gets a field's value as a Short.
	 * 
	 * @param field the field index
	 * @return Returns the value of the field with the specified
	 *         name of this record as a Short.
	 */
	public short getShort(T field);
	
	/**
	 * Gets a field's value as a Float.
	 * 
	 * @param field the field index
	 * @return Returns the value of the field with the specified
	 *         name of this record as a FLoat.
	 */
	public float getFloat(T field);
	
	/**
	 * Gets a field's value as a Byte.
	 * 
	 * @param field the field index
	 * @return Returns the value of the field with the specified
	 *         name of this record as a Byte.
	 */
	public byte getByte(T field);

	


}
