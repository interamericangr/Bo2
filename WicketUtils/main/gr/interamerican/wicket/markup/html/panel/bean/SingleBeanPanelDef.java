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

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.condition.AbstractAjaxEnabledCondition;
import gr.interamerican.wicket.condition.AjaxEnabledCondition;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDef;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

import java.io.Serializable;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * {@link ServicePanelDef} of {@link SingleBeanPanel}.
 * 
 * @param <B> 
 *        Type of bean presented by this panel.
 */
public interface SingleBeanPanelDef <B extends Serializable> 
extends ServicePanelWithBackDef, ModeAwareBeanPanelDef<B> {

	/**
	 * Gets the panel action.
	 * 
	 * A SingleBeanPanel allows an action to be performed.
	 * This is the action performed by the panel. Such an
	 * action can be a CRUD operation, or another action 
	 * that takes the panel's bean as input. If this is null,
	 * the executeButton is hidden.
	 *
	 * @return Returns the action
	 */
	CallbackAction getBeanAction();

	/**
	 * [OPTIONAL]
	 * Assigns a new value to the beanAction.
	 * 
	 * @see #getBeanAction()
	 *
	 * @param beanAction the queryAction to set
	 */
	void setBeanAction(CallbackAction beanAction);
	
	/**
	 * Gets the beanActionFlag.
	 * 
	 * @return Returns the beanActionFlag.
	 */
	public Flag getBeanActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the beanActionFlag.
	 * 
	 * @param beanActionFlag
	 */
	public void setBeanActionFlag(Flag beanActionFlag);
	
	/**
	 * Gets the {@link PanelCreator}. The PanelCreator creates a 
	 * panel that contains the fields of the bean form and has them
	 * bound to the {@link IModel} that is supplied. No {@link Form}
	 * is required in the created Panel.
	 * 
	 * @return Returns the panel creator.
	 */
	PanelCreator<B> getBeanFieldsPanelCreator();
	
	/**
	 * [MANDATORY]
	 * Sets the panel creator.
	 * 
	 * @see #getBeanFieldsPanelCreator()
	 * 
	 * @param panelCreator 
	 *        The panelCreator to set.
	 */
	void setBeanFieldsPanelCreator(PanelCreator<B> panelCreator);
	
	/**
	 * Gets the execute button label. This is the label that will be
	 * shown on the button of this panel.
	 * 
	 * @return Returns the execute button label.
	 */
	public IModel<String> getExecuteLabelModel();
	
	/**
	 * [OPTIONAL] Sets the execute button label.
	 * 
	 * @see #getExecuteLabelModel()
	 * 
	 * @param label
	 *        The label to set.
	 */
	public void setExecuteLabelModel(IModel<String> label);
	
	/**
	 * Gets the clear button label. This is the label that will be
	 * shown on the clear button of this panel.
	 * 
	 * @return Returns the clear button label.
	 */
	public IModel<String> getClearLabelModel();
	
	/**
	 * [OPTIONAL] Sets the clear button label.
	 * 
	 * @see #getClearLabelModel()
	 * 
	 * @param label
	 *        The label to set.
	 */
	public void setClearLabelModel(IModel<String> label);
	
	/**
	 * [OPTIONAL]
	 * Sets a {@link Condition} that checks the selected item before
	 * submitting the beanForm. The validator's check(B b) method
	 * returns true if the validation is successful. The intention
	 * is to alert the user that erroneous input has been added and
	 * correct it. There is, however the possibility that the user
	 * will press cancel instead. This may lead to inconsistent data,
	 * as the beanModel has been modified already (even though the UI
	 * may not indicate this). For this reason, it is preferred to work 
	 * with a temporary copy of the bean until the user changes have 
	 * been validated.
	 * 
	 * @param validator
	 *        The validator to set.
	 */
	public void setFormValidator(AjaxEnabledCondition<B> validator);
	
	/**
	 * Gets the validator.
	 * 
	 * 
	 * @see #setFormValidator(AjaxEnabledCondition)
	 * @see AbstractAjaxEnabledCondition
	 * 
	 * @return Returns the validator of this panel's form.
	 */
	public AjaxEnabledCondition<B> getFormValidator();
	
	/**
	 * [OPTIONAL]
	 * Sets the showClearButton.
	 * If the appearance of a clear form button is required, this should
	 * be set to true. This works by creating a new bean instance and setting
	 * it as the form model object. SingleBeanPanel attempts to create the 
	 * instance by resolving the bean class and using the default constructor.
	 * If this raises a RuntimeException, the user must override method
	 * <code>newBean()</code> explicitly.
	 * This property defaults to false.
	 * 
	 * @param showClearButton
	 */
	public void setShowClearButton(Boolean showClearButton);
	
	/**
	 * Gets the showClearButton.
	 * 
	 * @see #setShowClearButton(Boolean)
	 * 
	 * @return showClearButton.
	 */
	public Boolean getShowClearButton();
	
	/**
	 * Gets whether the single bean panel form includes a file
	 * upload item. 
	 * 
	 * @return whether the single bean panel form includes a file upload item.
	 */
	Boolean getSingleBeanFormContainsFileUpload();
	
	/**
	 * [OPTIONAL]
	 * Sets whether the single bean panel form includes a file upload item.
	 * If this is set to true, the single bean panel form is set as 
	 * <code>setMultipart(true)</code>. Defaults to false.
	 * 
	 * @param singleBeanFormContainsFileUpload
	 */
	void setSingleBeanFormContainsFileUpload(Boolean singleBeanFormContainsFileUpload);
	
	/**
	 * Gets the label displayed on top of this panel.
	 * 
	 * @return panelLabel.
	 */
	IModel<String> getPanelLabelModel();
	
	/**
	 * [OPTIONAL]
	 * Sets the label displayed on top of this panel.
	 * 
	 * @param panelLabel
	 */
	void setPanelLabelModel(IModel<String> panelLabel);
	
}
