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
package gr.interamerican.bo2.gui.handlers;

import org.junit.Test;

/**
 * Test for {@link PopUpExceptionHandler}.
 */
public class TestPopUpExceptionHandler {
	
	/**
	 * Test for handle(t)
	 */
	@Test(expected=RuntimeException.class)
	public void testHandle_exception() {		
		Exception ex = new InstantiationException();
		PopUpExceptionHandler.INSTANCE.handle(ex);		
	}
	
	/**
	 * Test for handle(t)
	 */
	@Test(expected=Error.class)
	public void testHandle_error() {		
		Error ex = new Error();
		PopUpExceptionHandler.INSTANCE.handle(ex);		
	}


}
