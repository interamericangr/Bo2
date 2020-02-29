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
package gr.interamerican.bo2.utils.adapters.trans;

import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.FunctionalUtils;
import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.adapters.Transformation;

import org.apache.commons.beanutils.NestedNullException;

/**
 * Extracts properties of an object to an array of objects.
 * 
 * The names of the properties that are extracted are specified in the
 * constructor. The properties can be nested.
 * 
 * @deprecated Use {@link GetPropertiesFunctional}. The {@link #nullTolerant}
 *             option is controlled by each function (see
 *             {@link FunctionalUtils} for null tolerant functions
 */
@Deprecated
public class GetProperties 
implements Transformation<Object, Object[]>{

	/**
	 * Properties to extract.
	 */
	private String[] properties;
	
	/**
	 * Indicates if the property retrieval should be tolerant
	 * when intermediate property values are null.
	 * 
	 * For example, for the nested property "foo.bar", if foo
	 * is null and nullTolerant is true, null will be returned
	 * instead of throwing a runtime exception.
	 */
	private boolean nullTolerant;

	/**
	 * Creates a new GetProperties object. 
	 *
	 * @param properties the properties
	 */
	public GetProperties(String[] properties) {
		this(properties,false);
	}
	
	/**
	 * Creates a new GetProperties object. 
	 *
	 * @param properties the properties
	 * @param nullTolerant the null tolerant
	 */
	public GetProperties(String[] properties, boolean nullTolerant) {
		super();
		this.properties = properties;
		this.nullTolerant = nullTolerant;
	}

	@Override
	public Object[] execute(Object a) {
		Object[] values = new Object[properties.length];
		for (int i = 0; i < properties.length; i++) {
			try {
				values[i] = JavaBeanUtils.getNestedProperty(a, properties[i]);
			} catch(RuntimeException rtex) {
				if(ExceptionUtils.isCausedBy(rtex, NestedNullException.class) && nullTolerant) {
					values[i] = null;
				} else {
					throw rtex;
				}
			}
		}
		return values;
	}
}