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

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;

/**
 * File attachment.
 */
class FileAttachment
extends AbstractAttachment
implements Attachment {
	

	/**
	 * Creates a new MailAttachment object. 
	 * 
	 * @param filename
	 */
	public FileAttachment(String filename) {
		super(filename);	
	}
	
	public MimeBodyPart getMimeBodyPart() throws MessagingException {		
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		DataSource datasource = new FileDataSource(filename);		
		DataHandler datahandler = new DataHandler(datasource);
		mimeBodyPart.setDataHandler(datahandler);
		mimeBodyPart.setFileName(filename);
		mimeBodyPart.setDisposition(MimeBodyPart.ATTACHMENT);
		return mimeBodyPart;			
	}
	
	

	

	
}
