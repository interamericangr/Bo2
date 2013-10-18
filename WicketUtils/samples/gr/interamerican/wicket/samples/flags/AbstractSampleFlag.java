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
package gr.interamerican.wicket.samples.flags;

import gr.interamerican.bo2.utils.adapters.Flag;

/**
 * Base sample Flag implementation.
 */
public abstract class AbstractSampleFlag implements Flag {
	
	/**
	 * Up message.
	 */
	public static String UP_MESSAGE = "up"; //$NON-NLS-1$
	
	/**
	 * Down message.
	 */
	public static String DOWN_MESSAGE = "down"; //$NON-NLS-1$

	public abstract boolean isUp();

	public String getUpMessage() {
		return UP_MESSAGE;
	}

	public String getDownMessage() {
		return DOWN_MESSAGE;
	}
	
}
