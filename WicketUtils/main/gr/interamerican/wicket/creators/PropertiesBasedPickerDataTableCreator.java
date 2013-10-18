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

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.SingleSelectionColumn;
import gr.interamerican.wicket.util.resource.StringResourceUtils;
import gr.interamerican.wicket.util.resource.WellKnownResourceIds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;

/**
 * Implementation of a {@link DataTableCreator} based on
 * an array of given properties. If the properties given
 * are empty or null, then all the properties of the bean
 * are used.
 * 
 * @param <B> type of bean.
 * 
 */
public class PropertiesBasedPickerDataTableCreator
<B extends Serializable>
extends PropertiesBasedDataTableCreator<B>{
	
	/**
	 * Label of select column.
	 */
	 IModel<String> selectColumnLabelModel;
	
	/**
	 * Creates a new PropertiesBasedDataTableCreator object. 
	 * 
	 * @param beanClass
	 *        Type of row bean. 
	 * @param properties
	 *        Ordered properties of the bean to show on the table columns.  
	 */
	public PropertiesBasedPickerDataTableCreator(Class<B> beanClass, String[] properties) {
		this(beanClass, properties, null, null, null);
	}
	
	/**
	 * Creates a new PropertiesBasedDataTableCreator object. 
	 * 
	 * @param beanClass
	 *        Type of row bean. 
	 * @param properties
	 *        Ordered properties of the bean to show on the table columns.  
	 * @param selectColumnLabelModel 
	 *        Label for the radioButtons column.
	 */
	public PropertiesBasedPickerDataTableCreator(Class<B> beanClass, String[] properties, IModel<String> selectColumnLabelModel) {
		this(beanClass, properties, null, selectColumnLabelModel, null);
	}
	
	/**
	 * Creates a new PropertiesBasedDataTableCreator object. 
	 * 
	 * @param beanClass
	 *        Type of row bean. 
	 * @param properties
	 *        Ordered properties of the bean to show on the table columns.  
	 * @param selectColumnLabelModel 
	 *        Label for the radioButtons column.
	 * @param sortProperty 
	 *        Default sorting property. 
	 */
	public PropertiesBasedPickerDataTableCreator(Class<B> beanClass, String[] properties, IModel<String> selectColumnLabelModel, String sortProperty) {
		this(beanClass, properties, null, selectColumnLabelModel, sortProperty);
	}
	
	/**
	 * Creates a new PropertiesBasedDataTableCreator object. 
	 * 
	 * @param beanClass 
	 *        Type of row bean.
	 * @param properties 
	 *        Ordered properties of the bean to show on the table columns.
	 * @param labels 
	 *        Ordered labels for the properties shown on the table columns.
	 * @param selectColumnLabelModel 
	 *        Label for the radioButtons column.
	 */
	public PropertiesBasedPickerDataTableCreator(Class<B> beanClass, String[] properties, String[] labels, IModel<String> selectColumnLabelModel) {
		this(beanClass, properties, labels, selectColumnLabelModel, null);
	}
	
	/**
	 * Creates a new PropertiesBasedDataTableCreator object. 
	 * 
	 * @param beanClass 
	 *        Type of row bean.
	 * @param properties 
	 *        Ordered properties of the bean to show on the table columns.
	 * @param labels 
	 *        Ordered labels for the properties shown on the table columns.
	 * @param selectColumnLabelModel 
	 *        Label for the radioButtons column.
	 * @param sortProperty 
	 *        Default sorting property. 
	 */
	public PropertiesBasedPickerDataTableCreator(Class<B> beanClass, String[] properties, String[] labels, IModel<String> selectColumnLabelModel, String sortProperty) {
		this(beanClass, properties, labels, selectColumnLabelModel, sortProperty, null);
	}
	
	/**
	 * Creates a new PropertiesBasedDataTableCreator object. 
	 * 
	 * @param beanClass 
	 *        Type of row bean.
	 * @param properties 
	 *        Ordered properties of the bean to show on the table columns.
	 * @param labels 
	 *        Ordered labels for the properties shown on the table columns.
	 * @param selectColumnLabelModel 
	 *        Label for the radioButtons column.
	 * @param sortProperty 
	 *        Default sorting property. 
	 * @param formatters 
	 *        Formatters Object of properties for column rendering.
	 */
	public PropertiesBasedPickerDataTableCreator(Class<B> beanClass, String[] properties, String[] labels, IModel<String> selectColumnLabelModel, String sortProperty, Formatter<?>[] formatters) {
		super(beanClass, properties, labels, sortProperty, formatters);
		this.selectColumnLabelModel = selectColumnLabelModel;
	}
	
	@Override
	public List<IColumn<B>> createColumns() {
		selectColumnLabelModel = StringResourceUtils.getResourceModel(
			WellKnownResourceIds.PDT_SELECT_LABEL, null, selectColumnLabelModel, StringConstants.EMPTY);
		List<IColumn<B>> tableColumns = new ArrayList<IColumn<B>>();
		tableColumns.add(new SingleSelectionColumn<B>(selectColumnLabelModel));
		tableColumns.addAll(super.createColumns());
		return tableColumns;
	}

}
