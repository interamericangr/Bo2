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

import static gr.interamerican.bo2.utils.StringConstants.COMMA;
import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.mail.MailMessage;

/**
 * Sends an eMail with the status of a long process.
 */
public class LongProcessMail 
implements VoidOperation<MultiThreadedLongProcess> {
	
	/**
	 * Creates the message.
	 */
	LongProcessToString msgCreator = new LongProcessToString();
	
	/**
	 * Recipients.
	 */
	String[] recipients;
	
	/**
	 * Message sender.
	 */
	String from;
	
	/**
	 * Gets the statusMessageRecipients.
	 *
	 * @return Returns the statusMessageRecipients
	 */
	public String getStatusMessageRecipients() {
		if (recipients==null) {
			return null;
		}
		String statusMessageRecipients = StringUtils.concatSeparated(COMMA,recipients);
		return statusMessageRecipients;
	}


	/**
	 * Assigns a new value to the statusMessageRecipients.
	 *
	 * @param statusMessageRecipients the statusMessageRecipients to set
	 */
	public void setStatusMessageRecipients(String statusMessageRecipients) {
		if (statusMessageRecipients==null) {
			recipients = null;
		} else {
			recipients = TokenUtils.splitTrim(statusMessageRecipients, COMMA);			
		}		
	}

	@Override
	public void execute(MultiThreadedLongProcess a) {
		String msg = msgCreator.execute(a);
		String subject = "Process " + a.getName(); //$NON-NLS-1$
		MailMessage mail = newMailMessage();
		mail.setFrom(from);		
		mail.setSubject(subject);
		mail.setMessage(msg);
		for (String to  : recipients) {
			mail.addTo(to);			
		}		
		mail.send();
	}
	
	/**
	 * Creates a new empty {@link MailMessage}.
	 * 
	 * @return Returns a new {@link MailMessage}.
	 */
	MailMessage newMailMessage() {
		return new MailMessage();
	}









	






	
	
	

}
