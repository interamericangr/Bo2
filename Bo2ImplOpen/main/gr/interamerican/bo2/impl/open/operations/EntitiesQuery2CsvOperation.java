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
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;

import java.util.Map;

/**
 * Exports a query to a CSV file.<br>
 * The CSV File to export is defined as a {@link NamedStream}, and the method
 * {@link NamedStream#writeString(String)} is called for each line.
 * 
 * @param <R>
 *            Type of Entity returned by the query.
 * @param <Q>
 *            Type of query.
 * @deprecated Switch to {@link gr.interamerican.bo2.impl.open.operations.functional.EntitiesQuery2CsvOperation}
 */
@Deprecated
public class EntitiesQuery2CsvOperation<R, Q extends EntitiesQuery<R>> 
extends AbstractEntitiesQuery2CsvOperation<R, Q>{
	/**
	 * Output stream.
	 */
	protected NamedStream<?> out;
	/**
	 * Logical name of output.
	 */
	String outputName;
	
	/** Indicates if the stream is shared,. */
	boolean useSharedStream = false;
	
	@Override
	public void init(Provider parent) throws InitializationException {		
		super.init(parent);
		NamedStreamsProvider nsp = getResource(NamedStreamsProvider.class);
		if(useSharedStream) {
			out = nsp.getSharedStream(outputName);
		} else {
			out = nsp.getStream(outputName);
		}
	}	
	
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
	 * @param outputName
	 *        Logical name of output stream.
	 * @param headers 
	 *        Headers of each property.
	 * @param nullTolerant 
	 *        Indicates if the nested property fetching should be null tolerant. @see GetProperties
	 */
	public EntitiesQuery2CsvOperation
	(Q query, String[] properties, Formatter<?>[] formatters, String outputName, String[] headers, boolean nullTolerant) {
		super(query, properties, formatters, headers, nullTolerant);
		this.outputName = outputName;
	}
	
	/**
	 * Creates a new EntitiesQuery2CsvOperation object. 
	 *
	 * @param query
	 *        Query to get the data from.
	 * @param properties
	 *        Array with the properties to extract from the entities fetched by the 
	 *        query. Can be also nested properties.
	 * @param specialFormatters
	 *        A map containing any special (non-default) formatters required along with
	 *        the index of the property they correspond to.
	 * @param outputName
	 *        Logical name of output stream.
	 * @param headers 
	 *        Headers of each property.
	 */
	public EntitiesQuery2CsvOperation
	(Q query, String[] properties, Map<Integer, Formatter<?>> specialFormatters, String outputName, String[] headers) {
		this(query,properties,getFormatters(properties.length, specialFormatters),outputName,headers,false);
	}
	
	/**
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
	 * @param outputName
	 *        Logical name of output stream.
	 * @param headers 
	 *        Headers of each property.
	 */
	public EntitiesQuery2CsvOperation
	(Q query, String[] properties, Formatter<?>[] formatters, String outputName, String[] headers) {
		this(query,properties,formatters,outputName,headers,false);
	}
	
	
	
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
	 * @param outputName
	 *        Logical name of output stream.
	 */
	public EntitiesQuery2CsvOperation
	(Q query, String[] properties, Formatter<?>[] formatters, String outputName) {
		this(query, properties, formatters, outputName, properties);
	}
	
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
	 * @param outputName
	 *        Logical name of output stream.
	 * @param nullTolerant 
	 *        Indicates if the nested property fetching should be null tolerant. @see GetProperties
	 */
	public EntitiesQuery2CsvOperation
	(Q query, String[] properties, Formatter<?>[] formatters, String outputName, boolean nullTolerant) {
		this(query, properties, formatters, outputName, properties, nullTolerant);
	}
	
	/**
	 * 
	 * Creates a new EntitiesQuery2CsvOperation object. 
	 *
	 * @param query
	 *        Query to get the data from.
	 * @param properties
	 *        Array with the properties to extract from the entities fetched by the 
	 *        query. Can be also nested properties.
	 *        entities' properties. 
	 * @param outputName
	 *        Logical name of output stream.
	 * @param headers 
	 *        Headers of each property.
	 * @param nullTolerant 
	 *        Indicates if the nested property fetching should be null tolerant. @see GetProperties
	 */
	public EntitiesQuery2CsvOperation
	(Q query, String[] properties, String outputName, String[] headers, boolean nullTolerant) {
		this(query,properties,defaultFormatters(properties.length), outputName, headers, nullTolerant);		
	}
	
	/**
	 * 
	 * Creates a new EntitiesQuery2CsvOperation object. 
	 *
	 * @param query
	 *        Query to get the data from.
	 * @param properties
	 *        Array with the properties to extract from the entities fetched by the 
	 *        query. Can be also nested properties.
	 * @param outputName
	 *        Logical name of output stream.
	 * @param headers 
	 *        Headers of each property.
	 */
	public EntitiesQuery2CsvOperation
	(Q query, String[] properties, String outputName, String[] headers) {
		this(query,properties,defaultFormatters(properties.length),outputName,headers,false);
	}
	
	/**
	 * 
	 * Creates a new EntitiesQuery2CsvOperation object. 
	 *
	 * @param query
	 *        Query to get the data from.
	 * @param properties
	 *        Array with the properties to extract from the entities fetched by the 
	 *        query. Can be also nested properties.
	 * @param outputName
	 *        Logical name of output stream.
	 */
	public EntitiesQuery2CsvOperation
	(Q query, String[] properties, String outputName) {
		this(query, properties, defaultFormatters(properties.length), outputName, properties);
	}
	
	/**
	 * 
	 * Creates a new EntitiesQuery2CsvOperation object. 
	 *
	 * @param query
	 *        Query to get the data from.
	 * @param properties
	 *        Array with the properties to extract from the entities fetched by the 
	 *        query. Can be also nested properties.
	 * @param outputName
	 *        Logical name of output stream.
	 * @param nullTolerant 
	 *        Indicates if the nested property fetching should be null tolerant. @see GetProperties
	 */
	public EntitiesQuery2CsvOperation
	(Q query, String[] properties, String outputName, boolean nullTolerant) {
		this(query, properties, defaultFormatters(properties.length), outputName, properties, nullTolerant);
	}

	/**
	 * Assigns a new value to the useSharedStream.
	 *
	 * @param useSharedStream the useSharedStream to set
	 */
	public void setUseSharedStream(boolean useSharedStream) {
		this.useSharedStream = useSharedStream;
	}

	@Override
	protected void writeLine(String line) throws DataException {
		out.writeString(line);
	}
}
