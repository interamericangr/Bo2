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

import static gr.interamerican.bo2.utils.StringConstants.COLON;
import static gr.interamerican.bo2.utils.StringConstants.NEWLINE;
import static gr.interamerican.bo2.utils.StringConstants.SPACE;
import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.batch.ModifiableLongProcessStatus;
import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.AnyOperation;

import java.util.Date;

/**
 * Create a String presenting the state of a process.
 */
@SuppressWarnings("nls")
public class LongProcessToString 
implements AnyOperation<MultiThreadedLongProcess, String> {
	/**
	 * Time of previous call.
	 */
	Date previousTmstmp = null;
	/**
	 * Status during previous call.
	 */
	ModifiableLongProcessStatus previous = null;
	/**
	 * Time of current call.
	 */
	Date currentTmstmp = null;
	/**
	 * Status during this call.
	 */	
	ModifiableLongProcessStatus current = null;
	
	
	/**
	 * Records the current status of a {@link LongProcess}.
	 * 
	 * @param process
	 * 
	 * @return Returns the status. 
	 */
	ModifiableLongProcessStatus getStatus(LongProcess process) {
		ModifiableLongProcessStatus status = 
			Factory.create(ModifiableLongProcessStatus.class);
		ReflectionUtils.copyProperties(process, status);
		return status;
	}
	
	
	
	@Override
	public String execute(MultiThreadedLongProcess a) {
		current = getStatus(a);
		currentTmstmp = new Date();
		
		String state = current.isFinished() ? "finished" : "runnning";		
		String msg = StringUtils.concat(
			"Process ", current.getName(), SPACE, COLON, SPACE, state, NEWLINE,
			"Processed elements: ", StringUtils.toString(current.getProcessedCount()), NEWLINE,
			"Successes: ", StringUtils.toString(current.getSuccessesCount()), NEWLINE,  
			"Failures: ", StringUtils.toString(current.getFailuresCount()), NEWLINE,
			"Avg speed: ", NumberUtils.format(avgSpeed()), " per minute",  NEWLINE,
			"Current speed: ", NumberUtils.format(currentSpeed()), " per minute", NEWLINE
		);
		return msg;
		
	}
	
	/**
	 * Gets the speed of the process measured from the start of
	 * the process.
	 * 
	 * @return Returns the speed of the process in elements per minute.
	 */
	double avgSpeed() {
		Date start = current.getStartTime();
		Date end;
		if (current.isFinished()) {
			end = current.getEndTime();
		} else {
			end = currentTmstmp;
		}
		long time = end.getTime() - start.getTime();
		double minutes = time/60000.0;
		double speed = current.getProcessedCount()/minutes;
		return speed;
	}
	
	
	/**
	 * Gets the speed of the process measured since the previous
	 * call.
	 * 
	 * @return Returns the speed of the process in elements per minute.
	 */
	double currentSpeed() {
		if (previousTmstmp==null) {
			previousTmstmp = currentTmstmp;
			previous = current;
			return 0.0;
		}
		long time = currentTmstmp.getTime() - previousTmstmp.getTime();
		double minutes = time/60000.0;
		long processed = current.getProcessedCount() - previous.getProcessedCount();
		double speed = processed/minutes;
		return speed;
	}
	
	

}
