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

import static org.junit.Assert.*;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit Tests of {@link CacheRegistry}.
 */
public class TestCacheRegistry {
	
	/** cache name. */
	private static String CACHE_NAME="xyzCache"; //$NON-NLS-1$
	
	/**
	 * setups the {@link CacheRegistry}.
	 */
	@BeforeClass
	public static void setupCacheRegistry() {
		Cache<Long> cache = new CacheImpl<Long>();
		CacheRegistry.registerCache(CACHE_NAME, cache, Long.class);
	}
	
	/**
	 * tests getRegisteredCache.
	 */
	@Test
	public void testGetRegisteredCache() {
		Cache<Long> cache = CacheRegistry.<Long>getRegisteredCache(CACHE_NAME);
		assertNotNull(cache);
	}
	
	/**
	 * tests getRegisteredCache.
	 */
	@Test(expected=RuntimeException.class)
	public void testRegisterCache() {
		CacheRegistry.registerCache(CACHE_NAME, null, Long.class);
	}
	
	/**
	 * tests getRegisteredCache.
	 */
	@Test
	public void testGetRegisteredCacheCodeType() {
		Class<?> cacheClass =  CacheRegistry.getRegisteredCacheCodeType(CACHE_NAME);
		assertEquals(Long.class,cacheClass);
	}
	
	/**
	 * tests flushAllCacheContents
	 */
	@Test
	public void testFlushAllCacheContents() {
		Cache<Long> cache = CacheRegistry.<Long>getRegisteredCache(CACHE_NAME);
		cache.put(new TypedSelectableImpl<Long>(1L, 1L, 1L, "s")); //$NON-NLS-1$
		assertEquals("s", cache.get(1L, 1L).getName()); //$NON-NLS-1$
		CacheRegistry.flushAllCacheContents();
		assertNull(cache.get(1L, 1L));
	}
}