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
 * Integer {@link Type}.
 */
public class IntegerType 
extends NonQuotedType<Integer> {

	/**
	 * Instance.
	 */
	public static final IntegerType INSTANCE = new IntegerType();
	
	/**
	 * Creates a new IntegerType object. 
	 *
	 */
	private IntegerType() {
		super();		
	}
	
	public Integer get(ResultSet rs, String columnIndex) throws SQLException {		
		return rs.getInt(columnIndex);
	}
	
	public Integer get(ResultSet rs, int columnIndex) throws SQLException {		
		return rs.getInt(columnIndex);
	}
	
	public Class<Integer> getJavaType() {
		return Integer.class;
	}	

}
