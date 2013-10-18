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
package gr.interamerican.wicket.markup.html.form;

import gr.interamerican.bo2.utils.attributes.SimpleCommand;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * A {@link Form} that executes a {@link SimpleCommand} 
 * on its submit event.
 * 
 * @param <T>
 *        Type of Form model object. 
 */
public class SimpleCommandForm<T> 
extends Form<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Command to execute on the submit event. 
	 */
	SimpleCommand action;

	/**
	 * Creates a new CallbackActionForm object. 
	 *
	 * @param id
	 * @param action 
	 */
	public SimpleCommandForm(String id, SimpleCommand action) {
		super(id);
		this.action = action;
	}
	
	/**
	 * Creates a new CallbackActionForm object. 
	 *
	 * @param id
	 * @param model 
	 * @param action 
	 */
	public SimpleCommandForm(String id, IModel<T> model, SimpleCommand action) {
		super(id,model);
		this.action = action;
	}
	
	@Override
	protected void onSubmit() {
		action.execute();		
		super.onSubmit();
	}

}
