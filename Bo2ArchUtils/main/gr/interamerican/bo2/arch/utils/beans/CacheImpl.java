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
package gr.interamerican.bo2.arch.utils.beans;

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.beans.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cache that can store multiple {@link TypedSelectable} types.
 * @param <C> 
 */
public class CacheImpl<C extends Comparable<? super C>> 
implements Cache<C>, Serializable {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Logger.
	 */
	static final Logger LOGGER = LoggerFactory.getLogger(CacheImpl.class.getName()); 
	
	/**
	 * Creates a new CacheImpl object.
	 */
	public CacheImpl() {
		super();	
	}

	/**
	 * Default value used in keys instead of null.
	 */
	private static final Long DEFAULT = 0L;
	
	/**
	 * Cache.	
	 */
	private Map<Pair<Long, C>, TypedSelectable<C>> cache = 
		new HashMap<Pair<Long, C>, TypedSelectable<C>>();
	
	/**
	 * Sub-caches.	
	 */
	private Map<Pair<Long, Long>, Set<TypedSelectable<C>>> subCaches = 
		new HashMap<Pair<Long, Long>, Set<TypedSelectable<C>>>();
	
	/**
	 * Creates a unique key for a pair of typeId and subTypeId.
	 *  
	 * @param typeId Type id.
	 * @param subTypeId Sub-type id.
	 * 
	 * @return Returns a key for this pair.
	 */
	private Pair<Long, Long> subKey(Long typeId, Long subTypeId) {
		Long type = Utils.notNull(typeId, DEFAULT);
		Long subtype = Utils.notNull(subTypeId, DEFAULT);
		return new Pair<Long, Long>(type, subtype);
	}
	
	/**
	 * Creates a unique key for the pair of typeId and subTypeId
	 * of a {@link TypedSelectable} value.
	 *  
	 * @param value 
	 * 
	 * @return Returns a key for the sub-cache where this value belongs .
	 */
	private Pair<Long, Long> subKey(TypedSelectable<C> value) {
		return subKey(value.getTypeId(), value.getSubTypeId());
	}
	
	/**
	 * Creates a unique key for a {@link TypedSelectable} value.
	 * 
	 * @param value Value mapped to the key. 
	 * @return Returns a key for this value.
	 */
	private Pair<Long, C> key(TypedSelectable<C> value) {
		return key(value.getTypeId(), value.getCode());
	}
	
	/**
	 * Creates a unique key for a typeId and code.
	 * 
	 * @param typeId Type id 
	 * @param code  code.
	 * 
	 * @return Returns a key for this pair of type id and code.
	 */
	private Pair<Long, C> key(Long typeId, C code) {		
		return new Pair<Long, C>(typeId, code);
	}

	@Override	
	public <T extends TypedSelectable<C>> 
	Set<T> getSubCache(Long typeId,	Long subTypeId) {
		Pair<Long, Long> key = subKey(typeId,subTypeId);
		return getSubset(key, subCaches);
	}
	
	@Override
	@SuppressWarnings("nls")
	public void put(TypedSelectable<C> value) {
		TypedSelectable<C> existing = cache.put(key(value), value);
		if(existing != null) {
			LOGGER.warn("Replaced [" + existing.getTypeId() + "," + existing.getCode() + "].");
		}
		putToSubCache(value);
		putToTypeEntries(value);
		
	}
	
	/**
	 * Puts the specified value to the appropriate set in the
	 * <code>subCaches</code> map.
	 * @param value
	 */
	void putToSubCache(TypedSelectable<C> value) {
		Pair<Long, Long> key = subKey(value); 
		putToSubset(subCaches, value, key);		
	}
	
	/**
	 * Puts the specified value to the appropriate set in the
	 * <code>typeEntries</code> map.
	 * @param value
	 */
	void putToTypeEntries(TypedSelectable<C> value) {
		Pair<Long, Long> key = subKey(value.getTypeId(), SUBTYPEID_FOR_ALL_TYPE_ENTRIES);
		putToSubset(subCaches, value, key);
	}
	
	/**
	 * Puts the specified entry to a sub-set.
	 * 
	 * @param map
	 * @param value
	 * @param key
	 */
	<K> void putToSubset(Map<K, Set<TypedSelectable<C>>> map, TypedSelectable<C> value, K key) {
		Set<TypedSelectable<C>> set = map.get(key);
		if (set==null) {
			set = new HashSet<TypedSelectable<C>>();
			map.put(key, set);
		} else {
			set.remove(value);
		}
		set.add(value);
	}
	
	@Override
	public void remove(TypedSelectable<C> value) {
		Pair<Long, C> k = key(value);
		if (cache.remove(k)!=null) {
			Pair<Long,Long> subCacheKey = subKey(value); 
			subCaches.get(subCacheKey).remove(value);
			Pair<Long,Long> allEntriesKey = 
				subKey(value.getTypeId(), SUBTYPEID_FOR_ALL_TYPE_ENTRIES);
			subCaches.get(allEntriesKey).remove(value);			
		}
	}

	@Override
	@Deprecated
	public TypedSelectable<C> get(Long typeId, Long subTypeId, C code) {		
		return get(typeId, code);
	}
	
	@Override
	public TypedSelectable<C> get(Long typeId, C code) {		
		return cache.get(key(typeId, code));
	}	

	@Override
	public void clear() {
		cache.clear();
		subCaches.clear();
	}
	
	/**
	 * Removes all elements that refer to a set identified by a typeId.
	 * 
	 * @param typeId 
	 */
	private void clearTypeId(Long typeId) {
		Set<Pair<Long, C>> keys = new HashSet<Pair<Long, C>>(cache.keySet());
		for (Pair<Long, C> key : keys) {
			if (key.getLeft().equals(typeId)) {
				cache.remove(key);
			}
		}
		Set<Pair<Long, Long>> subKeys = new HashSet<Pair<Long, Long>>(subCaches.keySet());
		for (Pair<Long, Long> subkey : subKeys) {
			if (subkey.getLeft().equals(typeId)) {
				subCaches.remove(subkey);
			}
		}		
	}
	
	@Override
	public void refill(Long typeId,
			Collection<? extends TypedSelectable<C>> values) {
		clearTypeId(typeId);
		for (TypedSelectable<C> value : values) {
			if(value.getTypeId()==typeId) {
				put(value);
			}
		}
	}
	
	@Override
	public <T extends TypedSelectable<C>> List<T> getSubCacheAsList(
			Long typeId, Long subTypeId) {
		Set<T> set = getSubCache(typeId, subTypeId);
		List<T> list = new ArrayList<T>();
		list.addAll(set);	
		return list;
	}
	
	
	/**
	 * Gets a subset from a map.
	 * 
	 * @param key
	 * @param map
	 * @return Returns the subset.
	 */
	<T extends TypedSelectable<C>, K> 
	Set<T> getSubset(K key, Map<K, Set<TypedSelectable<C>>> map) {
		Set<TypedSelectable<C>> subset = map.get(key);
		if (subset==null) {
			return new HashSet<T>();
		}
		Set<T> set = Utils.cast(subset);
		return new HashSet<T>(set);
	}

	@Override
	public <T extends TypedSelectable<C>> Set<T> getTypeEntries(Long typeId) {
		return getSubCache(typeId, SUBTYPEID_FOR_ALL_TYPE_ENTRIES);
	}
	
}

