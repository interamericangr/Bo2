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
package gr.interamerican.bo2.arch;

import gr.interamerican.bo2.arch.exceptions.DataException;

/**
 * Key generator.
 * 
 * @param <K> Type of key. 
 */
public interface KeyGenerator<K extends Key> {
	
	/**
	 * Generates a key for a new {@link PersistentObject}.
	 *
	 * @return Returns a new unique key.
	 *  
	 * @throws DataException the data exception
	 */
	public K getNewKey() throws DataException;

}
