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
package gr.interamerican.bo2.utils.meta.expressions;

import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;

import java.util.Map;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

/**
 * Engine for the evaluation of String expressions.
 */
public class ExpressionEngine {
	
	/**
	 * Singleton.
	 */
	public static final ExpressionEngine INSTANCE = new ExpressionEngine();
	
	/**
	 * {@link JexlEngine} singleton to delegate to.
	 */
	private static final JexlEngine engine = new JexlEngine();

	/**
	 * Evaluates an expression.
	 * 
	 * @param expression
	 * @param context
	 * @param failureMessage 
	 * @throws ValidationException 
	 */
	@SuppressWarnings("nls")
	public void evaluate(String expression, Map<String, Object> context, String failureMessage)
	throws ValidationException {
		MapContext mc = new MapContext(context);
		Expression exp = engine.createExpression(expression);
		Object evaluation =  exp.evaluate(mc);
		if(!(evaluation instanceof Boolean) || evaluation == null) {
			throw new ValidationException("Failed to evaluate [" + expression + "].");
		}
		Boolean ok = (Boolean) evaluation;
		if(!ok) {
			throw new ValidationException(failureMessage);
		}
	}
	
	

}
