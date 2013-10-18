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

import static gr.interamerican.bo2.utils.mail.MailConstants.ENCODING_GREEK;
import static gr.interamerican.bo2.utils.mail.MailConstants.TYPE_TEXT;

/**
 * Default values for mail properties.
 */
public class MailDefaults {
	/**
	 * Singleton instance.
	 */
	public static final MailDefaults INSTANCE = new MailDefaults();
	
	/**
	 * Message content encoding.
	 */
	String messageType = TYPE_TEXT;		
	/**
	 * Message content Charset.
	 */
	String messageBodyCharset = ENCODING_GREEK;
	/**
	 * header Charset.
	 */
	String messageSubjectCharset = ENCODING_GREEK;
	
	
	
	
	/**
	 * Creates a new MailDefaults object. 
	 *
	 */
	MailDefaults() {
		super();		
	}
	/**
	 * Gets the messageBodyEncoding.
	 *
	 * @return Returns the messageBodyEncoding
	 */
	public String getMessageType() {
		return messageType;
	}
	/**
	 * Assigns a new value to the messageBodyEncoding.
	 *
	 * @param messageBodyEncoding the messageBodyEncoding to set
	 */
	public void setMessageType(String messageBodyEncoding) {
		this.messageType = messageBodyEncoding;
	}	
	/**
	 * Gets the messageBodyCharset.
	 *
	 * @return Returns the messageBodyCharset
	 */
	public String getMessageBodyCharset() {
		return messageBodyCharset;
	}
	/**
	 * Assigns a new value to the messageBodyCharset.
	 *
	 * @param messageBodyCharset the messageBodyCharset to set
	 */
	public void setMessageBodyCharset(String messageBodyCharset) {
		this.messageBodyCharset = messageBodyCharset;
	}
	/**
	 * Gets the messageSubjectCharset.
	 *
	 * @return Returns the messageSubjectCharset
	 */
	public String getMessageSubjectCharset() {
		return messageSubjectCharset;
	}
	/**
	 * Assigns a new value to the messageSubjectCharset.
	 *
	 * @param messageSubjectCharset the messageSubjectCharset to set
	 */
	public void setMessageSubjectCharset(String messageSubjectCharset) {
		this.messageSubjectCharset = messageSubjectCharset;
	}
	
	

}
