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
package gr.interamerican.wicket.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.FormComponent;

import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.utils.MarkupConstants;

/**
 * An {@link AjaxFormComponentUpdatingBehavior} for change events on
 * {@link FormComponent}s with disabled the Lazy Loader Indicator, that will
 * invoke a {@link LegacyCallbackAction}.
 */
public class CallbackActionOnChangeBehaviorIndicatorAware
extends AjaxFormComponentUpdatingBehavior
implements IAjaxIndicatorAware {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The specific action.
	 */
	private LegacyCallbackAction callbackAction;

	/**
	 * Public Constructor.
	 *
	 * @param callbackAction
	 *            the callback action
	 */
	public CallbackActionOnChangeBehaviorIndicatorAware(LegacyCallbackAction callbackAction) {
		super(MarkupConstants.CHANGE);
		this.callbackAction = callbackAction;
	}

	@Override
	protected void onUpdate(AjaxRequestTarget target) {
		callbackAction.doInvoke(target);
	}

	@Override
	public String getAjaxIndicatorMarkupId() {
		return null;
	}
}