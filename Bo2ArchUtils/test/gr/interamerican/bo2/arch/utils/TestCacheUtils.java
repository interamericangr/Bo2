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
package gr.interamerican.bo2.arch.utils;

import static org.junit.Assert.assertNotNull;
import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

/**
 * 
 */
public class TestCacheUtils {

	
	/**
	 * cache 
	 */
	CacheImpl<Long> cache = new CacheImpl<Long>();
	
	/**
	 * collection
	 */
	Collection<TypedSelectableImpl> selectableCollection  = new ArrayList<TypedSelectableImpl>();
	
	/**
	 * translCollection
	 */
	Collection<TranslatableEntryOwnerImpl> translCollection  = new ArrayList<TranslatableEntryOwnerImpl>();
	
	/**
	 * test loadEntries
	 */
	@Test
	public void testLoadEntries(){
		TypedSelectableImpl typedSelectable = new TypedSelectableImpl();
		typedSelectable.setTypeId(1L);
		typedSelectable.setSubTypeId(1L);
		selectableCollection.add(typedSelectable);
		CacheUtils.loadEntries(cache, selectableCollection);
		assertNotNull((cache.getSubCacheAsList(1l, 1l)));
	}
	
	/**
	 * test load entryOwners
	 */
	@Test
	public void testloadEntryOwners(){
		TranslatableEntryOwnerImpl entryOwner = new TranslatableEntryOwnerImpl();
		translCollection.add(entryOwner);
		CacheUtils.loadEntryOwners(cache, translCollection);
		assertNotNull((cache.getSubCacheAsList(1l, 1l)));
	}
	
	
	
	
	/**
	 * interface to test
	 */
	private interface TypedSelectableSample extends TypedSelectable<Long>{
        //empty
	}
	
	/**
	 * implementation to test
	 */
	private class TypedSelectableImpl implements TypedSelectableSample{

		/**
		 * uid
		 */
		private static final long serialVersionUID = 8233108397841361674L;

		/**
		 * name
		 */
		String name;
		
		/**
		 * code
		 */
		Long code;
		
		/**
		 * typeId
		 */
		Long typeId;
	
		/**
		 * subTypeId
		 */
		Long subTypeId;

		/**
		 * ���������� name.
		 *
		 * @return name
		 */
		public String getName() {
			return name;
		}
		/**
		 * ��������� name.
		 *
		 * @param name 
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * ���������� code.
		 *
		 * @return code
		 */
		public Long getCode() {
			return code;
		}
		/**
		 * ��������� code.
		 *
		 * @param code 
		 */
		public void setCode(Long code) {
			this.code = code;
		}
		/**
		 * ���������� typeId.
		 *
		 * @return typeId
		 */
		public Long getTypeId() {
			return typeId;
		}
		/**
		 * ��������� typeId.
		 *
		 * @param typeId 
		 */
		public void setTypeId(Long typeId) {
			this.typeId = typeId;
		}
		/**
		 * ���������� subTypeId.
		 *
		 * @return subTypeId
		 */
		public Long getSubTypeId() {
			return subTypeId;
		}
		/**
		 * ��������� subTypeId.
		 *
		 * @param subTypeId 
		 */
		public void setSubTypeId(Long subTypeId) {
			this.subTypeId = subTypeId;
		}

		public int compareTo(Codified<Long> o) {
			if(o==null) { return 1; }
			return Utils.nullSafeCompare(o.getCode(), this.getCode());
		}
		
	}
	
	
	/**
	 * interface to test
	 */
	private interface TranslatableEntryOwnerSample extends TranslatableEntryOwner<Long,Long,Long>{
		//empty
	}
	
	
	/**
	 * TranslatableEntryOwnerImpl
	 */
	private class TranslatableEntryOwnerImpl implements TranslatableEntryOwnerSample{


		/**
		 * uid.
		 */
		private static final long serialVersionUID = -401136617322813111L;
		/**
		 * entry
		 */
		Entry entry = new Entry();
		
		public TranslatableEntry<Long, Long, Long> getEntry() {
			entry.setTypeId(1l);
			entry.setSubTypeId(1l);
			return entry;
		}
		
	}
	
	
	/**
	 * Entry.
	 */
	private class Entry
	extends TypedSelectableImpl
	implements TranslatableEntry<Long, Long, Long> {

		/**
		 * uid.
		 */
		private static final long serialVersionUID = 3942702939765163945L;


		public Long getTranslationResourceId() {
			return null;
		}


		public String getTranslation(Long languageId) {
			return null;
		}}
		
}
