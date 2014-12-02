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
package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.PoDeleter;
import gr.interamerican.bo2.arch.exceptions.DataException;

import java.io.Serializable;

/**
 * Implementation of {@link FastPoReader}.
 * 
 * This implementation depends on the Factory in order to create
 * instances of the PersistentObject and its PersistenceWorker.
 * This implementation caches the objects it reads in a Map.
 * 
 * @param <K> 
 *        Type of key.
 * @param <P> 
 *        Type of persistent object.
 * 
 */
public class FactorySupportedPoDeleter
<K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>> 
extends FactorySupportedPoHandler<K, P>
implements PoDeleter<K, P> {
	
	/**
	 * Creates a new FastPoReaderImpl object. 
	 *
	 * @param poClass
	 */
	public FactorySupportedPoDeleter(Class<P> poClass) {
		super(poClass);
	}
	
	@Override
	public void delete(K key) throws DataException {
		P p = createByKey(key);
		pw.delete(p);
	}
	
	@Override
	public void deleteByProperties(Object key) throws DataException {
		P p = createByKeyProperties(key);
		pw.delete(p);
	}
	
	

	
	
}
