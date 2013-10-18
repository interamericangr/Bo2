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
package gr.interamerican.wicket.bo2.markup.html.form;

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.StringConstants;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

/**
 * Custom ChoiceRenderer for non translatable Entry.
 * 
 * @param <T> 
 * 		  Type of typeSelectable object.
 * 		  
 */
public class ChoiceRendererForNonTranslatableEntry<T extends TypedSelectable<Long>>
implements IChoiceRenderer<T>{

	/**
	 * serial.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new ChoiceRendererForNonTranslatableEntry object.
	 */
	public ChoiceRendererForNonTranslatableEntry() {
		super();
	}
	
	/**
	 * Returns the id value of typeSelectable object.
	 */
	public String getIdValue(T typeSelectable, int index) {
		if (typeSelectable == null || typeSelectable.getCode() == null) {
			return null;
		}
		return typeSelectable.getCode().toString();
	}
	/**
	 * The value for displaying, is the "code - name" of typeSelectable object.
	 */
	public Object getDisplayValue(T typeSelectable) {
		return typeSelectable == null ? null : typeSelectable.getCode() 
											  + StringConstants.SPACE
											  + StringConstants.MINUS 
											  + StringConstants.SPACE
											  + typeSelectable.getName();
	}

}
