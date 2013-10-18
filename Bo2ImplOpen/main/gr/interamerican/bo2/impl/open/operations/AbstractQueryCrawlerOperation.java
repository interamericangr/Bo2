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
package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.utils.annotations.Child;

/**
 * An {@link AbstractQueryCrawlerOperation} is an Operation that performs work 
 * on each row of the result of a Query execution. <br/>
 * 
 * This operation requires a Query instance and an Operation class. The query is 
 * used to produce the rows which are used in order to provide input for the 
 * Operation. The user must implement {@link #handleRow()} in order to use
 * the current query row to give input to the operation then execute the
 * operation. 
 * <br/>
 * 
 * @param <Q> 
 *        Type of query being executed.
 */
public abstract class AbstractQueryCrawlerOperation<Q extends Query> 
extends AbstractOperation {
	
	/**
	 * Creates a new AbstractQueryCrawlerOperation object. 
	 *
	 * @param query
	 */
	public AbstractQueryCrawlerOperation(Q query) {
		super();
		this.query = query;
	}


	/**
	 * Query to execute.
	 */
	@Child protected Q query;
	
	
	/**
	 * This method is a place holder for defining functionality that has
	 * to be run before the query execution.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 */
	@SuppressWarnings("unused")
	protected void beforeQuery() throws LogicException, DataException {
		/* empty */
	}
	
	/**
	 * This method is a place holder for defining functionality that has
	 * to be run after crawling the query.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 */
	@SuppressWarnings("unused")
	protected void afterQuery() throws LogicException, DataException {
		/* empty */
	}
	
	/**       
	 * Handles the current row of the query.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	protected abstract void handleRow() throws LogicException, DataException;

	
	@Override
	public void execute() throws LogicException, DataException {
		beforeQuery();
		query.execute();
		while (query.next()) {
			handleRow();			
		}
		afterQuery();
	}

}
