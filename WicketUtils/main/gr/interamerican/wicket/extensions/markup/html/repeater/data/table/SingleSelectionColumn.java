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

import gr.interamerican.wicket.markup.html.panel.DataTableRadioButtonPanel;

import java.io.Serializable;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

/**
 * A {@link DataTable} column that allows to select a single row of the rows
 * contained in the table.
 * 
 * @param <T>
 *            the type of the object that will be rendered in this column's
 *            cells
 * @param <S>
 *            the type of the sort property
 */
public class SingleSelectionColumn<T extends Serializable, S>
extends AbstractColumn<T, S> {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Public Constructor.
	 *
	 * @param displayModel
	 *            the display model
	 */
	public SingleSelectionColumn(IModel<String> displayModel) {
		super(displayModel);
	}

	@Override
	public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, final IModel<T> rowModel) {
		cellItem.add(new DataTableRadioButtonPanel<>(componentId, rowModel));
	}
}