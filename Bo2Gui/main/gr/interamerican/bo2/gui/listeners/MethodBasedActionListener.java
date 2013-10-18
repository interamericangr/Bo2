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
package gr.interamerican.bo2.gui.listeners;

import gr.interamerican.bo2.arch.ext.OutputMedium;
import gr.interamerican.bo2.gui.handlers.PopUpExceptionHandler;
import gr.interamerican.bo2.utils.handlers.MethodInvocator;

/**
 * Method based action listener.
 */
public class MethodBasedActionListener 
extends AbstractActionListener {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Method name.
	 */
	String methodName;
	/**
	 * Method invocator.
	 */
	MethodInvocator mi;
	/**
	 * Show messages here, if available.
	 */
	OutputMedium medium;
	
	/**
	 * Creates a new MethodBasedActionListener object. 
	 *
	 * @param methodName
	 * @param owner
	 */
	public MethodBasedActionListener(String methodName, Object owner) {
		super(PopUpExceptionHandler.INSTANCE);
		this.methodName = methodName;
		setCaller(owner);
		if(caller instanceof OutputMedium) {
			medium = (OutputMedium) caller;
		}
	}
	
	@Override
	public void setCaller(Object caller) {
		super.setCaller(caller);
		mi = new MethodInvocator(this, methodName, caller);
	}

	@Override
	void work() {
		if(medium!=null) {
			medium.clearMessages();
		}
		mi.invoke();	
	}

}
