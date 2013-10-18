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

import gr.interamerican.bo2.utils.meta.descriptors.DateBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;
import gr.interamerican.wicket.utils.MarkupConstants;

import java.util.Date;

import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Self-drawn DateTimeField.
 */
public class SelfDrawnDateTimeField extends DateTimeField {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new SelfDrawnDateField object. 
	 * 
	 * @param id 
	 * @param descriptor 
	 */
	public SelfDrawnDateTimeField(String id, DateBoPropertyDescriptor descriptor) {
		super(id, new Model<Date>());
		SelfDrawnUtils.<Date>standardSelfDrawnFormComponentStuff(this, descriptor);
	}
	/**
	 * Creates a new SelfDrawnDateField object. 
	 * 
	 * @param id 
	 * @param descriptor 
	 * @param model
	 */
	public SelfDrawnDateTimeField(String id, IModel<Date> model, DateBoPropertyDescriptor descriptor) {
		super(id, model);
		SelfDrawnUtils.<Date>standardSelfDrawnFormComponentStuff(this, descriptor);
	}
	
	@Override
    protected void onComponentTag(ComponentTag tag) {
		tag.setName(MarkupConstants.SPAN);
        super.onComponentTag(tag); 
     }

}
