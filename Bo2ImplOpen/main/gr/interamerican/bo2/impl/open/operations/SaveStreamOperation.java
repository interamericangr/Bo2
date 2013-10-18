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
package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.streams.StreamsProvider;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Stores a file.
 */
public class SaveStreamOperation 
extends AbstractOperation {
	/**
	 * Bytes to store.
	 */
	byte[] bytes;
	
	/**
	 * Filename.
	 */
	String filename;
	
	/**
	 * {@link StreamsProvider}.
	 */
	StreamsProvider provider;
	
	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		provider = getResource(StreamsProvider.class);
	}

	@Override
	public void execute() throws LogicException, DataException {
		try {
			OutputStream stream = provider.getOutputStream(filename);
			stream.write(bytes);
			stream.close();
		} catch (IOException e) {
			throw new DataException(e);
		}
	}

	/**
	 * Gets the vytes to store.
	 * 
	 * @return Returns the bytes.
	 */
	public byte[] getBytes() {
		return bytes;
	}

	/**
	 * Sets the bytes to store.
	 * 
	 * @param bytes
	 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	/**
	 * Gets the filename.
	 * 
	 * @return Returns the filename.
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Sets the filename.
	 * 
	 * @param filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

}
