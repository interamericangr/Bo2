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
package gr.interamerican.wicket.modifiers;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.Model;


/**
 * The JavascriptEventConfirmation class.
 * 
 * This {@link AttributeModifier} adds a confirmation message.
 * The event on which the confirmation message is applied is 
 * specified in the constructor.   
 */
public class JavascriptEventConfirmation extends AttributeModifier {
	
	/**
	 * serialize.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Creates a new JavascriptEventConfirmation object. 
	 *
	 * @param event 
	 *        The event on which the confirmation message is added. 
	 * @param msg  
	 *        The confirmation message.
	 */
	public JavascriptEventConfirmation(String event, String msg) {
		super(event, true, new Model<String>(msg));
	}

	@Override
	protected String newValue(final String currentValue, final String replacementValue) {		
		@SuppressWarnings("nls")
		String script = 
			"var conf = confirm('" + replacementValue + "'); " + 
			"if (!conf) return false; ";  
		String current = Utils.notNull(currentValue, StringConstants.EMPTY);
		return script + current;
	}
}
