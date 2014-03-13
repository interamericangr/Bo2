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
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.meta.descriptors.AbstractBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.bo2.utils.meta.validators.Validator;

/**
 * BoPropertyDescriptor for properties that have their value selected from
 * a list of objects that is stored in a {@link Cache}.
 * 
 * @param <T>
 *        Type of property described by this descriptor. 
 * @param <C> 
 *        Type of code used to store the values in the cache.
 */
public abstract class AbstractCacheRelatedObjectBoPropertyDescriptor<T, C extends Comparable<? super C>>
extends AbstractBoPropertyDescriptor<T>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * type id.
	 */	
	protected Long typeId;
	
	/**
	 * sub type id;
	 */	
	protected Long subTypeId;
	
	/**
	 * Cache.
	 */
	protected String cacheName;
	
	/**
	 * Parser for cache code.
	 */
	protected Parser<C> codeParser;
	
	/**
	 * Formatter for cache code.
	 */
	protected Formatter<C> codeFormatter;
	
	/**
	 * Cache
	 */
	private transient Cache<C> cache;
	
	/**
	 * Creates a new AbstractCacheRelatedObjectBoPropertyDescriptor object. 
	 *
	 * @param typeId 
	 *        TypeId used to identify the list of valid values in the cache.
	 * @param subTypeId 
	 *        SubTypeId used to identify the list of valid values in the cache.
	 * @param cacheName
	 *        Name of the cache that keeps the possible values of the property.
	 * @param codeParser 
	 *        Parser used to parse the code.
	 * @param codeFormatter
	 *        Formatter used to format the code. 
	 */
	public AbstractCacheRelatedObjectBoPropertyDescriptor(
	Long typeId, Long subTypeId, String cacheName, Parser<C> codeParser, Formatter<C> codeFormatter) {
		super(null);
		this.typeId = typeId;
		this.subTypeId = subTypeId;
		this.cacheName = cacheName;
		this.codeParser = codeParser;
		this.codeFormatter = codeFormatter;
		validators.put(AbstractCacheRelatedObjectBoPropertyDescriptor.class, getCacheRelatedValidator());
	}
	
	/**
	 * Gets a {@link Validator} to validate values assigned to the property.
	 * 
	 * @return Returns the appropriate validator.
	 */
	protected abstract Validator<T> getCacheRelatedValidator();
	
	@Override
	public T valueOf(Number value) {
		Long lvalue = value.longValue();
		String strCode = StringUtils.toString(lvalue);
		try {
			return getParser().parse(strCode);
		} catch (ParseException e) {
			return null;
		}
	}
	
	@Override
	public Parser<T> getParser() {
		throw new IllegalStateException(
			"getParser() must be overriden by concrete sub-classes of "  //$NON-NLS-1$
		  + AbstractCacheRelatedObjectBoPropertyDescriptor.class.getName());
	}
	
	/**
	 * Initializes (if needed) and returns the transient field {@link #cache} 
	 * 
	 * @return returns the {@link Cache} of this AbstractCacheRelatedObjectBoPropertyDescriptor
	 */
	protected Cache<C> cache() {
		if(cache == null) {
			cache = CacheRegistry.getRegisteredCache(cacheName);
		}
		return cache;
	}
	
	/**
	 * @return Returns the cache name of this descriptor.
	 */
	public String getCacheName() {
		return cacheName;
	}
	
	/**
	 * @return Returns the typeId of the entries for the values of this descriptor.
	 */
	public Long getTypeId() {
		return typeId;
	}
	
}
