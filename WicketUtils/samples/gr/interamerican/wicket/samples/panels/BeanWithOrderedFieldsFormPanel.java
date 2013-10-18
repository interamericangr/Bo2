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

import gr.interamerican.bo2.samples.bean.BeanWithOrderedFields;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * 
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
	 * @param model 
	 */
	@SuppressWarnings("nls")
	public BeanWithOrderedFieldsFormPanel(String id, IModel<BeanWithOrderedFields> model) {
		super(id, model);
		
		TextField<String> firstField = new TextField<String>("first");
		TextField<String> secondField = new TextField<String>("second");
		TextField<Integer> thirdField = new TextField<Integer>("third");
		TextField<Long> fourthField = new TextField<Long>("fourth");
		TextField<Double> fifthField = new TextField<Double>("fifth");
		
		add(firstField);
		add(secondField);
		add(thirdField);
		add(fourthField);
		add(fifthField);
	}
	
}
