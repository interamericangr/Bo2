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
package gr.interamerican.bo2.impl.open.hibernate.types;

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.Selectable;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.impl.open.utils.Exceptions;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hibernate.HibernateException;

/**
 * Custom type used for lists of {@link TypedSelectable} entries.
 * The entries codes are comma-separated.
 * 
 * @param <C> 
 *        Type of code.
 */
public abstract class EntryListUserType<C extends Comparable<? super C>> 
extends AbstractUserType {
	
	/**
	 * Error message for uninitialized cache.
	 */
	private static final String CACHE_NOT_INITIALIZED = "EntryListUserType.CACHE_NOT_INITIALIZED"; //$NON-NLS-1$
	
	/**
	 * Error message for not existing cached object.
	 */
	private static final String NOT_EXISTING_CACHED_OBJECT = "EntryListUserType.NOT_EXISTING_CACHED_OBJECT"; //$NON-NLS-1$
	
	/**
	 * Error message for invalid input.
	 */
	private static final String INVALID_INPUT = "EntryListUserType.INVALID_INPUT"; //$NON-NLS-1$

	/**
	 * type id
	 */
	private Long typeId;

	/**
	 * Cache name.
	 */
	private String cacheName;
	
	@SuppressWarnings({ "unchecked", "nls" })
	public void setParameterValues(Properties parameters) {
		String strTypeId = parameters.getProperty("entryTypeId");
		this.typeId = NumberUtils.string2Long(strTypeId);
		this.cacheName = parameters.getProperty("cacheName");
	}

	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	public Class<?> returnedClass() {
		return ArrayList.class;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		String csv = rs.getString(names[0]);
		return convertStringToObject(csv);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		st.setString(index, convertObjectToString(value));
	}

	public String objectToSQLString(Object value) {
		return convertObjectToString(value);
	}

	public String toXMLString(Object value) {
		return convertObjectToString(value);
	}

	public Object fromXMLString(String xmlValue) {
		return convertStringToObject(xmlValue);
	}
	
	/**
	 * Parses a code from its string representation.
	 * 
	 * @param code
	 *        Code to parse
	 *        
	 * @return Returns the parsed code value.
	 */
	protected abstract C parseCode(String code);
	
	/**
	 * Coverts the persisted csv to a List of codes.
	 * 
	 * @param csv
	 *        Comma separated value.
	 *        
	 * @return Returns the persisted List of codes.
	 */
	private List<C> getCodes(String csv) {
		List<C> codes = new ArrayList<C>();
		if(csv!=null) {
			String[] tokens = TokenUtils.splitTrim(csv, StringConstants.COMMA);
			for(String token : tokens) {
				C code = parseCode(token);
				codes.add(code);
			}
		}
		return codes;
	}
	
	/**
	 * Converts the persisted comma-separated value to the corresponding
	 * ArrayList of {@link TypedSelectable} entries.
	 * 
	 * @param value
	 *        Persisted csv.
	 *        
	 * @return Returns an ArrayList of TypedSelectable. 
	 */
	@SuppressWarnings("nls")
	private ArrayList<TypedSelectable<C>> convertStringToObject(String value) {
		List<C> codes = getCodes(value);
		ArrayList<TypedSelectable<C>> results = new ArrayList<TypedSelectable<C>>();
		for(C code : codes) {
			TypedSelectable<C> result = this.cache().get(typeId, code);
			if(result == null) {
				String msg = "(" + typeId + "," + code + ")";
				throw Exceptions.runtime(NOT_EXISTING_CACHED_OBJECT, msg);
			}
			results.add(result);
		}
		return results;
	}
	
	/**
	 * Converts the object value to its String representation.
	 * In this case, a {@link List} of {@link Selectable} elements is
	 * converted to a String with its codes separated by commas.
	 *  
	 * @param value
	 *        Object handled by this UserType.
	 *        
	 * @return Returns the String representation.
	 */
	@SuppressWarnings("rawtypes")
	private String convertObjectToString(Object value) {
		if(value==null) {
			return StringConstants.EMPTY;
		}
		
		if(!(value instanceof List)) {
			throw Exceptions.runtime(EntryListUserType.INVALID_INPUT, value);
		}
		
		List input = (List) value;
		Object[] codes = new Object[input.size()];
		for(Object element : input) {
			if(!(element instanceof TypedSelectable)) {
				throw Exceptions.runtime(EntryListUserType.INVALID_INPUT, value);
			}
			TypedSelectable slctbl = (TypedSelectable) element;
			codes[input.indexOf(element)] = slctbl.getCode();
		}
		return StringUtils.array2String(codes, StringConstants.COMMA);
	}
	
	/**
	 * @return Returns the named cache
	 */
	@SuppressWarnings("unchecked")
	protected Cache<C> cache() {
		Cache<C> cache = (Cache<C>) CacheRegistry.getRegisteredCache(cacheName);
		if(cache == null) {
			throw Exceptions.runtime(CACHE_NOT_INITIALIZED, cacheName);
		}
		return cache;
	}
	
}
