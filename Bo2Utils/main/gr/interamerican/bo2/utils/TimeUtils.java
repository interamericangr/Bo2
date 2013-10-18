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
package gr.interamerican.bo2.utils;

/**
 * Time related utilities.
 */
public class TimeUtils {
	
	/**
	 * Converts seconds to milliseconds.
	 * 
	 * @param hours
	 * 
	 * @return Returns the equivalent of the specified hours
	 *         in milliseconds.
	 */
	public static long hours2millis(int hours) {
		return hours * 3600000L;		
	}
	
	/**
	 * Converts minutes to milliseconds.
	 * 
	 * @param minutes
	 * 
	 * @return Returns the equivalent of the specified minutes
	 *         in milliseconds.
	 */
	public static long minutes2millis(int minutes) {
		return minutes * 60000L;		
	}
	
	/**
	 * Converts seconds to milliseconds.
	 * 
	 * @param seconds
	 * 
	 * @return Returns the equivalent of the specified seconds
	 *         in milliseconds.
	 */
	public static long seconds2millis(int seconds) {
		return seconds * 1000L;		
	}
	
	

}
