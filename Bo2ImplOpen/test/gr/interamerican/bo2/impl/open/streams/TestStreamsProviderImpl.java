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
package gr.interamerican.bo2.impl.open.streams;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.junit.Test;

/**
 * Unit tests for {@link StreamsProviderImpl}.
 *
 */
public class TestStreamsProviderImpl {
	
	/**
	 * Tests the successful execution of getOutputStream.
	 * 
	 * Creates an OutputStream, writes some data in it, then 
	 * Creates an InputStream and reads the data.
	 * @throws DataException 
	 * @throws IOException 
	 */
	@Test
	public void testGetOutputStream_withoutException() 
	throws DataException, IOException {
		String filename = "TestStreamsProviderImpl.testfile1.txt"; //$NON-NLS-1$
		String path = UtilityForBo2Test.getTestStreamPath(filename);
		StreamsProviderImpl impl = new StreamsProviderImpl();		
		OutputStream out = impl.getOutputStream(path);
		String text = "Hello, this is a test\n";  //$NON-NLS-1$
		out.write(text.getBytes());
		out.close();
		impl.close();
	}
	
	/**
	 * Tests the failure of an output stream creation.
	 * 
	 * @throws DataException
	 */
	@Test(expected=DataException.class)
	public void testGetOutputStream_withException() throws DataException {
		String path = "\\ / /\\//123:: 3 goobaz";		 //$NON-NLS-1$
		StreamsProviderImpl impl = new StreamsProviderImpl();		
		@SuppressWarnings("unused")
		OutputStream out = impl.getOutputStream(path);
	}
	
	/**
	 * Tests the successful execution of getOutputStream.
	 * 
	 * Creates an OutputStream, writes some data in it, then 
	 * Creates an InputStream and reads the data.
	 * @throws DataException 
	 * @throws IOException 
	 */
	@Test
	public void testInputStream_withoutException() 
	throws DataException, IOException {
		String filename = "TestStreamsProviderImpl.testfile2.txt"; //$NON-NLS-1$
		String path = UtilityForBo2Test.getTestStreamPath(filename);
		StreamsProviderImpl impl = new StreamsProviderImpl();		
		OutputStream out = impl.getOutputStream(path);
		String text = "Hello, this is a test\n";  //$NON-NLS-1$
		out.write(text.getBytes());
		out.close();
		
		InputStream in = impl.getInputStream(path);
		byte[] buffer = new byte[100000];
		int bytesRead = in.read(buffer);		
		byte[] contents = ArrayUtils.copyOf(buffer, bytesRead);
		
		assertEquals(new String(contents), text);
		
		impl.close();
	}
	
	/**
	 * Tests the failure of an output stream creation.
	 * 
	 * @throws DataException
	 */
	@Test(expected=DataException.class)
	public void testGetInputStream_withException() throws DataException {
		String path = "\\ / /\\//123:: 3 goobaz";		 //$NON-NLS-1$
		StreamsProviderImpl impl = new StreamsProviderImpl();		
		@SuppressWarnings("unused")
		InputStream in = impl.getInputStream(path);
	}
	
	/**
	 * Tests getPath.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetPath() {
		String tempDir = "/opt"+File.separator; //$NON-NLS-1$
		Properties properties = new Properties();
		properties.put(StreamsProviderImpl.WORK_DIR, tempDir); //$NON-NLS-1$
		StreamsProviderImpl impl = new StreamsProviderImpl(properties);
		String fd = "file.txt";
		String path = impl.getPath(fd);
		assertEquals("/opt"+File.separator + "file.txt", path);
	}

}
