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

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.functions.SerializableUnaryOperator;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;
import gr.interamerican.wicket.creators.DataTableProvider;
import gr.interamerican.wicket.creators.ExportActionCreator;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDefImpl;

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
	private DataTableProvider<B, ?> dataTableCreator;

	/** list. */
	private List<B> list;

	/**
	 * Results label.
	 */
	private IModel<String> listLabel;

	/**
	 * Creator of action that handles the export (mandatory if we want the
	 * feature to be enabled)
	 */
	private ExportActionCreator<B> exportActionCreator;

	/**
	 * Export button label resource model (optional)
	 */
	private IModel<String> exportLabel;

	/**
	 * Modifier for the {@link ExportDataSetup} created by this (optional)
	 */
	private SerializableUnaryOperator<ExportDataSetup<B>> exportSetupModifier;

	@Override
	public DataTableProvider<B, ?> getDataTableCreator() {
		return dataTableCreator;
	}

	@Override
	public void setDataTableCreator(DataTableProvider<B, ?> dataTableCreator) {
		this.dataTableCreator = dataTableCreator;
	}

	@Override
	public List<B> getList() {
		return list;
	}

	@Override
	public void setList(List<B> list) {
		this.list = list;
	}

	@Override
	public void setListLabelModel(IModel<String> label) {
		this.listLabel = label;
	}

	@Override
	public IModel<String> getListLabelModel() {
		return listLabel;
	}

	@Override
	public IModel<String> getExportLabel() {
		return exportLabel;
	}

	@Override
	public void setExportLabel(IModel<String> exportLabel) {
		this.exportLabel = exportLabel;
	}

	@Override
	public ExportActionCreator<B> getExportActionCreator() {
		return exportActionCreator;
	}

	@Override
	public void setExportActionCreator(ExportActionCreator<B> exportActionCreator) {
		this.exportActionCreator = exportActionCreator;
	}

	@Override
	public SerializableUnaryOperator<ExportDataSetup<B>> getExportSetupModifier() {
		return exportSetupModifier;
	}

	@Override
	public void setExportSetupModifier(SerializableUnaryOperator<ExportDataSetup<B>> exportSetupModifier) {
		this.exportSetupModifier = exportSetupModifier;
	}
}