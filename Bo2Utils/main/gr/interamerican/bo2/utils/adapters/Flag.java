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
package gr.interamerican.bo2.utils.adapters;

import java.io.Serializable;

/**
 * Flag is an adapter interface for any class that has
 * an operation that returns a boolean value.
 */
public interface Flag extends Serializable {
	
	/**
	 * Returns true if the flag is up.
	 * 
	 * @return Returns true if the flag is up.
	 */
	boolean isUp();
	
	/**
	 * Message describing that the flag is Up.
	 * 
	 * @return Returns a descriptive message when the flag is up.
	 */
	String getUpMessage();
	
	/**
	 * Message describing that the flag is Down.
	 * 
	 * @return Returns a descriptive message when the flag is down.
	 */
	String getDownMessage();

}
