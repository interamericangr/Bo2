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
package gr.interamerican.wicket.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.util.ListModel;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.callback.LegacyCallbackAction;

/**
 * Action when a new value is added. This should work on something that is
 * contained within a {@link AbstractChoicesPanel}.
 * 
 * @param <T>
 *            Type of Object to be added
 */
public class AddNewAction<T> implements LegacyCallbackAction {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * List Model we update
	 */
	private final ListModel<T> selectedValues;

	/**
	 * Component from where we retrieve the value
	 */
	private final FormComponent<T> cmp;

	/**
	 * Label to display potential error message
	 */
	private final Label msg;

	/**
	 * Public Constructor.
	 * 
	 * @param selectedValues
	 *            List Model we update
	 * @param cmp
	 *            Component from where we retrieve the value
	 * @param msg
	 *            Label to display potential error message
	 */
	public AddNewAction(ListModel<T> selectedValues, FormComponent<T> cmp, Label msg) {
		this.selectedValues = selectedValues;
		this.cmp = cmp;
		this.msg = msg;
	}

	@Override
	public void invoke(AjaxRequestTarget target) throws Exception {
		AbstractChoicesPanel<?> parent = cmp.findParent(AbstractChoicesPanel.class);
		if (parent == null) {
			throw new Exception("This action was not attached to something that is a child of AbstractChoicesPanel"); //$NON-NLS-1$
		}
		T cmpValue = cmp.getModelObject();
		if (cmpValue == null) {
			StringResourceModel resourceModel = new StringResourceModel("selectEntry", parent); //$NON-NLS-1$
			msg.setDefaultModelObject(resourceModel.getObject());
		} else {
			selectedValues.getObject().add(cmpValue);
			cmp.setModelObject(null);
			msg.setDefaultModelObject(StringConstants.EMPTY);
		}
		target.add(parent);
	}
}