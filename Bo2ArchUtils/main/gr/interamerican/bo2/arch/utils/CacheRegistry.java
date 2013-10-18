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
package gr.interamerican.bo2.arch.utils;

import gr.interamerican.bo2.arch.ext.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Cache registry keeps all registered caches.
 */
public class CacheRegistry {
	
	/**
	 * Hidden constructor.
	 */
	private CacheRegistry() {/* empty */}

	/**
	 * Map with all registered caches.
	 */
	private static Map<String, Cache<?>> registry = new HashMap<String, Cache<?>>();
	
	/**
	 * Map with all registered caches code type.
	 */
	private static Map<String, Class<?>> codeTypeRegistry = new HashMap<String, Class<?>>();
	
	/**
	 * Gets a cache that has been registered with a specified name.
	 * @param <C>
	 *        Type of cache code.
	 * @param name Name of cache searched in registry.
	 * @return Gets the cache with the specified name.
	 */
	@SuppressWarnings("unchecked")
	public static <C extends Comparable<? super C>> Cache<C> getRegisteredCache(String name) {
		return (Cache<C>) registry.get(name);
	}
	
	/**
	 * Gets a cache code type, for the cache that has been registered
	 * with the specified name.
	 * 
	 * @param <C>
	 *        Type of cache code.
	 * @param name Name of cache searched in registry.
	 * @return Gets the code type for the cache with the specified name.
	 */
	@SuppressWarnings("unchecked")
	public static <C extends Comparable<? super C>> Class<C> getRegisteredCacheCodeType(String name) {
		return (Class<C>) codeTypeRegistry.get(name);
	}

	/**
	 * Registers a cache with a name.
	 * 
	 * If another cache with the same name exists, then a 
	 * RuntimeException will be thrown.
	 * 
	 * @param <C> Cache code type. 
	 * @param name Cache name.
	 * @param cache Cache to register.
	 * @param codeType Type of Cache code.
	 */
	public static <C extends Comparable<? super C>> void  
	registerCache(String name, Cache<C> cache, Class<C> codeType) {		
		Cache<?> old = registry.get(name);
		if (old!=null) {			
			String message = "Already exists cache with the same name " //$NON-NLS-1$
				           + name;
			throw new RuntimeException(message);
		}
		registry.put(name, cache);
		codeTypeRegistry.put(name, codeType);
	}
	
}
