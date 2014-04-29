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
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.adapters.Modification;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Dynamic cache for persistent objects. <br/>
 * 
 * This class fetches persistent objects from the database and keeps
 * them in a cache so that the next time the same object is asked, it
 * can be fetched from the local cache. <br/>
 * The purpose of this class is to keep objects that are not expected
 * to be modified.
 * 
 * @param <P>
 *        Type of {@link PersistentObject}.
 * @param <K>
 *        Type of PersistentObject key.
 */
public class PoCache
<K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>> {
	/**
	 * Indicates if the cache is enabled.
	 */
	boolean isCacheEnabled = false;

	/**
	 * Transformation to be applied on objects after reading them.
	 */
	Modification<P> modification;

	/**
	 * Map.
	 */
	Map<K, P> map = new HashMap<K,P>();

	/**
	 * Class of Persistent object.
	 */
	Class<P> poClass;

	/**
	 * Max cache size.
	 */
	int maxSize;

	/**
	 * Creates a new PoCache object.
	 *
	 * @param modification
	 *        Modification to apply to the objects being read.
	 *        Optional, this can be null.
	 * @param poClass
	 *        Persistent class.
	 * @param maxSize
	 *        Size of cache. If set to 0 then objects read will not
	 *        be cached.
	 */
	public PoCache(Class<P> poClass, int maxSize, Modification<P> modification) {
		super();
		this.maxSize = maxSize;
		this.isCacheEnabled = maxSize>0;
		this.modification = modification;
		this.map = new HashMap<K,P>();
		this.poClass = poClass;
	}

	/**
	 * Fetches the persistent object with the specified key searching it
	 * first in the local cache and then in the data store. <br/>
	 * 
	 * If the object is retrieved from the local cache, then it is re-attached
	 * to the current session.
	 * 
	 * @param key
	 *        Key of the persistent object being searched.
	 * 
	 * @return Returns the persistent object with the specified key.
	 *         If there is no {@link PersistentObject} found with the
	 *         specified key, then returns null.
	 * 
	 * @throws RuntimeException
	 *         That wraps any {@link InitializationException} or
	 *         {@link DataException} caught during the read operation.
	 */
	public P get(K key) {
		P po = map.get(key);
		if (po==null) {
			po = read(key);
		}
		return po;
	}

	/**
	 * Puts the specified persistent object in the cache, so that
	 * future calls to <code>get(key)</code> will return this element
	 * for the specified key.
	 * 
	 * This method is useful in unit testing by putting fixture objects
	 * in the cache and thus imitating the behavior of a system that would
	 * read these fixtures from the database.
	 * 
	 * @param po
	 *        Persistent object to put in the cache.
	 */
	public void set(P po) {
		K key = po.getKey();
		map.put(key, po);
	}

	/**
	 * Removes the persistent object from the PoCache. This method is useful if you want to remove specific objects from
	 * this cache. The object may re-enter in the cache on future put calls.
	 * 
	 * @param key
	 *            the keys that will be removed from the PoCache
	 */
	void remove(K key) {
		map.remove(key);
	}

	/**
	 * Reads the persistent object from the database.
	 * 
	 * @param key
	 *        Key of the persistent object being searched.
	 * 
	 * @return Returns the persistent object with the specified key.
	 *         If there is no {@link PersistentObject} found with the
	 *         specified key, then returns null.
	 * 
	 * @throws RuntimeException
	 *         That wraps any {@link InitializationException} or
	 *         {@link DataException} caught during the read operation.
	 */
	P read(K key) {
		P po = readFromDB(key);
		if ((po!=null) && isCacheEnabled) {
			map.put(key, po);
		}
		return po;
	}

	/**
	 * Reads the persistent object from the database.
	 * 
	 * This operation uses the provider from {@link Bo2Session}.
	 * Therefore this method must be called only in times when
	 * the Bo2Session will have a provider available.
	 * 
	 * 
	 * @param key
	 *        Key of the persistent object being searched.
	 * 
	 * @return Returns the persistent object with the specified key.
	 *         If there is no {@link PersistentObject} found with the
	 *         specified key, then returns null.
	 * 
	 * @throws RuntimeException
	 *         That wraps any {@link InitializationException} or
	 *         {@link DataException} caught during the read operation.
	 */
	P readFromDB(K key) {
		try {
			PersistenceWorker<P> pw = Factory.createPw(poClass);
			Provider provider = Bo2Session.getProvider();
			if(provider==null) {
				throw new RuntimeException("PoCache: NULL Provider on Bo2Session"); //$NON-NLS-1$
			}
			pw.init(provider);
			pw.open();
			P po = Factory.create(poClass);
			po.setKey(key);
			po = pw.read(po);
			pw.close();
			if (modification!=null) {
				po = modification.execute(po);
			}
			return po;
		} catch (PoNotFoundException e) {
			return null;
		} catch (InitializationException e) {
			throw new RuntimeException(e);
		} catch (DataException e) {
			throw new RuntimeException(e);
		}
	}

}
