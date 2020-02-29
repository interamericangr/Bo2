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

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The Class Utils.
 */
public class Utils {

	/**
	 * Creates a set of {@link Codified} objects with the elements of an array.
	 * 
	 * The index + 1 of each object in the array is turned to code and the
	 * result of its <code>toString()</code> method is used as name.
	 *
	 * @param array
	 *            the array
	 * @return Returns a set with {@link Codified} objects.
	 */
	public static Set<CodifiedNamedImpl<Integer>> arrayAsCodifiedSet(Object[] array) {

		Set<CodifiedNamedImpl<Integer>> set = new HashSet<CodifiedNamedImpl<Integer>>();
		for (int i = 0; i < array.length; i++) {
			CodifiedNamedImpl<Integer> entry = new CodifiedNamedImpl<Integer>(i + 1, array[i].toString());
			set.add(entry);
		}
		return set;
	}

	/**
	 * Φέρνει το μέγιστο στοιχείο ενός collection me PersistentObjects.
	 * 
	 * Η σύγκριση γίνεται με βάση το κλειδί.
	 * 
	 * @param <P>
	 *            Τύπος {@link PersistentObject}.
	 * @param collection
	 *            {@link Collection} με persistent objects.
	 * 
	 * @return Επιστρέφει το στοιχείο του collection με το ποιο μικρό κλειδί.
	 */
	public static <K extends Serializable & Comparable<? super K> , P extends PersistentObject<K>> P getMax(Collection<P> collection) {

		if (collection == null) {
			return null;
		}
		if (collection.isEmpty()) {
			return null;
		}
		Iterator<P> iter = collection.iterator();
		P candidate = iter.next();
		while (iter.hasNext()) {
			P nextItem = iter.next();
			candidate = maxByKey(candidate, nextItem);
		}
		return candidate;
	}

	/**
	 * Φέρνει το ελάχιστο στοιχείο ενός collection me PersistentObjects.
	 * 
	 * Η σύγκριση γίνεται με βάση το κλειδί.
	 * 
	 * @param <P>
	 *            Τύπος {@link PersistentObject}.
	 * @param collection
	 *            {@link Collection} με persistent objects.
	 * 
	 * @return Επιστρέφει το στοιχείο του collection με το ποιο μικρό κλειδί.
	 */
	public static <K extends Serializable & Comparable<? super K> , P extends PersistentObject<K>> P getMin(Collection<P> collection) {

		if (collection == null) {
			return null;
		}
		if (collection.isEmpty()) {
			return null;
		}
		Iterator<P> iter = collection.iterator();
		P candidate = iter.next();
		while (iter.hasNext()) {
			P nextItem = iter.next();
			candidate = minByKey(candidate, nextItem);
		}
		return candidate;
	}

	/**
	 * Από δύο persistent objects φέρνει αυτό με το μεγαλύτερο κλειδί.
	 * 
	 * Αν και τα δύο αντικείμενα έχουν ίσα κλειδιά, τότε η μέθοδος θα επιστρέψει
	 * το πρώτο από τα δύο αντικείμενα.
	 * 
	 * @param <P>
	 *            Είδος Persistent object.
	 * @param po1
	 *            Πρώτο αντικείμενο.
	 * @param po2
	 *            Δεύτερο αντικείμενο.
	 * 
	 * @return Επιστρέφει το αντικείμενο από τα δύο με το μεγαλύτερο κλειδί.
	 */
	public static <K extends Serializable & Comparable<? super K> , P extends PersistentObject<K>> P maxByKey(P po1, P po2) {
		K key1 = po1.getKey();
		K key2 = po2.getKey();
		if (key1.compareTo(key2) >= 0) {
			return po1;
		}
		return po2;
	}

	/**
	 * Από δύο persistent objects φέρνει αυτό με το μικρότερο κλειδί.
	 * 
	 * Αν και τα δύο αντικείμενα έχουν ίσα κλειδιά, τότε η μέθοδος θα επιστρέψει
	 * το πρώτο από τα δύο αντικείμενα.
	 * 
	 * @param <P>
	 *            Είδος Persistent object.
	 * @param po1
	 *            Πρώτο αντικείμενο.
	 * @param po2
	 *            Δεύτερο αντικείμενο.
	 * 
	 * @return Επιστρέφει το αντικείμενο από τα δύο με το μεγαλύτερο κλειδί.
	 */
	public static <K extends Serializable & Comparable<? super K> , P extends PersistentObject<K>> P minByKey(P po1, P po2) {
		K key1 = po1.getKey();
		K key2 = po2.getKey();
		if (key1.compareTo(key2) <= 0) {
			return po1;
		}
		return po2;
	}
}