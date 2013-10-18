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
package gr.interamerican.bo2.impl.open.streams;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Basic implementation of StreamsProvider.
 */
public class StreamsProviderImpl implements StreamsProvider {
	
	/**
	 * Key of temporary directory value from resource bundle.
	 */
	static final String WORK_DIR="workDir"; //$NON-NLS-1$
	
	/**
	 * Managed streams.
	 */
	private Map<String, Closeable> managedStreams = new HashMap<String, Closeable>();
	
	/**
	 * Path to temporary directory.
	 */
	private String workDir;
	
	/**
	 * Creates a new StreamsProviderImpl object. 
	 * 
	 * @param properties
	 *        Properties object with input for this NamedStreamCreator.
	 */
	public StreamsProviderImpl(Properties properties) {
		workDir = properties.getProperty(WORK_DIR);
		workDir = Utils.notNull(workDir, StringConstants.EMPTY);
	}
	
	/**
	 * Creates a new StreamsProviderImpl object. 
	 */
	public StreamsProviderImpl() {
		workDir = StringConstants.EMPTY;		
	}
		
	public InputStream getInputStream(String fileDescriptor) throws DataException {
		try {
			String path = getPath(fileDescriptor);
			File file = new File(path);
			FileInputStream stream = new FileInputStream(file);
			return stream;
		} catch (FileNotFoundException e) {
			throw new DataException(e);
		}
	}

	public OutputStream getOutputStream(String fileDescriptor) throws DataException {
		try {			
			String path = getPath(fileDescriptor);
			File file = new File(path);
			FileOutputStream stream = new FileOutputStream(file);
			return stream;
		} catch (FileNotFoundException e) {
			throw new DataException(e);
		}
	}
	
	@Override
	public InputStream getManagedInputStream(String fileDescriptor) throws DataException {
		InputStream is = getInputStream(fileDescriptor);
		managedStreams.put(fileDescriptor, is);
		return is;
	}

	@Override
	public OutputStream getManagedOutputStream(String fileDescriptor) throws DataException {
		OutputStream os = getOutputStream(fileDescriptor);
		managedStreams.put(fileDescriptor, os);
		return os;
	}
	
	/**
	 * Real path for the fileDescriptor.
	 * 
	 * @param fileDescriptor
	 * @return Returns the path.
	 */
	protected String getPath(String fileDescriptor) {
		if(workDir.endsWith(File.separator)) {
			return workDir + fileDescriptor;
		}
		return workDir + File.separator + fileDescriptor;
	}

	public void close() throws DataException {
		/* 
		 * Unmanaged streams should be explicitly closed by the program
		 * that opened them.  
		 */		
		for(Closeable c : managedStreams.values()) {
			try {
				c.close();
			} catch (IOException e) {
				throw new DataException(e);
			}
		}
	}

}
