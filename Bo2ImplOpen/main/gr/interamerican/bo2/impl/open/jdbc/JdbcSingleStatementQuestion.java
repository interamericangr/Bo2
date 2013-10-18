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

import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.annotations.ParametersOrder;
import gr.interamerican.bo2.utils.sql.SqlUtils;

import java.util.List;

/**
 * This is a question based on one and only one SQL statement.
 * 
 * The SQL statement is defined by annotating a field of
 * the class with the {@link Sql} annotation. <br/>
 * The question can have as many parameters as required. The values of 
 * the parameters are specified from the fields that are annotated with
 * the {@link Parameter} annotation. There are two alternatives for 
 * defining the order of the parameters. The first alternative is to
 * use the functionality of {@link AbstractJdbcWorker}, namely to define
 * the order in a {@link ParametersOrder} annotation. If the class has 
 * this annotation, then this method will be followed. Alternatively
 * the parameters can be defined as named parameters in the sql statement.
 * In this case, the order of their appearance in the statement is the
 * required order.
 * 
 * @param <A> 
 *        Type of answer.
 */
public abstract class JdbcSingleStatementQuestion<A> extends JdbcQuestion<A> {
	
	/**
	 * Statement locator.
	 */
	private StatementLocator locator;
	
	/**
	 * Creates a new JdbcSingleStatementQuestion object. 
	 *
	 */
	public JdbcSingleStatementQuestion() {
		super();
		locator = new StatementLocator(this);
	}
	
	/**
	 * Single SQL statement.
	 * 
	 * @return Returns the SQL statement.
	 */
	protected String sql() {
		return locator.sql();
	}
	
	@Override
	protected String[] getParameterNamesArray() {
		String[] names = super.getParameterNamesArray();
		if (names!=null) {
			return names;
		}
		
		String stmt = sql();
		List<String> paramNames = SqlUtils.getParameterNames(stmt);
		if (paramNames.isEmpty()) {
			return null;
		}
		return paramNames.toArray(new String[0]);
	}
	
}
