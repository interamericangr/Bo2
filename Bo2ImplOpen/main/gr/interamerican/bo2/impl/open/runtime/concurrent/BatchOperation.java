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
package gr.interamerican.bo2.impl.open.runtime.concurrent;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.batch.ProcessStatus;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamDefinition;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.workers.WorkerUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * same idea as {@link gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess} but focused
 * on running a single operation.
 *
 * @param <T> the generic type
 */
public class BatchOperation<T> implements Runnable, ProcessStatus {

	/**
	 * Default logger for Bo2.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchOperation.class);
	/**
	 * Batch process parameters.
	 */
	OperationParm<T> parameters;

	/**
	 * Session.
	 */
	Session<?, ?> session;
	
	/** The is finished. */
	boolean isFinished = false;
	
	/** The is finished abnormaly. */
	boolean isFinishedAbnormaly = false;
	
	/** The trace. */
	String trace = StringConstants.EMPTY;

	/**
	 * default constructor.
	 *
	 * @param parameters the parameters
	 */
	public BatchOperation(OperationParm<T> parameters) {
		this.parameters = parameters;
		this.session = Bo2Session.getSession();
	}

	/**
	 * Registers the shared streams contained in the {@link BatchProcessParm}s.
	 * The manager for the streams is the default streams manager of the Bo2
	 * deployment.
	 * 
	 * @throws InitializationException the initialization exception
	 * @throws DataException
	 */
	void registerNamedStreams() throws InitializationException, DataException {
		Provider provider = Bo2.getProvider();
		Bo2Session.setProvider(provider);
		try {
		List<NamedStreamDefinition> namedStreamDefinitions = parameters.getNamedStreamDefinitions();
		if (!CollectionUtils.isNullOrEmpty(namedStreamDefinitions)) {
				String managerName = Bo2.getDefaultDeployment().getDeploymentBean().getStreamsManagerName();
				NamedStreamsProvider nsp = provider.getResource(managerName, NamedStreamsProvider.class);
			for (NamedStreamDefinition def : namedStreamDefinitions) {
				nsp.registerStreamDefinition(def);
			}
		}
		} finally {
			provider.close();
			Bo2Session.setProvider(null);
	}
	}

	/**
	 * Executes the Operation.
	 *
	 * @throws InitializationException the initialization exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 * @throws UnexpectedException the unexpected exception
	 */
	public void execute() throws InitializationException, DataException, LogicException,
	UnexpectedException {
		registerNamedStreams();
		WorkerUtils.executeOperation(parameters.getPreProcessing(),
				parameters.getPreOperationParametersSetter(), LOGGER);
		WorkerUtils.executeOperation(Factory.create(parameters.getOperationClass()),
				parameters.getOperationParametersSetter(), LOGGER);
		WorkerUtils.executeOperation(parameters.getPostProcessing(),
				parameters.getPostOperationParametersSetter(), LOGGER);
	}

	@Override
	public void run() {
		try {
			Bo2Session.setSession(session);
			execute();
		} catch (Exception e) {
			isFinishedAbnormaly = true;
			trace = ExceptionUtils.getThrowableStackTrace(e);
			e.printStackTrace();
		} catch (Error e) {
			isFinishedAbnormaly = true;
			trace = ExceptionUtils.getThrowableStackTrace(e);
			e.printStackTrace();
		} finally {
			Bo2Session.setSession(null);
			isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public boolean isFinishedAbnormally() {
		return isFinishedAbnormaly;
	}

	@Override
	public String getExceptionMessage() {
		return trace;
	}
}