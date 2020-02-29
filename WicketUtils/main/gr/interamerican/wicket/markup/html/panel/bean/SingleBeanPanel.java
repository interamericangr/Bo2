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
package gr.interamerican.wicket.markup.html.panel.bean;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.condition.AjaxCondition;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.markup.html.panel.ServicePanelUtils;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBack;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.util.resource.StringResourceUtils;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;

/**
 * {@link ServicePanel} that allows the user to perform an action
 * on a bean. This can be, for example, a save/update action or an
 * action that uses this bean as input to produce a result.
 * 
 * The action implementation code should expect to find the bean
 * as shown in the UI (i.e. with user changes) in the <code>beanModel</code>
 * property of this panel's definition.
 *
 * @param <B> the generic type
 */
public class SingleBeanPanel<B extends Serializable> 
extends ServicePanelWithBack {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * service panel id. 
	 */
	public static final String SERVICE_PANEL_ID = "singleBeanPanel"; //$NON-NLS-1$
	
	/**
	 * wicket id of label in legend element.
	 */
	protected static final String PANEL_LABEL_ID = "panelLabel"; //$NON-NLS-1$
	
	/**
	 * wicket id of execute button.
	 */
	public static final String EXEC_BUTTON_ID = "executeButton"; //$NON-NLS-1$
	
	/**
	 * wicket id of clear button.
	 */
	public static final String CLEAR_BUTTON_ID = "clearButton"; //$NON-NLS-1$
	
	/**
	 * wicket id of clear button label.
	 */
	protected static final String CLEAR_BUTTON_TEXT_ID = "clearButtonText"; //$NON-NLS-1$
	
	/**
	 * wicket id of bean form.
	 */
	public static final String FORM_ID = "beanForm"; //$NON-NLS-1$
	
	/**
	 * wicket id of criteria form's fields panel.
	 */
	public static final String FORM_FIELDS_PANEL_ID = "beanFormFieldsPanel"; //$NON-NLS-1$
	
	/**
	 * Form fields panel.
	 */
	protected Panel formFieldsPanel;
	
	/**
	 * Form.
	 */
	protected Form<B> beanForm;
	
	/**
	 * Form submit button.
	 */
	protected CallbackAjaxButton executeButton;
	
	/**
	 * Clear form link.
	 */
	protected CallbackAjaxButton clearButton;
	
	/**
	 * Label of the legend.
	 */
	protected Label legendLabel;

	/**
	 * Creates a new SingleBeanPanel object. 
	 *
	 * @param definition the definition
	 */
	public SingleBeanPanel(SingleBeanPanelDef<B> definition) {
		super(definition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SingleBeanPanelDef<B> getDefinition() {
		return (SingleBeanPanelDef<B>)definition;
	}	
	
	@SuppressWarnings("nls")
	@Override
	protected void init() {
		super.init();
		IModel<String> executeLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SBP_EXECUTE_BTN_LABEL, null, getDefinition().getExecuteLabelModel(), "Execute");
		IModel<String> clearLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SBP_CLEAR_BTN_LABEL, null, getDefinition().getClearLabelModel(), "Clear");
		IModel<String> legendLabelModel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SBP_PANEL_LABEL, null, getDefinition().getPanelLabelModel(), StringConstants.EMPTY);

		executeButton = new CallbackAjaxButton(EXEC_BUTTON_ID, executeLabel, this::submit, getFeedBackPanel());

		ServicePanelUtils.disableButton(getDefinition(), getDefinition().getBeanActionFlag(), executeButton);
		
		clearButton = new CallbackAjaxButton(CLEAR_BUTTON_ID, clearLabel, this::clearForm, getFeedBackPanel());
		clearButton.setDefaultFormProcessing(false);
		if(!getDefinition().getShowClearButton()) {
			clearButton.setVisible(false);
		}
		
		PanelCreator<B> creator = getDefinition().getBeanFieldsPanelCreator();

		beanForm = new Form<B>(FORM_ID, getDefinition().getBeanModel());
		beanForm.setOutputMarkupId(true);
		if(getDefinition().getSingleBeanFormContainsFileUpload()) {
			beanForm.setMultiPart(true);
		}
		formFieldsPanel = creator.createPanel(getBeanPanelDef());
		formFieldsPanel.setOutputMarkupId(true);
		
		legendLabel = new Label(PANEL_LABEL_ID, legendLabelModel);
	}
	
	@Override
	protected void paint() {
		add(legendLabel);
		beanForm.add(formFieldsPanel);
		beanForm.add(executeButton);
		beanForm.add(clearButton);
		beanForm.add(backButton);
		add(beanForm);
		add(feedBackPanel);
		if(getDefinition().getBeanAction() == null) {
			executeButton.setVisible(false);
		}
	}
	
	@SuppressWarnings("nls")
	@Override
	protected void validateDef() {
		super.validateDef();
		
		if(getDefinition().getFormValidator() == null) {
			getDefinition().setFormValidator(AjaxCondition.alwaysTrue());
		}
		if(getDefinition().getShowClearButton() == null) {
			getDefinition().setShowClearButton(false);
		}
		if(getDefinition().getSingleBeanFormContainsFileUpload()==null) {
			getDefinition().setSingleBeanFormContainsFileUpload(false);
		}
		
		String msg = StringConstants.EMPTY;
		if(getDefinition().getBeanFieldsPanelCreator() == null) {
			msg += "Cannot initialize a SingleBeanPanel with null beanFieldsPanelCreator.\n";
		}
		if(getDefinition().getBeanModel() == null) {
			msg += "Cannot initialize a SingleBeanPanel with null beanModel.\n";
		}
		if(getDefinition().getBeanModel().getObject() == null) {
			msg += "Cannot initialize a SingleBeanPanel with null beanModel model object.\n";
		}
		if(!StringConstants.EMPTY.equals(msg)) {
			throw new RuntimeException(msg);
		}
		if (getDefinition().getBeanCreator() == null) {
			@SuppressWarnings("unchecked")
			Class<B> beanClass = (Class<B>) getDefinition().getBeanModel().getObject().getClass();
			getDefinition().setBeanCreator(ReflectionUtils.supplier(beanClass));
		}
	}
	
	/**
	 * Creates a new instance of B.
	 * 
	 * @return a new B.
	 * 
	 * @deprecated Stop Overwriting this - use
	 *             {@link SingleBeanPanelDef#setBeanCreator(gr.interamerican.bo2.utils.functions.SerializableSupplier)}
	 */
	@Deprecated
	protected B newBean() {
		return getDefinition().getBeanCreator().get();
	}
	
	/**
	 * Creates the definition for the bean panel that shows B's fields.
	 * 
	 * @return {@link ModeAwareBeanPanelDef}.
	 */
	private ModeAwareBeanPanelDef<B> getBeanPanelDef() {
		ModeAwareBeanPanelDef<B> def = new ModeAwareBeanPanelDefImpl<B>();
		ReflectionUtils.copyProperties(getDefinition(), def);
		def.setWicketId(FORM_FIELDS_PANEL_ID);
		return def;
	}

	/**
	 * Submits the form of this.
	 * 
	 * @param target
	 *            the target
	 * @param form
	 *            the form
	 */
	@SuppressWarnings("deprecation")
	public void submit(AjaxRequestTarget target, Form<?> form) {
		if (!ServicePanelUtils.authorizedByFlag(getDefinition().getBeanActionFlag())) {
			error(getDefinition().getBeanActionFlag().getDownMessage());
			return;
		}
		target.add(this);
		B bean = getDefinition().getBeanModel().getObject();
		if (!getDefinition().getFormValidator().check(bean, target, this)) {
			return;
		}
		PickAction<B> action = getDefinition().getBeanAction();
		// backwards compatibility
		if (action instanceof gr.interamerican.wicket.callback.CallbackAction) {
			gr.interamerican.wicket.callback.CallbackAction callback = (gr.interamerican.wicket.callback.CallbackAction) action;
			callback.callBack(target, form);
		} else {
			action.doPick(target, bean);
		}
	}

	/**
	 * Clears the form.
	 *
	 * @param target
	 *            the target
	 */
	void clearForm(AjaxRequestTarget target) {
		target.add(this);
		B newBean = newBean();
		getDefinition().getBeanModel().setObject(newBean);
		beanForm.setDefaultModelObject(newBean);
		Panel replacement = getDefinition().getBeanFieldsPanelCreator().createPanel(getBeanPanelDef());
		formFieldsPanel.replaceWith(replacement);
		formFieldsPanel = replacement;
	}
}