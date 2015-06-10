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
import gr.interamerican.bo2.samples.Streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test for {@link NamedBufferedReader}.
 */
@SuppressWarnings("nls")
public class TestNamedBufferedReader {
	
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
	 * @param rows
	 * 
	 * @return Returns the sample.
	 */
	NamedBufferedReader sample(String... rows) {
		Charset encoding = Charset.defaultCharset();
		InputStream in = Streams.input(encoding, lines);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		return new NamedBufferedReader(null, reader, "NBR", in, encoding, "NBR_uri");
	}
	
	
	
	
	
	/**
	 * Test for the constructor.
	 */
	@Test
	public void testConstructor() {
		StreamResource resourceType = StreamResourceEnum.BYTES;
		BufferedReader stream = mock(BufferedReader.class);
		String name = "bar";
		Object resource = new Object();
		String uri = "foo";
		Charset encoding = Charset.defaultCharset();		
		NamedBufferedReader ns = 
			new NamedBufferedReader(resourceType, stream, name, resource, encoding, uri);
		Assert.assertEquals(resourceType, ns.getResourceType());
		Assert.assertEquals(resource, ns.getResource());
		Assert.assertEquals(stream, ns.getStream());
		Assert.assertEquals(uri, ns.getUri());
		Assert.assertEquals(encoding, ns.getEncoding());
	}
	

	/**
	 * Unit test for readRecord().
	 * 
	 * @throws DataException
	 */	
	@Test
	public void testReadRecord() throws DataException {		
		NamedBufferedReader nbr = sample(lines);
		for (int i = 0; i < lines.length; i++) {
			byte[] rec = nbr.readRecord();
			byte[] expected = lines[i].getBytes(Charset.defaultCharset());
			Assert.assertArrayEquals(expected, rec);
		}
	}
	
	/**
	 * Unit test for readString().
	 * 
	 * @throws DataException
	 */	
	@Test
	public void testReadString() throws DataException {
		NamedBufferedReader nbr = sample(lines);
		for (int i = 0; i < lines.length; i++) {
			String line = nbr.readString();
			Assert.assertEquals(lines[i], line);
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
		NamedBufferedReader nbr = sample(lines);
		nbr.writeString("not to be written");
	}		
	
	/**
	 * Unit test for writeString.
	 * 
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testWriteRecord() 
	throws DataException {
		NamedBufferedReader nbr = sample(lines);
		nbr.writeRecord("not to be written".getBytes());
	}	
	
	/**
	 * Unit test for find.
	 * 
	 * @throws DataException
	 */	
	@Test(expected=DataOperationNotSupportedException.class)
	public void testFind() throws DataException {
		NamedBufferedReader nbr = sample(lines);
		nbr.find("123".getBytes());			
	}	
	
	/**
	 * Test for close().
	 * 
	 * @throws IOException 
	 * @throws DataException 
	 */
	@Test
	public void testClose() throws IOException, DataException {
		BufferedReader stream = mock(BufferedReader.class);
		Charset encoding = Charset.defaultCharset();		
		NamedBufferedReader ns = 
			new NamedBufferedReader(null, stream, "foo", stream, encoding, "bar");
		ns.close();
		verify(stream, times(1)).close();
	}
	
	
}
