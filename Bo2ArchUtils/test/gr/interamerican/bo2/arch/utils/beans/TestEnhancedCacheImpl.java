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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;

import org.junit.Test;

/**
 * TestControlPanelImpl
 */
public class TestEnhancedCacheImpl {
	
	/**
	 * ControlPanelImpl
	 */
	EnhancedCacheImpl<Integer, Long, Long> impl = new EnhancedCacheImpl<Integer, Long, Long>();
	
	/**
	 * Test getCache
	 */
	@Test
	public void testGetCache(){
		assertNotNull(impl.getCache());
	}

	/**
	 * Test getTranslator
	 */
	@Test
	public void testGetTranslator(){
		assertNotNull(impl.getTranslator());
	}
	
	/**
	 * Test the creation of a named control panel.
	 */
	@Test
	public void testStringArgConstructor(){
		String cacheName = "TheNameOfTheCache"; //$NON-NLS-1$		
		EnhancedCacheImpl<String, String, String> cp = 
			new EnhancedCacheImpl<String, String, String>(cacheName, String.class);
		Cache<?> registered = CacheRegistry.getRegisteredCache(cacheName);
		assertSame(cp.getCache(), registered);
	}
	
}
