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

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is a question based on one and only one SQL statement that
 * checks if something exists in the database.
 * 
 * The SQL statement is defined by annotating a field or a method of
 * the class with the {@link Sql} annotation. If the SQL query statement
 * fetches one or more rows, then the answer is true. If the statement
 * does not fetch any row, then the answer is false.
 * 
 */
public abstract class JdbcExistsQuestion extends JdbcSingleStatementQuestion<Boolean> {
	
	/**
	 * Object that holds the answer.
	 */
	Boolean answer;		
	
	@Override
	protected void work() throws DataException, LogicException {
		answer = false;
		String stmt = sql();
		try {			
			Object[] parms = this.getParamsFromNamedParams();
			ResultSet rs = executePreparedQuery(stmt,parms);
			if (rs.next()) {				
				answer = true;
			}
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}	
	
	@Override
	public Boolean getAnswer() {	
		return answer;
	}
	
}
