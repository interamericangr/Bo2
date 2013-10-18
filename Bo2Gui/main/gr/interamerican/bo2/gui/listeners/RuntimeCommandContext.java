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
package gr.interamerican.bo2.gui.listeners;

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
import gr.interamerican.bo2.arch.ext.OutputMedium;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.RuntimeCommand;
import gr.interamerican.bo2.impl.open.utils.Bo2;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>In applications that perform runtime commands on demand, such as web
 * applications and swing UIs, it is useful to provide similar facilities
 * to the ones provided by the {@link RuntimeCommand} for batch programs
 * to the agent that is responsible for performing the runtime commands
 * that are requested.
 * 
 * <p>Such an agent will typically include code
 * <pre>
 * RuntimeCommandContext.get().beginProcessing(); <br/>
 * try {
 *     ...
 *     RuntimeCommandContext.get().open(worker1);
 *     worker1.execute()
 *     ...
 * } catch {
 *     RuntimeCommandContext.get().onException(ex);
 * } finally {
 *     RuntimeCommandContext.get().endProcessing();
 * }
 * </pre>
 * 
 * <p>This implementation supports multiple threads issuing runtime commands
 * simultaneously. However, note that there is no provision for command queuing.
 * Each thread that issues a command has to wait its completion before issuing 
 * a new one.
 * 
 * TODO: move this to another bo2 project.
 * 
 * @see Worker
 * @see RuntimeCommand
 */
public class RuntimeCommandContext {
	
	/**
	 * ThreadLocal to implement static access on current thread.
	 */
	private static ThreadLocal<RuntimeCommandContext> threadLocal = new ThreadLocal<RuntimeCommandContext>();
	
	/**
	 * Provider.
	 */
	private Provider provider;
	
	/**
	 * {@link #onException(Throwable, OutputMedium)} has been invoked.
	 */
	private boolean error = false;
	
	/**
	 * Managed workers.
	 */
	private Set<Worker> managedWorkers = new HashSet<Worker>(); 
	
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
		try {
			W w = Factory.create(clazz);		
			w.init(get().getProvider());
			w.open();
			get().managedWorkers.add(w);
		return w;
		} catch (InitializationException ie) {
			throw new RuntimeException(ie);
		} catch (DataException de) {
			throw new RuntimeException(de);
		}
	}
	
	/**
	 * Initializes and opens a Worker.
	 * 
	 * Any Exception thrown during the operation is wrapped inside a 
	 * {@link RuntimeException}.
	 * 
	 * @param worker
	 * @param <W> Type of worker.
	 */
	public static final <W extends Worker> void open(W worker) {
		try {
			worker.init(get().getProvider());
			worker.open();
			get().managedWorkers.add(worker);
		} catch (InitializationException ie) {
			throw new RuntimeException(ie);
		} catch (DataException de) {
			throw new RuntimeException(de);
		}
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
		try {
			PersistenceWorker<P> pw = Factory.createPw(clazz);
			pw.init(get().getProvider());
			pw.open();
			get().managedWorkers.add(pw);
			return pw;
		} catch (InitializationException ie) {
			throw new RuntimeException(ie);
		} catch (DataException de) {
			throw new RuntimeException(de);
		}
	}
	
	/**
	 * Gets the threadLocal instance, creating it if necessary.
	 * 
	 * @return ThreadLocal {@link RuntimeCommandContext}.
	 */
	public static RuntimeCommandContext get() {
		if(threadLocal.get()==null) {
			threadLocal.set(new RuntimeCommandContext());	
		}
		return threadLocal.get();
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
	 * Begins processing.
	 */
	public void beginProcessing() {
		try {
			provider = Bo2.getDefaultDeployment().getProvider();
			Bo2Session.setProvider(provider);
			TransactionManager manager = provider.getTransactionManager();
			if(manager!=null) {
				manager.begin();
			}
		} catch (CouldNotBeginException e) {
			throw new RuntimeException(e);			
		} catch (InitializationException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Handles an exception during processing.
	 * @param t
	 * @param out
	 * 
	 * TODO: remove OutputMedium from this method?
	 * 
	 */
	public void onException(Throwable t, OutputMedium out) {
		error = true;
		
		/*
		 * possibly deprecate the OutputMedium mechanism.
		 */
		if(out!=null) {
			out.showError(t);
		} else {
			//TODO: Log the error
			t.printStackTrace();
		}
		
		try {
			TransactionManager manager = provider.getTransactionManager();
			if(manager!=null) {
				manager.rollback();
			}
		} catch (CouldNotRollbackException cnrbex) {
			cnrbex.setInitial(t);
			throw new RuntimeException(cnrbex);
		} finally {
			try {
				cleanup();
			} catch (DataException de) {
				throw new RuntimeException(de);
			}
		}
	}
	
	/**
	 * Ends processing.
	 */
	public void endProcessing() {
		threadLocal.remove();
		if(error) {
			return;
		}
		try {
			TransactionManager manager = provider.getTransactionManager();
			if(manager!=null) {
				manager.commit();
			}
		} catch (CouldNotCommitException de) {
			throw new RuntimeException(de);		
		} finally {
			try {
				cleanup();
			}catch (DataException de) {
				throw new RuntimeException(de);		
			}
		}
	}
	
	/**
	 * Cleans up resources opened during processing.
	 * @throws DataException
	 */
	private void cleanup() throws DataException{
		for(Worker w : managedWorkers) {
			w.close();
		}
		managedWorkers.clear();
		provider.close();
		provider = null;
	}

}
