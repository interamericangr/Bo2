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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * 
 */
public class TestMessages {

	
	/**
	 * tests GetString when object is null
	 */
	@Test
	public void testGetString(){
		String object = null;
		String key = "1"; //$NON-NLS-1$
		String expected = "!1! null"; //$NON-NLS-1$
		String actual = Messages.getString(key, object);
		assertEquals(expected,actual);
	}
}
