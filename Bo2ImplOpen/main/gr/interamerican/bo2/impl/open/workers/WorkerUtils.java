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
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Rule;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.adapters.Transformation;

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
	 * Fetches all results of an {@link EntitiesQuery} query as a list of a 
	 * specific sub-type of the query's return type.
	 * <br/>
	 * This is unsafe and should be used with caution.
	 * 
	 * @param query
	 *        Query who's results are put in the list.
	 * @param <A>
	 *        Type of entities returned by the query.
	 * @param <B>
	 *        Type of entities to downcast to.
	 * 
	 * @return Returns a list that contains all objects fetched by the 
	 *         query.
	 * @throws DataAccessException
	 */
	public static <A, B extends A> List<B> 
	queryResultsAsConvertedList(EntitiesQuery<A> query)
	throws DataAccessException {		
		List<A> list = queryResultsAsList(query);
		List<B> casted = CollectionUtils.convert(list);
		return casted;
	}
	
	/**
	 * Fetches all results of an {@link EntitiesQuery} query to a list.
	 * Each result is first transformed based on a provided {@link Transformation}
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
	queryTransformedResultsAsList(EntitiesQuery<P> query, Transformation<P, T> adapter)
	throws DataAccessException {		
		List<T> list = new ArrayList<T>();
		while (query.next()) {
			P p = query.getEntity();
			list.add(adapter.execute(p));
		}
		return list;
	}
	
	/**
	 * Instantiates and executes an operation.
	 * 
	 * This method does the following:
	 * <ul><li> Instantiates the operation using {@link Factory}. </li>
	 *     <li> Copies all common properties from the specified input object
	 *          to the operation. </li>
	 *     <li> Initializes the operation. </li>
	 *     <li> Opens the operation. </li>
	 *     <li> Executes the operation. </li>
	 *     <li> Closes the operation. </li> </ul>
	 *     
	 * 
	 * @param className
	 *        Name of operation class.
	 * @param provider
	 *        Provider used to initialize the operation. 
	 * @param inputs
	 *        Object used to copy any input properties to the operation.
	 *        
	 * @throws LogicException
	 * @throws DataException
	 */
	public static void execute(String className, Provider provider, Object inputs) 
	throws LogicException, DataException {
		Operation op = create(className, provider, inputs);
		op.open();
		op.execute();
		op.close();
	}
	
	/**
	 * Instantiates and executes an {@link Operation}.
	 * 
	 * This method does the following:
	 * <ul><li> Instantiates the operation using {@link Factory}. </li>
	 *     <li> Initializes the operation. </li>
	 *     <li> Opens the operation. </li>
	 *     <li> Executes the operation. </li>
	 *     <li> Closes the operation. </li> </ul>
	 *     
	 * 
	 * @param className
	 *        Name of operation class.
	 * @param provider
	 *        Provider used to initialize the operation.
	 *        
	 * @throws LogicException
	 * @throws DataException
	 */
	public static void execute(String className, Provider provider) 
	throws LogicException, DataException {
		execute(className, provider, null);
	}
	
	/**
	 * Instantiates and applies a {@link Rule}.
	 * 
	 * This method does the following:
	 * <ul><li> Instantiates the rule using {@link Factory}. </li>
	 *     <li> Copies all common properties from the specified input object
	 *          to the rule. </li>
	 *     <li> Initializes the rule. </li>
	 *     <li> Opens the rule. </li>
	 *     <li> Executes the rule. </li>
	 *     <li> Closes the rule. </li> </ul>
	 *     
	 * 
	 * @param className
	 *        Name of rule class.
	 * @param provider
	 *        Provider used to initialize the rule. 
	 * @param inputs
	 *        Object used to copy any input properties to the rule.
	 *        
	 * @throws LogicException
	 * @throws DataException
	 */
	public static void apply(String className, Provider provider, Object inputs) 
	throws LogicException, DataException {
		Rule op = create(className, provider, inputs);
		op.open();
		op.apply();
		op.close();
	}
	
	/**
	 * Instantiates and applies a {@link Rule}.
	 * 
	 * This method does the following:
	 * <ul><li> Instantiates the rule using {@link Factory}. </li>
	 *     <li> Initializes the rule. </li>
	 *     <li> Opens the rule. </li>
	 *     <li> Executes the rule. </li>
	 *     <li> Closes the rule. </li> </ul>
	 *     
	 * 
	 * @param className
	 *        Name of rule class.
	 * @param provider
	 *        Provider used to initialize the rule. 
	 *        
	 * @throws LogicException
	 * @throws DataException
	 */
	public static void apply(String className, Provider provider) 
	throws LogicException, DataException {
		apply(className, provider, null);
	}
	
	/**
	 * Creates and initializes a Worker.
	 * 
	 * @param className
	 * @param p
	 * @return Returns the worker.
	 */
	static <W extends Worker> W create(String className, Provider p, Object inputs) {		
		try {
			@SuppressWarnings("unchecked")
			W w = (W) Factory.create(className);
			w.init(p);
			if (inputs!=null) {
				ReflectionUtils.copyProperties(inputs, w);				
			}			
			return w;
		} catch (InitializationException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	 
	

}
