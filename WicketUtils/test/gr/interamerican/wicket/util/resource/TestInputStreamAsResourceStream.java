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

import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link InputStreamAsResourceStream}.
 */
public class TestInputStreamAsResourceStream {

	/**
	 * tests the one arg constructor.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testConstructor() throws IOException {
		InputStream stream = new ByteArrayInputStream(new byte[1]);
		try (InputStreamAsResourceStream resource = new InputStreamAsResourceStream(stream)) {
			Assert.assertEquals(stream, resource.stream);
		}
	}

	/**
	 * tests the getInputStream.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testGetInputStream() throws IOException {
		InputStream stream = new ByteArrayInputStream(new byte[1]);
		try (InputStreamAsResourceStream resource = new InputStreamAsResourceStream(stream)) {
			Assert.assertEquals(resource.stream, resource.getInputStream());
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
		try (InputStreamAsResourceStream resource = new InputStreamAsResourceStream(mock)) {
			// nothing
		}
		verify(mock).close();
	}
}