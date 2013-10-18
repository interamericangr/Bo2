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
package gr.interamerican.bo2.arch.utils;

import gr.interamerican.bo2.arch.ext.Codified;
import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.NumberConverter;

import java.beans.PropertyDescriptor;

/**
 * Reflection utilities aware of Bo2Architecture types.
 */
public class Bo2ArchReflectionUtils {
	
	/**
	 * Hidden constructor of a utility class.
	 */
	private Bo2ArchReflectionUtils() {/* empty */}
	
	/**
	 * For a property that is common in two instances, this method will
	 * copy the source's property value to the target's property, as long
	 * as this is possible.
	 * In a situation where there is a {@link Codified} source property 
	 * with a non-Codified target property this method attempts to assign 
	 * the source property value code to the target property.
	 * 
	 * @param source 
	 *        Object who's properties will be copied to the target object.	         
	 * @param target
	 *        Object to which the properties will be copied.
	 * @param property
	 *        Property name.
	 */
	@SuppressWarnings("unchecked")
	public static void copyPropertyHandlingCodifieds(Object source, Object target, String property) {
		if(source==null || target==null || StringUtils.isNullOrBlank(property)) {
			return;
		}
		PropertyDescriptor sourcePd = JavaBeanUtils.getPropertyDescriptor(source.getClass(), property);
		PropertyDescriptor targetPd = JavaBeanUtils.getPropertyDescriptor(target.getClass(), property);
		if(sourcePd==null || targetPd==null) {
			return;
		}
		boolean sourcePdTypeIsCodified = Codified.class.isAssignableFrom(sourcePd.getPropertyType());
		boolean targetPdTypeIsCodified = Codified.class.isAssignableFrom(targetPd.getPropertyType());
		if(sourcePdTypeIsCodified && !targetPdTypeIsCodified) {
			Object codifiedValue = JavaBeanUtils.getProperty(sourcePd, source);
			if(codifiedValue==null) {
				return;
			}
			Object code = ((Codified<?>) codifiedValue).getCode();
			if(code==null) {
				return;
			}
			/*
			 * Special treatment for Number codes...
			 */
			if(code instanceof Number && Number.class.isAssignableFrom(targetPd.getPropertyType())) {
				code = new NumberConverter((Class<? extends Number>) targetPd.getPropertyType()).execute((Number) code);
			}
			JavaBeanUtils.setProperty(targetPd, code, target);
		} else {
			JavaBeanUtils.copyProperty(source, target, sourcePd, targetPd);
		}
	}

}
