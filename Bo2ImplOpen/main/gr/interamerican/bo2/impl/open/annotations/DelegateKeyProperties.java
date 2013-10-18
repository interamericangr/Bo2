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

import gr.interamerican.bo2.arch.PersistentObject;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a {@link PersistentObject} class for having delegate methods
 * to some properties of its key.
 * 
 * If the value attribute of this annotation is empty or null,
 * then all properties of the type's key will be exposed
 * to its interface via delegates.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DelegateKeyProperties {
	
	
	/**
	 * Defines the properties of this object that constitute the key.
	 * 
	 * The properties are defined separated with commas.
	 * 
	 * @return Returns the properties that constitute the key.
	 */	
	public String[] value();

}
