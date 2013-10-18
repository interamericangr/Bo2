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
package gr.interamerican.bo2.samples.implopen.operations;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.jdbc.JdbcCommand;
import gr.interamerican.bo2.impl.open.jdbc.Sql;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.utils.annotations.Child;

import java.sql.SQLException;

/**
 * Sample Operation implementation.
 */
@SuppressWarnings("all")
@ManagerName("LOCALDB")
public class OperationWithJdbcWorker extends AbstractOperation {
	
	public static final String FAILING_ID = "1560";
	
	@Child WorkerFixture wf = new WorkerFixture();

	@Override
	public void execute() throws LogicException, DataException {
		wf.execute();
	}

	public int getRowCount() {
		return wf.getRowCount();
	}

	public void setId(String id) {
		wf.setId(id);
	}

	/**
	 * Sample worker implementation for the sample Operation.
	 */
	@SuppressWarnings("all")
	private class WorkerFixture extends JdbcCommand {

		@Sql
		String sql = "insert into X__X.INVOICE values (?, CURRENT TIMESTAMP, '', CURRENT DATE, '')";

		int rowCount;
		String id;

		@Override
		protected void work() throws DataException {
			rowCount = 0;
			if (id.equals(FAILING_ID)) {
				throw new RuntimeException(id);
			}
			try {
				rowCount = executePreparedUpdate(sql, new Object[] { id });
			} catch (SQLException e) {
				throw new DataException(e);
			}
		}

		public int getRowCount() {
			return rowCount;
		}

		public void setId(String id) {
			this.id = id;
		}
	}

}
