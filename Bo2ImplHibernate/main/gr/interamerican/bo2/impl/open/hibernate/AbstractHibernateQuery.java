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
package gr.interamerican.bo2.impl.open.hibernate;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.utils.ext.WorkerExecutionDetails;
import gr.interamerican.bo2.utils.Debug;

import java.util.Iterator;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.HibernateIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Abstract implementation of {@link EntitiesQuery} based on Hibernate.
 * 
 * This class also provides an implementation of {@link Question}.
 * When used as a question, this class will give as an answer the 
 * first entity fetched by the query. If the query does not fetch
 * any entity, then the answer will be null.
 * 
 * After the results have been obtained by the user, i.e.:
 * - when <code>ask()</code> finishes execution
 * - when <code>next()</code> returns false
 * the {@link HibernateIterator} is closed explicitly.
 * 
 * @param <P> Concerning the implementation of {@link EntitiesQuery}, this is
 *            the type of entity fetched by each row.
 *            Concerning the implementation of the {@link Question} interface,
 *            this is the type of answer.
 */
public abstract class AbstractHibernateQuery<P> 
extends AbstractHibernateWorker 
implements EntitiesQuery<P>, Question<P>  {
	
	/**
	 * Logger.
	 */
	private static Logger logger = 
		LoggerFactory.getLogger(AbstractHibernateQuery.class);
	
	/**
	 * Results iterator.
	 */
	private Iterator<?> iterator;
	
	/**
	 * The iterating object.
	 */
	protected Object currentObject;
	
	/**
	 * Gets the current row.
	 */
	private int row=0;
	
	/**
	 * Answer to the question.
	 */
	private P answer;
	
	/**
	 * Creates the iterator of the Hibernate query.
	 * 
	 * @return Returns the iterator of the Hibernate query.
	 */
	protected abstract Iterator<?> createIterator();

	@Override
	public boolean next() throws DataAccessException {
		try {
			Debug.setActiveModule(this);
			if (iterator.hasNext()) {
				currentObject = iterator.next();
				row++;
				Debug.resetActiveModule();
				return true;
			}
			Debug.resetActiveModule();
			closeIterator();
			return false;
		} catch (HibernateException he) {
			logHibernateException(he);
			Debug.resetActiveModule();
			throw new DataAccessException(he);
		}
	}

	@Override
	public int getRow() throws DataAccessException {
		return row;
	}

	@Override
	public void setAvoidLock(boolean avoidLock) {
		/* empty */
	}

	@Override
	public boolean isAvoidLock() {
		return true;
	}

	@Override
	public final void execute() throws DataException {
		WorkerExecutionDetails details = new WorkerExecutionDetails();
		details.setTaskInfo("Executing a hibernate query"); //$NON-NLS-1$
		try {
			Debug.setActiveModule(this);
			row = 0;
			iterator = createIterator();
			Debug.resetActiveModule();
		} catch (HibernateException he) {
			logger.error(he.toString());
			Debug.resetActiveModule();
			throw new DataException(he);
		} finally {
			checkTransactionHealth(details);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public P getEntity() throws DataAccessException {
		register(currentObject);
		return (P) currentObject;
	}

	@Override
	public final void ask() throws DataException {
		this.answer = null;
		execute();
		if (next()) {
			answer = getEntity();
		}
		closeIterator();
	}

	@Override
	public P getAnswer() {
		return answer;
	}

	/**
	 * Closes the {@link HibernateIterator} of the result set.
	 */
	@SuppressWarnings("nls")
	private void closeIterator() {
		if (iterator instanceof HibernateIterator) {
			Debug.debug(logger, "Closing hibernate iterator. " + this.getClass().getName());
			Hibernate.close(iterator);
		}
	}
}