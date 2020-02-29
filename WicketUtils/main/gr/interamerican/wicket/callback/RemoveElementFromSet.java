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
import java.util.Set;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.wicket.markup.html.panel.service.BeanPanelDef;

/**
 * {@link Consume} that removes the edit element from an input set.
 * 
 * @param <B>
 *            Type of elements in the collection.
 */
public class RemoveElementFromSet<B extends Serializable>
implements Consume<B> {

	/** Version UID. */
	private static final long serialVersionUID = 1L;

	/** Set To Remove the Element From. */
	Set<B> set;

	/**
	 * Creates a new RemoveElementFromSet object.
	 *
	 * @param set
	 *            Set To Remove the Element From
	 */
	public RemoveElementFromSet(Set<B> set) {
		this.set = set;
	}

	/**
	 * Creates a new RemoveElementFromSet object.
	 *
	 * @param def
	 *            Definition to extract the item to be remove
	 * @param set
	 *            Set To Remove the Element From
	 * @deprecated Use the other Constructor.
	 */
	@Deprecated
	public RemoveElementFromSet(@SuppressWarnings("unused") BeanPanelDef<B> def, Set<B> set) {
		this.set = set;
	}

	@Override
	public void consume(B bean) {
		CollectionUtils.reArrange(set);
		set.remove(bean);
	}
}