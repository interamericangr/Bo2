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
package gr.interamerican.bo2.utils.meta.ext.descriptors;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.parsers.StringParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link MultipleCachedEntriesBoPropertyDescriptor}.
 */
public class TestMultipleCachedEntriesBoPropertyDescriptor {

	/**
	 * TYPE_ID
	 */
	static final Long TYPE_ID = 1L;
	
	/**
	 * SUBTYPE_ID
	 */
	static final Long SUBTYPE_ID = 1000l;
	
	/**
	 * cache
	 */
	Cache<String> cache =  new CacheImpl<String>();
	
	/**
	 * codesParser
	 */
	Parser<String> codesParser = new  StringParser();
	/**
	 * Formatter
	 */
	Formatter<String> formatter = ObjectFormatter.<String>getInstance();
	
	/**
	 * MultipleCachedEntriesBoPropertyDescriptor
	 */
	MultipleCachedEntriesBoPropertyDescriptor<TypedSelectable<String>, String> descriptor = 
		new MultipleCachedEntriesBoPropertyDescriptor<TypedSelectable<String>, String>(TYPE_ID,SUBTYPE_ID,cache,codesParser, formatter);
	
	/**
	 * TypedSelectable
	 */
	TypedSelectable<String> typed = new TypedSelectableImpl<String>();
	
	/**
	 * TypedSelectable
	 */
	TypedSelectable<String> typed2 = new TypedSelectableImpl<String>();
	
	/**
	 */
	@SuppressWarnings("nls")
	@Before
	public void initialize(){
		
		typed.setCode("code1");
		typed.setTypeId(1L);
		typed.setSubTypeId(1000L);
		cache.put(typed);
		
		typed2.setCode("code2");
		typed2.setTypeId(2L);
		typed2.setTypeId(2000L);
		cache.put(typed2);
	}
	
	/**
	 * test Parse
	 * @throws ParseException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testParse() throws ParseException{
	
		CacheRegistry.registerCache("myCache", cache, String.class);
		List<TypedSelectable<String>> list = descriptor.parse("code1,code2");
		assertEquals(2,list.size());
	}
	
	/**
	 * test Validate
	 * @throws ValidationException 
	 */
	@Test
	public void testValidate() throws ValidationException{
		
		List<TypedSelectable<String>> list = new ArrayList<TypedSelectable<String>>();
		list.add(typed);
		list.add(typed2);
		descriptor.validate(list);
	}
	
	/**
	 * test getSelectableValues
	 */
	@Test
	public void testGetSelectableValues(){
		Set<TypedSelectable<String>> selectableSet = descriptor.getSelectableValues();
		assertEquals(selectableSet.size(),1);
	}
	
	
	
	/**
	 * test format()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFormat() {
		assertEquals(StringConstants.EMPTY , descriptor.format(null));
		assertEquals(StringConstants.EMPTY , descriptor.format(new ArrayList<TypedSelectable<String>>()));
		
		Set<TypedSelectable<String>> selectableSet = descriptor.getSelectableValues();
		assertEquals("code1", descriptor.format(selectableSet));
	}
	
}
