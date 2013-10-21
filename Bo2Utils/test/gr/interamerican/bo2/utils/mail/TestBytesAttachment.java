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
package gr.interamerican.bo2.utils.mail;

import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.Properties;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link BytesAttachment}.
 */
public class TestBytesAttachment {
	
	
	/**
	 * Test for the constructor.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testConstructor() {
		String filename = "/home/baz/foo.pdf";
		byte[] bytes = new byte[1000];
		
		BytesAttachment attach = new BytesAttachment(bytes, filename);
		Assert.assertEquals(filename, attach.filename);
		Assert.assertEquals(bytes, attach.content);
	}
	
	/**
	 * Test for the constructor.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetContent() {
		String filename = "/home/baz/foo.pdf";
		byte[] bytes = new byte[1000];		
		BytesAttachment attach = new BytesAttachment(bytes, filename);
		Assert.assertEquals(bytes, attach.getContent());
	}
	
	/**
	 * Test for the constructor.
	 * @throws MessagingException 
	 */
	@Test
	@SuppressWarnings("nls")
	public void testGetMimeBodyPart() throws MessagingException {
		String resourcePath = "/gr/interamerican/bo2/deployparms/deployment.properties"; //$NON-NLS-1$
		Properties properties = CollectionUtils.readProperties(resourcePath);	
		String workdir = properties.getProperty("streamsWorkDirectory");
		String path = workdir + "existing.txt";
		byte[] bytes = "This is the attachment text".getBytes();
		BytesAttachment attach = new BytesAttachment(bytes, path);		
		Assert.assertNotNull(attach.getMimeBodyPart());
	}
	

}
