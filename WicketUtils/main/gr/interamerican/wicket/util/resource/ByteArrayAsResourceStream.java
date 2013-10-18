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
package gr.interamerican.wicket.util.resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

/**
 * ByteArrayResourceStream is a simple AbstractResourceStream
 * that takes a data byte array as argument to its constructor.
 */
public class ByteArrayAsResourceStream extends AbstractResourceStream {

    /**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * data byte array.
	 */
	protected byte[] fileData;
	
	/**
     * Input stream.
     */ 
    private InputStream inputStream;
    
    /**
     * Creates a new ByteArrayResourceStream.
     * @param data 
     */
    public ByteArrayAsResourceStream(byte[] data) {
        super();
        fileData = data;
    }
    
    public void close() throws IOException {
    	if (inputStream!=null) {
    		inputStream.close();    		
    	}        
    }

    public InputStream getInputStream() throws ResourceStreamNotFoundException {
        inputStream = new ByteArrayInputStream(fileData);
        return inputStream;
    }

}
