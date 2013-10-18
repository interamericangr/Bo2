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
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import org.junit.Test;

/**
 * 
 */
public class TestTypedSelectableFormatter {

	
	/**
	 * TypedSelectableFormatter to test
	 */
	TypedSelectableFormatter formatter = new TypedSelectableFormatter();
	
	/**
	 * sample
	 */
	TypedSelectableImpl<Integer> impl = new TypedSelectableImpl<Integer>();
	
	/**
	 * CODE
	 */
	private static final Integer CODE = 1;
	
	/**
	 * TYPE_ID
	 */
	private static final Long TYPE_ID = 2L;
	
	/**
	 * TYPE_ID
	 */
	private static final Long SUBTYPE_ID = 0L;
	
	/**
	 * Tests format
	 */
	@Test
	public void testFormat(){
		impl.setCode(CODE);
		impl.setTypeId(TYPE_ID);
		impl.setSubTypeId(SUBTYPE_ID);
		String expected =  StringUtils.concat(TYPE_ID.toString(),StringConstants.COMMA,SUBTYPE_ID.toString(),StringConstants.COMMA,CODE.toString());
		String actual = formatter.format(impl);
		assertEquals(expected,actual);
	}
	
}
