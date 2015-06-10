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


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceEnum;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test for {@link NamedOutputStream}.
 */
@SuppressWarnings("nls")
public class TestNamedOutputStream {
	
	
	/**
	 * Array for the tests.
	 */
	String[] lines = {
			"this is a dog",
			"this is a cat",
			"cats and dogs are animals" 
	};
	
	/**
	 * Creates a sample.
	 * 
	 * @return Returns the sample.
	 */
	NamedOutputStream sample() {
		Charset encoding = Charset.defaultCharset();
		OutputStream out = new ByteArrayOutputStream();
		return new NamedOutputStream(null,out,"NOS",lines[0].length(),out,encoding,null);
	}
	
	
	/**
	 * Test for the constructor.
	 */
	@Test
	public void testConstructor() {
		StreamResource resourceType = StreamResourceEnum.BYTES;
		OutputStream stream = mock(OutputStream.class);
		String name = "bar";
		Object resource = new Object();
		String uri = "foo";
		int reclen = 100;
		Charset encoding = Charset.defaultCharset();		
		NamedOutputStream ns = 
			new NamedOutputStream(resourceType, stream, name, reclen, resource, encoding, uri);
		Assert.assertEquals(resourceType, ns.getResourceType());
		Assert.assertEquals(resource, ns.getResource());
		Assert.assertEquals(stream, ns.getStream());
		Assert.assertEquals(uri, ns.getUri());
		Assert.assertEquals(encoding, ns.getEncoding());
		Assert.assertEquals(reclen, ns.getRecordLength());
	}
	
	/**
	 * Unit test for readRecord.
	 * @throws FileNotFoundException
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testReadRecord() throws FileNotFoundException, DataException {
		NamedOutputStream ns = sample();
		ns.readRecord();
	}
	
	/**
	 * Unit test for readString.
	 * @throws FileNotFoundException
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testReadString() throws FileNotFoundException, DataException {
		NamedOutputStream ns = sample();
		ns.readString();
	}	
	
	/**
	 * Unit test for writeString.
	 * @throws DataException
	 * @throws IOException 
	 */	
	@Test()
	public void testWriteString() throws DataException, IOException {
		NamedOutputStream ns = sample();
		for (String string : lines) {
			ns.writeString(string);
		}
		ByteArrayOutputStream baos = (ByteArrayOutputStream) ns.getStream();
		byte[] bytes = baos.toByteArray();
		String expected = lines[0] + lines[1] + lines[2];
		byte[] expecteds = expected.getBytes(Charset.defaultCharset());
		Assert.assertArrayEquals(expecteds, bytes);
	}		
	
	/**
	 * Unit test for writeRecord.
	 * @throws DataException
	 * @throws IOException 
	 */	
	@Test()
	public void testWriteRecord() throws DataException, IOException {
		NamedOutputStream ns = sample();
		for (String string : lines) {
			byte[] rec = string.getBytes(Charset.defaultCharset());
			ns.writeRecord(rec);
		}
		ByteArrayOutputStream baos = (ByteArrayOutputStream) ns.getStream();
		byte[] bytes = baos.toByteArray();
		String expected = lines[0] + lines[1] + lines[2];
		byte[] expecteds = expected.getBytes(Charset.defaultCharset());
		Assert.assertArrayEquals(expecteds, bytes);
	}	
	
	/**
	 * Unit test for readString.
	 * @throws FileNotFoundException
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testFind() throws FileNotFoundException, DataException {
		NamedOutputStream ns = sample();
		ns.find("foo".getBytes());
	}	
	
	/**
	 * Test for close().
	 * 
	 * @throws IOException 
	 * @throws DataException 
	 */
	@Test
	public void testClose() throws IOException, DataException {
		OutputStream stream = mock(OutputStream.class);
		Charset encoding = Charset.defaultCharset();		
		NamedOutputStream ns = 
			new NamedOutputStream(null, stream, "foo", 10, stream, encoding, "bar");
		ns.close();
		verify(stream, times(1)).close();
	}
	
}
