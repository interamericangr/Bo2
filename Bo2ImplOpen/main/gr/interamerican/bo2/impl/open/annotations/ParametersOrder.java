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
package gr.interamerican.bo2.impl.open.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the order of a workers parameters.
 * 
 * This annotation is used in order to transformed a map with named
 * parameters to an array of objects that has these parameters in
 * the correct order. 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParametersOrder {
	
	/**
	 * Defines an order of parameter names, separated by comma.
	 * 
	 * The same name can be put more than once, resulting in having the
	 * same object more than once in the parameters array.
	 * 
	 * @return Returns a string that consists of the parameter names 
	 *         separated by commas.
	 */
	public String[] value();

}
