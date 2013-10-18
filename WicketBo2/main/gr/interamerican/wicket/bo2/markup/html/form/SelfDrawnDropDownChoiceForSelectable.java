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

import gr.interamerican.bo2.arch.ext.Selectable;
import gr.interamerican.bo2.utils.meta.ext.descriptors.SelectableBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;
import gr.interamerican.wicket.utils.MarkupConstants;

import java.util.ArrayList;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.IModel;

/**
 * SelfDrawn DropDownChoiceForSelectable.
 */
public class SelfDrawnDropDownChoiceForSelectable extends DropDownChoiceForSelectable {
	
  	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new SelfDrawnDropDownChoiceForSelectable object. 
	 *
	 * @param id
	 * @param model
	 * @param descriptor
	 */
	public SelfDrawnDropDownChoiceForSelectable(String id, IModel<Selectable<?>> model, SelectableBoPropertyDescriptor<?> descriptor) {
		super(id, model, new ArrayList<Selectable<?>>(descriptor.getChoices()));
		SelfDrawnUtils.standardSelfDrawnFormComponentStuffUnchecked(this, descriptor);
	}

	@Override
    protected void onComponentTag(ComponentTag tag) {  
		tag.setName(MarkupConstants.SELECT);
		tag.put(MarkupConstants.STYLE, MarkupConstants.WIDTH);
		super.onComponentTag(tag);
	}

}
