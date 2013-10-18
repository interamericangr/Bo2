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

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.callback.CallbackWrapper;
import gr.interamerican.wicket.condition.AjaxEnabledCondition;
import gr.interamerican.wicket.creators.PanelCreatorMode;
import gr.interamerican.wicket.markup.html.panel.ServicePanelUtils;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanel;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.bean.SingleBeanPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanel;
import gr.interamerican.wicket.markup.html.panel.searchFlow.SearchFlowPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.modifiers.JavascriptEventConfirmation;
import gr.interamerican.wicket.util.resource.StringResourceUtils;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;
import gr.interamerican.wicket.utils.WicketUtils;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * {@link ServicePanel} that shows a list of objects and allows
 * the user to pick one and/or perform CRUD operations on the list
 * (delete existing, edit existing, create new).
 * 
 * If the user does not define a <code>itemSelectedAction</code>
 * the pick capability is hidden. In this case, the radio buttons
 * are still useful for selecting the item to delete or edit. 
 * 
 * <p/>
 * This panel requires 5 {@link CallbackAction}s:<br/>
 * go back<br/>
 * select item<br/>
 * delete item<br/>
 * save new item<br/>
 * save edited item<br/>
 * 
 * @param <B> 
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
	 * Class of bean B.
	 */
	private Class<B> beanClass;
	
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
	 * @param definition
	 */
	public CrudPickerPanel(CrudPickerPanelDef<B> definition) {
		super(definition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CrudPickerPanelDef<B> getDefinition() {
		return (CrudPickerPanelDef<B>) definition;
	}

	@SuppressWarnings({ "serial", "nls", "unchecked" })
	@Override
	protected void init() {
		super.init();
		
		beanClass = (Class<B>) getDefinition().getBeanModel().getObject().getClass();
		
		IModel<String> deleteLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_DELETE_BTN_LABEL, this, getDefinition().getDeleteLabelModel(), "Delete"); 
		IModel<String> newLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_NEW_BTN_LABEL, this, getDefinition().getNewLabelModel(), "New");
		IModel<String> editLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_EDIT_BTN_LABEL, this, getDefinition().getEditLabelModel(), "Edit");
		IModel<String> viewLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.CPP_VIEW_BTN_LABEL, this, getDefinition().getViewLabelModel(), "View");
		
		CallbackAction deleteAction = getDefinition().getDeleteAction();
		CallbackAction updateAction = getDefinition().getUpdateAction();
		CallbackAction saveAction = getDefinition().getSaveAction();
		
		/*
		 * We will set the caller of the Edit/New/Delete actions as this panel.
		 * We will not allow the SingleBeanPanel to set itself as the caller of
		 * the Edit/Delete actions, because we need to be able to hide it in case
		 * of a DataException and still show the feedback message. 
		 */
		if(deleteAction!=null) {
			deleteAction.setCaller(this);
		}
		if(updateAction!=null) {
			updateAction.setCaller(this);
		}
		if(saveAction!=null) {
			saveAction.setCaller(this);
		}
		
		/*
		 * 1. Retrieve the currently selected item from the radioGroup model.
		 * 2. Set this item as the model object of the beanModel of this definition
		 * 3. If a deleteValidator is supplied, check against it. If it does not pass
		 *    stop the form processing here.
		 * 3. Clients of this panel that have supplied the beanModel, may retrieve
		 *    the item to be deleted from it.
		 * 4. After the deleteAction provided by the client has been performed, the
		 *    EditAction will repaint the results table.
		 */
		deleteButton = new CallbackAjaxButton(DELETE_BUTTON_ID, 
			deleteLabel, new RefreshTableAction(new DeleteItemAction(deleteAction)), getFeedBackPanel()) {		
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(!ServicePanelUtils.authorizedByFlag(getDefinition().getDeleteActionFlag())) {
					target.addComponent(feedBackPanel);
					CrudPickerPanel.this.error(getDefinition().getDeleteActionFlag().getDownMessage());
					return;
				}
				B selection = radioGroup.getModelObject();
				if(selection == null) { return; }
				getDefinition().getBeanModel().setObject(selection);
				AjaxEnabledCondition<B> deleteValidator = getDefinition().getDeleteValidator();
				if(deleteValidator!=null) {
					if(deleteValidator.check(selection, target)) {
						super.onSubmit(target, form);
					}
				} else {
					super.onSubmit(target, form);
				}
			}
		};
		if(getDefinition().getRequestConfirmOnDelete()) {
			AttributeModifier deleteConfirmation = new JavascriptEventConfirmation("onclick", "Are you sure?");
			deleteButton.add(deleteConfirmation);
		}
		ServicePanelUtils.disableButton(getDefinition(), getDefinition().getDeleteActionFlag(), deleteButton);
		
		/*
		 * 1. Retrieve the currently selected item from the radioGroup model.
		 * 2. Copy the item, we do not want to add unsaved changes to our models.
		 * 3. (optional) Read the selected item from the persistence layer.
		 * 4. Set this item as the model object of the beanModel of this definition
		 * 5. Create a new SingleBeanPanel. In the definition of this panel, put
		 *    the beanModel, as well as the updateAction received from the client
		 *    of the CrudPickerPanel wrapped in an EditItemAction that repaints
		 *    the results table.
		 */
		editButton = new AjaxButton(EDIT_BUTTON_ID, editLabel) {		
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(!ServicePanelUtils.authorizedByFlag(getDefinition().getUpdateActionFlag())) {
					target.addComponent(feedBackPanel);
					CrudPickerPanel.this.error(getDefinition().getUpdateActionFlag().getDownMessage());
					return;
				}
				B selection = radioGroup.getModelObject();
				if(selection == null) { return; }
				AjaxEnabledCondition<B> preEditValidator = getDefinition().getPreEditValidator();
				if(preEditValidator !=null && !preEditValidator.check(selection, target)) {
					return;
				}
				selection = copyBean(selection);
				getDefinition().getBeanModel().setObject(selection);
				if(getDefinition().getReadBeforeEdit()) {
					selection = readBean();
					getDefinition().getBeanModel().setObject(selection);
				}
				AjaxEnabledCondition<B> formValidator = getDefinition().getUpdateValidator();
				SingleBeanPanelDef<B> sbpDef = createSingleBeanPanelDef(
						new RefreshTableAction(new EditItemAction(getDefinition().getUpdateAction())), formValidator, PanelCreatorMode.EDIT);
				Panel replacement = new BeanPanel(sbpDef);
				checkSingleBeanPanelButtonsState(replacement);
				beanPanel.replaceWith(replacement);
				beanPanel = replacement;
				beanPanel.setVisible(true);
				target.addComponent(CrudPickerPanel.this);
			}
		};
		
		ServicePanelUtils.disableButton(getDefinition(), getDefinition().getUpdateActionFlag(), editButton);
		
		/*
		 * 1. Retrieve the currently selected item from the radioGroup model.
		 * 2. (optional) Read the selected item from the persistence layer.
		 * 3. Set this item as the model object of the beanModel of this definition
		 * 4. Create a new SingleBeanPanel. In the definition of this panel, put
		 *    the beanModel and a null beanAction. Also, disable the fields panel.
		 *    
		 * TODO: handle text areas here, so that users do not have to do custom
		 *       bean panel disabling in order to add a 'readonly' attribute mod
		 *       to TextAreas
		 */
		viewButton = new AjaxButton(VIEW_BUTTON_ID, viewLabel) {		
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				B selection = radioGroup.getModelObject();
				if(selection == null) { return; }
				if(getDefinition().getReadBeforeEdit()) {
					getDefinition().getBeanModel().setObject(selection);
					selection = readBean();
				}
				getDefinition().getBeanModel().setObject(selection);
				Panel replacement = new BeanPanel(createSingleBeanPanelDef(null, null, PanelCreatorMode.VIEW));
				checkSingleBeanPanelButtonsState(replacement);
				beanPanel.replaceWith(replacement);
				beanPanel = replacement;
				if(!getDefinition().getCustomSingleBeanPanelDisabling()) {
					Form<B> sbpForm = (Form<B>) beanPanel.get(SingleBeanPanel.FORM_ID);
					Panel sbpFieldsPanel = (Panel) sbpForm.get(SingleBeanPanel.FORM_FIELDS_PANEL_ID); 
					sbpFieldsPanel.setEnabled(false);
				}
				beanPanel.setVisible(true);
				target.addComponent(CrudPickerPanel.this);
			}
		};
		
		/*
		 * 1. Create a new bean instance and set it as the model object of the beanModel.
		 * 2. Create a new SingleBeanPanel. In the definition of this panel, put
		 *    the beanModel, as well as the saveAction received from the client
		 *    of the CrudPickerPanel wrapped in an NewItemAction that repaints
		 *    the results table.
		 */
		newButton = new AjaxButton(NEW_BUTTON_ID, newLabel) {		
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(!ServicePanelUtils.authorizedByFlag(getDefinition().getSaveActionFlag())) {
					target.addComponent(feedBackPanel);
					CrudPickerPanel.this.error(getDefinition().getSaveActionFlag().getDownMessage());
					return;
				}
				getDefinition().getBeanModel().setObject(newBean());
				AjaxEnabledCondition<B> formValidator = getDefinition().getSaveValidator();
				SingleBeanPanelDef<B> sbpDef = createSingleBeanPanelDef(
						new RefreshTableAction(new NewItemAction(getDefinition().getSaveAction())), formValidator, PanelCreatorMode.CREATE);
				Panel replacement = new BeanPanel(sbpDef);
				checkSingleBeanPanelButtonsState(replacement);
				beanPanel.replaceWith(replacement);
				beanPanel = replacement;
				beanPanel.setVisible(true);
				target.addComponent(CrudPickerPanel.this);
			}
		};
		
		ServicePanelUtils.disableButton(getDefinition(), getDefinition().getSaveActionFlag(), newButton);
		
		beanPanel = new EmptyPanel(NEW_OR_EDIT_PANEL_ID);
		
		if(getDefinition().getSingleBeanFormContainsFileUpload()) {
			tableForm.setMultiPart(true);
			tableForm.add(new AttributeModifier("enctype", new Model<String>("multipart/form-data")));
		}
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
		boolean needsFieldsPanelCreator = (getDefinition().getUpdateAction()!=null) || (getDefinition().getSaveAction()!=null);
		if(needsFieldsPanelCreator && getDefinition().getBeanFieldsPanelCreator()==null) {
			String msg = "Cannot create a CrudPicker panel with update/create capabilities without a beanFieldsPanelCreator.";
			throw new RuntimeException(msg);
		}
		if(needsFieldsPanelCreator  && getDefinition().getBeanModel()==null) {
			String msg = "Cannot create a CrudPicker panel with update/create capabilities with a null beanModel.";
			throw new RuntimeException(msg);
		}
		if(needsFieldsPanelCreator  && getDefinition().getBeanModel().getObject()==null) {
			String msg = "Cannot create a CrudPicker panel with update/create capabilities with a null beanModel object.";
			throw new RuntimeException(msg);
		}
		if(getDefinition().getReadBeforeEdit()==null) {
			getDefinition().setReadBeforeEdit(false);
		}
		if(getDefinition().getRequestConfirmOnDelete() == null) {
			getDefinition().setRequestConfirmOnDelete(false);
		}
		if(getDefinition().getViewEnabled()==null) {
			getDefinition().setViewEnabled(false);
		}
		if(getDefinition().getSingleBeanFormContainsFileUpload()==null) {
			getDefinition().setSingleBeanFormContainsFileUpload(false);
		}
		if(getDefinition().getCustomSingleBeanPanelDisabling()==null) {
			getDefinition().setCustomSingleBeanPanelDisabling(false);
		}
		if(getDefinition().getHideSingleBeanPanelButtons() == null) {
			getDefinition().setHideSingleBeanPanelButtons(false);
		}
		super.validateDef();
	}
	
	/**
	 * Creates the {@link SingleBeanPanelDef}. There is no need to
	 * propagate any authorization related flags, as they are applied
	 * to the buttons that call this anyway.
	 * 
	 * @param beanAction 
	 * @param validator 
	 * @param mode 
	 * 
	 * @return the {@link SingleBeanPanelDef}.
	 */
	protected SingleBeanPanelDef<B> createSingleBeanPanelDef(
	CallbackAction beanAction, AjaxEnabledCondition<B> validator, PanelCreatorMode mode) {
		
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
		
		SingleBeanPanelDef<B> def = new SingleBeanPanelDefImpl<B>();
		def.setBackAction(new HideBeanPanelAction());
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
		def.setWicketId(NEW_OR_EDIT_PANEL_ID);
		return def;
	}
	
	/**
	 * Creates a new instance of B. There is an assumption that the runtime class
	 * of B will have a default constructor. If this is not the case, the default
	 * implementation will throw a RuntimeException. If this happens, this method
	 * must be overridden.
	 * 
	 * @return a new B.
	 */
	@SuppressWarnings("nls")
	protected B newBean() {
		try {
			B instance = ReflectionUtils.newInstance(beanClass);
			return instance;
		} catch (RuntimeException re) {
			String msg = "Could not create a new instance of " + beanClass.getName()
						+" . You have to override newBean().";
			throw new RuntimeException(msg, re);
		}
	}
	
	/**
	 * Hook intended to be overridden by the user if
	 * he chooses to set <code>readBeforeEdit</code>
	 * to true in the {@link SearchFlowPanelDef}.
	 * 
	 * @return Returns a fresh copy of B from the 
	 *         persistence layer.
	 */
	@SuppressWarnings("nls")
	protected B readBean() { 
		String msg = "If you want to read before editing you MUST override readBean().";
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
	 * Wrapper around the delete action that is supplied by the service
	 * panel client. This wrapper is responsible for repainting the 
	 * panel's data table with the updated data.
	 * 
	 * Note that the delete action will find the item to be deleted
	 * in the service panel definition, <code>selectedItem</code>.
	 */
	private class DeleteItemAction extends CallbackWrapper {
		
		/**
		 * Creates a new CrudPickerPanel.DeleteItemAction object. 
		 * @param action 
		 */
		public DeleteItemAction(CallbackAction action) {
			super(action);
		} 

		@Override public void after() {
			/*
			 * in case the beanPanel shows the deleted item.
			 */
			if(beanPanel!=null) { beanPanel.setVisible(false); }
			B item = getDefinition().getBeanModel().getObject();
			if(item == null) { return; }
			List<B> list = getDefinition().getList();
			if(list.contains(item)) {
				list.remove(item);
			}
		}		
	}
	
	/**
	 * Wrapper around the edit action that is supplied by the service
	 * panel client. This wrapper is responsible for repainting the 
	 * panel's data table with the updated data.
	 * 
	 * Note that the edit action will find the needed data
	 * in the service panel definition, <code>selectedItem</code>.
	 */
	private class EditItemAction extends CallbackWrapper {
		
		/**
		 * Creates a new CrudPickerPanel.DeleteItemAction object. 
		 * @param action 
		 */
		public EditItemAction(CallbackAction action) {
			super(action);
		} 

		@Override public void after(AjaxRequestTarget target) {
			/*
			 * REMOVED to distribute other changes. Work in progress.
			 */
//			if(CrudPickerPanel.this.getFeedBackPanel().anyErrorMessage()) {
//				return;
//			}
			B item = getDefinition().getBeanModel().getObject();
			beanPanel.setVisible(false);
			if(item == null) { return; }
			CallbackAction refreshAction = getDefinition().getRefreshAfterDataOpAction();
			if(refreshAction != null) {
				 refreshAction.callBack(target);
				 return;
			}
			List<B> list = getDefinition().getList();
			if(list.contains(item)) {
				int idx = list.indexOf(item);
				list.set(idx, item);
			}
		}
	}
	
	/**
	 * Wrapper around the new item action that is supplied by the service
	 * panel client. This wrapper is responsible for repainting the 
	 * panel's data table with the updated data.
	 * 
	 */
	private class NewItemAction extends CallbackWrapper {
		
		/**
		 * Creates a new CrudPickerPanel.NewItemAction object.
		 * 
		 * @param action 
		 */
		public NewItemAction(CallbackAction action) {
			super(action);
		} 

		@Override public void after() {
			B item = getDefinition().getBeanModel().getObject();
			beanPanel.setVisible(false);
			if(item == null) { return; }
			List<B> list = getDefinition().getList();
			if(!list.contains(item)) {
				list.add(item);
			}
		}		
	}
	
	/**
	 * Hides the <code>newOrEditPanel</code>.
	 */
	private class HideBeanPanelAction implements CallbackAction {

		public void callBack(AjaxRequestTarget target) { hide(target); }
		public void callBack(AjaxRequestTarget target, Form<?> form) { hide(target); }
		public void setCaller(Component caller) { /* empty */ }
		public Component getCaller() { return CrudPickerPanel.this; }

		/**
		 * Hide the bean panel.
		 * @param target
		 */
		private void hide(AjaxRequestTarget target) {
			target.addComponent(beanPanel);
			beanPanel.setVisible(false);
		}
	}
	
	/**
	 * SingleBeanPanel.
	 */
	private class BeanPanel extends SingleBeanPanel<B> {
		/**
		 * serialVersionUID.
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * Construct.
		 * @param definition 
		 */
		public BeanPanel(SingleBeanPanelDef<B> definition) {
			super(definition);
		}
		@Override
		protected B newBean() {
			return CrudPickerPanel.this.newBean();
		}
	}

}
