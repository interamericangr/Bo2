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
package gr.interamerican.wicket.links;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;

import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * An {@link AjaxLink} that executes a {@link PickAction} on it's model object.
 * 
 * @param <T>
 *            type of model object
 */
public class PickActionAjaxLink<T> extends AjaxLink<T> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Action to execute when pressed.
	 */
	final PickAction<T> action;

	/**
	 * Text to be displayed on the confirmation dialog (optional)
	 */
	final String confirmationMessage;

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket id.
	 * @param model
	 *            Model Object
	 * @param action
	 *            PickAction executed on click.
	 */
	public PickActionAjaxLink(String id, IModel<T> model, PickAction<T> action) {
		this(id, model, action, null);
	}

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket id.
	 * @param model
	 *            Model Object
	 * @param action
	 *            PickAction executed on click.
	 * @param confirmationMessage
	 *            Text to be displayed on the confirmation dialog (optional)
	 */
	public PickActionAjaxLink(String id, IModel<T> model, PickAction<T> action, String confirmationMessage) {
		super(id, model);
		this.action = action;
		this.confirmationMessage = confirmationMessage;
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
		super.updateAjaxAttributes(attributes);
		if (confirmationMessage != null) {
			WicketUtils.addConfirmationDialog(confirmationMessage, attributes);
		}
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		action.doPick(target, getModelObject());
	}
}