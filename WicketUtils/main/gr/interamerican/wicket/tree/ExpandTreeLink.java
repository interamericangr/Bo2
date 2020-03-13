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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;

/**
 * An {@link AjaxLink} that will expand all the elements in an
 * {@link AbstractTree}.
 * 
 * @param <T>
 *            the node type
 */
public class ExpandTreeLink<T> extends AjaxLink<Void> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tree to Expand
	 */
	final AbstractTree<T> tree;

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id
	 * @param tree
	 *            Tree to Expand
	 */
	public ExpandTreeLink(String id, AbstractTree<T> tree) {
		super(id);
		this.tree = tree;
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		tree.getProvider().getRoots().forEachRemaining(this::expandIt);
		target.add(tree);
	}

	/**
	 * Expands a specific Node of the tree and all of it's Children.
	 * 
	 * @param node
	 *            {@link AbstractTree} to expand
	 */
	private void expandIt(T node) {
		tree.expand(node);
		tree.getProvider().getChildren(node).forEachRemaining(this::expandIt);
	}
}