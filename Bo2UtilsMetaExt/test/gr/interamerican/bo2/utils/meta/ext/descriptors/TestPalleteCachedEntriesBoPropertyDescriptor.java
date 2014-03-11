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
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.ext.AbstractCacheRelatedTest;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link PalleteCachedEntriesBoPropertyDescriptor}.
 */
public class TestPalleteCachedEntriesBoPropertyDescriptor extends AbstractCacheRelatedTest {
	/**
	 * TYPE_ID
	 */
	static final Long TYPE_ID = 1L;
	
	/**
	 * SUBTYPE_ID
	 */
	static final Long SUBTYPE_ID = 1000l;
	
	/**
	 * codesParser
	 */
	Parser<Long> codesParser = new  LongParser();
	/**
	 * Formatter
	 */
	Formatter<Long> formatter = ObjectFormatter.<Long>getInstance();
	
	/**
	 * MultipleCachedEntriesBoPropertyDescriptor
	 */
	PalleteCachedEntriesBoPropertyDescriptor<TypedSelectable<Long>, Long> descriptor = 
		new PalleteCachedEntriesBoPropertyDescriptor<TypedSelectable<Long>, Long>(TYPE_ID,SUBTYPE_ID,TEST_CACHE_NAME,codesParser, formatter);
	
	/**
	 * TypedSelectable
	 */
	TypedSelectable<Long> typed = new TypedSelectableImpl<Long>();
	
	/**
	 * TypedSelectable
	 */
	TypedSelectable<Long> typed2 = new TypedSelectableImpl<Long>();
	
	/**
	 */
	@Before
	public void initialize(){
		typed.setCode(1L);
		typed.setTypeId(1L);
		typed.setSubTypeId(1000L);
		cache().put(typed);
		
		typed2.setCode(2L);
		typed2.setTypeId(2L);
		typed2.setTypeId(2000L);
		cache().put(typed2);
	}
	
	/**
	 * test Parse
	 * @throws ParseException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testParse() throws ParseException{
		Collection<TypedSelectable<Long>> list = descriptor.parse("1,2");
		assertEquals(2,list.size());
	}
	
	/**
	 * test Validate
	 * @throws ValidationException 
	 */
	@Test
	public void testValidate() throws ValidationException{
		List<TypedSelectable<Long>> list = new ArrayList<TypedSelectable<Long>>();
		list.add(typed);
		list.add(typed2);
		descriptor.validate(list);
	}
	
	/**
	 * test getSelectableValues
	 */
	@Test
	public void testGetSelectableValues(){
		Set<TypedSelectable<Long>> selectableSet = descriptor.getSelectableValues();
		assertEquals(selectableSet.size(),1);
	}
	
	/**
	 * test format()
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFormat() {
		assertEquals(StringConstants.EMPTY , descriptor.format(null));
		assertEquals(StringConstants.EMPTY , descriptor.format(new ArrayList<TypedSelectable<Long>>()));
		
		Set<TypedSelectable<Long>> selectableSet = descriptor.getSelectableValues();
		assertEquals("1", descriptor.format(selectableSet));
	}
}
