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
package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.records.CsvRecord;
import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.adapters.trans.GetProperties;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.transformations.FormatArray;

import java.util.List;
import java.util.Map;

/**
 * Exports a query to a CSV file.
 * 
 * @param <R>
 *        Type of Entity returned by the query.  
 * @param <Q> 
 *        Type of query.
 * @deprecated Switch to {@link gr.interamerican.bo2.impl.open.operations.functional.AbstractEntitiesQuery2CsvOperation}
 */
@Deprecated
public abstract class AbstractEntitiesQuery2CsvOperation<R, Q extends EntitiesQuery<R>> 
extends AbstractQueryCrawlerOperation<Q>{
	/**
	 * Extracts the values of the properties.
	 */
	GetProperties get;
	/**
	 * Formats the values of the properties.
	 */
	FormatArray format;
	/**
	 * Record to print.
	 */
	CsvRecord record;
	
	/** headers. */
	String[] headers;
	
	/**
	 * Rows to write before anything else. 
	 */
	List<String> firstRows;
	
	/**
	 * Rows to write after everything else.
	 */
	List<String> lastRows;

	/**
	 * Creates a Formatters table with the specified length.
	 * 
	 * @param length
	 *            Length for the Required Formatters Array
	 * 
	 * @return Formatter array.
	 */
	static Formatter<?>[] defaultFormatters(int length) {
		Formatter<?>[] formatters = new Formatter<?>[length];
		for (int i = 0; i < formatters.length; i++) {
			formatters[i] = ObjectFormatter.INSTANCE;
		}
		return formatters;
	}

	/**
	 * Creates an array with formatters taking into account any non-default formatters
	 * specified.
	 *
	 * @param length            Length for the Required Formatters Array
	 * @param specialFormatters the special formatters
	 * @return Formatter array.
	 */
	static Formatter<?>[] getFormatters(int length, Map<Integer, Formatter<?>> specialFormatters) {
		Formatter<?>[] result = defaultFormatters(length);
		if(specialFormatters==null) {
			return result;
		}
		for(Map.Entry<Integer, Formatter<?>> entry : specialFormatters.entrySet()) {
			result[entry.getKey()] = entry.getValue();
		}
		return result;
	}
	
	/**
	 * 
	 * Constructor of AbstractEntitiesQuery2CsvOperation object.
	 *
	 * @param query
	 *        Query to get the data from.
	 * @param properties
	 *        Array with the properties to extract from the entities fetched by the 
	 *        query. Can be also nested properties.
	 * @param formatters
	 *        Array with formatters to use in order to print the values of the 
	 *        entities' properties. 
	 * @param headers 
	 *        Headers of each property.
	 * @param nullTolerant 
	 *        Indicates if the nested property fetching should be null tolerant. @see GetProperties
	 */
	public AbstractEntitiesQuery2CsvOperation
	(Q query, String[] properties, Formatter<?>[] formatters, String[] headers, boolean nullTolerant) {
		super(query);
		this.get = new GetProperties(properties, nullTolerant);
		this.format = new FormatArray(formatters);
		record = new CsvRecord(properties.length);
		this.headers = headers;
	}

	/**
	 * Writes a Line to the output.<br>
	 * Where this line is written depends entirely on the implementation.<br>
	 * It is the responsibility of this method to move to a new line after this
	 * is called.
	 *
	 * @param line            Line to Write
	 * @throws DataException the data exception
	 */
	protected abstract void writeLine(String line) throws DataException;

	/**
	 * Gets the query.
	 * 
	 * @return Returns the query.
	 */
	public Q getQuery() {
		return query;
	}

	@Override
	protected void handleRow() throws DataException {
		R row = query.getEntity();
		Object[] values = get.execute(row);
		String[] columns = format.execute(values);
		record.setFields(columns);
		writeLine(record.getBuffer());					
	}

	@Override
	protected void beforeQuery() throws LogicException, DataException {
		if(!CollectionUtils.isNullOrEmpty(firstRows)) {
			for(String row : firstRows) {
				writeLine(row);
			}
		}
		if(!ArrayUtils.isNullOrEmpty(headers)) {
			record.setFields(headers);
			writeLine(record.getBuffer());
		}
	}

	@Override
	protected void afterQuery() throws LogicException, DataException {
		if(!CollectionUtils.isNullOrEmpty(lastRows)) {
			for(String row : lastRows) {
				writeLine(row);
			}
		}
	}

	/**
	 * Assigns a new value to the firstRows.
	 *
	 * @param firstRows the firstRows to set
	 */
	public void setFirstRows(List<String> firstRows) {
		this.firstRows = firstRows;
	}

	/**
	 * Assigns a new value to the lastRows.
	 *
	 * @param lastRows the lastRows to set
	 */
	public void setLastRows(List<String> lastRows) {
		this.lastRows = lastRows;
	}
}