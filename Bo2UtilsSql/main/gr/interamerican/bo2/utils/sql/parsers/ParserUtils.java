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

import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.sql.SqlUtils;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utilities for Parsers.
 */
public class ParserUtils {	
	
	/**
	 * Removes the parameters of an SQL, that have a corresponding null
	 * value in a query criteria bean. The useless joins of this SQL are
	 * removed as well.
	 * 
	 * @param parser 
	 *        Parser used to remove the parameters.
	 * @param criteria
	 *        Criteria bean.
	 * @param sql
	 *        SQL string.
	 *        
	 * @return Returns a new sql that has does not include the clauses
	 *         that depend on parameters witch are null in the criteria bean. 
	 * @throws SqlParseException 
	 */
	public static String removeNullParameters(SqlParser parser, Object criteria, String sql) 
	throws SqlParseException {
		if(criteria==null) {
			return removeAllParameters(parser, sql);
		}
		List<String> paramsList = SqlUtils.getParameterNames(sql);
		String newSql = sql;
		Set<String> params = new HashSet<String>();
		params.addAll(paramsList);		
		Map<String, PropertyDescriptor> descriptors = 
			JavaBeanUtils.getBeansPropertiesMap(criteria.getClass());
		for (String paramName : params) {
			PropertyDescriptor pd = descriptors.get(paramName);
			if (pd==null) {
				invalidProperty(criteria,paramName);
			}
			Object paramValue = JavaBeanUtils.getProperty(pd, criteria);
			if (paramValue==null) {
				newSql = parser.removeParameter(paramName, newSql); 
			}
		}
		
		newSql = parser.removeUselessJoins(newSql);
		return newSql;		
	}
	
	/**
	 * Throws a RuntimeException.
	 * 
	 * @param criteria
	 * @param property
	 */
	static void invalidProperty(Object criteria, String property) {
		@SuppressWarnings("nls")
		String msg = StringUtils.concat(
			"Class ", criteria.getClass().getName(),
			" does not have a property with the name ", property);
		throw new RuntimeException(msg);
	}
	
	/**
	 * Removes all parameters. This is useful if the criteria object is null.
	 * 
	 * @param parser
	 *        SQL parser.
	 *        
	 * @param sql
	 *        SQL to manipulate
	 *        
	 * @return Returns the SQL after the removal of all parameters.
	 * @throws SqlParseException 
	 *        
	 */
	private static String removeAllParameters(SqlParser parser, String sql) 
	throws SqlParseException {
		String result = sql;
		List<String> paramsList = SqlUtils.getParameterNames(sql);
		for (String paramName : paramsList) {
			result = parser.removeParameter(paramName, result);
		}
		return result;
	}

}
