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

import java.util.Properties;

import javax.mail.Session;

/**
 * Abstraction for a mail server.
 */
public class MailServer {
	/**
	 * Singleton instance.
	 */
	public static final MailServer INSTANCE = new MailServer();
	
	/**
	 * Server name.
	 */
	String host;
	/**
	 * port.
	 */
	String port;
	/**
	 * Session with the server..
	 */
	Session session;
	
	/**
	 * Creates a new MailServer object. 
	 */
	MailServer() {
		super();
	}
	
	/**
	 * Creates the session.
	 * 
	 */
	void createSession() {
		Properties props = System.getProperties();
		if (host==null) {
			String msg = "No SMTP host specified"; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		props.put(MailConstants.SMTP_HOST, host); 
		if (port != null) {
			props.put(MailConstants.SMTP_PORT, port); 
		}		
		session = Session.getDefaultInstance(props, null);
	}

	/**
	 * Gets the session.
	 *
	 * @return Returns the session
	 */
	public Session getSession() {
		if (session==null) {
			createSession();
		}
		return session;
	}

	/**
	 * Gets the host.
	 *
	 * @return Returns the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Assigns a new value to the host.
	 *
	 * @param host the host to set
	 */
	public void setHost(String host) {		
		this.host = host;
	}

	/**
	 * Gets the port.
	 *
	 * @return Returns the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Assigns a new value to the port.
	 *
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}
	
	
}
