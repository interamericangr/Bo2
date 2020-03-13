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

import org.apache.wicket.markup.html.link.Link;

import gr.interamerican.bo2.utils.functions.SerializableRunnable;

/**
 * A {@link Link} of {@link Void} that executes a {@link SerializableRunnable}.
 */
public class RunnableLink extends Link<Void> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link SerializableRunnable} to execute
	 */
	private SerializableRunnable runnable;

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id
	 * @param runnable
	 *            {@link SerializableRunnable} to execute
	 */
	public RunnableLink(String id, SerializableRunnable runnable) {
		super(id);
		this.runnable = runnable;
	}

	@Override
	public void onClick() {
		runnable.run();
	}
}