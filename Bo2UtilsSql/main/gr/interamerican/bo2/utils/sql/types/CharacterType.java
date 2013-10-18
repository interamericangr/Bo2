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

import gr.interamerican.bo2.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Character {@link Type}.
 */
public class CharacterType 
extends QuotedType<Character> { 
	
	/**
	 * Instance.
	 */
	public static final CharacterType INSTANCE = new CharacterType();
	
	/**
	 * Creates a new CharacterType object. 
	 *
	 */
	private CharacterType() {
		super();		
	}
	
	public Character get(ResultSet rs, String columnIndex) throws SQLException {		
		return StringUtils.firstChar(rs.getString(columnIndex));		
		
	}
	
	public Character get(ResultSet rs, int columnIndex) throws SQLException {		
		return StringUtils.firstChar(rs.getString(columnIndex));
	}
	
	public Class<Character> getJavaType() {
		return Character.class;
	}	

}
