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
package gr.interamerican.bo2.utils.meta.ext.factories;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.ext.descriptors.PalleteCachedEntriesBoPropertyDescriptor;
import gr.interamerican.samples.utils.meta.SamplePropertyDefinition;

import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for {@link PalleteCachedEntriesBoPDFactory}.
 */
public class TestPalleteCachedEntriesBoPDFactory {
	/**
	 * Cache name.
	 */
	static final String CACHENAME = "TestPalleteCachedEntriesBoPDFactory"; //$NON-NLS-1$
	
	/**
	 * Tests initialization.
	 */
	@BeforeClass
	public static void initializeCache() {
		Cache<Long> cache = new CacheImpl<Long>();
		CacheRegistry.registerCache(CACHENAME, cache, Long.class);
	}
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreate() throws ParseException{

		TypedSelectable<Long> typed = new TypedSelectableImpl<Long>();
		typed.setCode(1L);
		typed.setTypeId(1000L);
		Cache<Long> cache = CacheRegistry.getRegisteredCache(CACHENAME);
		cache.put(typed);
		
		SamplePropertyDefinition pd =  new SamplePropertyDefinition();		
		pd.fillSamplePropertyDefinition();
		pd.setCacheName(CACHENAME);
		pd.setDefaultValue("1");
		PalleteCachedEntriesBoPropertyDescriptor<TypedSelectable<Long>, Long> descriptor = PalleteCachedEntriesBoPDFactory.create(pd);
		Collection<TypedSelectable<Long>> list = descriptor.getDefaultValue();
		assertEquals(1,list.size());
	}
	
	/**
	 * test create when fails
	 * @throws ParseException 
	 * 
	 */
	@SuppressWarnings("nls")
	@Test(expected = ParseException.class)
	public void testCreate_fails() throws ParseException{
		SamplePropertyDefinition pd =  new SamplePropertyDefinition();		
		pd.fillSamplePropertyDefinition();	
		pd.setCacheName(CACHENAME);
		pd.setListCd(1000L);
		pd.setSubListCd(2000L);
		pd.setDefaultValue("notLong");
		PalleteCachedEntriesBoPDFactory.<TypedSelectable<Long>, Long>create(pd);

	}
}
