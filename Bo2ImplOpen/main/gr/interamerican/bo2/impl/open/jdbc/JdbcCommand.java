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

/**
 * Abstract implementation of {@link DataCommand} based on JDBC.
 *
 */
public abstract class JdbcCommand 
extends AbstractJdbcWorker 
implements DataCommand {

	
	public void execute() throws DataException {
		try {
			Debug.setActiveModule(this);
			validateOpen();
			work();
		} finally {
			Debug.resetActiveModule();
		}
	}
	
	/**
	 * Main body of DataCommand.
	 * 
	 * @throws DataException
	 */
	protected abstract void work() throws DataException;

}
