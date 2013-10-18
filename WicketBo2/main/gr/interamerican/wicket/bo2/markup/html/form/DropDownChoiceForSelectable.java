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

import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

/**
 * A {@link DropDownChoice} that shows a list of {@link Selectable} objects.
 */
public class DropDownChoiceForSelectable extends DropDownChoice<Selectable<?>> {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new DropDownChoiceForSelectable object. 
	 * 
	 * @param id 
	 * @param model 
	 * @param choices 
	 */
	public DropDownChoiceForSelectable(String id, IModel<Selectable<?>> model, List<? extends Selectable<?>> choices) {
		super(id, model, choices, new ChoiceRendererForSelectable());
		setNullValid(true);
	}
	
	/**
	 * Creates a new DropDownChoiceForSelectable object. 
	 * 
	 * @param id 
	 * @param choices 
	 */
	public DropDownChoiceForSelectable(String id, List<? extends Selectable<?>> choices) {
		super(id, choices, new ChoiceRendererForSelectable());
		setNullValid(true);
	}

}
