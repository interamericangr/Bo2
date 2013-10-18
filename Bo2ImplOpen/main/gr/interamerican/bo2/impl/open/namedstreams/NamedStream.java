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

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;

import java.nio.charset.Charset;



/**
 * Wrapper around a stream object.
 * 
 * In order to support unified handling of named streams
 * in mainframe and workstation environments a stream is
 * wrapped inside a StreamInfo object. Stream operations
 * are used through the StreamInfo object. <br/>
 * The NamedStream interface is common for input and
 * output streams, as well as for sequential and indexed
 * files. Therefore it has methods for reading, writing
 * and finding records. In case the underlying stream 
 * implementation can't support any of the interface methods,
 * then these method should throw a DataOperationNotSupportedException
 * with a message that explains that the operation is not
 * supported by the underlying stream.
 * 
 * @param <T> 
 *        Type of stream.
 *
 */
public interface NamedStream<T> {
	
	/**
	 * Gets the stream.
	 * 
	 * @return returns the stream.
	 */
	public T getStream();
	

    /**
     * Gets the logical name of the stream.
     * 
     * @return Returns the stream logical name.
     */
    public String getName();
    
    /**
     * Gets the record length.
     * 
     * @return Returns the recordLength.
     */
    public int getRecordLength();
    
    /**
     * Reads the next record from the stream.
     * 
     * 
     * @return returns the next record as an array of bytes
     *          when the stream reaches end of file returns null.
     *           
     * @throws DataException 
     *         if an exception occurs during the IO operations.
     * @throws DataOperationNotSupportedException
     *         if the stream has been opened in read mode.
     *         
     */
    public byte[] readRecord() 
    throws DataException, DataOperationNotSupportedException;
    
    
    /**
     * Reads the next record from the stream and returns it to a string.
     * 
     * @return returns the next record as an array of bytes
     *          when the stream reaches end of file returns null.
     *           
     * @throws DataException 
     *         if an exception occurs during the IO operations.
     * @throws DataOperationNotSupportedException
     *         if the stream has been opened in read mode.
     *         
     */
    public String readString() 
    throws DataException, DataOperationNotSupportedException;
         
    
    /**
     * Writes a record to the stream.
     * 
     * @param record 
     *        record that will be written to the stream.
     *           
     * @throws DataException 
     *         if an exception occurs during the IO operations.
     * @throws DataOperationNotSupportedException
     *         if the stream has been opened in read mode.
     */
    public void writeRecord(byte[] record) 
    throws DataException, DataOperationNotSupportedException;
    
    /**
     * Writes a string to the stream.
     * 
     * @param string 
     *        string that will be written to the stream.
     *           
     * @throws DataException 
     *         if an exception occurs during the IO operations.
     * @throws DataOperationNotSupportedException
     *         if the stream has been opened in read mode.
     */
    public void writeString(String string) 
    throws DataException, DataOperationNotSupportedException;
    
	
	/**
	 * Finds a record with a specified key.
	 * 
	 * @param key of the record to be located
	 * @return returns true if the record is located.
	 * 
     * @throws DataException 
     *         if an exception occurs during the IO operations.
     * @throws DataOperationNotSupportedException
     *         if the stream has been opened in read mode.
	 */
	public boolean find(byte[] key) 
	throws DataException, DataOperationNotSupportedException;
	
	/**
	 * Closes the underlying stream object.
	 * 
     * @throws DataException 
     *         if an exception occurs during the IO operations.
     * @throws DataOperationNotSupportedException
     *         if the stream has been opened in read mode.
	 */
	public void close()
	throws DataException, DataOperationNotSupportedException;
	
	/**
	 * Gets the {@link StreamType}.
	 * 
	 * @return Returns the type of this stream.
	 */
	public StreamType getType();
	
	/**
	 * Gets the {@link StreamResource}.
	 * 
	 * @return Returns the resource type backing this stream.
	 */
	public StreamResource getResourceType();
 
	/**
	 * Gets the resource.
	 * 
	 * @return Returns the resource backing this stream.
	 */
	public Object getResource();
	
	/**
	 * Gets the encoding used by this stream. There is no setter for this property! 
	 * 
	 * @return Encoding used by the stream.
	 */
	public Charset getEncoding();

}
