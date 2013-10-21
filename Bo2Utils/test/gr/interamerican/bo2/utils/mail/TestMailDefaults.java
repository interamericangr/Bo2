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

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link MailServer}.
 */
public class TestMailDefaults {
	
	/**
	 * Object to test.
	 */
	MailDefaults defaults = new MailDefaults();
	
	/**
	 * Unit test for getMessageType.
	 */
	@Test
	public void testGetMessageType() {
		String messageType = "val"; //$NON-NLS-1$
		defaults.messageType = messageType;
		Assert.assertEquals(messageType, defaults.getMessageType());
	}
	
	/**
	 * Unit test for setMessageType.
	 */
	@Test
	public void testSetMessageType() {
		String messageType = "val"; //$NON-NLS-1$
		defaults.setMessageType(messageType);
		Assert.assertEquals(messageType, defaults.messageType);
	}
	
	/**
	 * Unit test for getMessageBodyCharset.
	 */
	@Test
	public void testGetMessageBodyCharset() {
		String messageBodyCharset = "val"; //$NON-NLS-1$
		defaults.messageBodyCharset = messageBodyCharset;
		Assert.assertEquals(messageBodyCharset, defaults.getMessageBodyCharset());
	}
	
	/**
	 * Unit test for setMessageBodyCharset.
	 */
	@Test
	public void testSetMessageBodyCharset() {
		String messageBodyCharset = "val"; //$NON-NLS-1$
		defaults.setMessageBodyCharset(messageBodyCharset);
		Assert.assertEquals(messageBodyCharset, defaults.messageBodyCharset);
	}
	
	/**
	 * Unit test for getMessageSubjectCharset.
	 */
	@Test
	public void testGetMessageSubjectCharset() {
		String messageSubjectCharset = "val"; //$NON-NLS-1$
		defaults.messageSubjectCharset = messageSubjectCharset;
		Assert.assertEquals(messageSubjectCharset, defaults.getMessageSubjectCharset());
	}
	
	/**
	 * Unit test for setMessageSubjectCharset.
	 */
	@Test
	public void testSetMessageSubjectCharset() {
		String messageSubjectCharset = "val"; //$NON-NLS-1$
		defaults.setMessageSubjectCharset(messageSubjectCharset);
		Assert.assertEquals(messageSubjectCharset, defaults.messageSubjectCharset);
	}
	
	

}
