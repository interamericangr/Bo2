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
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.callback.PickAction;
import gr.interamerican.wicket.links.LegacyCallbackAjaxLink;
import gr.interamerican.wicket.links.PickActionAjaxLink;

/**
 * Panel that contains a {@link LegacyCallbackAjaxLink} or
 * {@link PickActionAjaxLink}.
 */
public class AjaxLinkPanel extends Panel {

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
	 * @param bodyModel
	 *            Link's body model (displayed)
	 * @param behaviors
	 *            Behaviors Added to the {@link AjaxLink} Embedded in this Panel
	 */
	public AjaxLinkPanel(String id, LegacyCallbackAction action, IModel<?> bodyModel, Behavior... behaviors) {
		this(id, new LegacyCallbackAjaxLink(LINK_ID, action), bodyModel, behaviors);
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
	 * @param bodyModel
	 *            Link's body model (displayed)
	 * @param behaviors
	 *            Behaviors Added to the {@link AjaxLink} Embedded in this Panel
	 */
	public <T> AjaxLinkPanel(String id, IModel<T> model, PickAction<T> action, IModel<?> bodyModel, Behavior... behaviors) {
		this(id, new PickActionAjaxLink<>(LINK_ID, model, action), bodyModel, behaviors);
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
	 *            Behaviors Added to the {@link AjaxLink} Embedded in this Panel
	 */
	private AjaxLinkPanel(String id, AjaxLink<?> link, IModel<?> bodyModel, Behavior... behaviors) {
		super(id);
		link.add(behaviors);
		link.setBody(bodyModel);
		add(link);
	}
}