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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.functions.SerializableRunnable;
import gr.interamerican.bo2.utils.functions.SerializableUnaryOperator;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;
import gr.interamerican.wicket.ajax.markup.html.form.RunnableButton;
import gr.interamerican.wicket.creators.DataTableProvider;
import gr.interamerican.wicket.creators.DataTableProviderWithColumns;
import gr.interamerican.wicket.creators.ExportActionCreator;
import gr.interamerican.wicket.creators.FunctionalDataTableCreator;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.ExportableColumn;
import gr.interamerican.wicket.util.resource.StringResourceUtils;

/**
 * The Options relevant with adding a button that exports data.<br>
 * This is relevant to the {@link ListTablePanelDef}, as it uses the data
 * provided by it, and also it can used the {@link DataTableProvider} defined in
 * it.<br>
 * In order to do the later the following conditions should be met :
 * <ul>
 * <li>The creator must be a {@link DataTableProviderWithColumns}
 * <li>Columns that will be exported must implement the {@link ExportableColumn}
 * interface
 * </ul>
 * These 2 conditions are accomplished with the use of
 * {@link FunctionalDataTableCreator}.
 * 
 * @param <B>
 *            Type of the Exported bean
 */
public interface ExportButtonOptions<B extends Serializable> {

	/**
	 * Well known resource id for the label of the export button
	 */
	public String WELL_KNOWN_EXPORT_ID="export_data_label"; //$NON-NLS-1$

	/**
	 * Creates an {@link RunnableButton} that will export data using the options
	 * provided by this interface. The link will not be visible if no
	 * {@link #getExportActionCreator()} is defined.
	 * 
	 * @param id
	 *            Wicket Id of the Link
	 * @param def
	 *            {@link ListTablePanelDef} to use
	 * @param owner
	 *            Owner of this Link
	 * @return New {@link AjaxLink}
	 */
	default RunnableButton createButton(String id, ListTablePanelDef<B> def, Component owner) {
		SerializableRunnable action = createExportAction(def);
		IModel<String> bodyModel = StringResourceUtils.getResourceModel(WELL_KNOWN_EXPORT_ID, owner, getExportLabel(),
				"Export Data"); //$NON-NLS-1$
		RunnableButton returned = new RunnableButton(id, bodyModel, action);
		if (action == null) {
			returned.setVisible(false);
		}
		returned.setDefaultFormProcessing(false);
		return returned;
	}

	/**
	 * Creates a {@link SerializableRunnable} that will export data using the
	 * options provided by this interface. This will be null if no
	 * {@link #getExportActionCreator()} is defined.
	 * 
	 * @param def
	 *            {@link ListTablePanelDef} to use
	 * @return {@link SerializableRunnable} to do the job
	 */
	default SerializableRunnable createExportAction(ListTablePanelDef<B> def) {
		if (getExportActionCreator() == null) {
			return null;
		}
		ExportDataSetup<B> input = new ExportDataSetup<>();
		DataTableProvider<B, ?> creator = def.getDataTableCreator();
		if (creator instanceof DataTableProviderWithColumns) {
			useColumns(((DataTableProviderWithColumns<B, ?>) creator).getColumns(), input);
		}
		SerializableUnaryOperator<ExportDataSetup<B>> modifier = getExportSetupModifier();
		if (modifier != null) {
			input = modifier.apply(input);
		}
		return getExportActionCreator().getCreator(input, def::getList);
	}

	/**
	 * Uses a {@link List} of {@link IColumn} in order to add Columns to an
	 * {@link ExportDataSetup}.<br>
	 * This is applicable only of {@link ExportableColumn}s.
	 * 
	 * @param list
	 *            List with Columns
	 * @param input
	 *            Setup to modify
	 */
	default <S, I> void useColumns(List<IColumn<B, S>> list, ExportDataSetup<B> input) {
		for (IColumn<B, S> column : list) {
			if (column instanceof ExportableColumn) {
				@SuppressWarnings("unchecked")
				ExportableColumn<B, S, I> converted = (ExportableColumn<B, S, I>) column;
				input.addColumn(converted.getFunction(), converted.getFormatter(), converted.getHeader());
			}
		}
	}

	/**
	 * Export button label resource model (optional)
	 * 
	 * @return Export button label resource model (optional)
	 */
	IModel<String> getExportLabel();

	/**
	 * Export button label resource model (optional)
	 * 
	 * @param exportLabel
	 *            Export button label resource model (optional)
	 */
	void setExportLabel(IModel<String> exportLabel);

	/**
	 * Creator of action that handles the export (mandatory if we want the
	 * feature to be enabled)
	 * 
	 * @return Creator of action that handles the export (mandatory if we want
	 *         the feature to be enabled)
	 */
	ExportActionCreator<B> getExportActionCreator();

	/**
	 * Creator of action that handles the export (mandatory if we want the
	 * feature to be enabled)
	 * 
	 * @param exportActionCreator
	 *            Creator of action that handles the export (mandatory if we
	 *            want the feature to be enabled)
	 */
	void setExportActionCreator(ExportActionCreator<B> exportActionCreator);

	/**
	 * Modifier for the {@link ExportDataSetup} created by this (optional)
	 *
	 * @return Modifier for the {@link ExportDataSetup} created by this
	 *         (optional)
	 */
	SerializableUnaryOperator<ExportDataSetup<B>> getExportSetupModifier();

	/**
	 * Modifier for the {@link ExportDataSetup} created by this (optional)
	 *
	 * @param exportSetupModifier
	 *            Modifier for the {@link ExportDataSetup} created by this
	 *            (optional)
	 */
	void setExportSetupModifier(SerializableUnaryOperator<ExportDataSetup<B>> exportSetupModifier);
}