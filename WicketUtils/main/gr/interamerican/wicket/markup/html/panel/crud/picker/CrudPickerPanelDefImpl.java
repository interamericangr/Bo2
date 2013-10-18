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
package gr.interamerican.wicket.markup.html.panel.crud.picker;

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.condition.AjaxEnabledCondition;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanelDefImpl;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

/**
 * Implementation of {@link CrudPickerPanelDef}.
 * 
 * @param <B> 
 */
public class CrudPickerPanelDefImpl
<B extends Serializable> 
extends PickerPanelDefImpl<B>
implements CrudPickerPanelDef<B> {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Delete action.
	 */
	private CallbackAction deleteAction;
	
	/**
	 * deleteAction authorization flag.
	 */
	private Flag deleteActionFlag;
	
	/**
	 * Save action.
	 */
	private CallbackAction saveAction;
	
	/**
	 * saveAction authorization flag.
	 */
	private Flag saveActionFlag;
	
	/**
	 * Update action.
	 */
	private CallbackAction updateAction;
	
	/**
	 * updateAction authorization flag.
	 */
	private Flag updateActionFlag;
	
	/**
	 * Panel creator.
	 */
	private PanelCreator<B> beanFieldsPanelCreator;
	
	/**
	 * readBeforeEdit.
	 */
	private Boolean readBeforeEdit;
	
	/**
	 * Delete button label.
	 */
	private IModel<String> deleteLabelModel;
	
	/**
	 * Save button label.
	 */
	private IModel<String> newLabelModel;
	
	/**
	 * Edit button label.
	 */
	private IModel<String> editLabelModel;
	
	/**
	 * View button label.
	 */
	private IModel<String> viewLabelModel;
	
	/**
	 * Save label for the SingleBeanPanel.
	 */
	private IModel<String> saveLabelModel;
	
	/**
	 * Update label for SingleBeanPanel.
	 */
	private IModel<String> updateLabelModel;
	
	/**
	 * Clear label for SingleBeanPanel.
	 */
	private IModel<String> clearLabelModel;
	
	/**
	 * Back label of SingleBeanPanel.
	 */
	private IModel<String> beanFieldsPanelBackLabelModel;
	
	/**
	 * SingleBeanPanel label.
	 */
	private IModel<String> singleBeanPanelLabelModel;
	
	/**
	 * Indicates if the view button is shown.
	 */
	private Boolean viewEnabled;
	
	/**
	 * Validator upon saving a new item.
	 */
	private AjaxEnabledCondition<B> saveValidator;
	
	/**
	 * Validator upon updating an existing item.
	 */
	private AjaxEnabledCondition<B> updateValidator;
	
	/**
	 * Validator upon selecting an existing item for editing.
	 */
	private AjaxEnabledCondition<B> preEditValidator;
	
	/**
	 * Validator upon updating an existing item.
	 */
	private AjaxEnabledCondition<B> deleteValidator;
	
	/**
	 * Action that refreshes the rows of the data table from the 
	 * persistence layer after any save or update.
	 */
	private CallbackAction refreshAfterDataOpAction;
	
	/**
	 * Controls whether the single bean panel form contains
	 * a file upload item.
	 */
	private Boolean singleBeanFormContainsFileUpload;
	
	/**
	 * Controls whether pressing the delete will open a pop-up confirmation dialog.
	 */
	private Boolean requestConfirmOnDelete;
	
	/**
	 * Controls whether to hide the buttons of a {@link SingleBeanPanel}.
	 */
	private Boolean hideSingleBeanPanelButtons;
	
	/**
	 * Controls whether the single bean panel will be disabled explicitly
	 * by the user when in view mode.
	 */
	private Boolean customSingleBeanPanelDisabling;

	public CallbackAction getDeleteAction() {
		return deleteAction;
	}

	public void setDeleteAction(CallbackAction deleteAction) {
		this.deleteAction = deleteAction;
		
	}

	public CallbackAction getUpdateAction() {
		return updateAction;
	}

	public void setUpdateAction(CallbackAction updateAction) {
		this.updateAction = updateAction;
	}

	public CallbackAction getSaveAction() {
		return saveAction;
	}

	public void setSaveAction(CallbackAction saveAction) {
		this.saveAction = saveAction;
	}

	public PanelCreator<B> getBeanFieldsPanelCreator() {
		return beanFieldsPanelCreator;
	}

	public void setBeanFieldsPanelCreator(PanelCreator<B> beanFieldsPanelCreator) {
		this.beanFieldsPanelCreator = beanFieldsPanelCreator;
	}

	public Boolean getReadBeforeEdit() {
		return readBeforeEdit;
	}

	public void setReadBeforeEdit(Boolean readBeforeEdit) {
		this.readBeforeEdit = readBeforeEdit;
	}
	
	public IModel<String> getDeleteLabelModel() {
		return deleteLabelModel;
	}

	public void setDeleteLabelModel(IModel<String> label) {
		this.deleteLabelModel = label;
	}

	public IModel<String> getNewLabelModel() {
		return newLabelModel;
	}

	public void setNewLabelModel(IModel<String> label) {
		this.newLabelModel = label;
	}

	public IModel<String> getEditLabelModel() {
		return editLabelModel;
	}

	public void setEditLabelModel(IModel<String> label) {
		this.editLabelModel = label;
	}

	public IModel<String> getSaveLabelModel() {
		return saveLabelModel;
	}

	public void setSaveLabelModel(IModel<String> label) {
		this.saveLabelModel = label;
	}

	public IModel<String> getViewLabelModel() {
		return viewLabelModel;
	}

	public void setViewLabelModel(IModel<String> label) {
		this.viewLabelModel = label;
	}

	public Boolean getViewEnabled() {
		return viewEnabled;
	}

	public void setViewEnabled(Boolean viewEnabled) {
		this.viewEnabled = viewEnabled;
	}

	public void setSaveValidator(AjaxEnabledCondition<B> validator) {
		this.saveValidator = validator;
	}

	public AjaxEnabledCondition<B> getSaveValidator() {
		return saveValidator;
	}

	public void setUpdateValidator(AjaxEnabledCondition<B> validator) {
		this.updateValidator = validator;
	}

	public AjaxEnabledCondition<B> getUpdateValidator() {
		return updateValidator;
	}
	
	public void setPreEditValidator(AjaxEnabledCondition<B> validator) {
		this.preEditValidator = validator;
	}

	public AjaxEnabledCondition<B> getPreEditValidator() {
		return preEditValidator;
	}

	public CallbackAction getRefreshAfterDataOpAction() {
		return refreshAfterDataOpAction;
	}

	public void setRefreshAfterDataOpAction(CallbackAction refreshAfterDataOpAction) {
		this.refreshAfterDataOpAction = refreshAfterDataOpAction;
	}

	public IModel<String> getBeanFieldsPanelBackLabelModel() {
		return beanFieldsPanelBackLabelModel;
	}

	public void setBeanFieldsPanelBackLabelModel(IModel<String> backLabel) {
		this.beanFieldsPanelBackLabelModel = backLabel;
	}

	public void setDeleteValidator(AjaxEnabledCondition<B> validator) {
		this.deleteValidator = validator;
	}

	public AjaxEnabledCondition<B> getDeleteValidator() {
		return deleteValidator;
	}

	public Boolean getSingleBeanFormContainsFileUpload() {
		return singleBeanFormContainsFileUpload;
	}

	public void setSingleBeanFormContainsFileUpload(Boolean singleBeanFormContainsFileUpload) {
		this.singleBeanFormContainsFileUpload = singleBeanFormContainsFileUpload;
	}

	public Boolean getCustomSingleBeanPanelDisabling() {
		return customSingleBeanPanelDisabling;
	}

	public void setCustomSingleBeanPanelDisabling(Boolean customSingleBeanPanelDisabling) {
		this.customSingleBeanPanelDisabling = customSingleBeanPanelDisabling;
	}

	public Flag getDeleteActionFlag() {
		return deleteActionFlag;
	}

	public void setDeleteActionFlag(Flag deleteActionFlag) {
		this.deleteActionFlag = deleteActionFlag;
	}

	public Flag getUpdateActionFlag() {
		return updateActionFlag;
	}

	public void setUpdateActionFlag(Flag updateActionFlag) {
		this.updateActionFlag = updateActionFlag;
	}

	public Flag getSaveActionFlag() {
		return saveActionFlag;
	}

	public void setSaveActionFlag(Flag saveActionFlag) {
		this.saveActionFlag = saveActionFlag;
	}

	public IModel<String> getUpdateLabelModel() {
		return updateLabelModel;
	}

	public void setUpdateLabelModel(IModel<String> label) {
		updateLabelModel = label;
	}

	public IModel<String> getClearLabelModel() {
		return clearLabelModel;
	}

	public void setClearLabelModel(IModel<String> label) {
		clearLabelModel = label;
	}
	
	public IModel<String> getSingleBeanPanelLabelModel() {
		return singleBeanPanelLabelModel;
	}

	public void setSingleBeanPanelLabelModel(IModel<String> label) {
		this.singleBeanPanelLabelModel = label;
	}

	public Boolean getRequestConfirmOnDelete() {
		return requestConfirmOnDelete;
	}

	public void setRequestConfirmOnDelete(Boolean requestConfirmOnDelete) {
		this.requestConfirmOnDelete = requestConfirmOnDelete;
	}
	
	public void setHideSingleBeanPanelButtons(Boolean hideSingleBeanPanelButtons) {
		this.hideSingleBeanPanelButtons = hideSingleBeanPanelButtons;
	}
	
	public Boolean getHideSingleBeanPanelButtons() {
		return hideSingleBeanPanelButtons;
	}

}
