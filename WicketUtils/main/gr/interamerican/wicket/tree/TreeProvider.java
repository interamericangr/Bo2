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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * An {@link ITreeProvider} based on at least one {@link TreeElement}.<br>
 * Most Methods of this interface are delegated to {@link TreeElement}, because
 * each {@link TreeElement} has full knowledge of it's child elements.
 */
public class TreeProvider implements ITreeProvider<TreeElement<?>> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Root Elements
	 */
	private final List<TreeElement<?>> root;

	/**
	 * Public Constructor.
	 * 
	 * @param first
	 *            First Element of the Tree
	 * @param others
	 *            Other Elements
	 */
	@SafeVarargs
	public <T> TreeProvider(TreeElement<T> first, TreeElement<T>... others) {
		root = new ArrayList<>();
		root.add(first);
		root.addAll(Arrays.asList(others));
	}

	@Override
	public void detach() {
		// empty
	}

	@Override
	public Iterator<? extends TreeElement<?>> getRoots() {
		return root.iterator();
	}

	@Override
	public boolean hasChildren(TreeElement<?> node) {
		return node.hasChildren();
	}

	@Override
	public Iterator<? extends TreeElement<?>> getChildren(TreeElement<?> node) {
		return node.getChildren();
	}

	@Override
	public IModel<TreeElement<?>> model(TreeElement<?> object) {
		return Model.of(object);
	}
}