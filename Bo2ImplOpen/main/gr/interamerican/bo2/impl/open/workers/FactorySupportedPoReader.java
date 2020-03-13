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


import java.io.Serializable;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.PoReader;
import gr.interamerican.bo2.arch.exceptions.DataException;

/**
 * Implementation of {@link PoReader}.
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
public class FactorySupportedPoReader
<K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>> 
extends FactorySupportedPoHandler<K, P>
implements PoReader<K, P> {
	
	/**
	 * Creates a new FastPoReaderImpl object. 
	 *
	 * @param poClass the po class
	 */
	public FactorySupportedPoReader(Class<P> poClass) {
		super(poClass);		
	}

	@Override
	public P get(K key) throws DataException {
		P p = createByKey(key);
		return pw.read(p);
	}

	@Override
	public P getByProperties(Object key) throws DataException {
		P p = createByKeyProperties(key);
		return pw.read(p);
	}
}