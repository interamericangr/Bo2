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

import gr.interamerican.bo2.utils.meta.descriptors.BooleanBoPropertyDescriptor;
import gr.interamerican.wicket.utils.MarkupConstants;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Self-drawn CheckBox.
 */
public class SelfDrawnCheckBox extends CheckBox {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new SelfDrawnCheckBox object.
	 * 
	 * @param id
	 * @param descriptor
	 */
	public SelfDrawnCheckBox(String id, BooleanBoPropertyDescriptor descriptor) {
		super(id, new Model<Boolean>());
		postConstruct(descriptor);
	}

	/**
	 * Creates a new SelfDrawnCheckBox object.
	 * 
	 * @param id
	 * @param descriptor
	 * @param model
	 */
	public SelfDrawnCheckBox(String id, IModel<Boolean> model, BooleanBoPropertyDescriptor descriptor) {
		super(id, model);
		postConstruct(descriptor);
	}

	/**
	 * Stuff performed after super() on all constructors.
	 * 
	 * @param descriptor
	 */
	private void postConstruct(BooleanBoPropertyDescriptor descriptor) {
		if (descriptor.isHasDefault() && getDefaultModelObject()==null) {
			this.setDefaultModelObject(descriptor.getDefaultValue());
		}
		this.setEnabled(!descriptor.isReadOnly());
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		tag.setName(MarkupConstants.INPUT);
		tag.put(MarkupConstants.TYPE, MarkupConstants.CHECKBOX);
		super.onComponentTag(tag);
	}
	
}
