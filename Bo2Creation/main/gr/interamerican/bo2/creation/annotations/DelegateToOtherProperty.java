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
package gr.interamerican.bo2.creation.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as delegating some of its properties to others.
 * 
 * The annotation defines the pairs of properties being delegated
 * the first to the second.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DelegateToOtherProperty {
	/**
	 * Pairs of properties
	 */
	public static final char SEPARATOR = '-';
	
	/**
	 * Defines the property pairs.
	 * 
	 * Each pair must contain first the property that is delegated
	 * and second the property that accepts the delegation. To properties
	 * in each pair must be separated by tge character -.
	 * 
	 * @return Returns the pairs of properties.
	 */	
	public String[] value();

}
