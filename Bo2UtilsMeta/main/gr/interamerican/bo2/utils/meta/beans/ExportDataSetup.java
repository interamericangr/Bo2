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
package gr.interamerican.bo2.utils.meta.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gr.interamerican.bo2.utils.beans.TypeBasedSelection;
import gr.interamerican.bo2.utils.functions.SerializableFunction;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

/**
 * Setup for Operations that are supposed to export data for a bean.<br>
 * For each column a addColumn method has to be invoked.<br>
 * The order of the columns is the order they are set to this.
 * 
 * @param <R>
 *            Type of Entity to be reported
 */
public class ExportDataSetup<R> implements Serializable {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Rows to write before anything else.
	 */
	private List<String> firstRows;

	/**
	 * Rows to write after everything else.
	 */
	private List<String> lastRows;

	/**
	 * Formatters per type
	 */
	private final TypeBasedSelection<Formatter<?>> typeBasedFormatter = new TypeBasedSelection<>();

	/**
	 * The Columns this Exports
	 */
	private final List<SerializableFunctionWithFormatter<R, ?>> columns = new ArrayList<>();

	/**
	 * Registers a {@link Formatter} of a specific Type.<br>
	 * Do note that this applies on it's subclasses as well (see
	 * {@link TypeBasedSelection})
	 * 
	 * @param clz
	 *            Class of the Type
	 * @param formatter
	 *            Formatter to Register
	 * @param <T>
	 *            Type of the Formatter
	 * @return This for linking
	 */
	public <T> ExportDataSetup<R> registerTypeBasedFormatter(Class<T> clz, Formatter<T> formatter) {
		typeBasedFormatter.registerSelection(clz, formatter);
		return this;
	}

	/**
	 * Adds a column.
	 * 
	 * @param function
	 *            {@link SerializableFunction} used to retrieve the Column Content
	 * @param formatter
	 *            {@link Formatter} used for the Column
	 * @param header
	 *            Header of this Column
	 * @return This for linking
	 */
	public <T> ExportDataSetup<R> addColumn(SerializableFunction<R, T> function, Formatter<T> formatter, String header) {
		columns.add(new SerializableFunctionWithFormatter<>(function, formatter, header));
		return this;
	}

	/**
	 * Adds a column.
	 * 
	 * @param function
	 *            {@link SerializableFunction} used to retrieve the Column Content
	 * @param header
	 *            Header of this Column
	 * @return This for linking
	 */
	public ExportDataSetup<R> addColumn(SerializableFunction<R, ?> function, String header) {
		return addColumn(function, null, header);
	}

	/**
	 * Adds a column.
	 * 
	 * @param function
	 *            {@link SerializableFunction} used to retrieve the Column Content
	 * @param formatter
	 *            {@link Formatter} used for the Column
	 * @return This for linking
	 */
	public <T> ExportDataSetup<R> addColumn(SerializableFunction<R, T> function, Formatter<T> formatter) {
		return addColumn(function, formatter, null);
	}

	/**
	 * Adds a column.
	 * 
	 * @param function
	 *            {@link SerializableFunction} used to retrieve the Column Content
	 * @return This for linking
	 */
	public ExportDataSetup<R> addColumn(SerializableFunction<R, ?> function) {
		return addColumn(function, null, null);
	}

	/**
	 * Assigns a new value to the firstRows.
	 *
	 * @param firstRows
	 *            the firstRows to set
	 */
	public void setFirstRows(List<String> firstRows) {
		this.firstRows = firstRows;
	}

	/**
	 * Assigns a new value to the lastRows.
	 *
	 * @param lastRows
	 *            the lastRows to set
	 */
	public void setLastRows(List<String> lastRows) {
		this.lastRows = lastRows;
	}

	/**
	 * Gets the firstRows.
	 *
	 * @return Returns the firstRows
	 */
	public List<String> getFirstRows() {
		return firstRows;
	}

	/**
	 * Gets the lastRows.
	 *
	 * @return Returns the lastRows
	 */
	public List<String> getLastRows() {
		return lastRows;
	}

	/**
	 * Gets the typeBasedFormatter.
	 *
	 * @return Returns the typeBasedFormatter
	 */
	public TypeBasedSelection<Formatter<?>> getTypeBasedFormatter() {
		return typeBasedFormatter;
	}

	/**
	 * Gets the columns.
	 *
	 * @return Returns the columns
	 */
	public List<SerializableFunctionWithFormatter<R, ?>> getColumns() {
		return columns;
	}
}