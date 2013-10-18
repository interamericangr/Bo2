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
package gr.interamerican.bo2.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

/**
 * Unit tests for {@link StreamUtils}.
 */
@SuppressWarnings("nls")
public class TestStreamUtils {
	
	/**
	 * Test readProperties() success.
	 * @throws IOException 
	 */
	@Test
	public void testReadResourceFile_SucceedAll() throws IOException {
		String path = "/gr/interamerican/bo2/utils/sample.properties";
		String[] lines = StreamUtils.readResourceFile(path);
		assertEquals(3, lines.length);
		assertEquals("a=A", lines[0].trim());
	}
	
	/**
	 * Test readProperties() success.
	 * @throws IOException 
	 */
	@Test
	public void testReadResourceFile_SucceedNoEmpty() throws IOException {
		String path = "/gr/interamerican/bo2/utils/sample.properties";
		String[] lines = StreamUtils.readResourceFile(path, true, false);
		assertEquals(2, lines.length);
		assertEquals("a=A", lines[0].trim());
		assertEquals("b=B#Comment", lines[1].trim());
	}
	
	/**
	 * Test readProperties() success.
	 * @throws IOException 
	 */
	@Test
	public void testReadResourceFile_SucceedNoEmptyNoSharps() throws IOException {
		String path = "/gr/interamerican/bo2/utils/sample.properties";
		String[] lines = StreamUtils.readResourceFile(path, true, true);
		assertEquals(2, lines.length);
		assertEquals("a=A", lines[0].trim());
		assertEquals("b=B", lines[1].trim());
	}
	
	/**
	 * Test readProperties() failing.
	 * @throws IOException 
	 */
	@Test
	public void readResourceFile_Fail() throws IOException {
		String path = "/com/foo/sample.properties";		
		String[] lines = StreamUtils.readResourceFile(path);
		assertNull(lines);
	}
	
	
	/**
	 * Test readFile() success.
	 * 
	 * For this test to succeed, the file in the path <code>EXISTING_PATH</code>
	 * must exist.
	 * 
	 * @throws IOException 
	 */
	@Test
	public void testReadFile_Succeed() throws IOException {
		String resourcePath = "/gr/interamerican/bo2/deployparms/deployment.properties"; //$NON-NLS-1$
		Properties properties = CollectionUtils.readProperties(resourcePath);	
		String workdir = properties.getProperty("streamsWorkDirectory");
		String path = workdir + "existing.txt";
		String[] lines = StreamUtils.readFile(path);
		assertNotNull(lines);
		assertTrue(lines.length>0);
	}
	
	/**
	 * Test readProperties() failing.
	 * @throws IOException 
	 */
	@Test
	public void testReadFile_Fail() throws IOException {		
		String path =  "/com/foo/sample.properties";		
		String[] lines = StreamUtils.readFile(path);
		assertNull(lines);
	}
	
	/**
	 * Unit test for getStringFromResourceFile().
	 */
	@Test
	public void testGetStringFromResourceFile() {
		String path = "/gr/interamerican/bo2/deployparms/managers/managers.txt"; 
		String expected = StringUtils.concat(
			"/gr/interamerican/bo2/deployparms/managers/localdb/of.properties ",
			"/gr/interamerican/bo2/deployparms/managers/localfs/of.properties ",
			"/gr/interamerican/bo2/deployparms/managers/otherdb/of.properties ",
			"/gr/interamerican/bo2/deployparms/managers/odf/of.properties");
		String actual = StreamUtils.getStringFromResourceFile(path);
		assertEquals(expected, actual);
	}
	
	/**
	 * Unit test for getStringFromResourceFile().
	 */
	@Test(expected = RuntimeException.class)
	public void testGetStringFromResourceFile_fail() {
		String path = "/gr/interamerican/bo2/deployparms/managers/notExists.txt"; 
		StreamUtils.getStringFromResourceFile(path);
	}
	

}
