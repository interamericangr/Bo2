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
package gr.interamerican.bo2.samples.implopen.runtime.concurrent;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;

/**
 * Operation that prints a String.
 */
public class PrintStringOperation 
extends AbstractOperation {
	
	/**
	 * String to print.
	 */
	String string;
	
	@Override
	public void execute() throws LogicException, DataException {
		System.out.println("S:" + string); //$NON-NLS-1$
	}

	/**
	 * Gets the string.
	 *
	 * @return Returns the string
	 */
	public String getString() {
		return string;
	}

	/**
	 * Assigns a new value to the string.
	 *
	 * @param string the string to set
	 */
	public void setString(String string) {
		this.string = string;
	}

}
