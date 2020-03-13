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

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;

/**
 * Exports a query to a CSV file.<br>
 * The CSV File to export is defined as a {@link NamedStream}, and the method
 * {@link NamedStream#writeString(String)} is called for each line.
 * 
 * @param <R>
 *            Type of Entity returned by the query.
 * @param <Q>
 *            Type of query.
 */
public class EntitiesQuery2CsvOperation<R, Q extends EntitiesQuery<R>>
extends AbstractEntitiesQuery2CsvOperation<R, Q> {

	/**
	 * Output stream.
	 */
	NamedStream<?> out;

	/**
	 * Logical name of output.
	 */
	private final String outputName;

	/** Indicates if the stream is shared,. */
	boolean useSharedStream = false;

	/**
	 * Public Constructor.
	 *
	 * @param query
	 *            Query to get the data from.
	 * @param setup
	 *            Setup for this
	 * @param outputName
	 *            Logical name of output stream.
	 */
	public EntitiesQuery2CsvOperation(Q query, ExportDataSetup<R> setup, String outputName) {
		super(query, setup);
		this.outputName = outputName;
	}

	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		NamedStreamsProvider nsp = getResource(NamedStreamsProvider.class);
		if (useSharedStream) {
			out = nsp.getSharedStream(outputName);
		} else {
			out = nsp.getStream(outputName);
		}
	}

	/**
	 * Assigns a new value to the useSharedStream.
	 *
	 * @param useSharedStream
	 *            the useSharedStream to set
	 */
	public void setUseSharedStream(boolean useSharedStream) {
		this.useSharedStream = useSharedStream;
	}

	@Override
	protected void writeLine(String line) throws DataException {
		out.writeString(line);
	}
}