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
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.ext.validators.CachedEntriesValidator;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * BoPropertyDescriptor for multiple cached objects. This descriptor
 * describes a List of cached objects selected from a selection set.
 * 
 * The string representation of a property described by this descriptor
 * is a comma-separated ordered list of the codes of the entries.
 *  
 * @param <T> 
 *        Type of entry.
 * @param <C> 
 *        Type of cached entry code.
 */
public class MultipleCachedEntriesBoPropertyDescriptor 
<T extends TypedSelectable<C>, C extends Comparable<? super C>>
extends AbstractCacheRelatedObjectBoPropertyDescriptor<Collection<T>, C> {

	/**
	 * Creates a new MultipleChoiceCachedEntryBoPropertyDescriptor object. 
	 *
	 * @param typeId
	 * @param subTypeId
	 * @param cache
	 * @param codeParser
	 * @param codeFormatter 
	 */
	public MultipleCachedEntriesBoPropertyDescriptor(
	Long typeId, Long subTypeId, Cache<C> cache, Parser<C> codeParser, Formatter<C> codeFormatter) {
		super(typeId, subTypeId, cache, codeParser, codeFormatter);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> parse(String value) throws ParseException {
		String[] tokens = TokenUtils.splitTrim(value, StringConstants.COMMA);
		List<T> list = new ArrayList<T>();
		for(String token : tokens) {
			C code = codeParser.parse(token);
			list.add((T) cache.get(typeId, code));
		}
		return list;
	}
	
	@Override
	public String format(Collection<T> value) {
		if(CollectionUtils.isNullOrEmpty(value)) {
			return StringConstants.EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		for(TypedSelectable<C> entry : value) {
			if(entry==null) {
				sb.append(StringConstants.NULL);
			}
			sb.append(codeFormatter.format(entry.getCode()));
			sb.append(StringConstants.COMMA);
		}
		sb.setLength(sb.length()-1); // omit last comma
		return sb.toString();
	}
	
	@Override
	protected Validator<Collection<T>> getValidator() {
		return new CachedEntriesValidator<T, C>(cache);
	}
	
	/**
	 * Returns all the values that can be selected.
	 * 
	 * @return Returns a set containing all the values that can be selected.
	 */
	public Set<TypedSelectable<C>> getSelectableValues() {
		return cache.getSubCache(typeId, subTypeId);
	}

	@Override
	protected Formatter<Collection<T>> getFormatter() {
		/*
		 * Let format() be overridden without call to super.format().
		 */
		return null;
	}
	
}
