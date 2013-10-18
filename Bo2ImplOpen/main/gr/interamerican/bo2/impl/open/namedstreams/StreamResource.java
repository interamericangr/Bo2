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

/**
 * Type of resource behind a stream.
 */
public enum StreamResource {
	/**
	 * The stream is based on a File.
	 */
	FILE,
	
	/**
	 * The stream is in a byte array.
	 */
	BYTES,
	
	/**
	 * The stream is an object.
	 */
	OBJECT,
	
	/**
	 * The stream is a classpath resource stream. 
	 */
	CLASSPATH,
	
	/**
	 * The stream is a system stream. 
	 */
	SYSTEM,
	
	

}
