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
package gr.interamerican.bo2.samples.bean;

import gr.interamerican.bo2.utils.beans.Range;

import java.util.Date;

/**
 * 
 */
public class TripInfo {
	/**
	 * destination.
	 */
	String destination;
	
	/**
	 * trip period.
	 */
	Range<Date> period;
	
	/**
	 * Family.
	 */
	Family family;

	/**
	 * Gets the destination.
	 *
	 * @return Returns the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Assigns a new value to the destination.
	 *
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * Gets the period.
	 *
	 * @return Returns the period
	 */
	public Range<Date> getPeriod() {
		return period;
	}

	/**
	 * Assigns a new value to the period.
	 *
	 * @param period the period to set
	 */
	public void setPeriod(Range<Date> period) {
		this.period = period;
	}

	/**
	 * Gets the family.
	 *
	 * @return Returns the family
	 */
	public Family getFamily() {
		return family;
	}

	/**
	 * Assigns a new value to the family.
	 *
	 * @param family the family to set
	 */
	public void setFamily(Family family) {
		this.family = family;
	}
	

}
