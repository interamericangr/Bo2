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
package gr.interamerican.bo2.gui.batch.model;

import gr.interamerican.bo2.gui.batch.BatchProcessFrame;

import java.io.Serializable;
import java.util.Date;

/**
 * Model for the {@link BatchProcessFrame}.
 */
public interface RefreshModel extends Serializable {

	/**
	 * Gets the time of last refresh.
	 * 
	 * @return Returns the time of last refresh.
	 */
	Date getLastRefresh();

	/**
	 * Sets the time of last refresh.
	 * 
	 * @param lastRefresh
	 */
	void setLastRefresh(Date lastRefresh);

	/**
	 * Gets the auto-refresh flag.
	 * 
	 * @return Returns the value of the auto-refresh flag.
	 */
	boolean getAutoRefresh();

	/**
	 * Sets the auto-refresh flag.
	 * 
	 * @param autoRefresh
	 */
	void setAutoRefresh(boolean autoRefresh);
}
