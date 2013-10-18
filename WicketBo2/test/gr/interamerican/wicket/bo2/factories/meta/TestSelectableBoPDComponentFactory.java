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
import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.ext.descriptors.SelectableBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnDropDownChoiceForSelectable;
import gr.interamerican.wicket.markup.html.TestPage;

import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.model.PropertyModel;
import org.junit.Test;

/**
 * 
 */
public class TestSelectableBoPDComponentFactory extends BaseClassForTestingComponentFactory{
	/**
	 * TYPE
	 */
	private static final Long TYPE = 1000L;
	/**
	 * SUBTYPE
	 */
	private static final Long SUBTYPE = 1L;
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private SelectableBoPropertyDescriptor<Entry> selectableBoPropertyDescriptor =  createSelectableBoPropertyDescriptor();
	/**
	 * 
	 */
	private SelectableBoPDComponentFactory selectableBoPDComponentFactory = new SelectableBoPDComponentFactory();
	
	/**
	 * 
	 */
	@Test
	public void testDrawMain_First(){
		Component component = selectableBoPDComponentFactory.drawMain(selectableBoPropertyDescriptor, TestPage.TEST_ID);
		tester.startPage(testPageSource(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnDropDownChoiceForSelectable.class); 
	}
	
	/**
	 * 
	 */
	@Test
	public void testDrawMain_Second(){
		PropertyModel<Bean1> model = new PropertyModel<Bean1>(bean1,selectableBoPropertyDescriptor.getName());
		Component component = selectableBoPDComponentFactory.drawMain(TestPage.TEST_ID,model, selectableBoPropertyDescriptor);
		tester.startPage(testPageSource(component));
		tester.assertComponent(path(StringConstants.EMPTY), SelfDrawnDropDownChoiceForSelectable.class); 
	}
	
	
	/**
	 * Create MultipleChoiceCachedEntryBoPropertyDescriptor
	 * @return MultipleChoiceCachedEntryBoPropertyDescriptor
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SelectableBoPropertyDescriptor createSelectableBoPropertyDescriptor(){
		 Set<Entry> entriesSet = getEntriesSet();
		 SelectableBoPropertyDescriptor cd = new SelectableBoPropertyDescriptor();
		 cd.setChoices(entriesSet);
		 cd.setName("choices"); //$NON-NLS-1$
		 return cd;
	}

	/**
	 * @return entriesSet
	 */
	private Set<Entry> getEntriesSet() {
		 Set<Entry> entriesSet = new HashSet<Entry>();
		 
		 Entry value_1 = new Entry();
		 value_1.setCode(1L);
		 value_1.setTypeId(TYPE);
		 value_1.setSubTypeId(SUBTYPE);
		 entriesSet.add(value_1);
		 
		 Entry value_2 = new Entry();
		 value_2.setCode(2L);
		 value_2.setTypeId(TYPE);
		 value_2.setSubTypeId(SUBTYPE);
		 entriesSet.add(value_2);
		 
		 Entry value_3 = new Entry();
		 value_3.setCode(3L);
		 value_3.setTypeId(TYPE);
		 value_3.setSubTypeId(SUBTYPE);
		 entriesSet.add(value_3);
		
		 return entriesSet;
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
