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
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.wicket.ajax.markup.html.form.CallbackAjaxButton;
import gr.interamerican.wicket.callback.ICallbackAction;
import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.creators.DataTableProvider;
import gr.interamerican.wicket.markup.html.panel.ServicePanelUtils;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanel;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanel;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.util.resource.StringResourceUtils;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;

/**
 * Basic {@link ServicePanel} that shows a List of objects and
 * allows the user to pick one of them.
 * 
 * This panel requires one {@link ICallbackAction} that is executed
 * when the user requests to go back and one {@link PickAction}
 * that is executed when the user selects an object from the list.
 * 
 * If the <code>itemSelectedAction</code> is null, the select button
 * is not shown. This is useful only in the context of a {@link CrudPickerPanel}.
 * A plain PickerPanel does not have much use when the user can't pick anything.
 * 
 * @param <B> Type of bean that encapsulates a row of the result set.
 */
public class PickerPanel<B extends Serializable> 
extends ListTablePanel<B> {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * wicket id.
	 */
	public static final String SELECT_BUTTON_ID = "selectButton"; //$NON-NLS-1$
	
	/**
	 * wicket id.
	 */
	public static final String SECOND_SELECT_BUTTON_ID = "secondSelectButton"; //$NON-NLS-1$
	
	/**
	 * wicket id.
	 */
	protected static final String RADIOGROUP_ID = "radioGroup"; //$NON-NLS-1$
	
	/** Select row {@link AjaxButton}. */	
	protected CallbackAjaxButton selectButton;  
	
	/** Select row {@link AjaxButton}. */	
	protected CallbackAjaxButton secondSelectButton;  
	
	/**
	 * RadioGroup of select column.
	 */
	protected RadioGroup<B> radioGroup; 
	
	/**
	 * Creates a new AbstractQueryResultsPanel object. 
	 *
	 * @param definition the definition
	 */
	public PickerPanel(PickerPanelDef<B> definition) {			
		super(definition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PickerPanelDef<B> getDefinition() {		
		return (PickerPanelDef<B>)definition;
	}
	
	@SuppressWarnings("nls")
	@Override
	protected void init() {
		super.init();

		IModel<String> selectLabel = StringResourceUtils.getResourceModel(WellKnownResourceIds.PP_SELECT_BTN_LABEL,
				this, getDefinition().getSelectLabelModel(), "Select");
		IModel<String> secondSelectLabel = StringResourceUtils.getResourceModel(
				WellKnownResourceIds.PP_2ND_SELECT_BTN_LABEL, this, getDefinition().getSecondSelectLabelModel(),
				"Select");

		PickAction<B> itemSelectedAction = getDefinition().getItemSelectedAction();
		PickAction<B> secondItemSelectedAction = getDefinition().getSecondItemSelectedAction();

		selectButton = new CallbackAjaxButton(SELECT_BUTTON_ID, selectLabel,
				new WrappedICallbackAction(itemSelectedAction, getDefinition().getItemSelectedActionFlag()),
				getFeedBackPanel());

		ServicePanelUtils.disableButton(getDefinition(), getDefinition().getItemSelectedActionFlag(), selectButton);

		secondSelectButton = new CallbackAjaxButton(SECOND_SELECT_BUTTON_ID, secondSelectLabel,
				new WrappedICallbackAction(secondItemSelectedAction, getDefinition().getSecondItemSelectedActionFlag()),
				getFeedBackPanel());

		ServicePanelUtils.disableButton(getDefinition(), getDefinition().getSecondItemSelectedActionFlag(),
				secondSelectButton);

		radioGroup = new RadioGroup<B>(RADIOGROUP_ID, new Model<B>());
	}
	
	@Override
	protected void paint() {
		add(listLabel);
		radioGroup.add(table);
		tableForm.add(radioGroup);
		tableForm.add(selectButton);
		tableForm.add(secondSelectButton);
		tableForm.add(backButton);
		tableForm.add(getDefinition().createButton(EXPORT_BUTTON_ID, getDefinition(), this));
		add(tableForm);		
		add(feedBackPanel);
		if(getDefinition().getItemSelectedAction() == null) {
			selectButton.setVisible(false);
		}
		if(getDefinition().getSecondItemSelectedAction() == null) {
			secondSelectButton.setVisible(false);
		}
	}

	@SuppressWarnings("nls")
	@Override
	protected void validateDef() {
		super.validateDef();
		if(getDefinition().getRefreshListAfterPickAction()==null) {
			getDefinition().setRefreshListAfterPickAction(false);
		}
		if(getDefinition().getBeanModel() == null) {
			String msg = "Cannot initialize a PickerPanel with null model in its definition.";
			throw new RuntimeException(msg);
		}
	}

	/**
	 * Does the repainting of the panel's data table with the updated data.
	 * 
	 * @param target
	 * @param form
	 */
	protected void refreshTable(AjaxRequestTarget target, Form<?> form) {
		List<B> list = getDefinition().getList();
		DataTableProvider<B, ?> dataTableCreator = getDefinition().getDataTableCreator();
		RadioGroup<B> newRadioGroup = new RadioGroup<B>(RADIOGROUP_ID, new Model<B>());
		DataTable<B, ?> newTable = dataTableCreator.createDataTable(DATATABLE_ID, list);
		newRadioGroup.add(newTable);
		radioGroup.replaceWith(newRadioGroup);
		radioGroup = newRadioGroup;
		target.add(form);
	}

	/**
	 * The actual {@link ICallbackAction} used on 'select'.
	 */
	class WrappedICallbackAction implements ICallbackAction {

		/**
		 * Serial Version UID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * The Actual Action
		 */
		private PickAction<B> pick;

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
		public WrappedICallbackAction(PickAction<B> pick, Flag flag) {
			this.pick = pick;
			this.flag = flag;
		}

		@SuppressWarnings("deprecation")
		@Override
		public void invoke(AjaxRequestTarget target, Form<?> form) {
			if (!ServicePanelUtils.authorizedByFlag(flag)) {
				PickerPanel.this.error(flag.getDownMessage());
				return;
			}
			B selection = radioGroup.getModelObject();
			if (selection == null) {
				return;
			}
			getDefinition().getBeanModel().setObject(selection);
			// backwards compatibility
			if (pick instanceof gr.interamerican.wicket.callback.CallbackAction) {
				gr.interamerican.wicket.callback.CallbackAction callback = (gr.interamerican.wicket.callback.CallbackAction) pick;
				callback.setCaller(PickerPanel.this);
				callback.callBack(target, form);
			} else {
				pick.doPick(target, selection);
			}
			if (!getDefinition().getRefreshListAfterPickAction()) {
				return;
			}
			refreshTable(target, form);
		}
	}
}