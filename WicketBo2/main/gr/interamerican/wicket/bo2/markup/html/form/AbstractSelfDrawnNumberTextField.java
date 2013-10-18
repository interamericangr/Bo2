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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converters.AbstractDecimalConverter;

/**
 * Base class for self-drawn number TextField components.
 * 
 * @param <T>
 * 		  Type of model object.
 */
public abstract class AbstractSelfDrawnNumberTextField<T extends Number> 
extends AbstractSelfDrawnTextField<T> {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new AbstractSelfDrawnNumberTextField object. 
	 *
	 * @param id
	 * @param descriptor
	 */
	public AbstractSelfDrawnNumberTextField(String id, NumberBoPropertyDescriptor<T> descriptor) {
		super(id, descriptor);
	}
	
	/**
     * Creates a new AbstractSelfDrawnNumberTextField object.
     *
     * @param id
     * @param descriptor
     * @param model
     */
    public AbstractSelfDrawnNumberTextField(String id, IModel<T> model, NumberBoPropertyDescriptor<T> descriptor) {
        super(id, model, descriptor);
    }
    
	@Override
	public IConverter getConverter(final Class<?> type) {
		if(type!=getModelObjectClass()) {
			return super.getConverter(type);
		}
		AbstractDecimalConverter result = getNumberCoverter();
		if(result == null) {
			return super.getConverter(type);
		}
		NumberFormat nf = new DecimalFormat();
		int maxFractionDigits = ((NumberBoPropertyDescriptor<T>)descriptor).getLengthOfDecimalPart();
		nf.setMaximumFractionDigits(maxFractionDigits+1);
		result.setNumberFormat(getLocale(), nf);
		return result;
	}
	
	/**
	 * @return Returns a number converter. Applicable for {@link Number} sub-types
	 *         for which decimal digits make sense. For the rest, this MUST return
	 *         null.
	 */
	protected abstract AbstractDecimalConverter getNumberCoverter();
	
	/**
	 * @return Returns the model object class. This should conform to Class<T>
	 */
	protected abstract Class<T> getModelObjectClass(); 

}
