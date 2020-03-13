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

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.ICallbackAction;
import gr.interamerican.wicket.callback.MultiplePickAction;
import gr.interamerican.wicket.markup.html.panel.ServicePanelUtils;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanel;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.util.resource.StringResourceUtils;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;

/**
 * Basic {@link ServicePanel} that shows a List of objects and
 * allows the user to pick many of them.
 * 
 * This panel requires one {@link ICallbackAction} that is executed
 * when the user requests to go back and one {@link MultiplePickAction}
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
	
	/** Select checked rows {@link AjaxButton}. */	
	protected CallbackAjaxButton selectButton;
	
	/** 2nd select checked rows {@link AjaxButton}. */	
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
	 * @param definition the definition
	 */
	public MultipleSelectionsPanel(MultipleSelectionsPanelDef<B> definition) {
		super(definition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MultipleSelectionsPanelDef<B> getDefinition() {		
		return (MultipleSelectionsPanelDef<B>)definition;
	}
	
	@SuppressWarnings({ "nls" })
	@Override
	protected void init() {
		super.init();
		
		IModel<String> selectLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.MSP_SELECT_BTN_LABEL, this, getDefinition().getSelectLabelModel(), "Select");
		IModel<String> secondSelectLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.MSP_2ND_SELECT_BTN_LABEL, this, getDefinition().getSecondSelectLabelModel(), "2nd select");
		IModel<String> checkGroupSelectorLabelModel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.MSP_CHECKGROUP_SELECTOR_LABEL, this, getDefinition().getCheckGroupSelectorLabelModel(), "Select all");
		
		MultiplePickAction<B> itemsSelectedAction = getDefinition().getItemsSelectedAction(); 
		MultiplePickAction<B> secondItemsSelectedAction = getDefinition().getSecondItemsSelectedAction();
		
		
		selectButton = new CallbackAjaxButton(SELECT_BUTTON_ID, selectLabel,
				new WrappedICallbackAction(itemsSelectedAction, getDefinition().getItemsSelectedActionFlag()),
				getFeedBackPanel());

		secondSelectButton = new CallbackAjaxButton(SECOND_SELECT_BUTTON_ID, secondSelectLabel,
				new WrappedICallbackAction(secondItemsSelectedAction,
						getDefinition().getSecondItemsSelectedActionFlag()),
				getFeedBackPanel());
		
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
		tableForm.add(getDefinition().createButton(EXPORT_BUTTON_ID, getDefinition(), this));
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

	/**
	 * The actual {@link ICallbackAction} used.
	 */
	class WrappedICallbackAction implements ICallbackAction {

		/**
		 * Serial Version UID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The Actual Action
		 */
		private MultiplePickAction<B> pick;

		/**
		 * Flag
		 */
		private Flag flag;

		/**
		 * Constructor.
		 * 
		 * @param pick
		 *            The Actual Action
		 * @param flag
		 *            Flag
		 */
		public WrappedICallbackAction(MultiplePickAction<B> pick, Flag flag) {
			this.pick = pick;
			this.flag = flag;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void invoke(AjaxRequestTarget target, Form<?> form) {
			if (!ServicePanelUtils.authorizedByFlag(flag)) {
				MultipleSelectionsPanel.this.error(flag.getDownMessage());
				return;
			}
			if (CollectionUtils.isNullOrEmpty(getDefinition().getSelectionsModel().getObject())) {
				return;
			}
			// backwards compatibility
			if (pick instanceof gr.interamerican.wicket.callback.CallbackAction) {
				gr.interamerican.wicket.callback.CallbackAction callback = (gr.interamerican.wicket.callback.CallbackAction) pick;
				callback.setCaller(MultipleSelectionsPanel.this);
				callback.callBack(target, form);
			} else {
				pick.doPick(target, getDefinition().getSelectionsModel().getObject());
			}
		}
	}
}