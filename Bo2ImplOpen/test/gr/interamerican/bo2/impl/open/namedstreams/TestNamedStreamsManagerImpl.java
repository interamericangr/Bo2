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
package gr.interamerican.bo2.impl.open.namedstreams;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link NamedStreamsManagerImpl}.
 */
public class TestNamedStreamsManagerImpl {
	
	/**
	 * Object to test.
	 */
	NamedStreamsManagerImpl manager;
	
	/**
	 * Setup tests.
	 */
	@Before
	public void setup() {
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		manager = new NamedStreamsManagerImpl(p);
	}
	
	/**
	 * Opens a NamedStream.
	 * 
	 * @param resourceType 
	 * @param uri 
	 * 
	 * 
	 * @return returns the NamedPrintStream.
	 * @throws InitializationException 
	 */
	private NamedPrintStream open(StreamResource resourceType, String uri) 
	throws InitializationException {
		NamedStreamDefinition def = new NamedStreamDefinition();
		def.setName("TestNamedStreamsManagerImpl_open"); //$NON-NLS-1$
		def.setRecordLength(0);
		def.setResourceType(resourceType);
		def.setType(StreamType.PRINTSTREAM);
		def.setUri(uri);
		return (NamedPrintStream) manager.open(def);
	}
	
	/**
	 * Unit test for open.
	 * @throws InitializationException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testOpen_File() throws InitializationException {
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		String path = p.getProperty("workDir") + "newFile.txt";
		NamedPrintStream ns = open(StreamResource.FILE, path);
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Unit test for open.
	 * @throws InitializationException 
	 */
	@SuppressWarnings("nls")
	@Test(expected=InitializationException.class)
	public void testOpen_Classpath() throws InitializationException {
		String path = "/com/foo/bar.txt";
		open(StreamResource.CLASSPATH, path);
	}
	
	/**
	 * Unit test for open.
	 * @throws InitializationException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testOpen_System() throws InitializationException {		
		NamedPrintStream ns = open(StreamResource.SYSTEM, "Sysout");
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Unit test for open.
	 * @throws InitializationException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testOpen_InMemory() throws InitializationException {		
		NamedPrintStream ns = open(StreamResource.BYTES, "InMemory");
		Assert.assertNotNull(ns);
	}
	
	/**
	 * Unit test for open.
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConvertFileStream_Print2Out() 
	throws InitializationException, DataException {
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		String path = p.getProperty("workDir") + "newFile.txt";
		NamedPrintStream ns = open(StreamResource.FILE, path);
		Assert.assertNotNull(ns);
		String newName = "NEW_OUT";
		NamedOutputStream out = (NamedOutputStream) 
			manager.convert(ns, StreamType.OUTPUTSTREAM, newName);
		Assert.assertNotNull(out);
		Assert.assertEquals(newName, out.getName());
	}
	
	/**
	 * Unit test for open.
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConvertFileStream_Print2In() 
	throws InitializationException, DataException {
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		String path = p.getProperty("workDir") + "newFile.txt";
		NamedPrintStream ns = open(StreamResource.FILE, path);
		Assert.assertNotNull(ns);
		String newName = "NEW_IN";
		NamedInputStream in = (NamedInputStream) 
			manager.convert(ns, StreamType.INPUTSTREAM, newName);
		Assert.assertNotNull(in);
		Assert.assertEquals(newName, in.getName());
	}
	
	/**
	 * Unit test for open.
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConvertFileStream_Print2Reader()
	throws InitializationException, DataException {
		Properties p = UtilityForBo2Test.getLocalFsProperties();
		String path = p.getProperty("workDir") + "newFile.txt";
		NamedPrintStream ns = open(StreamResource.FILE, path);
		Assert.assertNotNull(ns);
		String newName = "NEW_READER";
		NamedBufferedReader in = (NamedBufferedReader) 
			manager.convert(ns, StreamType.BUFFEREDREADER, newName);
		Assert.assertNotNull(in);
		Assert.assertEquals(newName, in.getName());
	}
	
	/**
	 * Unit test for open.
	 * @throws InitializationException 
	 * @throws DataException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConvertMemoryStream_Print2Reader()
	throws InitializationException, DataException {		
		NamedPrintStream ns = open(StreamResource.BYTES, "M");
		Assert.assertNotNull(ns);
		String line = "Line";
		ns.writeString(line);
		
		String newName = "NEW_READER";
		NamedBufferedReader in = (NamedBufferedReader) 
			manager.convert(ns, StreamType.BUFFEREDREADER, newName);
		Assert.assertNotNull(in);
		Assert.assertEquals(newName, in.getName());
		
		String actual = in.readString();
		Assert.assertEquals(line, actual);
	}
	
	
	
	

}
