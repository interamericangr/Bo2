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

import gr.interamerican.bo2.utils.meta.descriptors.NumberBoPropertyDescriptor;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.converters.AbstractDecimalConverter;
import org.apache.wicket.util.convert.converters.DoubleConverter;

/**
 * Self-drawn Double TextField.
 */
public class SelfDrawnDoubleTextField extends AbstractSelfDrawnNumberTextField<Double>{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new DoubleSelfDrawnTextField object. 
	 *
	 * @param id
	 * @param descriptor
	 */
	public SelfDrawnDoubleTextField(String id, NumberBoPropertyDescriptor<Double> descriptor) {
		super(id, descriptor);
	}
	
	/**
	 * Creates a new DoubleSelfDrawnTextField object. 
	 *
	 * @param id
	 * @param descriptor
	 * @param model
	 */
	public SelfDrawnDoubleTextField(String id, IModel<Double> model, NumberBoPropertyDescriptor<Double> descriptor) {
		super(id, model, descriptor);
	}
	
	@Override
	protected AbstractDecimalConverter getNumberCoverter() {
		return new DoubleConverter();
	}
	
	@Override
	protected Class<Double> getModelObjectClass() {
		return Double.class;
	}
	
}
