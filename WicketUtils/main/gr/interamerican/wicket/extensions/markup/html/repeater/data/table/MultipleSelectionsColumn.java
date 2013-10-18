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
package gr.interamerican.wicket.extensions.markup.html.repeater.data.table;

import gr.interamerican.wicket.markup.html.panel.DataTableCheckBoxPanel;

import java.io.Serializable;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * A {@link DataTable} column that allows to select multiple rows
 * from the rows contained in the table.
 * 
 * @param <B> Type of object selected.
 */
public class MultipleSelectionsColumn<B  extends Serializable> 
extends AbstractColumn<B> {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new SingleSelectionColumn object. 
	 *
	 * @param displayModel
	 */
	public MultipleSelectionsColumn(IModel<String> displayModel) {
		super(displayModel);
	}

	public void populateItem(Item<ICellPopulator<B>> cellItem, String componentId, final IModel<B> rowModel) {
		DataTableCheckBoxPanel<B> checkBoxPanel = new DataTableCheckBoxPanel<B>(componentId, rowModel);
		cellItem.add(checkBoxPanel);
	}

}
