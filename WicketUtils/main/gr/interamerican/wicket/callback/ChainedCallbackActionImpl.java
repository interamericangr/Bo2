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

import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

/**
 * Implementation of {@link ChainedCallbackAction} based on a {@link Deque}.
 */
public class ChainedCallbackActionImpl extends AbstractCallbackAction implements ChainedCallbackAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Actions.
	 */
	ArrayDeque<CallbackAction> actions = new ArrayDeque<CallbackAction>();
	
	/**
	 * Has this {@link CallbackAction} been called?
	 */
	boolean wasCalled = false;
	
	/**
	 * Creates a new ChainedCallbackActionImpl object. 
	 */
	public ChainedCallbackActionImpl() {
		super();
	}
	
	/**
	 * Creates a new ChainedCallbackActionImpl object. 
	 * @param original 
	 */
	public ChainedCallbackActionImpl(CallbackAction original) {
		actions.addFirst(original);
	}

	public void callBack(AjaxRequestTarget target) {
		wasCalled = true;
		for(CallbackAction action : actions) {
			action.callBack(target);
		}
	}

	public void callBack(AjaxRequestTarget target, Form<?> form) {
		wasCalled = true;
		for(CallbackAction action : actions) {
			action.callBack(target, form);
		}
	}

	public void chainBefore(CallbackAction action) {
		sane();
		actions.addFirst(action);
	}

	public void chainAfter(CallbackAction action) {
		sane();
		actions.addLast(action);
	}

	/**
	 * Sanity check. Throws a RuntimeException if this action has been called
	 * already when a chain modification is requested.
	 */
	void sane() {
		if(wasCalled) {
			throw new RuntimeException("Illegal attempt to modify callback chain, after a call has been made."); //$NON-NLS-1$
		}
	}

}
