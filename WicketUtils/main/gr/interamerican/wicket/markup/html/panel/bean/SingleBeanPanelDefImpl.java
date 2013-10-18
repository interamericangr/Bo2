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
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.condition.AjaxEnabledCondition;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.creators.PanelCreatorMode;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDefImpl;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

/**
 * Implementation of {@link SingleBeanPanelDef}.
 * 
 * @param <B> 
 */
public class SingleBeanPanelDefImpl
<B extends Serializable> 
extends ServicePanelWithBackDefImpl
implements SingleBeanPanelDef<B> {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link CallbackAction} for query execution
	 */
	private CallbackAction beanAction;
	
	/**
	 * beanAction authorization flag.
	 */
	private Flag beanActionFlag;
	
	/**
	 * Panel creator.
	 */
	private PanelCreator<B> beanFieldsPanelCreator;
	
	/**
	 * Model.
	 */
	private IModel<B> model;
	
	/**
	 * 
	 */
	private PanelCreatorMode beanFieldsPanelMode;
	
	/**
	 * Execute button label.
	 */
	private IModel<String> executeLabelModel;
	
	/**
	 * Clear button label.
	 */
	private IModel<String> clearLabelModel;
	
	/**
	 * Form validator.
	 */
	private AjaxEnabledCondition<B> formValidator;
	
	/**
	 * Controls whether the single bean panel form contains
	 * a file upload item.
	 */
	private Boolean singleBeanFormContainsFileUpload;
	
	/**
	 * Show clear button.
	 */
	private Boolean showClearButton;
	
	/**
	 * Legend of the panel fieldset.
	 */
	private IModel<String> panelLabelModel;
	
	public CallbackAction getBeanAction() {
		return this.beanAction;
	}

	public void setBeanAction(CallbackAction beanAction) {
		this.beanAction = beanAction;
	}

	public PanelCreator<B> getBeanFieldsPanelCreator() {
		return beanFieldsPanelCreator;
	}

	public void setBeanFieldsPanelCreator(PanelCreator<B> panelCreator) {
		this.beanFieldsPanelCreator = panelCreator;
	}

	public IModel<B> getBeanModel() {
		return model;
	}

	public void setBeanModel(IModel<B> model) {
		this.model = model;
	}

	public void setExecuteLabelModel(IModel<String> label) {
		this.executeLabelModel = label;
	}

	public IModel<String> getExecuteLabelModel() {
		return executeLabelModel;
	}

	public void setFormValidator(AjaxEnabledCondition<B> validator) {
		this.formValidator = validator;
	}

	public AjaxEnabledCondition<B> getFormValidator() {
		return formValidator;
	}
	
	public PanelCreatorMode getBeanFieldsPanelMode(){
		return beanFieldsPanelMode;
	}
	
	public void setBeanFieldsPanelMode(PanelCreatorMode mode) {
		this.beanFieldsPanelMode = mode;
	}

	public IModel<String> getClearLabelModel() {
		return clearLabelModel;
	}

	public void setClearLabelModel(IModel<String> label) {
		this.clearLabelModel = label;
	}

	public void setShowClearButton(Boolean showClearButton) {
		this.showClearButton = showClearButton;
	}

	public Boolean getShowClearButton() {
		return showClearButton;
	}
	
	public Boolean getSingleBeanFormContainsFileUpload() {
		return singleBeanFormContainsFileUpload;
	}

	public void setSingleBeanFormContainsFileUpload(Boolean singleBeanFormContainsFileUpload) {
		this.singleBeanFormContainsFileUpload = singleBeanFormContainsFileUpload;
	}

	public Flag getBeanActionFlag() {
		return beanActionFlag;
	}

	public void setBeanActionFlag(Flag beanActionFlag) {
		this.beanActionFlag = beanActionFlag;
	}

	public IModel<String> getPanelLabelModel() {
		return panelLabelModel;
	}

	public void setPanelLabelModel(IModel<String> panelLabel) {
		this.panelLabelModel = panelLabel;
	}

}
