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
import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.EnhancedPropertyColumn;
import gr.interamerican.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import gr.interamerican.wicket.factories.ColumnFactory;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;

/**
 * Implementation of a {@link DataTableCreator} based on
 * an array of given properties. If the properties given
 * are empty or null, then all the properties of the bean
 * are used.
 * 
 * @param <B> type of bean.
 * 
 */
public class PropertiesBasedDataTableCreator
<B extends Serializable> 
extends AbstractDataTableCreator<B>{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Class of B
	 */
	protected Class<B> beanClass;
	
	/**
	 * Ordered bean properties to show.
	 */
	protected String[] properties;
	
	/**
	 * Ordered labels of bean properties.
	 */
	protected String[] labels;
	
	/**
	 * Ordered Formatters.
	 */
	protected Formatter<?>[] formatters;
	
	/**
	 * Default sort property.
	 */
	protected String sortProperty;
	
	/**
	 * Extra columns. These are appended after the property columns.
	 */
	protected ArrayList<IColumn<B>> extraColumns = new ArrayList<IColumn<B>>();
	
	/**
	 * Indicates if this table will be tolerant to faulty property expressions.
	 * This may be useful when the user knowingly represents instances of different
	 * types in the rows (e.g. 2 sub-classes of a base type) and the properties
	 * that need to be displayed do not exist in both types.
	 */
	protected boolean faultyExpressionsTolerant = false;
	
	/**
	 * Value to be shown for a faulty expression, if they are allowed.
	 */
	protected String faultyValue;
	
	/**
	 * Creates a new PropertiesBasedDataTableCreator object. 
	 * 
	 * @param beanClass 
	 *        Type of row bean.
	 * @param properties
	 *        Ordered properties of the bean to show on the table columns. 
	 */
	public PropertiesBasedDataTableCreator(Class<B> beanClass, String[] properties) {
		this(beanClass, properties, null, null);
	}
	
	/**
	 * Creates a new PropertiesBasedDataTableCreator object. 
	 * 
	 * @param beanClass 
	 *        Type of row bean.
	 * @param properties
	 *        Ordered properties of the bean to show on the table columns. 
	 * @param sortProperty
	 *        Default sorting property. 
	 */
	public PropertiesBasedDataTableCreator(Class<B> beanClass, String[] properties, String sortProperty) {
		this(beanClass, properties, null, sortProperty);
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
	 */
	public PropertiesBasedDataTableCreator(Class<B> beanClass, String[] properties, String[] labels) {
		this(beanClass, properties, labels, null);
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
	 * @param sortProperty
	 *        Default sorting property.
	 */
	public PropertiesBasedDataTableCreator(Class<B> beanClass, String[] properties, String[] labels, String sortProperty) {
		this(beanClass, properties, labels, sortProperty, null);
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
	 * @param sortProperty
	 *        Default sorting property.
	 * @param formatters 
	 *        Formatters Object of properties for column rendering.
	 */
	public PropertiesBasedDataTableCreator(Class<B> beanClass, String[] properties, String[] labels, String sortProperty, Formatter<?>[] formatters) {
		super();
		this.beanClass = beanClass;
		this.properties = properties;
		this.labels = labels;
		this.sortProperty = sortProperty;
		this.formatters = formatters;
		processNullOrEmptyProperties();
	}

	@Override
	public DataTable<B> createDataTable(String id, List<B> elements) {
		List<B> data = elements;
		if(sortProperty != null) {
			data = CollectionUtils.sort(data, beanClass, sortProperty);
		}
		SortableDataProvider<B> provider = new SortableDataProvider<B>(data, beanClass);
		return new DefaultDataTable<B>(id, createColumns(), provider, Utils.notNull(rowsPerPage, 10));
	}
	
	/**
	 * Creates the columns of the table.
	 * 
	 * @return the columns.
	 */
	protected List<IColumn<B>> createColumns() {
		List<IColumn<B>> columns = ColumnFactory.createPropertyColumns(properties, labels, properties, faultyExpressionsTolerant, formatters);
		for(IColumn<B> column : columns) {
			if(column instanceof EnhancedPropertyColumn && !StringUtils.isNullOrBlank(faultyValue)) {
				((EnhancedPropertyColumn<B>)column).setFaultyValue(faultyValue);
			}
		}
		columns.addAll(extraColumns);
		return columns;
	}
	
	/**
	 * If the properties is empty or null, all the bean's properties are used.
	 */
	private void processNullOrEmptyProperties() {
		if(properties == null || properties.length == 0) {
			PropertyDescriptor[] pds = JavaBeanUtils.getBeansProperties(beanClass);
			List<String> props = new ArrayList<String>();
			for (PropertyDescriptor pd : pds) {
				props.add(pd.getName());
			}
			properties = props.toArray(new String[]{});
			return;
		}
	}
	
	/**
	 * Adds user-created extra columns to the DataTable.
	 * The user is responsible for adding labels to these columns.
	 * 
	 * @param columns
	 */
	public void addExtraColumns(IColumn<B>... columns) {
		for(IColumn<B> column : columns) {
			extraColumns.add(column);
		}
	}
	
	/**
	 * Assigns a value to tolerantToFaultyExpressions.
	 * 
	 * @param tolerant
	 */
	public void setTolerantToFaultyExpressions(boolean tolerant) {
		this.faultyExpressionsTolerant = tolerant;
	}
	
	/**
	 * Assigns a value to faultyValue.
	 * 
	 * @param faultyValue
	 */
	public void setFaultyValue(String faultyValue) {
		this.faultyValue = faultyValue;
	}

}
