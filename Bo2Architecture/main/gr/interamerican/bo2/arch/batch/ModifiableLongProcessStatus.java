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
 * Setter methods for the {@link LongProcessStatus} properties.
 */
public interface ModifiableLongProcessStatus 
extends LongProcessStatus {
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	void setName(String name);
	
	/**
	 * Sets the start time.
	 *
	 * @param startTime the new start time
	 */
	void setStartTime(Date startTime);
	
	/**
	 * Sets the endTime.
	 *
	 * @param endTime the new end time
	 */
	void setEndTime(Date endTime);

	/**
	 * Sets the finished indicator.
	 *
	 * @param finished the new finished
	 */
	void setFinished(boolean finished);

	/**
	 * Sets the finished abnormally indicator.
	 *
	 * @param finishedAbnormally the new finished abnormally
	 */
	void setFinishedAbnormally(boolean finishedAbnormally);

	/**
	 * Sets the paused indicator.
	 *
	 * @param paused the new paused
	 */
	void setPaused(boolean paused);
	
	/**
	 * Sets the total count of elements.
	 *
	 * @param totalElementsCount the new total elements count
	 */
	void setTotalElementsCount(long totalElementsCount);

	/**
	 * Sets the count of processed elements.
	 *
	 * @param processedCount the new processed count
	 */
	void setProcessedCount(long processedCount);

	/**
	 * Sets the count of successes.
	 *
	 * @param successesCount the new successes count
	 */
	void setSuccessesCount(long successesCount);

	/**
	 * Sets the count of failures.
	 *
	 * @param failuresCount the new failures count
	 */
	void setFailuresCount(long failuresCount);	
	
	/**
	 * Sets the exception message.
	 *
	 * @param exceptionMessage the new exception message
	 */
	void setExceptionMessage(String exceptionMessage);

}
