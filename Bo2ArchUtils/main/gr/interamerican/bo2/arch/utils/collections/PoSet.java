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

import gr.interamerican.bo2.arch.PersistentObject;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This is a Set of {@link PersistentObject}.
 * 
 * Objects of this set can be searched by their key.
 * 
 * @param <P> Type of persistent object contained in the set. 
 */
public class PoSet<P extends PersistentObject<?>> 
implements Set<P>, Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Map containing the persistent objects.
	 * 
	 * 
	 * <T extends Object & Comparable<? super T>>
	 */
	private Map<Object, P> map;
	
	/**
	 * Creates a new PoSet object. 
	 *
	 */
	public PoSet() {
		super();	
		map= new HashMap<Object, P>();
	}
	
	/**
	 * Creates a new PoSet object. 
	 * @param set 
	 *
	 */
	public PoSet(Set<P> set) {
		this();
		for (P p : set) {
			map.put(p.getKey(), p);
		}		
	}
	
	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean contains(Object o) {
		return map.containsValue(o);
	}

	public Iterator<P> iterator() {
		return map.values().iterator();
	}

	public Object[] toArray() {
		return map.values().toArray();
	}

	public <T> T[] toArray(T[] a) {
		return map.values().toArray(a);
	}

	public boolean add(P e) {
		if(map.containsKey(e.getKey()))
			return false;
		else {
			map.put(e.getKey(), e);
			return true;
		}
	}

	public boolean remove(Object o) {
		try {
			@SuppressWarnings("unchecked")
			P element = (P) o;
			P removed = map.remove(element.getKey()); 
			return removed!=null;
		} catch (ClassCastException cce) {
			return false;
		}
	}

	public boolean containsAll(Collection<?> c) {
		return map.values().containsAll(c);
	}

	public boolean addAll(Collection<? extends P> c) {
		boolean ret = false;
		for (P element : c) {
			P previous = map.put(element.getKey(), element);
			if (previous==null) {
				ret = true;
			}
		}
		return ret;
	}

	public boolean retainAll(Collection<?> c) {
		boolean ret = false;
		Set<P> toDelete = new HashSet<P>();
		for (P element : map.values()) {
			if (!c.contains(element)) {
				toDelete.add(element);
				ret = true;
			}
		}
		for(P element : toDelete)
			map.remove(element.getKey());
		return ret;
	}

	public boolean removeAll(Collection<?> c) {
		boolean ret = false;
		for (Object o : c) {
			if (this.remove(o)) {
				ret = true;
			}
		}
		return ret;
	}

	public void clear() {
		map.clear();
	}
	
	/**
	 * Gets the PersistentObject of this set that has the specified key.
	 * 
	 * @param key Key of object.
	 * 
	 * @return Returns the PersistentObject of this set that has the key
	 *         specified by the parameter <code>key</code>. If the set
	 *         does not contain any element with this key, then returns
	 *         null.
	 */
	public P getByKey(Object key) {
		return map.get(key);
	}
	
	/**
	 * Gets the keys of the PersistentObject in this set.
	 * 
	 * The elements in this set are keys of {@link PersistentObject}
	 * so they are {@link Comparable} with other keys and {@link Serializable}.
	 * 
	 * @return Returns a set containing the keys of the persistent objects
	 *         in this set.
	 */
	public Set<Object> getKeys() {
		return map.keySet();
	}

	@Override	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		try {
			@SuppressWarnings("unchecked")
			PoSet<P> that = (PoSet<P>) o;
			return (this.containsAll(that) && that.containsAll(this));
		} catch (ClassCastException ccex) {
			return false;
		}		
	}
	
}
