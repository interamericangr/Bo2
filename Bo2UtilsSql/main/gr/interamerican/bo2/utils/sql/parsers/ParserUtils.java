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
package gr.interamerican.bo2.utils.sql.parsers;

import java.util.List;
import java.util.Map;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.sql.SqlUtils;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;

/**
 * Utilities for Parsers.
 */
public interface ParserUtils {	

	/**
	 * Removes the parameters of an SQL, that have a corresponding null value in
	 * a query criteria bean. The useless joins of this SQL are removed as well.
	 *
	 * @param parser
	 *            Parser used to remove the parameters.
	 * @param criteria
	 *            Criteria bean.
	 * @param sql
	 *            SQL string.
	 * @return Returns a new sql that has does not include the clauses that
	 *         depend on parameters witch are null in the criteria bean.
	 * @throws SqlParseException
	 *             the sql parse exception
	 */
	static String removeNullParameters(SqlParser parser, Object criteria, String sql) throws SqlParseException {
		if (criteria == null) {
			return removeAllParameters(parser, sql);
		}
		List<String> paramsList = SqlUtils.getParameterNames(sql);
		String newSql = sql;
		Map<String, Object> map = ReflectionUtils.getProperties(criteria);
		for (String paramName : CollectionUtils.toSet(paramsList)) {
			Object value = findValue(map, paramName);
			if (value == null) {
				newSql = parser.removeParameter(paramName, newSql);
			}
		}
		newSql = parser.removeUselessJoins(newSql);
		return newSql;
	}
	/**
	 * Finds the value for a parameter of an sql statement with respect in
	 * enclosed types.<br>
	 * A 'dot' notation is used for enclosed types.<br>
	 * In case of wrong name a {@link RuntimeException} is thrown.<br>
	 * In case a null value exists in the graph, then null is returned.
	 * 
	 * @param parametersMap
	 *            All parameters (consider using
	 *            {@link ReflectionUtils#getProperties(Object)} if this concerns
	 *            a single criteria object
	 * @param key
	 *            Parameter name
	 * @return object Final Object (or null)
	 */
	@SuppressWarnings("nls")
	static Object findValue(Map<String, Object> parametersMap, String key) {
		String[] tokens = TokenUtils.split(key, StringConstants.DOT);
		String first = tokens[0];
		if (!parametersMap.containsKey(first)) {
			throw new RuntimeException("Field " + key + " not found");
		}
		Object value = parametersMap.get(first);
		for (int j = 1; (j < tokens.length) && (value != null); j++) {
			value = ReflectionUtils.getProperty(tokens[j], value);
		}
		return value;
	}

	/**
	 * Removes all parameters. This is useful if the criteria object is null.
	 *
	 * @param parser
	 *            SQL parser.
	 * @param sql
	 *            SQL to manipulate
	 * 
	 * @return Returns the SQL after the removal of all parameters.
	 * @throws SqlParseException
	 *             the sql parse exception
	 */
	static String removeAllParameters(SqlParser parser, String sql) throws SqlParseException {
		String result = sql;
		List<String> paramsList = SqlUtils.getParameterNames(sql);
		for (String paramName : paramsList) {
			result = parser.removeParameter(paramName, result);
		}
		return result;
	}
}