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
package gr.interamerican.wicket.utils.images;

import static org.junit.Assert.*;

import org.apache.wicket.util.resource.IResourceStream;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link EmbeddedImage}.
 */
public class TestEmbeddedImage extends WicketTest {

	/**
	 * Test method for {@link EmbeddedImage#getReference()}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetReference() throws Exception {
		for (EmbeddedImage image : EmbeddedImage.values()) {
			IResourceStream stream = image.getReference().getResource().getResourceStream();
			assertEquals("image/jpeg", stream.getContentType()); //$NON-NLS-1$
			assertTrue(stream.getInputStream().read() >= 0);
		}
	}
}