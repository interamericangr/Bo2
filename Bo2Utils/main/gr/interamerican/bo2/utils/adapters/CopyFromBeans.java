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
package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.JavaBeanUtils;

/**
 * Modifies an object by setting its properties equal to the values
 * of the respective properties of a number of supplied beans.<br/>
 * 
 * The beans are specified in the constructor. Their order is
 * important.<br/>
 * 
 * The modification of the object is performed by
 * {@link JavaBeanUtils#copyProperties(Object, Object)}. <br/> 
 *  
 * @param <T> 
 *        Type of object being modified.
 */
public class CopyFromBeans<T>
implements Modification<T> {
	
	/**
	 * Beans whose properties are to be copied to the modified object.
	 */
	Object[] beans;
	
	/**
	 * Creates a new CopyFromBeans object. 
	 *
	 * @param beans
	 */
	public CopyFromBeans(Object... beans) {
		super();
		if(beans==null) {
			throw new RuntimeException("Cannot initialize with null."); //$NON-NLS-1$
		}
		this.beans = beans;
	}
	
	@Override
	public T execute(T a) {
		for(Object bean : beans) {
			JavaBeanUtils.copyProperties(bean, a);
		}
		return a;
	}

}
