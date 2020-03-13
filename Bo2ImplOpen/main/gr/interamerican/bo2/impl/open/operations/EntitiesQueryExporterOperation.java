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
import gr.interamerican.bo2.arch.exceptions.DataAccessException;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Exports an EntitiesQuery to a byte output stream.
 *
 * @param <Q>
 *        Type of query.
 * @param <R>
 *        Type of entity fetched by the query.
 */
public class EntitiesQueryExporterOperation
<Q extends EntitiesQuery<R>, R>
extends QueryExporterOperation<R, Q> {

	/**
	 * Creates a new EntitiesQueryExporterOperation.
	 * 
	 * @param query
	 *        Entities query.
	 * @param rowTransformation
	 *        Transformation that transforms the entities fetched by the query
	 *        to a byte array that will be written in the output stream. 
	 * @param outputName
	 *        Name of output stream.
	 */
	public EntitiesQueryExporterOperation
	(Q query, Transformation<R, byte[]> rowTransformation, String outputName) {
		super(query, rowTransformation, outputName);
	}

	@Override
	protected R getCurrentRow() throws DataAccessException {		
		return query.getEntity();
	}
}