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


import gr.interamerican.bo2.arch.ext.Selectable;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

/**
 * {@link IChoiceRenderer} for {@link Selectable} objects.
 */
class ChoiceRendererForSelectable 
implements IChoiceRenderer<Selectable<?>> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	public Object getDisplayValue(Selectable<?> object) {
		return object==null ? null : object.getName(); 
	}

	public String getIdValue(Selectable<?> object, int index) {
		if(object == null || object.getCode() == null) {
			return null;
		}
		return object.getCode().toString();
	}
	
}
