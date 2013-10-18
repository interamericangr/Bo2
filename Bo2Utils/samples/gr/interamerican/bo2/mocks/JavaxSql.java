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
package gr.interamerican.bo2.mocks;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 
 */
public class JavaxSql {
	
	/**
	 * Mock DataSource that gets a mock connection.	 
	 * 
	 * The <code>getConnection()</code> and <code>getConnection(user,pass)</code>
	 * of this datasource return a mock connection.    
	 * 
	 * @return Returns a mock datasource.
	 */
	public static DataSource mockDatasource() {
		Connection mockConn = mock(Connection.class);
		DataSource mockDs = mock(DataSource.class);
		try {
			when(mockDs.getConnection()).thenReturn(mockConn);
			when(mockDs.getConnection(anyString(),anyString())).thenReturn(mockConn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return mockDs;
	}
	
	/**
	 * Mock DataSource that fails.	 
	 * 
	 * All methods of this datasource throw the specified exception.  
	 *   
	 * @param ex
	 *        Exception to be thrown. 
	 *        This must be either an SQLException or a RuntimeException.
	 * 
	 * @return Returns a mock datasource.
	 */
	public static DataSource mockDatasourceThatFails(Exception ex) {
		DataSource mockDs = mock(DataSource.class);				
		try {
			when(mockDs.getConnection()).thenThrow(ex);			
			when(mockDs.getConnection(anyString(),anyString())).thenThrow(ex);
			when(mockDs.getLogWriter()).thenThrow(ex);
			when(mockDs.getLoginTimeout()).thenThrow(ex);
			doThrow(ex).when(mockDs).setLoginTimeout(anyInt());
			doThrow(ex).when(mockDs).setLogWriter((PrintWriter)any());
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return mockDs;
	}

}
