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

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Boolean {@link Type}.
 */
public class BooleanType 
extends AbstractType<Boolean> {
	
	/**
	 * Instance.
	 */
	public static final BooleanType INSTANCE = new BooleanType();
	
	/**
	 * Creates a new BooleanType object. 
	 *
	 */
	private BooleanType() {
		super();		
	}
	
	public Boolean get(ResultSet rs, String columnIndex) throws SQLException {		
		return rs.getBoolean(columnIndex);
	}
	
	public Boolean get(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getBoolean(columnIndex);
	}

	public String sqlString(Boolean t) {
		if (t==null) {
			return StringConstants.NULL;
		}
		return StringUtils.bool2String(t);
	}

	public Class<Boolean> getJavaType() {		
		return Boolean.class;
	}

}
