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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Timestamp {@link Type}.
 */
public class TimestampType 
extends AbstractType<Timestamp> { 
	/**
	 * Instance.
	 */
	public static final TimestampType INSTANCE = new TimestampType();	
	/**
	 * Date format.
	 */
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SSS"); //$NON-NLS-1$
	
	/**
	 * Creates a new TimestampType object. 
	 *
	 */
	private TimestampType() {
		super();
	}
	
	public Timestamp get(ResultSet rs, String columnIndex) throws SQLException {		
		return rs.getTimestamp(columnIndex);
	}
	
	public Timestamp get(ResultSet rs, int columnIndex) throws SQLException {	
		return rs.getTimestamp(columnIndex);
	}	
	
	public synchronized String sqlString(Timestamp t) {
		if (t==null) {
			return StringConstants.NULL;
		}
		String s = df.format(t);
		return StringUtils.quotes(s);
	}
	
	public Class<Timestamp> getJavaType() {		
		return Timestamp.class;
	}

}
