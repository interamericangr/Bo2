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
public class TestMailServer {
	
	/**
	 * Object to test.
	 */
	MailServer server = new MailServer();
	
	/**
	 * Unit test for getPort.
	 */
	@Test
	public void testGetPort() {
		String port = "val"; //$NON-NLS-1$
		server.port = port;
		Assert.assertEquals(port, server.getPort());
	}
	
	/**
	 * Unit test for setPort.
	 */
	@Test
	public void testSetPort() {
		String port = "val"; //$NON-NLS-1$
		server.setPort(port);
		Assert.assertEquals(port, server.port);
	}
	
	/**
	 * Unit test for getHost.
	 */
	@Test
	public void testGetHost() {
		String host = "val"; //$NON-NLS-1$
		server.host = host;
		Assert.assertEquals(host, server.getHost());
	}
	
	/**
	 * Unit test for setHost.
	 */
	@Test
	public void testSetHost() {
		String host = "val"; //$NON-NLS-1$
		server.setHost(host);
		Assert.assertEquals(host, server.host);
	}
	
	/**
	 * Unit test for setHost.
	 */
	@Test
	public void testGetSession() {
		String host = "10.2.3.60"; //$NON-NLS-1$
		server.setHost(host);
		server.setPort(null);
		server.createSession();
		Assert.assertNotNull(server.session);
		Assert.assertEquals(server.session, server.getSession());
	}
	
	/**
	 * Unit test for setHost.
	 */
	@Test
	public void testCreateSession() {
		String host = "10.2.3.60"; //$NON-NLS-1$
		server.setHost(host);
		server.setPort(null);
		server.createSession();
		Assert.assertNotNull(server.session);		
	}
	

}
