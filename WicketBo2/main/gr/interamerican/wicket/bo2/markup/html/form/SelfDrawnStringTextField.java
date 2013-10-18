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

import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;

import org.apache.wicket.model.IModel;

/**
 * Self-drawn String TextField.
 */
public class SelfDrawnStringTextField extends AbstractSelfDrawnTextField<String> {
   
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
       
    /**
     * Creates a new SelfDrawnStringTextField object.
     *
     * @param id
     * @param descriptor
     */
    public SelfDrawnStringTextField(String id, StringBoPropertyDescriptor descriptor) {
        super(id, descriptor);
        SelfDrawnUtils.<String>standardSelfDrawnFormComponentStuff(this, descriptor);
    }
   
    /**
     * Creates a new SelfDrawnStringTextField object.
     * 
     * @param id
     * @param model 
     * @param descriptor
     */
    public SelfDrawnStringTextField(String id, IModel<String> model, StringBoPropertyDescriptor descriptor) {
        super(id, model, descriptor);
        SelfDrawnUtils.<String>standardSelfDrawnFormComponentStuff(this, descriptor);
    }

}
