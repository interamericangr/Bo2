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
package gr.interamerican.bo2.gui.handlers;

import gr.interamerican.bo2.gui.frames.PopUpFrame;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.handlers.ExceptionHandler;
import gr.interamerican.bo2.utils.handlers.ThrowingExceptionHandler;

/**
 * <p>{@link ExceptionHandler} that pops up a message box with the
 * exception message.
 * 
 * <p>If the thrown is an Error or a RuntimeException, it is 
 * re-thrown as is. Otherwise, it is wrapped inside a 
 * RuntimeException. 
 */
public class PopUpExceptionHandler 
extends ThrowingExceptionHandler
implements ExceptionHandler {
	/**
	 * Instance.
	 */
	public static final PopUpExceptionHandler INSTANCE = new PopUpExceptionHandler();
	
	@Override
	public void handle(Throwable t) {
		String msg = ExceptionUtils.getThrowableStackTrace(t);
		PopUpFrame.popUp(msg,"ERROR"); //$NON-NLS-1$
		super.handle(t);
	}

}
