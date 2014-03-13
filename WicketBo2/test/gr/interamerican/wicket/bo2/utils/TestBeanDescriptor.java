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
package gr.interamerican.wicket.bo2.utils;

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

import java.util.Arrays;

/**
 * 
 */
public class TestBeanDescriptor extends BasicBusinessObjectDescriptor<TestBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * TYPE
	 */
	private static final Long TYPE = 1000L;
	/**
	 * SEC_TYPE
	 */
	private static final Long SEC_TYPE = 1001L;
	/**
	 * THRD_TYPE
	 */
	private static final Long THRD_TYPE = 1002L;
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
	 * FORMATTER
	 */
	private static final Formatter<Long> FORMATTER = ObjectFormatter.<Long> getInstance();
	/**
	 * CODE
	 */
	private static final Long CODE = 1L;

	/**
	 * Creates a new TestBeanDescriptor object.
	 * 
	 */
	public TestBeanDescriptor() {
		super();
		
		if (CacheRegistry.getRegisteredCache(this.getClass().getName()) == null) {
			CacheRegistry.registerCache(this.getClass().getName(), CACHE, Long.class);
		}
		
		BoPropertyDescriptor<?>[] descriptors = { firstAttributeDescriptor(), secondAttributeDescriptor(),
				thirdAttributeDescriptor() };
		this.setPropertyDescriptors(Arrays.asList(descriptors));
	}

	/**
	 * Create CachedEntryBoPropertyDescriptor
	 * 
	 * @return CachedEntryBoPropertyDescriptor
	 */
	public CachedEntryBoPropertyDescriptor<?, ?> firstAttributeDescriptor() {

		Entry value = new Entry();
		value.setCode(CODE);
		value.setTypeId(TYPE);
		value.setSubTypeId(SUBTYPE);
		CACHE.put(value);

		CachedEntryBoPropertyDescriptor<?, ?> cd = new CachedEntryBoPropertyDescriptor<Entry, Long>(1000L, 1L,
				this.getClass().getName(), PARSER, FORMATTER);

		cd.setName("firstAttribute"); //$NON-NLS-1$
		return cd;
	}

	/**
	 * Create CachedEntryBoPropertyDescriptor
	 * 
	 * @return CachedEntryBoPropertyDescriptor
	 */
	public CachedEntryBoPropertyDescriptor<?, ?> secondAttributeDescriptor() {

		Entry value = new Entry();
		value.setCode(CODE);
		value.setTypeId(SEC_TYPE);
		value.setSubTypeId(SUBTYPE);
		CACHE.put(value);
		
		
		CachedEntryBoPropertyDescriptor<?, ?> cd = new CachedEntryBoPropertyDescriptor<Entry, Long>(1001L, 1L,
				this.getClass().getName(), PARSER, FORMATTER);
		cd.setName("secondAttribute"); //$NON-NLS-1$
		return cd;
	}

	/**
	 * Create CachedEntryBoPropertyDescriptor
	 * 
	 * @return CachedEntryBoPropertyDescriptor
	 */
	public CachedEntryBoPropertyDescriptor<?, ?> thirdAttributeDescriptor() {

		Entry value = new Entry();
		value.setCode(CODE);
		value.setTypeId(THRD_TYPE);
		value.setSubTypeId(SUBTYPE);
		CACHE.put(value);
		CachedEntryBoPropertyDescriptor<?, ?> cd = new CachedEntryBoPropertyDescriptor<Entry, Long>(1002L, 1L,
				this.getClass().getName(), PARSER, FORMATTER);
		cd.setName("thirdAttribute"); //$NON-NLS-1$
		return cd;
	}

	/**
	 * Entry.
	 */
	private class Entry extends TypedSelectableImpl<Long> implements TranslatableEntry<Long, Long, Long> {

		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;

		public String getTranslation(Long languageId) {
			return getName();
			// + StringConstants.SPACE + languageId.toString();
		}

		public Long getTranslationResourceId() {
			return getCode();
		}
	}

}
