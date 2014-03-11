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

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.ext.descriptors.MultipleCachedEntriesBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnMultipleChoiceForEntry;
import gr.interamerican.wicket.markup.html.TestPage;

import java.util.ArrayList;

import org.apache.wicket.Component;
import org.apache.wicket.model.util.ListModel;
import org.junit.Test;

/**
 * 
 */
public class TestMultipleChoiceCachedEntryBoPDFactory extends BaseClassForTestingComponentFactory{	
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
	 * FORMATTER
	 */
	private static final Formatter<Long> FORMATTER = ObjectFormatter.<Long>getInstance();
	
	/**
	 * 
	 */
	private MultipleCachedEntriesBoPDComponentFactory multipleChoiceCachedEntryBoPDFactory = 
		new MultipleCachedEntriesBoPDComponentFactory();
	
	/**
	 * 
	 */
	private MultipleCachedEntriesBoPropertyDescriptor<?,?> multipleChoiceDesc = createMultipleChoiceDescriptor();
	/**
	 * 
	 */
	@Test
	public void testDrawMain_First(){
		Component component = multipleChoiceCachedEntryBoPDFactory.drawMain(multipleChoiceDesc, TestPage.TEST_ID);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnMultipleChoiceForEntry.class); 
	}
	
	/**
	 * 
	 */
	@Test
	public void testDrawMain_Sec(){
		Component component = multipleChoiceCachedEntryBoPDFactory.drawMain(TestPage.TEST_ID, new ListModel<Entry>(new ArrayList<Entry>()),multipleChoiceDesc);
		tester.startPage(getTestPage(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnMultipleChoiceForEntry.class); 
	}
	
	
	/**
	 * Create MultipleChoiceCachedEntryBoPropertyDescriptor
	 * @return MultipleChoiceCachedEntryBoPropertyDescriptor
	 * 
	 */
	public MultipleCachedEntriesBoPropertyDescriptor<?,?> createMultipleChoiceDescriptor(){

		 Entry value_1 = new Entry();
		 value_1.setCode(1L);
		 value_1.setTypeId(TYPE);
		 value_1.setSubTypeId(SUBTYPE);
		 cache().put(value_1);
		 
		 Entry value_2 = new Entry();
		 value_2.setCode(2L);
		 value_2.setTypeId(TYPE);
		 value_2.setSubTypeId(SUBTYPE);
		 cache().put(value_2);
		 
		 Entry value_3 = new Entry();
		 value_3.setCode(3L);
		 value_3.setTypeId(TYPE);
		 value_3.setSubTypeId(SUBTYPE);
		 cache().put(value_3);
		 
		 MultipleCachedEntriesBoPropertyDescriptor<?,?> cd = new MultipleCachedEntriesBoPropertyDescriptor<Entry, Long>(
        		 1000L, 1L,TEST_CACHE_NAME, PARSER, FORMATTER);
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
