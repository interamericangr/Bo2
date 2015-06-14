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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Associates a key with multiple values.
 * 
 * @param <K> 
 *        Type of key.
 * @param <V> 
 *        Type of value.
 * 
 */
public class MultipleValuesMap<K,V> 
implements Iterable<Pair<K, Set<V>>> {
	
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
	
	/**
	 * Gets the count of values associated with the specified key.
	 * 
	 * @param key 
	 * 
	 * @return Returns the size of the set that contains the values
	 *         associated with the specified key.
	 */
	public int size(K key) {
		Set<V> set = map.get(key);
		if (set==null) {
			return 0;
		}
		return set.size();
	}
	
	/**
	 * Gets the count of keys.
	 * 
	 * @param key 
	 * 
	 * @return Returns the size of the set that contains the values
	 *         associated with the specified key.
	 */
	public int size() {
		return map.size();
	}
	
	/**
	 * Removes the specified value from the set of values associated with 
	 * the specified key.
	 * 
	 * @param key
	 * @param value
	 * @return Returns true if the specified value was associated with
	 *         the specified key. 
	 */
	public boolean remove(K key, V value) {
		Set<V> set = map.get(key);
		if (set!=null) {			
			return set.remove(value);
		} else {
			return false;
		}
	}
	
	/**
	 * Removes all values associated with the specified key.
	 * 
	 * @param key
	 * @param value
	 * @return Returns true if the specified key existed in the map. 
	 */
	public boolean remove(K key) {
		boolean b = map.containsKey(key);		
		map.remove(key);
		return b;
	}


	@Override
	public Iterator<Pair<K, Set<V>>> iterator() {
		return new SimpleMapIterator<K, Set<V>>(map);
	}
	
	/**
	 * Checks if this map contains the specified key.
	 * 
	 * @param key
	 * 
	 * @return Returns true if the specified key exists in the map. 
	 */
	public boolean containsKey(K key) {				
		return map.containsKey(key);
	}
	
	/**
	 * Checks if this map contains the specified association
	 * between key and value.
	 * 
	 * @param key
	 * @param value 
	 * 
	 * @return Returns true if the specified key exists in the map. 
	 */
	public boolean containsValue(K key, V value) {	
		Set<V> set = map.get(key);
		if (set==null) {
			return false;
		}
		return set.contains(value);
	}


	
	
	
	
	

}
