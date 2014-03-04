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
import org.apache.wicket.util.convert.converter.AbstractDecimalConverter;
import org.apache.wicket.util.convert.converter.FloatConverter;

/**
 * Self-drawn Float TextField.
 */
public class SelfDrawnFloatTextField extends AbstractSelfDrawnNumberTextField<Float>{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new FloatSelfDrawnTextField object. 
	 *
	 * @param id
	 * @param descriptor
	 */
	public SelfDrawnFloatTextField(String id, NumberBoPropertyDescriptor<Float> descriptor) {
		super(id, descriptor, Float.class);
	}
	
	/**
	 * Creates a new FloatSelfDrawnTextField object. 
	 * 
	 * @param model 
	 * @param id
	 * @param descriptor
	 */
	public SelfDrawnFloatTextField(String id, IModel<Float> model, NumberBoPropertyDescriptor<Float> descriptor) {
		super(id, model, descriptor, Float.class);
	}
	
	@Override
	protected AbstractDecimalConverter<Float> getNumberCoverter() {
		return new FloatConverter();
	}
	
}
