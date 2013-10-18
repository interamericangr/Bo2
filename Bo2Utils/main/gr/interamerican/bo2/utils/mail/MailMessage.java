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

import static gr.interamerican.bo2.utils.StringConstants.EMPTY;
import static gr.interamerican.bo2.utils.StringConstants.SEMICOLON;
import static gr.interamerican.bo2.utils.StringConstants.SPACE;
import gr.interamerican.bo2.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Email message.
 */
public class MailMessage {
	/**
	 * Sender name.
	 */
	String from;
	/**
	 * To list.
	 */
	List<String> toList = new ArrayList<String>();
	/**
	 * CC list.
	 */
	List<String> ccList = new ArrayList<String>();
	/**
	 * BCC list.
	 */
	List<String> bccList = new ArrayList<String>();
	
	/**
	 * Message body.
	 */
	String message = EMPTY;
	/**
	 * Subject.
	 */
	String subject =  EMPTY;

	/**
	 * Subject encoding.
	 */
	String subjectCharset = MailDefaults.INSTANCE.getMessageSubjectCharset();
	
	/**
	 * Message content encoding.
	 */
	String messageCharset = MailDefaults.INSTANCE.getMessageBodyCharset();
	
	/**
	 * Message type.
	 */
	String messageType = MailDefaults.INSTANCE.getMessageType();
	
	/**
	 * Attachements.
	 */
	List<Attachment> attachments = new ArrayList<Attachment>();
	
	
	/**
	 * Validates the message before trying to send.
	 */
	void validate() {
		if (toList == null) {
			throw new RuntimeException("No recipient"); //$NON-NLS-1$
		}
	}

	/**
	 * Sends the message.
	 * 
	 * @param smtp 
	 *        Server to get an SMTP session.
	 * 
	 */
	void send(MailServer smtp) {
		try {
			validate();		
			MimeMessage msg = new MimeMessage(smtp.getSession());
			
			if (from != null) {
				msg.setFrom(new InternetAddress(from));
			}		
			for (String string : toList) {
				InternetAddress adress = new InternetAddress(string);
				msg.addRecipient(Message.RecipientType.TO, adress);
			}
			for (String string : ccList) {
				InternetAddress adress = new InternetAddress(string);
				msg.addRecipient(Message.RecipientType.CC, adress);
			}
			for (String string : bccList) {
				InternetAddress adress = new InternetAddress(string);
				msg.addRecipient(Message.RecipientType.BCC, adress);
			}		
			msg.setSubject(subject, subjectCharset);

			// main body
			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();	
			
			if (messageType==MailConstants.TYPE_TEXT) {
				messageBodyPart.setText(message, messageCharset);		
			} else {
				String contentType = getContentType();
				messageBodyPart.setContent(message, contentType);
			}				
			multipart.addBodyPart(messageBodyPart);
			
			for (Attachment attachment : attachments) {
				MimeBodyPart mimeBodyPart = attachment.getMimeBodyPart();
				multipart.addBodyPart(mimeBodyPart);
			}					
			msg.setContent(multipart);
			
			Transport.send(msg);
		} catch (AddressException e) {
			throw new RuntimeException(e);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	

	/**
	 * Sends the message.
	 */
	public void send() {
		send(MailServer.INSTANCE);		
	}
		
	
	

	/**
	 * Adds an attachment.
	 * 
	 * @param content
	 *        Content of the attachment.
	 * @param filename
	 *        Filename of the attachment.
	 * 
	 */
	public void addAttachment(byte[] content, String filename) {
		Attachment attachment = new BytesAttachment(content, filename);
		attachments.add(attachment);
	}
	
	/**
	 * Adds an attachment.
	 * 
	 * @param filename
	 *        Filename of the attachment. 
	 *
	 */
	public void addAttachment(String filename) {
		Attachment attachment = new FileAttachment(filename);
		attachments.add(attachment);
	}

	/**
	 * Sets the message subject
	 * 
	 * @param string
	 */
	public void setSubject(String string) {
		subject = string;
	}
	
	/**
	 * Adds a recipient.
	 * 
	 * @param string
	 */
	public void addTo(String string) {
		toList.add(string);
	}
	
	/**
	 * Adds a recipient.
	 * 
	 * @param string
	 */
	public void addCc(String string) {
		ccList.add(string);
	}

	/**
	 * Adds a recipient.
	 * 
	 * @param string
	 */
	public void addBcc(String string) {
		bccList.add(string);
	}

	/**
	 * Sets the sender
	 * 
	 * @param string
	 */
	public void setFrom(String string) {
		from = string;
	}

	/**
	 * Sets the message
	 * 
	 * @param string
	 */
	public void setMessage(String string) {
		message = string;
	}

	/**
	 * Gets the subjectEncoding.
	 *
	 * @return Returns the subjectEncoding
	 */
	public String getSubjectCharset() {
		return subjectCharset;
	}

	/**
	 * Assigns a new value to the subjectEncoding.
	 *
	 * @param subjectEncoding the subjectEncoding to set
	 */
	public void setSubjectCharset(String subjectEncoding) {
		this.subjectCharset = subjectEncoding;
	}

	/**
	 * Gets the messageEncoding.
	 *
	 * @return Returns the messageEncoding
	 */
	public String getMessageCharset() {
		return messageCharset;
	}

	/**
	 * Assigns a new value to the messageEncoding.
	 *
	 * @param messageEncoding the messageEncoding to set
	 */
	public void setMessageCharset(String messageEncoding) {
		this.messageCharset = messageEncoding;
	}

	/**
	 * Gets the from.
	 *
	 * @return Returns the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Gets the toList.
	 *
	 * @return Returns the toList
	 */
	public List<String> getToList() {
		return toList;
	}

	/**
	 * Gets the ccList.
	 *
	 * @return Returns the ccList
	 */
	public List<String> getCcList() {
		return ccList;
	}

	/**
	 * Gets the bccList.
	 *
	 * @return Returns the bccList
	 */
	public List<String> getBccList() {
		return bccList;
	}

	/**
	 * Gets the message.
	 *
	 * @return Returns the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the subject.
	 *
	 * @return Returns the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Gets the attachments.
	 *
	 * @return Returns the attachments
	 */
	public List<Attachment> getAttachments() {
		return attachments;
	}
	
	/**
	 * Content type.
	 * @return Returns the string for content tyoe.
	 */
	String getContentType() {
		return StringUtils.concat(
			messageType, SEMICOLON, SPACE, 
			"charset=", messageCharset); //$NON-NLS-1$
	}

	/**
	 * Gets the messageType.
	 *
	 * @return Returns the messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * Assigns a new value to the messageType.
	 *
	 * @param messageType the messageType to set
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	
	
}
