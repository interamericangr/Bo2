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
package gr.interamerican.wicket.bo2.markup.html.form;

import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.meta.descriptors.NumberBoPropertyDescriptor;

/**
 * Self-drawn Integer TextField.
 */
public class SelfDrawnIntegerTextField extends AbstractSelfDrawnTextField<Integer>{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new IntegerSelfDrawnTextField object. 
	 *
	 * @param id the id
	 * @param descriptor the descriptor
	 */
	public SelfDrawnIntegerTextField(String id, NumberBoPropertyDescriptor<Integer> descriptor) {
		super(id, descriptor, Integer.class);
	}
	
	/**
	 * Creates a new IntegerSelfDrawnTextField object. 
	 *
	 * @param id the id
	 * @param model the model
	 * @param descriptor the descriptor
	 */
	public SelfDrawnIntegerTextField(String id, IModel<Integer> model, NumberBoPropertyDescriptor<Integer> descriptor) {
		super(id, model, descriptor, Integer.class);
	}
}