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
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Time {@link Type}.
 */
public class TimeType extends AbstractType<Time> {
	
	/**
	 * Instance.
	 */
	public static final TimeType INSTANCE = new TimeType();
	
	/**
	 * Date format.
	 */
	private static DateFormat df = new SimpleDateFormat("HH:mm:ss"); //$NON-NLS-1$
	
	/**
	 * Creates a new TimeType object. 
	 */
	private TimeType() {
		super();		
	}

	public Time get(ResultSet rs, String columnIndex) throws SQLException {
		return rs.getTime(columnIndex);
	}

	public Time get(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getTime(columnIndex);
	}

	public synchronized String sqlString(Time t) {
		if (t==null) {
			return StringConstants.NULL;
		}
		String s = df.format(t);
		return StringUtils.quotes(s);
	}

	public Class<Time> getJavaType() {
		return Time.class;
	}

}
