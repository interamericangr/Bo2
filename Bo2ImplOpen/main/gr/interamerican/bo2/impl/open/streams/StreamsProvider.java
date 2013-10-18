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

import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.exceptions.DataException;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * The StreamsProvider creates and provides streams 
 * using a string argument that can describe each stream.
 * 
 * Even though there can be different implementations
 * for this interface, the initial idea is for an
 * object factory that creates streams and makes them 
 * available to the runtime independent business layer.
 * 
 * 
 */
public interface StreamsProvider extends ResourceWrapper {
	
	/**
	 * Creates an InputStream. This stream must be explicitly closed
	 * by the program that opened it.
	 * 
	 * @param fileDescriptor
	 *        string giving information to the provider
	 *        in order to create the stream. Can be the
	 *        filename or any other information.
	 *        
	 * @return returns an inputStream.
	 * 
	 * @throws DataException
	 */
	public InputStream getInputStream(String fileDescriptor)
	throws DataException;
	
	/**
	 * Creates an InputStream. This stream is managed, and will be closed
	 * by this Provider automatically.
	 * 
	 * @param fileDescriptor
	 *        string giving information to the provider
	 *        in order to create the stream. Can be the
	 *        filename or any other information.
	 *        
	 * @return returns an inputStream.
	 * 
	 * @throws DataException
	 */
	public InputStream getManagedInputStream(String fileDescriptor)
	throws DataException;
	
	/**
	 * Creates an OutputStream. This stream must be explicitly closed
	 * by the program that opened it.
	 * 
	 * @param fileDescriptor
	 *        string giving information to the provider
	 *        in order to create the stream. Can be the
	 *        filename or any other information.
	 *        
	 * @return returns an OutputStream.
	 * 
	 * @throws DataException
	 */
	public OutputStream getOutputStream(String fileDescriptor)
	throws DataException;
	
	/**
	 * Creates an OutputStream. This stream is managed, and will be closed
	 * by this Provider automatically.
	 * 
	 * @param fileDescriptor
	 *        string giving information to the provider
	 *        in order to create the stream. Can be the
	 *        filename or any other information.
	 *        
	 * @return returns an OutputStream.
	 * 
	 * @throws DataException
	 */
	public OutputStream getManagedOutputStream(String fileDescriptor)
	throws DataException;

}
