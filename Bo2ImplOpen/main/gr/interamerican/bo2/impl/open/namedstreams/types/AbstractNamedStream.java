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
package gr.interamerican.bo2.impl.open.namedstreams.types;

import gr.interamerican.bo2.impl.open.namedstreams.NamedStream;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
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
	
	/** stream logical name. */
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
     * Encoding used by this stream.
     */
    String uri;


    /**
     * Creates a new AbstractNamedStream object. 
     *
     * @param streamType the stream type
     * @param resourceType the resource type
     * @param stream the stream
     * @param name the name
     * @param recordLength the record length
     * @param resource the resource
     * @param encoding the encoding
     * @param uri the uri
     */
 	public AbstractNamedStream (
 			StreamType streamType, StreamResource resourceType, 
 			T stream, String name, int recordLength, Object resource, 
 			Charset encoding, String uri) {
 		super();
 		this.name = name;
 		this.recordLength = recordLength;
 		this.streamType = streamType;
 		this.resourceType = resourceType; 		
 		this.stream = stream;
 		this.resource = resource;
 		this.encoding = Utils.notNull(encoding, Bo2UtilsEnvironment.get().getDefaultTextCharset());
 		this.uri = uri;
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
   
      @Override
	public String getName() {
        return name;
    }
       
      @Override
	public int getRecordLength() {
        return recordLength;
    }
   
	@Override
	public T getStream() {
		return stream;
	}	
	
	@Override
	public StreamType getType() {
		return streamType;
	}
	
	@Override
	public StreamResource getResourceType() {
		return resourceType;
	}

	@Override
	public Object getResource() {
		return resource;
	}
	
	@Override
	public Charset getEncoding() {
		return encoding;
	}
	
	@Override
	public String getUri() {
		return uri;
	}
	
}
