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

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ResourceReference;

import gr.interamerican.bo2.utils.functions.SerializableConsumer;
import gr.interamerican.bo2.utils.functions.SerializableRunnable;
import gr.interamerican.wicket.links.ConsumerLink;
import gr.interamerican.wicket.links.RunnableLink;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * Panel that contains a {@link RunnableLink} or {@link ConsumerLink} that is
 * displayed as an image.
 */
public class LinkWithImagePanel extends Panel {

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
	 * @param resource
	 *            {@link ResourceReference} for the Image
	 */
	public LinkWithImagePanel(String id, SerializableRunnable action, ResourceReference resource) {
		this(id, new RunnableLink(LINK_ID, action), resource);
	}

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id of the Panel
	 * @param action
	 *            {@link SerializableRunnable} to be executed when the link is
	 *            pressed
	 * @param image
	 *            {@link EmbeddedImage} to use
	 */
	public LinkWithImagePanel(String id, SerializableRunnable action, EmbeddedImage image) {
		this(id, action, image.getReference());
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
	 * @param resource
	 *            {@link ResourceReference} for the Image
	 */
	public <T> LinkWithImagePanel(String id, IModel<T> model, SerializableConsumer<T> action,
			ResourceReference resource) {
		this(id, new ConsumerLink<>(LINK_ID, model, action), resource);
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
	 * @param image
	 *            {@link EmbeddedImage} to use
	 */
	public <T> LinkWithImagePanel(String id, IModel<T> model, SerializableConsumer<T> action, EmbeddedImage image) {
		this(id, model, action, image.getReference());
	}

	/**
	 * Private Constructor.
	 * 
	 * @param id
	 *            Wicket Id
	 * @param link
	 *            Actual Link With {@link #LINK_ID} wicket Id
	 * @param resource
	 *            {@link ResourceReference} for the Image
	 */
	private LinkWithImagePanel(String id, Link<?> link, ResourceReference resource) {
		super(id);
		link.add(new Image("image", resource)); //$NON-NLS-1$
		add(link);
	}
}