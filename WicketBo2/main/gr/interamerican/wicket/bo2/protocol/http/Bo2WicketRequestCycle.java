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

import static gr.interamerican.bo2.utils.StringConstants.COMMA;
import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.CouldNotBeginException;
import gr.interamerican.bo2.arch.exceptions.CouldNotCommitException;
import gr.interamerican.bo2.arch.exceptions.CouldNotRollbackException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.Bo2ArchUtils;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.impl.open.streams.StreamsProvider;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.utils.Bo2DeploymentParams;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.beans.Timer;
import gr.interamerican.wicket.def.WicketOutputMedium;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.Page;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebRequestCycle for Bo2 wicket projects.
 * 
 * This implementation of WebRequestCycle creates a transaction
 * for each request. A {@link TransactionManager} is used for that.
 * The WebRequestCycle offers a {@link Provider} property that can 
 * be used by any {@link Worker}.
 */
@SuppressWarnings("nls")
public class Bo2WicketRequestCycle extends WebRequestCycle {
	
	/**
	 * Calls <code>onBeginRequest()</code> on the specified cycle.
	 * 
	 * Exposes the protected method. Should be used only by test
	 * classes in order to emulate the wicket request-response cycle.
	 * 
	 * @param cycle
	 *        {@link Bo2WicketRequestCycle} on which the method is 
	 *        invoked.
	 */
	public static void beginRequest(Bo2WicketRequestCycle cycle) {
		cycle.onBeginRequest();
	}
	
	/**
	 * Calls <code>onEndRequest()</code> on the specified cycle.
	 * 
	 * Exposes the protected method. Should be used only by test
	 * classes in order to emulate the wicket request-response cycle.
	 * 
	 * @param cycle
	 *        {@link Bo2WicketRequestCycle} on which the method is 
	 *        invoked.
	 */
	public static void endRequest(Bo2WicketRequestCycle cycle) {
		cycle.onEndRequest();
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
		NamedStreamsProvider nsp = 
			provider().getResource(nspName, NamedStreamsProvider.class);
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
		PoUtils.reattach(object, Bo2WicketRequestCycle.get().getProvider());		
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
	
	
	public static Bo2WicketRequestCycle get() {
		return (Bo2WicketRequestCycle) RequestCycle.get();
	}
	
	/**
	 * logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(Bo2WicketRequestCycle.class);
	
	/**
	 * RequestCycleStats.
	 */
	private static RequestCycleStats stats = new RequestCycleStats();
	
	/**
	 * Resource manager for the operation.
	 */
	private Provider provider;
		
	/**
	 * Workers managed by this request cycle. 
	 */
	private List<Worker> workers = new ArrayList<Worker>();
	
	/**
	 * Names of workers.
	 */
	private String workerNames = null;
	
	/**
	 * Indicates if an error has happened in the current cycle.
	 */
	private boolean error = false;
	
	/**
	 * Timer.
	 */
	private Timer timer;
	

	
	/**
	 * Creates a new Bo2WicketRequestCycle object. 
	 *
	 * @param application
	 * @param request
	 * @param response
	 */
	public Bo2WicketRequestCycle
	(WebApplication application, WebRequest request, Response response) {
		super(application, request, response);
	}
	
	@Override
	protected void onRequestTargetSet(IRequestTarget requestTarget) {
		super.onRequestTargetSet(requestTarget);
		/*
		 * The requestTarget has not yet been added on the RequestCycle
		 * stack. For this reason we have to pass the target manually to
		 * the page.
		 */
		if(requestTarget instanceof AjaxRequestTarget) {
			AjaxRequestTarget target = (AjaxRequestTarget) requestTarget;
			Page page = target.getPage();
			if(page instanceof WicketOutputMedium) {
				WicketOutputMedium outputMedium = (WicketOutputMedium) page;
				outputMedium.clearMessages(target);
			}
		}
	}
	
	@Override
	protected void onBeginRequest() {
		stats.newCycle();
		try {
			timer = new Timer();
			Session<?, ?> session = Bo2WicketSession.get();
			Bo2Session.setSession(session);
			provider = Bo2.getDefaultDeployment().getProvider();
			Bo2Session.setProvider(provider);
			TransactionManager manager = provider.getTransactionManager();
			if(manager!=null) {
				manager.begin();
				debug("started the transaction manager " + this.toString());
			}
		} catch (CouldNotBeginException e) {
			throw new RuntimeException(e);			
		} catch (InitializationException e) {
			throw new RuntimeException(e);
		}
		super.onBeginRequest();
	}
	
	@Override
	protected void onEndRequest() {
		/*
		 * If an error has occurred, there is nothing to commit,
		 * as the transaction has already rolled back and the
		 * workers and the provider have been closed.
		 */
		if(!error) {
			try {
				TransactionManager manager = provider.getTransactionManager();
				if(manager!=null) {
					manager.commit();
					debug("committed the transaction.");
				}
			} catch (CouldNotCommitException cnce) {
				logger.error("CouldNotCommitException: " + ExceptionUtils.getThrowableStackTrace(cnce));
				throw new RuntimeException(cnce);		
			} finally {
				try {
					cleanup();
				}catch (DataException de) {
					logger.error("DataException on cleanup: " + ExceptionUtils.getThrowableStackTrace(de));
					throw new RuntimeException(de);		
				}
			}
		} else {			
			debug("Nothing to do, an error was registered in the cycle.");			
		}
		super.onEndRequest();
		logAndCleanSession();
	}
	
	@Override
	public Page onRuntimeException(Page page, RuntimeException e) {
		Throwable t = Bo2ArchUtils.unwrap(e);
		stats.updateExceptionStats(t);
		error = true;
		try {
			TransactionManager manager = provider.getTransactionManager();
			if(manager!=null) {
				manager.rollback();				
				debug("rolled back the transaction.");				
			}
		} catch (CouldNotRollbackException cnrbex) {
			cnrbex.setInitial(t);
			logger.error("CouldNotRollbackException: " + ExceptionUtils.getThrowableStackTrace(cnrbex));
		} finally {
			try {
				cleanup();
			} catch (DataException de) {
				de.initCause(t);
				logger.error("DataException on cleanup: " + ExceptionUtils.getThrowableStackTrace(de));
			}
		}
		/*
		 * If an invocation target exception is somewhere in the chain,
		 * use its (non null) target to render the error stack trace.
		 */
		if(ExceptionUtils.isCausedBy(t, InvocationTargetException.class)) {
			InvocationTargetException itex = ExceptionUtils.causeInTheChain(t, InvocationTargetException.class);
			t = Utils.notNull(itex.getTargetException(), t);
		}
		
		if (page instanceof WicketOutputMedium) {
			WicketOutputMedium outputMedium = (WicketOutputMedium) page;
			IRequestTarget target = getRequestTarget(); 
			if(target instanceof AjaxRequestTarget) {
				AjaxRequestTarget art = (AjaxRequestTarget) target;
				outputMedium.showError(t, art);
			} else {
				logger.error("Failed to display error from Throwable " + t.toString());
			}
			logAndCleanSession();
			return page;
		} else {
			logAndCleanSession();			
			return superOnRuntimeException(page, e);
		}
	}
	
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
			strategy.markExplicitSave(object, this.getProvider());
		}
	}
	
	/**
	 * Calls super.OnRuntimeException().
	 * 
	 * @param page
	 * @param e
	 * @return Returns the page returned by super.OnRuntimeException() 
	 */
	private Page superOnRuntimeException(Page page, RuntimeException e) {
		logger.error("UNEXPECTED EXCEPTION: " + ExceptionUtils.getThrowableStackTrace(e));
		/*
		 * TODO put the RuntimeException some place the ErrorPage can find it.
		 */
		Page errorPage = super.onRuntimeException(page, e);
		stats.logForDebugging(workerNames, timer);
		Bo2Session.setSession(null);
		return errorPage;
	}
	
	/**
	 * Closes any workers opened in this cycle and the provider.
	 * 
	 * @throws DataException 
	 */
	private void cleanup() throws DataException {		
		debug("performing cleanup.");			
		closeWorkers();
		provider.close();		
		debug("cleaned up.");
	}
	
	/**
	 * Closes any worker opened during this cycle.
	 * 
	 * @throws DataException 
	 */
	private void closeWorkers() throws DataException {	
		StringBuilder sb = new StringBuilder();
		for (Worker worker : workers) {
			worker.close();
			sb.append(worker.getClass().getSimpleName());
			sb.append(COMMA);
		}
		workers.clear();			
		this.workerNames = sb.toString();
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
	 * Cleanup and logging.
	 */
	void logAndCleanSession() {
		stats.logForDebugging(workerNames, timer);
		Bo2Session.setSession(null);
		Bo2Session.setProvider(null);
	}
	
	/**
	 * Writes a debug message through the logger.
	 * 
	 * @param msg
	 */
	void debug(String msg) {
		if (logger.isDebugEnabled()) {
			logger.debug(msg);
		}
	}
	

}

