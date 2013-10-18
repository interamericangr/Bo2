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
package gr.interamerican.bo2.arch.records;

/**
 * Record.
 * 
 *
 */
public interface Record {
	
	
	/**
	 * Sets the whole record.
	 *  
	 * @param arg String that will assign its value to the record.
	 */
	public void setBuffer(String arg);
	
	/**
	 * Sets the whole record.
	 *  
	 * @param arg Array of bytes that will assign its value to the record.
	 */
	public void setBytes(byte[] arg);
	
	
	/**
	 * Gets the whole record as a String.
	 * 
	 * @return Returns the record as a String
	 */
	public String getBuffer();
	
	/**
	 * Gets the record contents as an array of bytes.
	 * 
	 * @return Returns the contents of the record as a byte array.
	 */
	public byte[] getBytes();

}
