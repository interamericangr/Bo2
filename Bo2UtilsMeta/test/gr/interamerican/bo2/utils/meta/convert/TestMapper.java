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
package gr.interamerican.bo2.utils.meta.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import gr.interamerican.bo2.utils.meta.exceptions.ConversionException;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.IntegerParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 
 */
public class TestMapper {

	/**
	 * generic writer
	 */
	ObjectFormatter writer = ObjectFormatter.INSTANCE;
	
	/**
	 * integerParser
	 */
	Parser<Integer> parser = new IntegerParser();
	
	/**
	 * map with values
	 */
	Map<String, String> map = new HashMap<String, String>();
	
	
	/**
	 * mapper to test
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	Mapper mapper = new Mapper(writer, parser, map);

	
	/**
	 * Tests convert string to integer
	 * @throws ConversionException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConvert() throws ConversionException{
		map.put("0", "10"); //$NON-NLS-1$ //$NON-NLS-2$
		Integer expected = 10;
		Integer actual = (Integer) mapper.convert(0);
		assertEquals(expected,actual);
	}
	
	
	/**
	 * Tests convert an object which has't been mapped
	 * @throws ConversionException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConvert_NotMappedObjects() throws ConversionException{
        assertNull(mapper.convert(0));
	}
	
	/**
	 * Tests convert an object which can't be converted
	 * @throws ConversionException 
	 */
	@SuppressWarnings("unchecked")
	@Test(expected=ConversionException.class)
	public void testConvert_NoConvertedValue() throws ConversionException{
		map.put("0", "noIntegerValue"); //$NON-NLS-1$ //$NON-NLS-2$
		mapper.convert(0);
	}

	
	/**
	 * Tests convert an object which can't be converted
	 */
	@Test
	public void testGetDescription(){
		mapper.setDescription("description"); //$NON-NLS-1$
		assertEquals("description",mapper.getDescription()); //$NON-NLS-1$
	}
	
}

