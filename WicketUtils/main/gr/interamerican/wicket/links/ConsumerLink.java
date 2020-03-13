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
package gr.interamerican.wicket.links;

import java.util.function.Consumer;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.functions.SerializableConsumer;
import gr.interamerican.bo2.utils.functions.SerializableRunnable;

/**
 * A {@link Link} of {@link Void} that executes a {@link SerializableRunnable}.
 * 
 * @param <T>
 *            type of model object
 */
public class ConsumerLink<T> extends Link<T> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link Consumer} to execute
	 */
	private Consumer<T> consumer;

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id
	 * @param model
	 *            Model Object
	 * @param consumer
	 *            {@link Consumer} to execute
	 */
	public ConsumerLink(String id, IModel<T> model, SerializableConsumer<T> consumer) {
		super(id, model);
		this.consumer = consumer;
	}

	@Override
	public void onClick() {
		consumer.accept(getModelObject());
	}
}