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
package gr.interamerican.wicket.behavior;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;

import gr.interamerican.wicket.utils.MarkupConstants;

/**
 * This behavior can be added to a {@link FormComponent} just to
 * ensure that the model of the component is updated when the change event is
 * triggered.
 */
public class EmptyOnSelectionChangedBehavior extends AjaxFormComponentUpdatingBehavior {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The panel to re-render.
	 */
	private Panel panel;

	/**
	 * Creates a new EmptyOnSelectionChangedBehavior object.
	 * 
	 * @param panel
	 *            The panel to re-render.
	 */
	public EmptyOnSelectionChangedBehavior(Panel panel) {
		super(MarkupConstants.CHANGE);
		this.panel = panel;
	}

	@Override
	protected void onUpdate(AjaxRequestTarget target) {
		target.add(panel);
	}
}