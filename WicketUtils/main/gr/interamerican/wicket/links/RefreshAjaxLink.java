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

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;

/**
 * A {@link AjaxLink} that on execute inverts the visibility of an
 * {@link Component}s child.
 */
public class RefreshAjaxLink extends AjaxLink<Void> {

	/**
	 * serialize.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Container of the Component
	 */
	private final MarkupContainer container;

	/**
	 * Wicket id of the Component within the container
	 */
	private final String componentName;

	/**
	 * Public Constructor.
	 *
	 * @param id
	 *            Wicket Id of this
	 * @param container
	 *            Container of the Component
	 * @param componentName
	 *            Wicket id of the Component within the container
	 */
	public RefreshAjaxLink(String id, MarkupContainer container, String componentName) {
		super(id);
		this.container = container;
		this.componentName = componentName;
	}

	@Override
	public void onClick(AjaxRequestTarget target) {
		Component component = container.get(componentName);
		boolean newVisibility = !component.isVisible();
		component.setVisible(newVisibility);
		target.add(component);
	}
}