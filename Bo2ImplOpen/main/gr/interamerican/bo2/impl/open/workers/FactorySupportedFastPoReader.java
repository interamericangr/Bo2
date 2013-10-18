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

import gr.interamerican.bo2.arch.FastPoReader;
import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.annotations.Child;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
public class FactorySupportedFastPoReader
<K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>> 
extends AbstractResourceConsumer
implements FastPoReader<K, P> {
	
	/**
	 * Declaration class of P.
	 */
	Class<P> poClass;
	
	/**
	 * Persistence worker.
	 */
	@Child PersistenceWorker<P> pw;
	
	/**
	 * Cache.
	 */
	Map<K, P> cache = new HashMap<K, P>();

	/**
	 * Creates a new FastPoReaderImpl object. 
	 *
	 * @param poClass
	 */
	public FactorySupportedFastPoReader(Class<P> poClass) {
		super();
		this.poClass = poClass;
		this.pw = Factory.createPw(poClass);
	}
	
	public P get(K key) throws DataException {		
		P p = cache.get(key);
		if (p==null) {
			p = Factory.create(poClass);
			p.setKey(key);
			p = pw.read(p);
			cache.put(key, p);			
		}
		return p;
	}
}
