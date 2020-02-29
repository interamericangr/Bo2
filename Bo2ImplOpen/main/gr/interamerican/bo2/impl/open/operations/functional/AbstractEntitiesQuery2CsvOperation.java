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
package gr.interamerican.bo2.impl.open.operations.functional;

import java.util.ArrayList;
import java.util.List;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.operations.AbstractQueryCrawlerOperation;
import gr.interamerican.bo2.impl.open.records.CsvRecord;
import gr.interamerican.bo2.utils.AdapterUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.SelectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.TypeBasedSelection;
import gr.interamerican.bo2.utils.functions.SerializableFunction;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;
import gr.interamerican.bo2.utils.meta.beans.SerializableFunctionWithFormatter;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

/**
 * Exports a query to a CSV file.<br>
 * See {@link ExportDataSetup} for setup.<br>
 * The priority order of the {@link Formatter} preferred per column is :
 * <ul>
 * <li>The one registered along the Column with
 * {@link ExportDataSetup#addColumn(SerializableFunction, Formatter)}</li>
 * <li>One registered with
 * {@link ExportDataSetup#registerTypeBasedFormatter(Class, Formatter)}
 * (see {@link TypeBasedSelection})</li>
 * <li>Just {@link StringUtils#toString(Object)}</li>
 * </ul>
 * 
 * @param <R>
 *            Type of Entity returned by the query.
 * @param <Q>
 *            Type of query.
 */
public abstract class AbstractEntitiesQuery2CsvOperation<R, Q extends EntitiesQuery<R>>
extends AbstractQueryCrawlerOperation<Q> {

	/**
	 * Record to print.
	 */
	private CsvRecord record;

	/**
	 * Setup for this
	 */
	final ExportDataSetup<R> setup;

	/**
	 * Public Constructor.
	 *
	 * @param query
	 *            Query to get the data from.
	 * @param setup
	 *            Setup for this
	 */
	public AbstractEntitiesQuery2CsvOperation(Q query, ExportDataSetup<R> setup) {
		super(query);
		this.setup = setup;
	}

	/**
	 * Writes a Line to the output.<br>
	 * Where this line is written depends entirely on the implementation.<br>
	 * It is the responsibility of this method to move to a new line after this
	 * is called.
	 *
	 * @param line
	 *            Line to Write
	 * @throws DataException
	 *             the data exception
	 */
	protected abstract void writeLine(String line) throws DataException;

	@Override
	protected void handleRow() throws DataException {
		R row = query.getEntity();
		List<String> results = new ArrayList<>();
		for (SerializableFunctionWithFormatter<R, ?> column : setup.getColumns()) {
			results.add(process(row, column));
		}
		record.setFields(results.toArray(new String[results.size()]));
		writeLine(record.getBuffer());
	}

	/**
	 * Process a column.
	 * 
	 * @param object
	 *            Row Object to process
	 * @param column
	 *            Column to Process
	 * @return Result for this specific row and column
	 */
	<T> String process(R object, SerializableFunctionWithFormatter<R, T> column) {
		T row = column.getFunction().apply(object);
		Formatter<T> formatter = column.getFormatter();
		if (formatter != null) {
			return formatter.format(row);
		}
		@SuppressWarnings("unchecked")
		Formatter<T> typeBased = (Formatter<T>) setup.getTypeBasedFormatter().select(row);
		if (typeBased != null) {
			return typeBased.format(row);
		}
		return StringUtils.toString(row);
	}

	@Override
	protected void beforeQuery() throws LogicException, DataException {
		record = new CsvRecord(setup.getColumns().size());
		if (!CollectionUtils.isNullOrEmpty(setup.getFirstRows())) {
			for (String row : setup.getFirstRows()) {
				writeLine(row);
			}
		}
		if (SelectionUtils.selectFirstWithNotNullProperty(SerializableFunctionWithFormatter::getHeader,
				setup.getColumns()) != null) {
			List<String> headers = AdapterUtils.getProperty(setup.getColumns(), SerializableFunctionWithFormatter::getHeader);
			record.setFields(headers.toArray(new String[headers.size()]));
			writeLine(record.getBuffer());
		}
	}

	@Override
	protected void afterQuery() throws LogicException, DataException {
		if (!CollectionUtils.isNullOrEmpty(setup.getLastRows())) {
			for (String row : setup.getLastRows()) {
				writeLine(row);
			}
		}
	}
}