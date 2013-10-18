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
package gr.interamerican.wicket.bo2.descriptors;

import gr.interamerican.bo2.arch.ext.Translator;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.meta.BusinessObjectValidationExpression;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

/**
 * Implementation of {@link BusinessObjectValidationExpression} that
 * provides translatable messages.
 * 
 * @param <R>
 *        Type of translation resource id.
 * @param <L>
 *        Type of language id.
 */
public class TranslatableBusinessObjectValidationExpression<R, L> 
implements BusinessObjectValidationExpression {
	
	/**
	 * Descriptor.
	 */
	BusinessObjectValidationExpression expression;
	/**
	 * translator.
	 */
	Translator<R, L> translator;
	/**
	 * translation resource id.
	 */
	R resourceId;
	
	/**
	 * Creates a new TranslatableBusinessObjectEvaluationExpression object. 
	 * @param expression 
	 * @param resourceId 
	 * @param translator 
	 */
	public TranslatableBusinessObjectValidationExpression(
			BusinessObjectValidationExpression expression,
			R resourceId,
			Translator<R, L> translator) {
		this.expression = expression;
		this.resourceId = resourceId;
		this.translator = translator;
	}
	
	public String getExpression() {
		return expression.getExpression();
	}
	
	public String getMessage() {
		/*
		 * The unchecked conversion assumes that the Translator 
		 * and Bo2WicketSession languageId are of the same type.
		 */
		@SuppressWarnings("unchecked")
		L languageId = (L) Bo2WicketSession.get().getLanguageId();
		String message = translator.translate(resourceId, languageId);
		message = Utils.notNull(message, expression.getMessage());
		return message;
	}
	
	public void setExpression(String expression) {
		notAllowed();
	}
	
	public void setMessage(String message) {
		notAllowed();
	}
	
	/**
	 * Not allowed.
	 */
	private void notAllowed() {
		throw new UnsupportedOperationException("Not allowed"); //$NON-NLS-1$
	}

}
