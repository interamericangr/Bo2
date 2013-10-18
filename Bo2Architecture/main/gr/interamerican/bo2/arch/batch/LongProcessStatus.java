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
package gr.interamerican.bo2.arch.batch;

import java.util.Date;

/**
 * Status of a long running process.
 */
public interface LongProcessStatus {
	
	/**
	 * Gets the name.
	 * 
	 * @return Returns the name.
	 */
	String getName();
	
	/**
	 * Start time of the process.
	 * 
	 * @return Returns the start time of the process.
	 */
	Date getStartTime();
	
	/**
	 * End time of the process.
	 * 
	 * @return Returns the end time of the process.
	 */
	Date getEndTime();

	/**
	 * Indicates if the process has stopped.
	 *
	 * @return Returns true if processing has stopped.
	 */
	boolean isFinished();

	/**
	 * Indicates that the process has quit while processing is still
	 * not finished.
	 * 
	 * This could happen in the following cases:
	 * <li> The process was forced to quit. </>
	 * <li> Processing ended abnormally due to an exception. </>
	 *
	 * @return Returns true if processing has stopped abnormally.
	 */
	boolean isFinishedAbnormally();

	/**
	 * Indicates if the queue processor is paused.
	 *
	 * @return Returns the value of the paused flag.
	 */
	boolean isPaused();
	
	/**
	 * Gets the total count of elements that have to be processed by
	 * this process.
	 * 
	 * @return Returns the count of elements that have been polled 
	 *         from the queue for processing.
	 */
	long getTotalElementsCount();

	/**
	 * Gets the count of elements that have been processed by this
	 * process up to this moment.
	 * 
	 * @return Returns the count of elements that have been processed.
	 */
	long getProcessedCount();

	/**
	 * Gets the count of elements that have been successfully
	 * processed.
	 * 
	 * Successful process means that the operation was executed with
	 * input the specified elements and it completed without throwing
	 * an exception.
	 * 
	 * @return Returns the count of elements that have been successfully 
	 *         processed so far.
	 */
	long getSuccessesCount();

	/**
	 * Gets the count of elements that have been processed, but their 
	 * processing failed with an exception.
	 * 
	 * @return Returns the count of elements that have failed to be 
	 *         processed successfully so far.
	 */
	long getFailuresCount();	
	
	/**
	 * Gets the message of the exception that forced the process to 
	 * stop abnormally.
	 *
	 * @return Returns the exception message
	 */
	String getExceptionMessage();

}
