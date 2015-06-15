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
package gr.interamerican.bo2.impl.open.namedstreams.resourcetypes;

import gr.interamerican.bo2.impl.open.namedstreams.types.StreamType;

/**
 * Type of resource behind a stream.
 */
public enum StreamResourceEnum implements StreamResource {
	/**
	 * The stream is based on a File.
	 */
	FILE(),
	
	/**
	 * The stream is in a byte array.
	 */
	BYTES(),
	
	/**
	 * The stream is a classpath resource stream. 
	 */
	CLASSPATH(),
	
	/**
	 * The stream is a system stream. 
	 */
	SYSTEM(),
	
	/**
	 * The stream is a resource accessible over HTTP
	 * This stream supports only input {@link StreamType}s
	 */
	HTTP();
	
	/**
	 * Set the factory to each enum.
	 * This is done like this, because the factories depend on the
	 * enums, so they can't be created prior to them.
	 */
	static {
		FILE.setFactory(new FileNsFactory());
		BYTES.setFactory(new ByteNsFactory());
		CLASSPATH.setFactory(new ClasspathNsFactory());
		SYSTEM.setFactory(new SystemStreamNsFactory());
		HTTP.setFactory(new HttpNsFactory());
	}
	
	
	/**
	 * Factory.
	 */
	NamedStreamFactory factory;
	

	/**
	 * Creates a new StreamResource.
	 * 
	 * @param factory
	 */
	private StreamResourceEnum() {
		/* empty */
	}

	
	@Override
	public NamedStreamFactory getFactory() {
		return factory;
	}


	/**
	 * Sets the factory.
	 *
	 * @param factory the factory to set
	 */
	private void setFactory(NamedStreamFactory factory) {
		this.factory = factory;
	}
	
	
	
	
	

}
