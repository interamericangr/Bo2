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

import gr.interamerican.bo2.arch.ext.Translatable;
import gr.interamerican.bo2.arch.ext.TypedSelectable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the properties that are used for the creation of
 * a resource id that is necessary for the implementation
 * of the {@link Translatable} interface.
 * 
 * If the class already has any of the properties required
 * to implement {@link TypedSelectable}, then the property
 * specified by this annotation will be ignored. The subtype
 * attribute can be assigned the value null. This results
 * in a TypedSelectable with no subtype. 
 * <br/>
 * For example the class SomeClass has the following properties:
 * id, name, type, startDate, operationId, description.
 * If this class is marked with the following annotation: 
 * <br/>
 * <code>
 * @TypedSelectableProperties
 * (type="X",subtype=null,code="elementId",name="description")
 * </code>
 * <br/>
 * The attributes type and name will be ignored.
 * The property code will be added to the class, delegated to the
 * elementId property.
 * The subtype property will be added to the class, but it will
 * always return null.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TypedSelectableProperties {
	
	/**
	 * Defines the property used for Type.
	 * 
	 * @return Returns the property used for type.
	 */
	public String type();
	
	/**
	 * Defines the property used for Sub-Type.
	 * 
	 * @return Returns the property used for Sub-Type.
	 */
	public String subtype();
	
	/**
	 * Defines the property used for Code.
	 * 
	 * @return Returns the property used for Code.
	 */
	public String code();
	
	/**
	 * Defines the property used for Name.
	 * 
	 * @return Returns the property used for Name.
	 */
	public String name();

}
