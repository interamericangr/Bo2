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

import gr.interamerican.bo2.arch.batch.AutomatedLaunchedProcess;
import gr.interamerican.bo2.arch.batch.OperationLauncher;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;

/**
 * launches an operation.
 */
public class ConsoleOperationLauncher implements OperationLauncher, AutomatedLaunchedProcess {

	/** operation thread. */
	private Thread operationThread;
	
	/** The batch op. */
	private BatchOperation<?> batchOp;

	@Override
	public void launch(Properties properties) {
		BatchOperationUtility util = new BatchOperationUtility(Bo2Session.getSession());
		batchOp = util.createBatchOperation(properties);
		operationThread = util.startOperation(batchOp);
	}

	/**
	 * waits for the operationThread thread.
	 */
	public void waitForThread() {
		try {
			operationThread.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Integer waitForThreadAndGetProcessExitValue() {
		waitForThread();
		Integer exitValue = BatchOperationUtils.getExitValue(batchOp);
		return exitValue;
	}
}