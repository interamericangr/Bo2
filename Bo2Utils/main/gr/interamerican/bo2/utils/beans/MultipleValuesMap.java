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
package gr.interamerican.bo2.utils.beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 
 * @param <K> 
 * @param <V> 
 * 
 */
public class MultipleValuesMap<K,V> {
	
	/**
	 * Map to store the values.
	 */
	Map<K, Set<V>> map = new HashMap<K, Set<V>>();
	
	/**
	 * Puts the specified value to the map.
	 * 
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		Set<V> set = map.get(key);
		if (set==null) {
			set = new HashSet<V>();
			map.put(key, set);
		}
		set.add(value);
	}
	
	/**
	 * Gets a set with all values associated with
	 * the specified key.
	 * 
	 * @param key
	 * 
	 * @return Returns a set with the values associated with
	 *         the specified key.
	 */
	public Set<V> get(K key) {
		Set<V> set = map.get(key);
		if (set!=null) {
			return new HashSet<V>(set);
		} 
		return null;
	}
	
	/**
	 * Gets a set with the keys.
	 * 
	 * @return Returns a set with the keys.
	 */
	public Set<K> keySet() {
		return new HashSet<K>(map.keySet());
	}
	
	
	
	

}
