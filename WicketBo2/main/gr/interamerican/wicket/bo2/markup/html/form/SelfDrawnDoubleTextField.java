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
import org.apache.wicket.util.convert.IConverter;

import gr.interamerican.bo2.utils.meta.descriptors.NumberBoPropertyDescriptor;

/**
 * Self-drawn Double TextField.
 */
public class SelfDrawnDoubleTextField extends AbstractSelfDrawnTextField<Double> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Converter in use
	 */
	private final DoubleConverterForSelfDrawn converter;

	/**
	 * Creates a new DoubleSelfDrawnTextField object.
	 *
	 * @param id
	 *            the id
	 * @param descriptor
	 *            the descriptor
	 */
	public SelfDrawnDoubleTextField(String id, NumberBoPropertyDescriptor<Double> descriptor) {
		super(id, descriptor, Double.class);
		this.converter = new DoubleConverterForSelfDrawn(descriptor);
	}

	/**
	 * Creates a new DoubleSelfDrawnTextField object.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @param descriptor
	 *            the descriptor
	 */
	public SelfDrawnDoubleTextField(String id, IModel<Double> model, NumberBoPropertyDescriptor<Double> descriptor) {
		super(id, model, descriptor, Double.class);
		this.converter = new DoubleConverterForSelfDrawn(descriptor);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <C> IConverter<C> getConverter(Class<C> type) {
		return (IConverter<C>) converter;
	}
}