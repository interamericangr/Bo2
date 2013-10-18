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
import javax.activation.FileTypeMap;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.util.ByteArrayDataSource;

/**
 * A byte array to be an attachment.
 */
class BytesAttachment 
extends AbstractAttachment
implements Attachment {
	/**
	 * Binary content.
	 */
	byte[] content;	
	
	
	/**
	 * Creates a new MailAttachment object. 
	 *
	 * @param content
	 * @param filename
	 */
	public BytesAttachment(byte[] content, String filename) {
		super(filename);
		this.content = content;
	}

	/**
	 * Gets the content.
	 *
	 * @return Returns the content
	 */
	public byte[] getContent() {
		return content;
	}
	
	public MimeBodyPart getMimeBodyPart() throws MessagingException {		
		FileTypeMap map = FileTypeMap.getDefaultFileTypeMap();
		String mimetype = map.getContentType(filename);		
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		DataSource datasource = new ByteArrayDataSource(content, mimetype);		
		DataHandler datahandler = new DataHandler(datasource);
		mimeBodyPart.setDataHandler(datahandler);				
		mimeBodyPart.setFileName(filename);
		mimeBodyPart.setDisposition(MimeBodyPart.ATTACHMENT);
		return mimeBodyPart;
	}
	
}
