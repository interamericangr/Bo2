/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General License for more details.
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
import gr.interamerican.wicket.markup.html.panel.picker.PickerPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ModeAwareBeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

/**
 * {@link ServicePanelDef} of {@link CrudPickerPanel}.
 *
 * @param <B> the generic type
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
	PanelCreator<B> getBeanFieldsPanelCreator();
	
	/**
	 * [OPTIONAL]
	 * Sets the panel creator. The panel creator creates a panel
	 * that contains the fields of the bean form. This is useful
	 * only if the updateAction or the saveAction are not null.
	 * 
	 * @param panelCreator 
	 *        The panelCreator to set.
	 */
	void setBeanFieldsPanelCreator(PanelCreator<B> panelCreator);
	
	/**
	 * **********************************************************************.
	 *
	 * @return the delete action
	 */
	/*
	 * Actions, flags, conditions
	 */
	
	/**
	 * Gets the deleteAction.
	 * 
	 * @return Returns the deleteAction.
	 */
	Consume<B> getDeleteAction();
	
	/**
	 * [OPTIONAL]<br>
	 * Assigns a new value to the deleteAction {@link Consume}. If this is null,
	 * there will be no visible delete button.
	 * 
	 * This action should delete the input bean.<br>
	 * If, for some reason, the deleteAction decides NOT to delete the selected
	 * bean then an {@link Exception} should be thrown (the message will be
	 * displayed inside the CrudPickerPanel).<br>
	 * When you want to manually display an message on the parent panel, then
	 * use {@link #setDeleteValidator(AjaxCondition)}
	 *
	 * @param deleteAction
	 *            the new delete action
	 * @see #setDeleteValidator(AjaxCondition)
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
	 */
	ProcessAction<B> getUpdateAction();

	/**
	 * [OPTIONAL]<br>
	 * Assigns a new value to the updateAction {@link ProcessAction}. If this is
	 * null, there will be no visible update button.<br>
	 * 
	 * This action should update the input bean<br>
	 * If, for some reason, the updateAction decides NOT to update the selected
	 * bean then an {@link Exception} should be thrown (the message will be
	 * displayed inside the {@link CrudPickerPanel}).<br>
	 * When you want to manually display an message on the parent panel, then
	 * use {@link #setUpdateValidator(AjaxCondition)}.<br>
	 * Previous behavior when null is returned by this (or set to bean model of
	 * this definition) is kept for now, but will be <b>REMOVED</b> at some
	 * point.
	 *
	 * @param updateAction
	 *            the updateAction to set
	 * @see #setUpdateValidator(AjaxCondition)
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
	 */
	ProcessAction<B> getSaveAction();

	/**
	 * [OPTIONAL]<br>
	 * Assigns a new value to the saveAction {@link ProcessAction}. If this is
	 * null, there will be no visible save button.<br>
	 * 
	 * This action should save the input bean<br>
	 * If, for some reason, the saveAction decides NOT to save the selected
	 * bean then an {@link Exception} should be thrown (the message will be
	 * displayed inside the {@link CrudPickerPanel}).<br>
	 * When you want to manually display an message on the parent panel, then
	 * use {@link #setSaveValidator(AjaxCondition)}.<br>
	 * Previous behavior when null is returned by this (or set to bean model of
	 * this definition) is kept for now, but will be <b>REMOVED</b> at some
	 * point.
	 *
	 * @param saveAction
	 *            the saveAction to set
	 * @see #setSaveValidator(AjaxCondition)
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
	 * Sets the validator that is executed when selecting an existing B for update.
	 *
	 * @param validator the new pre edit validator
	 */
	void setPreEditValidator(AjaxCondition<B> validator);
	
	/**
	 * Gets the validator that is executed when selecting an existing B for update.
	 * 
	 * @return validator.
	 */
	AjaxCondition<B> getPreEditValidator();
	
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
	 * **********************************************************************.
	 *
	 * @return the request confirm on delete
	 */
	
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
	 * @param requestConfirmOnDelete the new request confirm on delete
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
	 * @param singleBeanFormContainsFileUpload the new single bean form contains file upload
	 */
	void setSingleBeanFormContainsFileUpload(Boolean singleBeanFormContainsFileUpload);
	
	/**
	 * [OPTIONAL]
	 * Reads the selected bean from the persistence layer before
	 * editing. This is useful when a CrudPickerPanel has to work with a
	 * PersistentObject that is not read with a HibernateWorker, but instead has
	 * only some of its properties set that were retrieved by a JdbcQuery. Note
	 * that all of the key properties of the PersistentObhect HAVE to be read.
	 * Defaults to false.<br>
	 * If you set this to true - you also have to set
	 * {@link #setReadBean(SerializableUnaryOperator)}.
	 * 
	 * @param readBeforeEdit
	 *            The readBeforeEdit to set.
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
	 * Gets the refreshAfterDataOpAction.
	 * 
	 * @return Returns the refreshAfterDataOpAction.
	 */
	LegacyCallbackAction getRefreshAfterDataOpAction();
	
	/**
	 * [OPTIONAL]
	 * Assigns a new value to the refreshAfterDataOpAction {@link LegacyCallbackAction}.
	 * If this is not null, this will be executed after updating or adding a row.
	 * 
	 * This action should refresh the results list from the persistence layer and
	 * repaint the crudpickerpanel results.
	 *
	 * @param refreshAfterDataOpAction the new refresh after data op action
	 */
	void setRefreshAfterDataOpAction(LegacyCallbackAction refreshAfterDataOpAction);
	
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
	 * when in view mode.
	 *
	 * @param customSingleBeanPanelDisabling the new custom single bean panel disabling
	 * @see #getCustomSingleBeanPanelDisabling()
	 */
	void setCustomSingleBeanPanelDisabling(Boolean customSingleBeanPanelDisabling);
	
	/**
	 * **********************************************************************.
	 *
	 * @return the delete label model
	 */
	
	/*
	 * Resource models.
	 */
	
	/**
	 * Gets the delete button label.
	 * 
	 * @return delete button label.
	 */
	IModel<String> getDeleteLabelModel();
	
	/**
	 * [OPTIONAL] Sets the delete button label.
	 *
	 * @param label the new delete label model
	 */
	void setDeleteLabelModel(IModel<String> label);
	
	/**
	 * Gets the new button label.
	 * 
	 * @return new button label.
	 */
	IModel<String> getNewLabelModel();
	
	/**
	 * [OPTIONAL] Sets the new button label.
	 *
	 * @param label the new new label model
	 */
	void setNewLabelModel(IModel<String> label);
	
	/**
	 * Gets the update button label.
	 * update
	 * @return save button label.
	 */
	IModel<String> getEditLabelModel();
	
	/**
	 * [OPTIONAL] Sets the update button label.
	 *
	 * @param label the new edits the label model
	 */
	void setEditLabelModel(IModel<String> label);
	
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
	 * Gets the save button label.
	 * 
	 * @return save button label.
	 */
	IModel<String> getSaveLabelModel();
	
	/**
	 * [OPTIONAL] Sets the save button label.
	 * The save button belongs to the SingleBeanPanel
	 * that is used to store a new B.
	 *
	 * @param label the new save label model
	 */
	void setSaveLabelModel(IModel<String> label);
	
	/**
	 * Gets the update button label.
	 * 
	 * @return update button label.
	 */
	IModel<String> getUpdateLabelModel();
	
	/**
	 * [OPTIONAL] Sets the update button label.
	 * The update button belongs to the SingleBeanPanel
	 * that is used to update a B.
	 *
	 * @param label the new update label model
	 */
	void setUpdateLabelModel(IModel<String> label);
	
	/**
	 * Gets the clear button label.
	 * 
	 * @return clear button label.
	 */
	IModel<String> getClearLabelModel();
	
	/**
	 * [OPTIONAL] Sets the clear button label.
	 * The clear button belongs to the SingleBeanPanel
	 * that is used to update/store a B.
	 *
	 * @param label the new clear label model
	 */
	void setClearLabelModel(IModel<String> label);
		
	/**
	 * Gets the back label of the SingleBeanPanel.
	 * 
	 * @return SingleBeanPanel back label.
	 */
	IModel<String> getBeanFieldsPanelBackLabelModel();
	
	/**
	 * [OPTIONAL]
	 * Sets the back label of the SingleBeanPanel.
	 *
	 * @param backLabel the new bean fields panel back label model
	 */
	void setBeanFieldsPanelBackLabelModel(IModel<String> backLabel);

	/**
	 * Gets the label of the SingleBeanPanel.
	 * 
	 * @return SingleBeanPanel label.
	 */
	IModel<String> getSingleBeanPanelLabelModel();
	
	/**
	 * [OPTIONAL]
	 * Sets the label (fieldset legend) of the SingleBeanPanel.
	 *
	 * @param label the new single bean panel label model
	 */
	void setSingleBeanPanelLabelModel(IModel<String> label);
	
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
}