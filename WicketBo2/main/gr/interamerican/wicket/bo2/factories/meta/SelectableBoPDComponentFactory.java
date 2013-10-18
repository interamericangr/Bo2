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
package gr.interamerican.wicket.bo2.factories.meta;

import gr.interamerican.bo2.arch.ext.Selectable;
import gr.interamerican.bo2.utils.meta.ext.descriptors.SelectableBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnDropDownChoiceForSelectable;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * BoPDComponentFactory for SelectableBoPropertyDescriptor<?>.
 */
@SuppressWarnings("unchecked")
public class SelectableBoPDComponentFactory extends 
AbstractBoPDComponentFactory<SelectableBoPropertyDescriptor<?>>{

	public Component drawMain(SelectableBoPropertyDescriptor<?> descriptor, String wicketId) {
		return new SelfDrawnDropDownChoiceForSelectable(wicketId, new Model<Selectable<?>>(), descriptor);
	}

	public Component drawMain(String cmpWicketId, IModel<?> model, SelectableBoPropertyDescriptor<?> descriptor) {		
		return new SelfDrawnDropDownChoiceForSelectable(cmpWicketId, (IModel<Selectable<?>>) model, descriptor);
	}
	
}
