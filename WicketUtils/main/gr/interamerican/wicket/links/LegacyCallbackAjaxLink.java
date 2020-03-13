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
import org.apache.wicket.ajax.markup.html.AjaxLink;

import gr.interamerican.wicket.callback.LegacyCallbackAction;

/**
 * An {@link AjaxLink} that executes a {@link LegacyCallbackAction} on it's
 * model object.
 */
public class LegacyCallbackAjaxLink extends AjaxLink<Void> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Action to execute when pressed.
	 */
	LegacyCallbackAction action;

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket id.
	 * @param action
	 *            LegacyCallbackAction executed on click.
	 */
	public LegacyCallbackAjaxLink(String id, LegacyCallbackAction action) {
		super(id);
		this.action = action;
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		action.doInvoke(target);
	}
}