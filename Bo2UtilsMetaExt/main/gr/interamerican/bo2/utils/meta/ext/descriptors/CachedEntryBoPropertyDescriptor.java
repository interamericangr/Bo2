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
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.ext.formatters.CachedEntryFormatter;
import gr.interamerican.bo2.utils.meta.ext.formatters.nr.NrCachedEntryFormatter;
import gr.interamerican.bo2.utils.meta.ext.parsers.CachedEntryParser;
import gr.interamerican.bo2.utils.meta.ext.validators.CachedEntryValidator;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.validators.Validator;

import java.util.Set;

/**
 * BoPropertyDescriptor for {@link Cache} entries.
 * 
 * @param <T> 
 *        Type of entry.
 * @param <C> 
 *        Type of Cache code.
 */
public class CachedEntryBoPropertyDescriptor<T extends TypedSelectable<C>, C extends Comparable<? super C>>
extends AbstractCacheRelatedObjectBoPropertyDescriptor<T, C> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new CachedEntryBoPropertyDescriptor object. 
	 *
	 * @param typeId
	 * @param subTypeId
	 * @param cacheName
	 * @param codeParser
	 * @param codeFormatter 
	 */
	public CachedEntryBoPropertyDescriptor(
	Long typeId, Long subTypeId, String cacheName, Parser<C> codeParser, Formatter<C> codeFormatter) {
		super(typeId, subTypeId, cacheName, codeParser, codeFormatter);
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T parse(String value) throws ParseException {
		C code = codeParser.parse(value);
		return (T) cache().get(typeId, code);
	}
	
	/**
	 * Gets the set of possible values for the entry.
	 * 
	 * @return Returns a set containing the possible values for the entry.
	 */
	public Set<T> getValues() {
		return cache().getSubCache(typeId, subTypeId);
	}
	
	@Override
	protected Validator<T> getCacheRelatedValidator() {
		return new CachedEntryValidator<T, C>(cacheName);
	}
	
	@Override
	public Parser<T> getParser() {
		return new CachedEntryParser<T, C>(cacheName, typeId, codeParser);
	}

	@Override
	protected Formatter<T> getFormatter() {
		if(isNullAllowed()) {
			return new NrCachedEntryFormatter<T, C>(codeFormatter);
		}
		return new CachedEntryFormatter<T, C>(codeFormatter);
	}

}
