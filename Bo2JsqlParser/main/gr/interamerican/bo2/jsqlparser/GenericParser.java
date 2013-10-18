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
package gr.interamerican.bo2.jsqlparser;

import gr.interamerican.bo2.jsqlparser.exceptions.NotSupportedByVisitorException;
import gr.interamerican.bo2.jsqlparser.op.RemoveUselessJoinsFromSql;
import gr.interamerican.bo2.jsqlparser.visitors.ColumnsFinder;
import gr.interamerican.bo2.jsqlparser.visitors.RemoveParameter;
import gr.interamerican.bo2.utils.sql.SqlProcessor;
import gr.interamerican.bo2.utils.sql.SqlUtils;
import gr.interamerican.bo2.utils.sql.elements.Column;
import gr.interamerican.bo2.utils.sql.elements.Parameter;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;
import gr.interamerican.bo2.utils.sql.parsers.SqlParser;

import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

/**
 * Implementation of {@link SqlParser} based on JsqlParser.
 */
public class GenericParser implements SqlParser {
	
	/**
	 * JSQL parser instance.
	 */
	private static final CCJSqlParserManager JSQL_PARSER = new CCJSqlParserManager();
	
	@Override
	public List<Parameter> getParameters(String sql) throws SqlParseException {
		List<String> parmNames = SqlUtils.getParameterNames(sql);
		return SqlUtils.namesToParametersList(parmNames);
	}
	
	@Override
	public List<Column> getColumns(String sql) throws SqlParseException {
		String normalized = SqlProcessor.normalizeSql(sql);
		String fixedSql = SqlProcessor.maskNamedParameters(normalized, getNamedParameters(sql));
		
		StringReader reader = new StringReader(fixedSql);
		Statement statement = null;
		try {
			statement = JSQL_PARSER.parse(reader);
		} catch (JSQLParserException e) {
			String msg = "Exception while parsing SQL: " + fixedSql; //$NON-NLS-1$
			throw new SqlParseException(msg, e);
		}
		if(statement instanceof Select) {
			try {
				Select selStmt = (Select) statement;
				ColumnsFinder columnsFinder = new ColumnsFinder();
				selStmt.accept(columnsFinder);
				return columnsFinder.getColumns();
			} catch (NotSupportedByVisitorException e) {
				String msg = e.getMessage();
				throw new SqlParseException(msg);
			}
		}
		throw new SqlParseException("Sql statement type not supported"); //$NON-NLS-1$
	}
	
	@Override
	public String removeParameter(String parameter, String sql)	throws SqlParseException {
		String result = SqlProcessor.normalizeSql(sql);
		Set<String> namedParams = getNamedParameters(result);
		String fixedSql = SqlProcessor.maskNamedParameters(result, namedParams);
		StringReader reader = new StringReader(fixedSql);
		Statement statement = null;
		try {
			statement = JSQL_PARSER.parse(reader);
		} catch (JSQLParserException e) {
			throw new SqlParseException(e);
		}
		if(statement instanceof Select) {
			try {
				Select selStmt = (Select) statement;
				RemoveParameter rp = new RemoveParameter(parameter);
				selStmt.accept(rp);
				result = rp.getResult();
				result = SqlProcessor.unmaskNamedParameters(result, namedParams);
				result = SqlProcessor.normalizeSql(result);
				return result;
			} catch (NotSupportedByVisitorException e) {
				String msg = e.getMessage();
				throw new SqlParseException(msg);
			}
		}
		throw new SqlParseException("Sql statement type not supported"); //$NON-NLS-1$
		
	}
	
	/**
	 * Gets the named parameters of a given SQL. The result is given as a {@link Set}, so there are no duplicates.
	 * 
	 * @param sql
	 *            Input sql.
	 * 
	 * @return Returns a Set with named parameters of the input sql.
	 */
	private Set<String> getNamedParameters(String sql) {
		List<String> allNamedParameters = SqlUtils.getParameterNames(sql);
		return new HashSet<String>(allNamedParameters);
	}
	
	/**
	 * Masks named parameters.
	 * @param sql
	 * @param namedParameters 
	 * @return SQL with masked named parameters.
	 */
	private String maskNamedParameters(String sql, Set<String> namedParameters) {
		String result = SqlProcessor.normalizeSql(sql);
		result = SqlProcessor.maskNamedParameters(result, namedParameters);
		return result; 
	}

	@Override
	public String removeUselessJoins(String sql) throws SqlParseException {
		Set<String> namedParameters = getNamedParameters(sql);
		String result = maskNamedParameters(sql, namedParameters);
		
		result = RemoveUselessJoinsFromSql.INSTANCE.remove(result);
		
		result = SqlProcessor.unmaskNamedParameters(result, namedParameters);
		result = SqlProcessor.normalizeSql(result);
		return result;
	}

}
