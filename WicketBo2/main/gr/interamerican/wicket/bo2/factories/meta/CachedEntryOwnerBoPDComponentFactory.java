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

import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryOwnerBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnDropDownChoiceForEntryOwner;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * Factory for {@link SelfDrawnDropDownChoiceForEntryOwner}.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CachedEntryOwnerBoPDComponentFactory 
extends AbstractBoPDComponentFactory<CachedEntryOwnerBoPropertyDescriptor<?,?>> {
	
public Component drawMain(CachedEntryOwnerBoPropertyDescriptor<?,?> descriptor,String wicketId) {
		List<?> choices = new ArrayList(descriptor.getValues());
		return new SelfDrawnDropDownChoiceForEntryOwner(wicketId, descriptor, choices, Bo2WicketSession.get());
	}	
	
	public Component drawMain(String wicketId, IModel<?> model, CachedEntryOwnerBoPropertyDescriptor<?,?> descriptor) {
		List<?> choices = new ArrayList(descriptor.getValues());
		return new SelfDrawnDropDownChoiceForEntryOwner(wicketId, model, descriptor, choices, Bo2WicketSession.get());
	}

}
