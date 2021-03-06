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
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;

import gr.interamerican.wicket.callback.CallbackAction;

/**
 * Behavior,that on Component's update action, performs a specified
 * {@link CallbackAction}.
 * 
 * @deprecated Either use {@link CallbackActionOnChangeBehavior} or
 *             {@link CallbackChoiceUpdatingBehavior} or manually extend the
 *             {@link AjaxFormComponentUpdatingBehavior}
 */
@Deprecated
public class CallbackActionBehavior 
extends AjaxFormComponentUpdatingBehavior {

	/**
	 * serialize uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The specific action.
	 */
	protected CallbackAction callbackAction;
	
	/**
	 * Creates a new CallbackActionBehavior object. 
	 *
	 * @param event the event
	 * @param callbackAction the callback action
	 */
	public CallbackActionBehavior(String event,CallbackAction callbackAction) {
		super(event);
		this.callbackAction = callbackAction;
	}
	
	/**
	 * Creates a new CallbackActionBehavior object. 
	 *
	 * @param event the event
	 */
	protected CallbackActionBehavior(String event) {
		super(event);
	}

	@Override
	protected void onUpdate(AjaxRequestTarget target) {
		callbackAction.callBack(target);
	}
}