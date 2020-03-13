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
package gr.interamerican.bo2.arch.utils.collections;




import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Abstract class that can store that can store rows 
 * fetched by a {@link Query}.
 * 
 * One object is created for each row fetched by the query.
 * The <code>value(Q q)</code> method creates this object. 
 * The <code>key(Q q)</code> method creates the key that will 
 * be used to store the value object to the map. 
 * 
 * @param <K> Class of key objects.
 * @param <V> Class of value objects.
 * @param <Q> Query class of value objects.
 */
public abstract class QueryResults<K,V,Q extends Query> implements Map<K,V> {
	
	/**
	 * map that stores the query rows.
	 */
	private Map<K,V> results=new HashMap<K,V>();
	
	/**
	 * Key that is used for data access.
	 *
	 * @param q Query that gives values to the QueryResults
	 * @return the key that will be used for data access
	 * @throws DataAccessException the data access exception
	 */
	public abstract K key(Q q) throws DataAccessException;
	
	/**
	 * Object that holds all useful information of a query row.
	 *
	 * @param q Query that gives values to the QueryResults
	 * @return the object that contains one row's data.
	 *         This object will be stored in the map.
	 *         
	 * @throws DataAccessException the data access exception
	 * @throws ClassCastException the class cast exception
	 */
	public abstract V value(Q q) throws DataAccessException;
	

	
	/**
	 * reads data from a DataQuery.
	 *
	 * @param q the q
	 * @throws DataAccessException the data access exception
	 */
	public void read(Q q) throws DataAccessException {
		while (q.next()) {
			K _key=key(q);
			V _value=value(q);
			results.put(_key,_value);
		}
	}

	@Override
	public void clear() {
		results.clear();
	}

	@Override
	public boolean containsKey(Object arg0) {
		return results.containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) {
		return results.containsValue(arg0);
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return results.entrySet();
	}

	@Override
	public boolean isEmpty() {
		return results.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return results.keySet();
	}

	@Override
	public V get(Object key) {
		return results.get(key);
	}

	@Override
	public V put(K key, V value) {
		return results.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> t) {
		results.putAll(t);
	}

	@Override
	public V remove(Object key) {
		return results.remove(key);
	}

	@Override
	public int size() {
		return results.size();
	}

	@Override
	public Collection<V> values() {
		return results.values();
	}
}