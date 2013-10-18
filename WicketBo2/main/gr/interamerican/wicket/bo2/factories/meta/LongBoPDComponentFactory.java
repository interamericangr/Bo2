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

import gr.interamerican.bo2.utils.meta.descriptors.LongBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnLongTextField;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * Factory for {@link SelfDrawnLongTextField}.
 */
public class LongBoPDComponentFactory 
extends AbstractBoPDComponentFactory<LongBoPropertyDescriptor> {	
	
	public Component drawMain(LongBoPropertyDescriptor descriptor,String wicketId) {
		return new SelfDrawnLongTextField(wicketId, descriptor);
	}
	
	@SuppressWarnings("unchecked")
	public Component drawMain(String wicketId, IModel<?> model, LongBoPropertyDescriptor descriptor) {
		return new SelfDrawnLongTextField(wicketId,(IModel<Long>) model, descriptor);
	}

}
