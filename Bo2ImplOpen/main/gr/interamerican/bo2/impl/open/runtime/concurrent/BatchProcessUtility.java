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

import static gr.interamerican.bo2.impl.open.runtime.monitor.MonitoringConstants.MONITOR_KEY;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.attributes.ModifiableByProperties;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;
import gr.interamerican.bo2.utils.runnables.Monitor;
import gr.interamerican.bo2.utils.runnables.MonitoringOperation;

/**
 * Utility for {@link BatchProcess} operations.
 *
 */
public class BatchProcessUtility {

	/**
	 * Session.
	 */
	Session<?,?> session;

	/**
	 * Creates a new BatchProcessUtility.
	 *
	 * @param session the session
	 */
	public BatchProcessUtility(Session<?,?> session) {
		super();
		this.session = session;
	}

	/**
	 * Creates a {@link BatchProcessParmsFactory}.
	 *
	 * @param properties the properties
	 * @return Returns the {@link BatchProcessParmsFactory}.
	 */
	public BatchProcessParmsFactory getFactory(Properties properties) {
		String className = properties.getProperty(BatchProcessParmNames.PARAMETERS_FACTORY_CLASS);
		if (StringUtils.isNullOrBlank(className)) {
			return new BatchProcessParmFactoryImpl();
		}
		Object factory = Factory.getCurrentFactory().create(className);
		return (BatchProcessParmsFactory) factory;
	}

	/**
	 * Creates a new {@link BatchProcess} and starts a new thread for it.
	 * 
	 * This method also sets the session of this {@link BatchProcessUtility} as the thread local
	 * {@link Bo2Session}. The new {@link BatchProcess} gets a reference to this session, which will
	 * be passed to the thread local
	 * session of the BatchProcess thread.
	 *
	 * @param batch the batch
	 * @return the thread created
	 */
	public Thread startBatchProcess(BatchProcess<?> batch) {
		long interval = batch.getInitialThreads() * 10;
		Thread t = new Thread(batch);
		t.start();
		ThreadUtils.sleepMillis(interval);
		return t;
	}

	/**
	 * Creates a new {@link BatchProcess} and starts a new thread for it.
	 * 
	 * This method also sets the session of this {@link BatchProcessUtility}
	 * as the thread local {@link Bo2Session}. The new {@link BatchProcess}
	 * gets a reference to this session, which will be passed to the thread local
	 * session of the BatchProcess thread.
	 *
	 * @param properties the properties
	 * @return Returns the {@link BatchProcess}.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BatchProcess createBatchProcess(Properties properties) {
		Bo2Session.setSession(session); //Set the session to the main thread of the batch process.
		BatchProcessParmsFactory factory = getFactory(properties);
		BatchProcessParm bpi = factory.createParameter(properties);
		BatchProcess batch = new BatchProcess(bpi);
		return batch;
	}

	/**
	 * Creates a monitoring for a batch process.
	 *
	 * @param batch
	 *        Batch process
	 * @param properties
	 *        Batch process properties
	 *
	 *	@return Returns the monitor.
	 */
	public Monitor<LongProcess> createMonitor(BatchProcess<?> batch, Properties properties) {
		return createMonitor(batch, properties, null);
	}

	/**
	 * Creates a monitoring for a batch process.
	 *
	 * @param batch
	 * @param properties
	 * @param extraMonitoringOperation
	 * @return Returns the monitor.
	 */
	public Monitor<LongProcess> createMonitor(BatchProcess<?> batch, Properties properties,
			Set<MonitoringOperation<LongProcess>> extraMonitoringOperation) {
		Monitor<LongProcess> monitor = new Monitor<LongProcess>(batch, LongProcess::isFinished);
		Set<String> classes = getMonitoringOperationClasses(properties);
		for (String className : classes) {
			MonitoringOperation<LongProcess> mo = createMonitoringOperation(className, properties);
			monitor.addOperation(mo);
		}
		if (!CollectionUtils.isNullOrEmpty(extraMonitoringOperation)) {
			for (MonitoringOperation<LongProcess> mo : extraMonitoringOperation) {
				monitor.addOperation(mo);
			}
		}
		return monitor;
	}
	/**
	 * Creates and starts the monitor in a new thread.
	 *
	 * @param monitor
	 *            Monitor
	 * @return thread created
	 */
	public Thread startMonitor(Monitor<LongProcess> monitor) {
		Thread t = new Thread(monitor);
		t.start();
		return t;
	}


	/**
	 * Creates a MonitoringOperation of the specified class.
	 * 
	 * If the monitoring operation is also {@link ModifiableByProperties},
	 * then it will be modified by the specified properties.
	 *
	 * @param className the class name
	 * @param properties the properties
	 * @return Returns the monitoring operation.
	 */
	MonitoringOperation<LongProcess> createMonitoringOperation(String className, Properties properties) {
		@SuppressWarnings("unchecked")
		MonitoringOperation<LongProcess> mo = (MonitoringOperation<LongProcess>)Factory.create(className);
		if (mo instanceof ModifiableByProperties) {
			ModifiableByProperties mmo = (ModifiableByProperties) mo;
			mmo.beModified(properties);
		}
		return mo;
	}


	/**
	 * Gets a set of strings that contain all properties that start
	 * with the prefix <code>monitor.</code>.
	 * 
	 * These strings give classes for monitoring operations.
	 *
	 * @param p the p
	 * @return Returns the set of strings.
	 */
	Set<String> getMonitoringOperationClasses(Properties p) {
		String ops = p.getProperty(MONITOR_KEY);
		if (StringUtils.isNullOrBlank(ops)) {
			return new HashSet<String>();
		}
		String[] split = TokenUtils.splitTrim(ops, StringConstants.COMMA, false);
		return new HashSet<String>(Arrays.asList(split));
	}
}
