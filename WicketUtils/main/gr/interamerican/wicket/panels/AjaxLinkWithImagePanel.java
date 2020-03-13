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

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ResourceReference;

import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.links.LegacyCallbackAjaxLink;
import gr.interamerican.wicket.links.PickActionAjaxLink;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * Panel that contains a {@link LegacyCallbackAjaxLink} or
 * {@link PickActionAjaxLink} that is displayed as an image.
 */
public class AjaxLinkWithImagePanel extends Panel {

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
	 *            {@link LegacyCallbackAction} to be executed when the link is
	 *            pressed
	 * @param resource
	 *            {@link ResourceReference} for the Image
	 * @param behaviors
	 *            Behaviors Added to the {@link AjaxLink} Embedded in this Panel
	 */
	public AjaxLinkWithImagePanel(String id, LegacyCallbackAction action, ResourceReference resource, Behavior... behaviors) {
		this(id, new LegacyCallbackAjaxLink(LINK_ID, action), resource, behaviors);
	}

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id of the Panel
	 * @param action
	 *            {@link LegacyCallbackAction} to be executed when the link is
	 *            pressed
	 * @param image
	 *            {@link EmbeddedImage} to use
	 * @param behaviors
	 *            Behaviors Added to the {@link AjaxLink} Embedded in this Panel
	 */
	public AjaxLinkWithImagePanel(String id, LegacyCallbackAction action, EmbeddedImage image, Behavior... behaviors) {
		this(id, action, image.getReference(), behaviors);
	}

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id of the Panel
	 * @param model
	 *            Model Object
	 * @param action
	 *            {@link PickAction} to be executed when the link is pressed
	 * @param resource
	 *            {@link ResourceReference} for the Image
	 * @param behaviors
	 *            Behaviors Added to the {@link AjaxLink} Embedded in this Panel
	 */
	public <T> AjaxLinkWithImagePanel(String id, IModel<T> model, PickAction<T> action, ResourceReference resource,
			Behavior... behaviors) {
		this(id, model, action, resource, null, behaviors);
	}

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id of the Panel
	 * @param model
	 *            Model Object
	 * @param action
	 *            {@link PickAction} to be executed when the link is pressed
	 * @param resource
	 *            {@link ResourceReference} for the Image
	 * @param confirmationMessage
	 *            Text to be displayed on the confirmation dialog (optional)
	 * @param behaviors
	 *            Behaviors Added to the {@link AjaxLink} Embedded in this Panel
	 */
	public <T> AjaxLinkWithImagePanel(String id, IModel<T> model, PickAction<T> action, ResourceReference resource,
			String confirmationMessage, Behavior... behaviors) {
		this(id, new PickActionAjaxLink<>(LINK_ID, model, action, confirmationMessage), resource, behaviors);
	}

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id of the Panel
	 * @param model
	 *            Model Object
	 * @param action
	 *            {@link PickAction} to be executed when the link is pressed
	 * @param image
	 *            {@link EmbeddedImage} to use
	 * @param behaviors
	 *            Behaviors Added to the {@link AjaxLink} Embedded in this Panel
	 */
	public <T> AjaxLinkWithImagePanel(String id, IModel<T> model, PickAction<T> action, EmbeddedImage image,
			Behavior... behaviors) {
		this(id, model, action, image.getReference(), behaviors);
	}

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id of the Panel
	 * @param model
	 *            Model Object
	 * @param action
	 *            {@link PickAction} to be executed when the link is pressed
	 * @param image
	 *            {@link EmbeddedImage} to use
	 * @param confirmationMessage
	 *            Text to be displayed on the confirmation dialog (optional)
	 * @param behaviors
	 *            Behaviors Added to the {@link AjaxLink} Embedded in this Panel
	 */
	public <T> AjaxLinkWithImagePanel(String id, IModel<T> model, PickAction<T> action, EmbeddedImage image,
			String confirmationMessage, Behavior... behaviors) {
		this(id, model, action, image.getReference(), confirmationMessage, behaviors);
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
	 * @param behaviors
	 *            Behaviors Added to the {@link AjaxLink} Embedded in this Panel
	 */
	private AjaxLinkWithImagePanel(String id, AjaxLink<?> link, ResourceReference resource, Behavior... behaviors) {
		super(id);
		link.add(new Image("image", resource)); //$NON-NLS-1$
		link.add(behaviors);
		add(link);
	}
}