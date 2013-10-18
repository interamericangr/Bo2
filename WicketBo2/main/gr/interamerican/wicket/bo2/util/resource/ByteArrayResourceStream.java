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
package gr.interamerican.wicket.bo2.util.resource;

import gr.interamerican.wicket.util.resource.ByteArrayAsResourceStream;

/**
 * ByteArrayResourceStream is a simple AbstractResourceStream
 * that takes a data byte array as argument to its constructor.
 * 
 * @deprecated Use {@link ByteArrayAsResourceStream} instead.
 */
public class ByteArrayResourceStream extends ByteArrayAsResourceStream {

    /**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	

    
    /**
     * Creates a new ByteArrayResourceStream.
     * @param data 
     */
    public ByteArrayResourceStream(byte[] data) {
        super(data);        
    }
    
   
}
