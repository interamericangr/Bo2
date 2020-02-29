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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.callback.MultiplePickAction;
import gr.interamerican.wicket.callback.SearchAction;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBack;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanel;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDef;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanel;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanelDef;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.picker.MultipleSelectionsPanel;
import gr.interamerican.wicket.markup.html.panel.picker.MultipleSelectionsPanelDef;
import gr.interamerican.wicket.markup.html.panel.picker.MultipleSelectionsPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanel;
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanelDef;
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.util.resource.StringResourceUtils;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;

/**
 * {@link ServicePanel} that manages a simple UI flow of a query.
 * This UI flow consists of:
 * <ul>
 * <li> A screen that allows the user to define criteria for the query
 * and execute it. </li>
 * <li> A screen that shows the results and allows the user to select
 * one (or more) items or go back to the previous screen. </li>
 * </ul>
 * Anyone using this, should provide the following Action's.
 * <ul>
 * <li> back action (optionally).</li>
 * <li> {@link SearchAction} (should put the results in this panel's definition).</li>
 * <li> selected item(s) action (should retrieve the selected item(s) from this
 *      panel's definition and do something with it.</li>
 * <li> delete, update, save action (should retrieve the item to perform
 *      the operation on from the beanModel of this panel's definition.</li>
 * </ul>
 * @param <C>
 *        Type of search criteria.
 * @param <B>
 *        Type of bean searched and being presented in the list.
 *
 */
public class SearchFlowPanel
<C extends Serializable,
B extends Serializable>
extends ServicePanelWithBack {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** ID of criteriaPanel. */
	public static final String CRITERIA_PANEL_ID = "criteriaPanel"; //$NON-NLS-1$

	/** ID of resiltsPanel. */
	public static final String RESULTS_PANEL_ID = "resultsPanel"; //$NON-NLS-1$

	/**
	 * Indicates if the results panel is a {@link CrudPickerPanel}.
	 */
	private Boolean hasCrudPickerPanel;

	/**
	 * Indicates if the results panel is a {@link PickerPanel}.
	 */
	private Boolean hasPickerPanel;

	/**
	 * Indicates if the results panel is a {@link MultipleSelectionsPanel}.
	 */
	private Boolean hasMultipleSelectionsPanel;

	/**
	 * Creates a new QueryFlowPanel object.
	 *
	 * @param definition the definition
	 */
	public SearchFlowPanel(SearchFlowPanelDef<C, B> definition) {
		super(definition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public SearchFlowPanelDef<C, B> getDefinition() {
		return (SearchFlowPanelDef<C, B>) definition;
	}

	@Override
	protected void paint() {
		SingleBeanPanelDef<C> criteriaPanelDef = createCriteriaPanelDef();
		add(new SingleBeanPanel<>(criteriaPanelDef));
		EmptyPanel resultsPanel = new EmptyPanel(RESULTS_PANEL_ID);
		add(resultsPanel);
		paintState(SearchFlowPanelState.CRITERIA);
		add(feedBackPanel);
		paintBackButton();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void init() {
		super.init();
		hasCrudPickerPanel = (getDefinition().getDeleteAction()!=null)
				|| (getDefinition().getUpdateAction()!=null)
				|| (getDefinition().getSaveAction()!=null)
				|| getDefinition().getViewEnabled();
		hasPickerPanel = (getDefinition().getPickAction()!=null)
				&& !getDefinition().getAllowMultipleSelections()
				&& !hasCrudPickerPanel;
		hasMultipleSelectionsPanel = getDefinition().getAllowMultipleSelections()
				&& !hasCrudPickerPanel
				&& !hasPickerPanel;
	}

	@SuppressWarnings({ "nls", "deprecation" })
	@Override
	protected void validateDef() {
		super.validateDef();
		if(getDefinition().getAutoPickSingleResult()==null) {
			getDefinition().setAutoPickSingleResult(false);
		}
		if(getDefinition().getResultsHidesCriteria()==null) {
			getDefinition().setResultsHidesCriteria(true);
		}
		if(getDefinition().getReadBeforeEdit() == null) {
			getDefinition().setReadBeforeEdit(false);
		}
		if(getDefinition().getViewEnabled() == null) {
			getDefinition().setViewEnabled(false);
		}
		if(getDefinition().getRequestConfirmOnDelete() == null) {
			getDefinition().setRequestConfirmOnDelete(false);
		}
		if(getDefinition().getAllowMultipleSelections() == null) {
			boolean multiple = getDefinition().getMultiplePickAction() != null
					|| getDefinition().getSecondMultiplePickAction() != null;
			getDefinition().setAllowMultipleSelections(multiple);
		}
		if(getDefinition().getRefreshAfterDataOp() == null) {
			getDefinition().setRefreshAfterDataOp(false);
		}
		if(getDefinition().getRefreshListAfterPickAction() == null) {
			getDefinition().setRefreshListAfterPickAction(false);
		}
		if(getDefinition().getCustomSingleBeanPanelDisabling()==null) {
			getDefinition().setCustomSingleBeanPanelDisabling(false);
		}
		if(getDefinition().getSingleBeanFormContainsFileUpload()==null) {
			getDefinition().setSingleBeanFormContainsFileUpload(false);
		}
		if(getDefinition().getAllowMultipleSelections() && (getDefinition().getSelectionsModel()==null)) {
			String msg = "Cannot instantiate a MultipleSelectionsPanel with a null selections model.";
			throw new RuntimeException(msg);
		}
		if(getDefinition().getResults()==null) {
			getDefinition().setResults(new ArrayList<B>());
		}
		if(!getDefinition().getAllowMultipleSelections()
				&& (getDefinition().getBeanModel() == null)) {
			String msg = "Cannot initialize a SearchFlowPanel with null bean model " +
					"in its definition.";
			throw new RuntimeException(msg);
		}
		if(!getDefinition().getAllowMultipleSelections()
				&& (getDefinition().getBeanModel() != null)
				&& (getDefinition().getBeanModel().getObject() == null)) {
			String msg = "Cannot initialize a SearchFlowPanel with a bean model " +
					"that has a null model object in its definition.";
			throw new RuntimeException(msg);
		}
		if (getDefinition().getBeanModel() != null && getDefinition().getBeanModel().getObject() != null
				&& getDefinition().getBeanCreator() == null) {
			@SuppressWarnings("unchecked")
			Class<B> beanClass = (Class<B>) getDefinition().getBeanModel().getObject().getClass();
			getDefinition().setBeanCreator(ReflectionUtils.supplier(beanClass));
		}
		if(getDefinition().getCriteriaModel() == null) {
			String msg = "Cannot initialize a SearchFlowPanel with null criteria model " +
					"in its definition.";
			throw new RuntimeException(msg);
		}
		if(getDefinition().getCriteriaModel().getObject() == null) {
			String msg = "Cannot initialize a SearchFlowPanel with a criteria model " +
					"that has a null model object in its definition.";
			throw new RuntimeException(msg);
		}
		if (getDefinition().getCriteriaBeanCreator() == null) {
			@SuppressWarnings("unchecked")
			Class<C> criteriaBeanClass = (Class<C>) getDefinition().getCriteriaModel().getObject().getClass();
			getDefinition().setCriteriaBeanCreator(ReflectionUtils.supplier(criteriaBeanClass));
		}
		if(getDefinition().getQueryAction()==null) {
			throw new RuntimeException("Cannot initialize a SearchFlowPanel without a query action.");
		}
		if ((getDefinition().getPickAction() != null || getDefinition().getSecondPickAction() != null)
				&& (getDefinition().getMultiplePickAction() != null
						|| getDefinition().getSecondMultiplePickAction() != null)) {
			throw new RuntimeException(
					"Both PickAction and MultiplePickAction were set - only one of these is allowed.");
		}
		if (getDefinition().getAllowMultipleSelections()) {
			@SuppressWarnings("unchecked")
			MultiplePickAction<B> multiplePickAction = Utils.notNull(getDefinition().getMultiplePickAction(),
					(MultiplePickAction<B>) getDefinition().getPickAction());
			getDefinition().setMultiplePickAction(multiplePickAction);
			@SuppressWarnings("unchecked")
			MultiplePickAction<B> secondMultiplePickAction = Utils.notNull(
					getDefinition().getSecondMultiplePickAction(),
					(MultiplePickAction<B>) getDefinition().getSecondPickAction());
			getDefinition().setSecondMultiplePickAction(secondMultiplePickAction);
		}
	}

	/**
	 * Paints the state of the {@link SearchFlowPanel}. In essence it controls
	 * the visibility of the criteria and results panel according the input
	 * {@link SearchFlowPanelState}.
	 * 
	 * @param state
	 *            Current State
	 */
	private void paintState(SearchFlowPanelState state) {
		state.paint(get(CRITERIA_PANEL_ID), get(RESULTS_PANEL_ID));
	}

	/**
	 * Creates the SingleBeanPanel definition for this search flow panel.
	 * 
	 * @return a {@link SingleBeanPanelDef}.
	 */
	protected SingleBeanPanelDef<C> createCriteriaPanelDef() {
		IModel<String> executeQueryLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SFP_SBP_EXECUTE_QUERY_BTN_LABEL, this, getDefinition().getExecuteQueryLabelModel(), null);
		IModel<String> clearCriteriaLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SFP_SBP_CLEAR_CRITERIA_BTN_LABEL, this, getDefinition().getClearCriteriaLabelModel(), null);
		IModel<String> criteriaPanelLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SFP_SBP_CRITERIA_PANEL_LABEL, this, getDefinition().getCriteriaPanelLabelModel(), null);

		SingleBeanPanelDef<C> def = new SingleBeanPanelDefImpl<C>();
		def.setWicketId(CRITERIA_PANEL_ID);
		def.setBeanCreator(this::newCriteriaBean);
		def.setShowClearButton(true);
		def.setBeanAction(this::doSearch);
		def.setBackAction(null);
		def.setBeanFieldsPanelCreator(getDefinition().getCriteriaFieldsPanelCreator());
		def.setBeanModel(getDefinition().getCriteriaModel());
		def.setExecuteLabelModel(executeQueryLabel);
		def.setClearLabelModel(clearCriteriaLabel);
		def.setPanelLabelModel(criteriaPanelLabel);
		return def;
	}

	/**
	 * Creates the ServicePanel definition for this search flow panel.
	 * 
	 * @return a sub-type of {@link ListTablePanelDef}.
	 */
	protected ListTablePanelDef<B> createResultsPanelDef() {
		IModel<String> selectLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SFP_PP_SELECT_BTN_LABEL, this, getDefinition().getSelectLabelModel(), null);
		IModel<String> secondSelectLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SFP_PP_2ND_SELECT_BTN_LABEL, this, getDefinition().getSecondSelectLabelModel(), null);

		if(hasPickerPanel) {
			PickerPanelDef<B> ppDef = new PickerPanelDefImpl<B>();
			setPickerPanelResultsDefinitionFields(ppDef, selectLabel, secondSelectLabel);
			return ppDef;
		}
		if(hasMultipleSelectionsPanel) {
			IModel<String> checkGroupSelectorLabel = StringResourceUtils.getResourceModel(
					WellKnownResourceIds.SFP_MSP_CHECKGROUP_SELECTOR_LABEL, this, getDefinition().getCheckGroupSelectorLabelModel(), null);
			MultipleSelectionsPanelDef<B> mspDef = new MultipleSelectionsPanelDefImpl<B>();
			mspDef.setItemsSelectedAction(getDefinition().getMultiplePickAction());
			mspDef.setItemsSelectedActionFlag(getDefinition().getPickActionFlag());
			mspDef.setSecondItemsSelectedAction(getDefinition().getSecondMultiplePickAction());
			mspDef.setSecondItemsSelectedActionFlag(getDefinition().getSecondPickActionFlag());
			mspDef.setSelectionsModel(getDefinition().getSelectionsModel());
			mspDef.setSelectLabelModel(selectLabel);
			mspDef.setSecondSelectLabelModel(secondSelectLabel);
			mspDef.setCheckGroupSelectorLabelModel(checkGroupSelectorLabel);
			return mspDef;
		}
		if(hasCrudPickerPanel) {
			IModel<String> newButtonLabel = StringResourceUtils.getResourceModel(
					WellKnownResourceIds.SFP_CPP_NEW_BTN_LABEL, this, getDefinition().getNewLabelModel(), null);
			IModel<String> editButtonLabel = StringResourceUtils.getResourceModel(
					WellKnownResourceIds.SFP_CPP_EDIT_BTN_LABEL, this, getDefinition().getEditLabelModel(), null);
			IModel<String> deleteButtonLabel = StringResourceUtils.getResourceModel(
					WellKnownResourceIds.SFP_CPP_DELETE_BTN_LABEL, this, getDefinition().getDeleteLabelModel(), null);
			IModel<String> viewButtonLabel = StringResourceUtils.getResourceModel(
					WellKnownResourceIds.SFP_CPP_VIEW_BTN_LABEL, this, getDefinition().getViewLabelModel(), null);
			IModel<String> sbpBackButtonLabel = StringResourceUtils.getResourceModel(
					WellKnownResourceIds.SFP_CPP_SBP_BACK_BTN_LABEL, this, getDefinition().getBeanFieldsPanelBackLabelModel(), null);
			IModel<String> sbpClearButtonLabel = StringResourceUtils.getResourceModel(
					WellKnownResourceIds.SFP_CPP_SBP_CLEAR_BTN_LABEL, this, getDefinition().getBeanFieldsPanelClearLabelModel(), null);
			IModel<String> sbpSaveButtonLabel = StringResourceUtils.getResourceModel(
					WellKnownResourceIds.SFP_CPP_SBP_SAVE_BTN_LABEL, this, getDefinition().getSaveLabelModel(), null);
			IModel<String> sbpUpdateButtonLabel = StringResourceUtils.getResourceModel(
					WellKnownResourceIds.SFP_CPP_SBP_UPDATE_BTN_LABEL, this, getDefinition().getUpdateLabelModel(), null);
			IModel<String> sbpPanelLabel = StringResourceUtils.getResourceModel(
					WellKnownResourceIds.SFP_CPP_SBP_PANEL_LABEL, this, getDefinition().getBeanFieldsPanelLabelModel(), null);

			CrudPickerPanelDef<B> cppDef = new CrudPickerPanelDefImpl<B>();
			setPickerPanelResultsDefinitionFields(cppDef, selectLabel, secondSelectLabel);
			cppDef.setBeanCreator(this::newBean);
			cppDef.setBeanFieldsPanelBackLabelModel(sbpBackButtonLabel);
			cppDef.setBeanFieldsPanelCreator(getDefinition().getBeanFieldsPanelCreator());
			cppDef.setClearLabelModel(sbpClearButtonLabel);
			cppDef.setCopyBean(this::copyBean);
			cppDef.setCustomSingleBeanPanelDisabling(getDefinition().getCustomSingleBeanPanelDisabling());
			cppDef.setDeleteAction(getDefinition().getDeleteAction());
			cppDef.setDeleteActionFlag(getDefinition().getDeleteActionFlag());
			cppDef.setDeleteLabelModel(deleteButtonLabel);
			cppDef.setDeleteValidator(getDefinition().getDeleteValidator());
			cppDef.setEditLabelModel(editButtonLabel);
			cppDef.setHideSingleBeanPanelButtons(getDefinition().getHideSingleBeanPanelButtons());
			cppDef.setNewLabelModel(newButtonLabel);
			cppDef.setPreEditValidator(getDefinition().getPreEditValidator());
			cppDef.setReadBean(this::read);
			cppDef.setReadBeforeEdit(getDefinition().getReadBeforeEdit());
			if(getDefinition().getRefreshAfterDataOp()) {
				cppDef.setRefreshAfterDataOpAction(this::repeatSearch);
			}
			cppDef.setRequestConfirmOnDelete(getDefinition().getRequestConfirmOnDelete());
			cppDef.setSaveAction(getDefinition().getSaveAction());
			cppDef.setSaveActionFlag(getDefinition().getSaveActionFlag());
			cppDef.setSaveLabelModel(sbpSaveButtonLabel);
			cppDef.setSaveValidator(getDefinition().getSaveValidator());
			cppDef.setSingleBeanFormContainsFileUpload(getDefinition().getSingleBeanFormContainsFileUpload());
			cppDef.setSingleBeanPanelLabelModel(sbpPanelLabel);
			cppDef.setUpdateAction(getDefinition().getUpdateAction());
			cppDef.setUpdateActionFlag(getDefinition().getUpdateActionFlag());
			cppDef.setUpdateLabelModel(sbpUpdateButtonLabel);
			cppDef.setUpdateValidator(getDefinition().getUpdateValidator());
			cppDef.setViewEnabled(getDefinition().getViewEnabled());
			cppDef.setViewLabelModel(viewButtonLabel);
			return cppDef;
		}
		return new ListTablePanelDefImpl<>();
	}

	/**
	 * Sets the fields of a {@link PickerPanelDef} for the Results Panel of
	 * this.<br>
	 * The fields that are set in
	 * {@link #setCommonResultsDefinitionFields(ListTablePanelDef)} are not set
	 * here.
	 * 
	 * @param ppDef
	 *            Definition to modify
	 * @param selectLabel
	 *            Label of the Select Button
	 * @param secondSelectLabel
	 *            Label of the Second Select Button
	 */
	private void setPickerPanelResultsDefinitionFields(PickerPanelDef<B> ppDef, IModel<String> selectLabel,
			IModel<String> secondSelectLabel) {
		ppDef.setBeanModel(getDefinition().getBeanModel());
		ppDef.setItemSelectedAction(getDefinition().getPickAction());
		ppDef.setItemSelectedActionFlag(getDefinition().getPickActionFlag());
		ppDef.setSelectLabelModel(selectLabel);
		ppDef.setSecondItemSelectedAction(getDefinition().getSecondPickAction());
		ppDef.setSecondItemSelectedActionFlag(getDefinition().getSecondPickActionFlag());
		ppDef.setSecondSelectLabelModel(secondSelectLabel);
		ppDef.setRefreshListAfterPickAction(getDefinition().getRefreshListAfterPickAction());
	}

	/**
	 * Sets the fields of a {@link ListTablePanelDef} for the Results Panel of
	 * this.
	 * 
	 * @param def
	 *            Definition to modify
	 */
	private void setCommonResultsDefinitionFields(ListTablePanelDef<B> def) {
		IModel<String> backToCriteriaLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SFP_LTP_BACK_BTN_LABEL, this, getDefinition().getBackToCriteriaLabelModel(), null);
		IModel<String> resultsLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SFP_LTP_LIST_TABLE_LABEL, this, getDefinition().getResultsLabelModel(), null);
		LegacyCallbackAction backAction = null;
		if (getDefinition().getResultsHidesCriteria()) {
			backAction = this::backToCriteriaAction;
		}
		def.setExportActionCreator(getDefinition().getExportActionCreator());
		def.setExportLabel(getDefinition().getExportLabel());
		def.setExportSetupModifier(getDefinition().getExportSetupModifier());
		def.setWicketId(RESULTS_PANEL_ID);
		def.setBackAction(backAction);
		def.setDataTableCreator(getDefinition().getDataTableCreator());
		def.setList(getDefinition().getResults());
		def.setBackLabelModel(backToCriteriaLabel);
		def.setListLabelModel(resultsLabel);
		def.setDisableUnauthorizedButtons(getDefinition().getDisableUnauthorizedButtons());
	}

	/**
	 * Creates and initializes the <code>resultsPanel</code>.
	 * 
	 * @return the new results panel.
	 */
	protected Panel createResultsPanel() {
		ListTablePanelDef<B> resultsPanelDef = createResultsPanelDef();
		setCommonResultsDefinitionFields(resultsPanelDef);
		if (hasPickerPanel) {
			return new PickerPanel<B>((PickerPanelDef<B>) resultsPanelDef);
		}
		if (hasMultipleSelectionsPanel) {
			return new MultipleSelectionsPanel<B>((MultipleSelectionsPanelDef<B>) resultsPanelDef);
		}
		if (hasCrudPickerPanel) {
			return new CrudPickerPanel<B>((CrudPickerPanelDef<B>) resultsPanelDef);
		}
		return new ListTablePanel<B>(resultsPanelDef);
	}

	/**
	 * Creates a new instance of B.
	 * 
	 * @return a new B.
	 * @deprecated Stop Overwriting this - use
	 *             {@link SearchFlowPanelDef#setBeanCreator(gr.interamerican.bo2.utils.functions.SerializableSupplier)}
	 */
	@Deprecated
	protected B newBean() {
		return getDefinition().getBeanCreator().get();
	}

	/**
	 * Creates a new instance of C.
	 * 
	 * @return a new C.
	 * 
	 * @deprecated Stop Overwriting this - use
	 *             {@link SearchFlowPanelDef#setCriteriaBeanCreator(gr.interamerican.bo2.utils.functions.SerializableSupplier)}
	 */
	@Deprecated
	protected C newCriteriaBean() {
		return getDefinition().getCriteriaBeanCreator().get();
		}

	/**
	 * Temporary method that simply delegates to {@link #readBean()} and ignores
	 * the input.
	 * 
	 * @param in
	 *            Input
	 * @return B we have read
	 */
	private B read(@SuppressWarnings("unused") B in) {
		return readBean();
	}

	/**
	 * Hook intended to be overridden by the user if he chooses to set
	 * <code>readBeforeEdit</code> to true in the {@link SearchFlowPanelDef}.
	 * 
	 * TODO: If an exception is caught here, there is no way to register the
	 *       message in a feedback panel that is ajax updated.
	 * 
	 * @return Returns a fresh copy of B from the persistence layer.
	 * @deprecated Stop Overwriting this - use
	 *             {@link SearchFlowPanelDef#setReadBean(gr.interamerican.bo2.utils.functions.SerializableUnaryOperator)}
	 */
	@Deprecated
	protected B readBean() {
		if (getDefinition().getReadBean() == null) {
			String msg = StringUtils.concat("If you want to read before editing ", //$NON-NLS-1$
					"you MUST fill CrudPickerPanelDef#setReadBean."); //$NON-NLS-1$
		throw new RuntimeException(msg);
	}
		return getDefinition().getReadBean().apply(getDefinition().getBeanModel().getObject());
	}

	/**
	 * Hook intended to be overridden by the user.
	 * 
	 * @param bean
	 *            instance to copy.
	 * 
	 * @return Returns a copy of B.
	 * @deprecated Stop Overwriting this - use
	 *             {@link SearchFlowPanelDef#setCopyBean(gr.interamerican.bo2.utils.functions.SerializableUnaryOperator)}
	 */
	@Deprecated
	protected B copyBean(B bean) {
		if (getDefinition().getCopyBean() == null) {
		return bean;
	}
		return getDefinition().getCopyBean().apply(bean);
	}

	/**
	 * This action will hide the resultsPanel and show the criteriaPanel.
	 * @param target 
	 */
	private void backToCriteriaAction(AjaxRequestTarget target) {
		target.add(this);
		backToCriteria(target);
		paintState(SearchFlowPanelState.CRITERIA);
	}

	/**
	 * Override this if you want to execute some code before going back to the
	 * criteria form from the results.
	 *
	 * @param target
	 *            the target
	 * @deprecated Stop Overwriting this - use
	 *             {@link SearchFlowPanelDef#setBackToCriteriaAction(LegacyCallbackAction)}
	 */
	@Deprecated
	protected void backToCriteria(AjaxRequestTarget target) {
		LegacyCallbackAction action = getDefinition().getBackToCriteriaAction();
		if (action != null) {
			action.doInvoke(target);
		}
	}

	/**
	 * This method supports the refreshing the result panel. This is useful in cases
	 * where the beans of the result list are updated (through pick action or a
	 * modal window) and you must show the changes to the screen. <br>
	 * You must be careful when you update the result beans. You should do the
	 * following <br>
	 * <code>def.getResults().clear(); <br> 
	 * def.getResults().addAll(results);</code> <br>
	 * instead of <br>
	 * <code>def.setResults(results);</code>
	 * 
	 * @param target 
	 */
	public void refreshResults(AjaxRequestTarget target) {
		target.add(this);
		replace(createResultsPanel());
	}

	/**
	 * @param target
	 * @param bean
	 * @throws Exception 
	 */
	@SuppressWarnings("deprecation")
	void doSearch(AjaxRequestTarget target, C bean) throws Exception {
		getDefinition().getResults().clear();
		SearchAction<C, B> search = getDefinition().getQueryAction();
		// backwards compatibility
		List<B> queryResults;
		if (search instanceof gr.interamerican.wicket.callback.CallbackAction) {
			gr.interamerican.wicket.callback.CallbackAction callback = (gr.interamerican.wicket.callback.CallbackAction) search;
			callback.setCaller(this);
			callback.callBack(target, (Form<?>) get(CRITERIA_PANEL_ID).get(SingleBeanPanel.FORM_ID));
			queryResults = getDefinition().getResults();
		} else {
			queryResults = search.search(bean);
		}
		if (queryResults == null) {
			return;
		}
		getDefinition().setResults(queryResults);
		replace(createResultsPanel());
		SearchFlowPanelState state;
		if (getDefinition().getResultsHidesCriteria()) {
			state = SearchFlowPanelState.RESULTS;
		} else {
			state = SearchFlowPanelState.BOTH;
		}
		paintState(state);
		target.add(this);
	}

	/**
	 * Repeats the search
	 * 
	 * @param target
	 * @throws Exception 
	 */
	void repeatSearch(AjaxRequestTarget target) throws Exception {
		doSearch(target, getDefinition().getCriteriaModel().getObject());
	}
}