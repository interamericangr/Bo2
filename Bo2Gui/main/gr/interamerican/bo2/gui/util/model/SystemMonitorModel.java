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
package gr.interamerican.bo2.gui.util.model;

import gr.interamerican.bo2.utils.SystemUtils;

import java.io.Serializable;

/**
 * Read-only model for displaying system status.
 */
public class SystemMonitorModel implements Serializable {

	/**
	 * serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return the used memory
	 */
	public double getUsedMemory() {
		return SystemUtils.usedMemory();
	}

	/**
	 * @return the free memory
	 */
	public double getFreeMemory() {
		return SystemUtils.freeMemory();
	}

	/**
	 * @return the max memory
	 */
	public double getMaxMemory() {
		return SystemUtils.maxMemory();
	}

	/**
	 * @return the total memory
	 */
	public double getTotalMemory() {
		return SystemUtils.totalMemory();
	}
	
	/**
	 * @return gc events.
	 */
	public long getGcEvents() {
		return SystemUtils.gcEvents();
	}
	
	/**
	 * @return gc time.
	 */
	public long getGcTime() {
		return SystemUtils.gcTime();
	}
	
}
