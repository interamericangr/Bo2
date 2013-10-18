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
package gr.interamerican.bo2.utils.handlers;



import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * {@link AbstractEventHandler} provides a base for implementing 
 * event handlers.
 * 
 * @param <C> 
 *        Type of caller object.
 */
public abstract class AbstractEventHandler<C> 
implements Serializable, Called<C> {
	
	/**
	 * serial uid.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Components that are passed as parameters in an object's event handler
	 */
	transient Map<Class<?>, Object> handlerParameters;
	
	/**
	 * Component that calls this {@link AbstractEventHandler}.
	 */
	protected C caller;
	
	/**
	 * ExceptionHandler to handle exceptions thrown.
	 */
	protected ExceptionHandler exceptionHandler;
	
	/**
	 * Creates a new AbstractEventHandler object. 
	 * 
	 * @param exceptionHandler
	 *        ExceptionHandler. 
	 *
	 */
	public AbstractEventHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	/**
	 * Gets a parameter of an event handler.
	 * 
	 * @param paramType
	 *        Type of parameter.
	 * @param <T> 
	 *        Type of parameter.
	 *  
	 * @return Gets the parameter with the specified type.
	 */
	public <T> T getHandlerParameter(Class<T> paramType) {
		@SuppressWarnings("unchecked")
		T t = (T) getHandlerParameters().get(paramType);
		return t;
	}
	
	/**
	 * Sets a handler parameter.
	 * 
	 * Factory methods that create components by overriding an event 
	 * handler of the component so that the event handler runs the
	 * event handler should set the event handler parameter with
	 * this method.
	 * 
	 * @param paramType 
	 *        Type of parameter. 
	 * @param param 
	 *        Parameter.
	 * @param <T>
	 *        Type of parameter.
	 */
	public <T> void setHandlerParameter(Class<T> paramType, T param) {
		getHandlerParameters().put(paramType, param);
	}
	
	/**
	 * Null safe getter.
	 * 
	 * @return Gets the handler parameters.
	 */
	protected Map<Class<?>, Object> getHandlerParameters() {
		if (handlerParameters==null) {
			handlerParameters = new HashMap<Class<?>, Object>();
		}
		return handlerParameters;
	}
	
	public C getCaller() {
		return caller;
	}

	public void setCaller(C caller) {
		this.caller = caller;
	}
	
	/**
	 * Delegates handling of the specified thrown
	 * exception to the exceptionHandler.
	 * 
	 * @param t
	 */
	public void handleThrown(Throwable t) {
		exceptionHandler.handle(t);
	}

		
}
