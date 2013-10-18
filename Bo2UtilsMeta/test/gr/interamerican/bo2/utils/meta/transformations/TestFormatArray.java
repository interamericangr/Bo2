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
package gr.interamerican.bo2.utils.meta.transformations;

import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link FormatArray}.
 */
public class TestFormatArray {
	
	/**
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testExecute() {
		Formatter[] formatters = {
				ObjectFormatter.INSTANCE,
				ObjectFormatter.INSTANCE
		};
		FormatArray f = new FormatArray(formatters);
		Object[] objects = {
				new Object(),
				new Object(),
				new Object()
		};
		String[] actuals = f.execute(objects);
		String[] expecteds = {
				formatters[0].format(objects[0]),
				formatters[1].format(objects[1]),
		};
		Assert.assertArrayEquals(expecteds, actuals);
		
	}

}
