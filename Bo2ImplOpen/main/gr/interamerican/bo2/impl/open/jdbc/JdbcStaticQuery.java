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

/**
 * {@link JdbcStaticQuery} is a concrete sub-class of {@link JdbcQuery}. <br/>
 * 
 * The SQL statement is defined in the constructor.
 */
public class JdbcStaticQuery 
extends JdbcQuery {
	
	/**
	 * SQL statement.
	 */
	@Sql String statement;

	/**
	 * Creates a new JdbcStaticQuery object. 
	 *
	 * @param statement
	 */
	public JdbcStaticQuery(String statement) {
		super();
		this.statement = statement;
	}
	
	@Override
	protected String sql() {	
		return statement;
	}	

}
