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
package gr.interamerican.bo2.impl.open.records;

import gr.interamerican.bo2.arch.records.Record;

import java.nio.charset.Charset;

/**
 * Simple implementation of {@link Record}.
 */
public class SimpleRecord extends AbstractBaseRecord {
	
	/**
	 * Record buffer.
	 */
	String buffer;

	/**
	 * Creates a new SimpleRecord object. 
	 */
	public SimpleRecord() {
		super();
	}
	
	/**
	 * Creates a new SimpleRecord object. 
	 * @param charset 
	 */
	public SimpleRecord(Charset charset) {
		super();
		setCharset(charset);
	}
	
	public void setBuffer(String buffer) {
		this.buffer = buffer;		
	}

	public void setBytes(byte[] bytes) {
		setBuffer(new String(bytes, charset()));		
	}

	public String getBuffer() {
		return buffer;
	}
	
	public byte[] getBytes() {
		return buffer.getBytes(charset());
	}

}
