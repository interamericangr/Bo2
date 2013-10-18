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
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import org.junit.Test;

/**
 * Unit test for {@link AbstractCacheRelatedObjectBoPropertyDescriptor}.
 */
public class TestAbstractCacheRelatedObjectBoPropertyDescriptor {
	/**
	 * TYPE
	 */
	private static final Long TYPE = 1000L;
	/**
	 * SUBTYPE
	 */
	private static final Long SUBTYPE = 1L;
	/**
	 * CACHE
	 */
	private static final Cache<Long> CACHE = new CacheImpl<Long>();
	/**
	 * PARSER
	 */
	private static final Parser<Long> PARSER = new LongParser();
	/**
	 * Formatter
	 */
	private static final Formatter<Long> FORMATTER = ObjectFormatter.<Long>getInstance();

	/**
	 * Test constructor
	 */
	@Test
	public void testConstructor(){
		Descriptor d = new Descriptor();
		assertEquals(TYPE, d.typeId);
		assertEquals(SUBTYPE, d.subTypeId);
		assertEquals(CACHE, d.cache);
		assertEquals(PARSER, d.codeParser);
		assertEquals(FORMATTER, d.codeFormatter);
	}	
	
	/**
	 * Implementation of descriptor for the tests.
	 */
	private class Descriptor extends AbstractCacheRelatedObjectBoPropertyDescriptor<TypedSelectableImpl<Long>, Long> {
		/**
		 * Creates a new Descriptor object. 
		 */
		public Descriptor() {
			super(TYPE,SUBTYPE,CACHE,PARSER,FORMATTER);			
		}

		@Override
		protected Validator<TypedSelectableImpl<Long>> getValidator() {
			return null;
		}
		
		@Override
		protected Formatter<TypedSelectableImpl<Long>> getFormatter() {
			return null;
		}
	}
	
}
