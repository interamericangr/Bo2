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

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.AbstractExportableColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import gr.interamerican.bo2.utils.functions.SerializableComparableFunction;
import gr.interamerican.bo2.utils.functions.SerializableFunction;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.extensions.markup.html.repeater.data.table.ExportableColumn;

/**
 * An {@link IColumn} that is based on a {@link SerializableComparableFunction}
 * (getter) applied in the items it works for.<br>
 * The displayed item is the one returned from the getter.<br>
 * If a {@link Formatter} is provided then it is gonna be used - otherwise a
 * {@link Model} of the resulting entity of the getter is returned.<br>
 * Do note that this Model is later passed on a {@link Label} that uses it to
 * display the Column Content, with the use of
 * {@link Component#getDefaultModelObjectAsString(Object)}.
 * 
 * @param <B>
 *            type of bean of the owning {@link DataTable}
 * @param <C>
 *            type of the entity being displayed
 */
public class FunctionalColumn<B, C extends Comparable<C> & Serializable>
extends AbstractExportableColumn<B, SerializableComparableFunction<B, C>>
implements ExportableColumn<B, SerializableComparableFunction<B, C>, C> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Formatter to apply
	 */
	private Formatter<C> formatter;

	/**
	 * Public Constructor.
	 *
	 * @param displayModel
	 *            Model to be displayed on top
	 * @param sortProperty
	 *            Getter used for sorting and also for displaying
	 * @param formatter
	 *            Formatter (optional)
	 */
	public FunctionalColumn(IModel<String> displayModel, SerializableComparableFunction<B, C> sortProperty, Formatter<C> formatter) {
		super(displayModel, sortProperty);
		this.formatter = formatter;
	}

	@Override
	public IModel<?> getDataModel(IModel<B> rowModel) {
		C item = getSortProperty().apply(rowModel.getObject());
		if (formatter != null && item != null) {
			return Model.of(formatter.format(item));
		}
		return Model.of(item);
	}

	@Override
	public String getHeader() {
		return getDisplayModel().getObject();
	}

	@Override
	public SerializableFunction<B, C> getFunction() {
		return getSortProperty();
	}

	@Override
	public Formatter<C> getFormatter() {
		return formatter;
	}
}