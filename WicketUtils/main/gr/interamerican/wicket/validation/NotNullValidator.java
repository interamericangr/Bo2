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
package gr.interamerican.wicket.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;

/**
 * An {@link AbstractFormValidator} that takes as input many
 * {@link FormComponent}s and verifies that if one of them is not null, then all
 * the other are not null as well.
 */
public class NotNullValidator extends AbstractFormValidator {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 51351L;

	/**
	 * {@link FormComponent}s to check.
	 */
	private FormComponent<?>[] components;

	/**
	 * Public Constructor
	 * 
	 * @param components
	 *            {@link FormComponent}s to check.
	 */
	public NotNullValidator(FormComponent<?>... components) {
		this.components = components;
	}

	@Override
	public FormComponent<?>[] getDependentFormComponents() {
		return components;
	}

	@Override
	public void validate(Form<?> form) {
		List<FormComponent<?>> empty = new ArrayList<>();
		List<FormComponent<?>> notEmpty = new ArrayList<>();
		for (FormComponent<?> component : components) {
			if (component.getConvertedInput() == null) {
				empty.add(component);
			} else {
				notEmpty.add(component);
			}
		}
		if (!notEmpty.isEmpty() && !empty.isEmpty()) {
			error(empty.get(0));
		}
	}
}