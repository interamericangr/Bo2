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
package gr.interamerican.wicket.markup.html.panel.back;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelWithFeedback;
import gr.interamerican.wicket.util.resource.StringResourceUtils;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;

/**
 * {@link ServicePanel} with a back button.
 * 
 */
public class ServicePanelWithBack 
extends ServicePanelWithFeedback {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * wicket id of form containing the back button..
	 */
	public static final String BACK_FORM_ID = "backForm"; //$NON-NLS-1$

	/**
	 * wicket id of back button.
	 */
	public static final String BACK_BUTTON_ID = "backButton"; //$NON-NLS-1$

	/**
	 * Back {@link AjaxButton}.
	 */
	protected CallbackAjaxButton backButton;

	/**
	 * Creates a new ServicePanelWithBack object.
	 *
	 * @param definition
	 *            the definition
	 */
	public ServicePanelWithBack(ServicePanelWithBackDef definition) {
		super(definition);
	}

	@Override
	public ServicePanelWithBackDef getDefinition() {
		return (ServicePanelWithBackDef) definition;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void init() {
		super.init();
		IModel<String> backLabel = StringResourceUtils.getResourceModel(WellKnownResourceIds.SPWB_BACK_BTN_LABEL, this,
				getDefinition().getBackLabelModel(), "Back"); //$NON-NLS-1$

		LegacyCallbackAction backAction = getDefinition().getBackAction();
		backButton = new CallbackAjaxButton(BACK_BUTTON_ID, backLabel, backAction, getFeedBackPanel());
		backButton.setDefaultFormProcessing(false);
		if (backAction != null) {
			backAction.setComponent(this);
		} else {
			backButton.setVisible(false);
		}
	}

	@Override
	protected void paint() {
		add(feedBackPanel);
		paintBackButton();
	}

	/**
	 * Paints the go-back button.
	 */
	protected void paintBackButton() {
		Form<Void> backForm = new Form<Void>(BACK_FORM_ID);
		add(backForm);
		backForm.add(backButton);
		if (getDefinition().getBackAction() != null) {
			backForm.setVisible(true);
		} else {
			backForm.setVisible(false);
		}
	}
}