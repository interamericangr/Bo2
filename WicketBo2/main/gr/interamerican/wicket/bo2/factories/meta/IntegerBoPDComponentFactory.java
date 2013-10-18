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

import gr.interamerican.bo2.utils.meta.descriptors.IntegerBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnIntegerTextField;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * Factory for {@link SelfDrawnIntegerTextField}.
 */
public class IntegerBoPDComponentFactory 
extends AbstractBoPDComponentFactory<IntegerBoPropertyDescriptor> {	
	
	public Component drawMain(IntegerBoPropertyDescriptor descriptor,String wicketId) {
		return new SelfDrawnIntegerTextField(wicketId, descriptor);
	}
	
	@SuppressWarnings("unchecked")
	public Component drawMain(String wicketId, IModel<?> model, IntegerBoPropertyDescriptor descriptor) {
		return new SelfDrawnIntegerTextField(wicketId, (IModel<Integer>) model, descriptor);
	}

}
