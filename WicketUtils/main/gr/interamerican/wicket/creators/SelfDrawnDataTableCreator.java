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

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;

import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;

/**
 * {@link DataTableCreator} implementation that dynamically picks data table
 * columns based on a {@link BusinessObjectDescriptor}. <br>
 * This DataTableCreator provides only coarse-grained data table creation
 * options, that can be obtained from a BusinessObjectDescriptor. Specifically,
 * it is possible to specify the columns, their order and their labels. More
 * fine-grained options (e.g. what is the default sorting property) will likely
 * require enhancements on the BusinessObjectDescriptor and BoPropertyDescriptor
 * types.
 * 
 * @param <T>
 *            Type of bean that the created data tables show.
 * 
 * @see PropertiesBasedDataTableCreator
 * @see PropertiesBasedPickerDataTableCreator
 * @see PropertiesBasedMultipleSelectionsDataTableCreator
 * 
 * @deprecated Use {@link FunctionalDataTableCreator#addSelfDrawnColumns(BusinessObjectDescriptor)}
 */
@Deprecated
public class SelfDrawnDataTableCreator<T extends Serializable>
implements DataTableCreator<T> {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** BusinessObjectDescriptor. */
	BusinessObjectDescriptor<T> objectDescriptor;

	/**
	 * DataTableCreator type to delegate data table creation to.
	 */
	@SuppressWarnings("rawtypes")
	Class<? extends DataTableCreator> creatorType;

	/**
	 * Type of beans rendered by the data tables created by this
	 * DataTableCreator.
	 */
	Class<T> type;

	/**
	 * Creates a new SelfDrawnDataTableCreator object.
	 *
	 * @param type
	 *            Type of beans rendered by the data tables created by this
	 *            DataTableCreator
	 * @param objectDescriptor
	 *            the object descriptor
	 * @param creatorType
	 *            one of PropertiesBasedDataTableCreator,
	 *            PropertiesBasedPickerDataTableCreator,
	 *            PropertiesBasedMultipleSelectionsDataTableCreator
	 */
	@SuppressWarnings("rawtypes")
	public SelfDrawnDataTableCreator(Class<T> type, BusinessObjectDescriptor<T> objectDescriptor,
			Class<? extends DataTableCreator> creatorType) {
		this.type = type;
		this.objectDescriptor = objectDescriptor;
		this.creatorType = creatorType;
	}

	@Override
	public DataTable<T, String> createDataTable(String id, List<T> elements) {
		DataTableCreator<T> delegate = creator();
		return delegate.createDataTable(id, elements);
	}

	/**
	 * Creates the DataTableCreator to delegate to.
	 * 
	 * @return DataTableCreator instance
	 */
	DataTableCreator<T> creator() {
		List<BoPropertyDescriptor<?>> propertyDescriptors = objectDescriptor.getPropertyDescriptors();
		String[] properties = new String[propertyDescriptors.size()];
		String[] labels = new String[propertyDescriptors.size()];

		List<BoPropertyDescriptor<?>> sorted = CollectionUtils.sort(propertyDescriptors,
				BoPropertyDescriptor::getIndex);

		int ctr = 0;
		for (BoPropertyDescriptor<?> boPropertyDescriptor : sorted) {
			properties[ctr] = boPropertyDescriptor.getName();
			labels[ctr] = boPropertyDescriptor.getLabel();
			ctr++;
		}

		if (creatorType == PropertiesBasedDataTableCreator.class) {
			return new PropertiesBasedDataTableCreator<T>(type, properties, labels);
		}

		if (creatorType == PropertiesBasedPickerDataTableCreator.class) {
			return new PropertiesBasedPickerDataTableCreator<T>(type, properties, labels, null);
		}

		if (creatorType == PropertiesBasedMultipleSelectionsDataTableCreator.class) {
			return new PropertiesBasedMultipleSelectionsDataTableCreator<T>(type, properties, null, labels);
		}

		throw new RuntimeException("Unknown " + creatorType); //$NON-NLS-1$
	}
}