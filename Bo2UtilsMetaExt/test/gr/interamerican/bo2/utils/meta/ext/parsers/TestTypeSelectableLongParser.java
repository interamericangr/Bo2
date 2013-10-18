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
package gr.interamerican.bo2.utils.meta.ext.parsers;

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link TypeSelectableLongParser}.
 */
public class TestTypeSelectableLongParser {

	/**
	 *  The parser.
	 */
	static TypeSelectableLongParser parser = 
		new TypeSelectableLongParser();
	
	/**
	 * Test for  {@link TypeSelectableLongParser#parse(String)}.
	 * @throws ParseException 
	 */
	@Test
	public void testParser() throws ParseException{
		String input = "1,2,3"; //$NON-NLS-1$
		TypedSelectable<Long> typeSelectable = parser.parse(input);
		Assert.assertNotNull(typeSelectable);
		
		Assert.assertEquals("1",typeSelectable.getTypeId().toString()); //$NON-NLS-1$
		Assert.assertEquals("2",typeSelectable.getSubTypeId().toString()); //$NON-NLS-1$
		Assert.assertEquals("3",typeSelectable.getCode().toString()); //$NON-NLS-1$
	}
}
