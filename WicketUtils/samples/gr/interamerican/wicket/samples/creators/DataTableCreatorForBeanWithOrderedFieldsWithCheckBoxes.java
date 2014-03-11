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
package gr.interamerican.wicket.samples.creators;

import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;
import gr.interamerican.wicket.creators.DataTableCreator;
import gr.interamerican.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import gr.interamerican.wicket.factories.ColumnFactory;
import gr.interamerican.wicket.markup.html.panel.DataTableCheckBoxPanel;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Sample {@link DataTableCreator} that shows a list of
 * {@link BeanWithOrderedFields} with 4 columns. The first
 * three columns show the bean's properties. The last column
 * shows a {@link Radio} for selection of the row.
 */
public class DataTableCreatorForBeanWithOrderedFieldsWithCheckBoxes 
implements DataTableCreator<BeanWithOrderedFields> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * rows per page.
	 */
	public int rowsPerPage = 0;

	@SuppressWarnings("nls")
	public DataTable<BeanWithOrderedFields> 
	createDataTable(String id, List<BeanWithOrderedFields> list) {		
		String propertyNames = "first,second,third";
		
		List<IColumn<BeanWithOrderedFields>> columns =
			ColumnFactory.createPropertyColumns(propertyNames);
		
		IColumn<BeanWithOrderedFields> pickerColumn = new AbstractColumn<BeanWithOrderedFields>(new Model<String>("pickerColumn")) {

			/** serial id */
			private static final long serialVersionUID = 1L;

			public void populateItem(Item<ICellPopulator<BeanWithOrderedFields>> cellItem, 
			String componentId, IModel<BeanWithOrderedFields> rowModel) {
				cellItem.add(new DataTableCheckBoxPanel<BeanWithOrderedFields>(componentId, rowModel));
			}
		};
		
		columns.add(pickerColumn);
		
		SortableDataProvider<BeanWithOrderedFields> dataProvider =
			new SortableDataProvider<BeanWithOrderedFields>
			(list, BeanWithOrderedFields.class);
		
		return new DefaultDataTable<BeanWithOrderedFields>
			(id, columns, dataProvider, rowsPerPage);
	}	

	/**
	 * Gets the rowsPerPage.
	 *
	 * @return Returns the rowsPerPage
	 */
	public Integer getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	 * Assigns a new value to the rowsPerPage.
	 *
	 * @param rowsPerPage the rowsPerPage to set
	 */
	public void setRowsPerPage(Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	
}
