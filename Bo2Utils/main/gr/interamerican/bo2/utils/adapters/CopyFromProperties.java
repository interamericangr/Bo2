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

import java.util.Properties;

/**
 * Modifies an object by setting its properties equal to the values
 * specified in a {@link Properties} object. <br/>
 * 
 * The Properties object is specified in the constructor.
 * The modification of the object is performed by
 * {@link JavaBeanUtils#copyFromProperties(Properties, Object)}. <br/> 
 *  
 * @param <T> 
 *        Type of object being modified.
 */
public class CopyFromProperties<T>
implements Modification<T> {
	
	/**
	 * Properties to copy to the modified object.
	 */
	Properties properties;
	
	/**
	 * Creates a new CopyFromProperties object. 
	 *
	 * @param properties
	 */
	public CopyFromProperties(Properties properties) {
		super();
		this.properties = properties;
	}
	
	@Override
	public T execute(T a) {
		JavaBeanUtils.copyFromProperties(properties, a);
		return a;
	}
	
	
	

}
