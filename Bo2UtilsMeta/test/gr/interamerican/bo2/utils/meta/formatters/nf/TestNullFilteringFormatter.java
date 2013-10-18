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
package gr.interamerican.bo2.utils.meta.formatters.nf;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.Replace;

import org.junit.Test;

/**
 * Abstract formatter that returns null, if the specified 
 * object is null.
 */
public class TestNullFilteringFormatter {
	
	/**
	 * Wrapped formatter.
	 */
	Formatter<Object> delegate = new Replace<Object>("f"); //$NON-NLS-1$
	
	/**
	 * writer to test
	 */
	NullFilteringFormatter<Object> formatter = new NullFilteringFormatter<Object>(delegate);
	
	/**
	 * Tests format(t) an integer
	 */
	@Test
	public void testMainFormat(){
		Object o = new Object();
		assertEquals(delegate.format(o), formatter.format(o));
	}

}
