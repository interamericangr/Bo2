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

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for {@link AbstractAttachment}.
 */
public class TestAbstractAttachment {
	/**
	 * instance to test.
	 */
	@SuppressWarnings("nls")
	AbstractAttachment attach = new AbstractAttachment("F") {		
		public MimeBodyPart getMimeBodyPart() throws MessagingException {
			return null;
		}
	};
	
	/**
	 * Test for the constructor.
	 */
	@Test
	@SuppressWarnings("nls")
	public void testConstructor() {
		Assert.assertEquals("F", attach.filename);
	}
	
	/**
	 * Test for the constructor.
	 */
	@Test
	public void testGetFilename() {
		Assert.assertEquals(attach.filename, attach.getFilename());
	}
	
	
	

}
