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
package gr.interamerican.bo2.arch.utils.copiers;

import gr.interamerican.bo2.arch.ext.Copier;
import gr.interamerican.bo2.utils.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.util.Date;

/**
 * 
 */
public class DateCopier implements Copier<Date> {

	public Date copy(Date objectToCopy) {
		if (objectToCopy==null) {
			return null;
		}
		Date newCopy;
		Constructor<?> constructor;
		
		constructor = ReflectionUtils.getNoArgConstructor(objectToCopy.getClass());
		if (constructor!=null) {
			newCopy = (Date) ReflectionUtils.newInstance(constructor, new Object[0]);
			newCopy.setTime(objectToCopy.getTime());
			return newCopy;						
		} 
		constructor = ReflectionUtils.getConstructor(objectToCopy.getClass(), long.class);
		if (constructor!=null) {
			Object[] args = {objectToCopy.getTime()};			
			newCopy = (Date) ReflectionUtils.newInstance(constructor, args);
			newCopy.setTime(objectToCopy.getTime());
			return newCopy;						
		} 
		String msg = "Could not create instance of " + objectToCopy.getClass(); //$NON-NLS-1$
		throw new RuntimeException(msg);
	}

}
