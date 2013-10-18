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

import gr.interamerican.bo2.arch.ext.TypedSelectable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the properties of a class that will be used for its
 * implementation of the {@link TypedSelectable} interface.
 * 
 * The properties must be specified in the following order:
 * Type, Subtype, Code, Name.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TranslatableProperties {
	
	/**
	 * Defines the properties of this object that constitute 
	 * the translation aresource id.
	 * 
	 * The properties are defined separated with commas.
	 * 
	 * @return Returns the properties that constitute the 
	 *         translation resource id.
	 */
	public String value();

}
