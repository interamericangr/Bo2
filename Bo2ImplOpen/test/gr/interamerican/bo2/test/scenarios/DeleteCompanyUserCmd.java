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
package gr.interamerican.bo2.test.scenarios;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.jdbc.JdbcCommand;
import gr.interamerican.bo2.impl.open.jdbc.Sql;

import java.sql.SQLException;

/**
 * 
 */
public class DeleteCompanyUserCmd extends JdbcCommand {
	
	@Sql
	private String sqlUsers = "delete from TEST.COMPANYUSER where ID<>999"; //$NON-NLS-1$
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.impl.open.jdbc.JdbcCommand#work()
	 */
	@Override
	protected void work() throws DataException {
		String[] params = new String[0];
		try {	
			executePreparedUpdate(sqlUsers, params);
		} catch (SQLException e) {
			throw new DataException(e);
		}

	}

}
