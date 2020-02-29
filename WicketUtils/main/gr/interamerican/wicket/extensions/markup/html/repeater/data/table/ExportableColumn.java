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

import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;

import gr.interamerican.bo2.utils.functions.SerializableFunction;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.wicket.markup.html.panel.listTable.ExportButtonOptions;

/**
 * A column that can have it's data easily exported.<br>
 * This is used from {@link ExportButtonOptions}.
 * 
 * @param <B>
 *            the type of the object that will be rendered in this column's
 *            cells
 * @param <S>
 *            the type of the sorting parameter
 * @param <I>
 *            the final object displayed
 */
public interface ExportableColumn<B, S, I>
		extends IColumn<B, S> {

	/**
	 * Header to display
	 * 
	 * @return header
	 */
	String getHeader();

	/**
	 * Function that retrieves the entity from a row to display.
	 * 
	 * @return function
	 */
	SerializableFunction<B, I> getFunction();

	/**
	 * Formatter to use upon the displayed entity.
	 * 
	 * @return formatter
	 */
	Formatter<I> getFormatter();
}