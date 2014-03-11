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

import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.samples.utils.meta.ext.EnumElement;
import gr.interamerican.bo2.samples.utils.meta.ext.ObjectType;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.ext.AbstractCacheRelatedTest;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

import org.junit.Test;

/**
 * 
 */
public class TestCachedEntryOwnerBoPropertyDescriptor extends AbstractCacheRelatedTest {

	/**
	 * TYPE
	 */
	private static final Long TYPE = 1000L;
	/**
	 * SUBTYPE
	 */
	private static final Long SUBTYPE = 1L;
	/**
	 * PARSER
	 */
	private static final Parser<Long> PARSER = new LongParser();
	/**
	 * Formatter
	 */
	private static final Formatter<Long> FORMATTER = ObjectFormatter.<Long> getInstance();
	/**
	 * CODE
	 */
	private static final Long CODE = 1L;

	/**
	 * Create CachedEntryBoPropertyDescriptor
	 * 
	 * @return CachedEntryBoPropertyDescriptor
	 */
	public CachedEntryOwnerBoPropertyDescriptor<ObjectType, Long> createDescriptor() {
		EnumElement value = new EnumElement(TYPE, ObjectType.OBJECT1);
		value.setCode(CODE);
		value.setSubTypeId(SUBTYPE);
		cache().put(value);

		CachedEntryOwnerBoPropertyDescriptor<ObjectType, Long> cashedDesc = 
			new CachedEntryOwnerBoPropertyDescriptor<ObjectType, Long>(TYPE, SUBTYPE, TEST_CACHE_NAME, PARSER, FORMATTER);
		return cashedDesc;
	}

	/**
	 * Test parse
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testParse() throws ParseException {
		CachedEntryOwnerBoPropertyDescriptor<ObjectType, Long> cashedDesc = createDescriptor();
		assertNotNull(cashedDesc.parse("1")); //$NON-NLS-1$
	}

	/**
	 * Test parse
	 * 
	 * @throws ParseException
	 */
	@Test(expected = ParseException.class)
	public void testParseFalseValue() throws ParseException {
		CachedEntryOwnerBoPropertyDescriptor<ObjectType, Long> cashedDesc = createDescriptor();
		assertNotNull(cashedDesc.parse("notNumber")); //$NON-NLS-1$
	}

	/**
	 * Test validate
	 * 
	 * @throws ValidationException
	 */
	@Test
	public void testValidate() throws ValidationException {
		CachedEntryOwnerBoPropertyDescriptor<ObjectType, Long> cashedDesc = createDescriptor();
		cashedDesc.validate(ObjectType.OBJECT1);
	}

	/**
	 * Test getValues
	 */
	@Test
	public void testGetValues() {
		CachedEntryOwnerBoPropertyDescriptor<ObjectType, Long> cashedDesc = createDescriptor();
		assertNotNull(cashedDesc.getValues());
	}
	
	/**
	 * Test getFormatter
	 */
	@Test
	public void testGetFormatter() {
		CachedEntryOwnerBoPropertyDescriptor<ObjectType, Long> cashedDesc = createDescriptor();
		assertNotNull(cashedDesc.getFormatter());
	}

}
