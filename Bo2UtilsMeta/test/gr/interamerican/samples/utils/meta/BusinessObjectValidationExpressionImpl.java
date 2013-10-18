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
package gr.interamerican.samples.utils.meta;

import gr.interamerican.bo2.utils.meta.BusinessObjectValidationExpression;

/**
 * Sample implementation of {@link BusinessObjectValidationExpression}.
 */
public class BusinessObjectValidationExpressionImpl
implements BusinessObjectValidationExpression {
	
	/**
	 * expression.
	 */
	private String expression;
	
	/**
	 * message.
	 */
	private String message;

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
