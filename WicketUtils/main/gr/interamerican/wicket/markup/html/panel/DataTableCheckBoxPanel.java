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

import java.io.Serializable;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * {@link Panel} that contains a check box used to select
 * one of the rows of a {@link DataTable}.
 *
 * @param <T> type of model object.
 */
public class DataTableCheckBoxPanel<T extends Serializable> extends Panel {
	
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new DataTableCheckBoxPanel object. 
	 *
	 * @param id the id
	 * @param model the model
	 */
	public DataTableCheckBoxPanel(String id, IModel<T> model) {
		super(id, model);
		add(new Check<T>("checkBox", model)); //$NON-NLS-1$
	}
}