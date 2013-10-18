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
package gr.interamerican.bo2.odftoolkit.utils;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import ognl.Ognl;
import ognl.OgnlException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Evaluates OGNL expressions against a model object.
 */
public class ExpressionEvaluator {
	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExpressionEvaluator.class);
	
	/**
	 * Singleton instance.
	 */
	private static final ExpressionEvaluator INSTANCE = new ExpressionEvaluator();
	
	
	
	/**
	 * Creates a new OgnlUtils object.
	 */
	private ExpressionEvaluator() {
		super();
	}

	/**
	 * {@link Formatter} used by this class.
	 */
	private Formatter<Object> formatter = new ObjectFormatter();
	
	/**
	 * If this is true, then if an error occurs during the evaluation
	 * of an OGNL expression, the exception will not be thrown and the
	 * OGNL expression will be evaluated to the value of <code>errorIndicator</code>.
	 */
	private boolean tolerateErrors = false;
	
	/**
	 * String indicating that an error has occurred during the
	 * evaluation of an expression. This field is used only if 
	 * <code>tolerateErrors</code> is true.
	 */
	private String errorIndicator = StringConstants.EMPTY;

	/**
	 * String used instead of null, when an expression is evaluated
	 * to null. 
	 */
	private String nullIndicator = StringConstants.NULL;
	
	/**
	 * Gets the instance.
	 *
	 * @return Returns the instance
	 */
	public static ExpressionEvaluator getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Assigns a new value to the formatter.
	 *
	 * @param formatter the formatter to set
	 */
	public void setFormatter(Formatter<Object> formatter) {
		this.formatter = formatter;
	}
	
	/**
	 * Gets the formatter.
	 *
	 * @return Returns the formatter
	 */
	public Formatter<Object> getFormatter() {
		return formatter;
	}	
	
	/**
	 * Evaluates an OGNL expression on a specified model object.
	 * 
	 * If an exception occurs during the evaluation of the OGNL expression, 
	 * then the result of this method is specified by the 
	 * <code>tolerateErrors</code> flag set on this {@link ExpressionEvaluator}.
	 * If this flag is set to <code>true</code>, then the method will return
	 * the value specified in the <code>errorIndicator</code> property.
	 * Otherwise a RuntimeException will be thrown. <br/>
	 * If the OGNL expression evaluates to <code>null</code>, then the
	 * method returns the value specified by the <code>nullIndicator</code>
	 * property of this {@link ExpressionEvaluator}.
	 * 
	 * @param ognlExpression
	 *        Expression to evaluate. 
	 * @param model
	 *        Context object to evaluate the expression.
	 *         
	 * @return Returns the value evaluated by the OGNL expression.
	 * 
	 * @throws RuntimeException 
	 *         If an exception occurs during the evaluation of the OGNL
	 *         expression and the <code>tolerateErrors</code> property 
	 *         of this {@link ExpressionEvaluator} is set to false.
	 *   
	 */
	public String getValue(String ognlExpression, Object model) {		
		Object val = parseExpression(ognlExpression, model);
		return formatter.format(val);
	}
	
	/**
	 * Evaluates an OGNL expression on a specified model object.
	 * 
	 * @param ognlExpression
	 *        Expression to evaluate. 
	 * @param model
	 *        Context object to evaluate the expression.
	 *         
	 * @return Returns the value.
	 */
	@SuppressWarnings("nls")
	Object parseExpression(String ognlExpression, Object model) {		
		try {
			Object v = Ognl.getValue(ognlExpression, model);
			return v==null ? nullIndicator : v;
		} catch (OgnlException e) {			
			if (logger.isErrorEnabled()) {
				String msg = "Exception caused by expression "  
					+ ognlExpression + " : " + e.toString();
				logger.error(msg);
			}
			if (!tolerateErrors) {
				throw new RuntimeException(e);				
			} else {
				return errorIndicator;
			}
		}
		
		
	}
	
	

	/**
	 * Gets the tolerateOgnlErrors.
	 * 
	 * If this property is <code>true</code>, then if an error occurs 
	 * during the evaluation of an OGNL expression, the exception will 
	 * not be thrown and the OGNL expression will be evaluated to an 
	 * empty string.
	 *
	 * @return Returns the tolerateErrors
	 */
	public boolean isTolerateErrors() {
		return tolerateErrors;
	}

	/**
	 * Assigns a new value to the tolerateErrors.
	 *
	 * @param tolerateErrors the tolerateErrors to set
	 */
	public void setTolerateErrors(boolean tolerateErrors) {
		this.tolerateErrors = tolerateErrors;
	}

	/**
	 * Gets the errorIndicator.
	 * 
	 * If the <code>tolerateErrors</code> is set to <code>true</code>,
	 * then the errorIndicator is a string that is returned by the 
	 * <code>getValue(String,Object)</code> method, when an exception
	 * occurs during the evaluation of the OGNL expression.
	 *
	 * @return Returns the errorIndicator
	 * 
	 * @see #setTolerateErrors(boolean)
	 * @see  #getValue(String, Object)
	 */
	public String getErrorIndicator() {
		return errorIndicator;
	}

	/**
	 * Assigns a new value to the errorIndicator.
	 *
	 * @param errorIndicator the errorIndicator to set
	 */
	public void setErrorIndicator(String errorIndicator) {
		this.errorIndicator = errorIndicator;
	}

	/**
	 * Gets the nullIndicator.
	 *
	 * @return Returns the nullIndicator
	 */
	public String getNullIndicator() {
		return nullIndicator;
	}

	/**
	 * Assigns a new value to the nullIndicator.
	 *
	 * @param nullIndicator the nullIndicator to set
	 */
	public void setNullIndicator(String nullIndicator) {
		this.nullIndicator = nullIndicator;
	}	
	
}
