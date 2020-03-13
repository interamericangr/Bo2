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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.bo2.utils.functions.SerializableSupplier;
import gr.interamerican.bo2.utils.functions.SerializableUnaryOperator;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;
import gr.interamerican.wicket.callback.Consume;
import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.callback.MultiplePickAction;
import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.callback.ProcessAction;
import gr.interamerican.wicket.callback.SearchAction;
import gr.interamerican.wicket.condition.AjaxCondition;
import gr.interamerican.wicket.creators.DataTableProvider;
import gr.interamerican.wicket.creators.ExportActionCreator;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDefImpl;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;

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
	
	/** Selected result {@link PickAction}. */
	private PickAction<B> pickAction;
	
	/** Selected result {@link MultiplePickAction}. */
	private MultiplePickAction<B> multiplePickAction;

	/**
	 * Authorization flag for pickAction. 
	 */
	private Flag pickActionFlag;
	
	/** Second selected result {@link PickAction}. */
	private PickAction<B> secondPickAction;
	
	/** Second selected result {@link MultiplePickAction}. */
	private MultiplePickAction<B> secondMultiplePickAction;

	/**
	 * Authorization flag for secondPickAction. 
	 */
	private Flag secondPickActionFlag;
	
	/**
	 * RefreshListAfterPickAction.
	 */
	private Boolean refreshListAfterPickAction;
	
	/** query results. */
	private List<B> results;
	
	/**   Result DataTable creator. */
	private DataTableProvider<B, ?> dataTableCreator;
	
	/** 
	 * autoPickSingleResult field. 
	 */
	private Boolean autoPickSingleResult;
	
	/** 
	 * resultsHidesCriteria field. 
	 */
	private Boolean resultsHidesCriteria;
	
	/** Delete {@link Consume}. */
	private Consume<B> deleteAction;
	
	/**
	 * Authorization flag for deleteAction. 
	 */
	private Flag deleteActionFlag;
	
	/** Save {@link ProcessAction}. */
	private ProcessAction<B> saveAction;
	
	/**
	 * Authorization flag for saveAction. 
	 */
	private Flag saveActionFlag;
		
	/** update {@link ProcessAction}. */
	private ProcessAction<B> updateAction;
	
	/**
	 * Authorization flag for updateAction. 
	 */
	private Flag updateActionFlag;
	
	/** query {@link SearchAction}. */
	private SearchAction<C, B> queryAction;
	
	/** Bean fields {@link PanelCreator}. */
	private PanelCreator<B> beanFieldsPanelCreator;
	
	/** Criteria fields {@link PanelCreator}. */
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
	 * Controls whether to hide the buttons of a {@link SingleBeanPanel}.
	 */
	private Boolean hideSingleBeanPanelButtons;
	
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
	
	/** delete label. */
	private IModel<String> deleteLabelModel;
	
	/** new label. */
	private IModel<String> newLabelModel;
	
	/** edit label. */
	private IModel<String> editLabelModel;
	
	/** save label. */
	private IModel<String> saveLabelModel;
	
	/** select label. */
	private IModel<String> selectLabelModel;
	
	/** second select label. */
	private IModel<String> secondSelectLabelModel;
	
	/** results label. */
	private IModel<String> resultsLabelModel;
	
	/** view label. */
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
	
	/** executeQueryLabel. */
	private IModel<String> executeQueryLabelModel;
	
	/**
	 * Validator upon saving a new item.
	 */
	private AjaxCondition<B> saveValidator;
	
	/**
	 * Validator upon deleting an item.
	 */
	private AjaxCondition<B> deleteValidator;
	
	/**
	 * Validator upon updating an existing item.
	 */
	private AjaxCondition<B> updateValidator;
	
	/**
	 * Validator upon selecting an existing item for editing.
	 */
	private AjaxCondition<B> preEditValidator;
	
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

	/**
	 * A {@link SerializableSupplier} that creates new instances of the bean
	 */
	private SerializableSupplier<B> beanCreator;

	/**
	 * A {@link SerializableSupplier} that creates new instances of the criteria bean
	 */
	private SerializableSupplier<C> criteriaBeanCreator;

	/**
	 * An {@link SerializableUnaryOperator} to apply on the selected bean before editing it
	 */
	private SerializableUnaryOperator<B> copyBean;

	/**
	 * An {@link SerializableUnaryOperator} to apply on a selected bean before
	 * viewing/editing it
	 */
	private SerializableUnaryOperator<B> readBean;

	/**
	 * Action that is executed before going back to the criteria form from the
	 * results
	 */
	private LegacyCallbackAction backToCriteriaAction;

	/**
	 * Creator of action that handles the export (mandatory if we want the
	 * feature to be enabled)
	 */
	private ExportActionCreator<B> exportActionCreator;
	
	/**
	 * Export button label resource model (optional)
	 */
	private IModel<String> exportLabel;

	/**
	 * Modifier for the {@link ExportDataSetup} created by this (optional)
	 */
	private SerializableUnaryOperator<ExportDataSetup<B>> exportSetupModifier;

	@Override
	public PickAction<B> getPickAction() {
		return this.pickAction;
	}

	@Override
	public void setPickAction(PickAction<B> pickAction) {
		this.pickAction = pickAction;
	}

	@Override
	public MultiplePickAction<B> getMultiplePickAction() {
		return multiplePickAction;
	}

	@Override
	public void setMultiplePickAction(MultiplePickAction<B> multiplePickAction) {
		this.multiplePickAction = multiplePickAction;
	}

	@Override
	public PickAction<B> getSecondPickAction() {
		return secondPickAction;
	}

	@Override
	public void setSecondPickAction(PickAction<B> secondPickAction) {
		this.secondPickAction = secondPickAction;
	}

	@Override
	public MultiplePickAction<B> getSecondMultiplePickAction() {
		return secondMultiplePickAction;
	}

	@Override
	public void setSecondMultiplePickAction(MultiplePickAction<B> secondMultiplePickAction) {
		this.secondMultiplePickAction = secondMultiplePickAction;
	}

	@Override
	public Flag getSecondPickActionFlag() {
		return secondPickActionFlag;
	}

	@Override
	public void setSecondPickActionFlag(Flag secondPickActionFlag) {
		this.secondPickActionFlag = secondPickActionFlag;
	}

	@Override
	public Boolean getRefreshListAfterPickAction() {
		return refreshListAfterPickAction;
	}

	@Override
	public void setRefreshListAfterPickAction(Boolean refreshListAfterPickAction) {
		this.refreshListAfterPickAction = refreshListAfterPickAction;
	}

	@Override
	public List<B> getResults() {
		return this.results;
	}

	@Override
	public void setResults(List<B> results) {
		this.results = results;
	}

	@Override
	public DataTableProvider<B, ?> getDataTableCreator() {
		return this.dataTableCreator;
	}

	@Override
	public void setDataTableCreator(DataTableProvider<B, ?> dataTableCreator) {
		this.dataTableCreator = dataTableCreator;
	}

	@Deprecated
	@Override
	public Boolean getAutoPickSingleResult() {
		return autoPickSingleResult;
	}

	@Deprecated
	@Override
	public void setAutoPickSingleResult(Boolean autoPickSingleResult) {
		this.autoPickSingleResult = autoPickSingleResult;
	}

	@Override
	public Boolean getResultsHidesCriteria() {
		return resultsHidesCriteria;
	}

	@Override
	public void setResultsHidesCriteria(Boolean resultsHidesCriteria) {
		this.resultsHidesCriteria = resultsHidesCriteria;
	}

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
	public void setBeanFieldsPanelCreator(PanelCreator<B> panelCreator) {
		this.beanFieldsPanelCreator = panelCreator;
	}

	@Override
	public IModel<B> getBeanModel() {
		return beanModel;
	}

	@Override
	public void setBeanModel(IModel<B> beanModel) {
		this.beanModel = beanModel;
	}

	@Override
	public IModel<C> getCriteriaModel() {
		return criteriaModel;
	}

	@Override
	public void setCriteriaModel(IModel<C> criteriaModel) {
		this.criteriaModel = criteriaModel;
	}

	@Override
	public PanelCreator<C> getCriteriaFieldsPanelCreator() {
		return criteriaFieldsPanelCreator;
	}

	@Override
	public void setCriteriaFieldsPanelCreator(
			PanelCreator<C> criteriaFieldsPanelCreator) {
		this.criteriaFieldsPanelCreator = criteriaFieldsPanelCreator;
	}

	@Override
	public SearchAction<C, B> getQueryAction() {
		return queryAction;
	}

	@Override
	public void setQueryAction(SearchAction<C, B> queryAction) {
		this.queryAction = queryAction;
	}

	@Override
	public Boolean getReadBeforeEdit() {
		return readBeforeEdit;
	}

	@Override
	public void setReadBeforeEdit(Boolean readBeforeEdit) {
		this.readBeforeEdit = readBeforeEdit;
	}

	@Deprecated
	@Override
	public Boolean getAllowMultipleSelections() {
		return allowMultipleSelections;
	}

	@Deprecated
	@Override
	public void setAllowMultipleSelections(Boolean allowMultipleSelections) {
		this.allowMultipleSelections = allowMultipleSelections;
	}

	@Override
	public IModel<String> getDeleteLabelModel() {
		return deleteLabelModel;
	}

	@Override
	public void setDeleteLabelModel(IModel<String> deleteLabel) {
		this.deleteLabelModel = deleteLabel;
	}

	@Override
	public IModel<String> getNewLabelModel() {
		return newLabelModel;
	}

	@Override
	public void setNewLabelModel(IModel<String> newLabel) {
		this.newLabelModel = newLabel;
	}

	@Override
	public IModel<String> getEditLabelModel() {
		return editLabelModel;
	}

	@Override
	public void setEditLabelModel(IModel<String> editLabel) {
		this.editLabelModel = editLabel;
	}

	@Override
	public IModel<String> getSaveLabelModel() {
		return saveLabelModel;
	}

	@Override
	public void setSaveLabelModel(IModel<String> saveLabel) {
		this.saveLabelModel = saveLabel;
	}

	@Override
	public IModel<String> getSelectLabelModel() {
		return selectLabelModel;
	}

	@Override
	public void setSelectLabelModel(IModel<String> selectLabel) {
		this.selectLabelModel = selectLabel;
	}

	@Override
	public IModel<String> getSecondSelectLabelModel() {
		return secondSelectLabelModel;
	}

	@Override
	public void setSecondSelectLabelModel(IModel<String> secondSelectLabel) {
		this.secondSelectLabelModel = secondSelectLabel;
	}

	@Override
	public IModel<String> getResultsLabelModel() {
		return resultsLabelModel;
	}

	@Override
	public void setResultsLabelModel(IModel<String> resultsLabel) {
		this.resultsLabelModel = resultsLabel;
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
	public Boolean getRefreshAfterDataOp() {
		return refreshAfterDataOp;
	}

	@Override
	public void setRefreshAfterDataOp(Boolean refreshAfterDataOp) {
		this.refreshAfterDataOp = refreshAfterDataOp;
	}

	@Override
	public IModel<String> getExecuteQueryLabelModel() {
		return executeQueryLabelModel;
	}

	@Override
	public void setExecuteQueryLabelModel(IModel<String> label) {
		this.executeQueryLabelModel = label;
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
	public IModel<String> getBackToCriteriaLabelModel() {
		return backToCriteriaLabelModel;
	}

	@Override
	public void setBackToCriteriaLabelModel(IModel<String> backLabel) {
		this.backToCriteriaLabelModel = backLabel;
	}

	@Override
	public IModel<String> getClearCriteriaLabelModel() {
		return clearCriteriaLabelModel;
	}

	@Override
	public void setClearCriteriaLabelModel(IModel<String> label) {
		this.clearCriteriaLabelModel = label;
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
	public Flag getPickActionFlag() {
		return pickActionFlag;
	}

	@Override
	public void setPickActionFlag(Flag pickActionFlag) {
		this.pickActionFlag = pickActionFlag;
	}

	@Override
	public IModel<String> getCriteriaPanelLabelModel() {
		return criteriaPanelLabelModel;
	}

	@Override
	public void setCriteriaPanelLabelModel(IModel<String> criteriaPanelLabel) {
		this.criteriaPanelLabelModel = criteriaPanelLabel;
	}

	@Override
	public IModel<String> getUpdateLabelModel() {
		return updateLabelModel;
	}

	@Override
	public void setUpdateLabelModel(IModel<String> updateLabel) {
		this.updateLabelModel = updateLabel;
	}

	@Override
	public IModel<String> getBeanFieldsPanelClearLabelModel() {
		return beanFieldsPanelClearLabelModel;
	}

	@Override
	public void setBeanFieldsPanelClearLabelModel(IModel<String> clearLabel) {
		this.beanFieldsPanelClearLabelModel = clearLabel;
	}

	@Override
	public IModel<String> getBeanFieldsPanelLabelModel() {
		return beanFieldsPanelLabelModel;
	}

	@Override
	public void setBeanFieldsPanelLabelModel(IModel<String> label) {
		this.beanFieldsPanelLabelModel = label;
	}

	@Override
	public IModel<ArrayList<B>> getSelectionsModel() {
		return selectionsModel;
	}

	@Override
	public void setSelectionsModel(IModel<ArrayList<B>> selectionsModel) {
		this.selectionsModel = selectionsModel;
	}

	@Override
	public IModel<String> getCheckGroupSelectorLabelModel() {
		return checkGroupSelectorLabelModel;
	}

	@Override
	public void setCheckGroupSelectorLabelModel(IModel<String> label) {
		this.checkGroupSelectorLabelModel = label;
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
	public Boolean getSingleBeanFormContainsFileUpload() {
		return singleBeanFormContainsFileUpload;
	}

	@Override
	public void setSingleBeanFormContainsFileUpload(Boolean singleBeanFormContainsFileUpload) {
		this.singleBeanFormContainsFileUpload = singleBeanFormContainsFileUpload;
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
	public void setBeanCreator(SerializableSupplier<B> beanCreator) {
		this.beanCreator = beanCreator;
	}

	@Override
	public SerializableSupplier<B> getBeanCreator() {
		return beanCreator;
	}

	@Override
	public void setCriteriaBeanCreator(SerializableSupplier<C> criteriaBeanCreator) {
		this.criteriaBeanCreator = criteriaBeanCreator;
	}

	@Override
	public SerializableSupplier<C> getCriteriaBeanCreator() {
		return criteriaBeanCreator;
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

	@Override
	public void setBackToCriteriaAction(LegacyCallbackAction backToCriteriaAction) {
		this.backToCriteriaAction = backToCriteriaAction;
	}

	@Override
	public LegacyCallbackAction getBackToCriteriaAction() {
		return backToCriteriaAction;
	}

	@Override
	public IModel<String> getExportLabel() {
		return exportLabel;
	}

	@Override
	public void setExportLabel(IModel<String> exportLabel) {
		this.exportLabel = exportLabel;
	}

	@Override
	public ExportActionCreator<B> getExportActionCreator() {
		return exportActionCreator;
	}

	@Override
	public void setExportActionCreator(ExportActionCreator<B> exportActionCreator) {
		this.exportActionCreator = exportActionCreator;
	}

	@Override
	public SerializableUnaryOperator<ExportDataSetup<B>> getExportSetupModifier() {
		return exportSetupModifier;
	}

	@Override
	public void setExportSetupModifier(SerializableUnaryOperator<ExportDataSetup<B>> exportSetupModifier) {
		this.exportSetupModifier = exportSetupModifier;
	}
}