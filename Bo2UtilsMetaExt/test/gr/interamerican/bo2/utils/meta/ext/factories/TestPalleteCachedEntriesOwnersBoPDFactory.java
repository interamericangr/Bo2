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
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.samples.utils.meta.ext.EnumElement;
import gr.interamerican.bo2.samples.utils.meta.ext.ObjectType;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.ext.descriptors.MultipleCachedEntriesOwnersBoPropertyDescriptor;
import gr.interamerican.samples.utils.meta.SamplePropertyDefinition;

import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for {@link PalleteCachedEntriesOwnersBoPDFactory}.
 */
public class TestPalleteCachedEntriesOwnersBoPDFactory {
	/**
	 * Cache name.
	 */
	static final String CACHENAME = "TestPalleteCachedEntriesOwnersBoPDFactory"; //$NON-NLS-1$
	
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
		
		EnumElement value = new EnumElement(1000L, ObjectType.OBJECT1);
		
		Cache<Long> cache = CacheRegistry.getRegisteredCache(CACHENAME);
		cache.put(value);

		SamplePropertyDefinition pd = new SamplePropertyDefinition();
		pd.fillSamplePropertyDefinition();
		pd.setCacheName(CACHENAME);
		pd.setListCd(1000L);
		pd.setSubListCd(2000L);
		pd.setDefaultValue("1");
		
		MultipleCachedEntriesOwnersBoPropertyDescriptor<ObjectType, Long> descriptor = PalleteCachedEntriesOwnersBoPDFactory.create(pd);
		Collection<ObjectType> list = descriptor.getDefaultValue();
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
		PalleteCachedEntriesOwnersBoPDFactory.<ObjectType, Long>create(pd);

	}
	
}
