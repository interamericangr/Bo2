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

import gr.interamerican.bo2.arch.batch.ProcessStatus;

/**
 * utilities for BatchOperations.
 */
public class BatchOperationUtils {

	/**
	 * Gets the exit value.
	 *
	 * @param a the a
	 * @return the exit value that the batch process vm would report. when finished. null returned
	 *         when batchprocess is null or has not yet finished
	 */
	public static Integer getExitValue(ProcessStatus a) {
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
			return 0;
		}
		return null;
	}
}
