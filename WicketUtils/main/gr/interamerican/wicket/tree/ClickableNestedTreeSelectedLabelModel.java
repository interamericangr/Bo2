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

import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * A Label that is dynamically filled with the path of the current selected node
 * of the tree.
 */
public class ClickableNestedTreeSelectedLabelModel extends AbstractReadOnlyModel<String> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 46987L;

	/**
	 * Tree from which the node we want to display
	 */
	final ClickableNestedTree tree;

	/**
	 * Public Constructor
	 * 
	 * @param tree
	 *            Tree from which the node we want to display
	 */
	public ClickableNestedTreeSelectedLabelModel(ClickableNestedTree tree) {
		this.tree = tree;
	}

	@Override
	public String getObject() {
		return tree.getSelected().getLabel();
	}
}