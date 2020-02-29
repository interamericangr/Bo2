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

/**
 * The Class BatchProcessConstants.
 */
public class BatchProcessConstants {

	/**
	 * Provider name for the provider used to initialized shared
	 * streams used a log files by multithreaded processes.
	 */
	public static final String LOGFILES_PROVIDER_NAME = "LOGFILES"; //$NON-NLS-1$

	/**
	 * Postfix of successes stream name.
	 */
	public static final String SUCCESSES = "_SUCCESSES"; //$NON-NLS-1$

	/**
	 * Postfix of failures stream name.
	 */
	public static final String FAILURES = "_FAILURES"; //$NON-NLS-1$

	/**
	 * Postfix of stacktraces stream name.
	 */
	public static final String STACKTRACES = "_STACKTRACES"; //$NON-NLS-1$
	
	/**
	 * User variable that indicates the batch application version that launched this batch process instance.
	 */
	public static final String BATCH_VERSION = "batch.app.version"; //$NON-NLS-1$
	
	/**
	 * exit value when {@link BatchProcess} closed by user before the end of execution.
	 */
	public static final int EXIT_VALUE_CLOSED_BY_USER = 87;
	
	/** exit value when {@link BatchProcess} when there are failures in threads. */
	public static final int EXIT_VALUE_WITH_THREAD_FAILURES = 85;

	/**
	 * Gets the exit value.
	 *
	 * @param a the a
	 * @return the exit value that the batch process vm would report. when finished. null returned
	 *         when batchprocess is null or has not yet finished
	 */
	public static Integer getExitValue(LongProcess a) {
		if (a == null) {
			return null;
		}
		if (!a.isFinished() && !a.isFinishedAbnormally()) {
			return null;
		}
		if (a.isFinishedAbnormally()) {
			return -1;
		}
		if (a.isFinished()) {
			if (a.getFailuresCount() > 0) {
				return EXIT_VALUE_WITH_THREAD_FAILURES;
			}
			return 0;
		}
		return null;
	}
}
