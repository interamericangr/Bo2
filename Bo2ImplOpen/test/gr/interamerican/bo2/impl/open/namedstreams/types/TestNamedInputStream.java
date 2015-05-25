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
package gr.interamerican.bo2.impl.open.namedstreams.types;

import static org.mockito.Mockito.*;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.types.NamedInputStream;
import gr.interamerican.bo2.samples.Streams;
import gr.interamerican.bo2.test.utils.UtilityForBo2Test;
import gr.interamerican.bo2.utils.Bo2UtilsEnvironment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 */
@SuppressWarnings("nls")
public class TestNamedInputStream {
	/**
	 * Array for the tests.
	 */
	String[] lines = {
			"this is a dog            ",
			"this is a cat            ",
			"cats and dogs are animals" 
	};
	
	/**
	 * Creates a sample.
	 * 
	 * @return Returns the sample.
	 */
	NamedInputStream sample(String... rows) {
		Charset encoding = Charset.defaultCharset();
		InputStream in = Streams.input(encoding, lines);
		return new NamedInputStream(null,in,"NIS",lines[0].length()+1,in,encoding,"NIS_URI");
	}
	
	
	/**
	 * Test for the constructor.
	 */
	@Test
	public void testConstructor() {
		StreamResource resourceType = StreamResource.BYTES;
		InputStream stream = mock(InputStream.class);
		String name = "bar";
		Object resource = new Object();
		String uri = "foo";
		int reclen = 100;
		Charset encoding = Charset.defaultCharset();		
		NamedInputStream ns = 
			new NamedInputStream(resourceType, stream, name, reclen, resource, encoding, uri);
		Assert.assertEquals(resourceType, ns.getResourceType());
		Assert.assertEquals(resource, ns.getResource());
		Assert.assertEquals(stream, ns.getStream());
		Assert.assertEquals(uri, ns.getUri());
		Assert.assertEquals(encoding, ns.getEncoding());
		Assert.assertEquals(reclen, ns.getRecordLength());
	}
	
	
	
	

	/**
	 * Unit test for readRecord.
	 * 
	 * @throws DataException
	 */	
	@Test
	public void testReadRecord() throws DataException {
		NamedInputStream nis = sample(lines);
		for (int i = 0; i < lines.length-1; i++) {
			byte[] rec = nis.readRecord();
			String expected = lines[i]+"\n";			
			byte[] expecteds = (expected).getBytes(Charset.defaultCharset());
			Assert.assertArrayEquals(expecteds, rec);
		}
	}
	
	/**
	 * Unit test for readString().
	 * 
	 * @throws DataException
	 */	
	@Test
	public void testReadString() throws DataException {
		NamedInputStream nis = sample(lines);
		for (int i = 0; i < lines.length-1; i++) {
			String line = nis.readString();
			String expected = lines[i]+"\n";	
			Assert.assertEquals(expected, line);
		}
	}	
	
	/**
	 * Unit test for writeString.
	 * 
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testWriteString() 
	throws DataException {		
		NamedInputStream nis = sample(lines);
		nis.writeString("not to be written");
	}		
	
	/**
	 * Unit test for writeString.
	 * 
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testWriteRecord() 
	throws DataException {
		NamedInputStream nis = sample(lines);
		nis.writeRecord("not to be written".getBytes());
	}	
	
	/**
	 * Unit test for find.
	 * 
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testFind() throws DataException {
		NamedInputStream nis = sample(lines);
		nis.find("123".getBytes());			
	}	
	
	/**
	 * Test for close().
	 * 
	 * @throws IOException 
	 * @throws DataException 
	 */
	@Test
	public void testClose() throws IOException, DataException {
		InputStream stream = mock(InputStream.class);
		Charset encoding = Charset.defaultCharset();		
		NamedInputStream ns = 
			new NamedInputStream(null, stream, "foo", 10, stream, encoding, "bar");
		ns.close();
		verify(stream, times(1)).close();
	}
	
	
}
