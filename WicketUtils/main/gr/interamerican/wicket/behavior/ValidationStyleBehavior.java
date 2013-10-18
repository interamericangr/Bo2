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
package gr.interamerican.wicket.behavior;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.wicket.utils.MarkupConstants;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;

/**
 * This behavior adds a CSS class on the class attribute
 * of the HTML element of a Component for which validation
 * failed. The CSS class that marks the validation failure
 * is added before any other classes already present.
 */
public class ValidationStyleBehavior extends AbstractBehavior {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Singleton.
	 */
	public static final ValidationStyleBehavior INSTANCE = new ValidationStyleBehavior();

	@Override
	public void onComponentTag(final Component component, final ComponentTag tag) {
		FormComponent<?> comp = (FormComponent<?>) component;   
		if (!comp.isValid()) {
			String value = MarkupConstants.INVALID;
			Object classAttribute = tag.getAttributes().get(MarkupConstants.CSS_CLASS); 
			if(classAttribute!=null) {
				value += StringConstants.SPACE + StringUtils.toString(classAttribute, StringConstants.EMPTY);
			}
			tag.getAttributes().put(MarkupConstants.CSS_CLASS, value);
		}
	}
	
	/**
	 * Use the Singleton.
	 */
	private ValidationStyleBehavior() { /* private, empty */ }
	
}
