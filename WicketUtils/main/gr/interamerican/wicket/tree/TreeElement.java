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
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;

import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.functions.SerializableFunction;

/**
 * An element of a {@link ClickableNestedTree}.
 * 
 * @param <T>
 *            Type of Element
 */
public class TreeElement<T> implements Serializable {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Child Elements
	 */
	private final List<TreeElement<?>> children;

	/**
	 * Full Tree Path to this Element
	 */
	final String label;

	/**
	 * Element Definition
	 */
	final TreeElementDefinition<T> definition;

	/**
	 * Actual Backing Item
	 */
	final T item;

	/**
	 * Public Constructor.
	 *
	 * @param item
	 *            Actual Backing Item
	 * @param definition
	 *            Element Definition
	 * @param initialLabel
	 *            Label before this Element
	 * @param separator
	 *            Separator for each label path
	 */
	public TreeElement(T item, TreeElementDefinition<T> definition, String initialLabel, String separator) {
		this.item = item;
		this.definition = definition;
		this.label = StringUtils.concatSeparated(separator, initialLabel, toString());
		this.children = new ArrayList<>();
		for (TreeElementSubDefinition<T, ?> sub : definition.getSubDefinitions()) {
			addChildren(sub, separator);
		}
	}

	/**
	 * Adds a sub definition for each children element of the specific
	 * {@link TreeElementSubDefinition}.
	 * 
	 * @param subDefinition
	 *            {@link TreeElementSubDefinition}
	 * @param separator
	 *            Separator for each label path
	 */
	<K> void addChildren(TreeElementSubDefinition<T, K> subDefinition, String separator) {
		for (K child : subDefinition.getGetter().apply(item)) {
			children.add(new TreeElement<K>(child, subDefinition.getChildDefinition(), label, separator));
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TreeElement)) {
			return false;
		}
		return Utils.equals(item, ((TreeElement<?>) obj).item);
	}

	@Override
	public String toString() {
		SerializableFunction<T, String> function = definition.getLabelFunction();
		if (function != null) {
			return function.apply(item);
		}
		return StringUtils.toString(item);
	}

	@Override
	public int hashCode() {
		return item.hashCode();
	}

	/**
	 * Returns the Full Tree Path to this Element.
	 * 
	 * @return Full Tree Path to this Element
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Returns the {@link TreeElement}s of this.
	 * 
	 * @return {@link TreeElement}s
	 */
	Iterator<? extends TreeElement<?>> getChildren() {
		return children.iterator();
	}

	/**
	 * Returns if this {@link TreeElement} has children's
	 * 
	 * @return true/false
	 */
	boolean hasChildren() {
		return !children.isEmpty();
	}

	/**
	 * Action invoked onclick.<br>
	 * This delegates to {@link TreeElementDefinition#getAction()}.
	 * 
	 * @param target
	 */
	public void invoke(AjaxRequestTarget target) {
		definition.getAction().doPick(target, item);
	}

	/**
	 * If this element has an action to execute.
	 * 
	 * @return true/false
	 */
	public boolean isClickable() {
		return definition.getAction() != null;
	}
}