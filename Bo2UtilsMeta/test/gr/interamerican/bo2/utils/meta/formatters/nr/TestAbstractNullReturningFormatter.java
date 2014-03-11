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
package gr.interamerican.bo2.utils.meta.formatters.nr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Abstract formatter that returns null, if the specified 
 * object is null.
 */
public class TestAbstractNullReturningFormatter {

	/**
	 * writer to test
	 */
	AbstractNullReturningFormatter<Object> formatter = new AbstractNullReturningFormatter<Object>() {
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;
		@Override
		protected String mainFormat(Object t) {
			return "not null"; //$NON-NLS-1$
		}
	};
	
	/**
	 * Tests format(t) an integer
	 */
	@Test
	public void testFormat_notNull(){
		Object o = new Object();
		String expected = "not null";  //$NON-NLS-1$
		String actual = formatter.format(o);
		assertEquals(expected,actual);
	}
	

	/**
	 * Tests format(t) an integer
	 */
	@Test
	public void testFormat_null(){
		assertNull(formatter.format(null));
	}
	
}
