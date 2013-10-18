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
package gr.interamerican.bo2.arch.ext;

/**
 * An {@link OutputMedium} is a class that can present output
 * to the user. 
 *  
 * The main usage of an output medium is to present errors or
 * other important messages to the user. Visual components as 
 * web pages are the obvious candidates for implementing this 
 * interface. Screen readers, mailers or any other message based
 * implementation can provide the same functionality.
 */
public interface OutputMedium {
	
	/**
	 * Presents a Throwable.
	 * 
	 * @param t Instance of {@link Throwable}
	 */
	public void showError (Throwable t);
	
	/**
	 * Presents a list of  messages.
	 * 
	 * @param messages Messages to present.
	 */
	public void showMessages (String[] messages);
	
	/**
	 * Clears the screen from any errors.
	 */
	public void clearMessages();
	
}
