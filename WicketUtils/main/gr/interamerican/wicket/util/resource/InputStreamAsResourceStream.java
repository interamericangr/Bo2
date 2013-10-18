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

import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.util.resource.AbstractResourceStream;

/**
 * InputStreamAsResourceStream is a simple AbstractResourceStream
 * that takes an InputStream as argument to its constructor.
 */
public class InputStreamAsResourceStream 
extends AbstractResourceStream {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * InputStream.
	 */
	InputStream stream;

	/**
	 * Creates a new InputStreamAsResourceStream object. 
	 *
	 * @param stream
	 */
	public InputStreamAsResourceStream(InputStream stream) {
		super();
		this.stream = stream;
	}

	public InputStream getInputStream() {
		return stream;
	}

	public void close() throws IOException {
		stream.close();		
	}

}
