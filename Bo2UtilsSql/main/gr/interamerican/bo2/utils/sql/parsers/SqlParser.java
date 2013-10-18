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

import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;

import java.util.List;

/**
 * Interface for an SQL parser.
 */
public interface SqlParser {
	/**
	 * Prefix character that marks a parameter.
	 */
	public static final Character PARM_CHAR = ':'; 
	
	/**
	 * SQL AND predicate
	 */
	public static final String AND = "AND"; //$NON-NLS-1$
	
	/**
	 * SQL OR predicate
	 */
	public static final String OR = "OR"; //$NON-NLS-1$
	
	/**
	 * SQL WHERE clause.
	 */
	public static final String WHERE = "WHERE"; //$NON-NLS-1$
	
	/**
	 * Gets a list with the parameters that are found in the specified SQL 
	 * string. <br/>
	 * 
	 * Each parameters occurs in the list as many times as it is found in the
	 * SQL string.  
	 * 
	 * @param sql
	 *        String being parsed.
	 *        
	 * @return Returns a list with the parameters found in the specified SQL string.
	 *         
	 * @throws SqlParseException 
	 */
	public List<Parameter> getParameters(String sql)
	throws SqlParseException;
	
	/**
	 * Gets a list with the columns that are found in the specified SQL 
	 * string. <br/>
	 * 
	 * Each column occurs in the list as many times as it is found in the
	 * SQL string.  
	 * 
	 * @param sql
	 *        String being parsed.
	 *        
	 * @return Returns a list with the columns returned from the specified SQL string.
	 *         
	 * @throws SqlParseException 
	 */
	public List<Column> getColumns(String sql)
	throws SqlParseException;
	
	/**
	 * Parses the specified SQL string and removes any part of the statement
	 * that depends on the specified parameter.
	 * 
	 * @param parameter 
	 *        Name of the parameter that will be removed from the specified
	 *        SQL string.
	 * @param sql
	 *        SQL String parsed
	 *        
	 * @return Returns the new SQL statement after removing any part that
	 *         depends on the specified parameter.
	 *         
	 * @throws SqlParseException 
	 */
	public String removeParameter(String parameter, String sql)
	throws SqlParseException;
	
	/**
	 * Parses the specified SQL string and removes any joins that are not
	 * useful for the result set columns or the where clause.
	 * 
	 * @param sql
	 *        SQL String parsed
	 *        
	 * @return Returns the new SQL statement after removing any joins that
	 *         are redundant.
	 *         
	 * @throws SqlParseException 
	 */
	public String removeUselessJoins(String sql)
	throws SqlParseException;

}
