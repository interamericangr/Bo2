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
package gr.interamerican.wicket.bo2.callbacks;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.callback.AbstractCommandCallback;
import gr.interamerican.wicket.callback.CallbackAction;

import java.io.Serializable;

import org.apache.wicket.RequestCycle;


/**
 * {@link Bo2WicketBlock} provides an abstract method that can
 * be used to run Bo2 operations.
 */
public abstract class Bo2WicketBlock 
extends AbstractCommandCallback
implements Serializable, CallbackAction {
	
	/**
	 * Creates a new Bo2WicketBlock object. 
	 */
	public Bo2WicketBlock() {
		super();
	}

	/**
	 * serial uid.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Execution main part.
	 * 
	 * Main work of the operation.
	 *  
	 * @throws InitializationException
	 * @throws DataException
	 * @throws LogicException
	 */
	public abstract void work() 
	throws InitializationException, DataException, LogicException;

	/**
	 * Execution body.
	 */
	@Override
	public void execute() {
		try {
			work();
		} catch (InitializationException ie) {
			handleThrown(ie);
		} catch (DataException de) {
			handleThrown(de);
		} catch (LogicException le) {
			if(caller!=null) {
				caller.error(le.getMessage());
			} 
			handleThrown(le);
		} 
	}	
	
	/**
	 * Gets the provider of the current {@link RequestCycle}.
	 * 
	 * @return Returns the provider of this RequestCycle.
	 */
	protected Provider getProvider() {
		Bo2WicketRequestCycle requestCycle = Bo2WicketRequestCycle.get();
		return requestCycle.getProvider();
	}
		
}
