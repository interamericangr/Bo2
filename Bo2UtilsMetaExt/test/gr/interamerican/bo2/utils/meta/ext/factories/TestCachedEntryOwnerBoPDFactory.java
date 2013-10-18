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
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryOwnerBoPropertyDescriptor;
import gr.interamerican.samples.utils.meta.SamplePropertyDefinition;

import org.junit.Test;

/**
 * 
 */
public class TestCachedEntryOwnerBoPDFactory {

	
	/**
	 * SamplePropertyDefinition
	 */
	SamplePropertyDefinition pd =  new SamplePropertyDefinition();
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCreate() throws ParseException{
		
		//register and fill cache
		Cache<Long> cache =  new CacheImpl<Long>();
		EnumElement value = new EnumElement(1000L, ObjectType.OBJECT1);
		cache.put(value);
		CacheRegistry.registerCache("mySelectableCache", cache, Long.class);
		
		pd.fillSamplePropertyDefinition();
		pd.setCacheName("mySelectableCache");
		pd.setListCd(1000L);
		pd.setSubListCd(2000L);
		pd.setDefaultValue("1");
		CachedEntryOwnerBoPropertyDescriptor<ObjectType, Long> descriptor = CachedEntryOwnerBoPDFactory.create(pd);
		
		assertEquals(new Long(1), descriptor.getDefaultValue().getEntry().getCode());

	}
	

	/**
	 * test create when fails
	 * @throws ParseException 
	 * 
	 */
	@SuppressWarnings("nls")
	@Test(expected = ParseException.class)
	public void testCreate_fails() throws ParseException{
		//register and fill cache
		Cache<Long> cache =  new CacheImpl<Long>();
		EnumElement value = new EnumElement(1000L, ObjectType.OBJECT1);
		cache.put(value);
		CacheRegistry.registerCache("mySelectableCache2", cache, Long.class);
		
		pd.fillSamplePropertyDefinition();
		pd.setCacheName("mySelectableCache2");
		pd.setListCd(1000L);
		pd.setSubListCd(2000L);
		pd.setDefaultValue("noSelectableValue");
		CachedEntryOwnerBoPDFactory.<ObjectType, Long>create(pd);

	}
	
	

}
