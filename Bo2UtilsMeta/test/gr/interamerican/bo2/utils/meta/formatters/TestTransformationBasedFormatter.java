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
package gr.interamerican.bo2.utils.meta.formatters;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.adapters.ToString;

import org.junit.Test;

/**
 * Unit test for {@link TransformationBasedFormatter}.
 */
public class TestTransformationBasedFormatter {
	
	/**
	 * Formatter to test.
	 */
	TransformationBasedFormatter<Object> formatter = 
		new TransformationBasedFormatter<Object>(new ToString());

	/**
	 * Tests format(t) a date value
	 */
	@Test
	public void testFormat(){		
		Object o = new Object();
		String expected = formatter.transformation.execute(o); 
		String actual = formatter.format(o);
		assertEquals(expected,actual);
	}
	
}
