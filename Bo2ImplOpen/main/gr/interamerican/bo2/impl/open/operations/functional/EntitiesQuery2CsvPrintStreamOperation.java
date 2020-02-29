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

import java.io.PrintStream;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;

/**
 * Exports a query as a CSV file to a {@link PrintStream}.<br>
 * This is just an implementation of the
 * {@link AbstractEntitiesQuery2CsvOperation} that uses a {@link PrintStream}.
 * 
 * @param <R>
 *            Type of Entity returned by the query.
 * @param <Q>
 *            Type of query.
 */
public class EntitiesQuery2CsvPrintStreamOperation<R, Q extends EntitiesQuery<R>>
extends AbstractEntitiesQuery2CsvOperation<R, Q> {

	/** Output. */
	private final PrintStream out;

	/**
	 * Public Constructor.
	 *
	 * @param query
	 *            Query to get the data from.
	 * @param setup
	 *            Setup for this
	 * @param out
	 *            Output {@link PrintStream}
	 */
	public EntitiesQuery2CsvPrintStreamOperation(Q query, ExportDataSetup<R> setup, PrintStream out) {
		super(query, setup);
		this.out = out;
	}

	@Override
	protected void writeLine(String line) throws DataException {
		out.println(line);
	}
}