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


/**
 * Binary file attachment.
 */
abstract class AbstractAttachment
implements Attachment {
	/**
	 * Filename.
	 */
	protected String filename;
	
	/**
	 * Creates a new MailAttachment object. 
	 * 
	 * @param filename
	 */
	public AbstractAttachment(String filename) {
		super();		
		this.filename = filename;	
	}


	/**
	 * Gets the filename.
	 *
	 * @return Returns the filename
	 */
	public String getFilename() {
		return filename;
	}

	
}
