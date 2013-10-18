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

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.creators.DataTableCreator;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBack;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;
import gr.interamerican.wicket.util.resource.StringResourceUtils;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * Basic {@link ServicePanel} that shows a List of objects.
 * 
 * This panel has its table drawn by a {@link DataTableCreator}.
 *  
 * @param <B> Type of bean that encapsulates a row of the result set.
 * 
 * TODO: there are cases, where the {@link DataTableCreator} might return
 * null, indicating that there are two different type of bean lists that
 * can be shown and the results list came up empty, so there is no info
 * on what bean type the user searched for. In this case a label indicating
 * no results found would suffice without confusing the end user.
 */
public class ListTablePanel
<B extends Serializable> 
extends ServicePanelWithBack {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * wicket id.
	 */
	protected static final String DATATABLE_ID = "listTable"; //$NON-NLS-1$
	
	/**
	 * wicket id.
	 */
	protected static final String LIST_LABEL_ID = "listLabel"; //$NON-NLS-1$
	
	/**
	 * wicket id.
	 */
	public static final String TABLE_FORM_ID = "tableForm"; //$NON-NLS-1$

	
	/**
	 * Form that contains the {@link DataTable}.
	 */
	protected Form<B> tableForm;
	
	/**
	 * DataTable.
	 */
	protected DataTable<B> table;
	
	/**
	 * List label.
	 */
	protected Label listLabel;
			
	/**
	 * Creates a new AbstractQueryResultsPanel object. 
	 * 
	 * @param definition 
	 *
	 */
	public ListTablePanel(ListTablePanelDef<B> definition) {			
		super(definition);	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListTablePanelDef<B> getDefinition() {
		return (ListTablePanelDef<B>)definition;
	}
	
	@Override
	protected void init() {
		super.init();
		tableForm = new Form<B>(TABLE_FORM_ID);
		DataTableCreator<B> dataTableCreator = getDefinition().getDataTableCreator();
		List<B> list = getDefinition().getList();
		table = dataTableCreator.createDataTable(DATATABLE_ID,list);
		
		IModel<String> listLabelModel = StringResourceUtils.getResourceModel(
			WellKnownResourceIds.LTP_LIST_TABLE_LABEL, this, getDefinition().getListLabelModel(), StringConstants.EMPTY);
		
		listLabel = new Label(LIST_LABEL_ID, listLabelModel);
	}
		
	@Override
	protected void paint() {
		add(listLabel);
		tableForm.add(table);
		tableForm.add(backButton);
		add(tableForm);
		add(feedBackPanel);
	}
	
	@SuppressWarnings("nls")
	@Override
	protected void validateDef() {
		super.validateDef();
		if(getDefinition().getList()==null) {
			getDefinition().setList(new ArrayList<B>());
		}
		if(getDefinition().getDataTableCreator() == null) {
			String msg = "Cannot initialize a ListTablePanel with null DataTableCreator.";
			throw new RuntimeException(msg);
		}
	}

}
