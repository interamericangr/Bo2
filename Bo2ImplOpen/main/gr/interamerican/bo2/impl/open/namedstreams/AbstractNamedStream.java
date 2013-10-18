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

import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;
import gr.interamerican.bo2.utils.Utils;

import java.nio.charset.Charset;




/**
 * Base class for implementations of {@link NamedStream}.
 * 
 * @param <T> Type of stream.
 */
public abstract class AbstractNamedStream<T> 
implements NamedStream<T> {
	
	/**
	 * The stream used for input.
	 */
	T stream;	
	
	/**
	 * stream logical name
	 */
    String name;
    
    /**
     * logical name of the stream.
     */
    int recordLength;
    
	/**
     * Type of stream. 
     */
    StreamType streamType;
   
	/**
     * Type of stream resource. 
     */
    StreamResource resourceType;
     
	/**
	 * Resource under the stream. 
	 */
    Object resource;

    /**
     * Encoding used by this stream.
     */
    Charset encoding;

    /**
 	 * Creates a new AbstractNamedStream object. 
 	 *
 	 * @param streamType
 	 * @param resourceType
     * @param stream 
     * @param name 
     * @param recordLength 
     * @param resource 
     * @param encoding 
 	 */
 	public AbstractNamedStream (
 			StreamType streamType, StreamResource resourceType, 
 			T stream, String name, int recordLength, Object resource, Charset encoding) {
 		super();
 		this.name = name;
 		this.recordLength = recordLength;
 		this.streamType = streamType;
 		this.resourceType = resourceType; 		
 		this.stream = stream;
 		this.resource = resource;
 		this.encoding = Utils.notNull(encoding, Bo2UtilsEnvironment.getDefaultTextCharset());
 		validate();
 	}
 	
 	/**
 	 * Validates the state of the stream.
 	 */
 	protected void validate() {
 		if (!streamType.isOk(stream)) {
 			String msg = "Stream not supported by StreamType"; //$NON-NLS-1$
 			throw new RuntimeException(msg);
 		}
 	}
   
    public String getName() {
        return name;
    }
       
    public int getRecordLength() {
        return recordLength;
    }
   
	public T getStream() {
		return stream;
	}	
	
	public StreamType getType() {
		return streamType;
	}
	
	public StreamResource getResourceType() {
		return resourceType;
	}

	public Object getResource() {
		return resource;
	}
	
	public Charset getEncoding() {
		return encoding;
	}
	
}
