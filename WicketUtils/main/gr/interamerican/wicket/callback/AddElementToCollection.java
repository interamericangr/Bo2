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
package gr.interamerican.wicket.callback;


import java.io.Serializable;
import java.util.Collection;

/**
 * {@link CallbackAction} that adds an element to an indexed collection.
 * 
 * @param <B> 
 *        Type of elements in the collection.
 */
public abstract class AddElementToCollection<B extends Serializable> 
extends SimpleCallbackAction {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Collection.
	 */
	Collection<B> collection;

	/**
	 * element.
	 */
	B element;
	
	/**
	 * Name of index property.
	 */
	String indexPropertyName;
	
	/**
	 * Creates a new AddElementToCollectionAction object. 
	 *
	 * @param collection
	 * @param element
	 * @param indexPropertyName 
	 */
	public AddElementToCollection
	(Collection<B> collection, B element, String indexPropertyName) {
		super();
		this.collection = collection;
		this.element = element;
		this.indexPropertyName = indexPropertyName;
	}
	
	

}
