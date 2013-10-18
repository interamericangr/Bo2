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
package gr.interamerican.bo2.arch.def;

import gr.interamerican.bo2.arch.exceptions.DataAccessException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;



/**
 * Container of fields that have a predefined order.
 * 
 * The fields can be accessed using the integer value 
 * of their order.
 * 
 */
public interface OrderedFieldsContainer {
   
    /**
     * Gets the string value of the specified field.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */
    public String getString(int field) throws DataAccessException;
    
    /**
     * Gets the BigDecimal value of the specified field.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */
    public BigDecimal getBigDecimal(int field) throws DataAccessException;
    
    
    /**
     * Gets the double value of the specified field.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */
    public double getDouble(int field) throws DataAccessException;
 
    
    /**
     * Gets the float value of the specified field.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */
	public float getFloat(int field) throws DataAccessException;


    /**
     * Gets the integer value of the specified field.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */
	public int getInt(int field) throws DataAccessException; 


    /**
     * Gets the long value of the specified field.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */
	public long getLong(int field) throws DataAccessException; 

    /**
     * Gets the short value of the specified field.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */
	public short getShort(int field) throws DataAccessException; 

    /**
     * Gets the boolean value of the specified field.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */	
	public boolean getBoolean(int field) throws DataAccessException;


    /**
     * Gets the byte value of the specified field.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */	
	public byte getByte(int field) throws DataAccessException;


    /**
     * Gets the value of the specified field as an array of bytes.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */
	public byte[] getBytes(int field) throws DataAccessException;
	
    /**
     * Gets the date value of the specified field.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */
    public Date getDate(int field) throws DataAccessException;
    
    /**
     * Gets the calendar value of the specified field.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */
    public Calendar getCalendar(int field) throws DataAccessException;
    
    /**
     * Gets an object value of the specified field.
     * 
     * @param field Order of accessed field.
     * @return returns the value of the field with the specified name.
     * @throws DataAccessException
     */
	public Object getObject(int field) throws DataAccessException;
    

}
