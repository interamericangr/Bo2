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
 * Abstract basic implementation of {@link Type}.
 * 
 * @param <T> 
 * 
 */
public abstract class AbstractType <T> 
implements Type<T> {
	
	public Object statementParameter(T t) {		
		return t;
	}
	
	public T get(ResultSet rs, int columnIndex, boolean returnNullValues) throws SQLException {
		T result = get(rs, columnIndex);
		if(rs.wasNull() && returnNullValues) {
			return null;
		}
		return result;
	}
	
	public T get(ResultSet rs, String columnIndex, boolean returnNullValues) throws SQLException {
		T result = get(rs, columnIndex);
		if(rs.wasNull() && returnNullValues) {
			return null;
		}
		return result;
	}

}
