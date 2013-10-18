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
package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.workers.WorkerUtils;

import java.util.List;

/**
 * A command that encapsulates an {@link EntitiesQuery}.
 * 
 * @param <Q>
 *            Type of {@link EntitiesQuery}
 * @param <P>
 *            Type of Entity within the {@link EntitiesQuery}
 */
public class EntitiesQueryCmd<Q extends EntitiesQuery<P>,P> {

	/**
	 * Instance of Query this Command is Based Upon
	 */
	Q query;
	
	/**
	 * Creates a new QueryCmd object.
	 * 
	 * @param clz
	 *            Type of Query we'll execute
	 */
	public EntitiesQueryCmd(Class<Q> clz) {
		query = Factory.create(clz);
	}

	/**
	 * @return all results
	 * @throws LogicException
	 * @throws DataException
	 * @throws UnexpectedException
	 */
	public synchronized List<P> getResults() throws LogicException, DataException, UnexpectedException {
		Cmd command = new Cmd();
		command.execute();
		return command.getResults();
	}
	
	
	/**
	 * Returns the query this command will execute.<br>
	 * This is useful in order to set options in the query.
	 * 
	 * @return query Query this command will execute
	 */
	public Q getQuery() {
		return query;
	}
	
	/**
	 * Encapsulated Command.
	 */
	class Cmd extends AbstractBo2RuntimeCmd {

		/**
		 * Results the Query Returned
		 */
		List<P> results;

		@Override
		public void work() throws LogicException, DataException, 
		InitializationException, UnexpectedException {
			query.init(getProvider());
			query.open();
			query.execute();
			results = WorkerUtils.queryResultsAsList(query);
			query.close();
		}

		/**
		 * Returns the results the Query Returned
		 * 
		 * @return Results the Query Returned
		 */
		public List<P> getResults() {
			return results;
		}
	}

}
