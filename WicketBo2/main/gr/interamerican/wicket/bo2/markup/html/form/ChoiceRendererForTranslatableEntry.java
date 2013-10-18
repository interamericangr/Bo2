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

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.impl.open.po.ValuesBasedKey;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

/**
 * Custom choiceRenderer that contains for the displaying value the translated 
 * value and the code of the translatableEnrty object.
 * 
 */
public class ChoiceRendererForTranslatableEntry 
extends ChoiceRendererForEntry<Long, TranslatableEntry<Long,ValuesBasedKey,Long>> {

	/**
	 * serial.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new ChoiceRendererForEntryWithCode object.
	 * 
	 * @param session 
	 * 
	 */
	public ChoiceRendererForTranslatableEntry(Bo2WicketSession<?, Long> session) {
		super(session);
	}
	
	/**
	 * The value for displaying, is the "code - translated value" of TranslatableEntry object.
	 */
	@Override
	public Object getDisplayValue(TranslatableEntry<Long,ValuesBasedKey, Long> object) {
		return object == null ? null : object.getCode() + StringConstants.SPACE
														+ StringConstants.MINUS
														+ StringConstants.SPACE
														+ object.getTranslation(languageId);
	}

}
