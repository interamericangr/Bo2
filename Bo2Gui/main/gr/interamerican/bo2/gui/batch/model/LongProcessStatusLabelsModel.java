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

import java.io.Serializable;

/**
 * Model object for the labels of a LongProcessStatus panel.
 */
public interface LongProcessStatusLabelsModel extends Serializable {

	/**
	 * Gets the lblName.
	 *
	 * @return Returns the lblName
	 */
	String getLblName();
	
	
	/**
	 * Gets the lblFinished.
	 *
	 * @return Returns the lblFinished
	 */
	String getLblFinished();

	/**
	 * Assigns a new value to the lblFinished.
	 *
	 * @param lblFinished the lblFinished to set
	 */
	void setLblFinished(String lblFinished);

	/**
	 * Gets the lblFinishedAbnormally.
	 *
	 * @return Returns the lblFinishedAbnormally
	 */
	String getLblFinishedAbnormally();

	/**
	 * Assigns a new value to the lblFinishedAbnormally.
	 *
	 * @param lblFinishedAbnormally the lblFinishedAbnormally to set
	 */
	void setLblFinishedAbnormally(String lblFinishedAbnormally);

	/**
	 * Gets the lblPaused.
	 *
	 * @return Returns the lblPaused
	 */
	String getLblPaused();

	/**
	 * Assigns a new value to the lblPaused.
	 *
	 * @param lblPaused the lblPaused to set
	 */
	void setLblPaused(String lblPaused);

	/**
	 * Gets the lblStartTime.
	 *
	 * @return Returns the lblStartTime
	 */
	String getLblStartTime();

	/**
	 * Assigns a new value to the lblStartTime.
	 *
	 * @param lblStartTime the lblStartTime to set
	 */
	void setLblStartTime(String lblStartTime);

	/**
	 * Gets the lblEndTime.
	 *
	 * @return Returns the lblEndTime
	 */
	String getLblEndTime();

	/**
	 * Assigns a new value to the lblEndTime.
	 *
	 * @param lblEndTime the lblEndTime to set
	 */
	void setLblEndTime(String lblEndTime);

	/**
	 * Gets the lblTotalElementsCount.
	 *
	 * @return Returns the lblTotalElementsCount
	 */
	String getLblTotalElementsCount();

	/**
	 * Assigns a new value to the lblTotalElementsCount.
	 *
	 * @param lblTotalElementsCount the lblTotalElementsCount to set
	 */
	void setLblTotalElementsCount(String lblTotalElementsCount);

	/**
	 * Gets the lblProcessedCount.
	 *
	 * @return Returns the lblProcessedCount
	 */
	String getLblProcessedCount();

	/**
	 * Assigns a new value to the lblProcessedCount.
	 *
	 * @param lblProcessedCount the lblProcessedCount to set
	 */
	void setLblProcessedCount(String lblProcessedCount);

	/**
	 * Gets the lblSuccessesCount.
	 *
	 * @return Returns the lblSuccessesCount
	 */
	String getLblSuccessesCount();

	/**
	 * Assigns a new value to the lblSuccessesCount.
	 *
	 * @param lblSuccessesCount the lblSuccessesCount to set
	 */
	void setLblSuccessesCount(String lblSuccessesCount);

	/**
	 * Gets the lblFailuresCount.
	 *
	 * @return Returns the lblFailuresCount
	 */
	String getLblFailuresCount();

	/**
	 * Assigns a new value to the lblFailuresCount.
	 *
	 * @param lblFailuresCount the lblFailuresCount to set
	 */
	void setLblFailuresCount(String lblFailuresCount);

}
