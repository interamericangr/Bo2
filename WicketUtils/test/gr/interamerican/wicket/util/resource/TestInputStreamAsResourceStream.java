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
	 */
	@Test
	public void testConstructor() throws IOException {
		InputStream stream = new ByteArrayInputStream(new byte[1]);
		InputStreamAsResourceStream resource = new InputStreamAsResourceStream(stream);
		Assert.assertEquals(stream, resource.stream);
		resource.close();
	}

	/**
	 * tests the getInputStream.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetInputStream() throws IOException {
		InputStream stream = new ByteArrayInputStream(new byte[1]);
		InputStreamAsResourceStream resource = new InputStreamAsResourceStream(stream);
		Assert.assertEquals(resource.stream, resource.getInputStream());
		resource.close();
	}

	/**
	 * tests close.
	 * @throws IOException
	 */
	@Test
	public void testClose() throws IOException  {
		InputStream stream = new ByteArrayInputStream(new byte[1]);
		InputStreamAsResourceStream resource = new InputStreamAsResourceStream(stream);
		resource.close();
	}

}
