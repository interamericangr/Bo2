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

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.functions.SerializableFunction;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

/**
 * An {@link IColumn} that contains a {@link Label}
 * 
 * @param <T>
 *            the type of the object that will be rendered in this column's
 *            cells
 * @param <S>
 *            the type of the sort property
 */
public class GenericLabelColumn<T, S> extends AbstractColumn<T, S> implements ExportableColumn<T, S, String> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A Function that extracts the text to be displayed on the Label
	 */
	private final SerializableFunction<T, String> textExtractor;

	/**
	 * Public Constructor.
	 *
	 * @param displayModel
	 *            Display model for the header of the column
	 * @param textExtractor
	 *            A Function that extracts the text to be displayed on the Label
	 */
	public GenericLabelColumn(IModel<String> displayModel, SerializableFunction<T, String> textExtractor) {
		super(displayModel);
		this.textExtractor = textExtractor;
	}

	@Override
	public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> rowModel) {
		cellItem.add(new Label(componentId, textExtractor.apply(rowModel.getObject())));
	}

	@Override
	public String getHeader() {
		return getDisplayModel().getObject();
	}

	@Override
	public SerializableFunction<T, String> getFunction() {
		return textExtractor;
	}

	@Override
	public Formatter<String> getFormatter() {
		return null;
	}
}