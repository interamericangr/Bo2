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
package gr.interamerican.wicket.markup.html.panel.listTable;


import gr.interamerican.wicket.creators.DataTableCreator;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDef;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanelDef;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.model.IModel;

/**
 * {@link ServicePanelDef} for {@link ListTablePanel}.
 * 
 * @param <B> type of bean.
 */
public interface ListTablePanelDef<B extends Serializable> 
extends ServicePanelWithBackDef {
		
	/**
	 * Gets the dataTableCreator.
	 * 
	 * @see #setDataTableCreator(DataTableCreator)
	 *
	 * @return Returns the {@link DataTableCreator}.
	 */
	DataTableCreator<B> getDataTableCreator();

	/**
	 * [MANDATORY]
	 * Assigns a new value to the DataTableCreator. The DataTableCreator
	 * creates a {@link DataTable} that shows the list.
	 *
	 * @param dataTableCreator 
	 *        the dataTableCreator to set
	 */
	void setDataTableCreator(DataTableCreator<B> dataTableCreator);
	
	/**
	 * Gets the list that contains the elements of the table.
	 * 
	 * @see #setList(List)
	 * 
	 * @return Returns the list that populates the data table.
	 */
	List<B> getList();
	
	/**
	 * [OPTIONAL]
	 * Sets the list that contains the elements of the table.
	 * If this is not set, a new ArrayList will be created.
	 * 
	 * @param list 
	 *        The list to set.
	 */
	void setList(List<B> list);
	
	/**
	 * [OPTIONAL]
	 * Sets the list label resource model. This is displayed on top
	 * of the panel's table.
	 * 
	 * @param label
	 */
	void setListLabelModel(IModel<String> label);
	
	/**
	 * Gets the list label.
	 * 
	 * @see #setListLabelModel(IModel)
	 * 
	 * @return list label.
	 */
	IModel<String> getListLabelModel();

}
