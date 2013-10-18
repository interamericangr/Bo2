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
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;

import java.util.List;

/**
 * This operation takes as input an array of queries and loads their 
 * elements on a cache. <br/>
 * 
 * The prerequisite for this is that each query is an {@link EntitiesQuery}
 * and the entity it returns is a sub-type of {@link TypedSelectable}.
 * The type of code of the {@link TypedSelectable} must match the type of
 * code of the cache that is loaded. <br/>
 * This operation can't be executed multiple times for different arrays of
 * queries, this is why it does not provide setter methods for the array
 * of queries and the cache, but instead takes both as parameters in its
 * constructor.
 * 
 * @param <C> Type of code that the cache holds.
 */
public class LoadQueriesToCacheOperation<C extends Comparable<? super C>> 
extends AbstractOperation {
	
	
	
	
	/**
	 * Queries to load to the cache.
	 */
	List<EntitiesQuery<? extends TypedSelectable<C>>> queries;
	
	/**
	 * Cache that will be loaded with the elements of the queries.
	 */
	Cache<C> cache;	
	
	/**
	 * Creates a new LoadQueriesToCacheOperation object.
	 *
	 * @param queries
	 * @param cache
	 */
	public LoadQueriesToCacheOperation(
			List<EntitiesQuery<? extends TypedSelectable<C>>> queries,
			Cache<C> cache) {
		super();
		this.queries = queries;
		this.cache = cache;
	}

	@Override
	public void execute() throws LogicException, DataException {
		if(queries!=null){
			for (EntitiesQuery<? extends TypedSelectable<C>> query : queries) {
			loadQuary(query);
			}		
		}
	}

	@Override
	public void open() throws DataException {	
		super.open();
		if(queries!=null){
			for (EntitiesQuery<? extends TypedSelectable<C>> query : queries) {
				query.open();
			}
		}
	}

	@Override
	public void close() throws DataException {		
		super.close();
		if(queries!=null){
			for (EntitiesQuery<? extends TypedSelectable<C>> query : queries) {
				query.close();
			}
		}
	}
	
	@Override
	public void init(Provider parent) throws InitializationException {		
		super.init(parent);
		if(queries!=null){
			for (EntitiesQuery<? extends TypedSelectable<C>> query : queries) {
				query.init(parent);
			}
		}
	}
	
	/**
	 * Loads a query in cache.
	 * 
	 * @param query Query to load.
	 * @throws DataException 
	 */
	void loadQuary(EntitiesQuery<? extends TypedSelectable<C>> query) 
	throws DataException {
		query.execute();
		while (query.next()) {
			TypedSelectable<C> entry = query.getEntity();
			cache.put(entry);
		}
	}
}
