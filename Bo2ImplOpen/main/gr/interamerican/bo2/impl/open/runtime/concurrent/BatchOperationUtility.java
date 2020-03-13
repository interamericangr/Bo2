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

import java.util.Properties;

import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

/**
 * Utility for {@link BatchOperation} operations.
 *
 */
public class BatchOperationUtility {

	/**
	 * Session.
	 */
	Session<?,?> session;

	/**
	 * Creates a new BatchProcessUtility.
	 *
	 * @param session the session
	 */
	public BatchOperationUtility(Session<?,?> session) {
		super();
		this.session = session;
	}

	/**
	 * Creates a {@link BatchProcessParmsFactory}.
	 *
	 * @param properties the properties
	 * @return Returns the {@link BatchProcessParmsFactory}.
	 */
	public OperationParmFactory getFactory(Properties properties) {
		String className = properties.getProperty(BatchOperationParmNames.PARAMETERS_FACTORY_CLASS);
		if (StringUtils.isNullOrBlank(className)) {
			return new OperationParmFactoryImpl();
		}
		Object factory = Factory.getCurrentFactory().create(className);
		return (OperationParmFactory) factory;
	}

	/**
	 * Creates a new {@link BatchOperation} and starts a new thread for it.
	 * 
	 * This method also sets the session of this {@link BatchOperationUtility} as the thread local
	 * {@link Bo2Session}. The new {@link BatchOperation} gets a reference to this session, which
	 * will be passed to the thread local session of the BatchProcess thread.
	 *
	 * @param batch the batch
	 * @return the thread created
	 */
	public Thread startOperation(BatchOperation<?> batch) {
		Thread t = new Thread(batch);
		t.start();
		ThreadUtils.sleepMillis(10);
		return t;
	}

	/**
	 * Creates a new {@link BatchOperation} and starts a new thread for it.
	 * 
	 * This method also sets the session of this {@link BatchOperationUtility}
	 * as the thread local {@link Bo2Session}. The new {@link BatchOperation}
	 * gets a reference to this session, which will be passed to the thread local
	 * session of the BatchProcess thread.
	 *
	 * @param properties the properties
	 * @return Returns the {@link BatchProcess}.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BatchOperation createBatchOperation(Properties properties) {
		Bo2Session.setSession(session); //Set the session to the main thread of the batch process.
		OperationParmFactory factory = getFactory(properties);
		OperationParm bpi = factory.createParameter(properties);
		BatchOperation batch = new BatchOperation(bpi);
		return batch;
	}

}
