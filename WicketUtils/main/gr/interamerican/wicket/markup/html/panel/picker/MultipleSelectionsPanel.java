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
package gr.interamerican.wicket.markup.html.panel.picker;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.markup.html.panel.ServicePanelUtils;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanel;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.util.resource.StringResourceUtils;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * Basic {@link ServicePanel} that shows a List of objects and
 * allows the user to pick many of them.
 * 
 * This panel requires one {@link CallbackAction} that is executed
 * when the user requests to go back and one {@link CallbackAction}
 * that is executed when the user selects some objects from the list.
 * 
 * @param <B> Type of bean that encapsulates a row of the result set.
 */
public class MultipleSelectionsPanel<B extends Serializable> 
extends ListTablePanel<B> {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * wicket id.
	 */
	protected static final String SELECT_BUTTON_ID = "selectButton"; //$NON-NLS-1$
	
	/**
	 * wicket id.
	 */
	protected static final String SECOND_SELECT_BUTTON_ID = "secondSelectButton"; //$NON-NLS-1$
	
	/**
	 * wicket id.
	 */
	protected static final String CHECKGROUP_ID = "checkGroup"; //$NON-NLS-1$
	
	/**
	 * wicket id.
	 */
	protected static final String SELECT_COLUMN_LABEL = "selectColumnLabel"; //$NON-NLS-1$	
	
	/**
	 * wicket id.
	 */
	protected static final String CHECKGROUP_SELECTOR_ID = "checkGroupSelector"; //$NON-NLS-1$
	
	/**
	 * wicket id.
	 */
	protected static final String CHECKGROUP_SELECTOR_LABEL_ID = "checkGroupSelectorLabel"; //$NON-NLS-1$
	
	/**
	 * Select checked rows {@link AjaxButton}
	 */	
	protected CallbackAjaxButton selectButton;
	
	/**
	 * 2nd select checked rows {@link AjaxButton}
	 */	
	protected CallbackAjaxButton secondSelectButton;  
	
	/**
	 * CheckGroup of select column.
	 */
	protected CheckGroup<B> checkGroup;
	
	/**
	 * Select / deselect all checkbox.
	 */
	protected CheckGroupSelector checkGroupSelector;
	
	/**
	 * Label for checkGroupSelector.
	 */
	protected Label checkGroupSelectorLabel;
	
	/**
	 * Creates a new MultipleSelectionsPanel object. 
	 *
	 * @param definition
	 */
	public MultipleSelectionsPanel(MultipleSelectionsPanelDef<B> definition) {
		super(definition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MultipleSelectionsPanelDef<B> getDefinition() {		
		return (MultipleSelectionsPanelDef<B>)definition;
	}
	
	@SuppressWarnings({ "serial", "nls" })
	@Override
	protected void init() {
		super.init();
		
		IModel<String> selectLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.MSP_SELECT_BTN_LABEL, this, getDefinition().getSelectLabelModel(), "Select");
		IModel<String> secondSelectLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.MSP_2ND_SELECT_BTN_LABEL, this, getDefinition().getSecondSelectLabelModel(), "2nd select");
		IModel<String> checkGroupSelectorLabelModel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.MSP_CHECKGROUP_SELECTOR_LABEL, this, getDefinition().getCheckGroupSelectorLabelModel(), "Select all");
		
		CallbackAction itemsSelectedAction = getDefinition().getItemsSelectedAction(); 
		if(itemsSelectedAction!=null) {
			itemsSelectedAction.setCaller(this);
		}
		CallbackAction secondItemsSelectedAction = getDefinition().getSecondItemsSelectedAction();
		if(secondItemsSelectedAction!=null) {
			secondItemsSelectedAction.setCaller(this);
		}
		
		
		selectButton = new CallbackAjaxButton(SELECT_BUTTON_ID, selectLabel, itemsSelectedAction, getFeedBackPanel()) {		
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(!ServicePanelUtils.authorizedByFlag(getDefinition().getItemsSelectedActionFlag())) {
					target.add(feedBackPanel);
					MultipleSelectionsPanel.this.error(getDefinition().getItemsSelectedActionFlag().getDownMessage());
					return;
				}
				if(CollectionUtils.isNullOrEmpty(getDefinition().getSelectionsModel().getObject())) {
					return; 
				}
				super.onSubmit(target, form);
			}
		};
		
		secondSelectButton = new CallbackAjaxButton(SECOND_SELECT_BUTTON_ID, secondSelectLabel, secondItemsSelectedAction, getFeedBackPanel()) {
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if(!ServicePanelUtils.authorizedByFlag(getDefinition().getSecondItemsSelectedActionFlag())) {
					target.add(feedBackPanel);
					MultipleSelectionsPanel.this.error(getDefinition().getSecondItemsSelectedActionFlag().getDownMessage());
					return;
				}
				if(CollectionUtils.isNullOrEmpty(getDefinition().getSelectionsModel().getObject())) {
					return; 
				}
				super.onSubmit(target, form);
			}
		};
		
		ServicePanelUtils.disableButton(getDefinition(), getDefinition().getItemsSelectedActionFlag(), selectButton);
		
		checkGroup = new CheckGroup<B>(CHECKGROUP_ID, getDefinition().getSelectionsModel());
		checkGroupSelector = new CheckGroupSelector(CHECKGROUP_SELECTOR_ID, checkGroup);
		checkGroupSelectorLabel = new Label(CHECKGROUP_SELECTOR_LABEL_ID, checkGroupSelectorLabelModel);
	}
	
	@Override
	protected void paint() {
		add(listLabel);
		checkGroup.add(table);
		checkGroup.add(checkGroupSelector);
		checkGroup.add(checkGroupSelectorLabel);
		tableForm.add(checkGroup);
		tableForm.add(selectButton);
		tableForm.add(secondSelectButton);
		tableForm.add(backButton);
		add(tableForm);		
		add(feedBackPanel);
		
		if(getDefinition().getSecondItemsSelectedAction()==null) {
			secondSelectButton.setVisible(false);
		}
	}
	
	@Override
	@SuppressWarnings("nls")
	protected void validateDef() {
		super.validateDef();
		if(getDefinition().getSelectionsModel() == null) {
			String msg = "Cannot instantiate a MultipleSelectionsPanel with a null selections model.";
			throw new RuntimeException(msg);
		}
		if(getDefinition().getItemsSelectedAction() == null) {
			String msg = "Cannot instantiate a MultipleSelectionsPanel with a null itemsSelectedAction.";
			throw new RuntimeException(msg);
		}
		if(getDefinition().getSelectionsModel().getObject()==null) {
			getDefinition().getSelectionsModel().setObject(new ArrayList<B>());
		}
	}

}
