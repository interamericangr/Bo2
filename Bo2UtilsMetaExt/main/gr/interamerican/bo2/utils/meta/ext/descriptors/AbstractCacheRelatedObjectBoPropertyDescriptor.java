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
import gr.interamerican.bo2.utils.meta.descriptors.AbstractBoPropertyDescriptor;
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
	protected Cache<C> cache;
	
	/**
	 * Parser for cache code.
	 */
	protected Parser<C> codeParser;
	
	/**
	 * Formatter for cache code.
	 */
	protected Formatter<C> codeFormatter;
	
	/**
	 * Creates a new AbstractCacheRelatedObjectBoPropertyDescriptor object. 
	 *
	 * @param typeId 
	 *        TypeId used to identify the list of valid values in the cache.
	 * @param subTypeId 
	 *        SubTypeId used to identify the list of valid values in the cache.
	 * @param cache
	 *        {@link Cache} that keeps the possible values of the property.
	 * @param codeParser 
	 *        Parser used to parse the code.
	 * @param codeFormatter
	 *        Formatter used to format the code. 
	 */
	public AbstractCacheRelatedObjectBoPropertyDescriptor(
	Long typeId, Long subTypeId, Cache<C> cache, Parser<C> codeParser, Formatter<C> codeFormatter) {
		super(null);
		this.typeId = typeId;
		this.subTypeId = subTypeId;
		this.cache = cache;
		this.codeParser = codeParser;
		this.codeFormatter = codeFormatter;
		validators.put(AbstractCacheRelatedObjectBoPropertyDescriptor.class, getValidator());
	}
	
	/**
	 * Gets a {@link Validator} to validate values assigned to the property.
	 * 
	 * @return Returns the appropriate validator.
	 */
	protected abstract Validator<T> getValidator();

}
