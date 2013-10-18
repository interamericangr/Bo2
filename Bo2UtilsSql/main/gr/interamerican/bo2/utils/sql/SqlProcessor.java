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
package gr.interamerican.bo2.utils.sql;

import static gr.interamerican.bo2.utils.StringConstants.EMPTY;
import static gr.interamerican.bo2.utils.StringConstants.QUOTE;
import static gr.interamerican.bo2.utils.StringConstants.SEMICOLON;
import static gr.interamerican.bo2.utils.StringConstants.SPACE;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.comparators.StringComparator;
import gr.interamerican.bo2.utils.sql.parsers.SqlParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Processes an SQL statement.
 */
@SuppressWarnings("nls")
public class SqlProcessor {
	
	/**
	 * The SQL parser will not parse SQL statements with named parameters, e.g. :name
	 * For this reason, we will mask these as string literals. So, :name will be 
	 * converted to ':name'.
	 * 
	 * @param sql
	 *        The SQL statement with named parameters.
	 * @param namedParameters
	 *        List of named parameters in the SQL statement.
	 *        The named parameters are expected without the ':'.
	 *        
	 * @return Returns the same SQL statement with 'masked' parameters.
	 */
	public static String maskNamedParameters(String sql, Set<String> namedParameters) {
		String result = sql;
		/*
		 * In order to avoid bugs when a parameter name contains another parameter name
		 * e.g. :streetNo contains :street
		 * we sort reverse the parameters first.
		 */
		List<String> namedParametersList = new ArrayList<String>(namedParameters);
		Collections.sort(namedParametersList, Collections.reverseOrder(new StringComparator()));
		for(String namedParam : namedParametersList) {
			String masked = QUOTE + SqlParser.PARM_CHAR + namedParam + QUOTE;
			result = result.replaceAll(SPACE + SqlParser.PARM_CHAR + namedParam, SPACE + masked);
		}
		return result;
	}
	
	/**
	 * This utility unmasks named parameters converted to string literals.
	 * For example, ':name' will be converted to :name.
	 * 
	 * @see #maskNamedParameters(String, Set)
	 * 
	 * @param sql
	 *        The SQL statement with named parameters.
	 * @param namedParameters
	 *        List of named parameters of the original SQL statement 
	 *        (before masking occurred). The named parameters are
	 *        expected without the ':'.
	 *        
	 * @return Returns the same SQL statement with unmasked parameters.
	 */
	public static String unmaskNamedParameters(String sql, Set<String> namedParameters) {
		String result = sql;
		for(String namedParam : namedParameters) {
			String masked = QUOTE + SqlParser.PARM_CHAR + namedParam + QUOTE;
			result = result.replaceAll(masked, SqlParser.PARM_CHAR + namedParam);
		}
		return result;
	}
	
	/**
	 * Normalizes an SQL string.
	 * 
	 * @param sql
	 * @return normalized sql.
	 */
	public static String normalizeSql(String sql) {
		String result = removeDoubleHyphenSqlComments(sql);
		/*
		 * 1. Make sure there is no ';'.
		 */
		result = result.replace(SEMICOLON, EMPTY);
		/*
		 * 2. Make sure there is a space before each colon.
		 */
		result = result.replace(SqlParser.PARM_CHAR.toString(), SPACE + SqlParser.PARM_CHAR);
		/*
		 * 3. Replace \n \t \r with space.
		 */
		result = result.replace("\n", SPACE);
		result = result.replace("\r", SPACE);
		result = result.replace("\t", SPACE);
		/*
		 * 4. Remove double spaces.
		 */
		return StringUtils.normalizeSpaces(result);
	}
	
	/**
	 * Removes -- style SQL comments.
	 * 
	 * @param sql
	 * @return SQL without comments.
	 */
	static String removeDoubleHyphenSqlComments(String sql) {
		return sql.replaceAll("--.*", StringConstants.SPACE);
	}

}
