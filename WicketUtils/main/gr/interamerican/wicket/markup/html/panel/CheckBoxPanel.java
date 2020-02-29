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
package gr.interamerican.wicket.markup.html.panel;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * A {@link Panel} that contains just a {@link CheckBox}
 */
public class CheckBoxPanel extends Panel {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new CheckBoxPanel object.
	 *
	 * @param id
	 *            the id
	 * @param isSelected
	 *            Value of the combo box
	 */
	public CheckBoxPanel(String id, boolean isSelected) {
		super(id);
		add(new CheckBox("checkboxId", new Model<Boolean>(isSelected))); //$NON-NLS-1$
	}
}