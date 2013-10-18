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

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.comparators.TypedSelectableComparator;
import gr.interamerican.bo2.utils.meta.ext.descriptors.PalleteCachedEntriesBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnPalleteForEntries;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

/**
 * BoPDComponentFactory for PalleteCachedEntryBoPropertyDescriptor.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PalleteCachedEntriesForBoPDComponentFactory 
extends AbstractBoPDComponentFactory<PalleteCachedEntriesBoPropertyDescriptor<?,?>> {
	
	public Component drawMain(PalleteCachedEntriesBoPropertyDescriptor<?,?> descriptor, String wicketId) {
		List<?> choices = new ArrayList<TypedSelectable<?>>(descriptor.getSelectableValues());
		
		List<TypedSelectable<Long>> choicesList = new ArrayList<TypedSelectable<Long>>((List<TypedSelectable<Long>>)choices);
		Collections.sort(choicesList, new TypedSelectableComparator());
		ListModel<TypedSelectable<Long>> choicesModel = new ListModel<TypedSelectable<Long>>(choicesList);
			
		return new SelfDrawnPalleteForEntries(wicketId, descriptor, choicesModel, Bo2WicketSession.get());
	}	

	public Component drawMain(String wicketId, IModel<?> model, PalleteCachedEntriesBoPropertyDescriptor<?,?> descriptor) {
		List<?> choices = new ArrayList<TypedSelectable<?>>(descriptor.getSelectableValues());

		List<TypedSelectable<Long>> choicesList = new ArrayList<TypedSelectable<Long>>((List<TypedSelectable<Long>>)choices);
		Collections.sort(choicesList, new TypedSelectableComparator());
		ListModel<TypedSelectable<Long>> choicesModel = new ListModel<TypedSelectable<Long>>(choicesList);
		
		return new SelfDrawnPalleteForEntries(wicketId, model, descriptor, choicesModel, Bo2WicketSession.get());
	}

}
