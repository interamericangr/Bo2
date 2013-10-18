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
package gr.interamerican.wicket.bo2.factories.meta;

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnDropDownChoiceForEntry;
import gr.interamerican.wicket.markup.html.TestPage;

import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;
import org.junit.Test;

/**
 * 
 */
public class TestCachedEntryBoPDComponentFactory extends BaseClassForTestingComponentFactory{	
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
	 * FORMATTER
	 */
	private static final Formatter<Long> FORMATTER = ObjectFormatter.<Long>getInstance();
	/**
	 * CODE
	 */
	private static final Long CODE = 1L;
	/**
	 * 
	 */
	private CachedEntryBoPDComponentFactory cachedEntryBoPDComponentFactory = 
		new CachedEntryBoPDComponentFactory();
	
	/**
	 * 
	 */
	private CachedEntryBoPropertyDescriptor<?,?> cashedDesc = createCacheEntryDescriptor();
	/**
	 * 
	 */
	@Test
	public void testDrawMain_First(){
		Component component = cachedEntryBoPDComponentFactory.drawMain(cashedDesc, TestPage.TEST_ID);
		tester.startPage(testPageSource(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnDropDownChoiceForEntry.class); 
	}
	/**
	 * 
	 */
	@Test
	public void testDrawMain_Second(){
		PropertyModel<Bean1> model = new PropertyModel<Bean1>(bean1,cashedDesc.getName());
		Component component = cachedEntryBoPDComponentFactory.drawMain(TestPage.TEST_ID, model, cashedDesc);
		tester.startPage(testPageSource(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnDropDownChoiceForEntry.class); 

	}
	
	/**
	 * Create CachedEntryBoPropertyDescriptor
	 * @return CachedEntryBoPropertyDescriptor
	 * 
	 */
	public CachedEntryBoPropertyDescriptor<?,?> createCacheEntryDescriptor(){

		 Entry value = new Entry();
		 value.setCode(CODE);
		 value.setTypeId(TYPE);
		 value.setSubTypeId(SUBTYPE);
		 CACHE.put(value);		 		    
		 CachedEntryBoPropertyDescriptor<?,?> cd = 
			 new CachedEntryBoPropertyDescriptor<Entry, Long>(1000L, 1L,CACHE, PARSER, FORMATTER);
		 cd.setName("id"); //$NON-NLS-1$
		 
		 return cd;
	}
	
	
	/**
	 * Entry.
	 */
	private class Entry
	extends TypedSelectableImpl<Long>
	implements TranslatableEntry<Long, Long, Long> {
		
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;

		public String getTranslation(Long languageId) {			
			return getName();
			//+ StringConstants.SPACE + languageId.toString();
		}
		
		public Long getTranslationResourceId() {			
			return getCode();
		}		
	}
	

}
