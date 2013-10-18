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
package gr.interamerican.bo2.impl.open.state;

import gr.interamerican.bo2.arch.utils.ext.Bo2State;


/**
 * States during a CRUD operation.
 */
public enum CrudStates implements Bo2State  {
	
	/**
	 * Preparing to read.
	 */
	PRE_READ,
	
	/**
	 * Reading.
	 */
	READ,
	
	/**
	 * After reading.
	 */
	POST_READ,
	
	/**
	 * Preparing to store.
	 */
	PRE_STORE,
	
	/**
	 * String.
	 */
	STORE,
	
	/**
	 * After Storing.
	 */
	POST_STORE,
	
	/**
	 * Preparing to delete.
	 */
	PRE_DELETE,
	
	/**
	 * deleting.
	 */
	DELETE,
	
	/**
	 * After delete.
	 */
	POST_DELETE,
	
	/**
	 * Preparing to update.
	 */
	PRE_UPDATE,
	
	/**
	 * Update.
	 */
	UPDATE,
	
	/**
	 * After Update.
	 */
	POST_UPDATE;
	
	

}
