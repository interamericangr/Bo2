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
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.ext.descriptors.PalleteCachedEntriesBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnPalleteForEntries;
import gr.interamerican.wicket.markup.html.TestPage;

import org.apache.wicket.Component;
import org.apache.wicket.model.util.ListModel;
import org.junit.Test;

/**
 * 
 */
public class TestPalleteCachedEntriesForBoPDComponentFactory extends BaseClassForTestingComponentFactory{	
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
	 * 
	 */
	private PalleteCachedEntriesForBoPDComponentFactory palleteCachedEntriesForBoPDComponentFactory = 
		new PalleteCachedEntriesForBoPDComponentFactory();
	
	/**
	 * 
	 */
	private PalleteCachedEntriesBoPropertyDescriptor<?,?> multipleChoiceDesc = createPalleteCachedEntriesDescriptor();
		
	/**
	 * 
	 */
	@Test
	public void testDrawMain_First(){
		Component component = palleteCachedEntriesForBoPDComponentFactory.drawMain(multipleChoiceDesc, TestPage.TEST_ID);
		tester.startPage(testPageSource(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnPalleteForEntries.class); 
	}
	
	/**
	 * 
	 */
	@Test
	public void testDrawMain_Sec(){
//		PropertyModel<Bean1> model = new PropertyModel<Bean1>(bean1,multipleChoiceDesc.getName());
		ListModel<Long> model = new ListModel<Long>(bean1.getMultipleChoiceField());
		Component component = palleteCachedEntriesForBoPDComponentFactory.drawMain(TestPage.TEST_ID, model,multipleChoiceDesc);
		tester.startPage(testPageSource(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnPalleteForEntries.class); 
	}
	
	
	/**
	 * Create MultipleChoiceCachedEntryBoPropertyDescriptor
	 * @return MultipleChoiceCachedEntryBoPropertyDescriptor
	 * 
	 */
	public PalleteCachedEntriesBoPropertyDescriptor<?,?> createPalleteCachedEntriesDescriptor(){

		 Entry value_1 = new Entry();
		 value_1.setCode(1L);
		 value_1.setTypeId(TYPE);
		 value_1.setSubTypeId(SUBTYPE);
		 CACHE.put(value_1);
		 
		 Entry value_2 = new Entry();
		 value_2.setCode(2L);
		 value_2.setTypeId(TYPE);
		 value_2.setSubTypeId(SUBTYPE);
		 CACHE.put(value_2);
		 
		 Entry value_3 = new Entry();
		 value_3.setCode(3L);
		 value_3.setTypeId(TYPE);
		 value_3.setSubTypeId(SUBTYPE);
		 CACHE.put(value_3);
		 
		 PalleteCachedEntriesBoPropertyDescriptor<?,?> cd = new PalleteCachedEntriesBoPropertyDescriptor<Entry, Long>(
        		 1000L, 1L,CACHE, PARSER, FORMATTER);
		 cd.setName("multipleChoiceField"); //$NON-NLS-1$
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
		}
		
		public Long getTranslationResourceId() {			
			return getCode();
		}		
	}

}
