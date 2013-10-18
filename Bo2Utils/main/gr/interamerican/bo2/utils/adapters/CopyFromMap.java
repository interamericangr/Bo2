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

import java.util.Map;

/**
 * Modifies an object by setting its properties equal to the values
 * specified in a {@link Map} object. <br/>
 * 
 * The Map object is specified in the constructor.
 * The modification of the object is performed by
 * {@link JavaBeanUtils#copyFromMap(Map, Object)}. 
 *  
 * @param <T> 
 */
public class CopyFromMap<T>
implements Modification<T> {
	
	/**
	 * Properties to copy to the modified object.
	 */
	Map<String, ?> map;
	
	/**
	 * Creates a new CopyFromProperties object. 
	 *
	 * @param map
	 */
	public CopyFromMap(Map<String, ?> map) {
		super();
		this.map = map;
	}
	
	@Override
	public T execute(T a) {
		JavaBeanUtils.copyFromMap(map, a);
		return a;
	}
	

}
