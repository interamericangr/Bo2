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
package gr.interamerican.wicket.bo2.markup.html.panel;

import gr.interamerican.wicket.callback.CallbackAction;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * A {@link CallbackActionPanel} is a {@link Panel} that receives
 * a List of {@link CallbackAction}s that are called when various
 * events are triggered within this panel.
 * 
 * It is this panel's responsibility to know how many {@link CallbackAction}s
 * it needs and in which order and the responsibility of its creators to
 * provide a valid List of {@link CallbackAction}s.
 */
public class CallbackActionPanel extends Panel {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * List of necessary {@link CallbackAction}s for this panel.
	 */
	protected List<CallbackAction> actions;

	/**
	 * Creates a new CallbackActionPanel object. 
	 *
	 * @param id
	 * @param actions 
	 */
	public CallbackActionPanel(String id, List<CallbackAction> actions) {
		super(id);
		this.actions = actions;
	}
	
	/**
	 * Executes the {@link CallbackAction}.
	 * 
	 * @param action
	 * @param target
	 */
	protected void callback(CallbackAction action, AjaxRequestTarget target) {
		if(action != null) {
			action.callBack(target);
		}
	}

}
