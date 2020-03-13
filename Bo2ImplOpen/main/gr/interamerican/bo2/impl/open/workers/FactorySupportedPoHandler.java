/*******************************************************************************
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

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.PoReader;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.annotations.Child;

import java.io.Serializable;

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
public class FactorySupportedPoHandler
<K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>> 
extends AbstractResourceConsumer {
	
	/**
	 * Declaration class of P.
	 */
	Class<P> poClass;
	
	/**
	 * Persistence worker.
	 */
	@Child PersistenceWorker<P> pw;

	/**
	 * Creates a new FastPoReaderImpl object. 
	 *
	 * @param poClass the po class
	 */
	public FactorySupportedPoHandler(Class<P> poClass) {
		this.poClass = poClass;
		this.pw = Factory.createPw(poClass);
	}
	
	
	/**
	 * Gets a Persistent object with key properties 
	 * equal to the properties of the specified object. 
	 *  
	 *
	 * @param key the key
	 * @return Returns the persistent object.
	 */
	protected P createByKeyProperties(Object key) {
		P p = Factory.create(poClass);
		K k = p.getKey();
		ReflectionUtils.copyProperties(key, k);		
		p.setKey(k);
		return p;
	}
	
	/**
	 * Gets a Persistent object with the specified key. 
	 *  
	 *
	 * @param key the key
	 * @return Returns the persistent object.
	 */
	protected P createByKey(K key) {
		P p = Factory.create(poClass);
		p.setKey(key);
		return p;
	}
}