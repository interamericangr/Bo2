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

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.OwnedEntry;
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.ext.formatters.CachedEntryOwnerFormatter;
import gr.interamerican.bo2.utils.meta.ext.formatters.nr.NrCachedEntryOwnerFormatter;
import gr.interamerican.bo2.utils.meta.ext.validators.CachedEntryOwnerValidator;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import java.util.HashSet;
import java.util.Set;

/**
 * BoPropertyDescriptor for {@link Cache} entry owners.
 * 
 * @param <T> 
 *        Type of entry owner. 
 * @param <C> 
 *        Type of Cache code.
 */
public class CachedEntryOwnerBoPropertyDescriptor
<T extends TranslatableEntryOwner<C,?,?>,C extends Comparable<? super C>>
extends AbstractCacheRelatedObjectBoPropertyDescriptor<T, C>{
	
	/**
	 * Creates a new TypedSelectableBoPropertyDescriptor object. 
	 *
	 * @param typeId
	 * @param subTypeId
	 * @param cache
	 * @param codeParser
	 * @param codeFormatter
	 */
	public CachedEntryOwnerBoPropertyDescriptor(
	Long typeId, Long subTypeId, Cache<C> cache, Parser<C> codeParser, Formatter<C> codeFormatter) {
		super(typeId, subTypeId, cache, codeParser, codeFormatter);
	}
	
	@Override
	public T parse(String value) throws ParseException {		
		C code = codeParser.parse(value);
		TypedSelectable<C> typedSelectable = cache.get(typeId, code);	
		return getOwner(typedSelectable);
	}
	
	/**
	 * Gets the set of possible values for the entry.
	 * 
	 * @return Returns a set containing the possible values for the entry.
	 */
	public Set<TranslatableEntryOwner<C,?,?>> getValues() {
		Set<TypedSelectable<C>> entries = cache.getSubCache(typeId, subTypeId);
		Set<TranslatableEntryOwner<C, ?, ?>> owners = new HashSet<TranslatableEntryOwner<C,?,?>>();
		for (TypedSelectable<C> typedSelectable : entries) {
			owners.add(getOwner(typedSelectable));
		}
		return owners;
	}
	
	/**
	 * Gets the owner of an entry.
	 * 
	 * @param typedSelectable
	 * @return Returns the owner of an entry.
	 */
	@SuppressWarnings("unchecked")
	private T getOwner(TypedSelectable<C> typedSelectable) {
		OwnedEntry<C, ?, ?> entry = (OwnedEntry<C, ?, ?>) typedSelectable;
		return (T) entry.getOwner();
	}

	@Override
	protected Validator<T> getValidator() {
		return new CachedEntryOwnerValidator<T, C>(cache);
	}

	@Override
	protected Formatter<T> getFormatter() {
		if(isNullAllowed()) {
			return new NrCachedEntryOwnerFormatter<T, C>(codeFormatter);
		}
		return new CachedEntryOwnerFormatter<T, C>(codeFormatter);
	}

}
