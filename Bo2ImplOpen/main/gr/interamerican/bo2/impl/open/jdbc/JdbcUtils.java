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


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.impl.open.annotations.ParametersOrder;
import gr.interamerican.bo2.impl.open.workers.AbstractBaseWorker;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.sql.SqlUtils;

/**
 * Utilities for Jdbc.
 */
public interface JdbcUtils {

	/**
	 * Gets the query results as a list of object arrays.
	 *
	 * @param q
	 *            Query to crawl.
	 * @param noNull
	 *            Specifies if nulls will be replaced by a string null constant.
	 * 
	 * @return Returns a list of object arrays.
	 * @throws DataAccessException
	 *             the data access exception
	 * @throws SQLException
	 *             the SQL exception
	 */
	static List<Object[]> queryResultsAsList(JdbcQuery q, boolean noNull)
			throws DataAccessException, SQLException {
		String[] columnNames = q.getColumnNames();
		int columns = columnNames.length;
		List<Object[]> list = new ArrayList<Object[]>();

		while (q.next()) {
			Object[] row = new Object[columns];
			for (int i = 0; i < columns; i++) {
				Object col = q.getObject(i + 1);
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
	 *            Query to crawl.
	 * @param noNull
	 *            Specifies if nulls will be replaced by a string null constant.
	 * 
	 * @return Returns a bidimensional array.
	 * @throws DataAccessException
	 *             the data access exception
	 * @throws SQLException
	 *             the SQL exception
	 */
	static Object[][] queryResultsAsMatrix(JdbcQuery q, boolean noNull)
			throws DataAccessException, SQLException {
		String[] columnNames = q.getColumnNames();
		List<Object[]> list = queryResultsAsList(q, noNull);
		int rows = list.size();
		int columns = columnNames.length;
		Object[][] contents = new Object[rows][columns];
		int i = 0;
		for (Object[] row : list) {
			contents[i] = row;
			i++;
		}
		return contents;
	}

	/**
	 * Gets the parameters for a jdbc based worker.<br>
	 * Essentially this is used when a Jdbc based worker overwrites the method
	 * getParameterNamesArrays of {@link AbstractBaseWorker}.<br>
	 * Firstly the passed super method is invoked (that works with the {@link ParametersOrder} ).<br>
	 * If there is no such annotation, then the sql is processed with the
	 * {@link SqlUtils#getParameterNames(String)}.
	 * 
	 * @param superMethod
	 *            The super method
	 * @param sqlSupplier
	 *            A {@link Supplier} of the sql statement of the worker
	 * @return Returns an array with the parameter names.
	 */
	static String[] getParameterNamesArrays(Supplier<String[]> superMethod, Supplier<String> sqlSupplier) {
		String[] names = superMethod.get();
		if (names != null) {
			return names;
		}

		String stmt = sqlSupplier.get();
		List<String> paramNames = SqlUtils.getParameterNames(stmt);
		if (paramNames.isEmpty()) {
			return null;
		}
		return paramNames.toArray(new String[0]);
	}
}