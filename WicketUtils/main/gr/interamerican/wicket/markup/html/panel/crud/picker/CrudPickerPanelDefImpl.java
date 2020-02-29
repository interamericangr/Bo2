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

import java.io.Serializable;

import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.bo2.utils.functions.SerializableSupplier;
import gr.interamerican.bo2.utils.functions.SerializableUnaryOperator;
import gr.interamerican.wicket.callback.Consume;
import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.callback.ProcessAction;
import gr.interamerican.wicket.condition.AjaxCondition;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanelDefImpl;

/**
 * Implementation of {@link CrudPickerPanelDef}.
 *
 * @param <B> the generic type
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
	private Consume<B> deleteAction;
	
	/**
	 * deleteAction authorization flag.
	 */
	private Flag deleteActionFlag;
	
	/**
	 * Save action.
	 */
	private ProcessAction<B> saveAction;
	
	/**
	 * saveAction authorization flag.
	 */
	private Flag saveActionFlag;
	
	/**
	 * Update action.
	 */
	private ProcessAction<B> updateAction;
	
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
	private AjaxCondition<B> saveValidator;
	
	/**
	 * Validator upon updating an existing item.
	 */
	private AjaxCondition<B> updateValidator;
	
	/**
	 * Validator upon selecting an existing item for editing.
	 */
	private AjaxCondition<B> preEditValidator;
	
	/**
	 * Validator upon updating an existing item.
	 */
	private AjaxCondition<B> deleteValidator;
	
	/**
	 * Action that refreshes the rows of the data table from the 
	 * persistence layer after any save or update.
	 */
	private LegacyCallbackAction refreshAfterDataOpAction;
	
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

	/**
	 * A {@link SerializableSupplier} that creates new instances of the bean
	 */
	private SerializableSupplier<B> beanCreator;

	/**
	 * An {@link SerializableUnaryOperator} to apply on the selected bean before editing it
	 */
	private SerializableUnaryOperator<B> copyBean;

	/**
	 * An {@link SerializableUnaryOperator} to apply on a selected bean before
	 * viewing/editing it
	 */
	private SerializableUnaryOperator<B> readBean;

	@Override
	public Consume<B> getDeleteAction() {
		return deleteAction;
	}

	@Override
	public void setDeleteAction(Consume<B> deleteAction) {
		this.deleteAction = deleteAction;
	}

	@Override
	public ProcessAction<B> getUpdateAction() {
		return updateAction;
	}

	@Override
	public void setUpdateAction(ProcessAction<B> updateAction) {
		this.updateAction = updateAction;
	}

	@Override
	public ProcessAction<B> getSaveAction() {
		return saveAction;
	}

	@Override
	public void setSaveAction(ProcessAction<B> saveAction) {
		this.saveAction = saveAction;
	}

	@Override
	public PanelCreator<B> getBeanFieldsPanelCreator() {
		return beanFieldsPanelCreator;
	}

	@Override
	public void setBeanFieldsPanelCreator(PanelCreator<B> beanFieldsPanelCreator) {
		this.beanFieldsPanelCreator = beanFieldsPanelCreator;
	}

	@Override
	public Boolean getReadBeforeEdit() {
		return readBeforeEdit;
	}

	@Override
	public void setReadBeforeEdit(Boolean readBeforeEdit) {
		this.readBeforeEdit = readBeforeEdit;
	}

	@Override
	public IModel<String> getDeleteLabelModel() {
		return deleteLabelModel;
	}

	@Override
	public void setDeleteLabelModel(IModel<String> label) {
		this.deleteLabelModel = label;
	}

	@Override
	public IModel<String> getNewLabelModel() {
		return newLabelModel;
	}

	@Override
	public void setNewLabelModel(IModel<String> label) {
		this.newLabelModel = label;
	}

	@Override
	public IModel<String> getEditLabelModel() {
		return editLabelModel;
	}

	@Override
	public void setEditLabelModel(IModel<String> label) {
		this.editLabelModel = label;
	}

	@Override
	public IModel<String> getSaveLabelModel() {
		return saveLabelModel;
	}

	@Override
	public void setSaveLabelModel(IModel<String> label) {
		this.saveLabelModel = label;
	}

	@Override
	public IModel<String> getViewLabelModel() {
		return viewLabelModel;
	}

	@Override
	public void setViewLabelModel(IModel<String> label) {
		this.viewLabelModel = label;
	}

	@Override
	public Boolean getViewEnabled() {
		return viewEnabled;
	}

	@Override
	public void setViewEnabled(Boolean viewEnabled) {
		this.viewEnabled = viewEnabled;
	}

	@Override
	public void setSaveValidator(AjaxCondition<B> validator) {
		this.saveValidator = validator;
	}

	@Override
	public AjaxCondition<B> getSaveValidator() {
		return saveValidator;
	}

	@Override
	public void setUpdateValidator(AjaxCondition<B> validator) {
		this.updateValidator = validator;
	}

	@Override
	public AjaxCondition<B> getUpdateValidator() {
		return updateValidator;
	}

	@Override
	public void setPreEditValidator(AjaxCondition<B> validator) {
		this.preEditValidator = validator;
	}

	@Override
	public AjaxCondition<B> getPreEditValidator() {
		return preEditValidator;
	}

	@Override
	public LegacyCallbackAction getRefreshAfterDataOpAction() {
		return refreshAfterDataOpAction;
	}

	@Override
	public void setRefreshAfterDataOpAction(LegacyCallbackAction refreshAfterDataOpAction) {
		this.refreshAfterDataOpAction = refreshAfterDataOpAction;
	}

	@Override
	public IModel<String> getBeanFieldsPanelBackLabelModel() {
		return beanFieldsPanelBackLabelModel;
	}

	@Override
	public void setBeanFieldsPanelBackLabelModel(IModel<String> backLabel) {
		this.beanFieldsPanelBackLabelModel = backLabel;
	}

	@Override
	public void setDeleteValidator(AjaxCondition<B> validator) {
		this.deleteValidator = validator;
	}

	@Override
	public AjaxCondition<B> getDeleteValidator() {
		return deleteValidator;
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
	public Boolean getCustomSingleBeanPanelDisabling() {
		return customSingleBeanPanelDisabling;
	}

	@Override
	public void setCustomSingleBeanPanelDisabling(Boolean customSingleBeanPanelDisabling) {
		this.customSingleBeanPanelDisabling = customSingleBeanPanelDisabling;
	}

	@Override
	public Flag getDeleteActionFlag() {
		return deleteActionFlag;
	}

	@Override
	public void setDeleteActionFlag(Flag deleteActionFlag) {
		this.deleteActionFlag = deleteActionFlag;
	}

	@Override
	public Flag getUpdateActionFlag() {
		return updateActionFlag;
	}

	@Override
	public void setUpdateActionFlag(Flag updateActionFlag) {
		this.updateActionFlag = updateActionFlag;
	}

	@Override
	public Flag getSaveActionFlag() {
		return saveActionFlag;
	}

	@Override
	public void setSaveActionFlag(Flag saveActionFlag) {
		this.saveActionFlag = saveActionFlag;
	}

	@Override
	public IModel<String> getUpdateLabelModel() {
		return updateLabelModel;
	}

	@Override
	public void setUpdateLabelModel(IModel<String> label) {
		updateLabelModel = label;
	}

	@Override
	public IModel<String> getClearLabelModel() {
		return clearLabelModel;
	}

	@Override
	public void setClearLabelModel(IModel<String> label) {
		clearLabelModel = label;
	}

	@Override
	public IModel<String> getSingleBeanPanelLabelModel() {
		return singleBeanPanelLabelModel;
	}

	@Override
	public void setSingleBeanPanelLabelModel(IModel<String> label) {
		this.singleBeanPanelLabelModel = label;
	}

	@Override
	public Boolean getRequestConfirmOnDelete() {
		return requestConfirmOnDelete;
	}

	@Override
	public void setRequestConfirmOnDelete(Boolean requestConfirmOnDelete) {
		this.requestConfirmOnDelete = requestConfirmOnDelete;
	}

	@Override
	public void setHideSingleBeanPanelButtons(Boolean hideSingleBeanPanelButtons) {
		this.hideSingleBeanPanelButtons = hideSingleBeanPanelButtons;
	}

	@Override
	public Boolean getHideSingleBeanPanelButtons() {
		return hideSingleBeanPanelButtons;
	}

	@Override
	public void setBeanCreator(SerializableSupplier<B> beanCreator) {
		this.beanCreator = beanCreator;
	}

	@Override
	public SerializableSupplier<B> getBeanCreator() {
		return beanCreator;
	}

	@Override
	public void setCopyBean(SerializableUnaryOperator<B> copyBean) {
		this.copyBean = copyBean;
	}

	@Override
	public SerializableUnaryOperator<B> getCopyBean() {
		return copyBean;
	}

	@Override
	public void setReadBean(SerializableUnaryOperator<B> readBean) {
		this.readBean = readBean;
	}

	@Override
	public SerializableUnaryOperator<B> getReadBean() {
		return readBean;
	}
}