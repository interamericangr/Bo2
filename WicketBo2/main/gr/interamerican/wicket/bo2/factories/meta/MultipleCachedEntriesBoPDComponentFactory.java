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
import gr.interamerican.bo2.utils.meta.ext.descriptors.MultipleCachedEntriesBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnMultipleChoiceForEntry;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * BoPDComponentFactory for MultipleChoiceCachedEntryBoPropertyDescriptor.
 */	
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MultipleCachedEntriesBoPDComponentFactory 
extends AbstractBoPDComponentFactory<MultipleCachedEntriesBoPropertyDescriptor<?,?>> {
	
	public Component drawMain(MultipleCachedEntriesBoPropertyDescriptor<?,?> descriptor, String wicketId) {
		List<?> choices = new ArrayList<TypedSelectable<?>>(descriptor.getSelectableValues());
		
		List<TypedSelectable<Long>> valuesList = new ArrayList<TypedSelectable<Long>>((List<TypedSelectable<Long>>)choices);
		Collections.sort(valuesList, new TypedSelectableComparator());
		
		return new SelfDrawnMultipleChoiceForEntry(wicketId, descriptor, valuesList, Bo2WicketSession.get());
	}	
	
	public Component drawMain(String wicketId, IModel<?> model, MultipleCachedEntriesBoPropertyDescriptor<?,?> descriptor) {
		List<?> choices = new ArrayList<TypedSelectable<?>>(descriptor.getSelectableValues());
		List<TypedSelectable<Long>> valuesList = new ArrayList<TypedSelectable<Long>>((List<TypedSelectable<Long>>)choices);
		Collections.sort(valuesList, new TypedSelectableComparator());
	
		return new SelfDrawnMultipleChoiceForEntry(wicketId, model, descriptor, valuesList, Bo2WicketSession.get());
	}
	
}
