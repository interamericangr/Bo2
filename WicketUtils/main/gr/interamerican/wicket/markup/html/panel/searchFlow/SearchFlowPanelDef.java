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

import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.bo2.utils.functions.SerializableSupplier;
import gr.interamerican.bo2.utils.functions.SerializableUnaryOperator;
import gr.interamerican.wicket.callback.Consume;
import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.callback.MultiplePickAction;
import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.callback.ProcessAction;
import gr.interamerican.wicket.callback.SearchAction;
import gr.interamerican.wicket.condition.AjaxCondition;
import gr.interamerican.wicket.creators.DataTableProvider;
import gr.interamerican.wicket.creators.PanelCreator;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDef;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanel;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDef;
import gr.interamerican.wicket.markup.html.panel.listTable.ExportButtonOptions;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanel;
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanel;
import gr.interamerican.wicket.markup.html.panel.service.BeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

/**
 * {@link ServicePanelDef} of {@link SearchFlowPanel}.
 * 
 * The panel defined by this bean, contains two main panels.
 * The first panel is a CriteriaPanel, where the user can
 * enter query criteria, and the second is where the query 
 * results are presented.
 * 
 * There are three possibilities for the second panel:<br>
 * <ul>
 * <li> {@link ListTablePanel}, when we just want to show a
 *      list of the results. </li>
 * <li> {@link PickerPanel}, when we want to allow the user
 *      to pick a result. </li>
 * <li> {@link CrudPickerPanel}, when we want to allow the user
 *      to pick a result, as well as perform CRUD operations on
 *      them. </li>
 * </ul>
 * The definition consists of a {@link SingleBeanPanelDef} and the
 * properties of a {@link CrudPickerPanelDef}. Depending on which
 * of the {@link CrudPickerPanelDef} properties are set, the results
 * panel actual implementation is chosen. <br>
 * {@link SearchFlowPanel} extends {@link BeanPanelDef}. For the 
 * SearchFlowPanel to have CRUD functionality, the beanModel property
 * of BeanPanelDef must be set on the SearchFlowPanelDef. Any preferred  
 * implementation of {@link IModel} can be used. Note that the model 
 * object cannot be null. 
 * 
 * @param <C> 
 *        Type of criteria.
 * @param <B> 
 *        Type of bean.
 */
public interface SearchFlowPanelDef
<C extends Serializable, B extends Serializable>
extends ServicePanelWithBackDef, BeanPanelDef<B>, ExportButtonOptions<B> {
	
	/*
	 * Component factories
	 */
	
	/**
	 * Gets the panel creator.
	 *
	 * @return Returns the panel creator.
	 * @see #setBeanFieldsPanelCreator(PanelCreator)
	 */
	PanelCreator<B> getBeanFieldsPanelCreator();
	
	/**
	 * [OPTIONAL]
	 * Sets the panel creator. The panel creator creates a panel
	 * that contains the fields of the bean form.
	 * If this is not set, the results panel cannot be a {@link CrudPickerPanel}.
	 * 
	 * @param panelCreator 
	 *        The panelCreator to set.
	 */
	void setBeanFieldsPanelCreator(PanelCreator<B> panelCreator);
	
	/**
	 * Gets the panel creator for the criteria panel.
	 *
	 * @return Returns the panel creator.
	 * @see #setCriteriaFieldsPanelCreator(PanelCreator)
	 */
	PanelCreator<C> getCriteriaFieldsPanelCreator();
	
	/**
	 * [MANDATORY]
	 * Sets the panel creator for the criteria panel.
	 * 
	 * @param panelCreator 
	 *        The panelCreator to set.
	 */
	void setCriteriaFieldsPanelCreator(PanelCreator<C> panelCreator);
	
	/**
	 * Gets the {@link DataTableProvider} that creates the DataTable shown
	 * in the results panel.
	 *
	 * @return Returns the {@link DataTableProvider}.
	 * @see #setDataTableCreator(DataTableProvider)
	 */
	DataTableProvider<B, ?> getDataTableCreator();

	/**
	 * [MANDATORY]
	 * Sets the {@link DataTableProvider} that creates the DataTable shown
	 * in the results panel. The results panel can be either a {@link ListTablePanel}
	 * or a {@link PickerPanel} depending whether the <code>pickAction</code> is
	 * null or not. In the latter case, the creator should contain a column with
	 * {@link Radio} buttons.
	 *
	 * @param dataTableCreator 
	 *        the dataTableCreator to set
	 */
	void setDataTableCreator(DataTableProvider<B, ?> dataTableCreator);
	
	/**
	 * *******************************************************************.
	 *
	 * @return the criteria model
	 */
	
	/*
	 * Models and place holders
	 */
	
	/**
	 * Gets the model of the criteria panel form. 
	 * 
	 * @see #setCriteriaModel(IModel)
	 * 
	 * @return the model of the panel form.
	 */
	IModel<C> getCriteriaModel();
	
	/**
	 * Sets the model of the criteria panel form. <br>
	 * 
	 * This is intended to allow users to specify whichever {@link IModel}
	 * implementation they prefer. The model object of this model is the
	 * criteria object. It cannot be null.
	 * 
	 * @param criteriaModel
	 *        the model of the criteria panel form.
	 */
	void setCriteriaModel(IModel<C> criteriaModel);
	
	/**
	 * [OPTIONAL] (MANDATORY if allowMultipleSelections is true).
	 * Gets the selections model. If the model collection is
	 * null, a new {@link ArrayList} is assigned as the object
	 * of this model.
	 * 
	 * @return Returns the selections model.
	 */
	IModel<ArrayList<B>> getSelectionsModel();
	
	/**
	 * Sets the selections model.
	 *
	 * @param selectionsModel the new selections model
	 * @see #getSelectionsModel()
	 */
	void setSelectionsModel(IModel<ArrayList<B>> selectionsModel);
	
	/**
	 * [OPTIONAL]
	 * Results of the query execution.
	 * 
	 * @return Returns the results of the query execution.
	 */
	List<B> getResults();
	
	/**
	 * [OPTIONAL]
	 * Sets the results of the query execution.
	 * 
	 * @param results
	 *        The results to set.
	 */
	void setResults(List<B> results);
	
	/**
	 * *******************************************************************.
	 *
	 * @return the pick action
	 */
	
	/*
	 * Actions, flags, conditions
	 */
	
	/**
	 * Gets the pickAction. This action is executed when the user selects an item.
	 * 
	 * @see #setPickAction(PickAction)
	 *
	 * @return Returns the queryAction
	 */
	PickAction<B> getPickAction();

	/**
	 * Sets the pickAction. <br>
	 * 
	 * This action is necessary only if picker functionality is wanted. 
	 * This action is executed when the user selects an item.
	 * If this action is null, then there won't be a select button and the results
	 * panel will be a {@link ListTablePanel} and not a {@link PickerPanel}.
	 *
	 * @param pickAction the pickAction to set
	 */
	void setPickAction(PickAction<B> pickAction);
	
	/**
	 * Gets the MultiplePickAction. This action is executed when the user selects many items.
	 * 
	 * @see #setMultiplePickAction(MultiplePickAction)
	 *
	 * @return Returns the MultiplePickAction
	 */
	MultiplePickAction<B> getMultiplePickAction();

	/**
	 * Sets the pickAction. <br>
	 * 
	 * This action is necessary only if picker functionality is wanted. This
	 * action is executed when the user selects many items. If this action is
	 * null, then there won't be a select button and the results panel will be a
	 * {@link ListTablePanel} and not a {@link PickerPanel}.<br>
	 * Do note that only this or {@link #setPickAction(PickAction)} can be set,
	 * and not both at the same time.
	 *
	 * @param multiplePickAction
	 *            the multiplePickAction to set
	 */
	void setMultiplePickAction(MultiplePickAction<B> multiplePickAction);

	/**
	 * Gets the pickActionFlag.
	 * 
	 * @return Returns the pickActionFlag.
	 */
	Flag getPickActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the pickActionFlag.
	 *
	 * @param pickActionFlag the new pick action flag
	 */
	void setPickActionFlag(Flag pickActionFlag);
	
	/**
	 * Gets the secondPickAction.
	 *
	 * @return Returns the secondPickAction
	 * @see #setSecondPickAction(PickAction)
	 */
	PickAction<B> getSecondPickAction();

	/**
	 * Sets the secondPickAction. <br>
	 * 
	 * This action is necessary only if two kinds of picker functionality are wanted. 
	 *
	 * @param secondPickAction the secondPickAction to set
	 */
	void setSecondPickAction(PickAction<B> secondPickAction);
	
	/**
	 * Gets the SecondMultiplePickAction. This action is executed when the user selects many items.
	 * 
	 * @see #setSecondMultiplePickAction(MultiplePickAction)
	 *
	 * @return Returns the SecondMultiplePickAction
	 */
	MultiplePickAction<B> getSecondMultiplePickAction();

	/**
	 * Sets the Second MultiplePickAction. <br>
	 * 
	 * This action is necessary only if picker functionality is wanted. This
	 * action is executed when the user selects many items. If this action is
	 * null, then there won't be a select button and the results panel will be a
	 * {@link ListTablePanel} and not a {@link PickerPanel}.<br>
	 * Do note that only this or {@link #setPickAction(PickAction)} can be set,
	 * and not both at the same time.
	 *
	 * @param secondMultiplePickAction
	 *            the secondMultiplePickAction to set
	 */
	void setSecondMultiplePickAction(MultiplePickAction<B> secondMultiplePickAction);

	/**
	 * Gets the secondPickActionFlag.
	 * 
	 * @return Returns the secondPickActionFlag.
	 */
	Flag getSecondPickActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the secondPickActionFlag.
	 *
	 * @param secondPickActionFlag the new second pick action flag
	 */
	void setSecondPickActionFlag(Flag secondPickActionFlag);
	
	/**
	 * Gets the refreshListAfterPickAction.
	 *
	 * @return refreshListAfterPickAction
	 * @see #setRefreshListAfterPickAction(Boolean)
	 */
	Boolean getRefreshListAfterPickAction();
	
	/**
	 * [OPTIONAL]
	 * Will repaint the data table after any select action is executed.
	 *
	 * @param refreshListAfterPickAction the new refresh list after pick action
	 */
	void setRefreshListAfterPickAction(Boolean refreshListAfterPickAction);
	
	/**
	 * Gets the deleteAction.
	 *
	 * @return Returns the deleteAction.
	 * @see #setDeleteAction(Consume)
	 */
	Consume<B> getDeleteAction();
	
	/**
	 * [OPTIONAL] Assigns a new value to the deleteAction {@link Consume}. If
	 * this is not set, the results panel cannot be a {@link CrudPickerPanel}.
	 * 
	 * This action should delete the selected bean.
	 *
	 * @param deleteAction
	 *            the new delete action
	 * @see CrudPickerPanelDef#setDeleteAction(Consume)
	 */
	void setDeleteAction(Consume<B> deleteAction);
	
	/**
	 * Gets the deleteActionFlag.
	 * 
	 * @return Returns the deleteActionFlag.
	 */
	Flag getDeleteActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the deleteActionFlag.
	 *
	 * @param deleteActionFlag the new delete action flag
	 */
	void setDeleteActionFlag(Flag deleteActionFlag);
	
	/**
	 * Gets the updateAction.
	 *
	 * @return Returns the updateAction.
	 * @see #setUpdateAction(ProcessAction)
	 */
	ProcessAction<B> getUpdateAction();

	/**
	 * Assigns a new value to the updateAction {@link ProcessAction}. <br>
	 * 
	 * This property is necessary only in order to support CRUD operations. If
	 * this is not set, the results panel cannot be a {@link CrudPickerPanel}.
	 * This action should update an existing bean (the one contained in this
	 * definition's model).
	 *
	 * @param updateAction
	 *            the updateAction to set
	 * @see CrudPickerPanelDef#setUpdateAction(ProcessAction)
	 */
	void setUpdateAction(ProcessAction<B> updateAction);
	
	/**
	 * Gets the updateActionFlag.
	 * 
	 * @return Returns the updateActionFlag.
	 */
	Flag getUpdateActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the updateActionFlag.
	 *
	 * @param updateActionFlag the new update action flag
	 */
	void setUpdateActionFlag(Flag updateActionFlag);
	
	/**
	 * Gets the saveAction.
	 *
	 * @return Returns the saveAction
	 * @see #setSaveAction(ProcessAction)
	 */
	ProcessAction<B> getSaveAction();

	/**
	 * [OPTIONAL]
	 * Assigns a new value to the saveAction {@link ProcessAction}.
	 * If this is not set, the results panel cannot be a {@link CrudPickerPanel}.
	 * 
	 * This action should store a new bean.
	 *
	 * @param saveAction the saveAction to set
	 * @see CrudPickerPanelDef#setSaveAction(ProcessAction)
	 */
	void setSaveAction(ProcessAction<B> saveAction);
	
	/**
	 * Gets the saveActionFlag.
	 * 
	 * @return Returns the saveActionFlag.
	 */
	Flag getSaveActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the saveActionFlag.
	 *
	 * @param saveActionFlag the new save action flag
	 */
	void setSaveActionFlag(Flag saveActionFlag);
	
	/**
	 * Gets the queryAction. <br>
	 * 
	 * This is the action that queries the data and fills the results
	 * list. If an exception is caught while performing the query it
	 * is possible to set the results list {@link #setResults(List)}
	 * to null, so that the results table is not rendered, and the user
	 * may try to execute the query again. However, this is not mandatory,
	 * as the query wrapper action will clear the results anyway, so
	 * in the end an empty results list will be shown along with the
	 * exception message (assuming you register this in the caller
	 * component of the queryAction, which is automatically set to be
	 * the SearchFlowPanel created by the definition.
	 *
	 * @return Returns the queryAction
	 * @see #setQueryAction(SearchAction)
	 */
	SearchAction<C, B> getQueryAction();

	/**
	 * Sets the queryAction.
	 *
	 * @param queryAction the queryAction to set
	 */
	void setQueryAction(SearchAction<C, B> queryAction);
	
	/**
	 * [OPTIONAL]
	 * Sets the validator that is executed when storing a new B.
	 *
	 * @param validator the new save validator
	 */
	void setSaveValidator(AjaxCondition<B> validator);
	
	/**
	 * Gets the validator that is executed when storing a new B.
	 * 
	 * @return validator.
	 */
	AjaxCondition<B> getSaveValidator();
	
	/**
	 * [OPTIONAL]
	 * Sets the validator that is executed when updating an existing B.
	 *
	 * @param validator the new update validator
	 */
	void setUpdateValidator(AjaxCondition<B> validator);
	
	/**
	 * Gets the validator that is executed when updating an existing B.
	 * 
	 * @return validator.
	 */
	AjaxCondition<B> getUpdateValidator();
	
	/**
	 * [OPTIONAL]
	 * Sets the validator that is executed when deleting an existing B.
	 *
	 * @param validator the new delete validator
	 */
	void setDeleteValidator(AjaxCondition<B> validator);
	
	/**
	 * Gets the validator that is executed when deleting an existing B.
	 * 
	 * @return validator.
	 */
	AjaxCondition<B> getDeleteValidator();
	
	/**
	 * [OPTIONAL]
	 * Sets the validator that is executed when selecting an existing B for update.
	 * This is applied to the results panel if it is a CrudPickerPanel
	 *
	 * @param validator the new pre edit validator
	 */
	void setPreEditValidator(AjaxCondition<B> validator);
	
	/**
	 * Gets the validator that is executed when selecting an existing B for update.
	 * This is applied to the results panel if it is a CrudPickerPanel
	 * 
	 * @return validator.
	 */
	AjaxCondition<B> getPreEditValidator();
	
	/**
	 * *******************************************************************.
	 *
	 * @return the single bean form contains file upload
	 */
	
	/*
	 * Options
	 */
	
	/**
	 * Gets whether the single bean panel form of the CrudPickerPanel results 
	 * panel includes a file upload item. 
	 * 
	 * @return whether the single bean panel form includes a file upload item.
	 */
	Boolean getSingleBeanFormContainsFileUpload();
	
	/**
	 * [OPTIONAL]
	 * Sets whether the single bean panel form of the CrudPickerPanel results 
	 * panel includes a file upload item.
	 * If this is set to true, the single bean panel form is set as 
	 * <code>setMultipart(true)</code>. Defaults to false.
	 *
	 * @param singleBeanFormContainsFileUpload the new single bean form contains file upload
	 */
	void setSingleBeanFormContainsFileUpload(Boolean singleBeanFormContainsFileUpload);
	
	/**
	 * Gets whether pressing the delete will open a pop-up confirmation dialog.
	 * 
	 * @return whether pressing the delete will open a pop-up confirmation dialog.
	 */
	Boolean getRequestConfirmOnDelete();
	
	/**
	 * [OPTIONAL]
	 * Sets whether pressing the delete will open a pop-up confirmation dialog.
	 * Defaults to false.
	 *
	 * @param requestConfirmOnDelete the new request confirm on delete
	 */
	void setRequestConfirmOnDelete(Boolean requestConfirmOnDelete);
	
	/**
	 * [OPTIONAL]
	 * Reads the selected bean from the persistence layer before editing.
	 * This is useful when a CrudPickerPanel has to work with a
	 * PersistentObject that is not read with a HibernateWorker, but
	 * instead has only some of its properties set that were retrieved
	 * by a JdbcQuery. Note that all of the key properties of the
	 * PersistentObhect HAVE to be read. Defaults to false.
	 * 
	 * @param readBeforeEdit
	 *        The readBeforeEdit to set.
	 */
	void setReadBeforeEdit(Boolean readBeforeEdit);
	
	/**
	 * Gets the readBeforeEdit.
	 * 
	 * @return Returns the readBeforeEdit.
	 */
	Boolean getReadBeforeEdit();
	
	/**
	 * Gets the viewEnabled.
	 * 
	 * @return Returns the viewEnabled.
	 */
	Boolean getViewEnabled();
	
	/**
	 * [OPTIONAL]
	 * Displays a view button that allows the user to view the
	 * selected item. Basically, the SingleBeanPanel is opened
	 * with the item as model object and all fields are disabled.
	 * Defaults to false.
	 * 
	 * @param viewEnabled
	 *        The viewEnabled to set.
	 */
	void setViewEnabled(Boolean viewEnabled);
	
	/**
	 * Indicates if the user is allowed to pick more than one result
	 * at the same time.
	 * 
	 * @return Returns true if the user is allowed to pick more than one result
	 *         at the same time. 
	 * @see #setMultiplePickAction(MultiplePickAction)
	 * @deprecated This is redundant information as there are 2 different
	 *             actions for single or multiple selections
	 */
	@Deprecated
	Boolean getAllowMultipleSelections();
	
	/**
	 * [OPTIONAL] - defaults to false<br>
	 * Sets the value of the <code>allowMultipleSelections</code> property.
	 * 
	 * @param allowMultipleSelections
	 *            The new allowMultipleSelections to set.
	 * 
	 * @see #getAllowMultipleSelections()
	 * @see #setPickAction(PickAction)
	 * @see #setMultiplePickAction(MultiplePickAction)
	 * @deprecated This is redundant information as there are 2 different
	 *             actions for single or multiple selections
	 */
	@Deprecated
	void setAllowMultipleSelections(Boolean allowMultipleSelections);
	
	/**
	 * Indicates if the criteria panel is hidden by the results panel.
	 * 
	 * If this property is true, then the execution of the query action
	 * will hide the criteria panel and show the results panel. In this 
	 * case, the result panel's back button will hide the results panel
	 * and show the criteria panel. <br>
	 * If this property is false, then both panels will be visible, the
	 * criteria panel and the results panel.
	 * 
	 * @return Returns true if the criteria panel must be hidden when
	 *         the results panel is presented.
	 */
	Boolean getResultsHidesCriteria();
	
	/**
	 * [OPTIONAL] - defaults to true<br>
	 * Sets the value of the <code>resultsHidesCriteria</code> property.
	 * 
	 * @param resultsHidesCriteria
	 *        The new resultsHidesCriteria to set.
	 *        
	 * @see #getResultsHidesCriteria()
	 */
	void setResultsHidesCriteria(Boolean resultsHidesCriteria);
	
	/**
	 * [OPTIONAL] - defaults to false<br>
	 * Indicates if the pick action will be executed automatically if the
	 * results have only one item.
	 * 
	 * This property makes sense only if the <code>pickAction</code> property
	 * is not null, so that the SearchFlowPanel defined by this {@link SearchFlowPanelDef}
	 * has also picker functionality. In this case, if this property is
	 * true, and the results of the query action contain only one item,
	 * then this item will be picked automatically, and the pickAction
	 * will be executed automatically.
	 * If this property is false, then the user will always have to pick
	 * manually an item even if the results only contain one iem.
	 * 
	 * @return Returns true if the pick action will be executed automatically
	 *         in case the results contain only one element.
	 * @deprecated Dropping it since it was not implemented and never requested to be done so
	 */
	@Deprecated
	Boolean getAutoPickSingleResult();
	
	/**
	 * Sets the value of the <code>autoPickSingleResult</code> property.
	 * 
	 * @param autoPickSingleResult
	 *        The new autoPickSingleResult to set.
	 *        
	 * @see #getAutoPickSingleResult()
	 * @deprecated Dropping it since it was not implemented and never requested to be done so
	 */
	@Deprecated
	void setAutoPickSingleResult(Boolean autoPickSingleResult);
	
	/**
	 * Gets refreshAfterDataOp.
	 * 
	 * @return refreshAfterDataOp.
	 */
	Boolean getRefreshAfterDataOp();
	
	/**
	 * Sets refreshAfterDataOp.
	 *
	 * @param refreshAfterDataOp the new refresh after data op
	 */
	void setRefreshAfterDataOp(Boolean refreshAfterDataOp);
	
	/**
	 * [OPTIONAL]
	 * Gets whether the user will handle manually the disabling of the SingleBeanPanel
	 * when in view mode. This is useful when some components of the panel need to remain 
	 * enabled, for example tabs or download links.
	 * The default is false.
	 *
	 * @return Returns customSingleBeanPanelDisabling.
	 * @see ModeAwareBeanPanelDef
	 */
	Boolean getCustomSingleBeanPanelDisabling();
	
	/**
	 * Sets whether the user will handle manually the disabling of the SingleBeanPanel
	 * of the CrudPickerPanel when in view mode.
	 *
	 * @param customSingleBeanPanelDisabling the new custom single bean panel disabling
	 * @see #getCustomSingleBeanPanelDisabling()
	 */
	void setCustomSingleBeanPanelDisabling(Boolean customSingleBeanPanelDisabling);
	
	/*
	 * LABELS
	 */
	
	/**
	 * Gets the deleteLabel.
	 *
	 * @return Returns the deleteLabel
	 */
	IModel<String> getDeleteLabelModel();

	/**
	 * Assigns a new value to the deleteLabel.
	 *
	 * @param deleteLabel the deleteLabel to set
	 */
	void setDeleteLabelModel(IModel<String> deleteLabel);

	/**
	 * Gets the newLabel.
	 *
	 * @return Returns the newLabel
	 */
	IModel<String> getNewLabelModel();

	/**
	 * Assigns a new value to the newLabel.
	 *
	 * @param newLabel the newLabel to set
	 */
	void setNewLabelModel(IModel<String> newLabel);

	/**
	 * Gets the editLabel.
	 *
	 * @return Returns the editLabel
	 */
	IModel<String> getEditLabelModel();

	/**
	 * Assigns a new value to the editLabel.
	 *
	 * @param editLabel the editLabel to set
	 */
	void setEditLabelModel(IModel<String> editLabel);

	/**
	 * Gets the selectLabel.
	 *
	 * @return Returns the selectLabel
	 */
	IModel<String> getSelectLabelModel();

	/**
	 * Assigns a new value to the selectLabel.
	 *
	 * @param selectLabel the selectLabel to set
	 */
	void setSelectLabelModel(IModel<String> selectLabel);

	/**
	 * Gets the secondSelectLabel.
	 *
	 * @return Returns the secondSelectLabel
	 */
	IModel<String> getSecondSelectLabelModel();

	/**
	 * Assigns a new value to the secondSelectLabel.
	 *
	 * @param secondSelectLabel the secondSelectLabel to set
	 */
	void setSecondSelectLabelModel(IModel<String> secondSelectLabel);
	
	/**
	 * Gets the resultsLabel.
	 *
	 * @return Returns the resultsLabel
	 */
	IModel<String> getResultsLabelModel();

	/**
	 * Assigns a new value to the resultsLabel.
	 *
	 * @param resultsLabel the resultsLabel to set
	 */
	void setResultsLabelModel(IModel<String> resultsLabel);

	/**
	 * Gets the backLabel.
	 *
	 * @return Returns the backLabel
	 */
	IModel<String> getBackToCriteriaLabelModel();

	/**
	 * Assigns a new value to the backLabel.
	 *
	 * @param backLabel the backLabel to set
	 */
	void setBackToCriteriaLabelModel(IModel<String> backLabel);
	
	/**
	 * Gets the view button label.
	 * 
	 * @return view button label.
	 */
	IModel<String> getViewLabelModel();
	
	/**
	 * [OPTIONAL] Sets the view button label.
	 *
	 * @param label the new view label model
	 */
	void setViewLabelModel(IModel<String> label);
	
	/**
	 * Gets the saveLabel.
	 *
	 * @return Returns the saveLabel
	 */
	IModel<String> getSaveLabelModel();

	/**
	 * [OPTIONAL]
	 * Sets the value of the executeLabel of the SingleBeanPanel
	 * when storing a new B.
	 *
	 * @param saveLabel the saveLabel to set
	 */
	void setSaveLabelModel(IModel<String> saveLabel);
	
	/**
	 * Gets the updateLabel.
	 *
	 * @return Returns the updateLabel
	 */
	IModel<String> getUpdateLabelModel();
	
	/**
	 * [OPTIONAL]
	 * Sets the value of the executeLabel of the SingleBeanPanel
	 * when updating a B.
	 *
	 * @param updateLabel the updateLabel to set
	 */
	void setUpdateLabelModel(IModel<String> updateLabel);
	
	/**
	 * Gets the execute query button label.
	 * 
	 * @return execute query button label.
	 */
	IModel<String> getExecuteQueryLabelModel();
	
	/**
	 * [OPTIONAL] Sets the execute query button label.
	 *
	 * @param label the new execute query label model
	 */
	void setExecuteQueryLabelModel(IModel<String> label);
	
	/**
	 * Gets the clear criteria button label.
	 * 
	 * @return clear criteria button label.
	 */
	IModel<String> getClearCriteriaLabelModel();
	
	/**
	 * [OPTIONAL] Sets the clear criteria button label.
	 *
	 * @param label the new clear criteria label model
	 */
	void setClearCriteriaLabelModel(IModel<String> label);
	
	/**
	 * Gets the back label of the SingleBeanPanel of the CrudPickerPanel.
	 * 
	 * @return SingleBeanPanel back label.
	 */
	IModel<String> getBeanFieldsPanelBackLabelModel();
	
	/**
	 * [OPTIONAL]
	 * Sets the back label of the SingleBeanPanel of the CrudPickerPanel.
	 *
	 * @param backLabel the new bean fields panel back label model
	 */
	void setBeanFieldsPanelBackLabelModel(IModel<String> backLabel);
	
	/**
	 * Gets the label displayed on top of the query criteria panel.
	 * 
	 * @return criteriaPanelLabel.
	 */
	IModel<String> getCriteriaPanelLabelModel();
	
	/**
	 * [OPTIONAL]
	 * Sets the the label displayed on top of the query criteria panel.
	 *
	 * @param criteriaPanelLabel the new criteria panel label model
	 */
	void setCriteriaPanelLabelModel(IModel<String> criteriaPanelLabel);
	
	/**
	 * Gets the clear label of the SingleBeanPanel.
	 * 
	 * @return SingleBeanPanel clear label.
	 */
	IModel<String> getBeanFieldsPanelClearLabelModel();
	
	/**
	 * [OPTIONAL]
	 * Sets the clear label of the SingleBeanPanel.
	 *
	 * @param clearLabel the new bean fields panel clear label model
	 */
	void setBeanFieldsPanelClearLabelModel(IModel<String> clearLabel);
	
	/**
	 * Gets the label of the SingleBeanPanel.
	 * 
	 * @return SingleBeanPanel label.
	 */
	IModel<String> getBeanFieldsPanelLabelModel();
	
	/**
	 * [OPTIONAL]
	 * Sets the label (fieldset legend) of the SingleBeanPanel.
	 *
	 * @param label the new bean fields panel label model
	 */
	void setBeanFieldsPanelLabelModel(IModel<String> label);
	
	/**
	 * Gets the checkGroupSelectorLabel.
	 *
	 * @return checkGroupSelectorLabel
	 */
	IModel<String> getCheckGroupSelectorLabelModel();
	
	/**
	 * [OPTIONAL]
	 * Sets a custom label for the select / deselect all checkbox.
	 *
	 * @param label the new check group selector label model
	 */
	void setCheckGroupSelectorLabelModel(IModel<String> label);

	/**
	 * [OPTIONAL]
	 * Gets a {@link SerializableSupplier} that creates new instances of the bean.<br>
	 * This is called when a new bean is created or when the clear button is
	 * pressed during edit.<br>
	 * The default implementation if not filled explicitly will make an
	 * assumption that the runtime class of B will have a default constructor.
	 * If this is not the case, the default implementation will throw a
	 * RuntimeException.
	 * 
	 * @return A {@link SerializableSupplier} that creates new instances of the bean.
	 */
	SerializableSupplier<B> getBeanCreator();

	/**
	 * [OPTIONAL]
	 * Sets a {@link SerializableSupplier} that creates new instances of the bean.
	 * This is called when a new bean is created or when the clear button is
	 * pressed during edit.<br>
	 * The default implementation if not filled explicitly will make an
	 * assumption that the runtime class of B will have a default constructor.
	 * If this is not the case, the default implementation will throw a
	 * RuntimeException.
	 * 
	 * @param beanCreator
	 *            A {@link SerializableSupplier} that creates new instances of the bean.
	 */
	void setBeanCreator(SerializableSupplier<B> beanCreator);

	/**
	 * [OPTIONAL]
	 * Gets a {@link SerializableSupplier} that creates new instances of the criteria bean.
	 * This is called when the clear button is
	 * pressed while filling the search criteria.<br>
	 * The default implementation if not filled explicitly will make an
	 * assumption that the runtime class of B will have a default constructor.
	 * If this is not the case, the default implementation will throw a
	 * RuntimeException.
	 * 
	 * @return A {@link SerializableSupplier} that creates new instances of the bean.
	 */
	SerializableSupplier<C> getCriteriaBeanCreator();

	/**
	 * [OPTIONAL]
	 * Sets a {@link SerializableSupplier} that creates new instances of the criteria bean.
	 * This is called when the clear button is
	 * pressed while filling the search criteria.<br>
	 * The default implementation if not filled explicitly will make an
	 * assumption that the runtime class of B will have a default constructor.
	 * If this is not the case, the default implementation will throw a
	 * RuntimeException.
	 * 
	 * @param criteriaBeanCreator
	 *            A {@link SerializableSupplier} that creates new instances of the criteria bean.
	 */
	void setCriteriaBeanCreator(SerializableSupplier<C> criteriaBeanCreator);

	/**
	 * [OPTIONAL]
	 * Gets an {@link SerializableUnaryOperator} to apply on the selected bean
	 * before editing it.<br>
	 * By default no modification will be done.<br>
	 * This is useful if when updating a B instance there is a chance that the
	 * update will be aborted after submitting the update form. This could
	 * happen, for example, if a validation message is shown and the user
	 * decides to go back instead of correcting the error and submitting the
	 * update form again.<br>
	 * If readBeforeEdit is true, this is not used, as instead of deep copying,
	 * a new instance is created from the persistence layer, which has the same
	 * effect.
	 * 
	 * @return {@link SerializableUnaryOperator} to apply on the selected bean before
	 *         editing it.
	 */
	SerializableUnaryOperator<B> getCopyBean();

	/**
	 * [OPTIONAL]
	 * Sets an {@link SerializableUnaryOperator} to apply on the selected bean
	 * before editing it.<br>
	 * By default no modification will be done.<br>
	 * This is useful if when updating a B instance there is a chance that the
	 * update will be aborted after submitting the update form. This could
	 * happen, for example, if a validation message is shown and the user
	 * decides to go back instead of correcting the error and submitting the
	 * update form again.<br>
	 * If readBeforeEdit is true, this is not used, as instead of deep copying,
	 * a new instance is created from the persistence layer, which has the same
	 * effect.
	 * 
	 * @param copyBean
	 *            {@link SerializableUnaryOperator} to apply on the selected bean before
	 *            editing it.
	 */
	void setCopyBean(SerializableUnaryOperator<B> copyBean);

	/**
	 * [OPTIONAL]
	 * Gets an {@link SerializableUnaryOperator} to apply on a selected bean
	 * before viewing/editing it.<br>
	 * This is only used when {@link #setReadBeforeEdit(Boolean)} is set to
	 * true.
	 * 
	 * @return {@link SerializableUnaryOperator} to apply on a selected bean before
	 *         viewing/editing it
	 */
	SerializableUnaryOperator<B> getReadBean();

	/**
	 * [OPTIONAL]
	 * Sets an {@link SerializableUnaryOperator} to apply on a selected bean
	 * before viewing/editing it.<br>
	 * This is only used when {@link #setReadBeforeEdit(Boolean)} is set to
	 * true.
	 * 
	 * @param readBean
	 *            {@link SerializableUnaryOperator} to apply on a selected bean before
	 *            viewing/editing it
	 */
	void setReadBean(SerializableUnaryOperator<B> readBean);

	/**
	 * Gets the backToCriteriaAction. This action is executed before going back
	 * to the criteria form from the results.
	 *
	 * @return Returns the backToCriteriaAction
	 */
	LegacyCallbackAction getBackToCriteriaAction();

	/**
	 * Sets the backToCriteriaAction. <br>
	 * This action is executed before going back to the criteria form from the
	 * results.
	 *
	 * @param backToCriteriaAction
	 */
	void setBackToCriteriaAction(LegacyCallbackAction backToCriteriaAction);
	
	/**
	 * [OPTIONAL]
	 * Sets the option to hide the buttons of a {@link SingleBeanPanel}.
	 *
	 * @param hideSingleBeanPanelButtons the new hide single bean panel buttons
	 */
	void setHideSingleBeanPanelButtons(Boolean hideSingleBeanPanelButtons);
	
	/**
	 * [OPTIONAL]
	 * Gets the option to hide the buttons of a {@link SingleBeanPanel}.
	 * 
	 * @return hideSingleBeanPanelButtons
	 */
	Boolean getHideSingleBeanPanelButtons();
}