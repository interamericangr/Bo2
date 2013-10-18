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
package gr.interamerican.bo2.utils.meta.exceptions;

import org.junit.Test;

/**
 * 
 */
public class TestConversionException {

	
	/**
	 * test constructors
	 */
	@SuppressWarnings("unused")
	@Test
	public void testConstructors(){
		
	ConversionException exception = new ConversionException();
	
	ConversionException exceptionWithMessage = new ConversionException("message"); //$NON-NLS-1$
	
	ParseException e = new ParseException("parseMessage"); //$NON-NLS-1$
	ConversionException exceptionWithCause = new ConversionException("message",e); //$NON-NLS-1$
	
	}
	
}
