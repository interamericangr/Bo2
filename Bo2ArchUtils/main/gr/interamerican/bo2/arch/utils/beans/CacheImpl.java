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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Cache that can store multiple {@link TypedSelectable} types.
 * @param <C> 
 */
public class CacheImpl<C extends Comparable<? super C>> 
implements Cache<C> {
	
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
	private HashMap<Pair<Long, C>, TypedSelectable<C>> cache = 
		new HashMap<Pair<Long, C>, TypedSelectable<C>>();
	
	/**
	 * Sub-caches.	
	 */
	private HashMap<Pair<Long, Long>, Set<TypedSelectable<C>>> subCaches = 
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

	@SuppressWarnings("unchecked")
	public <T extends TypedSelectable<C>> Set<T> getSubCache(Long typeId,
			Long subTypeId) {
		
		Set<T> set = new HashSet<T>();		
		Set<TypedSelectable<C>> subCache = subCaches.get(subKey(typeId, subTypeId));
		if (subCache!=null) {
			for (TypedSelectable<C> ts : subCache) {				
				set.add((T) ts);				
			}
		}
		return set;
	}
	
	public void put(TypedSelectable<C> value) {
		cache.put(key(value), value);
		Pair<Long, Long> subCacheKey = subKey(value); 
		Set<TypedSelectable<C>> subCache = subCaches.get(subCacheKey);
		if (subCache==null) {
			subCache = new HashSet<TypedSelectable<C>>();
		}
		if(subCache.contains(value)) {
			subCache.remove(value);
		}
		subCache.add(value);
		subCaches.put(subCacheKey, subCache);
	}
	
	public void remove(TypedSelectable<C> value) {		
		if (cache.remove(key(value))!=null) {
			subCaches.get(subKey(value)).remove(value);
		}
	}

	@Deprecated
	public TypedSelectable<C> get(Long typeId, Long subTypeId, C code) {		
		return get(typeId, code);
	}
	
	public TypedSelectable<C> get(Long typeId, C code) {		
		return cache.get(key(typeId, code));
	}	

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
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.ext.Cache#refill(java.lang.Long, java.util.Collection)
	 */
	public void refill(Long typeId,
			Collection<? extends TypedSelectable<C>> values) {
		clearTypeId(typeId);
		for (TypedSelectable<C> value : values) {
			if(value.getTypeId()==typeId) {
				put(value);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.ext.Cache#getSubCacheAsList(java.lang.Long, java.lang.Long)
	 */
	public <T extends TypedSelectable<C>> List<T> getSubCacheAsList(
			Long typeId, Long subTypeId) {
		Set<T> set = getSubCache(typeId, subTypeId);
		List<T> list = new ArrayList<T>();
		list.addAll(set);	
		return list;
	}
	
}

