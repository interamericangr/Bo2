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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date {@link Type}.
 */
public class DateType 
extends AbstractType<Date> {
	
	/**
	 * Instance.
	 */
	public static final DateType INSTANCE = new DateType();	
	
	/**
	 * Creates a new DateType object. 
	 *
	 */
	private DateType() {
		super();		
	}
	
	/**
	 * Date format.
	 */
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	
	public Date get(ResultSet rs, String columnIndex) throws SQLException {		
		return rs.getDate(columnIndex);
	}
	
	public Date get(ResultSet rs, int columnIndex) throws SQLException {		
		return rs.getDate(columnIndex);
	}	
	
	public synchronized String sqlString(Date t) {
		if (t==null) {
			return StringConstants.NULL;
		}
		String s = df.format(t);
		return StringUtils.quotes(s);
	}
	
	@Override
	public Object statementParameter(Date t) {
		if (t!=null) {			
			return new java.sql.Date(t.getTime());
		}
		return null;
	}
	
	public Class<Date> getJavaType() {
		return Date.class;
	}

}
