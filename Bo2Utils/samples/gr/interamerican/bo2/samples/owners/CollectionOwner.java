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
package gr.interamerican.bo2.samples.owners;

import java.util.Collection;

/**
 * Owner of a collection.
 * 
 * @param <B> 
 *        Type of elements in the collection.
 */
public class CollectionOwner<B> {
	
	/**
	 * The collection.
	 */
	Collection<B> collection;

	/**
	 * Gets the collection.
	 *
	 * @return Returns the collection
	 */
	public Collection<B> getCollection() {
		return collection;
	}

	/**
	 * Assigns a new value to the collection.
	 *
	 * @param collection the collection to set
	 */
	public void setCollection(Collection<B> collection) {
		this.collection = collection;
	}

}
