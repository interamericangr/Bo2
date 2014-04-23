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
package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link AbstractBo2RuntimeCmd} is an {@link RuntimeCommand} that  
 * can be implemented without defining an {@link Operation}.
 * 
 * The class has an operation that wraps its abstract <code>work()</code>.
 * method. It is possible to create Bo2 objects of any type as 
 * {@link PersistentObject}, {@link PersistenceWorker} and any other 
 * {@link Worker} type inside the <code>work()</code> method. The default
 * object factories of Bo2 are used for object creation. Workers created 
 * by the <code>open(clazz)</code> and <code>openPw(clazz)</code> methods
 * live as children of the operation.  
 * 
 */
public abstract class AbstractBo2RuntimeCmd
extends RuntimeCommand {
	
	/**
	 * Creates a new AbstractBo2RuntimeCmd object. 
	 */
	public AbstractBo2RuntimeCmd() {		
		this(null);
	}
	
	/**
	 * Creates a new AbstractBo2RuntimeCmd object.
	 *  
	 * @param deploymentPath 
	 *        Path to the resource file that contains the deployment
	 *        properties.
	 */
	public AbstractBo2RuntimeCmd(String deploymentPath) {		
		super(deploymentPath);
		operation = new RuntimeLayerAdapter();
	}
	
	/**
	 * Creates an object using the default Bo2 Factory.
	 * 
	 * @param clazz  Class of object.
	 * @param <P> Type defined by the class <code>clazz</code>.
	 * 
	 * @return Returns an instance of P.
	 */
	protected final <P> P create(Class<P> clazz) {
		return Factory.create(clazz);
	}
	
	/**
	 * Gets an open {@link PersistenceWorker} for {@link PersistentObject}
	 * objects of type P.
	 * 
	 * @param clazz Class of {@link PersistentObject}.
	 * @param <P> Type of {@link PersistentObject} defined by 
	 *        <code>clazz</code>.
	 * 
	 * @return Returns a new open PersistenceWorker for P objects.
	 * 
	 * @throws InitializationException
	 * @throws DataException
	 */
	protected final <P extends PersistentObject<?>> 
	PersistenceWorker<P> openPw(Class<P> clazz) 
	throws InitializationException, DataException {
		PersistenceWorker<P> pw = Factory.createPw(clazz);
		initAndOpen(pw);
		return pw;
	}
	
	/**
	 * Gets an open {@link Worker} object of a specified type.
	 * 
	 * @param clazz Class of worker.
	 * @param <W> Type of worker defined by <code>clazz</code>.
	 * 
	 * @return Returns a new open instance of W.
	 * 
	 * @throws InitializationException
	 * @throws DataException
	 */
	protected final <W extends Worker> W open(Class<W> clazz)
	throws InitializationException, DataException {
		W w = Factory.create(clazz);
		return open(w);
	}
	
	/**
	 * Schedules a job. The job will be submitted if and only if
	 * this unit of work commits successfully.
	 * 
	 * @param description
	 * 
	 * @param synchronous 
	 *        If this is true, the scheduler will wait for the jobs to finish
	 */
	protected final void schedule(JobDescription description, boolean synchronous) {
		if(synchronous) {
			((RuntimeLayerAdapter)operation).synchronousJobs.add(description);
		} else {
			((RuntimeLayerAdapter)operation).jobs.add(description);
		}
	}

	/**
	 * Gets an open {@link Worker} object of a specified instance.
	 * 
	 * @param w
	 * @return w
	 * @throws InitializationException
	 * @throws DataException
	 */
	protected final <W extends Worker> W open(W w) throws InitializationException, DataException {
		initAndOpen(w);
		return w;
	}
	
	/**
	 * Initializes and opens a worker.
	 * 
	 * @param w Worker.
	 * 
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	private void initAndOpen(Worker w) 
	throws InitializationException, DataException {
		w.init(getProvider());
		w.open();
		((RuntimeLayerAdapter)operation).workers.add(w);
	}
	
	/**
	 * main method.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws InitializationException
	 * @throws UnexpectedException
	 */
	public abstract void work() 
	throws LogicException, DataException, 
	InitializationException, UnexpectedException;
	
	/**
	 * Operation class for this {@link RuntimeCommand}.
	 */
	private class RuntimeLayerAdapter
	extends AbstractOperation {
		
		/**
		 * Workers created in this uow
		 */
		List<Worker> workers = new ArrayList<Worker>();
		
		/**
		 * Jobs scheduled in this uow
		 */
		List<JobDescription> jobs = new ArrayList<JobDescription>();
		
		/**
		 * Jobs scheduled in this uow that must be executed synchronously.
		 */
		List<JobDescription> synchronousJobs = new ArrayList<JobDescription>();
	
		@Override
		public void close() throws DataException {
			for (Worker worker : workers) {
				worker.close();
			}
			super.close();
		}
	
		@Override
		public void execute() throws LogicException, DataException {
			try {
				work();
			} catch (InitializationException e) {
				throw new DataException(e);
			} catch (UnexpectedException e) {
				Throwable cause = e.getCause();
				throw new DataException(cause);
			}
		}
	}
	
	


	

}
