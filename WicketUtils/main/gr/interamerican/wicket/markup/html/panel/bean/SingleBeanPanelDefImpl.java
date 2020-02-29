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

import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.bo2.utils.functions.SerializableSupplier;
import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.condition.AjaxCondition;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.creators.PanelCreatorMode;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDefImpl;

/**
 * Implementation of {@link SingleBeanPanelDef}.
 *
 * @param <B> the generic type
 */
public class SingleBeanPanelDefImpl <B extends Serializable> 
extends ServicePanelWithBackDefImpl
implements SingleBeanPanelDef<B> {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/** {@link PickAction} for query execution. */
	private PickAction<B> beanAction;
	
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
	
	/** The bean fields panel mode. */
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
	private AjaxCondition<B> formValidator;
	
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

	/**
	 * A {@link SerializableSupplier} that creates new instances of the bean
	 */
	private SerializableSupplier<B> beanCreator;

	@Override
	public PickAction<B> getBeanAction() {
		return this.beanAction;
	}

	@Override
	public void setBeanAction(PickAction<B> beanAction) {
		this.beanAction = beanAction;
	}

	@Override
	public PanelCreator<B> getBeanFieldsPanelCreator() {
		return beanFieldsPanelCreator;
	}

	@Override
	public void setBeanFieldsPanelCreator(PanelCreator<B> panelCreator) {
		this.beanFieldsPanelCreator = panelCreator;
	}

	@Override
	public IModel<B> getBeanModel() {
		return model;
	}

	@Override
	public void setBeanModel(IModel<B> model) {
		this.model = model;
	}

	@Override
	public void setExecuteLabelModel(IModel<String> label) {
		this.executeLabelModel = label;
	}

	@Override
	public IModel<String> getExecuteLabelModel() {
		return executeLabelModel;
	}

	@Override
	public void setFormValidator(AjaxCondition<B> validator) {
		this.formValidator = validator;
	}

	@Override
	public AjaxCondition<B> getFormValidator() {
		return formValidator;
	}

	@Override
	public PanelCreatorMode getBeanFieldsPanelMode(){
		return beanFieldsPanelMode;
	}

	@Override
	public void setBeanFieldsPanelMode(PanelCreatorMode mode) {
		this.beanFieldsPanelMode = mode;
	}

	@Override
	public IModel<String> getClearLabelModel() {
		return clearLabelModel;
	}

	@Override
	public void setClearLabelModel(IModel<String> label) {
		this.clearLabelModel = label;
	}

	@Override
	public void setShowClearButton(Boolean showClearButton) {
		this.showClearButton = showClearButton;
	}

	@Override
	public Boolean getShowClearButton() {
		return showClearButton;
	}

	@Override
	public Boolean getSingleBeanFormContainsFileUpload() {
		return singleBeanFormContainsFileUpload;
	}

	@Override
	public void setSingleBeanFormContainsFileUpload(Boolean singleBeanFormContainsFileUpload) {
		this.singleBeanFormContainsFileUpload = singleBeanFormContainsFileUpload;
	}

	@Override
	public Flag getBeanActionFlag() {
		return beanActionFlag;
	}

	@Override
	public void setBeanActionFlag(Flag beanActionFlag) {
		this.beanActionFlag = beanActionFlag;
	}

	@Override
	public IModel<String> getPanelLabelModel() {
		return panelLabelModel;
	}

	@Override
	public void setPanelLabelModel(IModel<String> panelLabel) {
		this.panelLabelModel = panelLabel;
	}

	@Override
	public void setBeanCreator(SerializableSupplier<B> beanCreator) {
		this.beanCreator = beanCreator;
	}

	@Override
	public SerializableSupplier<B> getBeanCreator() {
		return beanCreator;
	}
}