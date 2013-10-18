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

import gr.interamerican.bo2.utils.meta.descriptors.SelectionBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;
import gr.interamerican.wicket.utils.MarkupConstants;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * SelfDrawn DropDownChoice.
 * 
 * @param <T> 
 */
public class SelfDrawnSelectionDropDownChoice<T extends Serializable> extends DropDownChoice<T>{
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
			
	/**
	 * Creates a new SelfDrawnDropDownChoice object. 
	 * @param id 
	 * @param descriptor 
	 */
	public SelfDrawnSelectionDropDownChoice(String id, SelectionBoPropertyDescriptor<T> descriptor) {
		super(id, new Model<T>(), new ArrayList<T>(descriptor.getValues()));
		SelfDrawnUtils.<T>standardSelfDrawnFormComponentStuff(this, descriptor);
	}
	
	/**
     * Creates a new SelfDrawnDropDownChoice object.
     *	
     * @param id
     * @param descriptor
     * @param model
     */
    public SelfDrawnSelectionDropDownChoice(String id, IModel<T> model, SelectionBoPropertyDescriptor<T> descriptor) {
        super(id, model, new ArrayList<T>(descriptor.getValues()));
        SelfDrawnUtils.<T>standardSelfDrawnFormComponentStuff(this, descriptor);
    }

	@Override
    protected void onComponentTag(ComponentTag tag) {  
		tag.setName(MarkupConstants.SELECT);
		 tag.put(MarkupConstants.STYLE, MarkupConstants.WIDTH);
		super.onComponentTag(tag);
	}
	
}
