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
package gr.interamerican.bo2.impl.open.runtime.monitor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.impl.open.runtime.monitor.LongProcessMail;
import gr.interamerican.bo2.utils.mail.MailMessage;

import java.util.Date;
import java.util.Properties;

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
	 * Unit test for setStatusMessageRecipients().
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
	 * Unit test for setStatusMessageRecipients().
	 */
	@Test
	public void testSetStatusMessageRecipients_withNull() {
		LongProcessMail mail = new LongProcessMail();
		mail.setStatusMessageRecipients(null);
		Assert.assertNull(mail.recipients);
	}
	
	/**
	 * Unit test for getStatusMessageRecipients().
	 */
	@Test
	public void testGetStatusMessageRecipients_withNull() {
		LongProcessMail mail = new LongProcessMail();
		mail.recipients = null;
		String toList = mail.getStatusMessageRecipients();
		Assert.assertNull(toList);
	}
	
	/**
	 * Unit test for getStatusMessageRecipients().
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
	
	/**
	 * Unit test for beModified(p).
	 */
	@SuppressWarnings("nls")
	@Test
	public void testBeModified() {
		LongProcessMail mail = new LongProcessMail();
		Properties p = new Properties();
		String from = "Foo@Foo.com";
		String toList = "me@there.com, him@there.com";
		long interval = 1000L;
		p.setProperty(LongProcessMail.MONITOR_MESSAGE_FROM, from);
		p.setProperty(LongProcessMail.MONITOR_MESSAGE_RECIPIENTS, toList);
		p.setProperty(LongProcessMail.MONITOR_MESSAGE_INTERVAL, Long.toString(interval));
		mail.beModified(p);
		Assert.assertEquals(from, mail.from);
		Assert.assertEquals("me@there.com", mail.recipients[0]);
		Assert.assertEquals("him@there.com", mail.recipients[1]);
		Assert.assertEquals(interval, mail.getPeriodInterval());		
	}
	
	/**
	 * Unit test for isValid().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testIsValid_true() {
		LongProcessMail mail = new LongProcessMail();
		mail.setStatusMessageRecipients("foo@foo.com");
		mail.setPeriodInterval(1L);		
		Assert.assertTrue(mail.isValid());
	}
	
	/**
	 * Unit test for isValid().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testIsValid_false() {
		LongProcessMail mail = new LongProcessMail();
		mail.setStatusMessageRecipients("");
		mail.setPeriodInterval(1L);		
		Assert.assertFalse(mail.isValid());
	}
	
	/**
	 * Unit test for newMailMessage().
	 */	
	@Test
	public void testNewMailMessage() {
		LongProcessMail mail = new LongProcessMail();		
		Assert.assertNotNull(mail.newMailMessage());
	}
	

	
	

}
