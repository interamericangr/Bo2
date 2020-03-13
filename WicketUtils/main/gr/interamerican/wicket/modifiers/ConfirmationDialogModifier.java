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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.Link;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.Utils;

/**
 * An {@link AttributeModifier} that adds a confirmation dialog. The event on
 * which the confirmation message is applied is specified in the
 * constructor.<br>
 * Usually that event is 'onclick'.<br>
 * Do note that this utility is only valid for NON ajax based {@link Component}s
 * (i.e. {@link Link} , {@link Button} , etc... )
 */
public class ConfirmationDialogModifier extends AttributeModifier {
	
	/**
	 * serialize.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Public Constructor. 
	 *
	 * @param event 
	 *        The event on which the confirmation message is added. 
	 * @param msg  
	 *        The confirmation message.
	 */
	public ConfirmationDialogModifier(String event, String msg) {
		super(event, msg);
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