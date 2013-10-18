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

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.arch.ext.TypedSelectable;

import java.util.Collection;

/**
 * Utilities relevant with {@link Cache} objects.
 */
public class CacheUtils {
	
	
	/**
	 * Creates a new CacheUtils object.
	 */
	private CacheUtils() {/* empty hidden constructor */}

	/**
	 * Adds a collection of {@link TypedSelectable} elements in a cache.
	 * 
	 * @param cache
	 * @param collection
	 * @param <C> Type of code of the elements in the cache.
	 */
	public static <C extends Comparable<? super C>> void loadEntries
	(Cache<C> cache, Collection<? extends TypedSelectable<C>> collection) {
		for (TypedSelectable<C> typedSelectable : collection) {
			cache.put(typedSelectable);
		}
	}
	
	/**
	 * Adds a collection of {@link TypedSelectable} elements in a cache.
	 * 
	 * @param cache
	 * @param collection
	 * @param <C> Type of code of the elements in the cache.
	 */
	public static <C extends Comparable<? super C>> void loadEntryOwners
	(Cache<C> cache, Collection<? extends TranslatableEntryOwner<C, ?, ?>> collection) {
		for (TranslatableEntryOwner<C, ?, ?> owner : collection) {
			cache.put(owner.getEntry());
		}
	}

}
