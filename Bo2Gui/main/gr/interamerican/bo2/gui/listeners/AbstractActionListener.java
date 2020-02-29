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

import gr.interamerican.bo2.utils.exc.ExceptionHandler;
import gr.interamerican.bo2.utils.handlers.AbstractEventHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The listener interface for receiving abstractAction events.
 * The class that is interested in processing a abstractAction
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addAbstractActionListener</code> method. When
 * the abstractAction event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AbstractEventHandler
 */
public abstract class AbstractActionListener
extends AbstractEventHandler<Object>
implements ActionListener {
	
	/**
	 * Creates a new AbstractActionListener object. 
	 *
	 * @param exceptionHandler the exception handler
	 */
	public AbstractActionListener(ExceptionHandler exceptionHandler) {
		super(exceptionHandler);
	}

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		setHandlerParameter(ActionEvent.class, e);		
		work();
	}
	
	/**
	 * Main action.
	 */
	abstract void work();

}
