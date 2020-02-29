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
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.Utils;

/**
 * A Customized {@link Folder} contained in the {@link ClickableNestedTree}.
 */
public class ClickableNestedTreeFolder extends Folder<TreeElement<?>> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Currently Selected {@link TreeElement} of the Parent Tree.
	 */
	final IModel<TreeElement<?>> selected;

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket ID
	 * @param tree
	 *            Parent Tree
	 * @param model
	 *            {@link IModel} of this
	 * @param selected
	 *            The Currently Selected {@link TreeElement} of the Entire Tree
	 * @param behaviors
	 *            Behaviors enforced on this
	 */
	public ClickableNestedTreeFolder(String id, AbstractTree<TreeElement<?>> tree, IModel<TreeElement<?>> model,
			IModel<TreeElement<?>> selected, Behavior... behaviors) {
		super(id, tree, model);
		this.selected = selected;
		get("link").add(behaviors); //$NON-NLS-1$
	}

	@Override
	protected boolean isClickable() {
		return getModelObject().isClickable();
	}

	@Override
	protected boolean isSelected() {
		return Utils.equals(selected.getObject(), getModelObject());
	}

	@Override
	protected void onClick(AjaxRequestTarget target) {
		selected.setObject(getModelObject());
		getModelObject().invoke(target);
	}
}