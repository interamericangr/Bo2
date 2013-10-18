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
package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.arch.exceptions.WebServiceException;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.utils.ExceptionUtils;

/**
 * Wrapper for execution of {@link RuntimeCommand}s in web services.
 */
public class WebServiceRuntimeCommand {
	
	/**
	 * Wrapped command.
	 */
	RuntimeCommand cmd;
	
	/**
	 * Creates a new WebServiceRuntimeCommand object. 
	 * @param cmd 
	 */
	public WebServiceRuntimeCommand(RuntimeCommand cmd) {
		this.cmd = cmd;
	}
	
	/**
	 * Executes the command.
	 * @throws WebServiceException
	 */
	public void execute() throws WebServiceException {
		try {
			cmd.execute();
		} catch (UnexpectedException e) {
			throw new WebServiceException(e);
		} catch (DataException e) {
			throw new WebServiceException(e);
		} catch (LogicException e) {
			throw new WebServiceException(e);
		} catch (RuntimeException e) {
			Bo2.getLogger().error(ExceptionUtils.getThrowableStackTrace(e));
			throw new WebServiceException(e);
		}
	}

}
