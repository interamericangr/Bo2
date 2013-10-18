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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Unit tests for {@link StringAsResourceStream}.
 */
public class TestStringAsResourceStream {
	
	/**
	 * tests the one arg constructor.
	 */
	@Test
	public void testConstructor_with1Arg() {
		String string = "string"; //$NON-NLS-1$
		StringAsResourceStream stream = new StringAsResourceStream(string);
		assertNotNull(stream);
		assertEquals(string, stream.string);
	}
	
	/**
	 * tests the one arg constructor.
	 */
	@Test
	public void testConstructor_with2Arg() {
		String contentType = "text"; //$NON-NLS-1$
		String string = "string"; //$NON-NLS-1$
		StringAsResourceStream stream = new StringAsResourceStream(contentType,string);
		assertNotNull(stream);
		assertEquals(string, stream.string);
		assertEquals(contentType, stream.getContentType());
	}

}
