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

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.parsers.SqlParser;
import gr.interamerican.bo2.utils.sql.types.Type;
import gr.interamerican.bo2.utils.sql.types.TypeUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utilities relevant to SQL.
 */
public class SqlUtils {
	
	/**
	 * Private constructor of utility class.
	 */
	private SqlUtils() {
		/* empty */
	}
	
	/**
	 * Gets an ArrayList that contains the parameters that are found in the 
	 * specified SQL string. <br/>
	 * 
	 * The position of each parameter in the list is the same as its occurence
	 * in the SQL statement. Each parameters occurs in the list as many times 
	 * as it is found in the SQL string. Parameters are strings starting with 
	 * colon (:). 
	 * 
	 * @param sql
	 *        String being parsed.
	 *        
	 * @return Returns a list with the names of the parameters found in the
	 *         specified SQL string.
	 */
	public static List<String> getParameterNames(String sql) {
		ArrayList<String> params = new ArrayList<String>();
		String prepared = StringUtils.addSpaceBeforeChar(sql, SqlParser.PARM_CHAR);
		String separators = StringUtils.concat(
				StringConstants.SPACE, 
				StringConstants.COMMA,
				StringConstants.LEFT_PARENTHESIS,
				StringConstants.RIGHT_PARENTHESIS,
				StringConstants.NEWLINE);
		String[] tokens = TokenUtils.splitTrim(prepared, separators, false);
		for (String token : tokens) {
			if (token.charAt(0)==SqlParser.PARM_CHAR) {
				String param = token.substring(1);
				params.add(param);
			}
		}
		return params;
	}
	
	/**
	 * Creates an ArrayList {@link Parameter} objects from an ArrayList 
	 * of parameter names. <br/>
	 * 
	 * Each parameter occurs in the same position as its name in the 
	 * <code>parmNames</code> ArrayList
	 * 
	 * @param parmNames
	 *        ArrayList with parameter names.
	 *        
	 * @return Returns a list of parameter objects.
	 */
	public static List<Parameter> namesToParametersList(List<String> parmNames) {		
		ArrayList<Parameter> parameters = new ArrayList<Parameter>();
		for (String name : parmNames) {
			Parameter parm = new Parameter();
			parm.setName(name);
			parm.setType(null);
			parameters.add(parm);
		}		
		return parameters;
	}

	/**
	 * Null safe toString.
	 * 
	 * @param object
	 * 
	 * @return If the object is null, returns the string literal null.
	 *         Otherwise, returns the toString() value of the object.
	 */
	public static String toString(Object object) {
		if (object==null) {
			return StringConstants.NULL;
		}
		return object.toString();
	}
	
	/**
	 * Null safe toString.
	 * 
	 * @param object
	 * 
	 * @return If the object is null, returns the string literal null.
	 *         Otherwise, returns the toString() value of the object
	 *         in single quotes.
	 */
	public static String toQuotedString(Object object) {
		if (object==null) {
			return StringConstants.NULL;
		}
		return StringUtils.quotes(object.toString());
	}
	
	/**
	 * Creates the SQL string for the specified object.
	 * 
	 * @param object
	 *        Value to present as an SQL literal.
	 *        
	 * @return Returns a string literal that represents the specified value
	 *         in the SQL dialect.
	 */
	@SuppressWarnings("unchecked")
	public static String sqlString(Object object) {
		@SuppressWarnings("rawtypes") Type type = TypeUtils.getType(object);		
		return type.sqlString(object);
	}
	
	/**
	 * Creates the SQL string for the specified object.
	 * 
	 * @param object
	 *        Value to present as an SQL literal.
	 *        
	 * @return Returns a string literal that represents the specified value
	 *         in the SQL dialect.
	 */
	@SuppressWarnings("unchecked")
	public static Object statementParameter(Object object) {
		if (object==null) {
			return null;
		}
		@SuppressWarnings("rawtypes") Type type = TypeUtils.getType(object);		
		return type.statementParameter(object);
	}
	
	/**
	 * Replaces the parameters in a string with question mark parameter markers.
	 * @param sql 
	 * 
	 * @return Returns the replaced string.
	 */
	public static String replaceParametersWithMarkers(String sql) {
		List<String> paramNames = getParameterNames(sql);
		String s = sql;
		for (String param : paramNames) {
			String paramMarker = SqlParser.PARM_CHAR + param;
			s = s.replace(paramMarker, StringConstants.QUESTIONMARK);
		}
		return s;
	}
	
	/**
	 * This function gets a collection and creates and in or not in sql statement.<br> 
	 * E.g. from the set {1,2,3} will create the statement "in (1,2,3)"
	 * 
	 * @param col {@link Collection} to  
	 * @param column the database column name.
	 * @param isNot create "not in" instead of "in".
	 * @param addEnd include "add" in the {@link String}.
	 * @return the formatted {@link String} to be added to the sql.
	 */
	public static String createInFromCollection(Collection<?> col,String column,boolean isNot,boolean addEnd){
		String output=StringConstants.EMPTY;
		if ((CollectionUtils.isNullOrEmpty(col))||(column==null)||(column.length()==0)){
			return output;
		}
		for (Object c:col){
			if (output.length()>0){
				output+=StringConstants.COMMA;
			}
			output+=c;
		}
		output=StringConstants.LEFT_PARENTHESIS+output+StringConstants.RIGHT_PARENTHESIS;
		output=" in "+output; //$NON-NLS-1$
		if (isNot){
			output=" not"+output; //$NON-NLS-1$
		}
		output=StringConstants.SPACE+column+output;
		if (addEnd){
			output=" and"+output; //$NON-NLS-1$
		}
		return output;
	}
}
