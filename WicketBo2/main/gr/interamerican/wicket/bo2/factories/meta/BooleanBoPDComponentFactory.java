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

import gr.interamerican.bo2.utils.meta.descriptors.BooleanBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnCheckBox;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * Factory for {@link SelfDrawnCheckBox}.
 */
public class BooleanBoPDComponentFactory 
extends AbstractBoPDComponentFactory<BooleanBoPropertyDescriptor> {	
	
	public Component drawMain(BooleanBoPropertyDescriptor descriptor,String wicketId) {
		return new SelfDrawnCheckBox(wicketId, descriptor);
	}
	
	@SuppressWarnings("unchecked")
	public Component drawMain(String wicketId, IModel<?> model, BooleanBoPropertyDescriptor descriptor) {
		return new SelfDrawnCheckBox(wicketId, (IModel<Boolean>)model, descriptor);
	}

}
