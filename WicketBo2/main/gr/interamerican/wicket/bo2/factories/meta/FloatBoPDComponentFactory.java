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

import gr.interamerican.bo2.utils.meta.descriptors.FloatBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnFloatTextField;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * Factory for {@link SelfDrawnFloatTextField}.
 */
public class FloatBoPDComponentFactory 
extends AbstractBoPDComponentFactory<FloatBoPropertyDescriptor> {	
	
	public Component drawMain(FloatBoPropertyDescriptor descriptor,String wicketId) {
		return new SelfDrawnFloatTextField(wicketId, descriptor);
	}
	
	@SuppressWarnings("unchecked")
	public Component drawMain(String wicketId, IModel<?> model, FloatBoPropertyDescriptor descriptor) {
		return new SelfDrawnFloatTextField(wicketId,(IModel<Float>) model, descriptor);
	}

}
