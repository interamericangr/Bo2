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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.DataOperationNotSupportedException;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResource;
import gr.interamerican.bo2.impl.open.namedstreams.resourcetypes.StreamResourceEnum;
import gr.interamerican.bo2.utils.StringConstants;

/**
 * Unit test for {@link NamedPrintStream}.
 */
@SuppressWarnings("nls")
public class TestNamedPrintStream {

	/**
	 * Array for the tests.
	 */
	String[] lines = { "this is a dog", "this is a cat", "cats and dogs are animals" };

	/**
	 * Creates a sample.
	 *
	 * @return Returns the sample.
	 */
	NamedPrintStream sample() {
		Charset encoding = Charset.defaultCharset();
		OutputStream out = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(out);
		return new NamedPrintStream(null, ps, "NPS", out, encoding, null);
	}

	/**
	 * Test for the constructor.
	 */
	@Test
	public void testConstructor() {
		StreamResource resourceType = StreamResourceEnum.BYTES;
		PrintStream stream = mock(PrintStream.class);
		String name = "bar";
		Object resource = new Object();
		String uri = "foo";
		Charset encoding = Charset.defaultCharset();
		NamedPrintStream ns = new NamedPrintStream(resourceType, stream, name, resource, encoding, uri);
		Assert.assertEquals(resourceType, ns.getResourceType());
		Assert.assertEquals(resource, ns.getResource());
		Assert.assertEquals(stream, ns.getStream());
		Assert.assertEquals(uri, ns.getUri());
		Assert.assertEquals(encoding, ns.getEncoding());
		Assert.assertEquals(0, ns.getRecordLength());
	}

	/**
	 * Unit test for readRecord.
	 *
	 * @throws DataException
	 *             the data exception
	 */
	@Test(expected = DataOperationNotSupportedException.class)
	public void testReadRecord() throws DataException {
		NamedPrintStream ns = sample();
		ns.readRecord();
	}

	/**
	 * Unit test for readString.
	 *
	 * @throws DataException
	 *             the data exception
	 */
	@Test(expected = DataOperationNotSupportedException.class)
	public void testReadString() throws DataException {
		NamedPrintStream ns = sample();
		ns.readString();
	}

	/**
	 * Unit test for writeString.
	 */
	@Test
	public void testWriteString() {
		NamedPrintStream ns = sample();
		for (String string : lines) {
			ns.writeString(string);
		}
		ByteArrayOutputStream baos = (ByteArrayOutputStream) ns.getResource();
		byte[] bytes = baos.toByteArray();

		String expected = StringConstants.EMPTY;
		for (String string : lines) {
			expected = expected + string + System.lineSeparator();
		}
		byte[] expecteds = expected.getBytes(Charset.defaultCharset());
		Assert.assertArrayEquals(expecteds, bytes);
	}

	/**
	 * Unit test for writeRecord.
	 */
	@Test
	public void testWriteRecord() {
		NamedPrintStream ns = sample();
		for (String string : lines) {
			byte[] rec = string.getBytes(Charset.defaultCharset());
			ns.writeRecord(rec);
		}
		ByteArrayOutputStream baos = (ByteArrayOutputStream) ns.getResource();
		byte[] bytes = baos.toByteArray();

		String expected = StringConstants.EMPTY;
		for (String string : lines) {
			expected = expected + string + System.lineSeparator();
		}
		byte[] expecteds = expected.getBytes(Charset.defaultCharset());
		Assert.assertArrayEquals(expecteds, bytes);
	}

	/**
	 * Unit test for readString.
	 *
	 * @throws DataException
	 *             the data exception
	 */
	@Test(expected = DataOperationNotSupportedException.class)
	public void testFind() throws DataException {
		NamedPrintStream ns = sample();
		ns.find("foo".getBytes());
	}

	/**
	 * Test for close().
	 *
	 * @throws DataException
	 *             the data exception
	 */
	@Test
	public void testClose() throws DataException {
		PrintStream stream = mock(PrintStream.class);
		Charset encoding = Charset.defaultCharset();
		NamedPrintStream ns = new NamedPrintStream(null, stream, "foo", stream, encoding, "bar");
		ns.close();
		verify(stream).close();
	}
}