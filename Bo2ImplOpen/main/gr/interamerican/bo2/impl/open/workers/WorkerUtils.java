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
package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.utils.adapters.AnyOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities relevant with {@link Worker} objects.
 * 
 * 
 */
public class WorkerUtils {
	
	/**
	 * Fetches all results of an {@link EntitiesQuery} query to a list.
	 * 
	 * @param query
	 *        Query who's results are put in the list.
	 * @param <P>
	 *        Type of entities returned by the query.
	 * 
	 * @return Returns a list that contains all objects fetched by the 
	 *         query.
	 * @throws DataAccessException
	 */
	public static <P> List<P> 
	queryResultsAsList(EntitiesQuery<P> query)
	throws DataAccessException {		
		List<P> list = new ArrayList<P>();
		while (query.next()) {
			list.add(query.getEntity());
		}
		return list;
	}
	
	/**
	 * Fetches all results of an {@link EntitiesQuery} query to a list.
	 * Each result is first transformed based on a provided {@link AnyOperation}
	 * adapter.
	 * 
	 * @param query
	 *        Query who's results are put in the list.
	 * @param adapter
	 *        Adapter that transforms 
	 * @param <P>
	 *        Type of entities returned by the query.
	 * @param <T> 
	 *        Type of transformed object.
	 * 
	 * @return Returns a list that contains all transformed objects fetched by the 
	 *         query.
	 * @throws DataAccessException
	 */
	public static <P,T> List<T> 
	queryTransformedResultsAsList(EntitiesQuery<P> query, AnyOperation<P, T> adapter)
	throws DataAccessException {		
		List<T> list = new ArrayList<T>();
		while (query.next()) {
			P p = query.getEntity();
			list.add(adapter.execute(p));
		}
		return list;
	}
	

}
