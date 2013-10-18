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
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * StreamsProvider that polls a predefined folder until it finds
 * the stream it looks for. <br/>
 * 
 * {@link PollingStreamsProviderImpl} provides only InputStreams. 
 * It's <code>getInputStream(filename)</code> method expects to find
 * an existing file. If the file does not exist, then the method
 * waits a predefined time interval and then retries to find the file.
 * If a maximum number of retries is exceeded without finding the file,
 * then the method will throw a DataException.
 * The <code>getOutputStream(filename)</code> method is not supported.
 */
public class PollingStreamsProviderImpl implements StreamsProvider {
	
	/**
	 * Key of temporary directory value from resource bundle.
	 */
	static final String WORK_DIR="workDir"; //$NON-NLS-1$
	
	/**
	 * Key of max tries value from resource bundle.
	 */
	static final String MAX_TRIES = "maxTries"; //$NON-NLS-1$
	
	/**
	 * Key of time interval between retries.
	 */
	static final String INTERVAL = "interval"; //$NON-NLS-1$
	
	/**
	 * Managed streams.
	 */
	private Map<String, Closeable> managedStreams = new HashMap<String, Closeable>();
	
	/**
	 * Path to temporary directory.
	 */
	private String workDir;
	
	/**
	 * Path to temporary directory.
	 */
	private int maxTries;
	
	/**
	 * Path to temporary directory.
	 */
	private int interval;
	
	/**
	 * Creates a new StreamsProviderImpl object. 
	 * 
	 * @param properties
	 *        Properties object with input for this NamedStreamCreator.
	 */
	public PollingStreamsProviderImpl(Properties properties) {
		workDir = properties.getProperty(WORK_DIR);
		workDir = Utils.notNull(workDir, StringConstants.EMPTY);
		String mr = properties.getProperty(MAX_TRIES);
		maxTries = NumberUtils.string2Int(mr);
		if (maxTries==0) {
			maxTries = 1;
		}
		String ti = properties.getProperty(INTERVAL);
		interval = NumberUtils.string2Int(ti);
		if (interval==0) {
			interval = 1000;
		}		
	}
	
	/**
	 * Creates a new StreamsProviderImpl object. 
	 */
	public PollingStreamsProviderImpl() {
		workDir = StringConstants.EMPTY;		
		maxTries = 1;
		interval = 1000;		
	}
	
	/**
	 * Opens the specified file if it exists.
	 * 
	 * @param fileDescriptor
	 * 
	 * @return If the file exists, returns the FileInputStream,
	 *         otherwise returns null.
	 */
	InputStream getFile(String fileDescriptor) {
		try {
			String path = getPath(fileDescriptor);
			File file = new File(path);
			FileInputStream stream = new FileInputStream(file);
			return stream;
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Waits for the time interval.
	 */
	void sleep() {
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
		
	public InputStream getInputStream(String fileDescriptor) throws DataException {
		int tries=0;
		while (tries<maxTries) {
			tries++;
			InputStream stream = getFile(fileDescriptor);
			if (stream!=null) {
				return stream;
			}
			sleep();
		}
		String msg = "Could not find file " + fileDescriptor; //$NON-NLS-1$
		throw new DataException(msg);
	}
	
	
	@Override
	public InputStream getManagedInputStream(String fileDescriptor) throws DataException {
		InputStream is = getInputStream(fileDescriptor);
		managedStreams.put(fileDescriptor, is);
		return is;
	}

	public OutputStream getOutputStream(String fileDescriptor) throws DataException {
		throw new DataOperationNotSupportedException();
	}

	@Override
	public OutputStream getManagedOutputStream(String fileDescriptor) throws DataException {
		throw new DataOperationNotSupportedException();
	}
	
	/**
	 * Real path for the fileDescriptor.
	 * 
	 * @param fileDescriptor
	 * @return Returns the path.
	 */
	String getPath(String fileDescriptor) {
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
