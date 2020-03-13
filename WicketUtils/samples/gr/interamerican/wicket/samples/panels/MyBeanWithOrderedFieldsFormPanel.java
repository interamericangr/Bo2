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

import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.Utils;

/**
 * The Class MyBeanWithOrderedFields.
 */
public class MyBeanWithOrderedFieldsFormPanel extends BeanWithOrderedFieldsFormPanel {
	
	/**
	 * serial id. 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new BeanWithOrderedFieldsFormPanel object. 
	 *
	 * @param id the id
	 * @param model the model
	 */
	public MyBeanWithOrderedFieldsFormPanel(String id, IModel<MyBeanWithOrderedFields> model) {
		super(id, Utils.cast(model));
	}
}