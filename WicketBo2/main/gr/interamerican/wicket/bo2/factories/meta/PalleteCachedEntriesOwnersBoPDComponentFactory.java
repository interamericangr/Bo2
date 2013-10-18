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

import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.utils.meta.ext.descriptors.PalleteCachedEntriesOwnersBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnPalleteForEntriesOwners;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

/**
 * BoPDComponentFactory for PalleteCachedEntriesOwnersBoPropertyDescriptor.
 */
public class PalleteCachedEntriesOwnersBoPDComponentFactory 
extends AbstractBoPDComponentFactory<PalleteCachedEntriesOwnersBoPropertyDescriptor<?,?>> {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Component drawMain(PalleteCachedEntriesOwnersBoPropertyDescriptor<?,?> descriptor, String wicketId) {
		List<?> choices = new ArrayList<TranslatableEntryOwner<?, ?, ?>>(descriptor.getSelectableValues());
		
		List<TranslatableEntryOwner<Long,?,?>> choicesList = 
			new ArrayList<TranslatableEntryOwner<Long,?,?>>((List<TranslatableEntryOwner<Long,?,?>>)choices);
		ListModel<TranslatableEntryOwner<Long,?,?>> choicesModel = new ListModel<TranslatableEntryOwner<Long,?,?>>(choicesList);

		SelfDrawnPalleteForEntriesOwners<?,?> palleteForEntriesOwners = 
			new SelfDrawnPalleteForEntriesOwners(wicketId, descriptor, choicesModel, Bo2WicketSession.get());
		//TODO:Sort the Entries of Object Owners. SelfDrawnUtils.sortPairByLeftValue()
	
		return palleteForEntriesOwners;
	}	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Component drawMain(String wicketId, IModel<?> model, PalleteCachedEntriesOwnersBoPropertyDescriptor<?,?> descriptor) {
		List<?> choices = new ArrayList<TranslatableEntryOwner<?, ?, ?>>(descriptor.getSelectableValues());
		List<TranslatableEntryOwner<Long,?,?>> choicesList = new ArrayList<TranslatableEntryOwner<Long,?,?>>((List<TranslatableEntryOwner<Long,?,?>>)choices);
		ListModel<TranslatableEntryOwner<Long,?,?>> choicesModel = new ListModel<TranslatableEntryOwner<Long,?,?>>(choicesList);
		
		return new SelfDrawnPalleteForEntriesOwners(wicketId, model, descriptor, choicesModel, Bo2WicketSession.get());
	}

}
