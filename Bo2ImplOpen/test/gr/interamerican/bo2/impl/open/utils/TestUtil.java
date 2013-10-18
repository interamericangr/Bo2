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
package gr.interamerican.bo2.impl.open.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

/**
 * Unit test for {@link Util}.
 */
@SuppressWarnings("nls")
public class TestUtil {
	
	

	
	/**
	 * Test readProperties() success.
	 * @throws IOException 
	 */
	@Test
	public void testReadFile_Succeed() throws IOException {
		String path = "/gr/interamerican/bo2/impl/open/utils/sample.properties";
		String[] lines = Util.readFile(path);
		assertEquals(2, lines.length);
		assertEquals("a=A", lines[0].trim());
	}
	
	/**
	 * Test readProperties() failing.
	 * @throws IOException 
	 */
	@Test(expected=RuntimeException.class)
	public void readFile_Fail() throws IOException {
		String path = "/com/foo/sample.properties";
		@SuppressWarnings("unused")
		String[] lines = Util.readFile(path);
		
	}
	
	/**
	 * Tests getParameter().
	 */
	@Test
	public void testGetParameter_succeed() {
		String key = "AbstractJdbcWorker.SCHEMA_KEY";
		String expected = "X__X";
		String actual = Util.getParameter(key);
		assertEquals(expected, actual);		
	}
	
	/**
	 * Tests getParameter().
	 */
	@Test
	public void testGetParameter_fail() {
		String key = "com.foo.key";
		String expected = "!com.foo.key!";
		String actual = Util.getParameter(key);
		assertEquals(expected, actual);		
	}
	

}
