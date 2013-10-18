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
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.CacheUtils;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.utils.annotations.Child;

import java.util.List;

/**
 * Fills a {@link Cache}.
 * 
 * @param <C>
 *        Type of code in the cache.
 */
public abstract class AbstractCacheLoaderOperation<C extends Comparable<? super C>> 
extends AbstractOperation {
	
	/**
	 * system cache.
	 */
	protected Cache<C> cache;

	
	/**
	 * Creates a new AbstractCacheLoaderOperation object. 
	 * 
	 * @param cache
	 *        Cache to fill. 
	 *
	 */
	public AbstractCacheLoaderOperation(Cache<C> cache) {
		super();
		this.cache = cache;
		loadQueriesOperation = new LoadQueriesToCacheOperation<C> 
			(systemListEntriesQueries(), cache);
	}
	
	/**
	 * Loads the cache with the results of a list of queries.
	 */
	@Child private LoadQueriesToCacheOperation<C> loadQueriesOperation;

	/**
	 * Loads the cache with a list of entry owners.
	 */
	private void putEnumerationsInCache() {
		for (List<? extends TranslatableEntryOwner<C, ?, ?>> list : entryOwnersLists()) {
			CacheUtils.loadEntryOwners(cache, list);
		}
	}
	
	/**
	 * List that contains lists of {@link TranslatableEntryOwner} objects
	 * that will be loaded in the cache.
	 *   
	 * @return Returns a collection of {@link TranslatableEntryOwner} lists.
	 */
	protected abstract List<List<? extends TranslatableEntryOwner<C, ?, ?>>> entryOwnersLists();
	
	/**
	 * List with queries for which the results will be loaded in the cache.
	 * 
	 * The queries must be {@link EntitiesQuery} with entities that implement
	 * the {@link TypedSelectable} interface.
	 * 
	 * @return Returns a list of queries.
	 */
	protected abstract List<EntitiesQuery<? extends TypedSelectable<C>>> systemListEntriesQueries();
	
	
	@Override
	public void execute() throws LogicException, DataException {
		putEnumerationsInCache();
		loadQueriesOperation.execute();
	}

}
