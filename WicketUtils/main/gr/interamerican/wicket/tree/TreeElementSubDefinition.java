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
package gr.interamerican.wicket.tree;

import java.io.Serializable;
import java.util.Collection;

import gr.interamerican.bo2.utils.functions.SerializableFunction;

/**
 * A Sub Definition of a {@link TreeElementDefinition}, actually acting as
 * wrapper between two different {@link TreeElementDefinition}s.
 * 
 * @param <P>
 *            Type of Parent Element
 * @param <C>
 *            Type of Child Element
 */
public class TreeElementSubDefinition<P, C> implements Serializable {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Getter of the elements
	 */
	private final SerializableFunction<P, Collection<C>> getter;

	/**
	 * Definition of the Child elements
	 */
	private final TreeElementDefinition<C> childDefinition;

	/**
	 * Public Constructor.
	 * 
	 * @param getter
	 *            Getter of the elements
	 * @param childDefinition
	 *            Definition of the Child elements
	 */
	public TreeElementSubDefinition(final SerializableFunction<P, Collection<C>> getter,
			final TreeElementDefinition<C> childDefinition) {
		this.getter = getter;
		this.childDefinition = childDefinition;
	}

	/**
	 * Returns the Getter of the elements
	 *
	 * @return Getter of the elements
	 */
	public SerializableFunction<P, Collection<C>> getGetter() {
		return getter;
	}

	/**
	 * Returns the Definition of the Child elements
	 *
	 * @return Definition of the Child elements
	 */
	public TreeElementDefinition<C> getChildDefinition() {
		return childDefinition;
	}
}