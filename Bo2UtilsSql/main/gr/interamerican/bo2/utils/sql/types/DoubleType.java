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
 * Double {@link Type}.
 */
public class DoubleType 
extends NonQuotedType<Double> {
	
	/**
	 * Instance.
	 */
	public static final DoubleType INSTANCE = new DoubleType();	
	
	/**
	 * Creates a new DoubleType object. 
	 *
	 */
	private DoubleType() {
		super();		
	}
	
	public Double get(ResultSet rs, String columnIndex) throws SQLException {		
		return rs.getDouble(columnIndex);
	}
	
	public Double get(ResultSet rs, int columnIndex) throws SQLException {		
		return rs.getDouble(columnIndex);
	}
	
	public Class<Double> getJavaType() {
		return Double.class;
	}	
	

}
