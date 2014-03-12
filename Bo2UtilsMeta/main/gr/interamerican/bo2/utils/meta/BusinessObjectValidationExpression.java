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
package gr.interamerican.bo2.utils.meta;

import java.io.Serializable;

/**
 * A {@link BusinessObjectValidationExpression} contains an expression
 * that can be evaluated against a business object instance.
 */
public interface BusinessObjectValidationExpression extends Serializable {
	
	/**
	 * Gets the expression.
	 *
	 * @return Returns the expression
	 */
	public String getExpression();

	/**
	 * Assigns a new value to the expression.
	 *
	 * @param expression the expression to set
	 */
	public void setExpression(String expression);

	/**
	 * Gets the message.
	 *
	 * @return Returns the message
	 */
	public String getMessage();

	/**
	 * Assigns a new value to the message.
	 *
	 * @param message the message to set
	 */
	public void setMessage(String message);

}
