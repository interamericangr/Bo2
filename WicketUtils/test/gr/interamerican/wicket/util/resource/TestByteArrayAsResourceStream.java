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
package gr.interamerican.wicket.util.resource;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.*;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ByteArrayAsResourceStream}.
 */
public class TestByteArrayAsResourceStream {

	/**
	 * tests the one arg constructor.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testConstructor() throws IOException {
		byte[] array = new byte[10];
		try (ByteArrayAsResourceStream resource = new ByteArrayAsResourceStream(array)) {
			Assert.assertEquals(array, resource.fileData);
		}
	}

	/**
	 * tests the getInputStream.
	 *
	 * @throws ResourceStreamNotFoundException
	 *             the resource stream not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testGetInputStream() throws ResourceStreamNotFoundException, IOException {
		byte[] array = new byte[10];
		try (ByteArrayAsResourceStream resource = new ByteArrayAsResourceStream(array)) {
			Assert.assertNotNull(resource.getInputStream());
		}
	}

	/**
	 * tests close.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testClose() throws IOException {
		InputStream mock = mock(InputStream.class);
		byte[] array = new byte[10];
		try (ByteArrayAsResourceStream resource = new ByteArrayAsResourceStream(array)) {
			resource.inputStream = mock;
		}
		verify(mock).close();
	}
}