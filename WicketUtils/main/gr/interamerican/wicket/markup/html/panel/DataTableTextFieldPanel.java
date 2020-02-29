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

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * Panel containing a textField.
 * 
 * @param <T>
 *            The object type
 */
public class DataTableTextFieldPanel<T> extends Panel {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new {@link DataTableTextFieldPanel} object.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @param property
	 *            the property
	 */
	public DataTableTextFieldPanel(String id, IModel<T> model, String property) {
		super(id, model);
		add(new TextField<T>("textField", new PropertyModel<T>(model.getObject(), property))); //$NON-NLS-1$
	}
}