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

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Exports a query to a byte output stream.
 * 
 * This operation is meant to serve as a base class for operations
 * that extract data from a query and writes them to a binary stream.
 * 
 * @param <R>
 *        Type of Entity returned by the query.  
 * @param <Q> 
 *        Type of query.
 */
public abstract class QueryExporterOperation<R, Q extends Query> 
extends AbstractQueryCrawlerOperation<Q>{
	
	/**
	 * Transformation for each row.
	 */
	protected Transformation<R, byte[]> rowTransformation;
	
	/**
	 * Output stream.
	 */
	protected NamedStream<?> out;
	
	/**
	 * Logical name of output.
	 */
	String outputName;
	
	
	@Override
	public void init(Provider parent) throws InitializationException {		
		super.init(parent);
		NamedStreamsProvider nsp = getResource(NamedStreamsProvider.class);
		this.out = nsp.getStream(outputName);
	}

	/**
	 * Creates a new QueryExporterOperation object. 
	 *
	 * @param query
	 *        Query to export.
	 * @param rowTransformation
	 *        Transforms the query row to a byte array that is written to the stream. 
	 * @param outputName 
	 *        Logical name of output stream.
	 */
	public QueryExporterOperation(Q query, Transformation<R, byte[]> rowTransformation, String outputName) {
		super(query);
		this.rowTransformation = rowTransformation;
		this.outputName = outputName;
	}
	
	@Override
	protected void handleRow() throws DataException {
		R row = getCurrentRow();
		byte[] bytes = rowTransformation.execute(row);
		if (bytes!=null) {
			out.writeRecord(bytes);
		}		
	}
	
	/**
	 * Gets the current record.
	 * 
	 * @return Gets the current record.
	 * 
	 * @throws DataAccessException 
	 */
	protected abstract R getCurrentRow() throws DataAccessException;
	
	

}
