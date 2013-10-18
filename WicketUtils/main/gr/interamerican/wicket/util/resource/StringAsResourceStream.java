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

import org.apache.wicket.util.resource.AbstractStringResourceStream;

/**
 * {@link AbstractStringResourceStream} that has the string in the
 * constructor.
 */
public class StringAsResourceStream extends AbstractStringResourceStream {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * String with the contents of the stream.
	 */
	String string;

	/**
	 * Creates a new StringAsResourceStream object.
	 *  
	 * @param string 
	 *
	 */
	public StringAsResourceStream(String string) {
		super();
		this.string = string;
	}

	/**
	 * Creates a new StringAsResourceStream object. 
	 *
	 * @param contentType
	 * @param string 
	 */
	public StringAsResourceStream(String contentType, String string) {
		super(contentType);
		this.string = string;
	}

	@Override
	protected String getString() {		
		return string;
	}

}
