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

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.SystemUtils;

/**
 * 
 */
public class SystemState {
	
	/**
	 * Creates a SystemState that contains the changes since the
	 * specified previous SystemState.
	 * 
	 * @param first 
	 * @param last 
	 * 
	 * @return Returns a SystemState that contains the increase in 
	 *         the system properties that from the first to the last
	 *         system state.
	 */
	public static SystemState change(SystemState first, SystemState last) {
		return new SystemState(
				last.freeMemory - first.freeMemory,
				last.maxMemory - first.maxMemory,
				last.totalMemory - first.totalMemory,
				last.usedMemory - first.usedMemory);				
		
	}
	
	/**
	 * Free memoty.
	 */
	double freeMemory;
	/**
	 * Max memoty.
	 */
	double maxMemory;
	/**
	 * Total memory
	 */
	double totalMemory;
	/**
	 * Used memory.
	 */
	double usedMemory;
	/**
	 * Gets the freeMemory.
	 *
	 * @return Returns the freeMemory
	 */
	public double getFreeMemory() {
		return freeMemory;
	}
	/**
	 * Gets the maxMemory.
	 *
	 * @return Returns the maxMemory
	 */
	public double getMaxMemory() {
		return maxMemory;
	}
	/**
	 * Gets the totalMemory.
	 *
	 * @return Returns the totalMemory
	 */
	public double getTotalMemory() {
		return totalMemory;
	}
	/**
	 * Gets the usedMemory.
	 *
	 * @return Returns the usedMemory
	 */
	public double getUsedMemory() {
		return usedMemory;
	}
	
	@SuppressWarnings("nls")
	@Override
	public String toString() {
		String str = StringUtils.concat(
			"Memory usage:\n",	
			"freeMemory = ", Double.toString(freeMemory), "\n",
			"maxMemory = ", Double.toString(maxMemory), "\n",
			"totalMemory = ", Double.toString(totalMemory), "\n",
			"usedMemory = ", Double.toString(usedMemory), "\n");
		return str;
	}
	
	/**
	 * Creates a new SystemState object. 
	 *
	 */
	public SystemState() {
		this(
		 SystemUtils.freeMemory(),
		 SystemUtils.maxMemory(),
		 SystemUtils.totalMemory(),
		 SystemUtils.usedMemory());
	}
	/**
	 * Creates a new SystemState object. 
	 *
	 * @param freeMemory
	 * @param maxMemory
	 * @param totalMemory
	 * @param usedMemory
	 */
	private SystemState(double freeMemory, double maxMemory, double totalMemory, double usedMemory) {
		super();
		this.freeMemory = freeMemory;
		this.maxMemory = maxMemory;
		this.totalMemory = totalMemory;
		this.usedMemory = usedMemory;
	}
	
	

}
