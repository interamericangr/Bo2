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
 * Long {@link Type}.
 */
public class LongType 
extends NonQuotedType<Long> {	
	
	/**
	 * Instance.
	 */
	public static final LongType INSTANCE = new LongType();
	
	/**
	 * Creates a new LongType object. 
	 *
	 */
	private LongType() {
		super();
	}
	
	
	public Long get(ResultSet rs, String columnIndex) throws SQLException {		
		return rs.getLong(columnIndex);
	}
	
	public Long get(ResultSet rs, int columnIndex) throws SQLException {		
		return rs.getLong(columnIndex);
	}
	
	public Class<Long> getJavaType() {
		return Long.class;
	}

}
