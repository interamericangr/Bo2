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
 * {@link CallbackAction} that removes an element from a collection.
 * 
 * @param <B> 
 *        Type of elements in the collection.
 */
public class RemoveElementFromCollection<B extends Serializable> 
extends SimpleCallbackAction {
	
	/**
	 * 
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
	 * Creates a new RemoveElementFromCollection object. 
	 *
	 * @param collection
	 * @param element	   
	 */
	public RemoveElementFromCollection (Collection<B> collection, B element) {
		super();
		this.collection = collection;
		this.element = element;
	}
	
	
	@Override
	protected void work() {
		collection.remove(element);
	}
	
	

}
