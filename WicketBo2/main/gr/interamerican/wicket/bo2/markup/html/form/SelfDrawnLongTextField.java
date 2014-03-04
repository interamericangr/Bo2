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

/**
 * Self-drawn Long TextField.
 */
public class SelfDrawnLongTextField extends AbstractSelfDrawnNumberTextField<Long>{

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new LongSelfDrawnTextField object. 
	 *
	 * @param id
	 * @param descriptor
	 */
	public SelfDrawnLongTextField(String id, NumberBoPropertyDescriptor<Long> descriptor) {
		super(id, descriptor, Long.class);
	}
	
	/**
     * Creates a new LongSelfDrawnTextField object.
     *
     * @param id
     * @param descriptor
     * @param model
     */
    public SelfDrawnLongTextField(String id, IModel<Long> model, NumberBoPropertyDescriptor<Long> descriptor) {
        super(id, model, descriptor, Long.class);
    }
    
    @Override
    protected AbstractDecimalConverter<Long> getNumberCoverter() {
    	return null;
    }
    
}
