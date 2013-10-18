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
package gr.interamerican.bo2.impl.open.utils;

import org.junit.Test;

/**
 * 
 */
public class TestExceptions {

	
	/**
	 * test RuntimeException
	 */
	@Test
	public void testRuntime(){
		String key = "key"; //$NON-NLS-1$
		String msg = "msg"; //$NON-NLS-1$
		String extraInfo = "extraInfo"; //$NON-NLS-1$
		Exceptions.runtime(key, msg, extraInfo);
	}
	
	
	/**
	 * tests initializationException
	 */
	@Test
	public void testInitializationException(){
		String key = "key"; //$NON-NLS-1$
		Exceptions.initializationException(key);
	}
	
}
