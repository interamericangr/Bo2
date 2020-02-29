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
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;

import gr.interamerican.wicket.callback.ICallbackAction;
import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * AjaxButton that executes a callback action.
 */
@SuppressWarnings("deprecation")
public class CallbackAjaxButton extends AjaxButton {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Action to execute when pressed.
	 */
	final ICallbackAction action;

	/**
	 * Feedback panel for error messages.
	 */
	FeedbackPanel feedbackPanel;

	/**
	 * Text to be displayed on the confirmation dialog (optional)
	 */
	String confirmationText = null;

	/**
	 * Creates a new CallbackAjaxButton object.
	 * 
	 * This constructor will create a {@link StringResourceModel} for the button
	 * with the value button.id.
	 *
	 * @param id
	 *            Id of the button.
	 * @param action
	 *            Callback action to be executed on the button press.
	 * @param feedbackPanel
	 *            Feedback panel for error messages.
	 * @deprecated Use other Constructors
	 */
	@Deprecated
	public CallbackAjaxButton(String id, gr.interamerican.wicket.callback.CallbackAction action,
			FeedbackPanel feedbackPanel) {
		this(id, new ResourceModel("button." + id, ""), action, feedbackPanel); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Creates a new CallbackAjaxButton object.
	 * 
	 * This constructor will create a {@link StringResourceModel} for the button
	 * with the value button.id.
	 *
	 * @param id
	 *            Id of the button.
	 * @param action
	 *            Callback action to be executed on the button press.
	 * @deprecated Use other Constructors
	 */
	@Deprecated
	public CallbackAjaxButton(String id, gr.interamerican.wicket.callback.CallbackAction action) {
		this(id, action, null);
	}

	/**
	 * Creates a new CallbackAjaxButton object.
	 *
	 * @param id
	 *            Id of the button.
	 * @param model
	 *            Model for the button.
	 * @param action
	 *            Callback action to be executed on the button press.
	 * @param feedbackPanel
	 *            Feedback panel for error messages.
	 * @deprecated Use other Constructors
	 */
	@Deprecated
	public CallbackAjaxButton(String id, IModel<String> model, gr.interamerican.wicket.callback.CallbackAction action,
			FeedbackPanel feedbackPanel) {
		this(id, model, (ICallbackAction) action, feedbackPanel);
	}

	/**
	 * Creates a new CallbackAjaxButton object.
	 *
	 * @param id
	 *            Id of the button.
	 * @param model
	 *            Model for the button.
	 * @param action
	 *            Callback action to be executed on the button press.
	 * @deprecated Use other Constructors
	 */
	@Deprecated
	public CallbackAjaxButton(String id, IModel<String> model, gr.interamerican.wicket.callback.CallbackAction action) {
		this(id, model, (ICallbackAction) action);
	}

	/**
	 * Public Constructor with a model and an {@link ICallbackAction}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param model
	 *            Model for the button.
	 * @param action
	 *            ICallbackAction to be executed on the button press.
	 * @deprecated To be removed on bo2 v4 due to wicket changes
	 */
	@Deprecated
	public CallbackAjaxButton(String id, IModel<String> model, ICallbackAction action) {
		super(id, model);
		this.action = action;
	}

	/**
	 * Public Constructor with a model, an {@link ICallbackAction} and a
	 * {@link FeedbackPanel}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param model
	 *            Model for the button.
	 * @param action
	 *            ICallbackAction to be executed on the button press.
	 * @param feedbackPanel
	 *            Feedback panel for error messages.
	 * @deprecated To be removed on bo2 v4 due to wicket changes
	 */
	@Deprecated
	public CallbackAjaxButton(String id, IModel<String> model, ICallbackAction action, FeedbackPanel feedbackPanel) {
		this(id, model, action);
		this.feedbackPanel = feedbackPanel;
	}

	/**
	 * Public Constructor with a model, an {@link ICallbackAction} and a
	 * {@link FeedbackPanel}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param model
	 *            Model for the button.
	 * @param action
	 *            ICallbackAction to be executed on the button press.
	 * @param feedbackPanel
	 *            Feedback panel for error messages.
	 * @param confirmationText
	 *            Text to be displayed on the confirmation dialog (optional)
	 * @deprecated To be removed on bo2 v4 due to wicket changes
	 */
	@Deprecated
	public CallbackAjaxButton(String id, IModel<String> model, ICallbackAction action, FeedbackPanel feedbackPanel,
			String confirmationText) {
		this(id, model, action);
		this.confirmationText = confirmationText;
		this.feedbackPanel = feedbackPanel;
	}

	/**
	 * Public Constructor without a {@link IModel}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param action
	 *            ICallbackAction action to be executed on the button press.
	 * @deprecated To be removed on bo2 v4 due to wicket changes
	 */
	@Deprecated
	public CallbackAjaxButton(String id, ICallbackAction action) {
		this(id, null, action);
	}

	/**
	 * Public Constructor without a {@link IModel}, but with a
	 * {@link FeedbackPanel}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param action
	 *            ICallbackAction action to be executed on the button press.
	 * @param feedbackPanel
	 *            Feedback panel for error messages.
	 * @deprecated To be removed on bo2 v4 due to wicket changes
	 */
	@Deprecated
	public CallbackAjaxButton(String id, ICallbackAction action, FeedbackPanel feedbackPanel) {
		this(id, null, action, feedbackPanel);
	}

	/**
	 * Public Constructor with a model, an {@link LegacyCallbackAction} and a
	 * {@link FeedbackPanel}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param model
	 *            Model for the button.
	 * @param action
	 *            LegacyCallbackAction to be executed on the button press.
	 * @param feedbackPanel
	 *            Feedback panel for error messages.
	 */
	public CallbackAjaxButton(String id, IModel<String> model, LegacyCallbackAction action,
			FeedbackPanel feedbackPanel) {
		this(id, model, ICallbackAction.fromLegacy(action), feedbackPanel);
	}

	/**
	 * Public Constructor with a model, an {@link LegacyCallbackAction} and a
	 * {@link FeedbackPanel} and the confirmationText.
	 *
	 * @param id
	 *            Id of the button.
	 * @param model
	 *            Model for the button.
	 * @param action
	 *            LegacyCallbackAction to be executed on the button press.
	 * @param feedbackPanel
	 *            Feedback panel for error messages.
	 * @param confirmationText 
	 *            Text to be displayed on the confirmation dialog (optional)
	 */
	public CallbackAjaxButton(String id, IModel<String> model, LegacyCallbackAction action,
			FeedbackPanel feedbackPanel, String confirmationText) {
		this(id, model, ICallbackAction.fromLegacy(action), feedbackPanel, confirmationText);
	}

	/**
	 * Public Constructor with a model and an {@link LegacyCallbackAction}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param model
	 *            Model for the button.
	 * @param action
	 *            LegacyCallbackAction to be executed on the button press.
	 */
	public CallbackAjaxButton(String id, IModel<String> model, LegacyCallbackAction action) {
		this(id, model, ICallbackAction.fromLegacy(action));
	}

	/**
	 * Public Constructor without a {@link IModel}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param action
	 *            LegacyCallbackAction action to be executed on the button press
	 */
	public CallbackAjaxButton(String id, LegacyCallbackAction action) {
		this(id, null, ICallbackAction.fromLegacy(action));
	}

	/**
	 * Public Constructor without a {@link IModel}, but with a
	 * {@link FeedbackPanel}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param action
	 *            LegacyCallbackAction action to be executed on the button press
	 * @param feedbackPanel
	 *            Feedback panel for error messages.
	 */
	public CallbackAjaxButton(String id, LegacyCallbackAction action, FeedbackPanel feedbackPanel) {
		this(id, null, action, feedbackPanel);
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		action.doInvoke(target, form);
		renderFeedbackPanel(target);
	}

	@Override
	public void onError(AjaxRequestTarget target, Form<?> arg1) {
		renderFeedbackPanel(target);
	}

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
		super.updateAjaxAttributes(attributes);
		if (confirmationText != null) {
			WicketUtils.addConfirmationDialog(confirmationText, attributes);
		}
	}

	/**
	 * Render the {@link FeedbackPanel}.
	 *
	 * @param target
	 *            the target
	 */
	private void renderFeedbackPanel(AjaxRequestTarget target) {
		if (feedbackPanel != null) {
			target.add(feedbackPanel);
		}
	}
}