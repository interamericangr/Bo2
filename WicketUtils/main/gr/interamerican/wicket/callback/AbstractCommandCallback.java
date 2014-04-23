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

import gr.interamerican.bo2.utils.attributes.SimpleCommand;
import gr.interamerican.bo2.utils.handlers.AbstractEventHandler;
import gr.interamerican.bo2.utils.handlers.ThrowingExceptionHandler;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;


/**
 * {@link AbstractCommandCallback} is a base class for command pattern
 * based callbacks, namely callbacks that have a main <code>execute()</code> 
 * method.
 */
public abstract class AbstractCommandCallback 
extends AbstractEventHandler<Component>
implements CallbackAction, SimpleCommand {
	
	/**
	 * serial uid.
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Creates a new AbstractEventHandlerCallback object. 
	 */
	public AbstractCommandCallback() {
		super(ThrowingExceptionHandler.INSTANCE);
	}

	public void callBack(AjaxRequestTarget target) {
		this.setHandlerParameter(AjaxRequestTarget.class, target);
		this.setHandlerParameter(Component.class, caller);
		this.execute();
	}

	public void callBack(AjaxRequestTarget target, Form<?> form) {		
		this.setHandlerParameter(Form.class, form);
		callBack(target);
	}
		
}
