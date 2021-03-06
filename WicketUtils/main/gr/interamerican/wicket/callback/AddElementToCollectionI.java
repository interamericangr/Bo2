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

import gr.interamerican.bo2.utils.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;

/**
 * {@link CallbackAction} that adds an element to an indexed collection.
 * 
 * @param <B>
 *            Type of elements in the collection.
 * @deprecated Bad Utility
 */
@Deprecated
public class AddElementToCollectionI<B extends Serializable>
extends AddElementToCollection<B> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new AddElementToCollectionAction object.
	 *
	 * @param collection
	 *            the collection
	 * @param element
	 *            the element
	 * @param indexPropertyName
	 *            the index property name
	 */
	public AddElementToCollectionI(Collection<B> collection, B element, String indexPropertyName) {
		super(collection, element, indexPropertyName);
	}

	@Override
	protected void work() {
		CollectionUtils.addNextI(collection, element, indexPropertyName);
	}
}