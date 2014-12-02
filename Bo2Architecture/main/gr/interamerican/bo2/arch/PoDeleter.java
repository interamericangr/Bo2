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
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;

import java.io.Serializable;

/**
 * Interface of a utility that deletes persistent objects.  
 * 
 * @param <K> 
 *        Type of key.
 * @param <P> 
 *        Type of {@link PersistentObject}
 */
public interface PoDeleter
<K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>> 
extends Worker {
	
	/**
	 * Deletes the persistent object with the specified key.
	 * 
	 * @param key
	 *        Key of the persistent object.       
	 * 
	 * @throws DataException 
	 */
	public void delete(K key) throws DataException;
	
	/**
	 * Reads the persistent object that has a key that is either
	 * equal with the specified object or has its properties equal
	 * with the specified object.
	 * 
	 * This method is optional for classes implementing this interface.
	 * If a class does not support this method, then a {@link DataOperationNotSupportedException}
	 * should be thrown. 
	 * 
	 * @param key
	 *        Object with its properties equal with the properties
	 *        of the persistent object's key.
	 *        
	 * @throws DataException 
	 */
	public void deleteByProperties(Object key) throws DataException;
	
	

}
