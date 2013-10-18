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
package gr.interamerican.bo2.arch.utils.beans;

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.Translator;
import gr.interamerican.bo2.arch.ext.TypedSelectable;

import java.util.HashMap;

/**
 * Implementation of a {@link Translator}.
 * 
 * @param <R> 
 * @param <L> 
 */
public class TranslatorImpl<R extends Comparable<? super R>,L>
implements Translator<R,L> {
	
	/**
	 * Cache with translations.
	 * 
	 * Translations are put in the cache as TypedSelectable objects
	 * languageId is typeId, (there is no sub-type).
	 * resourceId is code. 
	 * translation is name.
	 */
	private Cache<R> cache = new CacheImpl<R>();
	
	/**
	 * Maps language ids with a long used as type id
	 * in the translations cache. 
	 */
	private HashMap<L, Long> languages =
		new HashMap<L, Long>();
	
	
	/* (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.Translator#translate(java.lang.Object, java.lang.Long)
	 */
	public String translate(R resourceId, L languageId) {
		Long langId=lang(languageId);
		if (langId==null) {
			return null;
		}
		TypedSelectable<R> entry = cache.get(langId, resourceId);
		if (entry!=null) {
			return entry.getName();
		}
		return null;
	}
	
	/**
	 * Gets a long used as key for languages in the translations cache.
	 * 
	 * @param languageId
	 * @return Returns a Long id for the language.
	 */
	private Long lang(L languageId) {
		if (languageId instanceof Long) {
			return (Long) languageId;
		} else {
			return languages.get(languageId);
		}
	}
	

	/*
	 * (non-Javadoc)
	 * @see gr.interamerican.bo2.arch.Translator#learn(java.lang.Object, java.lang.Long, java.lang.String)
	 */
	public void learn(
			final R resourceId, 
			final L languageId, 
			final String translation) {
		Long langId = lang(languageId);
		if (langId==null) {
			return;
		}		
		TypedSelectable<R> entry = new TypedSelectableImpl<R>();
		entry.setTypeId(langId);
		entry.setSubTypeId(null);
		entry.setCode(resourceId);
		entry.setName(translation);
		cache.put(entry);		
	}

}
