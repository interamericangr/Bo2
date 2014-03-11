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

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.callback.CallbackWrapper;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * {@link ServicePanel} that manages a simple UI flow of a query.
 * This UI flow consists of:
 * 
 * <li> A screen that allows the user to define criteria for the query
 * and execute it. </li>
 * <li> A screen that shows the results and allows the user to select
 * one (or more) items or go back to the previous screen. </li>
 * 
 * Anyone using this, should provide the following {@link CallbackAction}s.
 * 
 * <li> back action (optionally).
 * <li> query action (should put the results in this panel's definition).
 * <li> selected item(s) action (should retrieve the selected item(s) from this
 *      panel's definition and do something with it.
 * <li> delete, update, save action (should retrieve the item to perform
 *      the operation on from the beanModel of this panel's definition.
 * 
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

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID of criteriaPanel
	 */
	public static final String CRITERIA_PANEL_ID = "criteriaPanel"; //$NON-NLS-1$

	/**
	 * ID of resiltsPanel
	 */
	public static final String RESULTS_PANEL_ID = "resultsPanel"; //$NON-NLS-1$

	/**
	 * the criteria panel.
	 */
	protected Panel criteriaPanel;

	/**
	 *  the criteria panel definition.
	 */
	protected SingleBeanPanelDef<C> criteriaPanelDef;

	/**
	 * the results panel.
	 */
	protected Panel resultsPanel;

	/**
	 *  the criteria panel definition.
	 */
	protected ListTablePanelDef<B> resultsPanelDef;

	/**
	 * state of the panel.
	 */
	protected SearchFlowPanelState state;

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
	 * Bean class.
	 */
	private Class<B> beanClass;

	/**
	 * Criteria class.
	 */
	private Class<C> criteriaClass;

	/**
	 * Creates a new QueryFlowPanel object.
	 * 
	 * @param definition
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
		state.paint(this);
		add(criteriaPanel);
		add(resultsPanel);
		add(feedBackPanel);
		paintBackButton();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void init() {
		super.init();
		hasCrudPickerPanel = (getDefinition().getDeleteAction()!=null)
				|| (getDefinition().getUpdateAction()!=null)
				|| (getDefinition().getSaveAction()!=null);
		hasPickerPanel = (getDefinition().getPickAction()!=null)
				&& !getDefinition().getAllowMultipleSelections()
				&& !hasCrudPickerPanel;
		hasMultipleSelectionsPanel = getDefinition().getAllowMultipleSelections()
				&& !hasCrudPickerPanel
				&& !hasPickerPanel;
		if(!hasMultipleSelectionsPanel) {
			beanClass = (Class<B>) getDefinition().getBeanModel().getObject().getClass();
		}
		criteriaClass = (Class<C>) getDefinition().getCriteriaModel().getObject().getClass();

		getDefinition().getQueryAction().setCaller(this);

		criteriaPanelDef = createCriteriaPanelDef();
		resultsPanelDef = createResultsPanelDef();
		criteriaPanel = new CriteriaPanel(criteriaPanelDef);
		resultsPanel = new EmptyPanel(RESULTS_PANEL_ID);
		state = SearchFlowPanelState.CRITERIA;
	}

	@SuppressWarnings("nls")
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
			getDefinition().setAllowMultipleSelections(false);
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
		if(getDefinition().getQueryAction()==null) {
			String msg = "Cannot initialize a SearchFlowPanel without a query action.";
			throw new RuntimeException(msg);
		}
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

		SingleBeanPanelDef<C> cpDef = new SingleBeanPanelDefImpl<C>();
		cpDef.setWicketId(CRITERIA_PANEL_ID);
		cpDef.setShowClearButton(true);
		cpDef.setBeanAction(new QueryWrapperAction(getDefinition().getQueryAction()));
		cpDef.setBackAction(null);
		cpDef.setBeanFieldsPanelCreator(getDefinition().getCriteriaFieldsPanelCreator());
		cpDef.setBeanModel(getDefinition().getCriteriaModel());
		cpDef.setExecuteLabelModel(executeQueryLabel);
		cpDef.setClearLabelModel(clearCriteriaLabel);
		cpDef.setPanelLabelModel(criteriaPanelLabel);
		return cpDef;
	}

	/**
	 * Creates the ServicePanel definition for this search flow panel.
	 * 
	 * @return a sub-type of {@link ListTablePanelDef}.
	 */
	protected ListTablePanelDef<B> createResultsPanelDef() {
		CallbackAction backAction = null;
		if(getDefinition().getResultsHidesCriteria()) {
			backAction = new BackToCriteriaAction();
		}

		IModel<String> backToCriteriaLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SFP_LTP_BACK_BTN_LABEL, this, getDefinition().getBackToCriteriaLabelModel(), null);
		IModel<String> resultsLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SFP_LTP_LIST_TABLE_LABEL, this, getDefinition().getResultsLabelModel(), null);
		IModel<String> selectLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SFP_PP_SELECT_BTN_LABEL, this, getDefinition().getSelectLabelModel(), null);
		IModel<String> secondSelectLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.SFP_PP_2ND_SELECT_BTN_LABEL, this, getDefinition().getSecondSelectLabelModel(), null);

		if(hasPickerPanel) {
			PickerPanelDef<B> ppDef = new PickerPanelDefImpl<B>();
			ppDef.setWicketId(RESULTS_PANEL_ID);
			ppDef.setBackAction(backAction);
			ppDef.setDataTableCreator(getDefinition().getDataTableCreator());
			ppDef.setList(getDefinition().getResults());
			ppDef.setItemSelectedAction(getDefinition().getPickAction());
			ppDef.setItemSelectedActionFlag(getDefinition().getPickActionFlag());
			ppDef.setSecondItemSelectedAction(getDefinition().getSecondPickAction());
			ppDef.setSecondItemSelectedActionFlag(getDefinition().getSecondPickActionFlag());
			ppDef.setRefreshListAfterPickAction(getDefinition().getRefreshListAfterPickAction());
			ppDef.setBeanModel(getDefinition().getBeanModel());
			ppDef.setListLabelModel(resultsLabel);
			ppDef.setSecondSelectLabelModel(secondSelectLabel);
			ppDef.setSelectLabelModel(selectLabel);
			ppDef.setBackLabelModel(backToCriteriaLabel);
			return ppDef;
		}
		if(hasMultipleSelectionsPanel) {
			IModel<String> checkGroupSelectorLabel = StringResourceUtils.getResourceModel(
					WellKnownResourceIds.SFP_MSP_CHECKGROUP_SELECTOR_LABEL, this, getDefinition().getCheckGroupSelectorLabelModel(), null);

			MultipleSelectionsPanelDef<B> mspDef = new MultipleSelectionsPanelDefImpl<B>();
			mspDef.setWicketId(RESULTS_PANEL_ID);
			mspDef.setBackAction(backAction);
			mspDef.setDataTableCreator(getDefinition().getDataTableCreator());
			mspDef.setList(getDefinition().getResults());
			mspDef.setSelectionsModel(getDefinition().getSelectionsModel());
			mspDef.setItemsSelectedAction(getDefinition().getPickAction());
			mspDef.setItemsSelectedActionFlag(getDefinition().getPickActionFlag());
			mspDef.setSecondItemsSelectedAction(getDefinition().getSecondPickAction());
			mspDef.setSecondItemsSelectedActionFlag(getDefinition().getSecondPickActionFlag());
			mspDef.setListLabelModel(resultsLabel);
			mspDef.setSelectLabelModel(selectLabel);
			mspDef.setSecondSelectLabelModel(secondSelectLabel);
			mspDef.setCheckGroupSelectorLabelModel(checkGroupSelectorLabel);
			mspDef.setBackLabelModel(backToCriteriaLabel);
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
			cppDef.setWicketId(RESULTS_PANEL_ID);
			cppDef.setDataTableCreator(getDefinition().getDataTableCreator());
			cppDef.setList(getDefinition().getResults());
			cppDef.setItemSelectedAction(getDefinition().getPickAction());
			cppDef.setItemSelectedActionFlag(getDefinition().getPickActionFlag());
			cppDef.setRefreshListAfterPickAction(getDefinition().getRefreshListAfterPickAction());
			cppDef.setBackAction(backAction);
			cppDef.setBeanModel(getDefinition().getBeanModel());
			cppDef.setBeanFieldsPanelCreator(getDefinition().getBeanFieldsPanelCreator());
			cppDef.setSaveAction(getDefinition().getSaveAction());
			cppDef.setSaveActionFlag(getDefinition().getSaveActionFlag());
			cppDef.setUpdateAction(getDefinition().getUpdateAction());
			cppDef.setUpdateActionFlag(getDefinition().getUpdateActionFlag());
			cppDef.setDeleteAction(getDefinition().getDeleteAction());
			cppDef.setDeleteActionFlag(getDefinition().getDeleteActionFlag());
			cppDef.setReadBeforeEdit(getDefinition().getReadBeforeEdit());
			cppDef.setViewEnabled(getDefinition().getViewEnabled());
			cppDef.setRequestConfirmOnDelete(getDefinition().getRequestConfirmOnDelete());
			cppDef.setListLabelModel(resultsLabel);
			cppDef.setSecondSelectLabelModel(secondSelectLabel);
			cppDef.setSecondItemSelectedAction(getDefinition().getSecondPickAction());
			cppDef.setSelectLabelModel(selectLabel);
			cppDef.setSaveLabelModel(sbpSaveButtonLabel);
			cppDef.setUpdateLabelModel(sbpUpdateButtonLabel);
			cppDef.setEditLabelModel(editButtonLabel);
			cppDef.setNewLabelModel(newButtonLabel);
			cppDef.setDeleteLabelModel(deleteButtonLabel);
			cppDef.setViewLabelModel(viewButtonLabel);
			cppDef.setBackLabelModel(backToCriteriaLabel);
			cppDef.setBeanFieldsPanelBackLabelModel(sbpBackButtonLabel);
			cppDef.setClearLabelModel(sbpClearButtonLabel);
			cppDef.setSingleBeanPanelLabelModel(sbpPanelLabel);
			cppDef.setSaveValidator(getDefinition().getSaveValidator());
			cppDef.setUpdateValidator(getDefinition().getUpdateValidator());
			cppDef.setDeleteValidator(getDefinition().getDeleteValidator());
			cppDef.setPreEditValidator(getDefinition().getPreEditValidator());
			cppDef.setCustomSingleBeanPanelDisabling(getDefinition().getCustomSingleBeanPanelDisabling());
			cppDef.setSingleBeanFormContainsFileUpload(getDefinition().getSingleBeanFormContainsFileUpload());
			if(getDefinition().getRefreshAfterDataOp()) {
				cppDef.setRefreshAfterDataOpAction(new QueryWrapperAction(getDefinition().getQueryAction()));
			}
			return cppDef;
		}
		ListTablePanelDef<B> ltpDef = new ListTablePanelDefImpl<B>();
		ltpDef.setWicketId(RESULTS_PANEL_ID);
		ltpDef.setBackAction(backAction);
		ltpDef.setDataTableCreator(getDefinition().getDataTableCreator());
		ltpDef.setList(getDefinition().getResults());
		ltpDef.setBackLabelModel(getDefinition().getBackLabelModel());
		ltpDef.setListLabelModel(getDefinition().getResultsLabelModel());
		return ltpDef;
	}

	/**
	 * Creates and initializes the <code>resultsPanel</code>.
	 * 
	 * @return the new results panel.
	 */
	@SuppressWarnings("serial")
	protected Panel createResultsPanel() {
		if(hasPickerPanel) {
			Panel newResultsPanel = new PickerPanel<B>((PickerPanelDef<B>) resultsPanelDef);
			return newResultsPanel;
		}
		if(hasMultipleSelectionsPanel) {
			Panel newResultsPanel = new MultipleSelectionsPanel<B>((MultipleSelectionsPanelDef<B>) resultsPanelDef);
			return newResultsPanel;
		}
		if(hasCrudPickerPanel) {
			Panel newResultsPanel = new CrudPickerPanel<B>((CrudPickerPanelDef<B>) resultsPanelDef) {
				@Override protected B newBean() {
					return SearchFlowPanel.this.newBean();
				}
				@Override protected B readBean() {
					return SearchFlowPanel.this.readBean();
				}
				@Override protected B copyBean(B bean) {
					return SearchFlowPanel.this.copyBean(bean);
				}
			};
			return newResultsPanel;
		}
		Panel newResultsPanel = new ListTablePanel<B>(resultsPanelDef);
		return newResultsPanel;
	}


	/**
	 * Creates a new instance of B.
	 * 
	 * @return a new B.
	 */
	@SuppressWarnings("nls")
	protected B newBean() {
		try {
			B instance = ReflectionUtils.newInstance(beanClass);
			return instance;
		} catch (RuntimeException re) {
			String msg = StringUtils.concat(
					"Could not create a bean instance for SearchFlowPanel with ",
					"wicket:id " + getDefinition().getWicketId() + ". ",
					"Consider overriding newBean().");
			throw new RuntimeException(msg, re);
		}
	}

	/**
	 * Creates a new instance of C.
	 * 
	 * @return a new C.
	 */
	@SuppressWarnings("nls")
	protected C newCriteriaBean() {
		try {
			C instance = ReflectionUtils.newInstance(criteriaClass);
			return instance;
		} catch (RuntimeException re) {
			String msg = StringUtils.concat(
					"Could not create a criteria bean instance for SearchFlowPanel with ",
					"wicket:id " + getDefinition().getWicketId() + ". ",
					"Consider overriding newCriteriaBean().");
			throw new RuntimeException(msg, re);
		}
	}

	/**
	 * Hook intended to be overridden by the user if he chooses to set
	 * <code>readBeforeEdit</code> to true in the {@link SearchFlowPanelDef}.
	 * 
	 * TODO: If an exception is caught here, there is no way to register the
	 *       message in a feedback panel that is ajax updated.
	 * 
	 * @return Returns a fresh copy of B from the persistence layer.
	 */
	protected B readBean() {

		String msg = StringUtils.concat(
				"If you want to read before editing ", //$NON-NLS-1$
				"you MUST override readBean()."); //$NON-NLS-1$
		throw new RuntimeException(msg);
	}

	/**
	 * Hook intended to be overridden by the user. This is useful
	 * if when updating a B instance there is a chance that the update
	 * will be aborted after submitting the update form. This could happen,
	 * for example, if a validation message is shown and the user decides
	 * to go back instead of correcting the error and submitting the
	 * update form again.
	 * 
	 * If readBeforeEdit is true, this is not used, as instead of deep
	 * copying, a new instance is created from the persistence layer, which
	 * has the same effect.
	 * 
	 * @param bean instance to copy.
	 * 
	 * @return Returns a copy of B.
	 */
	protected B copyBean(B bean) {
		return bean;
	}

	/**
	 * Override this if you want to execute some code before
	 * going back to the criteria form from the results.
	 * 
	 * @param target
	 */
	protected void backToCriteria(@SuppressWarnings("unused") AjaxRequestTarget target) { 
		/* empty hook */
	}

	/**
	 * This action will hide the resultsPanel and show the criteriaPanel.
	 */
	public class BackToCriteriaAction implements CallbackAction {
		
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		public void callBack(AjaxRequestTarget target) {
			work(target);
		}

		public void callBack(AjaxRequestTarget target, Form<?> form) {
			work(target);
		}

		/**
		 * work.
		 * @param target
		 */
		void work(AjaxRequestTarget target) {
			target.add(SearchFlowPanel.this);
			backToCriteria(target);
			state = SearchFlowPanelState.CRITERIA;
			state.paint(SearchFlowPanel.this);
		}

		public void setCaller(Component caller) { /* empty */ }
		public Component getCaller() {
			return SearchFlowPanel.this;
		}
	}

	/**
	 * Wrapper around the query callback action that connects
	 * the callback action of the criteriaPanel with the resultsPanel.
	 */
	private class QueryWrapperAction extends CallbackWrapper {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new QueryAction object.
		 *
		 * @param action
		 */
		public QueryWrapperAction(CallbackAction action) {
			super(action);
		}

		@Override public void before() {
			resultsPanelDef.getList().clear();
		}

		@Override public void after() {
			List<B> queryResults =  getDefinition().getResults();
			if(queryResults==null) { return; }
			resultsPanelDef.setList(queryResults);
			Panel newResultsPanel = createResultsPanel();
			resultsPanel.replaceWith(newResultsPanel);
			resultsPanel = newResultsPanel;
			if (getDefinition().getResultsHidesCriteria()) {
				state = SearchFlowPanelState.RESULTS;
			} else {
				state = SearchFlowPanelState.BOTH;
			}
			state.paint(SearchFlowPanel.this);
		}
	}

	/**
	 * CriteriaPanel.
	 */
	private class CriteriaPanel extends SingleBeanPanel<C> {
		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new SearchFlowPanel.CriteriaPanel object.
		 * @param definition
		 */
		public CriteriaPanel(SingleBeanPanelDef<C> definition) {
			super(definition);
		}

		@Override protected C newBean() {
			return SearchFlowPanel.this.newCriteriaBean();
		}
	}

}
