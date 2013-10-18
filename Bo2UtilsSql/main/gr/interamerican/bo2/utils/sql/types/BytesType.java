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
 * byte[] {@link Type}.
 */
public class BytesType 
extends AbstractType<byte[]> {
	
	/**
	 * Instance.
	 */
	public static final BytesType INSTANCE = new BytesType();
	
	/**
	 * Creates a new BytesType object. 
	 *
	 */
	private BytesType() {
		super();		
	}
	
	public byte[] get(ResultSet rs, String columnIndex) throws SQLException {		
		return rs.getBytes(columnIndex);
	}
	
	public byte[] get(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getBytes(columnIndex);
	}

	public String sqlString(byte[] t) {
		if (t==null) {
			return StringConstants.NULL;
		}
		String s = new String(t);
		return StringUtils.quotes(s);
	}

	public Class<byte[]> getJavaType() {		
		return byte[].class;
	}

}
