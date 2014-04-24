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
package gr.interamerican.wicket.bo2.protocol.http;

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.impl.open.streams.StreamsProvider;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.utils.Bo2DeploymentParams;
import gr.interamerican.bo2.utils.beans.Timer;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Maintains the Bo2 context associated with a web Request-Response cycle. This includes
 * logging and {@link Worker}s opened in the cycle.
 * <br/>
 * This class also serves as a facade for transparently performing operations on {@link Worker}s
 * on web layer code.
 * <br/>
 * Each wicket request cycle creates a {@link Bo2WicketRequestCycle} object that is associated
 * with the current thread. Its facilities are available through public static methods that
 * mutate the thread local instance.
 * </br>
 * The naming is such for legacy reasons. TODO: rename to Bo2WicketRequestCycleContext
 */
public class Bo2WicketRequestCycle {
	
	/**
	 * Threadlocal context
	 */
	private static ThreadLocal<Bo2WicketRequestCycle> CONTEXT = new ThreadLocal<Bo2WicketRequestCycle>();
	
	/**
	 * logger.
	 */
	static final Logger LOGGER = LoggerFactory.getLogger(Bo2WicketRequestCycle.class.getName());

	/**
	 * RequestCycleStats.
	 */
	static RequestCycleStats stats = new RequestCycleStats();
	
	/**
	 * Gets the {@link Bo2WicketRequestCycle} instance for the current thread.
	 * 
	 * @return Bo2WicketRequestCycle.
	 */
	public static Bo2WicketRequestCycle get() {
		if(CONTEXT.get()==null) {
			CONTEXT.set(new Bo2WicketRequestCycle());
		}
		return CONTEXT.get();
	}
	
	/**
	 * Removes the threadlocal entry for the current thread.
	 * This should be called when the {@link Bo2WicketRequestCycle}
	 * instance is no longer useful.
	 */
	static void release() {
		CONTEXT.remove();
	}
	
	/**
	 * Calls <code>onBeginRequest()</code> on the specified cycle.
	 * 
	 * Exposes the protected method. Should be used only by test
	 * classes in order to emulate the wicket request-response cycle.
	 * 
	 * @param cycle
	 *        {@link RequestCycle} on which the method is 
	 *        invoked.
	 */
	public static void beginRequest(RequestCycle cycle) {
		cycle.getListeners().onBeginRequest(cycle);
	}
	
	/**
	 * Calls <code>onEndRequest()</code> on the specified cycle.
	 * 
	 * Exposes the protected method. Should be used only by test
	 * classes in order to emulate the wicket request-response cycle.
	 * 
	 * @param cycle
	 *        {@link RequestCycle} on which the method is 
	 *        invoked.
	 */
	public static void endRequest(RequestCycle cycle) {
		cycle.getListeners().onEndRequest(cycle);
	}

	/**
	 * Creates an object using the default Bo2 Factory.
	 * 
	 * @param clazz  Class of object.
	 * @param <P> Type defined by the class <code>clazz</code>.
	 * 
	 * @return Returns an instance of P.
	 */
	public static final <P> P create(Class<P> clazz) {
		return Factory.create(clazz);
	}
	
	/**
	 * Initializes and opens the specified worker.
	 * 
	 * @param w Worker to init and open.
	 */
	public static void initAndOpen(Worker w) {
		try {
			w.init(get().getProvider());
			w.open();
			get().workers.add(w);
		} catch (InitializationException ie) {
			throw new RuntimeException(ie);
		} catch (DataException de) {
			throw new RuntimeException(de);
		}
	}

	/**
	 * Gets an open {@link Worker} object of a specified type.
	 * 
	 * Any Exception thrown during the operation is wrapped inside a
	 * {@link RuntimeException}.
	 * 
	 * @param clazz Class of worker.
	 * @param <W> Type of worker defined by <code>clazz</code>.
	 * 
	 * @return Returns a new open instance of W.
	 */
	public static final <W extends Worker> W open(Class<W> clazz) {
		W w = Factory.create(clazz);
		initAndOpen(w);
		return w;
	}

	/**
	 * Gets an open {@link PersistenceWorker} for {@link PersistentObject}
	 * objects of type P.
	 * 
	 * Any Exception thrown during the operation is wrapped inside a
	 * {@link RuntimeException}.
	 * 
	 * @param clazz Class of {@link PersistentObject}.
	 * @param <P> Type of {@link PersistentObject} defined by
	 *        <code>clazz</code>.
	 * 
	 * @return Returns a new open PersistenceWorker for P objects.
	 */
	public static final <P extends PersistentObject<?>>
	PersistenceWorker<P> openPw(Class<P> clazz) {
		PersistenceWorker<P> w = Factory.createPw(clazz);
		initAndOpen(w);
		return w;
	}

	/**
	 * Executes an operation.
	 * 
	 * Any Exception thrown during the operation is wrapped inside a
	 * {@link RuntimeException}.
	 * 
	 * @param op Operation to execute.
	 */
	public static final void execute(Operation op) {
		initAndOpen(op);
		try {
			op.execute();
		} catch (DataException de) {
			throw new RuntimeException(de);
		} catch (LogicException le) {
			throw new RuntimeException(le);
		}
	}


	/**
	 * Gets the default NamedStreamsProvider defined in the deployment
	 * properties.
	 * 
	 * @return Returns the default NamedStreamsProvider.
	 * 
	 * @throws InitializationException
	 */
	public static final NamedStreamsProvider getDefaultNamedStreamsProvider()
	throws InitializationException {
		Bo2DeploymentParams depl =Bo2.getDefaultDeployment().getDeploymentBean();
		String nspName = depl.getStreamsManagerName();
		NamedStreamsProvider nsp = provider().getResource(nspName, NamedStreamsProvider.class);
		return nsp;
	}

	/**
	 * Gets the default NamedStreamsProvider defined in the deployment
	 * properties.
	 * 
	 * @return Returns the default NamedStreamsProvider.
	 * 
	 * @throws InitializationException
	 */
	public static final StreamsProvider getDefaultStreamsProvider()
	throws InitializationException {
		Bo2DeploymentParams depl = Bo2.getDefaultDeployment().getDeploymentBean();
		String nspName = depl.getStreamsManagerName();
		StreamsProvider sp = provider().getResource(nspName, StreamsProvider.class);
		return sp;
	}

	/**
	 * This method will reattach a dettached object to the current hibernate
	 * session.
	 * 
	 * The method is a facade that hides the hibernate session. If the current
	 * deployment does not support hibernate, the method will do nothing.
	 * If the specified object was not managed by hibernate, then nothing will
	 * happen. Only if the specified object was managed by hibernate, will it
	 * be attached to the session. This behavior guarantees that the application
	 * will run even if the specified object's persistence worker, changes and is
	 * not longer based on hibernate.
	 * 
	 * @param object
	 *        The object to re-attach
	 */
	public static final void reattach(Object object) {
		PoUtils.reattach(object, provider());
	}
	
	/**
	 * This method will reattach a dettached object to the current hibernate
	 * session. In the unit of work that this is called, it is mandatory to perform
	 * a database update.
	 * 
	 * 
	 * @param object
	 *        The object to re-attach
	 */
	public static final void reattachForUpdate(Object object) {
		PoUtils.reattachForUpdate(object, provider());
	}

	/**
	 * Gets the provider of the {@link Bo2WicketRequestCycle} associated
	 * with the current thread.
	 * 
	 * @return Returns the provider.
	 */
	public static final Provider provider() {
		return get().getProvider();
	}

	/*
	 * INSTANCE FIELDS AND METHODS
	 */
	
	/**
	 * Resource manager for the operation.
	 */
	Provider provider;

	/**
	 * Workers managed by this request cycle.
	 */
	List<Worker> workers = new ArrayList<Worker>();

	/**
	 * Indicates if an error has happened in the current cycle.
	 */
	boolean error = false;

	/**
	 * Timer.
	 */
	Timer timer;

	/**
	 * Marks the specified object as being saved only when explicitly defined
	 * by its persistent worker.
	 * 
	 * This method is used in order to prevent unintentionally saving changes
	 * to the specified. This could happen if the underlying persistence worker
	 * of the specified object is based on hibernate. Hibernate will flush all
	 * changes to any managed object whenever flush is called. This method will
	 * result in the specified object being excluded from flush. Calling this
	 * method will also change the flushing policy of the current cycle's
	 * HibernateSessionProvider to FlushStrategy#EXCLUDING. <br/>
	 * If the current deployment does not support hibernate or the specified
	 * object is not managed by hibernate, then this method will have no effect.
	 * 
	 * @param object
	 *        Object to mark.
	 */
	public void markExplicitSave(Object object) {
		DetachStrategy strategy = PoUtils.getDetachStrategy(object);
		if (strategy!=null) {
			strategy.markExplicitSave(object, getProvider());
		}
	}

	/**
	 * Closes any workers opened in this cycle and the provider.
	 * 
	 * @throws DataException
	 */
	void cleanup() throws DataException {
		closeWorkers();
		provider.close();
	}

	/**
	 * Closes any worker opened during this cycle.
	 * 
	 * @throws DataException
	 */
	private void closeWorkers() throws DataException {
		for (Worker worker : workers) {
			worker.close();
		}
		workers.clear();
	}

	/**
	 * Gets the provider.
	 *
	 * @return Returns the provider
	 */
	public Provider getProvider() {
		return provider;
	}

	/**
	 * Creates a new Bo2WicketRequestCycle object.
	 */
	private Bo2WicketRequestCycle() { /* empty */ }
	
}

