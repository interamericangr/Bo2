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

import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.runtime.monitor.LongProcessMail;
import gr.interamerican.bo2.impl.open.runtime.monitor.LongProcessSysout;
import gr.interamerican.bo2.impl.open.runtime.monitor.Tidy;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.adapters.cmd.PeriodicCommand;
import gr.interamerican.bo2.utils.adapters.cmd.SingleSubjectOperation;
import gr.interamerican.bo2.utils.adapters.trans.GetProperty;
import gr.interamerican.bo2.utils.adapters.vo.Refresh;
import gr.interamerican.bo2.utils.attributes.Refreshable;
import gr.interamerican.bo2.utils.attributes.SimpleCommand;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.GetBooleanProperty;
import gr.interamerican.bo2.utils.runnables.Monitor;

import java.util.Properties;
import java.util.Set;

/**
 * Utility for {@link BatchProcess} operations.
 */
public class BatchProcessUtility {
	
	public static final String MONITOR_PREFIX = "monitor.";
	
	/**
	 * Session.
	 */
	Session<?,?> session;
	
	/**
	 * Creates a new BatchProcessUtility.
	 * 
	 * @param session
	 */
	public BatchProcessUtility(Session<?,?> session) {
		super();
		this.session = session;
	}

	/**
	 * Creates a {@link BatchProcessParmsFactory}.
	 * 
	 * @param properties
	 * 
	 * @return Returns the {@link BatchProcessParmsFactory}.
	 */
	public BatchProcessParmsFactory getFactory(Properties properties) {
		String className = properties.getProperty(BatchProcessParmNames.PARAMETERS_FACTORY_CLASS);
		if (StringUtils.isNullOrBlank(className)) {
			return new BatchProcessParmFactoryImpl();
		}
		Object factory = ReflectionUtils.newInstance(className);
		return (BatchProcessParmsFactory) factory;		
	}
	
	/**
	 * Starts the batch process.
	 * 
	 * @param properties
	 * 
	 * @return Returns the {@link BatchProcess}.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BatchProcess start(Properties properties) {
		Bo2Session.setSession(session); //Set the session to the main thread of the batch process.
		BatchProcessParmsFactory factory = getFactory(properties);		 
		BatchProcessParm bpi = factory.createParameter(properties);				
		BatchProcess batch = new BatchProcess(bpi);
		new Thread(batch).start();		
		long interval = batch.getInitialThreads() * 10;
		ThreadUtils.sleepMillis(interval);
		return batch;
	}
	
	/**
	 * Creates a SimpleCommand that refreshes the UI.
	 * 
	 * @param refreshable
	 *        UI to refresh. to refresh. 
	 * 
	 * @return Returns the command.
	 */
	public SimpleCommand refreshCommand(Refreshable refreshable) {
		VoidOperation<Refreshable> refresh = 
			new Refresh<Refreshable>();
		return new SingleSubjectOperation <Refreshable> (refresh, refreshable);				
	}
	

	
	/**
	 * Starts a monitoring for a batch process.
	 * 
	 * @param batch
	 *        Batch process
	 * @param properties 
	 *        Batch process properties
	 *        
	 *	@return Returns the monitor.
	 */
	public Monitor<MultiThreadedLongProcess> startMonitor(BatchProcess<?> batch, Properties properties) {
		Condition<MultiThreadedLongProcess> stop =
			new GetBooleanProperty<MultiThreadedLongProcess>("finished", MultiThreadedLongProcess.class); //$NON-NLS-1$
				
		Monitor<MultiThreadedLongProcess> monitor = 
			new Monitor<MultiThreadedLongProcess>(batch, stop);
		
		
		
		/*
		 * TODO: add monitoring commands
		 */
		
		new Thread(monitor).start();
		return monitor;
	}
	
	/**
	 * Gets a set of strings that contain all properties that start 
	 * with the prefix <code>monitor.</code>.
	 * 
	 * These strings give classes for monitoring operations.
	 * 
	 * @param p
	 * 
	 * @return Returns the set of strings.
	 */
	Set<String> getMonitoringOperations(Properties p) {
		Set<String> keys = Utils.cast(p.keySet());
		for (String key : keys) {
			if (key.startsWith("monitor.")) {
				//String className = StringUtils
			}
		}
		return null;
		
	}
	
	

}
