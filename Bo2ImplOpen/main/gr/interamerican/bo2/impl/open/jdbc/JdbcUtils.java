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
package gr.interamerican.bo2.impl.open.jdbc;


import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class JdbcUtils {
	
	/**
	 * Gets the query results as a list of object arrays.
	 * 
	 * @param q
	 *        Query to crawl.
	 * @param noNull
	 *        Specifies if nulls will be replaced by a string null constant.
	 *        
	 * @return Returns a list of object arrays.
	 * @throws SQLException 
	 * @throws DataAccessException 
	 */
	public static List<Object[]> queryResultsAsList(JdbcQuery q, boolean noNull) 
	throws DataAccessException, SQLException {
		String[] columnNames = q.getColumnNames();
		int columns = columnNames.length;
		List<Object[]> list = new ArrayList<Object[]>(); 
		
		while(q.next()) {
			Object[] row = new Object[columns];
			for(int i=0; i<columns; i++) {
				Object col = q.getObject(i+1);				
				if (noNull) {
					row[i] = Utils.notNull(col, StringConstants.NULL);
				} else {
					row[i] = col;					
				}				 
			}
			list.add(row);
		}
		return list;
	}
	
	/**
	 * Gets the query results as a bidimensional array.
	 * 
	 * @param q
	 *        Query to crawl.
	 * @param noNull
	 *        Specifies if nulls will be replaced by a string null constant.
	 *        
	 * @return Returns a bidimensional array.
	 * @throws SQLException 
	 * @throws DataAccessException 
	 */
	public static Object[][] queryResultsAsMatrix(JdbcQuery q, boolean noNull) 
	throws DataAccessException, SQLException {
		String[] columnNames = q.getColumnNames();
		List<Object[]> list = queryResultsAsList(q,noNull);
		int rows = list.size();
		int columns = columnNames.length;		
		Object[][] contents = new Object[rows][columns];
		int i=0;
		for(Object[] row : list) {
			contents[i] = row;
			i++;
		}
		return contents;		
	}
	
}
