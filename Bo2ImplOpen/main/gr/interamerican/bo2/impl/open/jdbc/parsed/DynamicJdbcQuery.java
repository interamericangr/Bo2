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
package gr.interamerican.bo2.impl.open.jdbc.parsed;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.impl.open.jdbc.JdbcQuery;
import gr.interamerican.bo2.impl.open.parse.ParserProvider;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.sql.SqlUtils;
import gr.interamerican.bo2.utils.sql.exceptions.SqlParseException;
import gr.interamerican.bo2.utils.sql.parsers.ParserUtils;
import gr.interamerican.bo2.utils.sql.parsers.SqlParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JdbcQuery that has its SQL query statement generated dynamically
 * according to the parameters set to a query criteria bean.
 * 
 * Query parameters that have their corresponding value in the criteria
 * bean set to null, are omitted from the SQL statement.
 * 
 * @param <C> 
 *        Type of criteria.
 */
public abstract class DynamicJdbcQuery<C> 
extends JdbcQuery 
implements CriteriaDependent<C> {
	
	/**
	 * Parser.
	 */
	SqlParser parser;
	
	/**
	 * query criteria.
	 */
	C criteria;
	
	/**
	 * Parameters for the prepared statement.
	 */
	Object[] parms;
	
	/**
	 * Gets the base SQL statement that includes all parameters.
	 * 
	 * @return Returns the base SQL statement.
	 */
	public abstract String baseSql();	
	
	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		ParserProvider parserProvider = getResource(ParserProvider.class);
		this.parser = parserProvider.getParser();
	}
	
	public C getCriteria() {
		return criteria;
	}
	
	public void setCriteria(C criteria) {
		this.criteria = criteria;
	} 
	
	@Override
	protected final String sql() {	
		String stmt;
		try {
			stmt = ParserUtils.removeNullParameters(parser, criteria, baseSql());
		} catch (SqlParseException e) {
			throw new RuntimeException(e);
		}
		List<String> parmNames = SqlUtils.getParameterNames(stmt);
		
		if (parmNames.size()>0) {
			Map<String, Object> map = ReflectionUtils.getProperties(criteria);			
			List<Object> parmValues = new ArrayList<Object>();			
			for (String parmName : parmNames) {
				Object value = map.get(parmName); 
				/*
				 * If the object is a Codified, then we will
				 * want to bind its code to the parameters and not
				 * the object itself. 
				 */
				if(value instanceof Codified) {
					Codified<?> c = (Codified<?>) value;
					parmValues.add(c.getCode());
				} 
				/*
				 * If the object is a TranslatableEntryOwner, i.e. an Enum,
				 * we will want to bind the code of its TranslatableEntry
				 * to the parameters and not the object itself.
				 */
				else if(value instanceof TranslatableEntryOwner) {
					@SuppressWarnings("rawtypes")
					TypedSelectable ts = ((TranslatableEntryOwner) value).getEntry();
					parmValues.add(ts.getCode());
				}
				else {
					parmValues.add(value);
				}
			}
			parms = parmValues.toArray();
		} else {
			parms = null;
		}
		String jdbcStmt = SqlUtils.replaceParametersWithMarkers(stmt);
		logStatement(jdbcStmt, parms);
		return jdbcStmt;
	}
	
	@Override
	protected final Object[] parameters() {		
		return parms;
	}

}
