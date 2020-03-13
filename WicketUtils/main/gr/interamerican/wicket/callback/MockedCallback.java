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

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

/**
 * Dummy action.
 * 
 * This action does nothing, however, it has a field that tells if
 * it has been executed.
 */
@SuppressWarnings("rawtypes")
public class MockedCallback implements LegacyCallbackAction, ICallbackAction, PickAction,
MultiplePickAction, Consume, ProcessAction, SearchAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/**
	 * Shows if it has been excuted.
	 */
	private boolean executed = false;

	@Override
	public List search(Object criteria) throws Exception {
		executed = true;
		return null;
	}

	@Override
	public Object process(Object bean) throws Exception {
		executed = true;
		return null;
	}

	@Override
	public void consume(Object bean) throws Exception {
		executed = true;
	}

	@Override
	public void doPick(AjaxRequestTarget target, List bean) {
		executed = true;
	}

	@Override
	public void pick(AjaxRequestTarget target, Object bean) throws Exception {
		executed = true;
	}

	@Override
	public void invoke(AjaxRequestTarget target, Form<?> form) throws Exception {
		executed = true;
	}

	@Override
	public void invoke(AjaxRequestTarget target) throws Exception {
		executed = true;
	}

	/**
	 * Gets the executed.
	 *
	 * @return Returns the executed
	 */
	public boolean isExecuted() {
		return executed;
	}

	/**
	 * Assigns a new value to the executed.
	 *
	 * @param executed the executed to set
	 */
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}
}