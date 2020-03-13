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

import java.util.Iterator;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.conditions.EqualityCondition;

/**
 * A {@link NestedTree} of a {@link TreeElement} and it's sub-elements.<br>
 * This uses the {@link ClickableNestedTreeFolder}, which also provides click
 * capability to it.<br>
 * The action executed onclick is the one defined on
 * {@link TreeElement#invoke(AjaxRequestTarget)}.<br>
 * Also this keeps a reference of the currently selected object (by default the
 * first root element).
 * 
 * @see TreeProvider
 */
public class ClickableNestedTree extends NestedTree<TreeElement<?>> {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Currently Selected {@link TreeElement} on this (root by default)
	 */
	final IModel<TreeElement<?>> selected;

	/**
	 * Behaviors enforced on the {@link ClickableNestedTreeFolder}.
	 */
	final Behavior[] behaviors;

	/**
	 * Public Constructor.
	 * 
	 * @param id
	 *            Wicket id.
	 * @param model
	 *            The Provider in use
	 * @param behaviors
	 *            Behaviors enforced on the {@link ClickableNestedTreeFolder}.
	 */
	public ClickableNestedTree(String id, ITreeProvider<TreeElement<?>> model, Behavior... behaviors) {
		super(id, model);
		setOutputMarkupId(true);
		this.selected = new Model<>(model.getRoots().next());
		this.behaviors = behaviors;
	}

	@Override
	protected Component newContentComponent(String id, IModel<TreeElement<?>> node) {
		return new ClickableNestedTreeFolder(id, this, node, selected, behaviors);
	}

	/**
	 * Sets the currently Selected {@link TreeElement}.
	 *
	 * @param selected
	 *            The currently Selected {@link TreeElement}.
	 */
	public void setSelected(TreeElement<?> selected) {
		this.selected.setObject(selected);
	}

	/**
	 * Returns the currently Selected {@link TreeElement}.
	 *
	 * @return The Currently Selected {@link TreeElement}.
	 */
	public TreeElement<?> getSelected() {
		return selected.getObject();
	}

	/**
	 * Picks or Expands the elements on this based on other elements.<br>
	 * The purpose of this function is to maintain the state of this
	 * {@link ClickableNestedTree} if for some reason it was re-created.
	 * 
	 * @param oldSelected
	 *            The Selected Element of the Old Tree
	 * @param oldExpanded
	 *            The Expanded Elements of the Old Tree
	 */
	public void pickAndExpand(TreeElement<?> oldSelected, Set<TreeElement<?>> oldExpanded) {
		Iterator<? extends TreeElement<?>> iterator = getProvider().getRoots();
		while (iterator.hasNext()) {
			pickOrExpand(oldSelected, oldExpanded, iterator.next());
		}
	}

	/**
	 * Picks or expands a {@link TreeElement} and it's child elements.
	 * 
	 * @param oldSelected
	 *            The Selected Element of the Old Tree
	 * @param oldExpanded
	 *            The Expanded Elements of the Old Tree
	 * @param element
	 *            Target Element
	 */
	void pickOrExpand(TreeElement<?> oldSelected, Set<TreeElement<?>> oldExpanded, TreeElement<?> element) {
		if (Utils.equals(oldSelected, element)) {
			setSelected(element);
		}
		if (SelectionUtils.existsByCondition(new EqualityCondition<>(element), oldExpanded)) {
			expand(element);
		}
		Iterator<? extends TreeElement<?>> iterator = getProvider().getChildren(element);
		while (iterator.hasNext()) {
			pickOrExpand(oldSelected, oldExpanded, iterator.next());
		}
	}
}