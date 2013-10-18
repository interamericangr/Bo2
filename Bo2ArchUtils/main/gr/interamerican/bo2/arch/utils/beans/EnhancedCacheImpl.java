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
import gr.interamerican.bo2.arch.ext.EnhancedCache;
import gr.interamerican.bo2.arch.ext.Translator;
import gr.interamerican.bo2.arch.utils.CacheRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of {@link EnhancedCache}
 * 
 * @param <C> 
 * @param <R> 
 * @param <L> 
 * 
 */
public class EnhancedCacheImpl
<C extends Comparable<? super C>,R extends Comparable<? super R>,L>  
implements gr.interamerican.bo2.arch.ext.EnhancedCache<C,R,L> {
	
	/**
	 * Cache.
	 */
	private Cache<C> cache;
	
	/**
	 * Translator.
	 */
	private Translator<R,L> translator = new TranslatorImpl<R,L>();
	
	/**
	 * modules
	 */
	Map<C, Set<C>> modules = new HashMap<C, Set<C>>();
	
	/**
	 * Creates a new ControlPanelImpl object. 
	 *
	 */
	public EnhancedCacheImpl() {
		super();
		cache = new CacheImpl<C>();
	}
	
	/**
	 * Creates a new ControlPanelImpl object. 
	 * 
	 * @param cacheName Cache name.
	 * @param codeType Type of cache code.
	 */
	public EnhancedCacheImpl(String cacheName, Class<C> codeType) {
		super();
		cache = new CacheImpl<C>();
		CacheRegistry.registerCache(cacheName, cache, codeType);		
	}

	public Cache<C> getCache() {		
		return cache;
	}
	
	public Translator<R,L> getTranslator() {		
		return translator;
	}
	


}
