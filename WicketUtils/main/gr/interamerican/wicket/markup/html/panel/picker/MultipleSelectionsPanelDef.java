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

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.wicket.callback.CallbackAction;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.wicket.model.IModel;

/**
 * {@link ServicePanelDef} for {@link MultipleSelectionsPanel}.
 * 
 * @param <B> 
 *        Type of bean.
 */
public interface MultipleSelectionsPanelDef<B extends Serializable>
extends ListTablePanelDef<B> {
	
	/**
	 * Gets the itemsSelectedAction.
	 *
	 * @return Returns the itemsSelectedAction
	 */
	CallbackAction getItemsSelectedAction();

	/**
	 * [MANDATORY]
	 * Assigns a new value to the itemsSelectedAction. This action is executed
	 * when the user presses the select button. One of the things this action
	 * does, is to retrieve the <code>selections</code> property from this
	 * definition. The selected items will be there.
	 * 
	 * This may be optional for {@link PickerPanel} sub-classes. In such a case,
	 * if this property is null, the select button won't be available.
	 *
	 * @param itemsSelectedAction the itemsSelectedAction to set
	 */
	void setItemsSelectedAction(CallbackAction itemsSelectedAction);
	
	/**
	 * Gets the itemsSelectedActionFlag.
	 * 
	 * @return Returns the itemsSelectedActionFlag.
	 */
	Flag getItemsSelectedActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the itemsSelectedActionFlag.
	 * 
	 * @param itemsSelectedActionFlag
	 */
	void setItemsSelectedActionFlag(Flag itemsSelectedActionFlag);
	
	//******
	
	/**
	 * Gets the secondItemsSelectedAction.
	 *
	 * @return Returns the secondItemsSelectedAction
	 */
	CallbackAction getSecondItemsSelectedAction();

	/**
	 * [OPTIONAL]
	 * Assigns a new value to the secondItemsSelectedAction. This action is executed
	 * when the user presses the 2nd select button. One of the things this action
	 * does, is to retrieve the <code>selections</code> property from this
	 * definition. The selected items will be there.
	 * 
	 * if this property is null, the second select button won't be available.
	 *
	 * @param secondItemsSelectedAction the itemsSelectedAction to set
	 */
	void setSecondItemsSelectedAction(CallbackAction secondItemsSelectedAction);
	
	/**
	 * Gets the secondItemsSelectedActionFlag.
	 * 
	 * @return Returns the secondItemsSelectedActionFlag.
	 */
	Flag getSecondItemsSelectedActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the secondItemsSelectedActionFlag.
	 * 
	 * @param secondItemsSelectedActionFlag
	 */
	void setSecondItemsSelectedActionFlag(Flag secondItemsSelectedActionFlag);
	
	/**
	 * [MANDATORY]
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
	 * @see #getSelectionsModel()
	 * 
	 * @param selectionsModel
	 */
	void setSelectionsModel(IModel<ArrayList<B>> selectionsModel);
	
	/**
	 * Gets the selectLabel.
	 * 
	 * @return selectLabel
	 */
	IModel<String> getSelectLabelModel();
	
	/**
	 * [OPTIONAL] Sets a custom label for the select button.
	 * 
	 * @param label
	 */
	void setSelectLabelModel(IModel<String> label);
	
	/**
	 * Gets the secondSelectLabel.
	 * 
	 * @return selectLabel
	 */
	IModel<String> getSecondSelectLabelModel();
	
	/**
	 * [OPTIONAL] Sets a custom label for the 2nd select button.
	 * 
	 * @param label
	 */
	void setSecondSelectLabelModel(IModel<String> label);
	
	/**
	 * Gets the checkGroupSelectorLabel
	 * 
	 * @return checkGroupSelectorLabel
	 */
	IModel<String> getCheckGroupSelectorLabelModel();
	
	/**
	 * [OPTIONAL]
	 * Sets a custom label for the select / deselect all checkbox.
	 * 
	 * @param label
	 */
	void setCheckGroupSelectorLabelModel(IModel<String> label);

}
