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
package gr.interamerican.wicket.creators;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.MultipleSelectionsColumn;
import gr.interamerican.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import gr.interamerican.wicket.util.resource.StringResourceUtils;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;

/**
 * Implementation of a {@link DataTableCreator} based on
 * an array of given properties. If the properties given
 * are empty or null, then all the properties of the bean
 * are used. The created DataTable has a column with
 * CheckBoxes.
 * 
 * @param <B> type of bean.
 * 
 */
public class PropertiesBasedMultipleSelectionsDataTableCreator<B extends Serializable>
extends PropertiesBasedDataTableCreator<B>{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Label of select column.
	 */
	protected IModel<String> selectColumnLabelModel;
		
	/**
	 * Disable paging.
	 */
	protected Boolean disablePaging = false;
	 
	/**
	 * Creates a new PropertiesBasedMultipleSelectionsDataTableCreator object. 
	 *
	 * @param beanClass
	 *        Type of row bean.
	 * @param properties
	 *        Ordered properties of the bean to show on the table columns.
	 * @param selectColumnLabelModel 
	 *        Label for the checkBoxes column.
	 */
	public PropertiesBasedMultipleSelectionsDataTableCreator(Class<B> beanClass, String[] properties, IModel<String> selectColumnLabelModel) {
		this(beanClass, properties, selectColumnLabelModel, null, null);
	}

	/**
	 * Creates a new PropertiesBasedMultipleSelectionsDataTableCreator object. 
	 *
	 * @param beanClass
	 *        Type of row bean.
	 * @param properties
	 *        Ordered properties of the bean to show on the table columns.
	 * @param selectColumnLabelModel 
	 *        Label for the checkBoxes column.
	 * @param sortProperty
	 *        Default sorting property. 
	 */
	public PropertiesBasedMultipleSelectionsDataTableCreator(Class<B> beanClass, String[] properties, IModel<String> selectColumnLabelModel, String sortProperty) {
		this(beanClass, properties, selectColumnLabelModel, null, sortProperty);
	}
	
	/**
	 * Creates a new PropertiesBasedMultipleSelectionsDataTableCreator object. 
	 *
	 * @param beanClass
	 *        Type of row bean.
	 * @param properties
	 *        Ordered properties of the bean to show on the table columns.
	 * @param selectColumnLabelModel 
	 *        Label for the checkBoxes column.
	 * @param labels 
	 *        Ordered labels for the properties shown on the table columns.
	 */
	public PropertiesBasedMultipleSelectionsDataTableCreator(Class<B> beanClass, String[] properties, IModel<String> selectColumnLabelModel, String[] labels) {
		this(beanClass, properties, selectColumnLabelModel, labels, null);
	}
	
	/**
	 * Creates a new PropertiesBasedMultipleSelectionsDataTableCreator object. 
	 *
	 * @param beanClass
	 *        Type of row bean.
	 * @param properties
	 *        Ordered properties of the bean to show on the table columns.
	 * @param selectColumnLabelModel 
	 *        Label for the checkBoxes column.
	 * @param labels 
	 *        Ordered labels for the properties shown on the table columns.
	 * @param sortProperty
	 *        Default sorting property.
	 */
	public PropertiesBasedMultipleSelectionsDataTableCreator(
	Class<B> beanClass, String[] properties, IModel<String> selectColumnLabelModel, String[] labels, String sortProperty) {
		this(beanClass, properties, selectColumnLabelModel, labels, sortProperty, null);
	}
	
	/**
	 * Creates a new PropertiesBasedMultipleSelectionsDataTableCreator object. 
	 *
	 * @param beanClass
	 *        Type of row bean.
	 * @param properties
	 *        Ordered properties of the bean to show on the table columns.
	 * @param selectColumnLabelModel 
	 *        Label for the checkBoxes column.
	 * @param labels 
	 *        Ordered labels for the properties shown on the table columns.
	 * @param sortProperty
	 *        Default sorting property.
	 * @param formatters 
	 *        Formatters Object of properties for column rendering.
	 */
	public PropertiesBasedMultipleSelectionsDataTableCreator(
			Class<B> beanClass, String[] properties, IModel<String> selectColumnLabelModel, String[] labels, String sortProperty, Formatter<?>[] formatters) {
		super(beanClass, properties, labels, sortProperty, formatters);
		this.selectColumnLabelModel = selectColumnLabelModel;
	}
	
	@Override
	public List<IColumn<B>> createColumns() {
		selectColumnLabelModel = StringResourceUtils.getResourceModel(
			WellKnownResourceIds.MSDT_SELECT_LABEL, null, selectColumnLabelModel, StringConstants.EMPTY);
		List<IColumn<B>> tableColumns = new ArrayList<IColumn<B>>();
		tableColumns.add(new MultipleSelectionsColumn<B>(selectColumnLabelModel));
		tableColumns.addAll(super.createColumns());
		return tableColumns;
	}
	
	@Override
	public DataTable<B> createDataTable(String id, List<B> elements) {
		List<B> data = elements;
		if(sortProperty != null) {
			data = CollectionUtils.sort(data, beanClass, sortProperty);
		}
		SortableDataProvider<B> provider = new SortableDataProvider<B>(data, beanClass);
		if(disablePaging && provider.size()>0) {
			return new DefaultDataTable<B> (id, createColumns(), provider, provider.size());
		}
		return new DefaultDataTable<B> (id, createColumns(), provider, Utils.notNull(rowsPerPage, 10));
	}
	
	/**
	 * Assigns a new value to the disablePaging.
	 * 
	 * This flag controls whether the created {@link DataTable}
	 * will page its rows or not. This setting may change at any
	 * time and its change affect any tables created from that
	 * point on.
	 *
	 * @param disablePaging the disablePaging to set
	 */
	public void setDisablePaging(Boolean disablePaging) {
		this.disablePaging = disablePaging;
	}

}
