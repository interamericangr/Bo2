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
package gr.interamerican.bo2.utils.meta.parsers;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ParserResolver}.
 */
public class TestParserResolver {
	
	/**
	 * Unit test for getParser.
	 */
	@Test
	public void testGetParser() {
		Assert.assertTrue(ParserResolver.getParser(Boolean.class) instanceof BooleanParser);
		Assert.assertTrue(ParserResolver.getParser(Date.class) instanceof DateParser);
		Assert.assertTrue(ParserResolver.getParser(Integer.class) instanceof IntegerParser);
		Assert.assertTrue(ParserResolver.getParser(Long.class) instanceof LongParser);
		Assert.assertTrue(ParserResolver.getParser(Short.class) instanceof ShortParser);
		Assert.assertTrue(ParserResolver.getParser(String.class) instanceof StringParser);
	}

}
