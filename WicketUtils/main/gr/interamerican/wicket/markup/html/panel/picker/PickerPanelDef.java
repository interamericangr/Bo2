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

import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.adapters.Flag;
import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.markup.html.panel.listTable.ListTablePanelDef;
import gr.interamerican.wicket.markup.html.panel.service.BeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

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
	 * @return Returns the itemSelectedAction
	 * @see #setItemSelectedAction(PickAction)
	 */
	public PickAction<B> getItemSelectedAction();
	
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
	public void setItemSelectedAction(PickAction<B> itemSelectedAction);
	
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
	 * @param itemSelectedActionFlag the new item selected action flag
	 */
	public void setItemSelectedActionFlag(Flag itemSelectedActionFlag);
	
	/**
	 * Gets the secondItemSelectedAction.
	 *
	 * @return Returns the secondItemSelectedAction
	 * @see #setSecondItemSelectedAction(PickAction)
	 */
	public PickAction<B> getSecondItemSelectedAction();

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
	public void setSecondItemSelectedAction(PickAction<B> secondItemSelectedAction);
	
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
	 * @param secondItemSelectedActionFlag the new second item selected action flag
	 */
	public void setSecondItemSelectedActionFlag(Flag secondItemSelectedActionFlag);
	
	/**
	 * Gets the selectLabel.
	 *
	 * @return selectLabel
	 * @see #setSelectLabelModel(IModel)
	 */
	public IModel<String> getSelectLabelModel();
	
	/**
	 * [OPTIONAL] 
	 * Sets a custom label for the select button.
	 *
	 * @param label the new select label model
	 */
	public void setSelectLabelModel(IModel<String> label);
	
	/**
	 * Gets the secondSelectLabel.
	 *
	 * @return secondSelectLabel
	 * @see #setSecondSelectLabelModel(IModel)
	 */
	public IModel<String> getSecondSelectLabelModel();
	
	/**
	 * [OPTIONAL] 
	 * Sets a custom label for the second select button.
	 *
	 * @param label the new second select label model
	 */
	public void setSecondSelectLabelModel(IModel<String> label);
	
	/**
	 * Gets the refreshListAfterPickAction.
	 *
	 * @return refreshListAfterPickAction
	 * @see #setRefreshListAfterPickAction(Boolean)
	 */
	public Boolean getRefreshListAfterPickAction();
	
	/**
	 * [OPTIONAL]
	 * Will repaint the data table after any select action is executed.
	 *
	 * @param refreshListAfterPickAction the new refresh list after pick action
	 */
	public void setRefreshListAfterPickAction(Boolean refreshListAfterPickAction);

}
