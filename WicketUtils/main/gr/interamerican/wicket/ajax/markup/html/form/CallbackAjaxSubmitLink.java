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
package gr.interamerican.wicket.ajax.markup.html.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;

import gr.interamerican.wicket.callback.LegacyCallbackAction;

/**
 * An {@link AjaxLink} that executes a {@link LegacyCallbackAction}.<br>
 */
public class CallbackAjaxSubmitLink extends AjaxSubmitLink {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Action to execute when pressed.
	 */
	final LegacyCallbackAction action;

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id
	 * @param action
	 *            Action To execute
	 */
	public CallbackAjaxSubmitLink(String id, LegacyCallbackAction action) {
		super(id);
		this.action = action;
	}

	/**
	 * Creates a new CallbackAjaxSubmitLink object.
	 *
	 * @param id
	 *            Wicket Id
	 * @param form
	 *            Owning Form
	 * @param action
	 *            Action To execute
	 */
	public CallbackAjaxSubmitLink(String id, Form<?> form, LegacyCallbackAction action) {
		super(id, form);
		this.action = action;
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		action.doInvoke(target);
	}
}