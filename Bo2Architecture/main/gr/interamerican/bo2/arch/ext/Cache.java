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
package gr.interamerican.bo2.arch.ext;



import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Cache caches the values of objects used often and changed rare.
 * 
 * List elements are the most frequent objects handled by a cache.
 * 
 * Cache Objects are uniquely identified by the pair (typeId, code).
 * 
 * @param <C> Type of entries code.
 */
public interface Cache<C extends Comparable<? super C>> {
	
	/**
	 * Gets an object from the cache.
	 * 
	 * @param typeId Id of the selected type.
	 * @param code Code of the required value.
	 * 
	 * @return Returns the object from the cache.
	 */
	public TypedSelectable<C> get(Long typeId, C code);
	
	/**
	 * Gets an object from the cache.
	 * 
	 * @param typeId Id of the selected type.
	 * @param subTypeId Id of the selected sub type.
	 * @param code Code of the required value.
	 * 
	 * @return Returns the object from the cache.
	 * 
	 * @deprecated Use get(Long typeId, C code) instead.
	 */
	public TypedSelectable<C> get(Long typeId, Long subTypeId, C code);

	/**
	 * Gets the set of entries that holds objects categorized
	 * to a type and sub-type.
	 * 
	 * @param typeId type Id.
	 * @param subTypeId sub-type Id.
	 * @param <T> Type of elements in the set.
	 * 
	 * @return Returns the set that holds object of the specified type
	 *         and sub-type.
	 */
	public <T extends TypedSelectable<C>> Set<T> getSubCache(Long typeId, Long subTypeId);
	
	/**
	 * Gets the contents of a sub-cache in a list.
	 *
	 * @param typeId
	 * @param subTypeId
	 * @param <T> Type of elements in the list.
	 * 
	 * @return Returns a list with the contents of the sub-cache.
	 */
	public <T extends TypedSelectable<C>> List<T> getSubCacheAsList(Long typeId, Long subTypeId);		

	/**
	 * Puts an object in the cache. Existing entries will be
	 * replaced.
	 * 
	 * @param value
	 */
	public void put(TypedSelectable<C> value);
	
	/**
	 * Removes an object from the cache.
	 * 
	 * @param value
	 */
	public void remove(TypedSelectable<C> value); 

	/**
	 * Clears the cached objects of all types.
	 */
	public void clear();

	/**
	 * Refreshes the cached set of {@link TypedSelectable} objects
	 * that is stored in this cache and is identified with a typeId.
	 *
	 * If the values collection is empty, then this method will clear
	 * the cached objects.
	 * 
	 * @param typeId Id of the selected type.	  
	 * @param values New set of values.
	 */
	public void refill(Long typeId, Collection<? extends TypedSelectable<C>> values);
	
	
		
}
