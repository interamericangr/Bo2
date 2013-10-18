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
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

/**
 * {@link ServicePanelDef} of {@link CrudPickerPanel}.
 * 
 * @param <B> 
 */
public interface CrudPickerPanelDef
<B extends Serializable>
extends PickerPanelDef<B> {
	
	/*
	 * Component factories
	 */
	
	/**
	 * Gets the panel creator.
	 * 
	 * @return Returns the panel creator.
	 */
	public PanelCreator<B> getBeanFieldsPanelCreator();
	
	/**
	 * [OPTIONAL]
	 * Sets the panel creator. The panel creator creates a panel
	 * that contains the fields of the bean form. This is useful
	 * only if the updateAction or the saveAction are not null.
	 * 
	 * @param panelCreator 
	 *        The panelCreator to set.
	 */
	public void setBeanFieldsPanelCreator(PanelCreator<B> panelCreator);
	
	/*************************************************************************/
	/*
	 * Actions, flags, conditions
	 */
	
	/**
	 * Gets the deleteAction.
	 * 
	 * @return Returns the deleteAction.
	 */
	public CallbackAction getDeleteAction();
	
	/**
	 * [OPTIONAL]
	 * Assigns a new value to the deleteAction {@link CallbackAction}.
	 * If this is null, there will be no visible delete button.
	 * 
	 * This action should delete the selected bean (the one contained
	 * in this definition's model). If, for some reason, the deleteAction
	 * decides NOT to delete the selected bean, the object of the beanModel
	 * should be explicitly set to null by the deleteAction.
	 * 
	 * @param deleteAction
	 */
	public void setDeleteAction(CallbackAction deleteAction);
	
	/**
	 * Gets the deleteActionFlag.
	 * 
	 * @return Returns the deleteActionFlag.
	 */
	public Flag getDeleteActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the deleteActionFlag.
	 * 
	 * @param deleteActionFlag
	 */
	public void setDeleteActionFlag(Flag deleteActionFlag);
	
	/**
	 * Gets the updateAction
	 *
	 * @return Returns the updateAction.
	 */
	public CallbackAction getUpdateAction();

	/**
	 * [OPTIONAL]
	 * Assigns a new value to the updateAction {@link CallbackAction}.
	 * If this is null, there will be no visible update button.
	 * 
	 * This action should update the selected bean (the one contained
	 * in this definition's model). If, for some reason, the updateAction
	 * decides NOT to update the selected bean, the object of the beanModel
	 * should be explicitly set to null by the updateAction.
	 *
	 * @param updateAction the updateAction to set
	 */
	public void setUpdateAction(CallbackAction updateAction);
	
	/**
	 * Gets the updateActionFlag.
	 * 
	 * @return Returns the updateActionFlag.
	 */
	public Flag getUpdateActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the updateActionFlag.
	 * 
	 * @param updateActionFlag
	 */
	public void setUpdateActionFlag(Flag updateActionFlag);
	
	/**
	 * Gets the saveAction
	 * 
	 *
	 * @return Returns the saveAction
	 */
	public CallbackAction getSaveAction();

	/**
	 * [OPTIONAL]
	 * Assigns a new value to the saveAction {@link CallbackAction}.
	 * If this is null, there will be no visible save new button. 
	 * If, for some reason, the saveAction decides NOT to save the 
	 * new bean, the object of the beanModel should be explicitly set 
	 * to null by the saveAction.
	 * 
	 * This action should store a new bean.
	 *
	 * @param saveAction the saveAction to set
	 */
	public void setSaveAction(CallbackAction saveAction);
	
	/**
	 * Gets the saveActionFlag.
	 * 
	 * @return Returns the saveActionFlag.
	 */
	public Flag getSaveActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the saveActionFlag.
	 * 
	 * @param saveActionFlag
	 */
	public void setSaveActionFlag(Flag saveActionFlag);
	
	/**
	 * [OPTIONAL]
	 * Sets the validator that is executed when storing a new B.
	 * 
	 * @param validator
	 */
	public void setSaveValidator(AjaxEnabledCondition<B> validator);
	
	/**
	 * Gets the validator that is executed when storing a new B.
	 * 
	 * @return validator.
	 */
	public AjaxEnabledCondition<B> getSaveValidator();
	
	/**
	 * [OPTIONAL]
	 * Sets the validator that is executed when updating an existing B.
	 * 
	 * @param validator
	 */
	public void setUpdateValidator(AjaxEnabledCondition<B> validator);
	
	/**
	 * Gets the validator that is executed when updating an existing B.
	 * 
	 * @return validator.
	 */
	public AjaxEnabledCondition<B> getUpdateValidator();
	
	/**
	 * [OPTIONAL]
	 * Sets the validator that is executed when selecting an existing B for update.
	 * 
	 * @param validator
	 */
	public void setPreEditValidator(AjaxEnabledCondition<B> validator);
	
	/**
	 * Gets the validator that is executed when selecting an existing B for update.
	 * 
	 * @return validator.
	 */
	public AjaxEnabledCondition<B> getPreEditValidator();
	
	/**
	 * [OPTIONAL]
	 * Sets the validator that is executed when deleting an existing B.
	 * 
	 * @param validator
	 */
	public void setDeleteValidator(AjaxEnabledCondition<B> validator);
	
	/**
	 * Gets the validator that is executed when deleting an existing B.
	 * 
	 * @return validator.
	 */
	public AjaxEnabledCondition<B> getDeleteValidator();
	
	/*************************************************************************/
	
	/*
	 * Options
	 */
	
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
	 * @param requestConfirmOnDelete
	 */
	void setRequestConfirmOnDelete(Boolean requestConfirmOnDelete);
	
	/**
	 * Gets whether the single bean panel form includes a file
	 * upload item. 
	 * 
	 * @return whether the single bean panel form includes a file upload item.
	 */
	Boolean getSingleBeanFormContainsFileUpload();
	
	/**
	 * [OPTIONAL]
	 * Sets whether the single bean panel form includes a file upload item.
	 * If this is set to true, the single bean panel form is set as 
	 * <code>setMultipart(true)</code>. Defaults to false.
	 * 
	 * @param singleBeanFormContainsFileUpload
	 */
	void setSingleBeanFormContainsFileUpload(Boolean singleBeanFormContainsFileUpload);
	
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
	public void setReadBeforeEdit(Boolean readBeforeEdit);
	
	/**
	 * Gets the readBeforeEdit.
	 * 
	 * @return Returns the readBeforeEdit.
	 */
	public Boolean getReadBeforeEdit();
	
	/**
	 * Gets the viewEnabled.
	 * 
	 * @return Returns the viewEnabled.
	 */
	public Boolean getViewEnabled();
	
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
	public void setViewEnabled(Boolean viewEnabled);
	
	/**
	 * Gets the refreshAfterDataOpAction.
	 * 
	 * @return Returns the refreshAfterDataOpAction.
	 */
	public CallbackAction getRefreshAfterDataOpAction();
	
	/**
	 * [OPTIONAL]
	 * Assigns a new value to the refreshAfterDataOpAction {@link CallbackAction}.
	 * If this is not null, this will be executed after updating or adding a row.
	 * 
	 * This action should refresh the results list from the persistence layer and
	 * repaint the crudpickerpanel results.
	 * 
	 * @param refreshAfterDataOpAction
	 */
	public void setRefreshAfterDataOpAction(CallbackAction refreshAfterDataOpAction);
	
	/**
	 * [OPTIONAL]
	 * Gets whether the user will handle manually the disabling of the SingleBeanPanel
	 * when in view mode. This is useful when some components of the panel need to remain 
	 * enabled, for example tabs or download links.
	 * The default is false.
	 * 
	 * @see ModeAwareBeanPanelDef
	 * 
	 * @return Returns customSingleBeanPanelDisabling.
	 */
	Boolean getCustomSingleBeanPanelDisabling();
	
	/**
	 * Sets whether the user will handle manually the disabling of the SingleBeanPanel
	 * when in view mode.
	 * 
	 * @see #getCustomSingleBeanPanelDisabling()
	 * 
	 * @param customSingleBeanPanelDisabling
	 */
	void setCustomSingleBeanPanelDisabling(Boolean customSingleBeanPanelDisabling);
	
	/*************************************************************************/
	
	/*
	 * Resource models.
	 */
	
	/**
	 * Gets the delete button label.
	 * 
	 * @return delete button label.
	 */
	public IModel<String> getDeleteLabelModel();
	
	/**
	 * [OPTIONAL] Sets the delete button label.
	 * 
	 * @param label
	 */
	public void setDeleteLabelModel(IModel<String> label);
	
	/**
	 * Gets the new button label.
	 * 
	 * @return new button label.
	 */
	public IModel<String> getNewLabelModel();
	
	/**
	 * [OPTIONAL] Sets the new button label.
	 * 
	 * @param label
	 */
	public void setNewLabelModel(IModel<String> label);
	
	/**
	 * Gets the update button label.
	 * update
	 * @return save button label.
	 */
	public IModel<String> getEditLabelModel();
	
	/**
	 * [OPTIONAL] Sets the update button label.
	 * 
	 * @param label
	 */
	public void setEditLabelModel(IModel<String> label);
	
	/**
	 * Gets the view button label.
	 * 
	 * @return view button label.
	 */
	public IModel<String> getViewLabelModel();
	
	/**
	 * [OPTIONAL] Sets the view button label.
	 * 
	 * @param label
	 */
	public void setViewLabelModel(IModel<String> label);
	
	/**
	 * Gets the save button label.
	 * 
	 * @return save button label.
	 */
	public IModel<String> getSaveLabelModel();
	
	/**
	 * [OPTIONAL] Sets the save button label.
	 * The save button belongs to the SingleBeanPanel
	 * that is used to store a new B.
	 * 
	 * @param label
	 */
	public void setSaveLabelModel(IModel<String> label);
	
	/**
	 * Gets the update button label.
	 * 
	 * @return update button label.
	 */
	public IModel<String> getUpdateLabelModel();
	
	/**
	 * [OPTIONAL] Sets the update button label.
	 * The update button belongs to the SingleBeanPanel
	 * that is used to update a B.
	 * 
	 * @param label
	 */
	public void setUpdateLabelModel(IModel<String> label);
	
	/**
	 * Gets the clear button label.
	 * 
	 * @return clear button label.
	 */
	public IModel<String> getClearLabelModel();
	
	/**
	 * [OPTIONAL] Sets the clear button label.
	 * The clear button belongs to the SingleBeanPanel
	 * that is used to update/store a B.
	 * 
	 * @param label
	 */
	public void setClearLabelModel(IModel<String> label);
		
	/**
	 * Gets the back label of the SingleBeanPanel.
	 * 
	 * @return SingleBeanPanel back label.
	 */
	public IModel<String> getBeanFieldsPanelBackLabelModel();
	
	/**
	 * [OPTIONAL]
	 * Sets the back label of the SingleBeanPanel.
	 * 
	 * @param backLabel
	 */
	public void setBeanFieldsPanelBackLabelModel(IModel<String> backLabel);

	/**
	 * Gets the label of the SingleBeanPanel.
	 * 
	 * @return SingleBeanPanel label.
	 */
	public IModel<String> getSingleBeanPanelLabelModel();
	
	/**
	 * [OPTIONAL]
	 * Sets the label (fieldset legend) of the SingleBeanPanel.
	 * 
	 * @param label
	 */
	public void setSingleBeanPanelLabelModel(IModel<String> label);
	
	/**
	 * [OPTIONAL]
	 * Sets the option to hide the buttons of a {@link SingleBeanPanel}.
	 * 
	 * @param hideSingleBeanPanelButtons
	 */
	public void setHideSingleBeanPanelButtons(Boolean hideSingleBeanPanelButtons);
	
	/**
	 * [OPTIONAL]
	 * Gets the option to hide the buttons of a {@link SingleBeanPanel}.
	 * 
	 * @return hideSingleBeanPanelButtons
	 */
	public Boolean getHideSingleBeanPanelButtons();
}
