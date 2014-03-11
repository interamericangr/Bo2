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
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.samples.utils.meta.ext.EnumElement;
import gr.interamerican.bo2.samples.utils.meta.ext.ObjectType;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.ext.AbstractCacheRelatedTest;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link PalleteCachedEntriesOwnersBoPropertyDescriptor}.
 */
public class TestPalleteCachedEntriesOwnersBoPropertyDescriptor extends AbstractCacheRelatedTest {
	/**
	 * TYPE
	 */
	private static final Long TYPE = 1000L;
	/**
	 * SUBTYPE
	 */
	private static final Long SUBTYPE = 1L;
	/**
	 * CODE 1.
	 */
	private static final Long CODE_1 = 1L;
	/**
	 * CODE 2.
	 */
	private static final Long CODE_2 = 2L;
	/**
	 * CODE 3.
	 */
	private static final Long CODE_3 = 3L;
	
	/**
	 * codesParser
	 */
	static final Parser<Long> codesParser = new LongParser();
	
	/**
	 * Formatter
	 */
	static final Formatter<Long> formatter = ObjectFormatter.<Long>getInstance();
	
	/**
	 * MultipleCachedEntriesBoPropertyDescriptor
	 */
	PalleteCachedEntriesOwnersBoPropertyDescriptor<ObjectType, Long> descriptor = 
		new PalleteCachedEntriesOwnersBoPropertyDescriptor<ObjectType, Long>(TYPE,SUBTYPE,TEST_CACHE_NAME,codesParser, formatter);

	
	/**
	 * initialize
	 */
	@Before
	public void initialize(){		
		EnumElement enumElement = new EnumElement(TYPE, ObjectType.OBJECT1);
		enumElement.setCode(CODE_1);
		enumElement.setSubTypeId(SUBTYPE);
		cache().put(enumElement);
		
		EnumElement enumElement2 = new EnumElement(TYPE, ObjectType.OBJECT2);
		enumElement2.setCode(CODE_2);
		enumElement2.setSubTypeId(SUBTYPE);
		cache().put(enumElement2);
		
		EnumElement enumElement3 = new EnumElement(TYPE, ObjectType.OBJECT3);
		enumElement3.setCode(CODE_3);
		enumElement3.setSubTypeId(SUBTYPE);
		cache().put(enumElement3);
	}
	
	/**
	 * test for {@link PalleteCachedEntriesOwnersBoPropertyDescriptor#parse(String)}.
	 * @throws ParseException 
	 */
	@Test
	public void testParse() throws ParseException{	
		String codes = createCodes();
		Collection<ObjectType> values = descriptor.parse(codes); 
		assertEquals(3, values.size());
	}

	/**
	 * test {@link PalleteCachedEntriesOwnersBoPropertyDescriptor#format(java.util.Collection)}.
	 */
	@Test
	public void testFormat(){
		assertEquals(StringConstants.EMPTY , descriptor.format(null));
		assertEquals(StringConstants.EMPTY , descriptor.format(new ArrayList<ObjectType>()));
		
		List<ObjectType> objectTypesList = new ArrayList<ObjectType>();
		objectTypesList.add(ObjectType.OBJECT1);
		objectTypesList.add(ObjectType.OBJECT2);
		objectTypesList.add(ObjectType.OBJECT3);
		assertEquals(createCodes(), descriptor.format(objectTypesList));
	}

	/**
	 * test {@link PalleteCachedEntriesOwnersBoPropertyDescriptor#getValidator()}.
	 */
	@Test
	public void testGetValidator(){
		Assert.assertNotNull(descriptor.getValidator());
	}
	
	/**
	 * test {@link PalleteCachedEntriesOwnersBoPropertyDescriptor#getSelectableValues()}.
	 */
	@Test
	public void testGetSelectableValues(){
		Set<TranslatableEntryOwner<Long,?, ?>> selectableValues = descriptor.getSelectableValues();
		assertEquals(3, selectableValues.size());
	}

	/**
	 *  Test {@link PalleteCachedEntriesOwnersBoPropertyDescriptor#getFormatter()}.
	 */
	@Test
	public void testGetFormatter(){
		Assert.assertNull(descriptor.getFormatter());
	}

	/**
	 * @return codes of {@link EnumElement}.
	 */
	private String createCodes() {
		StringBuilder builder = new StringBuilder();
		builder.append(CODE_1.toString());
		builder.append(StringConstants.COMMA);
		builder.append(CODE_2.toString());
		builder.append(StringConstants.COMMA);
		builder.append(CODE_3.toString());
		
		String codes = builder.toString();
		return codes;
	}
}
