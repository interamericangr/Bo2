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
import gr.interamerican.wicket.markup.html.panel.service.BeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

import java.io.Serializable;

import org.apache.wicket.model.IModel;

/**
 * {@link ServicePanelDef} for {@link PickerPanel}.
 * 
 * @param <B> type of bean.
 */
public interface PickerPanelDef<B extends Serializable> 
extends ListTablePanelDef<B>, BeanPanelDef<B> {		

	/**
	 * Gets the itemSelectedAction.
	 * 
	 * @see #setItemSelectedAction(CallbackAction)
	 *
	 * @return Returns the itemSelectedAction
	 */
	public CallbackAction getItemSelectedAction();
	
	/**
	 * [MANDATORY]
	 * Assigns a new value to the itemSelectedAction. This action is executed
	 * when the user presses the select button. The action implementation may
	 * retrieve the currently selected item by using the <code>beanModel</code> 
	 * property.
	 * 
	 * This may be optional for {@link PickerPanel} sub-classes. In such a case,
	 * if this property is null, the select button won't be available.
	 *
	 * @param itemSelectedAction the itemSelectedAction to set
	 */
	public void setItemSelectedAction(CallbackAction itemSelectedAction);
	
	/**
	 * Gets the itemSelectedActionFlag.
	 * 
	 * @return Returns the itemSelectedActionFlag.
	 */
	public Flag getItemSelectedActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the itemSelectedActionFlag.
	 * 
	 * @param itemSelectedActionFlag
	 */
	public void setItemSelectedActionFlag(Flag itemSelectedActionFlag);
	
	/**
	 * Gets the secondItemSelectedAction.
	 * 
	 * @see #setSecondItemSelectedAction(CallbackAction)
	 *
	 * @return Returns the secondItemSelectedAction
	 */
	public CallbackAction getSecondItemSelectedAction();

	/**
	 * [OPTIONAL]
	 * Assigns a new value to the secondItemSelectedAction. This action is executed
	 * when the user presses the second select button. The action implementation may
	 * retrieve the currently selected item by using the <code>beanModel</code> 
	 * property. This is meant to provide additional flexibility in cases where 
	 * it is desired to perform more than one custom operations on the selected item.
	 * If more than two are desired it is better to show custom buttons using the 
	 * itemSelectedAction that perform the desired custom operations on the selected item.
	 * 
	 * If this property is null, the second select button won't be available.
	 *
	 * @param secondItemSelectedAction the secondItemSelectedAction to set
	 */
	public void setSecondItemSelectedAction(CallbackAction secondItemSelectedAction);
	
	/**
	 * Gets the secondItemSelectedActionFlag.
	 * 
	 * @return Returns the secondItemSelectedActionFlag.
	 */
	public Flag getSecondItemSelectedActionFlag();
	
	/**
	 * [OPTIONAL]
	 * Sets the secondItemSelectedActionFlag.
	 * 
	 * @param secondItemSelectedActionFlag
	 */
	public void setSecondItemSelectedActionFlag(Flag secondItemSelectedActionFlag);
	
	/**
	 * Gets the selectLabel.
	 * 
	 * @see #setSelectLabelModel(IModel)
	 * 
	 * @return selectLabel
	 */
	public IModel<String> getSelectLabelModel();
	
	/**
	 * [OPTIONAL] 
	 * Sets a custom label for the select button.
	 * 
	 * @param label
	 */
	public void setSelectLabelModel(IModel<String> label);
	
	/**
	 * Gets the secondSelectLabel.
	 * 
	 * @see #setSecondSelectLabelModel(IModel)
	 * 
	 * @return secondSelectLabel
	 */
	public IModel<String> getSecondSelectLabelModel();
	
	/**
	 * [OPTIONAL] 
	 * Sets a custom label for the second select button.
	 * 
	 * @param label
	 */
	public void setSecondSelectLabelModel(IModel<String> label);
	
	/**
	 * Gets the refreshListAfterPickAction.
	 * 
	 * @see #setRefreshListAfterPickAction(Boolean)
	 * 
	 * @return refreshListAfterPickAction
	 */
	public Boolean getRefreshListAfterPickAction();
	
	/**
	 * [OPTIONAL]
	 * Will repaint the data table after any select action is executed.
	 * 
	 * @param refreshListAfterPickAction
	 */
	public void setRefreshListAfterPickAction(Boolean refreshListAfterPickAction);

}
