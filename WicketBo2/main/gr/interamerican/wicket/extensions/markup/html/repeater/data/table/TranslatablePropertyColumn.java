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
package gr.interamerican.wicket.extensions.markup.html.repeater.data.table;

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.ext.TranslatableEntryOwner;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;


/**
 * @param <T> 
 * 
 */
public class TranslatablePropertyColumn<T> extends PropertyColumn<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new TranslatablePropertyColumn object. 
	 *
	 * @param displayModel
	 * @param propertyExpression
	 */
	public TranslatablePropertyColumn(IModel<String> displayModel, String propertyExpression) {
		super(displayModel, propertyExpression);
	}
	
	/**
	 * Creates a new TranslatablePropertyColumn object. 
	 *
	 * @param displayModel
	 * @param sortProperty
	 * @param propertyExpression
	 */
	public TranslatablePropertyColumn(IModel<String> displayModel, String sortProperty, String propertyExpression) {
		super(displayModel, sortProperty, propertyExpression);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected IModel<?> createLabelModel(IModel<T> rowModel) {
		T object = rowModel.getObject();
		if(object instanceof TranslatableEntryOwner){	
			String label = StringConstants.EMPTY;
			TranslatableEntry translatableEntry = 
				((TranslatableEntryOwner)object).getEntry();
			Object languageId = 
				Bo2WicketSession.get().getLanguageId();
			if(translatableEntry!=null){
				label = translatableEntry.getTranslation(languageId);
			}
			return new Model<String>(label);	
		}else if(object instanceof TranslatableEntry){
			TranslatableEntry entry = (TranslatableEntry) object;
			Object languageId = 
				Bo2WicketSession.get().getLanguageId();
			String label = entry.getTranslation(languageId);
			return new Model<String>(label);
		}else{
			return super.createLabelModel(rowModel);	
		}	
	}
}
