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
 * 
 */
public class TestConversionUtils {
	
	/**
	 * generic writer
	 */
	ObjectFormatter writer = ObjectFormatter.INSTANCE;
	
	/**
	 * BooleanParser
	 */
	Parser<Boolean> booleanParser = new BooleanParser();
	
	/**
	 * map with values
	 */
	Map<String, String> map = new HashMap<String, String>();
	
	
	/**
	 * mapper to test
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Mapper mapper = new Mapper(writer, booleanParser, map);
	
	/**
	 * ConversionUtils
	 */
	ConversionUtils utils = new ConversionUtils();
	
	/**
	 * Tests convert a string to boolean
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConvert(){
		map.put("0", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		Boolean expected = true;
		Boolean actual = (Boolean) ConversionUtils.convert(mapper, 0);
		assertEquals(expected,actual);
	}
	
	/**
	 * Tests convert a no integer value to integer 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(expected = RuntimeException.class)
	public void testConvert_ConversionException(){
		Parser<Integer> intParser = new IntegerParser();
		Mapper mapper = new Mapper(writer, intParser, map);
		map.put("0", "noIntegerValue"); //$NON-NLS-1$ //$NON-NLS-2$
        ConversionUtils.convert(mapper, 0);
	}
	
	
	/**
	 * tests MandatoryConvert
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testMandatoryConvert(){
		map.put("0", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		Boolean expected = true;
		Boolean actual = (Boolean) ConversionUtils.mandatoryConvert(mapper, 0);
		assertEquals(expected,actual);
	}
	
	/**
	 * tests MandatoryConvert
	 */
	@SuppressWarnings("unchecked")
	@Test(expected=RuntimeException.class)
	public void testMandatoryConvert_nullValue(){
		map.put("0", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		ConversionUtils.mandatoryConvert(mapper, 1);
		
	}
}
