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

import gr.interamerican.bo2.arch.ext.Selectable;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Set of {@link Selectable} objects.
 * 
 * @param <C> Type of selectables code. 
 * 
 */
public class SelectablesSet<C extends Comparable<? super C>> 
implements Set<Selectable<C>>, Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Map containing the persistent objects.
	 */
	private Map<C , Selectable<C>> map = 
		new HashMap<C, Selectable<C>>();
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Set#size()
	 */
	public int size() {
		return map.size();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#isEmpty()
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return map.containsValue(o);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#iterator()
	 */
	public Iterator<Selectable<C>> iterator() {
		return map.values().iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#toArray()
	 */
	public Object[] toArray() {
		return map.values().toArray();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#toArray(T[])
	 */
	public <T> T[] toArray(T[] a) {
		return map.values().toArray(a);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#add(java.lang.Object)
	 */
	public boolean add(Selectable<C> e) {
		if(map.containsKey(e.getCode()))
			return false;
		else {
			map.put(e.getCode(), e);
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		try {			
			@SuppressWarnings("unchecked")
			Selectable<C> element = (Selectable<C>) o;
			Selectable<C> removed = map.remove(element.getCode()); 
			return removed!=null;
		} catch (ClassCastException cce) {
			return false;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Set#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		return map.values().containsAll(c);
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends Selectable<C>> c) {
		boolean ret = false;
		for (Selectable<C> element : c) {
			Selectable<C> previous = map.put(element.getCode(), element);
			if (previous==null) {
				ret = true;
			}
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		boolean ret = false;
		Set<Selectable<C>> toDelete = new HashSet<Selectable<C>>();
		for (Selectable<C> element : map.values()) {
			if (!c.contains(element)) {
				toDelete.add(element);
				ret = true;
			}
		}
		for(Selectable<C> element : toDelete)
			map.remove(element.getCode());
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		boolean ret = false;
		for (Object o : c) {
			if (this.remove(o)) {
				ret = true;
			}
		}
		return ret;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Set#clear()
	 */
	public void clear() {
		map.clear();
	}
	
	/**
	 * Gets the PersistentObject of this set that has the specified key.
	 * 
	 * @param code code of object.
	 * 
	 * @return Returns the Selectable of this set that has the code
	 *         specified by the parameter <code>code</code>. If the set
	 *         does not contain any element with this code, then returns
	 *         null.
	 */
	public Selectable<C> getByCode(C code) {
		return map.get(code);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
			SelectablesSet<C> that = (SelectablesSet<C>) o;
			return (this.containsAll(that) && that.containsAll(this));
		} catch (ClassCastException ccex) {
			return false;
		}
	}
}
