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

import gr.interamerican.bo2.arch.DataCommand;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.Debug;

import java.sql.SQLException;

/**
 * Abstract implementation of {@link DataCommand} based on JDBC.
 *
 */
public abstract class JdbcSimpleCommand 
extends JdbcCommand 
implements DataCommand {
	
	/**
	 * SQL statement of the query.
	 * 
	 * @return Returns the SQL string for the query.
	 */	
	protected abstract String sql();
	
	/**
	 * Parameters for the query.
	 * 
	 * If the command does not need parameters,
	 * this method should return null or an empty array.
	 * 
	 * @return Returns an array with parameters for the 
	 *         statement.
	 */
	protected abstract Object[] parameters();
	
	@Override
	public final void work() throws DataException {		
		try {
			Debug.setActiveModule(this);
			executePreparedUpdate(sql(), parameters());
		} catch (SQLException e) {			
			throw new DataException(e);
		} finally {
			Debug.resetActiveModule();			
		}
	}
	
	@Override
	public void open() throws DataException {		
		super.open();
		prepare(sql());
	}

}
