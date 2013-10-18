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
import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.arch.utils.beans.CodifiedNamedImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 */
public class Utils {
	
	/**
	 * Creates a set of {@link Codified} objects with the elements of an array.
	 * 
	 * The index + 1 of each object in the array is turned to code and the result
	 * of its <code>toString()</code> method is used as name.
	 * 
	 * @param array 
	 * 
	 * @return Returns a set with {@link Codified} objects.
	 */
	public static Set<CodifiedNamedImpl<Integer>> arrayAsCodifiedSet(Object[] array) {
		
		Set<CodifiedNamedImpl<Integer>> set = new HashSet<CodifiedNamedImpl<Integer>>();
		for (int i = 0; i < array.length; i++) {
			CodifiedNamedImpl<Integer> entry = new CodifiedNamedImpl<Integer>(i+1, array[i].toString());
			set.add(entry);
		}
		return set;
	}
	
	/**
	 * ������ �� ������� �������� ���� collection me PersistentObjects.
	 * 
	 * � �������� ������� �� ���� �� ������.
	 * 
	 * @param <P> ����� {@link PersistentObject}.
	 * @param collection {@link Collection} �� persistent objects.
	 * 
	 * @return ���������� �� �������� ��� collection �� �� ���� ����� ������.
	 */	
	public static <P extends PersistentObject<?>> 
	P getMax(Collection<P> collection) {
		
		if (collection==null) {
			return null;
		}
		if (collection.isEmpty()) {
			return null;
		}
		Iterator<P> iter = collection.iterator();
		P candidate = iter.next();
	    while(iter.hasNext()) {
		    P nextItem = iter.next();
		    candidate = maxByKey(candidate, nextItem);
		}
		return candidate;
	}
	
	/**
	 * ������ �� �������� �������� ���� collection me PersistentObjects.
	 * 
	 * � �������� ������� �� ���� �� ������.
	 * 
	 * @param <P> ����� {@link PersistentObject}.
	 * @param collection {@link Collection} �� persistent objects.
	 * 
	 * @return ���������� �� �������� ��� collection �� �� ���� ����� ������.
	 */	
	public static <P extends PersistentObject<?>> 
	P getMin(Collection<P> collection) {
		
		if (collection==null) {
			return null;
		}
		if (collection.isEmpty()) {
			return null;
		}
		Iterator<P> iter = collection.iterator();
		P candidate = iter.next();
	    while(iter.hasNext()) {
		    P nextItem = iter.next();
		    candidate = minByKey(candidate, nextItem);
		}
		return candidate;
	}
	
	/**
	 * ��� ��� persistent objects ������ ���� �� �� ���������� ������.
	 * 
	 * �� ��� �� ��� ����������� ����� ��� �������, ���� � ������� ��
	 * ���������� �� ����� ��� �� ��� �����������.
	 * 
	 * @param <P> ����� Persistent object.
	 * @param po1 ����� �����������.
	 * @param po2 ������� �����������.
	 * 
	 * @return ���������� �� ����������� ��� �� ��� �� �� ���������� ������.
	 */
	@SuppressWarnings("unchecked")
	public static <P extends PersistentObject<?>> 
	P maxByKey(P po1, P po2) {		
		Comparable<Object> key1 = 
	    	(Comparable<Object>) po1.getKey();
	    Comparable<Object> key2 = 
	    	(Comparable<Object>) po2.getKey();
	    if (key1.compareTo(key2)>=0) {
	    	return po1;
	    } else {
	    	return po2;
	    }
	}
	
	/**
	 * ��� ��� persistent objects ������ ���� �� �� ���������� ������.
	 * 
	 * �� ��� �� ��� ����������� ����� ��� �������, ���� � ������� ��
	 * ���������� �� ����� ��� �� ��� �����������.
	 * 
	 * @param <P> ����� Persistent object.
	 * @param po1 ����� �����������.
	 * @param po2 ������� �����������.
	 * 
	 * @return ���������� �� ����������� ��� �� ��� �� �� ���������� ������.
	 */
	@SuppressWarnings("unchecked")
	public static <P extends PersistentObject<?>> 
	P minByKey(P po1, P po2) {		
		Comparable<Object> key1 = 
	    	(Comparable<Object>) po1.getKey();
	    Comparable<Object> key2 = 
	    	(Comparable<Object>) po2.getKey();
	    if (key1.compareTo(key2)<=0) {
	    	return po1;
	    } else {
	    	return po2;
	    }
	}

}
