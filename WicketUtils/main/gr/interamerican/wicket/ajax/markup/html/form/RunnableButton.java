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
package gr.interamerican.wicket.ajax.markup.html.form;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.functions.SerializableRunnable;

/**
 * A {@link Button} that {@link #onSubmit()} executes the given
 * {@link SerializableRunnable}.
 */
public class RunnableButton extends Button {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 275L;

	/**
	 * {@link SerializableRunnable} to execute.
	 */
	private final SerializableRunnable runnable;

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Component id
	 * @param runnable
	 *            {@link SerializableRunnable} to execute
	 */
	public RunnableButton(String id, SerializableRunnable runnable) {
		super(id);
		this.runnable = runnable;
	}

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Component id
	 * @param model
	 *            The model property
	 * @param runnable
	 *            {@link SerializableRunnable} to execute
	 */
	public RunnableButton(String id, IModel<String> model, SerializableRunnable runnable) {
		super(id, model);
		this.runnable = runnable;
	}

	@Override
	public void onSubmit() {
		runnable.run();
	}
}