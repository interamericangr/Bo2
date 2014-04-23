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
package gr.interamerican.wicket.bo2.factories.meta;

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.samples.utils.meta.Bean1descriptor;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;

import org.junit.Before;

/**
 * 
 */
public class BaseClassForTestingComponentFactory extends Bo2WicketTest{
	/**
	 * 
	 */
	protected static final String COMPONENT = "component"; //$NON-NLS-1$
	
	/**
	 * 
	 */
	protected static final String LABEL_ID = "label"; //$NON-NLS-1$
	
	/**
	 * Bean1.
	 */
	protected Bean1 bean1 = new Bean1();
	/**
	 * Bean1descriptor
	 */
	protected Bean1descriptor bean1descriptor = new Bean1descriptor();
	
	/**
	 * Cache name.
	 */
	protected static final String TEST_CACHE_NAME = "TEST_CACHE"; //$NON-NLS-1$
	
	/**
	 * cache registration fixture.
	 */
	@Before
	public void before() {
		if(CacheRegistry.getRegisteredCache(TEST_CACHE_NAME)!=null) {
			CacheRegistry.getRegisteredCache(TEST_CACHE_NAME).clear();
		} else {
			CacheRegistry.registerCache(TEST_CACHE_NAME, new CacheImpl<Long>(), Long.class);
		}
	}
	
	/**
	 * @return Returns the test cache
	 */
	protected Cache<Long> cache() {
		return CacheRegistry.<Long>getRegisteredCache(TEST_CACHE_NAME);
	}
	
}
