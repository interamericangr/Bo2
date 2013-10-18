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

import java.io.Serializable;

/**
 * Each object that is stored in a data store is a PersistentObject.
 * 
 * A PersistentObject has no particular functionality, every object could can be
 * a PersistentObject, provided that it is stored somewhere. This is the reason,
 * PersistentObject is an empty interface.
 * 
 * @param <K>
 *            Type of key.
 * 
 */
public interface PersistentObject<K extends Serializable & Comparable<? super K>> extends Serializable {
	
	/**
	 * Key of the persistent object.
	 * 
	 * @return Returns the key of this object.
	 */
	public K getKey();

	/**
	 * Sets the key of the persistent object
	 * 
	 * @param key
	 *            the key to set
	 */
	public void setKey(K key);
	

	/**
	 * Makes any necessary tidying of the object's state.
	 * 
	 * This method should always be called prior to any 
	 * persistent operation.
	 */
	public void tidy();

}
