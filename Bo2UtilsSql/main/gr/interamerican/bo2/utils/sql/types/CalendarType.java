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

import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Calendar {@link Type}.
 */
public class CalendarType 
implements Type<Calendar> { 
	
	/**
	 * Instance.
	 */
	public static final CalendarType INSTANCE = new CalendarType();
	
	/**
	 * Creates a new CalendarType object. 
	 *
	 */
	private CalendarType() {
		super();		
	}
	
	/**
	 * Date format.
	 */
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SSS"); //$NON-NLS-1$
	
	public Calendar get(ResultSet rs, String columnIndex) throws SQLException {		
		Timestamp dt = rs.getTimestamp(columnIndex);		
		return DateUtils.getCalendar(dt);
	}
	
	public Calendar get(ResultSet rs, int columnIndex) throws SQLException {		
		Timestamp dt = rs.getTimestamp(columnIndex);		
		return DateUtils.getCalendar(dt);
	}	
	
	public synchronized String sqlString(Calendar t) {
		if (t==null) {
			return StringConstants.NULL;
		}
		String s = df.format(t.getTime());
		return StringUtils.quotes(s);
	}
	
	public Object statementParameter(Calendar t) {
		if (t!=null) {			
			return new java.sql.Date(t.getTimeInMillis());
		}
		return null;
	}
	
	public Class<Calendar> getJavaType() {		
		return Calendar.class;
	}

	public Calendar get(ResultSet rs, String columnIndex, boolean returnNullValues) throws SQLException {
		return get(rs, columnIndex);
	}

	public Calendar get(ResultSet rs, int columnIndex, boolean returnNullValues) throws SQLException {
		return get(rs, columnIndex);
	}

}
