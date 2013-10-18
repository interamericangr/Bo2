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
package gr.interamerican.bo2.utils.meta.ext.formatters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for {@link ShortFormatter}.
 */
public class TestShortFormatter {

	/**
	 * TypedSelectableFormatter to test
	 */
	ShortFormatter formatter = new ShortFormatter();

	/**
	 * Tests {@link ShortFormatter#format(Short)}.
	 */
	@Test
	public void testFormat(){
		Short shortValue = 2;
		String expected =  "2"; //$NON-NLS-1$
		String actual = formatter.format(shortValue);
		assertEquals(expected,actual);
	}
	
	
}
