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
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;
import gr.interamerican.bo2.utils.meta.ext.AbstractCacheRelatedTest;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

import org.junit.Test;

/**
 * Unit test for {@link CachedEntryBoPropertyDescriptor}.
 */
public class TestCachedEntryBoPropertyDescriptor extends AbstractCacheRelatedTest {

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
	private static final Formatter<Long> FORMATTER = ObjectFormatter.<Long>getInstance();
	/**
	 * CODE
	 */
	private static final Long CODE = 1L;

	/**
	 * Create CachedEntryBoPropertyDescriptor
	 * 
	 * @return CachedEntryBoPropertyDescriptor
	 * 
	 */
	public CachedEntryBoPropertyDescriptor<TypedSelectable<Long>, Long> createDescriptor() {
		TypedSelectable<Long> value = new TypedSelectableImpl<Long>();
		value.setCode(CODE);
		value.setTypeId(TYPE);
		value.setSubTypeId(SUBTYPE);
		cache().put(value);

		CachedEntryBoPropertyDescriptor<TypedSelectable<Long>, Long> cashedDesc = 
			new CachedEntryBoPropertyDescriptor<TypedSelectable<Long>, Long>(TYPE, SUBTYPE, TEST_CACHE_NAME, PARSER, FORMATTER);
		return cashedDesc;
	}

	/**
	 * Test parse Parse a value given the code
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testParse() throws ParseException {

		CachedEntryBoPropertyDescriptor<TypedSelectable<Long>, Long> cashedDesc = createDescriptor();
		String value = "1"; //$NON-NLS-1$
		TypedSelectable<Long> typedSelectable = cashedDesc.parse(value);
		assertNotNull(typedSelectable);
	}

	/**
	 * Test parse Parse a value given the code
	 * 
	 * @throws ParseException
	 */
	@Test(expected = ParseException.class)
	public void testParseFalseValue() throws ParseException {

		CachedEntryBoPropertyDescriptor<TypedSelectable<Long>, Long> cashedDesc = createDescriptor();
		String value = "noNumber"; //$NON-NLS-1$
		cashedDesc.parse(value);
	}

	/**
	 * Test validate Checks if value exists in Cashe
	 * 
	 * @throws ValidationException
	 */
	@Test
	public void testValidate() throws ValidationException {
		CachedEntryBoPropertyDescriptor<TypedSelectable<Long>, Long> cashedDesc = createDescriptor();
		TypedSelectable<Long> value = new TypedSelectableImpl<Long>();
		value.setCode(CODE);
		value.setTypeId(TYPE);
		value.setSubTypeId(SUBTYPE);
		cashedDesc.validate(value);
	}

	/**
	 * Test validate Checks if value exists in Cashe
	 * 
	 * @throws ValidationException
	 */
	@Test(expected = ValidationException.class)
	public void testValidateNoInCacheValue() throws ValidationException {
		CachedEntryBoPropertyDescriptor<TypedSelectable<Long>, Long> cashedDesc = createDescriptor();
		TypedSelectable<Long> value = new TypedSelectableImpl<Long>();
		value.setCode(CODE);
		value.setTypeId(2000L);
		value.setSubTypeId(SUBTYPE);
		cashedDesc.validate(value);
	}

	/**
	 * Test getValues Gets the set of possible values for the entry
	 */
	@Test
	public void testGetValues() {
		CachedEntryBoPropertyDescriptor<TypedSelectable<Long>, Long> cashedDesc = createDescriptor();
		assertNotNull(cashedDesc.getValues());
	}
	
	/**
	 * Test getFormatter case 1.
	 */
	@Test
	public void testGetFormatter_case1() {
		CachedEntryBoPropertyDescriptor<TypedSelectable<Long>, Long> cashedDesc = createDescriptor();
		assertNotNull(cashedDesc.getFormatter());
	}

	/**
	 * Test getFormatter case 2.
	 */
	@Test
	public void testGetFormatter_case2() {
		CachedEntryBoPropertyDescriptor<TypedSelectable<Long>, Long> cashedDesc = createDescriptor();
		cashedDesc.setNullAllowed(false);
		assertNotNull(cashedDesc.getFormatter());
	}
}
