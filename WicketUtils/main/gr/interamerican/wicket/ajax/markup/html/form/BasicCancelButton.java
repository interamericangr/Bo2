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
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * An {@link AjaxButton} that can be used as a cancel button.<br>
 * This will {@link #setDefaultFormProcessing(boolean)} as false, and on submit
 * it will mark it's parent as not visible and add on the
 * {@link AjaxRequestTarget} the parent of it's parent in order to apply the
 * hiding of the parent.<br>
 * In order for this to work - the <b>parent</b> must set
 * {@link #setOutputMarkupId(boolean)} to true.
 */
public class BasicCancelButton extends AjaxButton {

	/** Version UID. */
	private static final long serialVersionUID = 6245L;

	/**
	 * Creates a new CancelButton object.
	 * 
	 * @param id
	 *            Wicket Id
	 * @param model
	 *            model used to set <code>value</code> markup attribute
	 */
	public BasicCancelButton(String id, IModel<String> model) {
		super(id, model);
		setDefaultFormProcessing(false);
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		getParent().setVisible(false);
		target.add(getParent().getParent());
	}
}