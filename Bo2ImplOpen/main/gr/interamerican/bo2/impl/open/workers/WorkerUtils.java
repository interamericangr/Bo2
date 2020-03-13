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
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.RuntimeCommand;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.adapters.Transformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;

/**
 * Utilities relevant with {@link Worker} objects.
 *
 *
 */
public class WorkerUtils {

	/**
	 * Fetches all results of an {@link EntitiesQuery} query to a list.
	 *
	 * @param <P>        Type of entities returned by the query.
	 * @param query        Query who's results are put in the list.
	 * @return Returns a list that contains all objects fetched by the
	 *         query.
	 * @throws DataAccessException the data access exception
	 */
	public static <P> List<P> queryResultsAsList(EntitiesQuery<P> query)
			throws DataAccessException {
		return queryResultsToCollection(query, new ArrayList<P>());
	}

	/**
	 * Fetches all results of an {@link EntitiesQuery} query as a list of a
	 * specific sub-type of the query's return type.
	 * <br>
	 * This is unsafe and should be used with caution.
	 *
	 * @param <A>        Type of entities returned by the query.
	 * @param <B>        Type of entities to downcast to.
	 * @param query        Query who's results are put in the list.
	 * @return Returns a list that contains all objects fetched by the
	 *         query.
	 * @throws DataAccessException the data access exception
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
	 *
	 * @param <P>        Type of entities returned by the query.
	 * @param query        Query who's results are put in the list.
	 * @return Returns a list that contains all objects fetched by the
	 *         query.
	 * @throws DataAccessException the data access exception
	 */
	public static <P> Set<P> queryResultsAsSet(EntitiesQuery<P> query)
			throws DataAccessException {
		return queryResultsToCollection(query, new HashSet<P>());
	}

	/**
	 * Fetches all results of an {@link EntitiesQuery} query as a list of a
	 * specific sub-type of the query's return type.
	 * <br>
	 * This is unsafe and should be used with caution.
	 *
	 * @param <A>        Type of entities returned by the query.
	 * @param <B>        Type of entities to downcast to.
	 * @param query        Query who's results are put in the list.
	 * @return Returns a list that contains all objects fetched by the
	 *         query.
	 * @throws DataAccessException the data access exception
	 */
	public static <A, B extends A> Set<B>
	queryResultsAsConvertedSet(EntitiesQuery<A> query)
			throws DataAccessException {
		Set<A> set = queryResultsAsSet(query);
		Set<B> casted = CollectionUtils.convert(set);
		return casted;
	}

	/**
	 * Adds the entities fetched by a query to a collection.
	 *
	 * @param <P> the generic type
	 * @param <C> the generic type
	 * @param query the query
	 * @param collection the collection
	 * @return Returns the collection.
	 * @throws DataAccessException the data access exception
	 */
	public static <P, C extends Collection<P>> C
	queryResultsToCollection(EntitiesQuery<P> query, C collection)
			throws DataAccessException {
		while (query.next()) {
			collection.add(query.getEntity());
		}
		return collection;
	}

	/**
	 * Adds the entities fetched by a query to a collection.
	 *
	 * @param <P> the generic type
	 * @param <T> the generic type
	 * @param <C> the generic type
	 * @param query the query
	 * @param collection the collection
	 * @param adapter the adapter
	 * @return Returns the collection.
	 * @throws DataAccessException the data access exception
	 */
	public static <P, T, C extends Collection<T>> C
	queryTransformedResultsToCollection(EntitiesQuery<P> query, C collection, Transformation<P, T> adapter)
			throws DataAccessException {
		while (query.next()) {
			P p = query.getEntity();
			T t = adapter.execute(p);
			collection.add(t);
		}
		return collection;
	}

	/**
	 * Fetches all results of an {@link EntitiesQuery} query to a list.
	 * Each result is first transformed based on a provided {@link Transformation}
	 * adapter.
	 *
	 * @param <P>        Type of entities returned by the query.
	 * @param <T>        Type of transformed object.
	 * @param query        Query who's results are put in the list.
	 * @param adapter        Adapter that transforms
	 * @return Returns a list that contains all transformed objects fetched by the
	 *         query.
	 * @throws DataAccessException the data access exception
	 */
	public static <P,T> List<T>
	queryTransformedResultsAsList(EntitiesQuery<P> query, Transformation<P, T> adapter)
			throws DataAccessException {
		return queryTransformedResultsToCollection(query, new ArrayList<T>(), adapter);
	}

	/**
	 * Fetches all results of an {@link EntitiesQuery} query to a set.
	 * Each result is first transformed based on a provided {@link Transformation}
	 * adapter.
	 *
	 * @param <P>        Type of entities returned by the query.
	 * @param <T>        Type of transformed object.
	 * @param query        Query who's results are put in the set.
	 * @param adapter        Adapter that transforms
	 * @return Returns a list that contains all transformed objects fetched by the
	 *         query.
	 * @throws DataAccessException the data access exception
	 */
	public static <P,T> Set<T>
	queryTransformedResultsAsSet(EntitiesQuery<P> query, Transformation<P, T> adapter)
			throws DataAccessException {
		return queryTransformedResultsToCollection(query, new HashSet<T>(), adapter);
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
	 * @param className        Name of operation class.
	 * @param provider        Provider used to initialize the operation.
	 * @param inputs        Object used to copy any input properties to the operation.
	 * @throws LogicException the logic exception
	 * @throws DataException the data exception
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
	 * @param className        Name of operation class.
	 * @param provider        Provider used to initialize the operation.
	 * @throws LogicException the logic exception
	 * @throws DataException the data exception
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
	 * @param className        Name of rule class.
	 * @param provider        Provider used to initialize the rule.
	 * @param inputs        Object used to copy any input properties to the rule.
	 * @throws LogicException the logic exception
	 * @throws DataException the data exception
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
	 * @param className        Name of rule class.
	 * @param provider        Provider used to initialize the rule.
	 * @throws LogicException the logic exception
	 * @throws DataException the data exception
	 */
	public static void apply(String className, Provider provider)
			throws LogicException, DataException {
		apply(className, provider, null);
	}

	/**
	 * Creates and initializes a Worker.
	 *
	 * @param <W> the generic type
	 * @param className the class name
	 * @param p the p
	 * @param inputs the inputs
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

	/**
	 * Execute an operation inside a RuntimeCommand.
	 *
	 * @param op            Operation to execute.
	 *            Can be null.
	 * @param parametersSetter            Modification that sets the input parameters to the
	 *            operation. Can be null.
	 * @param logger the logger
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 * @throws UnexpectedException the unexpected exception
	 */
	public static void executeOperation(Operation op, Modification<Object> parametersSetter,
			Logger logger) throws DataException, LogicException, UnexpectedException {
		if (op != null) {
			if (parametersSetter != null) {
				parametersSetter.execute(op);
			}
			if (logger != null) {
				logger.info("Executing " + op.getClass().getName()); //$NON-NLS-1$
			}
			RuntimeCommand cmd = new RuntimeCommand(op);
			cmd.execute();
		}
	}
}
