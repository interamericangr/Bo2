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
package gr.interamerican.wicket.samples.panels;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;

/**
 * The Class BeanWithOrderedFieldsFormPanel.
 */
public class BeanWithOrderedFieldsFormPanel extends Panel {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new BeanWithOrderedFieldsFormPanel object.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 */
	@SuppressWarnings("nls")
	public BeanWithOrderedFieldsFormPanel(String id, IModel<BeanWithOrderedFields> model) {
		super(id, model);
		add(new TextField<String>("first"));
		add(new TextField<String>("second"));
		add(new TextField<Integer>("third"));
		add(new TextField<Long>("fourth"));
		add(new TextField<Double>("fifth"));
	}
}