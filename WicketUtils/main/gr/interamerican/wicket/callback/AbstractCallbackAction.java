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
package gr.interamerican.wicket.callback;

import org.apache.wicket.Component;


/**
 * Abstract {@link CallbackAction}. 
 */
public abstract class AbstractCallbackAction 
implements CallbackAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Caller component.
	 */
	protected Component caller;
	

	public void setCaller(Component caller) {
		this.caller = caller;
	}

	
	public Component getCaller() {		
		return this.caller;
	}

}
