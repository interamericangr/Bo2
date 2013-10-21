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
 * Unit test for {@link FileAttachment}.
 */
public class TestFileAttachment {
	
	
	/**
	 * Test for the constructor.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConstructor() {
		String filename = "/home/baz/foo.pdf";				
		FileAttachment attach = new FileAttachment(filename);
		Assert.assertEquals(filename, attach.filename);
	}
	
	/**
	 * Test for the constructor.
	 * @throws MessagingException 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetMimeBodyPart() throws MessagingException {
		String resourcePath = "/gr/interamerican/bo2/deployparms/deployment.properties"; //$NON-NLS-1$
		Properties properties = CollectionUtils.readProperties(resourcePath);	
		String workdir = properties.getProperty("streamsWorkDirectory");
		String path = workdir + "existing.txt";
		FileAttachment attach = new FileAttachment(path);		
		Assert.assertNotNull(attach.getMimeBodyPart());
	}
	

}
