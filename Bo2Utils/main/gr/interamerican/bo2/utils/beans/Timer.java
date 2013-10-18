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
package gr.interamerican.bo2.utils.beans;

import java.util.Date;

/**
 * Timer utility.
 */
public class Timer {
	/**
	 * start time
	 */
	Date start;
	
	/**
	 * Creates a new Timer object. 
	 *
	 */
	public Timer() {
		super();
		reset();
	}

	/**
	 * Resets the timer.
	 */
	public void reset() {
		start = new Date();
	}
	
	/**
	 * Gets the current time metric in milliseconds.
	 * 
	 * @return Gets the current time metric.
	 */
	public long get() {
		Date now = new Date();
		return now.getTime() - start.getTime();
	}

}
