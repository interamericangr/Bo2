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
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

import java.io.PrintStream;
import java.util.Map;

/**
 * Exports a query as a CSV file to a {@link PrintStream}.<br>
 * This is just an implementation of the
 * {@link AbstractEntitiesQuery2CsvOperation} that uses a {@link PrintStream}.
 * 
 * @param <R>
 *            Type of Entity returned by the query.
 * @param <Q>
 *            Type of query.
 * @deprecated Switch to {@link gr.interamerican.bo2.impl.open.operations.functional.EntitiesQuery2CsvPrintStreamOperation}
 */
@Deprecated
public class EntitiesQuery2CsvPrintStreamOperation<R, Q extends EntitiesQuery<R>> 
extends AbstractEntitiesQuery2CsvOperation<R, Q> {
	
	/** Output. */
	PrintStream out;
	
	/**
	 * 
	 * Creates a new EntitiesQuery2CsvOperation object. 
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
	 * @param out
	 *        Output {@link PrintStream}
	 */
	public EntitiesQuery2CsvPrintStreamOperation
	(Q query, String[] properties, Formatter<?>[] formatters, String[] headers, boolean nullTolerant, PrintStream out) {
		super(query, properties, formatters, headers, nullTolerant);
		this.out = out;
	}
	
	/**
	 * Creates a new EntitiesQuery2CsvOperation object. 
	 *
	 * @param query        Query to get the data from.
	 * @param properties        Array with the properties to extract from the entities fetched by the 
	 *        query. Can be also nested properties.
	 * @param specialFormatters        A map containing any special (non-default) formatters required along with
	 *        the index of the property they correspond to.
	 * @param headers        Headers of each property.
	 * @param out the out
	 */
	public EntitiesQuery2CsvPrintStreamOperation
	(Q query, String[] properties, Map<Integer, Formatter<?>> specialFormatters, String[] headers, PrintStream out) {
		this(query,properties,getFormatters(properties.length, specialFormatters),headers,false, out);
	}

	@Override
	protected void writeLine(String line) throws DataException {
		out.println(line);
	}
}