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
package gr.interamerican.bo2.arch.utils;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.utils.beans.CodifiedNamedImpl;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import org.junit.Test;

/**
 * 
 */
public class TestBo2ArchStringUtils {

	/**
	 * CODE
	 */
	private static final Integer CODE = 1;
	
	/**
	 * TYPE_ID
	 */
	private static final String NAME = "name1"; //$NON-NLS-1$
	
	/**
	 * codifiedNamedImpl
	 */
	CodifiedNamedImpl<Integer> impl = new CodifiedNamedImpl<Integer>();
	
	/**
	 * tests FormatCodifiedNamed
	 */
	@Test
	public void testFormatCodifiedNamed(){
		impl.setCode(CODE);
		impl.setName(NAME);
		String expected =  StringUtils.concat(CODE.toString(),StringConstants.SPACE,StringConstants.COMMA,StringConstants.SPACE,NAME);
		String actual = Bo2ArchStringUtils.formatCodifiedNamed(impl);
		assertEquals(expected,actual);
	}
	
	
	/**
	 * tests FormatCodifiedNamed with null value
	 */
	@Test
	public void testFormatCodifiedNamed_NullValue(){
		CodifiedNamedImpl<Integer> impl2 = null;
		String expected = "null"; //$NON-NLS-1$
		String actual = Bo2ArchStringUtils.formatCodifiedNamed(impl2);
		assertEquals(expected,actual);
	}
	
	
}
