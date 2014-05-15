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
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.utils.Exceptions;
import gr.interamerican.bo2.utils.NumberUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;

/**
 * Custom type used to persist cached codified entries.
 * 
 * Example declaration of type in a hibernate mapping file:
 * 
 * <typedef
 * 	class="gr.interamerican.bo2.impl.open.hibernate.types.EntryUserTypeForLong" name="DamageCause">
 *	<param name="entryClassName">gr.interamerican.bo.def.pc.common.setup.po.DamageCause</param>
 *	<param name="entryTypeId">100006</param>
 *	<param name="cacheName">OnE_cache</param>
 * </typedef>
 * 
 * @param <C>
 *        Type of code.
 */
public abstract class EntryUserType<C extends Comparable<? super C>> 
extends AbstractUserType {
	
	/**
	 * Error message for uninitialized cache
	 */
	private static final String CACHE_NOT_INITIALIZED = "EntryUserType.CACHE_NOT_INITIALIZED"; //$NON-NLS-1$
	
	/**
	 * Error message for uninitialized cache
	 */
	private static final String NOT_EXISTING_CACHED_OBJECT = "EntryUserType.NOT_EXISTING_CACHED_OBJECT"; //$NON-NLS-1$

	/**
	 * FQCN of the {@link TypedSelectable}
	 */
	private Class<? extends TypedSelectable<Long>> entryClass;

	/**
	 * type id
	 */
	protected Long typeId;

	/**
	 * Cache name.
	 */
	private String cacheName;
	
	public void setParameterValues(Properties parameters) {
		String entryClassName = parameters.getProperty("entryClassName"); //$NON-NLS-1$
		String strTypeId = parameters.getProperty("entryTypeId"); //$NON-NLS-1$
		this.typeId = NumberUtils.string2Long(strTypeId);

		this.cacheName = parameters.getProperty("cacheName"); //$NON-NLS-1$
		
		if (typeId == 0) {
			String msg = "Invalid typeId parameter for class" + entryClassName; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		if(entryClassName != null) {
			entryClass = (Class<? extends TypedSelectable<Long>>) Factory.getType(entryClassName);
		}
	}

	@SuppressWarnings({ "nls" })
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		C code = getCode(rs, names[0]);
		Selectable<C> result = this.cache().get(this.typeId, code);
		if(result==null) {
			String msg = "(" + typeId + "," + code + ")"; 
			Exceptions.runtime(NOT_EXISTING_CACHED_OBJECT, msg);
		}
		return result;
	}

	public Class<?> returnedClass() {
		return entryClass;
	}

	public int[] sqlTypes() {
		return new int[] { Types.BIGINT };
	}
	
	/**
	 * Gets the code value of the {@link TypedSelectable} object from the
	 * {@link ResultSet}.
	 * 
	 * @param rs
	 *            the resultset
	 * @param name
	 *            the name of the column
	 * @return the code value
	 * @throws SQLException
	 */
	protected abstract C getCode(ResultSet rs, String name) throws SQLException;
	
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
