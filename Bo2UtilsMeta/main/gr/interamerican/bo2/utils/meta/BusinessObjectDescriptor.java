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

import gr.interamerican.bo2.utils.attributes.Labeled;
import gr.interamerican.bo2.utils.attributes.Named;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.exceptions.MultipleValidationsException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Descriptor of a business object.
 * 
 * @param <T> Type of business object.
 */
public interface BusinessObjectDescriptor<T> 
extends Named, Labeled, Serializable {

	/**
	 * Gets the propertyDescriptors.
	 *
	 * @return Returns the propertyDescriptors
	 */
	public List<BoPropertyDescriptor<?>> getPropertyDescriptors();

	/**
	 * Assigns a new value to the propertyDescriptors.
	 *
	 * @param propertyDescriptors the propertyDescriptors to set
	 */
	public void setPropertyDescriptors(List<BoPropertyDescriptor<?>> propertyDescriptors);
	
	/**
	 * Gets the values of the business properties from an object.
	 * 
	 * @param object
	 * 
	 * @return Returns a Map that associates the beans' BoPropertyDescriptors
	 *         with the values of their properties. 
	 */
	public Map<BoPropertyDescriptor<?>, Object> get(T object);
	
	/**
	 * Sets the values of the business properties from an object.
	 * 
	 * @param object 
	 *        Object on which the values are set.
	 *        
	 * @param values 
	 *        Map associating {@link BoPropertyDescriptor} objects with the
	 *        values of the properties they describe.
	 *       
	 * 
	 * @throws MultipleValidationsException 
	 */
	public void set(T object, Map<BoPropertyDescriptor<?>, Object> values)
	throws MultipleValidationsException;
	
	/**
	 * Validates the bean's values against this descriptor.
	 * 
	 * @param object bean to validate.
	 * 
	 * @throws MultipleValidationsException
	 */
	public void validate(T object) throws MultipleValidationsException;
		
	/**
	 * @param expressions
	 *        Expressions against which a T can be validated.
	 */
	public void setExpressions(Set<BusinessObjectValidationExpression> expressions);
	
	/**
	 * @return Returns the expressions against which a T can be validated.
	 */
	public Set<BusinessObjectValidationExpression> getExpressions();
	
	/**
	 * Returns the BoPropertyDescriptor with the specified name.
	 * 
	 * @param descriptorName
	 * @return Returns the BoPropertyDescriptor with the specified name.
	 */
	public BoPropertyDescriptor<?> getDescriptorByName(String descriptorName);
	
	/**
	 * Asks this {@link BusinessObjectDescriptor} which of its property descriptors
	 * affects the argument. If the argument is not contained in {@link #getPropertyDescriptors()}
	 * a RuntimeExcetion is thrown.
	 * 
	 * @param affected
	 *        Affected 
	 * @return The affecting BoPropertyDescriptor
	 */
	public BoPropertyDescriptor<?> whoAffectsMe(BoPropertyDescriptor<?> affected);
	
}
