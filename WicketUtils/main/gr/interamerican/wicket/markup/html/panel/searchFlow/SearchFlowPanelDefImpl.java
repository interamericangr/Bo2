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
package gr.interamerican.wicket.markup.html.panel.searchFlow;

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.condition.AjaxEnabledCondition;
import gr.interamerican.wicket.creators.DataTableCreator;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDefImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;

/**
 * Implementation of {@link SearchFlowPanelDef}.
 * 
 * @param <C> criteria 
 * @param <B> bean
 */
public class SearchFlowPanelDefImpl
<C extends Serializable, B extends Serializable>
extends ServicePanelWithBackDefImpl
implements SearchFlowPanelDef<C, B> {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Selected result {@link CallbackAction}
	 */
	private CallbackAction pickAction;

	/**
	 * Authorization flag for pickAction. 
	 */
	private Flag pickActionFlag;
	
	/**
	 * Second selected result {@link CallbackAction}
	 */
	private CallbackAction secondPickAction;

	/**
	 * Authorization flag for secondPickAction. 
	 */
	private Flag secondPickActionFlag;
	
	/**
	 * RefreshListAfterPickAction.
	 */
	private Boolean refreshListAfterPickAction;
	
	/**
	 * query results
	 */
	private List<B> results;
	
	/** 
	 * Result DataTable creator 
	 */
	private DataTableCreator<B> dataTableCreator;
	
	/** 
	 * autoPickSingleResult field. 
	 */
	private Boolean autoPickSingleResult;
	
	/** 
	 * resultsHidesCriteria field. 
	 */
	private Boolean resultsHidesCriteria;
	
	/**
	 * Delete {@link CallbackAction}
	 */
	private CallbackAction deleteAction;
	
	/**
	 * Authorization flag for deleteAction. 
	 */
	private Flag deleteActionFlag;
	
	/**
	 * Save {@link CallbackAction}
	 */
	private CallbackAction saveAction;
	
	/**
	 * Authorization flag for saveAction. 
	 */
	private Flag saveActionFlag;
		
	/**
	 * update {@link CallbackAction}
	 */
	private CallbackAction updateAction;
	
	/**
	 * Authorization flag for updateAction. 
	 */
	private Flag updateActionFlag;
	
	/**
	 * query {@link CallbackAction}
	 */
	private CallbackAction queryAction;
	
	/**
	 * Bean fields {@link PanelCreator}
	 */
	private PanelCreator<B> beanFieldsPanelCreator;
	
	/**
	 * Criteria fields {@link PanelCreator}
	 */
	private PanelCreator<C> criteriaFieldsPanelCreator;
	
	/**
	 * Bean model.
	 */
	private IModel<B> beanModel;
	
	/**
	 * Criteria model.
	 */
	private IModel<C> criteriaModel;
	
	/**
	 * readBeforeEdit.
	 */
	private Boolean readBeforeEdit;
	
	/**
	 * viewEnabled.
	 */
	private Boolean viewEnabled;
	
	/**
	 * Controls whether pressing the delete will open a pop-up confirmation dialog.
	 */
	private Boolean requestConfirmOnDelete;
	
	/**
	 * Indicates if multiple selections are allowed.
	 */
	private Boolean allowMultipleSelections;
	
	/**
	 * Indicates whether the single bean panel form of the CrudPickerPanel results 
	 * panel includes a file upload item. 
	 */
	private Boolean singleBeanFormContainsFileUpload;
	
	/**
	 * Multiple selections model.
	 */
	private IModel<ArrayList<B>> selectionsModel;
	
	/**
	 * delete label
	 */
	private IModel<String> deleteLabelModel;
	
	/**
	 * new label
	 */
	private IModel<String> newLabelModel;
	
	/**
	 * edit label
	 */
	private IModel<String> editLabelModel;
	
	/**
	 * save label
	 */
	private IModel<String> saveLabelModel;
	
	/**
	 * select label
	 */
	private IModel<String> selectLabelModel;
	
	/**
	 * second select label
	 */
	private IModel<String> secondSelectLabelModel;
	
	/**
	 * results label
	 */
	private IModel<String> resultsLabelModel;
	
	/**
	 * view label
	 */
	private IModel<String> viewLabelModel;
	
	/**
	 * BeanFieldsPanel back label.
	 */
	private IModel<String> beanFieldsPanelBackLabelModel;
	
	/**
	 * BeanFieldsPanel update label.
	 */
	private IModel<String> updateLabelModel;
	
	/**
	 * BeanFieldsPanel clear label.
	 */
	private IModel<String> beanFieldsPanelClearLabelModel;
	
	/**
	 * BeanFieldsPanel label.
	 */
	private IModel<String> beanFieldsPanelLabelModel;
	
	/**
	 * executeQueryLabel
	 */
	private IModel<String> executeQueryLabelModel;
	
	/**
	 * Validator upon saving a new item.
	 */
	private AjaxEnabledCondition<B> saveValidator;
	
	/**
	 * Validator upon deleting an item.
	 */
	private AjaxEnabledCondition<B> deleteValidator;
	
	/**
	 * Validator upon updating an existing item.
	 */
	private AjaxEnabledCondition<B> updateValidator;
	
	/**
	 * If it is true, the crud picker panel that shows the results
	 * has its rows updated from the persistence layer after any save 
	 * or update.
	 */
	private Boolean refreshAfterDataOp;
	
	/**
	 * Clear criteria label.
	 */
	private IModel<String> clearCriteriaLabelModel;
	
	/**
	 * Back to criteria label.
	 */
	private IModel<String> backToCriteriaLabelModel;
	
	/**
	 * Label displayed on top of query criteria panel.
	 */
	private IModel<String> criteriaPanelLabelModel;
	
	/**
	 * Controls whether the crud picker panel's single bean panel 
	 * will be disabled explicitly by the user when in view mode.
	 */
	private Boolean customSingleBeanPanelDisabling;
	
	/**
	 * Select/Deselect all checkbox label.
	 */
	private IModel<String> checkGroupSelectorLabelModel;
	
	public CallbackAction getPickAction() {
		return this.pickAction;
	}

	public void setPickAction(CallbackAction pickAction) {
		this.pickAction = pickAction;
	}
	
	public CallbackAction getSecondPickAction() {
		return secondPickAction;
	}

	public void setSecondPickAction(CallbackAction secondPickAction) {
		this.secondPickAction = secondPickAction;
	}

	public Flag getSecondPickActionFlag() {
		return secondPickActionFlag;
	}

	public void setSecondPickActionFlag(Flag secondPickActionFlag) {
		this.secondPickActionFlag = secondPickActionFlag;
	}

	public Boolean getRefreshListAfterPickAction() {
		return refreshListAfterPickAction;
	}

	public void setRefreshListAfterPickAction(Boolean refreshListAfterPickAction) {
		this.refreshListAfterPickAction = refreshListAfterPickAction;
	}

	public List<B> getResults() {
		return this.results;
	}

	public void setResults(List<B> results) {
		this.results = results;
	}

	public DataTableCreator<B> getDataTableCreator() {
		return this.dataTableCreator;
	}

	public void setDataTableCreator(DataTableCreator<B> dataTableCreator) {
		this.dataTableCreator = dataTableCreator;
	}

	public Boolean getAutoPickSingleResult() {
		return autoPickSingleResult;
	}

	public void setAutoPickSingleResult(Boolean autoPickSingleResult) {
		this.autoPickSingleResult = autoPickSingleResult;
	}

	public Boolean getResultsHidesCriteria() {
		return resultsHidesCriteria;
	}

	public void setResultsHidesCriteria(Boolean resultsHidesCriteria) {
		this.resultsHidesCriteria = resultsHidesCriteria;
	}

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

	public void setBeanFieldsPanelCreator(PanelCreator<B> panelCreator) {
		this.beanFieldsPanelCreator = panelCreator;
	}

	public IModel<B> getBeanModel() {
		return beanModel;
	}

	public void setBeanModel(IModel<B> beanModel) {
		this.beanModel = beanModel;
	}

	public IModel<C> getCriteriaModel() {
		return criteriaModel;
	}

	public void setCriteriaModel(IModel<C> criteriaModel) {
		this.criteriaModel = criteriaModel;
	}

	public PanelCreator<C> getCriteriaFieldsPanelCreator() {
		return criteriaFieldsPanelCreator;
	}

	public void setCriteriaFieldsPanelCreator(
			PanelCreator<C> criteriaFieldsPanelCreator) {
		this.criteriaFieldsPanelCreator = criteriaFieldsPanelCreator;
	}

	public CallbackAction getQueryAction() {
		return queryAction;
	}

	public void setQueryAction(CallbackAction queryAction) {
		this.queryAction = queryAction;
	}
	
	public Boolean getReadBeforeEdit() {
		return readBeforeEdit;
	}

	public void setReadBeforeEdit(Boolean readBeforeEdit) {
		this.readBeforeEdit = readBeforeEdit;
	}

	public Boolean getAllowMultipleSelections() {
		return allowMultipleSelections;
	}

	public void setAllowMultipleSelections(Boolean allowMultipleSelections) {
		this.allowMultipleSelections = allowMultipleSelections;
	}
	
	public IModel<String> getDeleteLabelModel() {
		return deleteLabelModel;
	}
	
	public void setDeleteLabelModel(IModel<String> deleteLabel) {
		this.deleteLabelModel = deleteLabel;
	}
	
	public IModel<String> getNewLabelModel() {
		return newLabelModel;
	}

	public void setNewLabelModel(IModel<String> newLabel) {
		this.newLabelModel = newLabel;
	}

	public IModel<String> getEditLabelModel() {
		return editLabelModel;
	}

	public void setEditLabelModel(IModel<String> editLabel) {
		this.editLabelModel = editLabel;
	}

	public IModel<String> getSaveLabelModel() {
		return saveLabelModel;
	}

	public void setSaveLabelModel(IModel<String> saveLabel) {
		this.saveLabelModel = saveLabel;
	}

	public IModel<String> getSelectLabelModel() {
		return selectLabelModel;
	}

	public void setSelectLabelModel(IModel<String> selectLabel) {
		this.selectLabelModel = selectLabel;
	}

	public IModel<String> getSecondSelectLabelModel() {
		return secondSelectLabelModel;
	}

	public void setSecondSelectLabelModel(IModel<String> secondSelectLabel) {
		this.secondSelectLabelModel = secondSelectLabel;
	}

	public IModel<String> getResultsLabelModel() {
		return resultsLabelModel;
	}

	public void setResultsLabelModel(IModel<String> resultsLabel) {
		this.resultsLabelModel = resultsLabel;
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

	public Boolean getRefreshAfterDataOp() {
		return refreshAfterDataOp;
	}

	public void setRefreshAfterDataOp(Boolean refreshAfterDataOp) {
		this.refreshAfterDataOp = refreshAfterDataOp;
	}

	public IModel<String> getExecuteQueryLabelModel() {
		return executeQueryLabelModel;
	}

	public void setExecuteQueryLabelModel(IModel<String> label) {
		this.executeQueryLabelModel = label;
	}
	
	public IModel<String> getBeanFieldsPanelBackLabelModel() {
		return beanFieldsPanelBackLabelModel;
	}

	public void setBeanFieldsPanelBackLabelModel(IModel<String> backLabel) {
		this.beanFieldsPanelBackLabelModel = backLabel;
	}

	public IModel<String> getBackToCriteriaLabelModel() {
		return backToCriteriaLabelModel;
	}

	public void setBackToCriteriaLabelModel(IModel<String> backLabel) {
		this.backToCriteriaLabelModel = backLabel;
	}

	public IModel<String> getClearCriteriaLabelModel() {
		return clearCriteriaLabelModel;
	}

	public void setClearCriteriaLabelModel(IModel<String> label) {
		this.clearCriteriaLabelModel = label;
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

	public Flag getPickActionFlag() {
		return pickActionFlag;
	}

	public void setPickActionFlag(Flag pickActionFlag) {
		this.pickActionFlag = pickActionFlag;
	}

	public IModel<String> getCriteriaPanelLabelModel() {
		return criteriaPanelLabelModel;
	}

	public void setCriteriaPanelLabelModel(IModel<String> criteriaPanelLabel) {
		this.criteriaPanelLabelModel = criteriaPanelLabel;
	}

	public IModel<String> getUpdateLabelModel() {
		return updateLabelModel;
	}

	public void setUpdateLabelModel(IModel<String> updateLabel) {
		this.updateLabelModel = updateLabel;
	}

	public IModel<String> getBeanFieldsPanelClearLabelModel() {
		return beanFieldsPanelClearLabelModel;
	}

	public void setBeanFieldsPanelClearLabelModel(IModel<String> clearLabel) {
		this.beanFieldsPanelClearLabelModel = clearLabel;
	}

	public IModel<String> getBeanFieldsPanelLabelModel() {
		return beanFieldsPanelLabelModel;
	}

	public void setBeanFieldsPanelLabelModel(IModel<String> label) {
		this.beanFieldsPanelLabelModel = label;
	}

	public IModel<ArrayList<B>> getSelectionsModel() {
		return selectionsModel;
	}

	public void setSelectionsModel(IModel<ArrayList<B>> selectionsModel) {
		this.selectionsModel = selectionsModel;
	}
	
	public IModel<String> getCheckGroupSelectorLabelModel() {
		return checkGroupSelectorLabelModel;
	}

	public void setCheckGroupSelectorLabelModel(IModel<String> label) {
		this.checkGroupSelectorLabelModel = label;
	}

	public void setDeleteValidator(AjaxEnabledCondition<B> validator) {
		this.deleteValidator = validator;
	}

	public AjaxEnabledCondition<B> getDeleteValidator() {
		return deleteValidator;
	}
	
	public Boolean getRequestConfirmOnDelete() {
		return requestConfirmOnDelete;
	}

	public void setRequestConfirmOnDelete(Boolean requestConfirmOnDelete) {
		this.requestConfirmOnDelete = requestConfirmOnDelete;
	}

	public Boolean getSingleBeanFormContainsFileUpload() {
		return singleBeanFormContainsFileUpload;
	}

	public void setSingleBeanFormContainsFileUpload(Boolean singleBeanFormContainsFileUpload) {
		this.singleBeanFormContainsFileUpload = singleBeanFormContainsFileUpload;
	}
	
}
