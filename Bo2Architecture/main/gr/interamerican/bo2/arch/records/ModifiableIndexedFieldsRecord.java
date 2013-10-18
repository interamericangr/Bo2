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
package gr.interamerican.bo2.arch.records;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;



/**
 * A record that can have its values changed.
 * 
 * The record offers setter methods for modification of its field's values.
 * 
 * @param <T> 
 * 
 */
public interface ModifiableIndexedFieldsRecord<T> 
extends IndexedFieldsRecord<T> {
	
	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */	
	public void setObject(T field, Object value);
	
	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */
	public void setBytes(T field, byte[] value);	

		
	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */	
	public void setDouble(T field, double value);

	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */	
	public void setInt(T field, int value);
	
	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */	
	public void setLong(T field, long value);
	
	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */
	public void setBoolean(T field, boolean value);
	
	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */	
	public void setDate(T field, Date value);
	
	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */
	public void setCalendar(T field, Calendar value);
	
	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */
	public void setBigDecimal(T field, BigDecimal value);
	
	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */	
	public void setString(T field, String value);
	
	
	
	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */	
	public void setShort(T field, short value);
	
	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */	
	public void setFloat(T field, float value);
	
	/**
	 * Sets the value of a field.
	 * 
	 * @param field index of the field to set.
	 * @param value New value for the field.
	 */	
	public void setByte(T field, byte value);

}
