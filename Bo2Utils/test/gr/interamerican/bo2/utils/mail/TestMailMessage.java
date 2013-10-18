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
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for {@link MailMessage}.
 */
public class TestMailMessage {
	
	/**
	 * Mail server setup.
	 */
	@BeforeClass
	public static void setup() {
		MailServer.INSTANCE.setHost("10.2.3.60");
		MailServer.INSTANCE.setPort(null);
	}
	
	/**
	 * Unit test for the constructor.
	 */
	@Test
	public void testConstructor() {
		MailMessage m = new MailMessage();
		Assert.assertTrue(m.toList.isEmpty());
		Assert.assertTrue(m.ccList.isEmpty());
		Assert.assertTrue(m.bccList.isEmpty());
		Assert.assertTrue(m.attachments.isEmpty());
		Assert.assertNull(m.from);
		Assert.assertEquals(StringConstants.EMPTY, m.subject);
		Assert.assertEquals(StringConstants.EMPTY, m.message);
		Assert.assertEquals(MailDefaults.INSTANCE.getMessageBodyCharset(), m.getMessageCharset());
		Assert.assertEquals(MailDefaults.INSTANCE.getMessageSubjectCharset(), m.getSubjectCharset());
		Assert.assertEquals(MailDefaults.INSTANCE.getMessageType(), m.getMessageType());		
	}
	
	/**
	 * Unit test for the constructor.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testSend() {
		MailMessage m = new MailMessage();
		String from = "TestSuite&Bo2.org";
		String subject = "Bo2 test message";
		m.setFrom(from);
		String recipient = "nakoss@interamerican.gr";
		String ccRecipient = "katerosd@interamerican.gr";
		m.addTo(recipient);		
		m.addCc(ccRecipient);
		m.setSubject(subject);
		String body = StringUtils.concat(
			"<html><h1>Δοκιμαστικό μήνυμα</h1>",
			"<body>This is the message in detail</body></html>");
		m.setMessage(body);
		m.setMessageType(MailConstants.TYPE_HTML);
		
		String resourcePath = "/gr/interamerican/bo2/deployparms/deployment.properties"; //$NON-NLS-1$
		Properties properties = CollectionUtils.readProperties(resourcePath);	
		String workdir = properties.getProperty("streamsWorkDirectory");
		String path = workdir + "existing.txt";
		
		byte[] bytes = "αυτό είναι ένα ελληνικό κείμενο".getBytes();		
		m.addAttachment(bytes, path);
		
		List<String> toList = m.getToList();
		Assert.assertTrue(toList.size()==1);
		Assert.assertTrue(toList.contains(recipient));
		
		List<String> ccList = m.getToList();
		Assert.assertTrue(ccList.size()==1);
		Assert.assertTrue(ccList.contains(recipient));
		
		List<String> bccList = m.getToList();
		Assert.assertTrue(bccList.size()==1);
		Assert.assertTrue(bccList.contains(recipient));
		
		Assert.assertEquals(from, m.getFrom());
		Assert.assertEquals(body, m.getMessage());
		Assert.assertEquals(subject, m.getSubject());
		Assert.assertEquals(1, m.getAttachments().size());
		
		m.send();		
	}
	
	
	

}
