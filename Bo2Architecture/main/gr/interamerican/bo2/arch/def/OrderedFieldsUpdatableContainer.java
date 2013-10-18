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
package gr.interamerican.bo2.arch.def;

import gr.interamerican.bo2.arch.exceptions.DataAccessException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;



/**
 * Container of fiels that have a predefined order
 * and can be modified using their order.
 * 
 */
public interface OrderedFieldsUpdatableContainer {
	
	/**
	 * Sets the value of a field assigning to it a string.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setString(int field, String value)
	throws DataAccessException;
	
	
	/**
	 * Sets the value of a field assigning to it a big decimal.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setBigDecimal(int field, BigDecimal value)
	throws DataAccessException;
	
	
	/**
	 * Sets the value of a field assigning to it a double.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setDouble(int field, double value)
	throws DataAccessException;
	
	/**
	 * Sets the value of a field assigning to it a float.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setFloat(int field, float value)
	throws DataAccessException;
	
	/**
	 * Sets the value of a field assigning to it a double.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setInt(int field, int value)
	throws DataAccessException;
	
	/**
	 * Sets the value of a field assigning to it a long.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setLong(int field, long value)
	throws DataAccessException;
	
	/**
	 * Sets the value of a field assigning to it a short.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setShort(int field, short value)
	throws DataAccessException;
	
	/**
	 * Sets the value of a field assigning to it a boolean.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setBoolean(int field, boolean value)
	throws DataAccessException;
	
	
	/**
	 * Sets the value of a field assigning to it a byte.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setByte(int field, byte value)
	throws DataAccessException;

	/**
	 * Sets the value of a field assigning to it a byte array.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setBytes(int field, byte[] value)
	throws DataAccessException;
	
	/**
	 * Sets the value of a field assigning to it a date.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setDate(int field, Date value)
	throws DataAccessException;
	
	
	/**
	 * Sets the value of a field assigning to it a calendar.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setCalendar(int field, Calendar value)
	throws DataAccessException;
	
	
	/**
	 * Sets the value of a field assigning to it an object.
	 * 
	 * @param field field order.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setObject(int field, Object value)
	throws DataAccessException;


}
