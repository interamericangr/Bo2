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

import java.util.List;

/**
 * Long process that is using multiple LongProcesses that
 * run on different threads.
 * 
 */
public interface MultiThreadedLongProcess 
extends LongProcess {
	
	/**
	 * Gets a list with the sub-processes of this long process.
	 * 
	 * @return Returns a list with the sub-processes.
	 */
	public List<LongProcess> getSubProcesses();
	
	/**
	 * Gets the count of sub-processes.
	 * 
	 * @return Returns the count of sub-processes.
	 */
	public int getCountOfSubProcesses();
	
	/**
	 * Gets the count of finished sub-processes.
	 * 
	 * @return Returns the count of finished sub-processes.
	 */
	public int getCountOfFinishedSubProcesses();
	
	/**
	 * Gets the count of paused sub-processes.
	 * 
	 * @return Returns the count of paused sub-processes.
	 */
	public int getCountOfPausedSubProcesses();
	
	/**
	 * Gets the count of abnormally finished sub-processes.
	 * 
	 * @return Returns the count of abnormally finished sub-processes.
	 */
	public int getCountOfAbnormallyFinishedSubProcesses();
	
	/**
	 * Indicates if the sub-processes count can be increased.
	 * 
	 * @return Returns true if more threads can be added to this process.
	 */
	public boolean isCanIncreaseSubProcesses();
	

}
