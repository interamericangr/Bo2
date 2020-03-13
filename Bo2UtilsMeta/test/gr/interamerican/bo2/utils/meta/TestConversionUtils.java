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
package gr.interamerican.bo2.utils.meta;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.meta.convert.Mapper;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.BooleanParser;
import gr.interamerican.bo2.utils.meta.parsers.IntegerParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * The Class TestConversionUtils.
 */
public class TestConversionUtils {

	/**
	 * Tests convert a string to boolean.
	 */
	@Test
	public void testConvert() {
		Map<String, String> map = new HashMap<String, String>();
		Mapper<Object, Boolean> mapper = new Mapper<>(ObjectFormatter.INSTANCE, new BooleanParser(), map);
		map.put("0", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		Boolean expected = true;
		Boolean actual = ConversionUtils.convert(mapper, 0);
		assertEquals(expected, actual);
	}

	/**
	 * Tests convert a no integer value to integer.
	 */
	@Test(expected = RuntimeException.class)
	public void testConvert_ConversionException() {
		Map<String, String> map = new HashMap<String, String>();
		Parser<Integer> intParser = new IntegerParser();
		Mapper<Object, Integer> mapper = new Mapper<>(ObjectFormatter.INSTANCE, intParser, map);
		map.put("0", "noIntegerValue"); //$NON-NLS-1$ //$NON-NLS-2$
		ConversionUtils.convert(mapper, 0);
	}

	/**
	 * tests MandatoryConvert.
	 */
	@Test
	public void testMandatoryConvert() {
		Map<String, String> map = new HashMap<String, String>();
		Mapper<Object, Boolean> mapper = new Mapper<>(ObjectFormatter.INSTANCE, new BooleanParser(), map);
		map.put("0", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		Boolean expected = true;
		Boolean actual = ConversionUtils.mandatoryConvert(mapper, 0);
		assertEquals(expected, actual);
	}

	/**
	 * tests MandatoryConvert.
	 */
	@Test(expected = RuntimeException.class)
	public void testMandatoryConvert_nullValue() {
		Map<String, String> map = new HashMap<String, String>();
		Mapper<Object, Boolean> mapper = new Mapper<>(ObjectFormatter.INSTANCE, new BooleanParser(), map);
		map.put("0", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		ConversionUtils.mandatoryConvert(mapper, 1);
	}
}