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

import static gr.interamerican.bo2.utils.StringConstants.COMMA;
import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.utils.ArrayUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.attributes.ModifiableByProperties;
import gr.interamerican.bo2.utils.mail.MailMessage;
import gr.interamerican.bo2.utils.runnables.AbstractMonitoringOperation;
import gr.interamerican.bo2.utils.runnables.MonitoringOperation;

import java.util.Properties;

/**
 * Sends an eMail with the status of a long process.
 */
public class LongProcessMail
extends AbstractMonitoringOperation<LongProcess>
implements MonitoringOperation<LongProcess>, ModifiableByProperties {
	
	/**
	 * Property key for monitoringMailRecipients.
	 */
	public static final String MONITOR_MESSAGE_RECIPIENTS = "monitoringMailRecipients"; //$NON-NLS-1$
	/**
	 * Property key for interval.
	 * This property defines the interval between two executions
	 * in milliseconds.
	 */
	public static final String MONITOR_MESSAGE_INTERVAL = "monitoringMailInterval"; //$NON-NLS-1$
	
	/**
	 * Property key for monitoringMailFrom.
	 */
	public static final String MONITOR_MESSAGE_FROM = "monitoringMailFrom"; //$NON-NLS-1$
	
	
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
	public void execute(LongProcess a) {
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
	
	@Override
	public void beModified(Properties properties) {
		setIntervalFromProperties(properties, MONITOR_MESSAGE_INTERVAL);
		String strRecipients = properties.getProperty(MONITOR_MESSAGE_RECIPIENTS);		
		setStatusMessageRecipients(strRecipients);
		String strFrom = properties.getProperty(MONITOR_MESSAGE_FROM);
		from = Utils.notNull(strFrom, "LongProcessMonitor"); //$NON-NLS-1$
	}


	@Override
	public boolean isValid() {		
		if (ArrayUtils.isNullOrEmpty(recipients)) {
			return false;
		}
		return super.isValid();
	}	
	

}
