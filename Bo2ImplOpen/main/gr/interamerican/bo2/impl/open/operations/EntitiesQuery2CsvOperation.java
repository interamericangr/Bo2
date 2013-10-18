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
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.records.CsvRecord;
import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.adapters.GetProperties;
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
 */
public class EntitiesQuery2CsvOperation<R, Q extends EntitiesQuery<R>> 
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
	/**
	 * Output stream.
	 */
	protected NamedStream<?> out;
	/**
	 * Logical name of output.
	 */
	String outputName;
	/**
	 * headers
	 */
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
	 * Indicates if the stream is shared,
	 */
	boolean useSharedStream = false;
	
	/**
	 * Creates a Formatters table with the specified length.
	 * 
	 * @param length
	 * 
	 * @return Returns the table.
	 */
	private static Formatter<?>[] defaultFormatters(int length) {
		Formatter<?>[] formatters = new Formatter<?>[length];
		for (int i = 0; i < formatters.length; i++) {
			formatters[i] = ObjectFormatter.INSTANCE;
		}
		return formatters;
	}
	
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
		super(query);
		this.get = new GetProperties(properties, nullTolerant);
		this.format = new FormatArray(formatters);
		this.outputName = outputName;
		record = new CsvRecord(properties.length);
		this.headers = headers;
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
	 * Creates an array with formatters taking into account any non-default formatters
	 * specified.
	 * 
	 * @param properties
	 * @param specialFormatters
	 * 
	 * @return Formatter array.
	 */
	private static Formatter<?>[] getFormatters(int properties, Map<Integer, Formatter<?>> specialFormatters) {
		Formatter<?>[] result = defaultFormatters(properties);
		if(specialFormatters==null) {
			return result;
		}
		for(Map.Entry<Integer, Formatter<?>> entry : specialFormatters.entrySet()) {
			result[entry.getKey()] = entry.getValue();
		}
		return result;
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
		out.writeString(record.getBuffer());					
	}
	
	@Override
	protected void beforeQuery() throws LogicException, DataException {
		if(!CollectionUtils.isNullOrEmpty(firstRows)) {
			for(String row : firstRows) {
				out.writeString(row);
			}
		}
		if(!ArrayUtils.isNullOrEmpty(headers)) {
			record.setFields(headers);
			out.writeString(record.getBuffer());
		}
	}
	
	@Override
	protected void afterQuery() throws LogicException, DataException {
		if(!CollectionUtils.isNullOrEmpty(lastRows)) {
			for(String row : lastRows) {
				out.writeString(row);
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

	/**
	 * Assigns a new value to the useSharedStream.
	 *
	 * @param useSharedStream the useSharedStream to set
	 */
	public void setUseSharedStream(boolean useSharedStream) {
		this.useSharedStream = useSharedStream;
	}

}
