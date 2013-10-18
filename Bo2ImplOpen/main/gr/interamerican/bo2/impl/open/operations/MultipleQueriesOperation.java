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

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.impl.open.workers.WorkerUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Operation that executes more than one {@link EntitiesQuery}
 * and puts the results of each query in a List.
 * 
 * Each query is indexed with a query name. The operation returns
 * a map that maps the list with each query's results to the name 
 * of the query. The same criteria are applied to each query, provided
 * it is {@link CriteriaDependent}.
 * 
 * @param <M> 
 *        Type of entities fetched by the queries. 
 */
public class MultipleQueriesOperation<M> 
extends AbstractOperation 
implements CriteriaDependent<Object>{
	
	/**
	 * Criteria bean for all queries.
	 */
	Object criteria;
	
	/**
	 * Map with the queries.
	 */
	Map<String, EntitiesQuery<? extends M>> queries;
	
	/**
	 * Map with the query results.
	 */
	Map<String, List<? extends M>> results = new HashMap<String, List<? extends M>>();
	
	/**
	 * Gets the queries.
	 *
	 * @return Returns the queries
	 */
	public Map<String, EntitiesQuery<? extends M>> getQueries() {
		return queries;
	}

	public Object getCriteria() {
		return criteria;
	}

	public void setCriteria(Object criteria) {
		this.criteria = criteria;
	}

	/**
	 * Assigns a new value to the queries.
	 *
	 * @param queries the queries to set
	 */
	public void setQueries(Map<String, EntitiesQuery<? extends M>> queries) {
		this.queries = queries;
	}

	/**
	 * Gets the results.
	 *
	 * @return Returns the results
	 */
	public Map<String, List<? extends M>> getResults() {
		return results;
	}

	@Override
	public void init(Provider parent) throws InitializationException {		
		super.init(parent);
		for (Query q : queries.values()) {
			q.init(parent);
		}
	}
	
	@Override
	public void open() throws DataException {		
		super.open();
		for (Query q : queries.values()) {
			q.open();
		}
	}
	
	@Override
	public void close() throws DataException {	
		for (Query q : queries.values()) {
			q.close();
		}
		super.close();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute() throws LogicException, DataException {
		results.clear(); 
		for (Map.Entry<String, EntitiesQuery<? extends M>> entry : queries.entrySet()) {
			String name = entry.getKey();
			EntitiesQuery<? extends M> query = entry.getValue();
			if (query instanceof CriteriaDependent) {
				((CriteriaDependent) query).setCriteria(criteria);
			}
			query.execute();
			List<? extends M> result = WorkerUtils.queryResultsAsList(query);
			results.put(name, result);
		}		
	}

}
