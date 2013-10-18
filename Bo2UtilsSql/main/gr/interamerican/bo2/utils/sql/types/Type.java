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
package gr.interamerican.bo2.utils.sql.types;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Describes the type of a column.
 * 
 * @param <T> 
 *        Type of column.
 */
public interface Type<T> {
	
	/**
	 * Gets the specified column from the current row of the specified result set.
	 * 
	 * @param rs
	 *        Result set.
	 * @param columnIndex
	 *        Column to get.
	 *        
	 * @return Returns the value of the specified column.
	 * 
	 * @throws SQLException 
	 */
	public T get(ResultSet rs, String columnIndex)
	throws SQLException;
	
	/**
	 * Gets the specified column from the current row of the specified result set.
	 * 
	 * @param rs
	 *        Result set.
	 * @param columnIndex
	 *        Column to get.
	 * @param returnNullValues
	 *        If the SQL value is NULL, null is returned
	 *        
	 * @return Returns the value of the specified column.
	 * 
	 * @throws SQLException 
	 */
	public T get(ResultSet rs, String columnIndex, boolean returnNullValues)
	throws SQLException;
	
	/**
	 * Gets the specified column from the current row of the specified result set.
	 * 
	 * @param rs
	 *        Result set.
	 * @param columnIndex
	 *        Column to get.
	 *        
	 * @return Returns the value of the specified column.
	 * 
	 * @throws SQLException 
	 */
	public T get(ResultSet rs, int columnIndex)
	throws SQLException;
	
	/**
	 * Gets the specified column from the current row of the specified result set.
	 * 
	 * @param rs
	 *        Result set.
	 * @param columnIndex
	 *        Column to get.
	 * @param returnNullValues
	 *        If the SQL value is NULL, null is returned
	 *        
	 * @return Returns the value of the specified column.
	 * 
	 * @throws SQLException 
	 */
	public T get(ResultSet rs, int columnIndex, boolean returnNullValues)
	throws SQLException;
	
	/**
	 * Creates the SQL string for the specified object.
	 * 
	 * @param t
	 *        Value to present as an SQL literal.
	 *        
	 * @return Returns a string literal that represents the specified value
	 *         in the SQL dialect.
	 */
	public String sqlString(T t);
	

	/**
	 * Gets the value that must be set as parameter to a statement.
	 * 
	 * @param t
	 *        Value to put as parameter.
	 *        
	 * @return Returns the object that must be set as parameter to a prepared 
	 *         statement.
	 */
	public Object statementParameter(T t);
	
	/**
	 * Gets the java type.
	 * 
	 * @return Returns the java type.
	 */
	public Class<T> getJavaType();

}

