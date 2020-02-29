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
package gr.interamerican.wicket.bo2.callbacks;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;

import gr.interamerican.wicket.components.CallbackActionBehavior;
import gr.interamerican.wicket.components.OnChangeSelfUpdatingBehavior;

/**
 * Behavior that on an event,render the component that this handler is bound to.
 * 
 * @deprecated Either use {@link OnChangeSelfUpdatingBehavior} or
 *             {@link FormChoiceSelfUpdatingBehavior} or manually extend the
 *             {@link AjaxFormComponentUpdatingBehavior}
 */
@Deprecated
public class OnEventActionBehavior 
extends CallbackActionBehavior 
implements IAjaxIndicatorAware{

	/**
	 * serial.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new OnChangeValueActionBehavior object. 
	 *
	 * @param event the event
	 */
	public OnEventActionBehavior(String event) {
		super(event);
		callbackAction = new MethodBasedBo2WicketBlock("onChangeValue", this); //$NON-NLS-1$
	}
	
	/**
	 * On change value.
	 *
	 * @param target the target
	 */
	@SuppressWarnings("unused")
	private void onChangeValue(AjaxRequestTarget target){
		target.add(this.getComponent());
	}
	
	@Override
	public String getAjaxIndicatorMarkupId() {
		return null;
	}
}