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
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryBoPropertyDescriptor;
import gr.interamerican.samples.utils.meta.SamplePropertyDefinition;

import org.junit.Test;

/**
 * 
 */
public class TestCachedEntryBoPDFactory {

	
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
		Cache<String> cache =  new CacheImpl<String>();
		TypedSelectable<String> typed = new TypedSelectableImpl<String>();
		typed.setCode("code1");
		typed.setTypeId(1000L);
		cache.put(typed);
		CacheRegistry.registerCache("myStringCache", cache, String.class);
		
		pd.fillSamplePropertyDefinition();
		pd.setCacheName("myStringCache");
		pd.setListCd(1000L);
		pd.setSubListCd(2000L);
		pd.setDefaultValue("code1");
		CachedEntryBoPropertyDescriptor<TypedSelectable<String>, String> descriptor = CachedEntryBoPDFactory.create(pd);
		
		assertEquals(typed.getCode(),descriptor.getDefaultValue().getCode());
		assertEquals(typed.getTypeId(),descriptor.getDefaultValue().getTypeId());
	}
	

	/**
	 * Test create when fails
	 * @throws ParseException 
	 * 
	 */
	@SuppressWarnings("nls")
	@Test(expected=ParseException.class)
	public void testCreate_fail() throws ParseException{
		
		//register and fill cache
		Cache<Integer> cache =  new CacheImpl<Integer>();
		TypedSelectable<Integer> typed = new TypedSelectableImpl<Integer>();
		typed.setCode(1);
		typed.setTypeId(1000L);
		cache.put(typed);
		CacheRegistry.registerCache("myIntegerCache", cache, Integer.class);
		
		pd.fillSamplePropertyDefinition();
		pd.setCacheName("myIntegerCache");
		pd.setListCd(1000L);
		pd.setSubListCd(2000L);
		pd.setDefaultValue("code1");
		CachedEntryBoPropertyDescriptor<TypedSelectable<String>, String> descriptor = CachedEntryBoPDFactory.create(pd);
		
		assertEquals(typed.getCode(),descriptor.getDefaultValue().getCode());
		assertEquals(typed.getTypeId(),descriptor.getDefaultValue().getTypeId());
	}

}
