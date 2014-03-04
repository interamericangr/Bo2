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

import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnStringTextField;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnTextArea;
import gr.interamerican.wicket.utils.MarkupConstants;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * Factory for {@link SelfDrawnStringTextField} and {@link SelfDrawnTextArea}.
 */
public class StringBoPDComponentFactory extends AbstractBoPDComponentFactory<StringBoPropertyDescriptor> {

	public Component drawMain(StringBoPropertyDescriptor descriptor, String wicketId) {
		int maxLength = descriptor.getMaxLength();
		if (maxLength > MarkupConstants.COLS_VALUE) {
			return new SelfDrawnTextArea(wicketId, descriptor);
		}
		return new SelfDrawnStringTextField(wicketId, descriptor);
	}

	@SuppressWarnings("unchecked")
	public Component drawMain(String cmpWicketId, IModel<?> model, StringBoPropertyDescriptor descriptor) {
		int maxLength = descriptor.getMaxLength();
		if (maxLength > MarkupConstants.COLS_VALUE) {
			return new SelfDrawnTextArea(cmpWicketId, (IModel<String>) model, descriptor);
		}
		return new SelfDrawnStringTextField(cmpWicketId, (IModel<String>) model, descriptor);
	}

}
