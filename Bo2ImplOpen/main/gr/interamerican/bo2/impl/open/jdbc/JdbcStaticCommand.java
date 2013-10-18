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

import java.sql.SQLException;

/**
 * JdbcStaticCommand is a {@link JdbcCommand} that has only
 * one SQL statement and has no parameters.
 */
public class JdbcStaticCommand 
extends JdbcCommand {
	
	/**
	 * SQL statement.
	 */
	@Sql String statement;
	
	/**
	 * Rows modified by last execution.
	 */
	int rowsModified;

	/**
	 * Creates a new JdbcStaticCommand object. 
	 *
	 * @param statement
	 *        SQL statement of the command.
	 */
	public JdbcStaticCommand(String statement) {
		super();
		this.statement = statement;
	}

	
	@Override
	protected void work() throws DataException {
		try {
			rowsModified = executePreparedUpdate(statement,null);
		} catch (SQLException e) {
			throw new DataException(e);
		}		
	}


	/**
	 * Gets the count of rows modified by the last execution
	 * of the command.
	 *
	 * @return Returns the count of rows modified by the last
	 *         execution of the command.
	 */
	public int getRowsModified() {
		return rowsModified;
	}

}
