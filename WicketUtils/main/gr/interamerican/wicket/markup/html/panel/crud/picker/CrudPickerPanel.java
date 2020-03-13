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
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.functions.SerializableSupplier;
import gr.interamerican.bo2.utils.functions.SerializableUnaryOperator;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.Consume;
import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.callback.ProcessAction;
import gr.interamerican.wicket.condition.AjaxCondition;
import gr.interamerican.wicket.creators.PanelCreatorMode;
import gr.interamerican.wicket.markup.html.panel.ServicePanelUtils;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanel;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.util.resource.StringResourceUtils;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * {@link ServicePanel} that shows a list of objects and allows
 * the user to pick one and/or perform CRUD operations on the list
 * (delete existing, edit existing, create new).
 * 
 * If the user does not define a <code>itemSelectedAction</code>
 * the pick capability is hidden. In this case, the radio buttons
 * are still useful for selecting the item to delete or edit. 
 * 
 * <p>
 * This panel requires 5 Actions:<br>
 * go back<br>
 * select item<br>
 * delete item<br>
 * save new item<br>
 * save edited item<br>
 * </p>
 * @param <B> the generic type
 */
public class CrudPickerPanel<B extends Serializable> 
extends PickerPanel<B> {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * wicket id.
	 */
	public static final String DELETE_BUTTON_ID = "deleteButton"; //$NON-NLS-1$
	
	/**
	 * wicket id.
	 */
	public static final String EDIT_BUTTON_ID = "editButton"; //$NON-NLS-1$
	
	/**
	 * wicket id.
	 */
	public static final String VIEW_BUTTON_ID = "viewButton"; //$NON-NLS-1$
	
	/**
	 * wicket id.
	 */
	public static final String NEW_BUTTON_ID = "newButton"; //$NON-NLS-1$
	
	/**
	 * wicket id.
	 */
	public static final String NEW_OR_EDIT_PANEL_ID = "beanPanel"; //$NON-NLS-1$
	
	/**
	 * New or edit bean panel. 
	 */
	protected Panel beanPanel; 
	
	/**
	 * Delete selected row {@link AjaxButton}.
	 */
	protected CallbackAjaxButton deleteButton;
	
	/**
	 * Edit selected row {@link AjaxButton}.
	 */
	protected AjaxButton editButton;
	
	/**
	 * View selected row {@link AjaxButton}.
	 */
	protected AjaxButton viewButton;
	
	/**
	 * New row {@link AjaxButton}.
	 */
	protected AjaxButton newButton;

	/**
	 * Creates a new CrudPickerPanel object. 
	 *
	 * @param definition the definition
	 */
	public CrudPickerPanel(CrudPickerPanelDef<B> definition) {
		super(definition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CrudPickerPanelDef<B> getDefinition() {
		return (CrudPickerPanelDef<B>) definition;
	}

	@SuppressWarnings("nls")
	@Override
	protected void init() {
		super.init();
		
		IModel<String> deleteLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_DELETE_BTN_LABEL, this, getDefinition().getDeleteLabelModel(), "Delete"); 
		IModel<String> newLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_NEW_BTN_LABEL, this, getDefinition().getNewLabelModel(), "New");
		IModel<String> editLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_EDIT_BTN_LABEL, this, getDefinition().getEditLabelModel(), "Edit");
		IModel<String> viewLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_VIEW_BTN_LABEL, this, getDefinition().getViewLabelModel(), "View");

		String text = null;
		if (getDefinition().getRequestConfirmOnDelete()) {
			text = "Are you sure?";
		}
		deleteButton = new CallbackAjaxButton(DELETE_BUTTON_ID, deleteLabel, this::doDelete, getFeedBackPanel(), text);
		ServicePanelUtils.disableButton(getDefinition(), getDefinition().getDeleteActionFlag(), deleteButton);

		editButton = new CallbackAjaxButton(EDIT_BUTTON_ID, editLabel, this::createEditPanel, feedBackPanel);
		
		ServicePanelUtils.disableButton(getDefinition(), getDefinition().getUpdateActionFlag(), editButton);
		
		viewButton = new CallbackAjaxButton(VIEW_BUTTON_ID, viewLabel, this::doView, feedBackPanel);
		newButton = new CallbackAjaxButton(NEW_BUTTON_ID, newLabel, this::createNewPanel, feedBackPanel);
		
		ServicePanelUtils.disableButton(getDefinition(), getDefinition().getSaveActionFlag(), newButton);
		
		beanPanel = new EmptyPanel(NEW_OR_EDIT_PANEL_ID);
		
		if(getDefinition().getSingleBeanFormContainsFileUpload()) {
			tableForm.setMultiPart(true);
		}
	}

	@Override
	protected void paint() {
		add(listLabel);
		radioGroup.add(table);
		tableForm.add(radioGroup);
		tableForm.add(selectButton);
		tableForm.add(secondSelectButton);
		tableForm.add(editButton);
		tableForm.add(newButton);
		tableForm.add(deleteButton);
		tableForm.add(viewButton);
		tableForm.add(backButton);
		tableForm.add(getDefinition().createButton(EXPORT_BUTTON_ID, getDefinition(), this));
		add(tableForm);
		add(feedBackPanel);
		add(beanPanel);
		
		beanPanel.setVisible(false);
		if(getDefinition().getItemSelectedAction() == null) {
			selectButton.setVisible(false);
		}
		if(getDefinition().getSecondItemSelectedAction() == null) {
			secondSelectButton.setVisible(false);
		}
		if(getDefinition().getSaveAction() == null) {
			newButton.setVisible(false);
		}
		if(getDefinition().getDeleteAction() == null) {
			deleteButton.setVisible(false);
		}
		if(getDefinition().getUpdateAction() == null) {
			editButton.setVisible(false);
		}
		if(!getDefinition().getViewEnabled()) {
			viewButton.setVisible(false);
		}
		
	}

	@SuppressWarnings("nls")
	@Override
	protected void validateDef() {
		if (getDefinition().getViewEnabled() == null) {
			getDefinition().setViewEnabled(false);
		}
		boolean needsFieldsPanelCreator = (getDefinition().getUpdateAction() != null)
				|| (getDefinition().getSaveAction() != null) || getDefinition().getViewEnabled();
		if (needsFieldsPanelCreator && getDefinition().getBeanFieldsPanelCreator() == null) {
			String msg = "Cannot create a CrudPicker panel with update/create capabilities without a beanFieldsPanelCreator.";
			throw new RuntimeException(msg);
		}
		if (needsFieldsPanelCreator && getDefinition().getBeanModel() == null) {
			String msg = "Cannot create a CrudPicker panel with update/create capabilities with a null beanModel.";
			throw new RuntimeException(msg);
		}
		if (needsFieldsPanelCreator && getDefinition().getBeanModel().getObject() == null) {
			String msg = "Cannot create a CrudPicker panel with update/create capabilities with a null beanModel object.";
			throw new RuntimeException(msg);
		}
		if (needsFieldsPanelCreator && getDefinition().getBeanCreator() == null) {
			@SuppressWarnings("unchecked")
			Class<B> beanClass = (Class<B>) getDefinition().getBeanModel().getObject().getClass();
			getDefinition().setBeanCreator(ReflectionUtils.supplier(beanClass));
		}
		if (getDefinition().getReadBeforeEdit() == null) {
			getDefinition().setReadBeforeEdit(false);
		}
		if (getDefinition().getRequestConfirmOnDelete() == null) {
			getDefinition().setRequestConfirmOnDelete(false);
		}
		if (getDefinition().getSingleBeanFormContainsFileUpload() == null) {
			getDefinition().setSingleBeanFormContainsFileUpload(false);
		}
		if (getDefinition().getCustomSingleBeanPanelDisabling() == null) {
			getDefinition().setCustomSingleBeanPanelDisabling(false);
		}
		if (getDefinition().getHideSingleBeanPanelButtons() == null) {
			getDefinition().setHideSingleBeanPanelButtons(false);
		}
		super.validateDef();
	}

	/**
	 * Creates the {@link SingleBeanPanelDef}. There is no need to propagate any
	 * authorization related flags, as they are applied to the buttons that call
	 * this anyway.
	 *
	 * @param beanAction
	 *            the bean action
	 * @param validator
	 *            the validator
	 * @param mode
	 *            the mode
	 * @return the {@link SingleBeanPanelDef}.
	 */
	protected SingleBeanPanelDef<B> createSingleBeanPanelDef(
	PickAction<B> beanAction, AjaxCondition<B> validator, PanelCreatorMode mode) {
		
		IModel<String> sbpBackLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_SBP_BACK_BTN_LABEL, this, getDefinition().getBeanFieldsPanelBackLabelModel(), null);
		IModel<String> sbpClearLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_SBP_CLEAR_BTN_LABEL, this, getDefinition().getClearLabelModel(), null);
		IModel<String> sbpUpdateLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_SBP_UPDATE_BTN_LABEL, this, getDefinition().getUpdateLabelModel(), null);
		IModel<String> sbpSaveLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_SBP_SAVE_BTN_LABEL, this, getDefinition().getSaveLabelModel(), null);
		IModel<String> sbpLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_SBP_PANEL_LABEL, this, getDefinition().getSingleBeanPanelLabelModel(), null);
		
		SingleBeanPanelDef<B> def = new SingleBeanPanelDefImpl<>();
		def.setBackAction(this::hideBean);
		def.setBackLabelModel(sbpBackLabel);
		def.setBeanAction(beanAction);
		def.setBeanFieldsPanelCreator(getDefinition().getBeanFieldsPanelCreator());
		def.setBeanModel(getDefinition().getBeanModel());
		def.setBeanFieldsPanelMode(mode);
		def.setClearLabelModel(sbpClearLabel);
		if(PanelCreatorMode.EDIT.equals(mode)) {
			def.setExecuteLabelModel(sbpUpdateLabel);
		} else {
			def.setExecuteLabelModel(sbpSaveLabel);
		}
		def.setFormValidator(validator);
		def.setPanelLabelModel(sbpLabel);
		def.setSingleBeanFormContainsFileUpload(getDefinition().getSingleBeanFormContainsFileUpload());
		def.setBeanCreator(this::newBean);
		def.setWicketId(NEW_OR_EDIT_PANEL_ID);
		return def;
	}

	/**
	 * Creates a new instance of B.
	 * 
	 * @return a new B.
	 * 
	 * @deprecated Stop Overwriting this - use
	 *             {@link CrudPickerPanelDef#setBeanCreator(SerializableSupplier)}
	 */
	@Deprecated
	protected B newBean() {
		return getDefinition().getBeanCreator().get();
	}

	/**
	 * Hook intended to be overridden by the user if he chooses to set
	 * <code>readBeforeEdit</code> to true in the {@link CrudPickerPanelDef}.
	 * 
	 * @return Returns a fresh copy of B from the persistence layer.
	 * 
	 * @deprecated Stop Overwriting this - use
	 *             {@link CrudPickerPanelDef#setReadBean(SerializableUnaryOperator)}
	 */
	@Deprecated
	protected B readBean() { 
		if (getDefinition().getReadBean() == null) {
			throw new RuntimeException(
					"If you want to read before editing you MUST fill CrudPickerPanelDef#setReadBean ."); //$NON-NLS-1$
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
	 *             {@link CrudPickerPanelDef#setCopyBean(SerializableUnaryOperator)}
	 */
	@Deprecated
	protected B copyBean(B bean) {
		if (getDefinition().getCopyBean() == null) {
			return bean;
		}
		return getDefinition().getCopyBean().apply(bean);
	}

	/**
	 * Updates the model object and the list in case a new instance is created.
	 * <br>
	 * The case where the object equality between a list element and the
	 * oldInstance is true for more than one of the list elements is not
	 * checked. This should not happen.
	 *
	 * @param oldInstance
	 *            the old instance
	 * @param newInstance
	 *            the new instance
	 */
	void resynchDefinition(B oldInstance, B newInstance) {
		if (oldInstance == newInstance) {
			return; // nothing to do
		}

		List<B> list = getDefinition().getList();
		int index = list.indexOf(oldInstance);
		// an exception may be thrown here, if index=-1; this is intentional and
		// should never happen
		getDefinition().getList().set(index, newInstance);
	}

	/**
	 * Hides the Bean Panel.
	 * 
	 * @param target
	 */
	void hideBean(AjaxRequestTarget target) {
		target.add(beanPanel);
		beanPanel.setVisible(false);
	}

	/**
	 * Action executed when the user chooses to view an existing item.<br>
	 * This does :
	 * <ul>
	 * <li>Retrieve the currently selected item from the radioGroup model.</li>
	 * <li>(optional) Read the selected item from the persistence layer.</li>
	 * <li>Set this item as the model object of the beanModel of this
	 * definition</li>
	 * <li>Create a new SingleBeanPanel. In the definition of this panel, put
	 * the beanModel and a null beanAction. Also, disable the fields panel.</li>
	 * </ul>
	 * TODO: handle text areas here, so that users do not have to do custom bean
	 * panel disabling in order to add a 'readonly' attribute mod to TextAreas
	 * 
	 * @param target
	 * @param form
	 */
	private void doView(AjaxRequestTarget target, @SuppressWarnings("unused") Form<?> form) {
		B selection = radioGroup.getModelObject();
		if (selection == null) {
			return;
		}
		if (getDefinition().getReadBeforeEdit()) {
			getDefinition().getBeanModel().setObject(selection);
			selection = readBean();
		}
		getDefinition().getBeanModel().setObject(selection);
		Panel replacement = new SingleBeanPanel<>(createSingleBeanPanelDef(null, null, PanelCreatorMode.VIEW));
		checkSingleBeanPanelButtonsState(replacement);
		beanPanel.replaceWith(replacement);
		beanPanel = replacement;
		if (!getDefinition().getCustomSingleBeanPanelDisabling()) {
			Component sbpFieldsPanel = beanPanel.get(SingleBeanPanel.FORM_ID).get(SingleBeanPanel.FORM_FIELDS_PANEL_ID);
			sbpFieldsPanel.setEnabled(false);
		}
		beanPanel.setVisible(true);
		target.add(this);
	}

	/**
	 * Action executed when the user chooses to create a new item.<br>
	 * This does :
	 * <ul>
	 * <li>Create a new bean instance and set it as the model object of the
	 * beanModel.</li>
	 * <li>Create a new SingleBeanPanel. In the definition of this panel, put
	 * the beanModel, as well as the saveAction received from the client of the
	 * CrudPickerPanel wrapped in an NewItemAction that repaints the results
	 * table.</li>
	 * </ul>
	 * 
	 * @param target
	 * @param form
	 */
	private void createNewPanel(AjaxRequestTarget target, @SuppressWarnings("unused") Form<?> form) {
		if (!ServicePanelUtils.authorizedByFlag(getDefinition().getSaveActionFlag())) {
			error(getDefinition().getSaveActionFlag().getDownMessage());
			return;
		}
		getDefinition().getBeanModel().setObject(newBean());
		AjaxCondition<B> formValidator = getDefinition().getSaveValidator();
		SingleBeanPanelDef<B> sbpDef = createSingleBeanPanelDef(this::doCreate, formValidator, PanelCreatorMode.CREATE);
		Panel replacement = new SingleBeanPanel<>(sbpDef);
		checkSingleBeanPanelButtonsState(replacement);
		beanPanel.replaceWith(replacement);
		beanPanel = replacement;
		beanPanel.setVisible(true);
		target.add(this);
	}

	/**
	 * Action executed when the user chooses to edit an item.<br>
	 * This does :
	 * <ul>
	 * <li>Retrieve the currently selected item from the radioGroup model.
	 * </ul>
	 * <li>Copy the item, we do not want to add unsaved changes to our models.
	 * </ul>
	 * <li>(optional) Read the selected item from the persistence layer.
	 * </ul>
	 * <li>Set this item as the model object of the beanModel of this definition
	 * </ul>
	 * <li>Create a new SingleBeanPanel. In the definition of this panel, put
	 * the beanModel, as well as the updateAction received from the client of
	 * the CrudPickerPanel wrapped in an EditItemAction that repaints the
	 * results table.
	 * </li>
	 * </ul>
	 * 
	 * @param target
	 * @param form
	 */
	private void createEditPanel(AjaxRequestTarget target, @SuppressWarnings("unused") Form<?> form) {
		if (!ServicePanelUtils.authorizedByFlag(getDefinition().getUpdateActionFlag())) {
			error(getDefinition().getUpdateActionFlag().getDownMessage());
			return;
		}
		B picked = radioGroup.getModelObject();
		if (picked == null) {
			return;
		}
		AjaxCondition<B> preEditValidator = getDefinition().getPreEditValidator();
		if (preEditValidator != null && !preEditValidator.check(picked, target, this)) {
			return;
		}

		/*
		 * When copying we do not update the definition list. The point is to
		 * leave it intact. If the update succeeds the updated instance will be
		 * properly replaced. If not, the original instance will still be on the
		 * list.
		 */
		getDefinition().getBeanModel().setObject(copyBean(picked));

		B selected;
		/*
		 * the instance might be changed here, so we resynch
		 */
		if (getDefinition().getReadBeforeEdit()) {
			B read = readBean();
			getDefinition().getBeanModel().setObject(read);
			resynchDefinition(picked, read);
			selected = read;
		} else {
			selected = picked;
		}

		AjaxCondition<B> formValidator = getDefinition().getUpdateValidator();
		SingleBeanPanelDef<B> sbpDef = createSingleBeanPanelDef((t, b) -> doUpdate(selected, t, b), formValidator,
				PanelCreatorMode.EDIT);
		Panel replacement = new SingleBeanPanel<>(sbpDef);
		checkSingleBeanPanelButtonsState(replacement);
		beanPanel.replaceWith(replacement);
		beanPanel = replacement;
		beanPanel.setVisible(true);
		target.add(this);
	}

	/**
	 * Optionally hides the buttons of the {@link SingleBeanPanel}.
	 * 
	 * @param panel SingleBeanPanel
	 */
	private void checkSingleBeanPanelButtonsState(Panel panel) {
		if (getDefinition().getHideSingleBeanPanelButtons()) { 
			String pathExecute = WicketUtils.wicketPath(SingleBeanPanel.FORM_ID, SingleBeanPanel.EXEC_BUTTON_ID);
			String cancelExecute = WicketUtils.wicketPath(SingleBeanPanel.FORM_ID, SingleBeanPanel.BACK_BUTTON_ID);
			Component[] cmpArray = new Component[] {panel.get(pathExecute), panel.get(cancelExecute)};
			WicketUtils.setVisible(false, cmpArray);
		}
	}

	/**
	 * @param target
	 * @param bean
	 * @throws Exception 
	 */
	@SuppressWarnings("deprecation")
	void doCreate(AjaxRequestTarget target, B bean) throws Exception {
		ProcessAction<B> create = getDefinition().getSaveAction();
		B newItem;
		// backwards compatibility
		if (create instanceof gr.interamerican.wicket.callback.CallbackAction) {
			gr.interamerican.wicket.callback.CallbackAction callback = (gr.interamerican.wicket.callback.CallbackAction) create;
			callback.setCaller(this);
			callback.callBack(target, tableForm);
			newItem = getDefinition().getBeanModel().getObject();
		} else {
			newItem = create.process(bean);
		}
		beanPanel.setVisible(false);
		target.add(this);
		// backwards compatibility
		if (newItem == null) {
			return;
		}
		List<B> list = getDefinition().getList();
		if (!list.contains(newItem)) {
			list.add(newItem);
		}
		refreshTable(target, tableForm);
	}

	/**
	 * @param oldInstance 
	 * @param target
	 * @param bean
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	void doUpdate(B oldInstance, AjaxRequestTarget target, B bean) throws Exception {
		ProcessAction<B> update = getDefinition().getUpdateAction();
		B newInstance;
		// backwards compatibility
		if (update instanceof gr.interamerican.wicket.callback.CallbackAction) {
			gr.interamerican.wicket.callback.CallbackAction callback = (gr.interamerican.wicket.callback.CallbackAction) update;
			callback.setCaller(this);
			callback.callBack(target, tableForm);
			newInstance = getDefinition().getBeanModel().getObject();
		} else {
			newInstance = update.process(bean);
		}
		beanPanel.setVisible(false);
		target.add(this);
		// backwards compatibility
		if (newInstance == null) {
			return;
		}
		LegacyCallbackAction refreshAction = getDefinition().getRefreshAfterDataOpAction();
		if (refreshAction != null) {
			refreshAction.doInvoke(target);
		} else {
			resynchDefinition(oldInstance, newInstance);
		}
		refreshTable(target, tableForm);
	}

	/**
	 * Action executed choosing to delete an item.<br>
	 * This does :
	 * <ul>
	 * <li>Retrieve the currently selected item from the radioGroup model.</li>
	 * <li>Set this item as the model object of the beanModel of this
	 * definition</li>
	 * <li>If a deleteValidator is supplied, check against it. If it does not
	 * pass stop the form processing here.</li>
	 * <li>Clients of this panel that have supplied the beanModel, may retrieve
	 * the item to be deleted from it.</li>
	 * <li>Executes the delete action
	 * <li>
	 * <li>Removes the deleted item from the list (and the one selected as well
	 * for safety)</li>
	 * <li>After the deleteAction provided by the client has been performed, we
	 * repaint the results table</li>
	 * </ul>
	 * 
	 * @param target
	 *            The target
	 * @param form
	 * @throws Exception 
	 */
	@SuppressWarnings("deprecation")
	void doDelete(AjaxRequestTarget target, Form<?> form) throws Exception {
		if (!ServicePanelUtils.authorizedByFlag(getDefinition().getDeleteActionFlag())) {
			error(getDefinition().getDeleteActionFlag().getDownMessage());
			return;
		}
		B selection = radioGroup.getModelObject();
		if (selection == null) {
			return;
		}
		getDefinition().getBeanModel().setObject(selection);
		AjaxCondition<B> deleteValidator = getDefinition().getDeleteValidator();
		if (deleteValidator != null && !deleteValidator.check(selection, target, this)) {
			return;
		}
		Consume<B> delete = getDefinition().getDeleteAction();
		// backwards compatibility
		if (delete instanceof gr.interamerican.wicket.callback.CallbackAction) {
			gr.interamerican.wicket.callback.CallbackAction callback = (gr.interamerican.wicket.callback.CallbackAction) delete;
			callback.setCaller(this);
			callback.callBack(target, form);
		} else {
			delete.consume(selection);
		}
		// in case the beanPanel shows the deleted item.
		if (beanPanel != null) {
			beanPanel.setVisible(false);
		}
		List<B> list = getDefinition().getList();
		list.remove(getDefinition().getBeanModel().getObject());
		list.remove(selection);
		refreshTable(target, form);
		target.add(this);
	}
}