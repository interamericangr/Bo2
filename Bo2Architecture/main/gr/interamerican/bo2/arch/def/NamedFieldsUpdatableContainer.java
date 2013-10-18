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
 * Container of fiels that can be modified by their names.
 * 
 */
public interface NamedFieldsUpdatableContainer {
	
	/**
	 * Sets the value of a field assigning to it a string.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setString(String field, String value)
	throws DataAccessException;
	
	
	/**
	 * Sets the value of a field assigning to it a big decimal.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setBigDecimal(String field, BigDecimal value)
	throws DataAccessException;
	
	
	/**
	 * Sets the value of a field assigning to it a double.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setDouble(String field, double value)
	throws DataAccessException;
	
	/**
	 * Sets the value of a field assigning to it a float.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setFloat(String field, float value)
	throws DataAccessException;
	
	/**
	 * Sets the value of a field assigning to it a double.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setInt(String field, int value)
	throws DataAccessException;
	
	/**
	 * Sets the value of a field assigning to it a long.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setLong(String field, long value)
	throws DataAccessException;
	
	/**
	 * Sets the value of a field assigning to it a short.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setShort(String field, short value)
	throws DataAccessException;
	
	/**
	 * Sets the value of a field assigning to it a boolean.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setBoolean(String field, boolean value)
	throws DataAccessException;
	
	
	/**
	 * Sets the value of a field assigning to it a byte.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setByte(String field, byte value)
	throws DataAccessException;

	/**
	 * Sets the value of a field assigning to it a byte array.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setBytes(String field, byte[] value)
	throws DataAccessException;
	
	/**
	 * Sets the value of a field assigning to it a date.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setDate(String field, Date value)
	throws DataAccessException;
	
	
	/**
	 * Sets the value of a field assigning to it a calendar.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setCalendar(String field, Calendar value)
	throws DataAccessException;
	
	
	/**
	 * Sets the value of a field assigning to it an object.
	 * 
	 * @param field field name.
	 * @param value value to set to the field.
	 * 
	 * @throws DataAccessException
	 */
	public void setObject(String field, Object value)
	throws DataAccessException;


}
