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
package gr.interamerican.bo2.impl.open.namedstreams;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.beans.PropertiesInitializedBean;

import java.util.Properties;

/**
 * Bean used to get a list of beans defined in a properties file.
 */
public class StreamsList 
extends PropertiesInitializedBean {
	
	/**
	 * Creates a new StreamsList object. 
	 *
	 * @param properties
	 */
	public StreamsList(Properties properties) {
		super(properties);		
	}

	/**
	 * input streams.
	 */
	private String bufferedReaders;
	
	/**
	 * input streams.
	 */
	private String printStreams;
	
	/**
	 * input streams.
	 */	
	private String inputStreams;
	
	/**
	 * input streams.
	 */	
	private String outputStreams;

	/**
	 * Gets the bufferedReaders.
	 *
	 * @return Returns the bufferedReaders
	 */
	public String getBufferedReaders() {
		return bufferedReaders;
	}

	/**
	 * Assigns a new value to the bufferedReaders.
	 *
	 * @param bufferedReaders the bufferedReaders to set
	 */
	public void setBufferedReaders(String bufferedReaders) {
		this.bufferedReaders = bufferedReaders;
	}

	/**
	 * Gets the printStreams.
	 *
	 * @return Returns the printStreams
	 */
	public String getPrintStreams() {
		return printStreams;
	}

	/**
	 * Assigns a new value to the printStreams.
	 *
	 * @param printStreams the printStreams to set
	 */
	public void setPrintStreams(String printStreams) {
		this.printStreams = printStreams;
	}

	/**
	 * Gets the inputStreams.
	 *
	 * @return Returns the inputStreams
	 */
	public String getInputStreams() {
		return inputStreams;
	}

	/**
	 * Assigns a new value to the inputStreams.
	 *
	 * @param inputStreams the inputStreams to set
	 */
	public void setInputStreams(String inputStreams) {
		this.inputStreams = inputStreams;
	}

	/**
	 * Gets the outputStreams.
	 *
	 * @return Returns the outputStreams
	 */
	public String getOutputStreams() {
		return outputStreams;
	}

	/**
	 * Assigns a new value to the outputStreams.
	 *
	 * @param outputStreams the outputStreams to set
	 */
	public void setOutputStreams(String outputStreams) {
		this.outputStreams = outputStreams;
	}
	
	/**
	 * Gets the outputStreams.
	 *
	 * @return Returns the outputStreams
	 */
	public String[] getOutputStreamsArray() {
		return getArray(outputStreams);
	}
	
	/**
	 * Gets the bufferedReaders.
	 *
	 * @return Returns the bufferedReaders
	 */
	public String[] getBufferedReadersArray() {
		return getArray(bufferedReaders);
	}
	
	/**
	 * Gets the printStreams.
	 *
	 * @return Returns the printStreams
	 */
	public String[] getPrintStreamsArray() {		
		return getArray(printStreams);
	}
	
	/**
	 * Gets the inputStreams.
	 *
	 * @return Returns the inputStreams
	 */
	public String[] getInputStreamsArray() {
		return getArray(inputStreams);
	}
	
	/**
	 * Gets an array with the elements contained in the property.
	 * 
	 * Empty elements are omitted.
	 * 
	 * @param property
	 * @return Returns an array of strings.
	 */
	private String[] getArray(String property) {
		if (property==null) {
			return new String[0];
		}
		String[] array = TokenUtils.split(property, StringConstants.COMMA);
		return StringUtils.removeEmpty(array);
	}
	

}
