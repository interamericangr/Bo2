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
package gr.interamerican.wicket.panels;

import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.functions.SerializableConsumer;
import gr.interamerican.bo2.utils.functions.SerializableRunnable;
import gr.interamerican.wicket.links.ConsumerLink;
import gr.interamerican.wicket.links.RunnableLink;

/**
 * Panel that contains a {@link RunnableLink} or {@link ConsumerLink}.
 */
public class LinkPanel extends Panel {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Wicket Id for the Link
	 */
	private static final String LINK_ID = "link"; //$NON-NLS-1$

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id of the Panel
	 * @param action
	 *            {@link SerializableRunnable} to be executed when the link is
	 *            pressed
	 * @param bodyModel
	 *            Link's body model (displayed)
	 * @param behaviors
	 *            Behaviors Added to the {@link Link} Embedded in this Panel
	 */
	public LinkPanel(String id, SerializableRunnable action, IModel<?> bodyModel, Behavior... behaviors) {
		this(id, new RunnableLink(LINK_ID, action), bodyModel, behaviors);
	}

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id of the Panel
	 * @param model
	 *            Model Object
	 * @param action
	 *            {@link SerializableConsumer} to be executed when the link is
	 *            pressed
	 * @param bodyModel
	 *            Link's body model (displayed)
	 * @param behaviors
	 *            Behaviors Added to the {@link Link} Embedded in this Panel
	 */
	public <T> LinkPanel(String id, IModel<T> model, SerializableConsumer<T> action, IModel<?> bodyModel,
			Behavior... behaviors) {
		this(id, new ConsumerLink<>(LINK_ID, model, action), bodyModel, behaviors);
	}

	/**
	 * Private Constructor.
	 * 
	 * @param id
	 *            Wicket Id
	 * @param link
	 *            Actual Link With {@link #LINK_ID} wicket Id
	 * @param bodyModel
	 *            Link's body model (displayed)
	 * @param behaviors
	 *            Behaviors Added to the {@link Link} Embedded in this Panel
	 */
	private LinkPanel(String id, Link<?> link, IModel<?> bodyModel, Behavior... behaviors) {
		super(id);
		link.add(behaviors);
		link.setBody(bodyModel);
		add(link);
	}
}