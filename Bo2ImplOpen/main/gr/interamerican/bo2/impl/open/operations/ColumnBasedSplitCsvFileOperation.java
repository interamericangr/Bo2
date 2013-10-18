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
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamSimpleQuery;
import gr.interamerican.bo2.impl.open.records.CsvRecord;
import gr.interamerican.bo2.impl.open.streams.StreamsProvider;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.annotations.Child;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This operation splits a CSV file into one or more CSV files depending
 * on the value of a column. The input is the logical name of the CSV stream
 * and the (0-based) number of the column on which the splitting is based. 
 * The result is a Map<String, File> where String is the discriminator column 
 * value and File a File pointing to the filesystem location the corresponding 
 * rows have been written to. 
 */
public class ColumnBasedSplitCsvFileOperation extends AbstractOperation {
	
	/**
	 * columns separator
	 */
	private char separator = CsvRecord.DEFAULT_SEPARATOR;
	
	/**
	 * Index of discriminator column.
	 */
	private int index;
	
	/**
	 * Logical name of input stream file.
	 */
	private String inputStreamName;
	
	/**
	 * Indicates if the first row of the input file must be treated
	 * as a heading, that will not be taken into account for splitting,
	 * but will be placed in the first row of all resulting files instead.
	 */
	private boolean inputHasHeading;
	
	/**
	 * Keeps the heading, if inputHasHeading is true.
	 */
	private String heading;
	
	/**
	 * Output streams.
	 */
	private Map<String, PrintStream> outputs = new HashMap<String, PrintStream>();
	
	/**
	 * Output stream descriptors.
	 */
	private Map<String, String> results = new HashMap<String, String>();
	
	/**
	 * Streams provider for writing output files.
	 */
	private StreamsProvider streamsProvider;
	
	/**
	 * Query for the main CSV file.
	 */
	@Child private NamedStreamSimpleQuery query = new NamedStreamSimpleQuery();
	
	/**
	 * Creates a new ColumnBasedSplitCsvFileOperation object. 
	 * @param inputStreamName 
	 * @param index 
	 * @param separator 
	 * @param inputHasHeading 
	 */
	public ColumnBasedSplitCsvFileOperation(String inputStreamName, int index, char separator, boolean inputHasHeading) {
		this.index = index;
		this.inputStreamName = inputStreamName;
		this.query.setStreamName(inputStreamName);
		this.query.setManagerName(this.getManagerName());
		this.separator = separator;
		this.inputHasHeading = inputHasHeading;
	}
	
	/**
	 * Creates a new ColumnBasedSplitCsvFileOperation object. 
	 * @param inputStreamName 
	 * @param index 
	 */
	public ColumnBasedSplitCsvFileOperation(String inputStreamName, int index) {		
		this(inputStreamName, index, CsvRecord.DEFAULT_SEPARATOR, false);
	}
	
	/**
	 * Creates a new ColumnBasedSplitCsvFileOperation object. 
	 * @param inputStreamName 
	 * @param index 
	 * @param inputHasHeading
	 *        
	 */
	public ColumnBasedSplitCsvFileOperation(String inputStreamName, int index, boolean inputHasHeading) {		
		this(inputStreamName, index, CsvRecord.DEFAULT_SEPARATOR, inputHasHeading);
	}
	
	/**
	 * Creates a new ColumnBasedSplitCsvFileOperation object. 
	 * @param inputStreamName 
	 * @param index 
	 * @param separator 
	 */
	public ColumnBasedSplitCsvFileOperation(String inputStreamName, int index, char separator) {
		this(inputStreamName, index, separator, false);
	}
	
	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		streamsProvider = getResource(StreamsProvider.class);		
	}

	@Override
	public void execute() throws LogicException, DataException {
		query.execute();
		while (query.next()) {
			if(inputHasHeading && query.getRow()==1) {
				heading = query.getRecord().getBuffer();
			} else {
				String row = query.getRecord().getBuffer();
				String[] fields = TokenUtils.splitTrim(row, separator);
				String value = fields[index];
				PrintStream out = getPrintStream(value);
				out.println(row);
			}
		}
		closeFiles();
	}
	
	/**
	 * Gets the printStream for the specified column value. When opening
	 * a new stream, if inputHasHeading is true, it will also add the heading.
	 * 
	 * @param columnValue
	 * @return Returns the printstream.
	 * @throws DataException
	 */
	private PrintStream getPrintStream(String columnValue) throws DataException {		
		PrintStream stream = outputs.get(columnValue);
		if (stream==null) {
			String path = StringUtils.concat
				(inputStreamName, StringConstants.MINUS, StringUtils.toString(columnValue), ".csv" ); //$NON-NLS-1$
			OutputStream out = streamsProvider.getOutputStream(path);
			stream = new PrintStream(out);
			outputs.put(columnValue, stream);
			results.put(columnValue, path);
			if(inputHasHeading) {
				stream.println(heading);
			}
		}		
		return outputs.get(columnValue);
	}
	
	
	/**
	 * Closes the files.
	 */
	private void closeFiles() {
		Collection<PrintStream> streams = outputs.values();
		for (PrintStream printStream : streams) {
			printStream.close();
		}
	}
	
	/**
	 * Gets the results.
	 *
	 * @return Returns the results
	 */
	public Map<String, String> getResults() {
		return results;
	}

}
