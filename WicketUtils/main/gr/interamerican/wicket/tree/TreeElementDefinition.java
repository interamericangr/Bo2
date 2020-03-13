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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import gr.interamerican.bo2.utils.functions.SerializableFunction;
import gr.interamerican.wicket.callback.PickAction;

/**
 * A Definition of a {@link TreeElement}.<br>
 * This definition can optionally define a {@link PickAction}, so the click
 * option will be enabled.<br>
 * This can contain various sub-definitions, rendered in the same order as
 * invoked on
 * {@link #addSubdefinition(SerializableFunction, TreeElementDefinition)}.
 * 
 * @param <T>
 *            Type of Element
 */
public class TreeElementDefinition<T> implements Serializable {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Action Executed when this tree element is clicked
	 */
	final PickAction<T> action;

	/**
	 * Function to resolve the label for the Tree Element
	 */
	final SerializableFunction<T, String> labelFunction;

	/**
	 * Sub Tree Definitions of this
	 */
	final List<TreeElementSubDefinition<T, ?>> subDefinitions = new ArrayList<>();

	/**
	 * Public Constructor.
	 * 
	 * @param action
	 *            Action Executed when this tree element is clicked
	 * @param labelFunction
	 *            Function to resolve the label for the Tree Element Function to
	 *            resolve the label for the Tree
	 */
	public TreeElementDefinition(PickAction<T> action, SerializableFunction<T, String> labelFunction) {
		this.action = action;
		this.labelFunction = labelFunction;
	}

	/**
	 * Public Constructor.
	 * 
	 * @param action
	 *            Action Executed when this tree element is clicked
	 */
	public TreeElementDefinition(PickAction<T> action) {
		this(action, null);
	}

	/**
	 * Public Constructor.
	 * 
	 * @param labelFunction
	 *            Function to resolve the label for the Tree Element Function to
	 *            resolve the label for the Tree
	 */
	public TreeElementDefinition(SerializableFunction<T, String> labelFunction) {
		this(null, labelFunction);
	}

	/**
	 * Public Constructor.
	 */
	public TreeElementDefinition() {
		this(null, null);
	}

	/**
	 * Adds a Sub definition on this.
	 * 
	 * @param getter
	 *            Getter of the child elements
	 * @param childDefinition
	 *            Definition for the Child Element
	 */
	public <C> void addSubdefinition(final SerializableFunction<T, Collection<C>> getter,
			final TreeElementDefinition<C> childDefinition) {
		subDefinitions.add(new TreeElementSubDefinition<>(getter, childDefinition));
	}

	/**
	 * Returns the Sub Tree Definitions of this.
	 *
	 * @return Sub Tree Definitions of this
	 */
	public List<TreeElementSubDefinition<T, ?>> getSubDefinitions() {
		return subDefinitions;
	}

	/**
	 * Returns the Function to resolve the label for the Tree Element.
	 *
	 * @return Function to resolve the label for the Tree Element
	 */
	public SerializableFunction<T, String> getLabelFunction() {
		return labelFunction;
	}

	/**
	 * Returns the Action Executed when this tree element is clicked
	 *
	 * @return Action Executed when this tree element is clicked
	 */
	public PickAction<T> getAction() {
		return action;
	}
}