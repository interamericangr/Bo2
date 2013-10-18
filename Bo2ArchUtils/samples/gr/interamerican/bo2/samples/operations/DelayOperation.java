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
package gr.interamerican.bo2.samples.operations;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

/**
 * Operation that stops the execution for a specified
 * time interval.
 */
public class DelayOperation 
extends EmptyOperation {
	/**
	 * Time delay.
	 */
	long delay = 0;

	/**
	 * Gets the delay.
	 *
	 * @return Returns the delay
	 */
	public long getDelay() {
		return delay;
	}

	/**
	 * Assigns a new value to the delay.
	 *
	 * @param delay the delay to set
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	@Override
	public void execute() throws LogicException, DataException {	
		ThreadUtils.sleepMillis(delay);
	}

}
