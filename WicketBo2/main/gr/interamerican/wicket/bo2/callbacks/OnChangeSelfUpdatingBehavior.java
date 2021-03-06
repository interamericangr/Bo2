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
import org.apache.wicket.markup.html.form.FormComponent;

import gr.interamerican.wicket.utils.MarkupConstants;

/**
 * An {@link AjaxFormComponentUpdatingBehavior} for change events on
 * {@link FormComponent}s , that will render the component that this handler is
 * bound to.
 * 
 * @deprecated Moved to WicketUtils with different package name
 */
@Deprecated
public class OnChangeSelfUpdatingBehavior
extends AjaxFormComponentUpdatingBehavior
implements IAjaxIndicatorAware {

	/**
	 * serial.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Public Constructor.
	 */
	public OnChangeSelfUpdatingBehavior() {
		super(MarkupConstants.CHANGE);
	}

	@Override
	protected void onUpdate(AjaxRequestTarget target) {
		target.add(this.getComponent());
	}

	@Override
	public String getAjaxIndicatorMarkupId() {
		return null;
	}
}