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
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDefImpl;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.model.IModel;

/**
 * Definition for a ListTablePanel.
 * 
 * @param <B>
 *        Type of object that populates the list. 
 */
public class ListTablePanelDefImpl<B extends Serializable> 
extends ServicePanelWithBackDefImpl
implements ListTablePanelDef<B> {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
		
	/** 
	 * Data table creator. 
	 */
	private DataTableCreator<B> dataTableCreator;
	
	/**
	 * list
	 */
	private List<B> list;
	
	/**
	 * Results label.
	 */
	private IModel<String> listLabel;

	public DataTableCreator<B> getDataTableCreator() {
		return dataTableCreator;
	}

	public void setDataTableCreator(DataTableCreator<B> dataTableCreator) {
		this.dataTableCreator = dataTableCreator;
	}

	public List<B> getList() {
		return list;
	}

	public void setList(List<B> list) {
		this.list = list;
	}

	public void setListLabelModel(IModel<String> label) {
		this.listLabel = label;
	}

	public IModel<String> getListLabelModel() {
		return listLabel;
	}

}
