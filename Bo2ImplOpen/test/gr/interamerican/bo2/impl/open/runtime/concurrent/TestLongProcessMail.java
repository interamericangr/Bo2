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
package gr.interamerican.bo2.impl.open.runtime.concurrent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.utils.mail.MailMessage;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link LongProcessMail}.
 */
public class TestLongProcessMail {
	
	/**
	 * Tests execute().
	 */
	@Test
	public void testExecute() {
		Date end = new Date();
		Date start = new Date(end.getTime()-1000000);
		MultiThreadedLongProcess mlp = 
			TestLongProcessToString.mockForToString(start, end, false);
		
		/*
		 * Mock message.
		 */
		final MailMessage msg = mock(MailMessage.class);		
		/*
		 * Stub the object so that it sends a mock message. 
		 */
		LongProcessMail mail = new LongProcessMail() {
			@Override
			MailMessage newMailMessage() {
				return msg;
			}
		};
		mail.setStatusMessageRecipients("foo@foo.com"); //$NON-NLS-1$
		mail.execute(mlp);
		verify(msg, times(1)).send();
	}
	
	/**
	 * Unit test for setRecipients().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSetStatusMessageRecipients_withStrings() {
		LongProcessMail mail = new LongProcessMail();		
		String toList = "me@there.com, him@there.com";
		mail.setStatusMessageRecipients(toList);
		Assert.assertEquals("me@there.com", mail.recipients[0]);
		Assert.assertEquals("him@there.com", mail.recipients[1]);
	}
	
	/**
	 * Unit test for setRecipients().
	 */
	@Test
	public void testSetStatusMessageRecipients_withNull() {
		LongProcessMail mail = new LongProcessMail();
		mail.setStatusMessageRecipients(null);
		Assert.assertNull(mail.recipients);
	}
	
	/**
	 * Unit test for setRecipients().
	 */
	@Test
	public void testGetStatusMessageRecipients_withNull() {
		LongProcessMail mail = new LongProcessMail();
		mail.recipients = null;
		String toList = mail.getStatusMessageRecipients();
		Assert.assertNull(toList);
	}
	
	/**
	 * Unit test for setRecipients().
	 */
	@Test
	public void testGetStatusMessageRecipients_withOnE() {
		String to = "x@x.com"; //$NON-NLS-1$
		String[] toList = {to};
		LongProcessMail mail = new LongProcessMail();
		mail.recipients = toList;
		String recipients = mail.getStatusMessageRecipients();
		Assert.assertEquals(to, recipients);
	}
	
	

}
